package refactored;

import refactored.repository.order.DatabaseOrderRepository;
import refactored.repository.order.IOrderRepository;
import refactored.service.email.IEmailService;
import refactored.service.email.SMTPEmailService;
import refactored.service.order.OrderService;
import refactored.service.payment.IPaymentService;
import refactored.service.tax.ITaxCalculatorService;
import refactored.service.payment.CreditCardPaymentService;
import refactored.service.payment.PaypalPaymentService;
import refactored.service.tax.StandardTaxCalculatorService;

public class Main {

    public static void main(String[] args) {

        ITaxCalculatorService calculator = new StandardTaxCalculatorService();
        IPaymentService paymentStrategy = new CreditCardPaymentService();
        IOrderRepository repository = new DatabaseOrderRepository();
        IEmailService emailService = new SMTPEmailService();

        OrderService service = new OrderService(
                calculator,
                repository,
                emailService,
                paymentStrategy
        );

        // Procesar orden
        System.out.println("=== Processing Order ===");
        service.processOrder("john@example.com", 100.0);

        System.out.println("\n=== Changing Payment Method ===");
        // Demostrar OCP - cambiar strategy sin modificar código
        IPaymentService paypalStrategy = new PaypalPaymentService();
        OrderService orderServicePaypal = new OrderService(
                calculator,
                repository,
                emailService,
                paypalStrategy
        );

        orderServicePaypal.processOrder("jane@example.com", 50.0);
    }

}
