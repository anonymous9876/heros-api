package name.anonymous.heros.batch.common.jobexecutionlistener;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.anonymous.heros.batch.heros.model.entity.batch.Sequence;
import name.anonymous.heros.batch.heros.repository.SequenceRepository;
import name.anonymous.heros.batch.heros.util.properties.PropertiesService;

@Service
public class ImportFileJobListener implements JobExecutionListener {
	private static final Logger logger = LoggerFactory.getLogger(ImportFileJobListener.class);
	private static final String FILE_NAME_KEY = "fileName";
	private static final String SEQUENCE_NAME_KEY = "sequence";
	private static final String FILE_SEQ_KEY = "seq";

	private File importDirectoryOk;
	private File ImportDirectoryIn;
	private File importDirectoryTrash;
	private SequenceRepository sequenceRepository;

	@Autowired
	public ImportFileJobListener(PropertiesService propertiesService, SequenceRepository sequenceRepository) {
		importDirectoryOk = new File(propertiesService.getImportDirectoryOk());
		ImportDirectoryIn = new File(propertiesService.getImportDirectoryIn());
		importDirectoryTrash = new File(propertiesService.getImportDirectoryTrash());
		this.sequenceRepository = sequenceRepository;
	}

	@Override
	public void beforeJob(JobExecution jobExecution) {
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		String fileName = jobExecution.getJobParameters().getString(FILE_NAME_KEY);
		Long fileSeq = new Long(jobExecution.getJobParameters().getString(FILE_SEQ_KEY));
		File source;
		try {
			source = new File(new URL(fileName).toURI());
			File targetDirectory = null;
			boolean jobIsOK = jobExecution.getExitStatus().equals(ExitStatus.COMPLETED);
			if (jobIsOK) {
				targetDirectory = importDirectoryOk;
			} else {
//				targetDirectory = importDirectoryTrash;
				targetDirectory = ImportDirectoryIn;
			}

			File target = new File(targetDirectory.getAbsolutePath() + File.separator + source.getName());
			boolean renameHasSucceeded= true;
			try {
				Files.move(source.toPath(), target.toPath());
			} catch (Exception e) {
				renameHasSucceeded= false;
				logger.error(String.format("can not move %s to %s", source.getAbsolutePath(), target.getAbsolutePath()), e);
			}

			if (renameHasSucceeded) {
				logger.info(String.format("move %s to %s has succeeded", source.getAbsolutePath(), target.getAbsolutePath()));
				if (jobIsOK) {
					String sequenceName = jobExecution.getJobParameters().getString(SEQUENCE_NAME_KEY);
					Sequence entitySeq;
					Optional<Sequence> optionalSequence= sequenceRepository.findById(sequenceName);
					if (optionalSequence.isPresent()) {
					    entitySeq = optionalSequence.get();
					} else {
					    entitySeq = new Sequence();
					    entitySeq.setName(sequenceName);
					    entitySeq.setSeq(fileSeq);
					    entitySeq.setLastChange(new Date());
					}

					Long nextSequenceValue = entitySeq.getSeq().longValue()  + 1;
					entitySeq.setLastChange(new Date());
					entitySeq.setSeq(nextSequenceValue);
					sequenceRepository.save(entitySeq);
					logger.info(String.format("la sequence %s a été incrémenté après l'intégration du fichier %s", sequenceName,
							fileName));
				}
		}
		} catch (MalformedURLException | URISyntaxException e1) {
			logger.error(String.format("can not find %s", fileName), e1);
		}
	}
}
