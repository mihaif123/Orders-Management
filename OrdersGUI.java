package Presentation;

import Business_Logic.ClientBLL;
import Business_Logic.OrdersBLL;
import Business_Logic.ProductBLL;
import Connection.ConnectionFactory;
import Model.Bill;
import Model.Client;
import Model.Orders;
import Model.Product;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represents the graphical user interface for managing Orders data.
 */

public class OrdersGUI extends JFrame{
    private JPanel panel1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JButton createOrderButton;
    private JButton showOrdersButton;
    private JTable table1;
    private JButton hideButton;
    private JScrollPane scroll;
    private JPanel panel;
    /**
     * Constructs a new OrdersGUI object.
     */
    public OrdersGUI()
    {
        super("OrdersGUI");

        this.setContentPane(panel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();

        scroll.setVisible(false);
        panel.setVisible(false);
        hideButton.setVisible(false);

        setSize(470,190);

        // ActionListener for Create Order button
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrdersBLL ordersBLL = new OrdersBLL();
                ProductBLL productBLL = new ProductBLL();
                ClientBLL clientBLL = new ClientBLL();

                Orders order = new Orders();

                order.setClientid(Integer.parseInt(textField1.getText()));
                order.setProdid(Integer.parseInt(textField2.getText()));
                order.setCantitate(Integer.parseInt(textField3.getText()));

                try {
                   productBLL.findProductById(order.getProdid());
                }
                catch (NoSuchElementException ee)
                {
                    JOptionPane.showMessageDialog(panel1,"Invalid Product ID","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    clientBLL.findClientById(order.getClientid());
                }
                catch (NoSuchElementException ee)
                {
                    JOptionPane.showMessageDialog(panel1,"Invalid Client ID","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }


                int remaining = productBLL.findProductById(order.getProdid()).getStoc() - order.getCantitate();
                System.out.println(remaining);

                if(remaining < 0)
                {
                    JOptionPane.showMessageDialog(panel1,"Quantity higher than stock","ERROR",JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product aux = new Product();
                aux.setId(productBLL.findProductById(order.getProdid()).getId());
                aux.setName(productBLL.findProductById(order.getProdid()).getName());
                aux.setPret(productBLL.findProductById(order.getProdid()).getPret());
                aux.setStoc(remaining);

                try {
                    productBLL.updateProduct(aux);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

                try {
                    ordersBLL.addOrders(order);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
                Bill bill = new Bill(order.getCantitate(),order.getClientid(),order.getProdid(),order.getCantitate() * aux.getPret());

                String query = "insert into Log" + "(cantitate,clientid,prodid,fullprice) values (?, ? , ?, ?)" ;
                Connection connection = null;
                PreparedStatement statement = null;
                try {
                    connection = ConnectionFactory.getConnection();
                    statement = connection.prepareStatement(query);

                    statement.setInt(1, bill.cantitate());
                    statement.setInt(2, bill.clientid());
                    statement.setInt(3,bill.prodid());
                    statement.setInt(4,bill.fullprice());
                    System.out.println(statement.toString());
                    statement.executeUpdate();

                } catch (SQLException ee)
                {
                    System.out.println(ee.getMessage());
                }

                JOptionPane.showMessageDialog(panel1,"Order sent succesfully");

            }
        });

        // ActionListener for Show Orders button
        showOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrdersBLL ordersBll = new OrdersBLL();

                panel.setVisible(true);
                scroll.setVisible(true);
                table1.setVisible(true);
                hideButton.setVisible(true);


                setSize(470,650);

                try {
                    refershTable(ordersBll);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        // ActionListener for Hide button
        hideButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel.setVisible(false);
                scroll.setVisible(false);
                table1.setVisible(false);
                hideButton.setVisible(false);
                setSize(470,190);
            }
        });
    }

    void refershTable(OrdersBLL ordersBLL) throws SQLException, IllegalAccessException {

        DefaultTableModel model = (DefaultTableModel) table1.getModel();

        ordersBLL.populateTable(model);

    }


}
