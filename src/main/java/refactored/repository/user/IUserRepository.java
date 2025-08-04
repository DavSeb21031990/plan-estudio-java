package refactored.repository.user;

import refactored.exception.TechnicalException;
import refactored.model.User;

public interface IUserRepository {
  User save(User user) throws TechnicalException;
}
