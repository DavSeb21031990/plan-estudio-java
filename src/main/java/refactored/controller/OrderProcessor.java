package refactored.controller;

public class OrderProcessor {

    public void processOrder(String email, double amount) {
        // TODO: Identificar qué principio SOLID viola cada sección

        // Validación de email
        if (!email.contains("@")) {
            // ❌ Viola: [¿Cuál principio?]
        }

        // Cálculo de impuestos
        double tax = amount * 0.15;
        // ❌ Viola: [¿Cuál principio?]

        // Guardar en base de datos
        System.out.println("Saving to database...");
        // ❌ Viola: [¿Cuál principio?]

        // Enviar email
        System.out.println("Sending email...");
        // ❌ Viola: [¿Cuál principio?]
    }

}
