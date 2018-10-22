package name.anonymous.heros.batch.common.exception;

public class BadEntityException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -1808485790414976823L;

	public BadEntityException() {
	}

	public BadEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BadEntityException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadEntityException(String message) {
		super(message);
	}

	public BadEntityException(Throwable cause) {
		super(cause);
	}

}
