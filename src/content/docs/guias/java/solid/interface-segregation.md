---
title: Interface Segregation ‐ (ISP)
description: Explicación y ejemplos de Interface Segregation ‐ (ISP)
---

Los clientes no deben depender de interfaces que no usan. Interfaces pequeñas y granulares son mejores que una interface grande y monolítica.
```java title="❌ Violación Común"
// ❌ Interface "fat" - muchas responsabilidades
public interface Worker {
    void work();
    void eat();
    void sleep();
    void code();
    void managePeople();
    void attendMeetings();
}

// ❌ Robot implementa métodos que no necesita
public class Robot implements Worker {
    public void work() { /* OK */ }
    public void eat() { throw new UnsupportedOperationException(); } // ❌ No come
    public void sleep() { throw new UnsupportedOperationException(); } // ❌ No duerme
    public void code() { /* OK */ }
    public void managePeople() { throw new UnsupportedOperationException(); } // ❌ No maneja gente
    public void attendMeetings() { throw new UnsupportedOperationException(); } // ❌ No va a meetings
}
```
## Mejores prácticas
- **Interfaces pequeñas** (1-3 métodos máx.).
- **Cohesión alta** (métodos relacionados).
- **Compuse** múltiples interfaces si necesario.
- **Role-based** interfaces (por responsabilidad).
- Cada clase implementa solo lo que realmente usa.
- Clientes dependen solo de métodos que necesitan.