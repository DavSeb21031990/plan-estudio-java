package refactored.service.tax;

import refactored.exception.BusinessException;

public class StandardTaxCalculatorService implements ITaxCalculatorService {

    public static final double TAX_RATE = 0.15;

    @Override
    public double calculateTax(double amount) {
        try{
            return amount * TAX_RATE;
        }catch (Exception e){
            throw new BusinessException("CALCULATION_TAXES_ERROR", e.getMessage());
        }
    }

}
