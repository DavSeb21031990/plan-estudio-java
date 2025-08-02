package features;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class StatusHandlerTest {

    @Test
    void getStatus() {
        assertEquals("Pendiente", StatusHandler.getStatus(OrdenStatus.PENDING));
        assertEquals("Completada", StatusHandler.getStatus(OrdenStatus.COMPLETED));
        assertEquals("Cancelada", StatusHandler.getStatus(OrdenStatus.CANCELLED));
    }
}