package refactored.service.order;

import refactored.model.Order;
import refactored.repository.order.IOrderRepository;
import refactored.service.email.IEmailService;
import refactored.service.tax.ITaxCalculatorService;
import refactored.service.payment.IPaymentService;

public class OrderService implements IOrderService {

    /**
     * Inyección de dependencias
     * - Se usa las abstracciones y no las implementaciones evitando
     * que la clase esta fuertemente cohesionada con dependencias concretas
     */
    private final ITaxCalculatorService taxCalculator;
    private final IOrderRepository orderRepository;
    private final IEmailService emailService;
    private final IPaymentService paymentStrategy;

    public OrderService(ITaxCalculatorService taxCalculator, IOrderRepository orderRepository, IEmailService emailService, IPaymentService paymentStrategy) {
        this.taxCalculator = taxCalculator;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
        this.paymentStrategy = paymentStrategy;
    }

    @Override
    public void processOrder(String email, double amount){

        // Calcular impuestos
        double tax = this.taxCalculator.calculateTax(amount);
        double total = amount + tax;

        // Procesar pagos
        boolean paymentSuccess = this.paymentStrategy.processPayment(total);

        if(paymentSuccess){
            // Crear y guardar orden
            Order order = new Order(email, amount, tax, total);
            this.orderRepository.save(order);

            // Envío de email de confirmación
            this.emailService.sendConfirmation(email, order);

            System.out.println("Order processed successfully");
        }else{
            System.out.println("Payment failed");
        }

    }

}
