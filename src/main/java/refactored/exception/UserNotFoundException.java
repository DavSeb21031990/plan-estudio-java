package refactored.exception;

public class UserNotFoundException extends BusinessException {

  UserNotFoundException(String message) {
    super("USER_NOT_FOUND", message);
  }

}
