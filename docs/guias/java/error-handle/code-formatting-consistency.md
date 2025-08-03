# Code Formatting Consistency
## 🎯 Qué significa:
Mantener el MISMO estilo de código en TODO el proyecto - indentación, espacios, naming, orden, etc.

### ❌ PROBLEMA - Código inconsistente:
```java
// ❌ Diferentes estilos en el mismo proyecto
public class UserService{
    private String name;
    
    public void processOrder(String email,double amount) {
        if(email==null){
            throw new IllegalArgumentException("Email required");
        }
        
        for(int i=0;i<10;i++){
            System.out.println( "Processing..." );
        }
    }
}

public class OrderService {
    private String name ;
    
    public void processPayment( String email , double amount )
    {
        if ( email == null )
        {
            throw new IllegalArgumentException( "Email required" ) ;
        }
        
        for ( int i = 0 ; i < 10 ; i++ )
        {
            System.out.println("Processing...");
        }
    }
}
```
### ✅ SOLUCIÓN - Código consistente:
```java
// ✅ Mismo estilo en todo el proyecto
public class UserService {
    private String name;
    
    public void processOrder(String email, double amount) {
        if (email == null) {
            throw new IllegalArgumentException("Email required");
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.println("Processing...");
        }
    }
}

public class OrderService {
    private String name;
    
    public void processPayment(String email, double amount) {
        if (email == null) {
            throw new IllegalArgumentException("Email required");
        }
        
        for (int i = 0; i < 10; i++) {
            System.out.println("Processing...");
        }
    }
}
```
## 🛠️ ASPECTOS DE CONSISTENCY:
### Indentación
```java
// ✅ Consistente - 4 espacios
public class Example {
    private String name;
    
    public void method() {
        if (condition) {
            doSomething();
        }
    }
}
```

### Espacios alrededor de operadores
```java
// ✅ Consistente
int result = a + b;
if (x == y) { }
for (int i = 0; i < 10; i++) { }
```

### Llaves (Braces)
```java
// ✅ Consistente - same line
public void method() {
    if (condition) {
        doSomething();
    } else {
        doOtherThing();
    }
}
```

### Orden de imports
```java
// ✅ Consistente - orden alfabético
import java.util.ArrayList;
import java.util.List;

import org.springframework.service.Service;

import com.company.domain.User;
```

### Naming conventions
```java
// ✅ Consistente
public class UserService {           // PascalCase para classes
    private String userName;         // camelCase para variables
    private static final int MAX_RETRY = 3; // UPPER_CASE para constantes
    
    public void processOrder() { }   // camelCase para métodos
}
```

## 🔧 Herramientas para automatiar:
### Google Java Style Guide
- IntelliJ
    - Descargue el archivo xml: [Archivo Xml](https://google.github.io/styleguide/intellij-java-google-style.xml)
    - Ingresar a `Settings`
    - Seleccione `Editor`
    - Seleccione `Code Style`
    - Seleccione Java
    - Importe el archivo xml
    - Aplique los cambios

---

⬅️ [Inicio](../../../../README.md)