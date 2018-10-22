package name.anonymous.heros.batch.common.exception;

import org.springframework.batch.core.JobParametersInvalidException;

public class FileSequenceNotInitializedException extends JobParametersInvalidException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1193558084929810672L;

	public FileSequenceNotInitializedException(String msg) {
		super(msg);
	}
}
