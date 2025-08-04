---
title: 'Error Handling Coverage',
description: 'Guia para cobertura en el manejo de errores'
---

Es una métrica que mide qué tan bien están cubiertas las rutas de manejo de errores en tu código por las pruebas. Se enfoca específicamente en verificar que los casos de error, excepciones y condiciones inesperadas estén siendo probados adecuadamente.

## Tipos de Coverage para Manejo de Errores
### Exception Coverage
Verifica que todas las excepciones posibles sean lanzadas y manejadas
Incluye try-catch blocks, throws, y error callbacks

### Error Path Coverage
Asegura que todos los caminos de código que manejan errores sean ejecutados
Incluye validaciones, checks de null/undefined, y condiciones de fallo

### Recovery Coverage
Prueba que los mecanismos de recuperación funcionen correctamente
Incluye fallbacks, reintentos, y estados de recuperación

## Caso de Prueba
### Configuración Gradle
`````groovy
plugins {
    id 'java'
    id 'jacoco'
    id 'jacoco-report-aggregation'
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

repositories {
    mavenCentral()
}

dependencies {
    // JUnit 5
    testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
    testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
    
    // Mockito
    testImplementation 'org.mockito:mockito-core:4.11.0'
    testImplementation 'org.mockito:mockito-junit-jupiter:4.11.0'
    
    // AssertJ para mejores assertions
    testImplementation 'org.assertj:assertj-core:3.24.2'
    
    // Para tests parametrizados
    testImplementation 'org.junit.jupiter:junit-jupiter-params:5.9.2'
}

// Configuración de JUnit 5
test {
    useJUnitPlatform()
    
    // Configuración adicional para tests
    testLogging {
        events "passed", "skipped", "failed"
        exceptionFormat "full"
    }
    
    // Finalizar con reporte de JaCoCo
    finalizedBy jacocoTestReport
}

// Configuración de JaCoCo
jacoco {
    toolVersion = "0.8.8"
}

jacocoTestReport {
    dependsOn test
    
    reports {
        xml.required = true
        html.required = true
        csv.required = false
    }
    
    // Excluir clases específicas
    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.collect {
            fileTree(dir: it, exclude: [
                '**/dto/**',
                '**/config/**',
                '**/Main.class',
                '**/*Exception.class'
            ])
        }))
    }
    
    finalizedBy jacocoTestCoverageVerification
}

// Verificación de umbrales de coverage
jacocoTestCoverageVerification {
    dependsOn jacocoTestReport
    
    violationRules {
        // Regla general para todo el proyecto
        rule {
            limit {
                counter = 'LINE'
                value = 'COVEREDRATIO'
                minimum = 0.80
            }
            limit {
                counter = 'BRANCH'
                value = 'COVEREDRATIO'
                minimum = 0.75
            }
        }
        
        // Regla específica para clases de servicio
        rule {
            element = 'CLASS'
            includes = ['**/service/*Service']
            
            limit {
                counter = 'LINE'
                value = 'COVEREDRATIO'
                minimum = 0.90
            }
            limit {
                counter = 'BRANCH'
                value = 'COVEREDRATIO'
                minimum = 0.85
            }
        }
        
        // Regla específica para manejo de errores
        rule {
            element = 'CLASS'
            includes = ['**/service/**', '**/repository/**']
            
            limit {
                counter = 'BRANCH'
                value = 'COVEREDRATIO'
                minimum = 0.80
            }
        }
    }
}

// Task personalizada para generar reporte detallado
task coverageReport(type: JacocoReport) {
    dependsOn test
    
    sourceSets sourceSets.main
    executionData fileTree(project.rootDir.absolutePath).include("**/build/jacoco/*.exec")
    
    reports {
        xml.required = true
        html.required = true
        html.outputLocation = file("${buildDir}/reports/jacoco/html")
    }
}

// Task para verificar coverage solo de error handling
task errorHandlingCoverage {
    dependsOn test, jacocoTestReport
    
    doLast {
        def reportFile = file("${buildDir}/reports/jacoco/test/html/index.html")
        if (reportFile.exists()) {
            println "Error handling coverage report generated: ${reportFile.absolutePath}"
            
            // Aquí podrías agregar lógica personalizada para analizar 
            // específicamente el coverage de manejo de errores
        }
    }
}

// Task para ejecutar solo tests de error handling
task testErrorHandling(type: Test) {
    useJUnitPlatform {
        includeTags 'error-handling'
    }
    
    testLogging {
        events "passed", "skipped", "failed"
        showStandardStreams = true
    }
}
`````

### Configuración Multi-módulo
````groovy title="settings.gradle"
rootProject.name = 'error-handling-coverage'

include 'core'
include 'service'
include 'repository'

// Para proyectos multi-módulo
gradle.projectsEvaluated {
    rootProject.subprojects { subproject ->
        subproject.apply plugin: 'jacoco'
        
        subproject.jacocoTestReport {
            reports {
                xml.required = true
                html.required = true
            }
        }
    }
}
````

````groovy title="Build Script para Módulos (subprojects/build.gradle)"
// Configuración común para todos los submódulos
subprojects {
    apply plugin: 'java'
    apply plugin: 'jacoco'
    
    dependencies {
        testImplementation 'org.junit.jupiter:junit-jupiter:5.9.2'
        testImplementation 'org.mockito:mockito-core:4.11.0'
        testImplementation 'org.assertj:assertj-core:3.24.2'
    }
    
    test {
        useJUnitPlatform()
        finalizedBy jacocoTestReport
    }
    
    jacocoTestReport {
        reports {
            xml.required = true
            html.required = true
        }
    }
}

// Agregación de reportes de coverage
reporting {
    reports {
        testCodeCoverageReport(JacocoCoverageReport) {
            testType = TestSuiteType.UNIT_TEST
        }
    }
}
````

### Test Unitarios
````java
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@ExtendWith(MockitoExtension.class)
@Tag("error-handling")
class UserServiceErrorHandlingTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private EmailValidator emailValidator;
    
    private UserService userService;
    
    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository, emailValidator);
    }
    
    @Test
    @Tag("validation-error")
    void findUserById_WithNullId_ShouldThrowInvalidDataException() {
        assertThatThrownBy(() -> userService.findUserById(null))
            .isInstanceOf(InvalidDataException.class)
            .hasMessage("User ID cannot be null or empty");
    }
    
    @Test
    @Tag("business-error")
    void findUserById_WhenUserNotExists_ShouldThrowUserNotFoundException() throws SQLException {
        when(userRepository.findById("user123")).thenReturn(null);
        
        assertThatThrownBy(() -> userService.findUserById("user123"))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User not found: user123");
    }
    
    @Test
    @Tag("technical-error")
    void findUserById_WhenDatabaseError_ShouldThrowDataAccessException() throws SQLException {
        when(userRepository.findById("user123"))
            .thenThrow(new SQLException("Connection timeout"));
        
        assertThatThrownBy(() -> userService.findUserById("user123"))
            .isInstanceOf(DataAccessException.class)
            .hasMessage("Failed to retrieve user: user123")
            .hasCauseInstanceOf(SQLException.class);
    }
}
````

### Comandos de Gradle para Error Handling Coverage
````shell
# Ejecutar todos los tests con coverage
./gradlew clean test jacocoTestReport

# Verificar umbrales de coverage  
./gradlew jacocoTestCoverageVerification

# Ejecutar análisis completo
./gradlew clean test jacocoTestReport errorHandlingAnalysis

# Ver reporte en navegador (Windows)
./gradlew jacocoTestReport && start build/reports/jacoco/test/html/index.html
````