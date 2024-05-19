package Business_Logic;

import Data_Access.OrdersDAO;
import Model.Orders;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer class for managing Orders entities.
 */
public class OrdersBLL {
    private OrdersDAO ordersDAO;

    /**
     * Constructs a new OrdersBLL object.
     */
    public OrdersBLL() {
        ordersDAO = new OrdersDAO();
    }

    /**
     * Finds an order by its ID.
     *
     * @param id The ID of the order to find.
     * @return The order with the specified ID.
     * @throws NoSuchElementException if no order with the specified ID is found.
     */
    public Orders findOrdersById(int id) {
        Orders orders = ordersDAO.findById(id);
        if (orders == null) {
            throw new NoSuchElementException("The Orders with id =" + id + " was not found!");
        }
        return orders;
    }

    /**
     * Retrieves all orders.
     *
     * @return A list of all orders.
     * @throws SQLException if a database access error occurs.
     */
    public List<Orders> findAllOrders() throws SQLException {
        return ordersDAO.findAll();
    }

    /**
     * Adds a new order.
     *
     * @param orders The order to add.
     * @throws IllegalAccessException if the operation is not permitted.
     */
    public void addOrders(Orders orders) throws IllegalAccessException {
        ordersDAO.insert(orders);
    }

    /**
     * Deletes an order by its ID.
     *
     * @param id The ID of the order to delete.
     */
    public void deleteOrders(int id) {
        ordersDAO.delete(id);
    }

    /**
     *
     * @param table
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public void populateTable(DefaultTableModel table) throws SQLException, IllegalAccessException {
        ordersDAO.populateTable(table);
    }
}
