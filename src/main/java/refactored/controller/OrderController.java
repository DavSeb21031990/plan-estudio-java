package refactored.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderController {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

  public void processOrder(String email, double amount) {

    // Validación de email
    if (!email.contains("@")) {
      // ❌ Viola: [¿Cuál principio?]
      LOGGER.error("Invalid email");
    }

    // Cálculo de impuestos
    double tax = amount * 0.15;
    LOGGER.info("Tax: {}", tax);
    // ❌ Viola: [¿Cuál principio?]

    // Guardar en base de datos
    LOGGER.info("Saving to database...");
    // ❌ Viola: [¿Cuál principio?]

    // Enviar email
    LOGGER.info("Sending email...");
    // ❌ Viola: [¿Cuál principio?]
  }

}
