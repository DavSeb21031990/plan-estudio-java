# Single Responsability ‐ (SRP)
Una clase debe tener una sola razón para cambiar (una sola responsabilidad).
```java title="❌ Uso incorrecto"
// ❌ UserService hace MUCHAS cosas (viola SRP)
public class UserService {
    // Responsabilidad 1: Lógica de negocio
    public void createUser(String email) { ... }
    
    // Responsabilidad 2: Validación
    public boolean isValidEmail(String email) { ... }
    
    // Responsabilidad 3: Persistencia  
    public void saveToDatabase(User user) { ... }
    
    // Responsabilidad 4: Envío de emails
    public void sendWelcomeEmail(String email) { ... }
    
    // Responsabilidad 5: Logging
    public void logUserCreation(String email) { ... }
}
```

```java title="✅ Uso correcto"
// ✅ Cada clase tiene UNA responsabilidad
public class UserService {           // Solo lógica de negocio
public class EmailValidator {        // Solo validación
public class UserRepository {       // Solo persistencia  
public class EmailService {         // Solo envío emails
public class UserLogger {           // Solo logging
```
## Pregunta clave
**¿Por cuantas razones podría cambiar esta clase?**. Si son más de 1, esa clase se debe refactorizar.

---

⬅️ [Inicio](../../../../README.md)