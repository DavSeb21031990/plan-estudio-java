package refactored.service.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreditCardPaymentService implements IPaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardPaymentService.class);

  @Override
  public boolean processPayment(double amount) {
    LOGGER.info("Processing $ {} via Credit Card", amount);
    return true;
  }

  @Override
  public String getPaymentMethod() {
    return "Credit Card";
  }
}
