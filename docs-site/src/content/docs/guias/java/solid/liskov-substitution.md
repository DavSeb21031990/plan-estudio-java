---
title: Liskov Substitution ‐ (LSP)
description: Explicación y ejemplos de Liskov Substitution ‐ (LSP)
---

Los objetos de superclases deben poder ser reemplazados por objetos de sus subclases.
```java title="❌ Violación Común"
// ❌ Rectangle y Square violan LSP
public class Rectangle {
    protected int width, height;
    
    public void setWidth(int width) { this. Width = width; }
    public void setHeight(int height) { this.height = height; }
    public int getArea() { return width * height; }
}

public class Square extends Rectangle {
    @Override
    public void setWidth(int width) {
        this.width = width;
        this.height = width; // ❌ Comportamiento inesperado!
    }
    
    @Override
    public void setHeight(int height) {
        this.width = height;  // ❌ Cambia width también!
        this.height = height;
    }
}

// ❌ Test que falla:
public void testLSP(Rectangle rect) {
    rect.setWidth(5);
    rect.setHeight(4);
    assertEquals(20, rect.getArea()); // Falla con Square!
}
```

```java title="✅ Uso Correcto"
// ✅ Usar interface común
public interface Shape {
    int getArea();
}

public class Rectangle implements Shape {
    private final int width, height;
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    public int getArea() { return width * height; }
}

public class Square implements Shape {
    private final int side;
    
    public Square(int side) { this.side = side; }
    
    public int getArea() { return side * side; }
}
```
## Características de violación de LSP
- Subclase lanza excepciones que parent no lanza
- Subclase tiene precondiciones más fuertes
- Subclase tiene postcondiciones más débiles
- `instanceof` checks en el código