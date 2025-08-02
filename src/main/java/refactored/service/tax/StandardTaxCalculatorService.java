package refactored.service.tax;

public class StandardTaxCalculatorService implements ITaxCalculatorService {

    public static final double TAX_RATE = 0.15;

    @Override
    public double calculateTax(double amount) {
        return amount * TAX_RATE;
    }

}
