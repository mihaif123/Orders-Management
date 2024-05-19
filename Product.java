package Model;

/**
 * Represents a product entity with an ID, name, pret, and stoc quantity.
 */
public class Product {

    private int id;
    private String name;
    private int pret;
    private int stoc;

    /**
     * Constructs a new Product object with default values.
     */
    public Product() {

    }

    /**
     * Constructs a new Product object with the specified ID, name, pret, and stoc quantity.
     *
     * @param id    The ID of the product.
     * @param name  The name of the product.
     * @param pret The pret of the product.
     * @param stoc The stoc quantity of the product.
     */
    public Product(int id, String name, int pret, int stoc) {
        this.id = id;
        this.name = name;
        this.pret = pret;
        this.stoc = stoc;
    }

    /**
     * Gets the ID of the product.
     *
     * @return The ID of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id The new ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The new name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the pret of the product.
     *
     * @return The pret of the product.
     */
    public int getPret() {
        return pret;
    }

    /**
     * Sets the pret of the product.
     *
     * @param pret The new pret of the product.
     */
    public void setPret(int pret) {
        this.pret = pret;
    }

    /**
     * Gets the stoc quantity of the product.
     *
     * @return The stoc quantity of the product.
     */
    public int getStoc() {
        return stoc;
    }

    /**
     * Sets the stoc quantity of the product.
     *
     * @param stoc The new stoc quantity of the product.
     */
    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    /**
     * Returns a string representation of the Product object.
     *
     * @return A string representation of the Product object.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pret=" + pret +
                ", stoc=" + stoc +
                '}';
    }
}
