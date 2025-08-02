package refactored.service.tax;

public class StandardTaxCalculatorService implements ITaxCalculatorService {

    @Override
    public double calculateTax(double amount) {
        // TODO: lógica de cálculo de impuestos
        return amount * 0.15;
    }

}
