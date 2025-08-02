package refactored.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import refactored.repository.order.IOrderRepository;
import refactored.service.email.IEmailService;
import refactored.service.payment.IPaymentService;
import refactored.service.tax.ITaxCalculatorService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

  @Mock
  ITaxCalculatorService taxCalculator;
  @Mock
  IOrderRepository orderRepository;
  @Mock
  IEmailService emailService;
  @Mock
  IPaymentService paymentStrategy;

  @InjectMocks OrderService orderService;


  @Test
  void whenEmailIsIncorrect_thenThrowException() {
    String email = "david";
    double amount = 100.0;

    Exception exception = assertThrows(IllegalArgumentException.class,
        () -> orderService.processOrder(email, amount));

    assertTrue(exception instanceof IllegalArgumentException);
    assertEquals("Email is required", exception.getMessage());

    verify(taxCalculator, never()).calculateTax(anyDouble());
  }
}
