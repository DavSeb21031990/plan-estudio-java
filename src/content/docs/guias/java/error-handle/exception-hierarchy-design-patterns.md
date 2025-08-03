---
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