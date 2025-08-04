package refactored.exception;

public class TechnicalException extends ApplicationException {

  public TechnicalException(String errorCode, String message, Throwable cause){
    super(errorCode, message, cause);
  }

}
