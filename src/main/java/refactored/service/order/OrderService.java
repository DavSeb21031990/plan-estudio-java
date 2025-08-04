package refactored.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import refactored.exception.TechnicalException;
import refactored.model.Order;
import refactored.repository.order.IOrderRepository;
import refactored.service.email.IEmailService;
import refactored.service.payment.IPaymentService;
import refactored.service.tax.ITaxCalculatorService;

/**
 * The OrderService class provides the implementation of the IOrderService interface. It handles
 * the core functionality of processing orders, including tax calculation, order persistence,
 * payment processing, and sending confirmation emails. This class utilizes dependency injection
 * to manage its dependencies, allowing for flexibility and adherence to the Dependency Inversion
 * Principle.
 * <p/>
 * Responsibilities:
 * - Validate input data for order processing.
 * - Calculate applicable taxes based on the order amount using the tax calculator service.
 * - Process payments through the provided payment service strategy.
 * - Create an order with the calculated details and save it via the order repository.
 * - Send confirmation emails for successfully processed orders.
 */
public class OrderService implements IOrderService {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

  /**
   * Inyección de dependencias - Se usa las abstracciones y no las implementaciones evitando que la
   * clase esta fuertemente cohesionada con dependencias concretas.
   */
  private final ITaxCalculatorService taxCalculator;
  private final IOrderRepository orderRepository;
  private final IEmailService emailService;
  private final IPaymentService paymentStrategy;

  /**
   * Constructs an instance of the OrderService class with the required dependencies. This class
   * handles the processing of orders, including tax calculation, order persistence, email
   * notifications, and payment processing.
   *
   * @param taxCalculator   the service responsible for calculating taxes for a given order amount
   * @param orderRepository the repository for persisting order data
   * @param emailService    the service used to send confirmation emails for processed orders
   * @param paymentStrategy the strategy used for processing payments
   */
  public OrderService(ITaxCalculatorService taxCalculator, IOrderRepository orderRepository,
      IEmailService emailService, IPaymentService paymentStrategy) {
    this.taxCalculator = taxCalculator;
    this.orderRepository = orderRepository;
    this.emailService = emailService;
    this.paymentStrategy = paymentStrategy;
  }

  @Override
  public void processOrder(String email, double amount) {

    validateInputs(email, amount);

    double tax = this.taxCalculator.calculateTax(amount);
    this.paymentStrategy.processPayment(amount + tax);

    try{
      generateOrder(email, amount, tax);
    }catch (TechnicalException e){
      LOGGER.error("Error processing order: {}", e.getMessage());
      throw e;
    }

  }

  private void generateOrder(String email, double amount, double tax) {
    // Crear y guardar orden
    Order order = createOrder(email, amount, tax);
    // Envío de email de confirmación
    this.emailService.sendConfirmation(email, order);
    LOGGER.info("Order processed successfully");
  }

  private Order createOrder(String email, double amount, double tax) {
    double total = amount + tax;
    Order order = new Order(email, amount, tax, total);
    this.orderRepository.save(order);
    return order;
  }

  private void validateInputs(String email, double amount) {
    if (email == null || email.isBlank() || !email.contains("@")) {
      throw new IllegalArgumentException("Email is required");
    }
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
  }

}
