---
title: Open Closed ‐ (OCP)
description: Explicación y ejemplos de Open Closed ‐ (OCP)
---

Abierto para la extensión cerrado para la modificación.
- Agrega nuevas funcionalidades **SIN** cambiar las existentes.
- **Strategy Pattern** permite esto creando algoritmos intercambiables
## Ejemplo
```java title="Estructura Base"
// 1. Strategy Interface
public interface PaymentStrategy {
    boolean processPayment(double amount);
}

// 2. Concrete Strategies
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Credit Card");
        return true;
    }
}

public class PayPalPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via PayPal");
        return true;
    }
}

// 3. Context
public class PaymentProcessor {
    private PaymentStrategy strategy;
    
    public void setPaymentStrategy(PaymentStrategy strategy) {
        this.strategy = strategy;
    }
    
    public boolean pay(double amount) {
        return strategy.processPayment(amount);
    }
}
```

```java title="Aplicación de Open/Closed"
// ✅ EXTENSIÓN - Agregar nueva estrategia SIN modificar código existente
public class CryptoPayment implements PaymentStrategy {
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Processing $" + amount + " via Cryptocurrency");
        return true;
    }
}

// Uso
PaymentProcessor processor = new PaymentProcessor();
processor.setPaymentStrategy(new CreditCardPayment());
processor.pay(100.0);

processor.setPaymentStrategy(new CryptoPayment()); // Nueva estrategia!
processor. Pay(50.0);
```