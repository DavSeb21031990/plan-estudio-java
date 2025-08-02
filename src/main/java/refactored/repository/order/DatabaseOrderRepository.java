package refactored.repository.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import refactored.model.Order;

public class DatabaseOrderRepository implements IOrderRepository {

  private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseOrderRepository.class);

  @Override
  public void save(Order order) {
    LOGGER.info("Order saved to database");
  }

}
