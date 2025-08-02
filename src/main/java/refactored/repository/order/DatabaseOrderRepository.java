package refactored.repository.order;

import refactored.model.Order;

public class DatabaseOrderRepository implements IOrderRepository {

    @Override
    public void save(Order order) {
        // TODO: lógica para guardar en base de datos
        System.out.println("Order saved to database");
    }

}
