---
title: Error Handling Philosophy
description: Guias para Error Handling Philosophy
---

## 🎯 Qué significa:
La filosofía/enfoque sobre CÓMO y CUÁNDO manejar errores en tu código.

## 🤔 Filosofías principales:

### Fail Fast Philosophy

```java
// ✅ Detectar errores temprano
public void processPayment(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive"); // Falla inmediatamente
    }
    // Continue processing...
}
```

### Defensive Programming
```java
// ✅ Validar todo, no asumir nada
public String getUserName(User user) {
    if (user == null) return "Unknown";
    if (user.getName() == null) return "No name";
    return user.getName();
}
```

### Exception vs Return Codes
```java
// ❌ Return codes (old style)
public int saveUser(User user) {
    if (user == null) return -1;    // Error code
    if (invalid) return -2;         // Different error code
    return 0;                       // Success
}

// ✅ Exceptions (modern)
public void saveUser(User user) {
    if (user == null) {
        throw new IllegalArgumentException("User cannot be null");
    }
    // Clear success path
}
```

## Uso recomendado

### FAIL FAST (Input Validation)
✅ **USAR SIEMPRE** en entradas de métodos públicos:
- Detectas problemas inmediatamente, no 10 métodos después.
```java
// ✅ SIEMPRE hacer esto
public void processOrder(String email, double amount) {
    if (email == null || email.trim().isEmpty()) {
        throw new IllegalArgumentException("Email cannot be null or empty");
    }
    if (amount <= 0) {
        throw new IllegalArgumentException("Amount must be positive");
    }
    // Continuar con confianza - inputs son válidos
}
```

### Exceptions vs Return Codes
#### ✅ USA EXCEPTIONS cuando:
- Condiciones inesperadas (programming errors)
- Violations de business rules
- External system failures

```java
// ✅ Exception - violation de business rule
if (user.getAge() < 18) {
    throw new BusinessRuleException("User must be 18+ to order alcohol");
}

// ✅ Exception - external system failure  
if (!paymentGateway.isAvailable()) {
    throw new PaymentServiceException("Payment service temporarily unavailable");
}
```

#### ✅ USA RETURN VALUES cuando:
- Expected failures (parte normal del flujo)
- Validation results
- Search operations

```java
// ✅ Return value - expected scenario
public Optional<User> findUserByEmail(String email) {
    // No encontrar user es escenario normal
    return Optional.ofNullable(userRepository.get(email));
}

// ✅ Return value - validation result
public ValidationResult validateUser(User user) {
    if (user.getEmail() == null) {
        return ValidationResult.invalid("Email required");
    }
    return ValidationResult.valid();
}
```

### Defensive Programming
✅ USA EN métodos internos/private:
```java
// ✅ Defensive - método interno
private String formatEmail(String email) {
    if (email == null) return "";  // Defensive - no crash
    return email.toLowerCase().trim();
}

// ✅ Aggressive - método público  
public void sendEmail(String email) {
    if (email == null) {
        throw new IllegalArgumentException("Email required"); // Fail fast
    }
    String formatted = formatEmail(email); // Método interno es defensive
}
```

### Exception Handling Layers
```java
// ✅ LAYER 1: Controllers - Catch y convert to HTTP responses
@RestController
public class OrderController {
    
    public ResponseEntity<String> createOrder(String email, double amount) {
        try {
            orderService.processOrder(email, amount);
            return ResponseEntity.ok("Order created");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400
        } catch (BusinessRuleException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // 400
        } catch (PaymentServiceException e) {
            return ResponseEntity.status(503).body("Service unavailable"); // 503
        }
    }
}

// ✅ LAYER 2: Services - Business logic, let exceptions bubble up
@Service  
public class OrderService {
    
    public void processOrder(String email, double amount) {
        // Validate inputs (fail fast)
        if (email == null) throw new IllegalArgumentException("Email required");
        
        // Business logic - let exceptions bubble up
        paymentService.charge(amount); // Si falla, exception sube
        emailService.send(email);     // Si falla, exception sube
    }
}

// ✅ LAYER 3: Repositories - Convert technical exceptions
@Repository
public class OrderRepository {
    
    public void save(Order order) {
        try {
            database.insert(order);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to save order", e); // Convert technical to business
        }
    }
}
```