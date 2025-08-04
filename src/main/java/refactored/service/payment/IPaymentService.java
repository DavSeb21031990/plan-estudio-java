package refactored.service.payment;

public interface IPaymentService {
    void processPayment(double amount);
    String getPaymentMethod();
}
