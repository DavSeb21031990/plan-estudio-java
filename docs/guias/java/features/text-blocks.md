# TextBlock
Los `TextBlock` son una característica de Java para manejar cadenas de texto multi línea, de forma más legible y mantenible.
## Características
- Funcionalidad estándar desde Java 15.
- Se usa `"""` para usar Text Blocks.
- Se elimina automáticamente la indentación común.
## Ventajas
- **Legibilidad:** El código es mucho más fácil de leer y mantener.
- **Escape:** No se necesita escapar de comillas internas.
- **Interpolación:** Funciona perfectamente con `.formatted()`.
## Ejemplos
```java title="Consulta SQL"
String query = """
    SELECT u.nombre, COUNT(p.id) as pedidos
    FROM usuarios u 
    LEFT JOIN pedidos p ON u.id = p.usuario_id
    WHERE u.activo = true
    GROUP BY u.id, u.nombre
    """;
```

```java title="HTML - JSON"
String json = """
    {
        "usuario": "%s",
        "timestamp": "%s",
        "datos": %s
    }
    """.formatted(nombre, LocalDateTime.now(), datosJson);
```

```java title="Archivo de Configuración"
String dockerCompose = """
    version: '3.8'
    services:
      app:
        image: myapp:latest
        ports:
          - "%d:8080"
    """.formatted(puerto);
```
## Métodos útiles
- `lines()`: Se usa para trabajar con líneas individuales.
```java
textBlock.lines() 
.filter(line -> !line.isEmpty()) 
.map(line -> "// " + line) .collect(Collectors.joining("\n"));
```
- `indent(<value>)`: Agregar indentación.
```java 
textBlock.indent(4); 
```
- `stripIndent()`: Elimina indentación.
```java
textBlock.stripIndent();
```
## Mejores Prácticas
- Usa constantes para `queries` reutilizables.
- Valida parámetros antes de `formatted()`
- Mantén una estructura de indentación clara.
- Evita Text Block excesivamente largos.
- No concatenes directamente datos de usuario.

---

⬅️ [Inicio](../../../../README.md)