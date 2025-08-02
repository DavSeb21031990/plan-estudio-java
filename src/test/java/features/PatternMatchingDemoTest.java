package features;

import com.development.attendance.service.features.PatternMatchingDemo;
import org.junit.jupiter.api.Test;

class PatternMatchingDemoTest {

    @Test
    void processObject() {

        String str = "Hello World!";
        Integer num = 123;
        Boolean bool = true;
        Double dbl = 123.456;

        assertEquals("String: " + str, PatternMatchingDemo.processObject(str));
        assertEquals("Integer: " + num, PatternMatchingDemo.processObject(num));
        assertEquals("Boolean: " + bool, PatternMatchingDemo.processObject(bool));
        assertEquals("No encontró ninguna coincidencia", PatternMatchingDemo.processObject(dbl));

    }

    @Test
    void isValidEmail() {

        String example1 = "david@correo.com";
        String example2 = "davidcorreo.com";
        String example3 = "david";

        assertTrue(PatternMatchingDemo.isValidEmail(example1));
        assertFalse(PatternMatchingDemo.isValidEmail(example2));
        assertFalse(PatternMatchingDemo.isValidEmail(example3));
    }
}