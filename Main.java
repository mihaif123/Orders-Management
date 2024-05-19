package Start;

import Business_Logic.ClientBLL;
import Business_Logic.OrdersBLL;
import Model.Client;
import Presentation.ClientGUI;
import Presentation.OrdersGUI;
import Presentation.ProductGUI;

import javax.swing.plaf.BorderUIResource;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args){

        ClientGUI clientGUI= new ClientGUI();
        ProductGUI productGUI = new ProductGUI();
        OrdersGUI ordersGUI = new OrdersGUI();


        clientGUI.setVisible(true);
        productGUI.setVisible(true);
        ordersGUI.setVisible(true);
    }
}
