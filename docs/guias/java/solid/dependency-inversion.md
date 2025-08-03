# Dependency Inversion ‐ (DIP)
Depende de abstracciones (interfaces), no de implementaciones concretas.
- `Spring IoC` maneja las dependencias automáticamente.
```java title="❌ Uso Incorrecto"
// ❌ UserService depende directamente de implementación concreta
public class UserService {
    private UserRepository repository = new MySQLUserRepository(); // ❌ Hard dependency
    
    public void createUser(User user) {
        repository.save(user);
    }
}
```

```java title="✅ Uso correcto en Spring"
// ✅ Depende de abstracción
public interface UserRepository {
    void save(User user);
}

@Service
public class UserService {
    private final UserRepository repository; // ✅ Abstraction
    
    public UserService(UserRepository repository) { // ✅ Constructor injection
        this.repository = repository;
    }
}

@Repository 
public class MySQLUserRepository implements UserRepository {
    // Implementation details
}
```

---

⬅️ [Inicio](../../../../README.md)