package features;

import com.development.attendance.service.features.OrdenStatus;
import com.development.attendance.service.features.StatusHandler;
import org.junit.jupiter.api.Test;

class StatusHandlerTest {

    @Test
    void getStatus() {
        assertEquals("Pendiente", StatusHandler.getStatus(OrdenStatus.PENDING));
        assertEquals("Completada", StatusHandler.getStatus(OrdenStatus.COMPLETED));
        assertEquals("Cancelada", StatusHandler.getStatus(OrdenStatus.CANCELLED));
    }
}