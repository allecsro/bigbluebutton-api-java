package ro.alecsandru.bigbluebutton;

public class BigBlueButtonException extends RuntimeException {
	public BigBlueButtonException() {
		super();
	}

	public BigBlueButtonException(String message) {
		super(message);
	}

	public BigBlueButtonException(String message, Throwable cause) {
		super(message, cause);
	}

	public BigBlueButtonException(Throwable cause) {
		super(cause);
	}

	protected BigBlueButtonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
