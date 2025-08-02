package refactored.service.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CashPaymentService implements IPaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CashPaymentService.class);

  @Override
  public boolean processPayment(double amount) {
    LOGGER.info("Processing $ {} via Cash", amount);
    return true;
  }

  @Override
  public String getPaymentMethod() {
    return "Cash";
  }
}
