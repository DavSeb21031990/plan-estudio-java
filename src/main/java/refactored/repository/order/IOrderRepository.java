package refactored.repository.order;

import refactored.model.Order;

public interface IOrderRepository {
    void save(Order order);
}
