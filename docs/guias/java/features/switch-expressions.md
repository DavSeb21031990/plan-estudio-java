# Switch Expressions
Es una característica de java que transforma los `switch` tradicionales de `statement`(ejecuta código) a una `expression` (retorna valor) haciéndolos más expresivos y seguros.
## Comparaciones
- `break`: Es obligatorio.
```java title="❌ Switch Tradicional"
String resultado = ""; 
switch (dia) { 
	case "LUNES": resultado = "Día laboral"; 
		break; // ¡Obligatorio! 
	case "SABADO": resultado = "Fin de semana"; 
		break; 
	default: resultado = "Desconocido"; 
}
```

- `break`: Se omite.
- `case`: Puede manejar varios valores.
- `yield`: Se usa para retornar valores en bloques multi línea o muy complejos.
- No necesita `default` cuando se evalúa un `Enum`, por que el compilador te obliga a cubrir todos los casos.
- Si no evalúas un `Enum` es obligatorio el uso de `default`.
- Cuando ingresa en un `case`, luego de ejecutar su bloque la ejecución del `switch` termina automáticamente, no continua evaluando el resto de `case`.
```java title="✅Switch Expression"
String resultado = switch (dia) { 
	case "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES" -> 
		"Día laboral"; 
	case "SABADO", "DOMINGO" -> 
		"Fin de semana"; 
	default -> "Desconocido"; 
};
```
### Uso de `yield`

```java title="Uso de yield"
String resultado = switch (operacion) { 
	case "SUMA" -> a + b;
	case "DIVISION" -> { 
		if (b == 0) throw new ArithmeticException("División por cero");
		yield a / b; 
	} 
	default -> 0.0; 
};
```
## Ventajas
- **Más seguro:** No hay `Fall-Through` accidental.
- **Más conciso:** Menos código repetitivo.
- **Más Expresivo:** Lee como lenguaje natural.
- **Exhaustivo:** El compilador garantiza todos los casos.
- **Funcional:** Se puede usar en expresiones complejas.
## Cuando usar
- **Asignación de valores** basada en condiciones.
- **Transformación de datos** (mapeo, conversión).
- **Procesamiento de `enums`** y tipos conocidos.
- **Reemplazo de cadenas `if-else`** complejas.
## Cuando evitar
- Solo necesitas ejecutar código sin retornar valores.
- La lógica es extremadamente compleja.
- Requieres muchos efectos secundarios (logging, modificar estado).
## Disponible desde:
- **Estándar: Java 14+
- Con `Pattern Matching`: Java 17+

---

⬅️ [Inicio](../../../../README.md)