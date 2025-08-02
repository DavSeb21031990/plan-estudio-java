package features;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRecordTest {

    @Test
    void shouldCreateRecord() {
        // Crear record
        String email = "david@correo.com";
        String name = "David";
        LocalDateTime createAt = LocalDateTime.now();
        UserRecord record1 = new UserRecord(email, name, createAt);
        // Verificar getters retornan valores correctos
        assertEquals(email, record1.email());
        assertEquals(name, record1.name());
        assertEquals(createAt, record1.createAt());
    }

    @Test
    void shouldImplementEqualsCorrectly() {
        // Crear 2 records con mismos valores
        String email = "david@correo.com";
        String name = "David";
        LocalDateTime createAt = LocalDateTime.now();
        UserRecord record1 = new UserRecord(email, name, createAt);
        UserRecord record2 = new UserRecord(email, name, createAt);
        // Verificar son equals()
        assertEquals(record1, record2);
        // Crear record con valores diferentes
        UserRecord record3 = new UserRecord("sebastian@correo.com", "Sebastian", LocalDateTime.now());
        // Verificar NO son equals()
        assertNotEquals(record1, record3);
    }

    @Test
    void shouldImplementHashCodeCorrectly() {
        // Records iguales deben tener mismo hashCode
        String email = "david@correo.com";
        String name = "David";
        LocalDateTime createAt = LocalDateTime.now();
        UserRecord record1 = new UserRecord(email, name, createAt);
        UserRecord record2 = new UserRecord(email, name, createAt);

        assertEquals(record1.hashCode(), record2.hashCode());
    }

    @Test
    void shouldHaveWorkingToString() {
        // Verificar toString() contiene los valores
        String email = "david@correo.com";
        String name = "David";
        LocalDateTime createAt = LocalDateTime.now();
        UserRecord record1 = new UserRecord(email, name, createAt);

        assertTrue(record1.toString().contains(email));
        assertTrue(record1.toString().contains(name));
        assertTrue(record1.toString().contains(createAt.toString()));
    }

}