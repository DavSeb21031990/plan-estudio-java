# Clases `Record`
Las clases `record` son un tipo especial de clases, estas se declaran usando la siguiente sintaxis:
```java
record MiClase (private int a, private int b){}
```
Las clases `record` tiene las siguientes características:
- Las clases `record` son implícitamente de tipo final.
- Todos los componentes miembros de la clase se definen en la cabecera y son de tipo final.
- Los métodos `constructor`, `equals`, `hash`, `toString`, `getter` y `setter` se generan automáticamente.
- Este tipo de clases se pueden definir dentro de una clase o un método de instancia.
- Cuando se define una clase `record` dentro de una clase normal, esta son implícitamente de tipo `static`.
- No se pueden heredar, pero si pueden implementar interfaces.
- Se puede definir un constructor a parte del que se genera automáticamente.
  ```java
record MiClase (int a, int b){
public MiClase(int a, int b){
//
}
}
```
	También se puede obviar el volver a mencionar los miembros en el constructor, de la siguiente forma:
	```java
record MiClase (int a, int b){
   public MiClase {
	  //
   }
}
```

- Esta clase cuenta con dos métodos nativos:
    - `RecordComponents[] getRecordComponents()`: Es un método el cual devuelve un array con los miembros de la clase.
    - `boolean isRecord()`: Es un método que devuelve un booleano en caso la clase sea un `record`.
- Se puede declarar genéricos al definir una clase `record`.
- Se puede agregar anotaciones en la definición de los miembros dela clase.

---

⬅️ [Inicio](../../../../README.md)