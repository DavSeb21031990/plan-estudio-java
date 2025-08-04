package refactored.service.email;

import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import refactored.exception.ValidationException;
import refactored.model.Order;

public class SMTPEmailService implements IEmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SMTPEmailService.class);

    @Override
    public void sendConfirmation(String email, Order order) {
        if( email == null || email.isBlank() || !email.contains("@")){
            throw new ValidationException("Invalid email");
        }
        if(Objects.isNull(order)){
            throw new ValidationException("Order must not be null");
        }
        LOGGER.info("Email sent to: {}", email);
    }

}
