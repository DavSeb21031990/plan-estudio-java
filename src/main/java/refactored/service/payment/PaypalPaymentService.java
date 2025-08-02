package refactored.service.payment;

public class PaypalPaymentService implements IPaymentService {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Paypal");
        return false;
    }

    @Override
    public String getPaymentMethod() {
        return "Paypal";
    }
}
