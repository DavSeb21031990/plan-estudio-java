package refactored.service.payment;

public interface IPaymentService {
    boolean processPayment(double amount);
    String getPaymentMethod();
}
