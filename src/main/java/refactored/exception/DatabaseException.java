package refactored.exception;

public class DatabaseException extends TechnicalException {

  public DatabaseException(String message, Throwable cause) {
    super("DATABASE_ERROR", message, cause);
  }
}
