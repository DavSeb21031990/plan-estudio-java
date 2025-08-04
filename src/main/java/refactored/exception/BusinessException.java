package refactored.exception;

public class BusinessException extends ApplicationException {

  public BusinessException(String errorCode, String message){
    super(errorCode, message);
  }

}
