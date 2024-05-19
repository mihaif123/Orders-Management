package Business_Logic;

import Data_Access.ProductDAO;
import Model.Product;

import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Business Logic Layer class for managing Product entities.
 */
public class ProductBLL {
    private ProductDAO productDAO;

    /**
     * Constructs a new ProductBLL object.
     */
    public ProductBLL(){
        productDAO = new ProductDAO();
    }

    /**
     * Finds a product by its ID.
     *
     * @param id The ID of the product to find.
     * @return The product with the specified ID.
     * @throws NoSuchElementException if no product with the specified ID is found.
     */
    public Product findProductById(int id) {
        Product product = productDAO.findById(id);
        if (product == null) {
            throw new NoSuchElementException("The Product with id =" + id + " was not found!");
        }
        return product;
    }

    /**
     * Retrieves all products.
     *
     * @return A list of all products.
     * @throws SQLException if a database access error occurs.
     */
    public List<Product> findAllProducts() throws SQLException {
        return productDAO.findAll();
    }

    /**
     * Adds a new product.
     *
     * @param product The product to add.
     * @throws IllegalAccessException if the operation is not permitted.
     */
    public void addProduct(Product product) throws IllegalAccessException {
        productDAO.insert(product);
    }

    /**
     * Updates an existing product.
     *
     * @param product The product to update.
     * @throws NoSuchFieldException if a field does not exist.
     * @throws IllegalAccessException if the operation is not permitted.
     */
    public void updateProduct(Product product) throws NoSuchFieldException, IllegalAccessException {
        productDAO.update(product);
    }

    /**
     * Deletes a product by its ID.
     *
     * @param id The ID of the product to delete.
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }

    public void populateTable(DefaultTableModel table) throws SQLException, IllegalAccessException {
        productDAO.populateTable(table);
    }
}
