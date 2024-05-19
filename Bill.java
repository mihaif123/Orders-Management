package Model;

/**
 * Bill record
 * @param cantitate
 * @param clientid
 * @param prodid
 * @param fullprice
 */
public record Bill(int cantitate, int clientid , int prodid,int fullprice) {
    public Bill {
        if (cantitate <= 0 || clientid <= 0 || prodid <= 0 || fullprice < 0) {
            throw new IllegalArgumentException("Invalid fullprice, cantitate, clientid sau prodid");
        }
    }
}