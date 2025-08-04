package refactored.repository.user;

import java.sql.SQLException;
import java.util.Optional;
import refactored.exception.TechnicalException;
import refactored.model.User;

public class MysqlUserRepository implements IUserRepository{

  @Override
  public User save(User user) throws TechnicalException {
    try{
      return saveDataBase(user);
    }catch (SQLException e){
      throw new TechnicalException("DATABASE_ERROR", "Failed to save user", e);
    }
  }

  private <T> T saveDataBase(T object) throws SQLException {
    return Optional.ofNullable(object)
        .orElseThrow(() -> new SQLException("Error"));
  }
}