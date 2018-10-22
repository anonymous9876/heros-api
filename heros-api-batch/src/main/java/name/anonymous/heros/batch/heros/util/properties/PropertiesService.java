package name.anonymous.heros.batch.heros.util.properties;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Service;

@Service
public class PropertiesService {
	public PropertiesService() {
		super();
		importDirectoryIn = "C:\\tmp\\indirect-supplier\\BATCH\\in";
		importDirectoryTrash = "C:\\tmp\\indirect-supplier\\BATCH\\trash";
		importDirectoryOk = "C:\\tmp\\indirect-supplier\\BATCH\\out";

		importOrdersDirectoryFile = "Mission_";
		importOrdersDirectoryExt = ".csv";
	}

	// Properties des batchs
	// Imports
	private String importDirectoryIn;
	private String importDirectoryTrash;
	private String importDirectoryOk;

	private String importOrdersDirectoryFile;
	private String importOrdersDirectoryExt;

	public String getImportDirectoryIn() {
		return importDirectoryIn;
	}

	public void setImportDirectoryIn(String importDirectoryIn) {
		this.importDirectoryIn = importDirectoryIn;
	}

	public String getImportDirectoryTrash() {
		return importDirectoryTrash;
	}

	public void setImportDirectoryTrash(String importDirectoryTrash) {
		this.importDirectoryTrash = importDirectoryTrash;
	}

	public String getImportDirectoryOk() {
		return importDirectoryOk;
	}

	public void setImportDirectoryOk(String importDirectoryOk) {
		this.importDirectoryOk = importDirectoryOk;
	}

	public String getImportOrdersDirectoryFile() {
		return importOrdersDirectoryFile;
	}

	public void setImportOrdersDirectoryFile(String importOrdersDirectoryFile) {
		this.importOrdersDirectoryFile = importOrdersDirectoryFile;
	}

	public String getImportOrdersDirectoryExt() {
		return importOrdersDirectoryExt;
	}

	public void setImportOrdersDirectoryExt(String importOrdersDirectoryExt) {
		this.importOrdersDirectoryExt = importOrdersDirectoryExt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
