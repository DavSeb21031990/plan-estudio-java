package refactored.service.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import refactored.exception.TechnicalException;

public class CashPaymentService implements IPaymentService {

  private static final Logger LOGGER = LoggerFactory.getLogger(CashPaymentService.class);

  @Override
  public void processPayment(double amount) {
    try{
      LOGGER.info("Processing $ {} via Cash", amount);
    }catch (Exception e){
      throw new TechnicalException("PROCESS_PAYMENT_ERROR", e.getMessage(), e);
    }
  }

  @Override
  public String getPaymentMethod() {
    return "Cash";
  }
}
