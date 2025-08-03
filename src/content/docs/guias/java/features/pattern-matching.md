---
title: Pattern Matching
description: Guias para Pattern Matching
---

Es una característica que permite examinar o obtener los datos de objetos de manera mas expresiva y segura. Elimina el código repetitivo y hace el código más legible.
**Disponible desde:**
- `ìnstanceOf pattern` : Java 16+
- `switch pattern`: Java 17+
- `reccord pattern`: Java 21+
## Ejemplos con `instanceOf`

- Casting Manual
```java title="❌ Forma Antigua"
String resultado = "";
if (valor instanceof String) {
    resultado = "Es texto: " + valor;
} else if (valor instanceof Integer) {
    resultado = "Es número: " + valor;
} else {
    resultado = "Desconocido";
}
```

- Declaración y casting automático
- No hay riesgo de `ClassCastException`
```java title="✅ Forma Nueva"
if (obj instanceof String str) {
	System.out.println("Longitud: " + str.length());
}
```
## Ejemplos con `switch`

- Casting Manual
```java title="❌ Forma Antigua"
String resultado = ""; 
switch (valor.getClass().getSimpleName()) { 
	case "String": resultado = "Es texto: " + valor;
		break; 
	case "Integer": resultado = "Es número: " + valor;
		break; 
	default: resultado = "Desconocido"; 
}
```

- Declaración y casting automático
- No hay riesgo de `ClassCastException`
```java title="✅ Forma Nueva"
String resultado = switch (valor) { 
	case String str -> "Es texto: " + str; 
	case Integer num -> "Es número: " + num; 
	case Double decimal -> "Es decimal: " + decimal; 
	case null -> "Valor nulo"; 
	default -> "Desconocido"; 
};
```
## Ejemplos con `switch` y `when`
- Evalúa si `valor` es de tipo `String` y además si cumple una condición usando `when`
- `String str`: Evalúa si valor es un `String`
- `when str.length() > 10`: Evalúa una condición
- Los casos más **específicos** deben ir **antes** que los generales:
    - `case String str when str.length() > 10 -> "Muy largo"; // Específico`
    - `primero case String str -> "Normal"; // General después`
```java
String resultado = switch (valor) {
    case String str when str.length() > 10 -> "String muy largo";
    case String str when str.length() > 5 -> "String largo";
    case String str -> "String corto";
    default -> "No es string";
};

```
## Ejemplo con `switch` y desestructuración de Objetos con `records`
- `case ApiReponse(true, Usuario(var id, var nombre, var email, true), var msg, var code)`:
    - Evalúa si `respuesta` es de tipo `ApiResponse` y desestructura el objeto.
    - Evalúa si `ApiResponse` es exitosa con `true`
    - Desestructura el objeto `Usuario` que es parte del objeto `ApiResponse` y también verifica si Usuario esta activo con `true`.
- `yield`: Se usa en `switch` para retornar valores cuando el case usa **bloques `{}`**
  o tiene **múltiples statements**. Para cases de una sola línea con `->` se omite.
- `Pattern matching` es especialmente poderoso con `sealed` classes porque el compilador **garantiza exhaustividad** (todos los casos cubiertos).
```java
public String procesarRespuestaAPI(ApiResponse<?> respuesta) { 
	return switch (respuesta) { 
		case ApiResponse(
				true, 
				Usuario(var id, var nombre, var email, true), 
				var msg, 
				var code
			) -> 
			String.format("Usuario activo: %s (%s) - ID: %d", 
						nombre, email, id); 
		case ApiResponse(
				true, 
				Usuario(var id, var nombre, var email, false), 
				var msg, 
				var code
			) -> 
			String.format("Usuario inactivo: %s - ID: %d", 
						nombre, id); 
		case ApiResponse(
				true, 
				List<?> lista, 
				var msg, 
				var code
			) when !lista.isEmpty() -> 
			String.format("Lista recibida con %d elementos",   lista.size()); 
		case ApiResponse(
				false, 
				Error(var tipo, var desc, var fecha), 
				var msg, 
				var code
			) -> 
			String.format("Error %s: %s (Fecha: %s)", tipo, desc, fecha); 
		case ApiResponse(
				false, 
				var data, 
				var msg, 
				var code
			) when code >= 400 -> {
				yield String.format("Error HTTP %d: %s", code, msg);
			} 
		default -> "Respuesta no procesable"; 
	}; 
}
```