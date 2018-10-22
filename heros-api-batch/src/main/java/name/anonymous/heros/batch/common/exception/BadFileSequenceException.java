package name.anonymous.heros.batch.common.exception;

import org.springframework.batch.core.JobParametersInvalidException;

public class BadFileSequenceException extends JobParametersInvalidException {

	/**
	 *
	 */
	private static final long serialVersionUID = 6784465705856999275L;

	public BadFileSequenceException(String msg) {
		super(msg);
	}
}
