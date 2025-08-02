package refactored.service.payment;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CashPaymentTest {

    @Mock
    private IPaymentService paymentRepository;

    @InjectMocks
    private CashPaymentService cashPayment;

    @Test
    void pay() {
        cashPayment.processPayment(anyDouble());
        //verify(paymentRepository).savePayment();
    }
}