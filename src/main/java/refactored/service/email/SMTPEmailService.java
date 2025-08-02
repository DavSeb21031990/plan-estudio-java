package refactored.service.email;

import refactored.model.Order;

public class SMTPEmailService implements IEmailService {

    @Override
    public void sendConfirmation(String email, Order order) {
        // TODO: lógica para enviar email de confirmación
        System.out.println("Email sent to: " + email);
    }

}
