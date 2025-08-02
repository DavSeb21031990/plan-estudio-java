package refactored.service.payment;

public class CreditCardPaymentService implements IPaymentService {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Credit Card");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Credit Card";
    }
}
