package Model;

/**
 * Represents an order entity with an ID, cantitate, client ID, and product ID.
 */
public class Orders {

    private int id;
    private int cantitate;
    private int clientid;
    private int prodid;

    /**
     * Constructs a new Orders object with default values.
     */
    public Orders() {

    }

    /**
     * Constructs a new Orders object with the specified ID, cantitate, client ID, and product ID.
     *
     * @param id        The ID of the order.
     * @param cantitate  The cantitate of the product in the order.
     * @param clientid  The ID of the client who placed the order.
     * @param prodid The ID of the product in the order.
     */
    public Orders(int id, int cantitate, int clientid, int prodid) {
        this.id = id;
        this.cantitate = cantitate;
        this.clientid = clientid;
        this.prodid = prodid;
    }

    /**
     * Gets the ID of the order.
     *
     * @return The ID of the order.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the order.
     *
     * @param id The new ID of the order.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the cantitate of the product in the order.
     *
     * @return The cantitate of the product in the order.
     */
    public int getCantitate() {
        return cantitate;
    }

    /**
     * Sets the cantitate of the product in the order.
     *
     * @param cantitate The new cantitate of the product in the order.
     */
    public void setCantitate(int cantitate) {
        this.cantitate = cantitate;
    }

    /**
     * Gets the ID of the client who placed the order.
     *
     * @return The ID of the client who placed the order.
     */
    public int getClientid() {
        return clientid;
    }

    /**
     * Sets the ID of the client who placed the order.
     *
     * @param clientid The new ID of the client who placed the order.
     */
    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    /**
     * Gets the ID of the product in the order.
     *
     * @return The ID of the product in the order.
     */
    public int getProdid() {
        return prodid;
    }

    /**
     * Sets the ID of the product in the order.
     *
     * @param prodid The new ID of the product in the order.
     */
    public void setProdid(int prodid) {
        this.prodid = prodid;
    }

    /**
     * Returns a string representation of the Orders object.
     *
     * @return A string representation of the Orders object.
     */
    @Override
    public String toString() {
        return "Orders{" +
                "id=" + id +
                ", cantitate=" + cantitate +
                ", clientid=" + clientid +
                ", prodid=" + prodid +
                '}';
    }
}
