---
title: Logging Handler
description: Guia de Logs
---

## Definición
Los logs son registros de eventos que ocurren en tu aplicación. Son como un "diario" que te permite:

- Rastrear qué está pasando en tu sistema
- Debuggear problemas
- Monitorear el comportamiento
- Cumplir auditorías

## Niveles de logging
````java
// Configuración típica en logback.xml o application.properties
logger.trace("Información muy detallada para debugging");
logger.debug("Información útil para desarrolladores");
logger.info("Información general del flujo de la aplicación");
logger.warn("Algo inusual pero no crítico");
logger.error("Error que requiere atención");
````
**Orden de severidad:** `TRACE` < `DEBUG` < `INFO` < `WARN` < `ERROR`
## Cuándo usar cada nivel

### TRACE/DEBUG
- Cuando usar:
  - Validaciones paso a paso
  - Estados internos
  - Llamadas a servicios externos
  - Parámetros de entrada y salida
  - SQL queries o operaciones de base de datos
````java
public User findUser(Long id) {
    logger.debug("Buscando usuario con ID: {}", id);
    User user = userRepository.findById(id);
    logger.debug("Usuario encontrado: {}", user != null ? user.getEmail() : "null");
    return user;
}
````

### INFO
- Cuando usar:
  - Inicio y fin de operaciones importantes
  - Cambios de estado importantes
  - Operaciones de negocio clave
  - Configuración y startup de aplicación
  - Métricas de negocio importantes
  - Integraciones externas exitosas
- Cuando no usar:
  - Detalles internos o debugging
  - Operaciones muy frecuentes
````java
@PostMapping("/orders")
public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
    logger.info("Creando nueva orden para usuario: {}", request.getUserEmail());
    Order order = orderService.processOrder(request);
    logger.info("Orden creada exitosamente con ID: {}", order.getId());
    return ResponseEntity.ok(order);
}
````

### WARN
- Cuando usar:
  - Situaciones sospechosas pero válidas
  - Reintentos
  - Configuración subóptima
  - Datos faltantes o incompletos
  - Fallbacks o comportamientos degradados
  - Límites alcanzados
````java
public void processPayment(Payment payment) {
    if (payment.getAmount() > MAX_AMOUNT) {
        logger.warn("Pago por monto alto detectado: {} para usuario: {}", 
                   payment.getAmount(), payment.getUserId());
    }
    
    if (isWeekend()) {
        logger.warn("Procesando pago en fin de semana, puede tener retrasos");
    }
}
````

### ERROR
- Cuando usar:
  - Error externo crítico
  - Error de infraestructura
  - Error inesperado
  - Problemas de conectividad
  - Problemas de configuración
  - Inconsistencias de datos críticas
````java
public void sendEmail(String email, String message) {
    try {
        emailService.send(email, message);
    } catch (EmailException e) {
        logger.error("Error enviando email a: {}, mensaje: {}", email, message, e);
        throw new NotificationException("No se pudo enviar email", e);
    }
}
````

## Logging con manejo de excepciones

### Log y Re-throw (capa inferior)
````java
@Service
public class PaymentService {
    
    public void processPayment(PaymentRequest request) {
        try {
            // lógica de pago
            paymentGateway.charge(request);
            logger.info("Pago procesado exitosamente para usuario: {}", request.getUserId());
            
        } catch (PaymentGatewayException e) {
            // Log con contexto y re-throw
            logger.error("Error en gateway de pago para usuario: {}, monto: {}", 
                        request.getUserId(), request.getAmount(), e);
            throw new PaymentProcessingException("Error procesando pago", e);
        }
    }
}
````

### Log y Handle (capa superior)
````java
@RestController
public class OrderController {
    
    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest request) {
        try {
            Order order = orderService.processOrder(request);
            return ResponseEntity.ok(order);
            
        } catch (PaymentProcessingException e) {
            // Log final y respuesta al usuario
            logger.warn("Orden rechazada por fallo en pago para usuario: {}", 
                       request.getUserEmail());
            return ResponseEntity.badRequest()
                .body(new ErrorResponse("No se pudo procesar el pago"));
                
        } catch (Exception e) {
            // Error inesperado
            logger.error("Error inesperado procesando orden para usuario: {}", 
                        request.getUserEmail(), e);
            return ResponseEntity.status(500)
                .body(new ErrorResponse("Error interno del servidor"));
        }
    }
}
````

## Mejores prácticas

### Usa placeholders, no concatenación
````java
// ❌ Malo
logger.info("Usuario " + user.getName() + " realizó compra por " + amount);

// ✅ Bueno
logger.info("Usuario {} realizó compra por {}", user.getName(), amount);
````

### Incluye contexto relevante
````java
// ❌ Poco útil
logger.error("Error en base de datos", e);

// ✅ Útil
logger.error("Error ejecutando query para usuario ID: {}, tabla: users", userId, e);
````

### No loggees información sensible
````java
// ❌ Peligroso
logger.info("Usuario autenticado: {}, password: {}", username, password);

// ✅ Seguro  
logger.info("Usuario autenticado: {}", username);
````

### Estructura consistente
````java
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
    
    public Order processOrder(OrderRequest request) {
        String correlationId = UUID.randomUUID().toString();
        
        logger.info("[{}] Iniciando procesamiento de orden para usuario: {}", 
                   correlationId, request.getUserEmail());
        
        try {
            // validaciones
            validateOrder(request);
            logger.debug("[{}] Validaciones completadas", correlationId);
            
            // procesar pago
            processPayment(request);
            logger.debug("[{}] Pago procesado", correlationId);
            
            // crear orden
            Order order = createOrder(request);
            logger.info("[{}] Orden creada exitosamente con ID: {}", 
                       correlationId, order.getId());
            
            return order;
            
        } catch (ValidationException e) {
            logger.warn("[{}] Validación fallida para usuario: {}, error: {}", 
                       correlationId, request.getUserEmail(), e.getMessage());
            throw e;
            
        } catch (Exception e) {
            logger.error("[{}] Error inesperado procesando orden para usuario: {}", 
                        correlationId, request.getUserEmail(), e);
            throw new OrderProcessingException("Error procesando orden", e);
        }
    }
}
````

## Configuración
````properties
# Nivel raíz
logging.level.root=INFO

# Tu aplicación en DEBUG
logging.level.com.tuempresa.tuapp=DEBUG

# Librerías externas en WARN
logging.level.org.springframework=WARN
logging.level.org.hibernate=WARN

# Archivo de logs
logging.file.name=logs/application.log
logging.pattern.file=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
````