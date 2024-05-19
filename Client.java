package Model;

/**
 * Represents a client entity with an ID, name, and surname.
 */
public class Client {

    private int id ;
    private String name;
    private String prenume;
    /**
     * Constructs a new Client object with default values.
     */
    public Client()
    {

    }
    /**
     * Constructs a new Client object with the specified ID, name, and surname.
     *
     * @param id      The ID of the client.
     * @param name    The name of the client.
     * @param prenume The surname of the client.
     */
    public Client(int id, String name, String prenume) {
        this.id = id;
        this.name = name;
        this.prenume = prenume;
    }

    /**
     * Gets the ID of the client.
     *
     * @return The ID of the client.
     */
    public int getId() {
        return id;
    }
    /**
     * Sets the ID of the client.
     *
     * @param id The new ID of the client.
     */

    public void setId(int id) {
        this.id = id;
    }
    /**
     * Gets the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the client.
     *
     * @param name The new name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the client.
     *
     * @return The surname of the client.
     */
    public String getPrenume() {
        return prenume;
    }
    /**
     * Sets the surname of the client.
     *
     * @param prenume The new surname of the client.
     */
    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }
    /**
     * Returns a string representation of the Client object.
     *
     * @return A string representation of the Client object.
     */
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", prenume='" + prenume + '\'' +
                '}';
    }
}
