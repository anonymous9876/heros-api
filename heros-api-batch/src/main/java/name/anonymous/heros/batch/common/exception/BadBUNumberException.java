package name.anonymous.heros.batch.common.exception;

public class BadBUNumberException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -2675166868890672898L;

	public BadBUNumberException() {
	}

	public BadBUNumberException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadBUNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadBUNumberException(String message) {
		super(message);
	}

	public BadBUNumberException(Throwable cause) {
		super(cause);
	}


}
