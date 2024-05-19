package Business_Logic;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;
import Data_Access.ClientDAO;
import Model.Client;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 * Business Logic Layer class for managing Client entities.
 */
public class ClientBLL {

    private ClientDAO clientDAO;

    /**
     * Constructs a new ClientBLL object.
     */
    public ClientBLL() {
        clientDAO = new ClientDAO();
    }

    /**
     * Finds a client by their ID.
     *
     * @param id The ID of the client to find.
     * @return The client with the specified ID.
     * @throws NoSuchElementException if no client with the specified ID is found.
     */
    public Client findClientById(int id) {
        Client client = clientDAO.findById(id);
        if (client == null) {
            throw new NoSuchElementException("The Client with id =" + id + " was not found!");
        }
        return client;
    }

    /**
     * Retrieves all clients.
     *
     * @return A list of all clients.
     * @throws SQLException if a database access error occurs.
     */
    public List<Client> findAllClients() throws SQLException {
        return clientDAO.findAll();
    }

    /**
     * Adds a new client.
     *
     * @param client The client to add.
     * @throws IllegalAccessException if the operation is not permitted.
     */
    public void addClient(Client client) throws IllegalAccessException {
        clientDAO.insert(client);
    }

    /**
     * Updates an existing client.
     *
     * @param client The client to update.
     * @throws NoSuchFieldException if a field does not exist.
     * @throws IllegalAccessException if the operation is not permitted.
     */
    public void updateClient(Client client) throws NoSuchFieldException, IllegalAccessException {
        clientDAO.update(client);
    }

    /**
     * Deletes a client by their ID.
     *
     * @param id The ID of the client to delete.
     */
    public void deleteClient(int id) {
        clientDAO.delete(id);
    }

    /**
     *
     * @param table
     * @return
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public JTableHeader populateTable(DefaultTableModel table) throws SQLException, IllegalAccessException {
        return clientDAO.populateTable(table);
    }

}
