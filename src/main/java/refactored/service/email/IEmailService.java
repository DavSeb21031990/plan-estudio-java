package refactored.service.email;

import refactored.model.Order;

public interface IEmailService {
    void sendConfirmation(String email, Order order);
}
