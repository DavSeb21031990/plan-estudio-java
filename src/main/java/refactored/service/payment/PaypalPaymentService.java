package refactored.service.payment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PaypalPaymentService implements IPaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaypalPaymentService.class);

    @Override
    public boolean processPayment(double amount) {
        LOGGER.info("Processing ${} via Paypal", amount);
        return false;
    }

    @Override
    public String getPaymentMethod() {
        return "Paypal";
    }
}
