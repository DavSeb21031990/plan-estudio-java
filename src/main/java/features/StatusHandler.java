package features;

public class StatusHandler {

    private StatusHandler(){
        //
    }

    public static String getStatus(OrdenStatus status){
        return switch (status){
            case OrdenStatus.PENDING -> "Pendiente";
            case OrdenStatus.COMPLETED -> "Completada";
            case OrdenStatus.CANCELLED -> "Cancelada";
        };
    }
}
