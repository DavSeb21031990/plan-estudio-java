package refactored.service.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DebitCardPaymentTest {

    @InjectMocks
    private DebitCardPaymentService debitCardPayment;

    @Test
    void pay() {
        debitCardPayment.processPayment(anyDouble());
        //verify(paymentRepository).savePayment();
    }
}