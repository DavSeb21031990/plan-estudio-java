package refactored.repository.order;

import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import refactored.exception.TechnicalException;
import refactored.model.Order;

public class DatabaseOrderRepository implements IOrderRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseOrderRepository.class);

  @Override
  public void save(Order order) {
    try{
      this.saveDataBase(order);
    }catch (SQLException e){
      throw new TechnicalException("DATABASE_ERROR", "Failed to save order", e);
    }
  }

  private void saveDataBase(Order order) throws SQLException {
    if(order == null){
      throw new SQLException("Error en la base de datos");
    }
    LOGGER.info("Order saved to database: {}", order);
  }

}
