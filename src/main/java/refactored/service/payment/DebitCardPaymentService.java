package refactored.service.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DebitCardPaymentService implements IPaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(DebitCardPaymentService.class);

  @Override
  public boolean processPayment(double amount) {
    LOGGER.info("Processing $ {} via Debit", amount);
    return true;
  }

  @Override
  public String getPaymentMethod() {
    return "Debit";
  }
}
