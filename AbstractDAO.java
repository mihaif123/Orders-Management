package Data_Access;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Model.Client;
import Model.Orders;
import Model.Product;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


/**
 * Data Access Object (DAO) for handling Abstract entities.
 *
 * @param <T>
 */

public class AbstractDAO<T> {

    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")

    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Query for retreiving contitioned fields
     *
     * @param field The field to be used as condition
     * @return String to be used as Query
     */
    private String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Query for updating a specific entry in a table
     *
     * @param entry The object to be edited
     * @return String to be used as Query
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private String createUpdateQuery(T entry) throws NoSuchFieldException, IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        sb.append(type.getSimpleName());
        sb.append(" set ");

        for(Field field : type.getDeclaredFields())
        {
            if(field.getName().equalsIgnoreCase("id"))
                continue;

            sb.append(field.getName());
            sb.append(" = ");

            Field aux = entry.getClass().getDeclaredField(field.getName());
            aux.setAccessible(true);
            sb.append("\"");
            sb.append(aux.get(entry));
            aux.setAccessible(false);
            sb.append("\"");
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 2);

        Field field = entry.getClass().getDeclaredField("id");
        field.setAccessible(true);

        sb.append(" where id = " + field.get(entry));
        field.setAccessible(false);

        System.out.println(sb.toString());
        return sb.toString();
    }

    /**
     *  Query for inserting a value in a table
     * @param entry Object to be inserted
     * @return String to be used as Query
     * @throws IllegalAccessException
     */
    private String createInsertQuery(T entry) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();

        sb.append("insert into ");
        sb.append(type.getSimpleName());
        sb.append(" (");
        for(Field field : type.getDeclaredFields())
        {
            sb.append(field.getName());
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(") values (");

        for(Field field : entry.getClass().getDeclaredFields())
        {
            field.setAccessible(true);
            sb.append("\"");
            sb.append(field.get(entry));
            sb.append("\"");
            field.setAccessible(false);
            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");


        System.out.println(sb.toString());


        return sb.toString();
    }

    /**
     * Query for returning all fields
     * @return String to be used as Query
     */
    public String createSelectAllQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * Query for deleting a field with the id sent as parameter
     * @param id The id of the Object to be deleted
     * @return String to be used as Query
     */
    private String deleteValuesQuery(int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("delete from ");
        sb.append(type.getSimpleName());
        sb.append(" where id = \"");
        sb.append(id);
        sb.append("\"");
        System.out.println(sb);
        return sb.toString();
    }

    /**
     * Function to retreive all fields in a table using reflexion
     * @return String to be used as Query
     * @throws SQLException
     */
    public List<T> findAll() throws SQLException {
        List<T> list = new ArrayList<T>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectAllQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            list = createObjects(resultSet);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return list;
    }

    /**
     * Function to retreive specific field based on id in a table using reflexion
     * @param id Id to be searched through the Table
     * @return String to be used as Query
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");

        try {

            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            List<T> objects = createObjects(resultSet);
            if (objects.isEmpty()) {
                return null;
            }



            return objects.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());

        }
        finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     *  Function for creating an object
     * @param resultSet Objects
     * @return String to be used as Query
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Function to insert a field in a table using reflexion
     * @param t Object to be inserted
     * @throws IllegalAccessException
     */
    public void insert(T t) throws IllegalAccessException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = null;

            query = createInsertQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            //statement.setInt(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Function to update a field in a table using reflexion using relfexion
     * @param t Object to be updated
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void update(T t) throws NoSuchFieldException, IllegalAccessException {

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = null;

        query = createUpdateQuery(t);

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Function to delete a specific field based on id sent as parameter using relfexion
     * @param id Object to be deleted
     */
    public void delete(int id)
    {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = null;

        try {
            query = deleteValuesQuery(id);
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);

            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Function to populate a table using relfexion
     * @param table The model of the table to be populated
     * @return Returns the header of the table
     * @throws SQLException
     * @throws IllegalAccessException
     */
    public JTableHeader populateTable(DefaultTableModel table) throws SQLException, IllegalAccessException {

        table.setColumnCount(0);
        table.setRowCount(0);

        JTableHeader header = new JTableHeader();
        header.setName(type.getSimpleName());

        for(Field i : type.getDeclaredFields())
        {
            table.addColumn(i.getName());
        }
        List<T> tList = findAll();

        for(T i : tList)
        {
            Vector<Object> fields = new Vector<>();
           for(Field j : i.getClass().getDeclaredFields())
           {
               j.setAccessible(true);
               fields.add(j.get(i));
               j.setAccessible(false);
           }
           table.addRow(fields);

        }
        return header;
    }

}

