---
title: 'Checked vs Unchecked exceptions strategy'
description: 'Guia para la estrategia - Checked vs Unchecked exceptions strategy'
---

## Conceptos Fundamentales
### Checked Exceptions
- Deben ser declaradas o manejadas obligatoriamente
- **DEBE** declarar la excepción en el método
````java
// Deben ser declaradas o manejadas obligatoriamente
public class UserService {
    
    // DEBE declarar la excepción en el método
    public User findUser(String id) throws UserNotFoundException, DataAccessException {
        if (id == null) {
            throw new UserNotFoundException("User ID cannot be null");
        }
        
        try {
            return userRepository.findById(id);
        } catch (SQLException e) {
            // Conversión de checked a checked
            throw new DataAccessException("Database error", e);
        }
    }
}
````

````java title="Excepciones"
// Excepciones checked personalizadas
public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class DataAccessException extends Exception {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
````
### Unchecked Exceptions
- No requieren declaración obligatoria
- **NO** necesita declarar RuntimeException
````java
// No requieren declaración obligatoria
public class UserService {
    
    // NO necesita declarar RuntimeException
    public User findUser(String id) {
        if (id == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        
        if (id.trim().isEmpty()) {
            throw new UserNotFoundException("User not found: " + id);
        }
        
        return userRepository.findById(id);
    }
}
````

````java title="Excepciones"
// Excepciones unchecked personalizadas
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}

public class InvalidUserDataException extends RuntimeException {
    public InvalidUserDataException(String message) {
        super(message);
    }
}
````

## Comparación Detallada
| Aspecto         | Checked Exceptions         | Unchecked Exceptions         |
|------------------|-----------------------------|-------------------------------|
| **Herencia**     | Extienden `Exception`       | Extienden `RuntimeException` |
| **Compilación** | Error si no se manejan       | Compila sin manejo           |
| **Declaración**  | Obligatoria en método        | Opcional                     |
| **Manejo**       | `try/catch` obligatorio     | `try/catch` opcional         |
| **Performance**  | Overhead mínimo            | Overhead mínimo              |
| **API Design**   | Hace explícito el contrato | API más limpia               |

## Estrategias de Implementación
### Estrategia 1: Solo Checked Exceptions
````java title="Servicio"
// Enfoque tradicional - Todo checked
public class OrderService {
    
    public Order createOrder(OrderRequest request) 
            throws ValidationException, PaymentException, InventoryException {
        
        validateOrder(request); // throws ValidationException
        
        try {
            processPayment(request.getPayment()); // throws PaymentException
            reserveInventory(request.getItems()); // throws InventoryException
            
            return saveOrder(request);
        } catch (PaymentException | InventoryException e) {
            // Manejo específico y re-throw
            logError("Order creation failed", e);
            throw e;
        }
    }
    
    private void validateOrder(OrderRequest request) throws ValidationException {
        if (request == null) {
            throw new ValidationException("Order request cannot be null");
        }
        if (request.getItems().isEmpty()) {
            throw new ValidationException("Order must contain items");
        }
    }
}

````

```java title="Controller"
// Uso - Manejo obligatorio
public class OrderController {
    
    public ResponseEntity<Order> createOrder(OrderRequest request) {
        try {
            Order order = orderService.createOrder(request);
            return ResponseEntity.ok(order);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (PaymentException e) {
            return ResponseEntity.status(402).body(null);
        } catch (InventoryException e) {
            return ResponseEntity.status(409).body(null);
        }
    }
}
```

### Estrategia 2: Solo Unchecked Exceptions
````java title="Servicio"
// Enfoque moderno - Todo unchecked
public class OrderService {
    
    // API limpia sin throws
    public Order createOrder(OrderRequest request) {
        validateOrder(request);
        
        try {
            processPayment(request.getPayment());
            reserveInventory(request.getItems());
            
            return saveOrder(request);
        } catch (PaymentException | InventoryException e) {
            logError("Order creation failed", e);
            throw e; // Re-throw unchecked
        }
    }
    
    private void validateOrder(OrderRequest request) {
        if (request == null) {
            throw new ValidationException("Order request cannot be null");
        }
        if (request.getItems().isEmpty()) {
            throw new ValidationException("Order must contain items");
        }
    }
}
````

````java title="Excepciones"
// Excepciones unchecked
public class ValidationException extends RuntimeException {
    public ValidationException(String message) { super(message); }
}

public class PaymentException extends RuntimeException {
    public PaymentException(String message, Throwable cause) { 
        super(message, cause); 
    }
}
````

````java title="Controller"
// Uso - Manejo opcional pero recomendado
public class OrderController {
    
    public ResponseEntity<Order> createOrder(OrderRequest request) {
        try {
            Order order = orderService.createOrder(request);
            return ResponseEntity.ok(order);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (PaymentException e) {
            return ResponseEntity.status(402).body(null);
        } catch (InventoryException e) {
            return ResponseEntity.status(409).body(null);
        }
        // Manejo opcional - podría omitirse
    }
}
````

### Estrategia 3: Híbrida (Recomendada)
- Checked
  - Errores recuperables/esperados
  - Errores de formato esperado
  - Errores de negocio esperados
- Unchecked
  - Error de programación
````java title="Servicio"
// Combina ambos enfoques según el contexto
public class FileProcessorService {
    
    // Checked para errores recuperables/esperados
    public ProcessResult processFile(String filePath) 
            throws FileNotFoundException, InvalidFormatException {
        
        validateInput(filePath); // unchecked - error de programación
        
        try {
            File file = loadFile(filePath); // checked - error esperado
            return parseFile(file); // checked - error de formato esperado
        } catch (IOException e) {
            throw new FileNotFoundException("File not accessible: " + filePath, e);
        }
    }
    
    // Unchecked para errores de programación
    private void validateInput(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }
    }
    
    // Checked para errores de negocio esperados
    private ProcessResult parseFile(File file) throws InvalidFormatException {
        try {
            return parser.parse(file);
        } catch (ParseException e) {
            throw new InvalidFormatException("Invalid file format", e);
        }
    }
}
````

````java title="Excepciones"
// Jerarquía de excepciones híbrida
public abstract class BusinessException extends Exception {
    protected BusinessException(String message) { super(message); }
    protected BusinessException(String message, Throwable cause) { 
        super(message, cause); 
    }
}

public abstract class SystemException extends RuntimeException {
    protected SystemException(String message) { super(message); }
    protected SystemException(String message, Throwable cause) { 
        super(message, cause); 
    }
}

// Excepciones específicas
public class InvalidFormatException extends BusinessException {
    public InvalidFormatException(String message, Throwable cause) {
        super(message, cause);
    }
}

public class ConfigurationException extends SystemException {
    public ConfigurationException(String message) {
        super(message);
    }
}
````
## Criterios de Decisión
### Usar Checked Exceptions cuando
- Errores recuperables por el caller
- Parte fundamental del contrato de la API
- Errores de recursos externos
````java
// 1. Errores recuperables por el caller
public class EmailService {
    public void sendEmail(Email email) throws EmailDeliveryException {
        // El caller puede reintentar o usar otro proveedor
    }
}

// 2. Parte fundamental del contrato de la API
public class PaymentGateway {
    public PaymentResult processPayment(Payment payment) 
            throws InsufficientFundsException, CardExpiredException {
        // Errores esperados que el caller debe manejar
    }
}

// 3. Errores de recursos externos
public class DatabaseService {
    public List<User> getUsers() throws SQLException {
        // Error de conectividad - caller puede decidir qué hacer
    }
}
````

### Usar Unchecked Exceptions cuando
- Errores de programación
- Errores que raramente se pueden recuperar
- Errores del sistema
````java
// 1. Errores de programación
public class Calculator {
    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Division by zero");
        }
        return a / b;
    }
}

// 2. Errores que raramente se pueden recuperar
public class ConfigurationLoader {
    public Configuration load() {
        try {
            return loadFromFile();
        } catch (IOException e) {
            throw new ConfigurationException("Failed to load config", e);
        }
    }
}

// 3. Errores del sistema
public class CacheService {
    public void put(String key, Object value) {
        if (isMemoryFull()) {
            throw new OutOfMemoryError("Cache memory exhausted");
        }
    }
}
````

## Patrones de Conversión
### Wrapping de Checked a Unchecked
````java title="Repositorio"
public class RepositoryImpl {
    
    // Convierte checked SQLException a unchecked
    public User findById(String id) {
        try {
            return jdbcTemplate.queryForObject(sql, mapper, id);
        } catch (SQLException e) {
            throw new DataAccessException("Database error", e);
        }
    }
}
````

````java title="Excepciones"
// Excepción wrapper unchecked
public class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause);
    }
    
    // Método helper para obtener la causa original
    public SQLException getSQLException() {
        return (SQLException) getCause();
    }
}
````

### Wrapping de Unchecked a Checked
````java
public class ValidationService {
    
    // Convierte unchecked a checked para hacer explícito el contrato
    public void validateUser(User user) throws ValidationException {
        try {
            validator.validate(user); // Puede lanzar IllegalArgumentException
        } catch (IllegalArgumentException e) {
            throw new ValidationException("User validation failed", e);
        }
    }
}
````

## Testing de Estrategias
### Testing Checked Exceptions
````java
@Test
void processFile_WhenFileNotFound_ShouldThrowFileNotFoundException() {
    String invalidPath = "nonexistent.txt";
    
    assertThatThrownBy(() -> fileProcessor.processFile(invalidPath))
        .isInstanceOf(FileNotFoundException.class)
        .hasMessage("File not accessible: nonexistent.txt")
        .hasCauseInstanceOf(IOException.class);
}

@Test  
void processFile_WithValidFile_ShouldReturnResult() throws Exception {
    // Test del happy path - manejo obligatorio de excepciones
    ProcessResult result = fileProcessor.processFile("valid.txt");
    assertThat(result).isNotNull();
}
````

### Testing Unchecked Exceptions
````java
@Test
void divide_ByZero_ShouldThrowIllegalArgumentException() {
    assertThatThrownBy(() -> calculator.divide(10, 0))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Division by zero");
}

@Test
void divide_WithValidNumbers_ShouldReturnResult() {
    // No manejo obligatorio de excepciones
    double result = calculator.divide(10, 2);
    assertThat(result).isEqualTo(5.0);
}
````

## Mejores Prácticas
### DO - Buenas Prácticas
````java
// 1. Usar checked para errores recuperables
public class OrderService {
    public Order createOrder(OrderData data) throws OrderValidationException {
        // Caller puede mostrar errores al usuario y pedir corrección
    }
}

// 2. Usar unchecked para errores de programación
public class StringUtils {
    public static String reverse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }
        return new StringBuilder(input).reverse().toString();
    }
}

// 3. Documentar excepciones unchecked importantes
/**
 * Processes user data
 * @param userData the user data to process
 * @return processed user
 * @throws IllegalArgumentException if userData is null or invalid
 * @throws ConfigurationException if system is not properly configured
 */
public User processUser(UserData userData) {
    // Implementation
}

// 4. Crear jerarquías de excepciones coherentes
public abstract class OrderException extends Exception {
    protected OrderException(String message) { super(message); }
}

public class OrderValidationException extends OrderException {
    public OrderValidationException(String message) { super(message); }
}

public class OrderProcessingException extends OrderException {
    public OrderProcessingException(String message, Throwable cause) { 
        super(message, cause); 
    }
}
````

### DON'T - Malas Prácticas
````java
// 1. NO usar checked exceptions para errores de programación
public class BadValidator {
    public void validate(String input) throws ValidationException {
        if (input == null) { // Error de programación
            throw new ValidationException("Input is null"); // MAL
        }
    }
}

// 2. NO ignorar excepciones silenciosamente
public class BadFileReader {
    public String readFile(String path) {
        try {
            return Files.readString(Paths.get(path));
        } catch (IOException e) {
            return ""; // MAL - pérdida de información
        }
    }
}

// 3. NO crear demasiadas checked exceptions
public class OverComplicatedService {
    public void process() throws ExceptionA, ExceptionB, ExceptionC, 
                               ExceptionD, ExceptionE { // MAL - demasiadas
    }
}

// 4. NO mezclar niveles de abstracción
public class BadController {
    public ResponseEntity<String> getData() {
        try {
            return ResponseEntity.ok(service.getData());
        } catch (SQLException e) { // MAL - detalles de implementación filtrados
            return ResponseEntity.status(500).build();
        }
    }
}
````

## Recomendaciones por Contexto
### APIs Públicas
- Usar checked exceptions para errores que el cliente debe manejar
- Documentar claramente todas las excepciones
- Mantener estabilidad en el contrato de excepciones

### Código Interno
- Preferir unchecked exceptions para mayor flexibilidad
- Usar checked solo cuando el manejo sea realmente necesario
- Crear jerarquías simples y coherentes

### Frameworks/Librerías
- Seguir las convenciones del ecosistema
- Spring: principalmente unchecked
- Jakarta EE: mix de checked/unchecked
- Crear wrappers apropiados para APIs externas