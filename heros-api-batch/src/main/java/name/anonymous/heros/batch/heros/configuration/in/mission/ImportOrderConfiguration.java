package name.anonymous.heros.batch.heros.configuration.in.mission;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import name.anonymous.heros.batch.common.exception.BadFileSequenceException;
import name.anonymous.heros.batch.common.jobexecutionlistener.ImportFileJobListener;
import name.anonymous.heros.batch.common.partitioner.MultiResourcePartitionerPerserveOrder;
import name.anonymous.heros.batch.common.stepexecutionsplitter.SimpleStepExecutionSplitterPreserveOrder;
import name.anonymous.heros.batch.heros.model.entity.api.Hero;
import name.anonymous.heros.batch.heros.model.entity.api.Mission;
import name.anonymous.heros.batch.heros.model.entity.api.ProductLineItem;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.Address;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.MissionState;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.Money;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.Person;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.Product;
import name.anonymous.heros.batch.heros.model.entity.api.embeddable.Version;
import name.anonymous.heros.batch.heros.model.entity.batch.Sequence;
import name.anonymous.heros.batch.heros.model.entity.esb.EsbMission;
import name.anonymous.heros.batch.heros.repository.SequenceRepository;
import name.anonymous.heros.batch.heros.util.properties.PropertiesService;

@Configuration
public class ImportOrderConfiguration {
	private static final int BASH_CHUNK_NUMBER = 50;

	private static final String SER_TYPE_LIG = "SER";

	private static final String SEQUENCE_VALUE = "indirect-supplier";

	private static final String SEQUENCE_NAME_KEY = "sequence";

	private static final String FILE_NAME_KEY = "fileName";
	private static final String FILE_SEQ_KEY = "seq";

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportOrderConfiguration.class);

	@Autowired
	private JobBuilderFactory jobBuilderFactory;

	@Autowired
	private StepBuilderFactory stepBuilderFactory;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private SequenceRepository sequenceRepository;

	@Bean
	public ItemProcessor<EsbMission, EsbMission> itemProcessorImport() {
		return item -> {
			return item;
		};
	}

	@Bean
	public ItemProcessor<Mission, Mission> itemProcessorMissionIntegration(
			PropertiesService propertiesService, EntityManager entityManager) {
		return item -> {
			Mission oldMission = entityManager.unwrap(Session.class)
					.byNaturalId(Mission.class).using("num", item.getNum()).load();
			if (oldMission != null) {
				item.setId(oldMission.getId());
				item.setProductLineItems(oldMission.getProductLineItems());
			}
			return item;
		};
	}

	@Bean
	public ItemProcessor<ProductLineItem, ProductLineItem> itemProcessorProductLineItemApiModelIntegration(
			PropertiesService propertiesService, EntityManager entityManager) {
		return item -> {
			ProductLineItem oldProductLineItem = entityManager.unwrap(Session.class)
					.byNaturalId(ProductLineItem.class)
					.using("Mission", item.getMission())
					.using("numLig", item.getNumLig())
					.load();

			if (oldProductLineItem != null) {
				item.setId(oldProductLineItem.getId());
			}
			if (!SER_TYPE_LIG.equals(item.getTypeLig())) {
				item.setMntLig(item.getQte() * item.getPu());
			}
			if (Integer.parseInt(item.getMission().getVersion().getNum()) > 0 && item.getDateLivSouh() != null) {
				item.setDateLivAccept(item.getDateLivSouh());
			}
			return item;
		};
	}

	@StepScope
	@Bean
	public FlatFileItemReader<EsbMission> itemReaderImport(@Value("#{jobParameters[fileName]}") String fileName) {
		FlatFileItemReader<EsbMission> itemReader = new FlatFileItemReader<>();
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		itemReader.setResource(patternResolver.getResource(fileName));

		DefaultLineMapper<EsbMission> lineMapper = new DefaultLineMapper<>();
		DelimitedLineTokenizer delimitedLineTokenizer = new DelimitedLineTokenizer(";");
		delimitedLineTokenizer.setNames("num_bu", "num", "dat_creation", "version_cde", "etat", "etat_date", "num_four",
				"nom_four", "mnt_cde", "devise_cde", "date_liv_souh", "date_liv_annon", "adr_lib_adr001",
				"adr_lib_adr002", "adr_lib_adr003", "adr_cod_pst", "adr_lib_vil", "adr_num_pay", "dem_nom",
				"dem_prenom", "dem_email", "dem_ldap", "num_lig", "num_art_int", "num_art_fou", "description_art",
				"unite", "qte", "pu", "mnt_lig", "type_lig", "qte_rcpt");
		lineMapper.setLineTokenizer(delimitedLineTokenizer);

		BeanWrapperFieldSetMapper<EsbMission> beanWrapperFieldSetMapper = new BeanWrapperFieldSetMapper<>();
		beanWrapperFieldSetMapper.setTargetType(EsbMission.class);
		HashMap<String, CustomDateEditor> customEditors = new HashMap<>();
		CustomDateEditor customDateEditor = new CustomDateEditor(new SimpleDateFormat("yyyyMMdd"), true);
		customEditors.put("java.util.Date", customDateEditor);
//		customEditors.put("java.time.LocalDate", customDateEditor);//NOT WORK

		beanWrapperFieldSetMapper.setCustomEditors(customEditors);
		lineMapper.setFieldSetMapper(beanWrapperFieldSetMapper);

		itemReader.setLineMapper(lineMapper);
		itemReader.setEncoding("UTF-8");
		itemReader.setName("OrderReaderImport");
		return itemReader;
	}

	@Bean(destroyMethod = "")
	public JdbcCursorItemReader<Mission> MissionItemReader(DataSource dataSource) {
		JdbcCursorItemReader<Mission> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		jdbcCursorItemReader.setDataSource(dataSource);

		jdbcCursorItemReader.setSql(
				"select distinct num_cde, dat_cde, version_cde, etat, etat_date, num_four, nom_four, mnt_cde, devise_cde from esb_order_indirect");
		jdbcCursorItemReader.setRowMapper((ResultSet rs, int rowNum) -> {
			Mission header = new Mission();
			header.setNum(rs.getString("num"));

			Version version = new Version();
			version.setNum(rs.getString("version"));
			version.setDate(rs.getDate("date"));
			header.setVersion(version);

			MissionState state = new MissionState();
			state.setValue(rs.getString("state"));
			state.setLastModified(rs.getDate("last_modified"));
			header.setState(state);
			Hero hero = new Hero();
			hero.setName(rs.getString("nom_hero"));
			hero.setNum(rs.getString("num_hero"));
			header.setHero(hero);

			Money totalAmout = new Money();
			totalAmout.setTtc(rs.getDouble("totalAmount"));
			totalAmout.setCurrency(rs.getString("currency"));
			return header;
		});
		return jdbcCursorItemReader;
	}

	@Bean(destroyMethod = "")
	public JdbcCursorItemReader<ProductLineItem> ProductLineItemItemReader(DataSource dataSource,
			EntityManager em) {
		JdbcCursorItemReader<ProductLineItem> jdbcCursorItemReader = new JdbcCursorItemReader<>();
		jdbcCursorItemReader.setDataSource(dataSource);

		jdbcCursorItemReader.setSql("select oih.id as order_indirect_header_id, eoi.num_cde, "
				+ "eoi.date_liv_souh, eoi.date_liv_annon, "
				+ "eoi.adr_lib_adr001, eoi.adr_lib_adr002, eoi.adr_lib_adr003, eoi.adr_cod_pst, eoi.adr_lib_vil, eoi.adr_num_pay, "
				+ "eoi.dem_nom, eoi.dem_prenom, eoi.dem_email, eoi.dem_ldap, "
				+ "eoi.num_lig, eoi.num_art_int, eoi.num_art_fou, eoi.description_art, eoi.unite, eoi.qte, eoi.pu, eoi.mnt_lig, eoi.type_lig, eoi.qte_rcpt "
				+ "from esb_order_indirect eoi " + "join order_indirect_header oih on eoi.num_cde = oih.num_cde ");
		jdbcCursorItemReader.setRowMapper((ResultSet rs, int rowNum) -> {
			ProductLineItem detail = new ProductLineItem();
			UUID MissionIdPrimaryKey = UUID.fromString(rs.getString("order_indirect_header_id"));
			Mission newMission = em.find(Mission.class, MissionIdPrimaryKey);
			detail.setMission(newMission);

			detail.setDateLivSouh(rs.getDate("date_liv_souh"));
			detail.setDateLivAnnon(rs.getDate("date_liv_annon"));

			Address delivryAddress = new Address();
			delivryAddress.setAdrLibAdr001(rs.getString("adr_lib_adr001"));
			delivryAddress.setAdrLibAdr002(rs.getString("adr_lib_adr002"));
			delivryAddress.setAdrLibAdr003(rs.getString("adr_lib_adr003"));
			delivryAddress.setAdrCodPst(rs.getString("adr_cod_pst"));
			delivryAddress.setAdrLibVil(rs.getString("adr_lib_vil"));
			delivryAddress.setAdrNumPay(rs.getString("adr_num_pay"));
			detail.setDelivryAddress(delivryAddress);

			Person buyer = new Person();
			buyer.setLastName(rs.getString("dem_nom"));
			buyer.setFirstName(rs.getString("dem_prenom"));
			buyer.setEmail(rs.getString("dem_email"));
			buyer.setLdap(rs.getString("dem_ldap"));
			detail.setBuyer(buyer);

			detail.setNumLig(rs.getInt("num_lig"));

			Product product = new Product();
			product.setNumArtInt(rs.getString("num_art_int"));
			product.setNumArtFou(rs.getString("num_art_fou"));
			product.setDescriptionArt(rs.getString("description_art"));
			detail.setProduct(product);

			detail.setUnite(rs.getString("unite"));
			detail.setQte(rs.getInt("qte"));
			detail.setPu(rs.getDouble("pu"));
			detail.setMntLig(rs.getDouble("mnt_lig"));
			detail.setTypeLig(rs.getString("type_lig"));
			detail.setQteRcpt(rs.getInt("qte_rcpt"));
			return detail;
		});
		return jdbcCursorItemReader;
	}

	@Bean(name = "heros.ImportOrder.importFilesJob")
	public Job importFilesJob(@Qualifier("heros.ImportOrder.partitionFilesSteps") Step partitionStep,
			PropertiesService propertiesService) {
		return jobBuilderFactory.get("heros.ImportOrder.importFilesJob").incrementer(new RunIdIncrementer())
				.validator((JobParameters parameters) -> {
					File directoryIn = new File(propertiesService.getImportDirectoryIn());
					if (!directoryIn.exists()) {
						throw new IllegalStateException(String.format("ImportDirectoryIn doesn't exist : %s",
								propertiesService.getImportDirectoryIn()));
					} else if (!directoryIn.isDirectory()) {
						throw new IllegalStateException(String.format("ImportDirectoryIn is not a directory : %s",
								propertiesService.getImportDirectoryIn()));
					} else if (!directoryIn.canRead()) {
						throw new IllegalStateException(String.format("ImportDirectoryIn can not read files : %s",
								propertiesService.getImportDirectoryIn()));
					}
				}).flow(partitionStep).end().build();
	}

	@Bean(name = "heros.ImportOrder.importFileJob")
	public Job importFileJob(@Qualifier("heros.ImportOrder.importFile") Step importFile,
			@Qualifier("heros.ImportOrder.integrateMissionStep") Step integrateMissionStep,
			@Qualifier("heros.ImportOrder.integrateProductLineItemStep") Step integrateProductLineItemStep,
			@Qualifier("heros.ImportOrder.beforeImport") Step beforeImport,
			ImportFileJobListener importFileJobListener) {
		return jobBuilderFactory.get("heros.ImportOrder.importFileJob").incrementer(new RunIdIncrementer())
				.validator((JobParameters parameters) -> {
					String fileName = parameters.getString(FILE_NAME_KEY);
					Long fileSeq = new Long(parameters.getString(FILE_SEQ_KEY));
					String sequenceName = parameters.getString(SEQUENCE_NAME_KEY);
					Sequence entitySeq;
					Optional<Sequence> optionalSequence = sequenceRepository.findById(sequenceName);
					if (optionalSequence.isPresent()) {
						entitySeq = optionalSequence.get();
					} else {
						entitySeq = new Sequence();
						entitySeq.setName(sequenceName);
						entitySeq.setSeq(fileSeq);
						entitySeq.setLastChange(new Date());
					}
					if (!fileSeq.equals(entitySeq.getSeq())) {
						throw new BadFileSequenceException(String.format(
								"Sequence incorrecte -- attendue = %s, le fichier %s ne sera pas importé.",
								entitySeq.getSeq(), fileName));
					}
					LOGGER.info(String.format("la sequence %s du fichier %s est correcte", sequenceName, fileName));

					File file;
					try {
						file = new File(new URL(fileName).toURI());
						if (!file.canRead()) {
							throw new IllegalStateException(
									String.format("ImportOrder can not read files : %s", fileName));
						} else if (!file.canWrite()) {
							throw new IllegalStateException(
									String.format("ImportOrder can not delete files : %s", fileName));
						}
					} catch (MalformedURLException | URISyntaxException e) {
						LOGGER.error("", e);
					}
				}).listener(importFileJobListener).flow(beforeImport).next(importFile)
				.next(integrateMissionStep).next(integrateProductLineItemStep).end().build();
	}

	@Bean
	@Qualifier("heros.ImportOrder.runImportFile")
	public Step runImportFile(@Qualifier("heros.ImportOrder.importFileJob") Job importFileJob) {
		return stepBuilderFactory.get("runImportFile").job(importFileJob)
				.parametersExtractor((Job job, StepExecution stepExecution) -> {
					String fileName = stepExecution.getExecutionContext().get(FILE_NAME_KEY).toString();
					String seq = fileName.split("_")[2];
					Map<String, JobParameter> parameters = new HashMap<>();
					// FILE_NAME_KEY = java.net.URL.toExternalForm()
					parameters.put(FILE_NAME_KEY, new JobParameter(fileName));
					parameters.put(FILE_SEQ_KEY, new JobParameter(seq));
					parameters.put(SEQUENCE_NAME_KEY, new JobParameter(SEQUENCE_VALUE));
					return job.getJobParametersIncrementer().getNext(new JobParameters(parameters));
				}).build();
	}

	@Bean
	@Qualifier("heros.ImportOrder.partitionFilesSteps")
	public Step partitionFilesSteps(MultiResourcePartitionerPerserveOrder partitioner,
			@Qualifier("heros.ImportOrder.runImportFile") Step runImportFile) {
		return stepBuilderFactory.get("partitionFilesSteps").partitioner("runImportFile", partitioner)
				.splitter(new SimpleStepExecutionSplitterPreserveOrder(jobRepository, false, "runImportFileSplitter",
						partitioner))
				.step(runImportFile).build();
	}

	@Bean
	@Qualifier("heros.ImportOrder.beforeImport")
	public Step beforeImport(
			@Qualifier("heros.ImportOrder.truncateImportOrderTableTasklet") Tasklet truncateImportOrderTableTasklet) {
		return stepBuilderFactory.get("heros.ImportOrder.beforeImport").tasklet(truncateImportOrderTableTasklet)
				.build();
	}

	@Bean
	@Qualifier("heros.ImportOrder.importFile")
	public Step importFile(FlatFileItemReader<EsbMission> itemReaderImport,
			JpaItemWriter<EsbMission> jpaEsbMissionItemWriter) {
		return stepBuilderFactory.get("heros.ImportOrder.importFile")
				.<EsbMission, EsbMission>chunk(BASH_CHUNK_NUMBER)
				.reader(itemReaderImport)
				.processor(itemProcessorImport())
				.writer(jpaEsbMissionItemWriter).build();
	}

	@Bean
	@Qualifier("heros.ImportOrder.integrateMissiontep")
	public Step integrateMissiontep(
			JdbcCursorItemReader<Mission> MissiontemReader,
			ItemProcessor<Mission, Mission> itemProcessorMissionntegration,
			JpaItemWriter<Mission> jpaMissionpiModelItemWriter) {
		return stepBuilderFactory
				.get("heros.ImportOrder.integrateMissiontep")
				.<Mission, Mission>chunk(BASH_CHUNK_NUMBER)
				.reader(MissiontemReader)
				.processor(itemProcessorMissionntegration)
				.writer(jpaMissionpiModelItemWriter)
				.build();
	}

	@Bean(destroyMethod = "")
	@Qualifier("heros.ImportOrder.integrateMProductLineItemtep")
	public Step integrateMProductLineItemtep(
			JdbcCursorItemReader<ProductLineItem> MProductLineItemtemReader,
			ItemProcessor<ProductLineItem, ProductLineItem> itemProcessorMissionntegration,
			JpaItemWriter<ProductLineItem> jpaMissionpiModelItemWriter) {
		return stepBuilderFactory.get("heros.ImportOrder.integrateMProductLineItemtep")
				.<ProductLineItem, ProductLineItem>chunk(BASH_CHUNK_NUMBER)
				.reader(MProductLineItemtemReader)
				.processor(itemProcessorMissionntegration)
				.writer(jpaMissionpiModelItemWriter)
				.build();
	}

	@StepScope
	@Bean
	public MultiResourcePartitionerPerserveOrder partitioner(PropertiesService propertiesService) {
		MultiResourcePartitionerPerserveOrder partitioner = new MultiResourcePartitionerPerserveOrder();
		Path importDirectoryIn = Paths.get(propertiesService.getImportDirectoryIn());
		Path importOrderDirectoryFile = Paths.get(propertiesService.getImportOrdersDirectoryFile());
		Path importASNTrackingDirectoryExt = Paths.get(propertiesService.getImportOrdersDirectoryExt());
		Resource[] resources;
		PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		try {
			resources = patternResolver
					.getResources(String.format("file:%s/%s*%s", importDirectoryIn.toString().replaceAll("\\\\", "/"),
							importOrderDirectoryFile, importASNTrackingDirectoryExt));
		} catch (IOException e) {
			throw new RuntimeException("I/O problems when resolving the input file pattern.", e);
		}
		// ordonner les ressources pour qu'elle soit traiter de manière chronologique
		Arrays.sort(resources, Comparator.comparing(Resource::getFilename));
		partitioner.setResources(resources);
		return partitioner;
	}

	/**
	 * comportement du writer : insert la ligne si la clé primaire est absente (de
	 * la table ou de l'entite si generee) update la ligne si la clé primaire est
	 * déjà présente il n'y a aucune suppression, il faut truncate manuellement la
	 * table pour éviter des traitement inutiles si la table est technique
	 *
	 * @param localContainerEntityManagerFactoryBean
	 * @return un jpaEsbMissionItemWriter de Spring
	 */
	@Bean
	@Qualifier("heros.ImportOrder.jpaEsbMissionItemWriter")
	public JpaItemWriter<EsbMission> jpaEsbMissionItemWriter(
			LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		JpaItemWriter<EsbMission> itemWriter = new JpaItemWriter<>();
		itemWriter.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return itemWriter;
	}

	@Bean
	@Qualifier("heros.ImportOrder.jpaMissionItemWriter")
	public JpaItemWriter<Mission> missionItemWriter(
			LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		JpaItemWriter<Mission> itemWriter = new JpaItemWriter<>();
		itemWriter.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return itemWriter;
	}

	@Bean
	@Qualifier("heros.ImportOrder.jpaProductLineItemItemWriter")
	public JpaItemWriter<ProductLineItem>productLineItemItemWriter(
			LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
		JpaItemWriter<ProductLineItem> itemWriter = new JpaItemWriter<>();
		itemWriter.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
		return itemWriter;
	}

	@Bean
	@Qualifier("heros.ImportOrder.truncateImportOrderTableTasklet")
	public Tasklet truncateImportOrderTableTasklet(JdbcTemplate jdbcTemplate) {
		return (StepContribution contribution, ChunkContext chunkContext) -> {
			jdbcTemplate.execute("truncate esb_order_indirect");
			LOGGER.info("truncateTable MissionBatchModel");
			return RepeatStatus.FINISHED;
		};
	}
}
