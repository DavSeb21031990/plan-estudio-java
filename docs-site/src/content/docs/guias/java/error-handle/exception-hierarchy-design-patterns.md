1---
title: Exception Hierarchy Design Patterns
description: Guias para Exception Hierarchy Design Patterns
---

Organizar tus **custom exceptions** en una **estructura jerárquica lógica** que facilite el manejo y
categorización de errores.

## 🏗️ Patrón Básico: Hierarchy by Layer

### Base Exception (Root):
````java
// ✅ Exception base para toda tu aplicación
public abstract class ApplicationException extends RuntimeException {
    private final String errorCode;
    private final LocalDateTime timestamp;
    private final Map<String, Object> context;
    
    protected ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.context = new HashMap<>();
    }
    
    protected ApplicationException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.timestamp = LocalDateTime.now();
        this.context = new HashMap<>();
    }
    
    // Getters...
    public String getErrorCode() { return errorCode; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Map<String, Object> getContext() { return context; }
}
````
### Layer-Specific Exceptions:
````java
// ✅ Domain Layer Exceptions
public abstract class DomainException extends ApplicationException {
    protected DomainException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// ✅ Application Layer Exceptions  
public abstract class ApplicationLayerException extends ApplicationException {
    protected ApplicationLayerException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// ✅ Infrastructure Layer Exceptions
public abstract class InfrastructureException extends ApplicationException {
    protected InfrastructureException(String errorCode, String message) {
        super(errorCode, message);
    }
    
    protected InfrastructureException(String errorCode, String message, Throwable cause) {
        super(errorCode, message, cause);
    }
}
````
## 🎯 Patrón por funcionalidad:
### Specific Domain Exceptions:
````java
// ✅ Business Rule Violations
public class BusinessRuleException extends DomainException {
    public BusinessRuleException(String rule, String details) {
        super("BUSINESS_RULE_VIOLATION", 
              String.format("Business rule '%s' violated: %s", rule, details));
    }
}

public class InvalidEmailException extends DomainException {
    public InvalidEmailException(String email) {
        super("INVALID_EMAIL", "Email format is invalid: " + email);
        getContext().put("providedEmail", email);
    }
}

public class InsufficientFundsException extends DomainException {
    public InsufficientFundsException(double requested, double available) {
        super("INSUFFICIENT_FUNDS", 
              String.format("Requested: %.2f, Available: %.2f", requested, available));
        getContext().put("requestedAmount", requested);
        getContext().put("availableAmount", available);
    }
}
````
### Application Service Exceptions:
````java
// ✅ Use Case Failures
public class UserCreationFailedException extends ApplicationLayerException {
    public UserCreationFailedException(String reason) {
        super("USER_CREATION_FAILED", "Failed to create user: " + reason);
    }
}

public class OrderProcessingException extends ApplicationLayerException {
    public OrderProcessingException(String orderId, String reason) {
        super("ORDER_PROCESSING_FAILED", 
              String.format("Order %s processing failed: %s", orderId, reason));
        getContext().put("orderId", orderId);
    }
}

public class ValidationException extends ApplicationLayerException {
    private final List<String> violations;
    
    public ValidationException(List<String> violations) {
        super("VALIDATION_FAILED", "Input validation failed");
        this.violations = new ArrayList<>(violations);
        getContext().put("violations", violations);
    }
    
    public List<String> getViolations() { return new ArrayList<>(violations); }
}
````
### Infrastructure Exceptions:
````java
// ✅ Technical/External Failures
public class DatabaseException extends InfrastructureException {
    public DatabaseException(String operation, Throwable cause) {
        super("DATABASE_ERROR", "Database operation failed: " + operation, cause);
        getContext().put("operation", operation);
    }
}

public class ExternalServiceException extends InfrastructureException {
    public ExternalServiceException(String serviceName, String operation, Throwable cause) {
        super("EXTERNAL_SERVICE_ERROR", 
              String.format("External service '%s' failed during '%s'", serviceName, operation), 
              cause);
        getContext().put("serviceName", serviceName);
        getContext().put("operation", operation);
    }
}

public class PaymentGatewayException extends ExternalServiceException {
    public PaymentGatewayException(String gateway, String reason, Throwable cause) {
        super(gateway, "payment_processing", cause);
        getContext().put("reason", reason);
    }
}
````
## Patrón de Severity Levels:
### Exceptions por Severidad:
````java
// ✅ Critical - Requiere atención inmediata
public class CriticalException extends ApplicationException {
    protected CriticalException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// ✅ Warning - Importante pero no crítico
public class WarningException extends ApplicationException {
    protected WarningException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// ✅ Info - Informacional
public class InfoException extends ApplicationException {
    protected InfoException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// Ejemplos específicos:
public class DataCorruptionException extends CriticalException {
    public DataCorruptionException(String details) {
        super("DATA_CORRUPTION", "Critical: Data corruption detected - " + details);
    }
}

public class SlowResponseException extends WarningException {
    public SlowResponseException(long responseTime) {
        super("SLOW_RESPONSE", "Response time exceeded threshold: " + responseTime + "ms");
    }
}
````
## Patrón Recover Strategy:
### Exceptions por Recovery Strategy:
````java
// ✅ Retryable - Se puede reintentar
public abstract class RetryableException extends ApplicationException {
    private final int maxRetries;
    private int attemptCount = 0;
    
    protected RetryableException(String errorCode, String message, int maxRetries) {
        super(errorCode, message);
        this.maxRetries = maxRetries;
    }
    
    public boolean canRetry() { return attemptCount < maxRetries; }
    public void incrementAttempt() { attemptCount++; }
}

// ✅ Fatal - No se puede recuperar
public abstract class FatalException extends ApplicationException {
    protected FatalException(String errorCode, String message) {
        super(errorCode, message);
    }
}

// Ejemplos:
public class NetworkTimeoutException extends RetryableException {
    public NetworkTimeoutException() {
        super("NETWORK_TIMEOUT", "Network operation timed out", 3);
    }
}

public class AuthenticationException extends FatalException {
    public AuthenticationException(String reason) {
        super("AUTH_FAILED", "Authentication failed: " + reason);
    }
}
````
## Exception Translation Pattern:
### Translation Between Layers:
````java
// ✅ Repository Layer
@Repository
public class UserRepository {
    
    public User save(User user) {
        try {
            return jdbcTemplate.save(user);
        } catch (SQLException e) {
            // Translate technical exception to domain exception
            throw new DatabaseException("save_user", e);
        } catch (DataIntegrityViolationException e) {
            // Translate to business exception
            throw new BusinessRuleException("unique_email", 
                                           "Email already exists: " + user.getEmail());
        }
    }
}

// ✅ Service Layer  
@Service
public class UserService {
    
    public void createUser(CreateUserRequest request) {
        try {
            User user = userMapper.fromRequest(request);
            userRepository.save(user);
        } catch (DatabaseException e) {
            // Translate infrastructure exception to application exception
            throw new UserCreationFailedException("Database error: " + e.getMessage());
        } catch (BusinessRuleException e) {
            // Business exceptions bubble up unchanged
            throw e;
        }
    }
}
````
## 📊 HIERARCHY VISUALIZATION:
````markdown
ApplicationException (Base)
├── DomainException
│   ├── BusinessRuleException
│   ├── InvalidEmailException
│   └── InsufficientFundsException
├── ApplicationLayerException  
│   ├── UserCreationFailedException
│   ├── OrderProcessingException
│   └── ValidationException
└── InfrastructureException
    ├── DatabaseException
    ├── ExternalServiceException
    │   └── PaymentGatewayException
    └── NetworkException
        └── NetworkTimeoutException (RetryableException)
````
## 🎯 Exception Handling Strategy:
### Layer-Specific Handling:
````java
// ✅ Controller Layer - Convert to HTTP responses
@RestController
public class OrderController {
    
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorResponse> handleDomainException(DomainException e) {
        return ResponseEntity.badRequest()
            .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }
    
    @ExceptionHandler(ApplicationLayerException.class) 
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationLayerException e) {
        return ResponseEntity.unprocessableEntity()
            .body(new ErrorResponse(e.getErrorCode(), e.getMessage()));
    }
    
    @ExceptionHandler(InfrastructureException.class)
    public ResponseEntity<ErrorResponse> handleInfrastructureException(InfrastructureException e) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
            .body(new ErrorResponse(e.getErrorCode(), "Service temporarily unavailable"));
    }
}
````