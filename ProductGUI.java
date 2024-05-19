package Presentation;

import Business_Logic.ClientBLL;
import Business_Logic.ProductBLL;
import Business_Logic.ProductBLL;
import Model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
/**
 * This class represents the graphical user interface for managing Product data.
 */
public class ProductGUI extends JFrame{
    private JTextArea textArea1;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField textField7;
    private JTextField textField8;
    private JButton addButton;
    private JButton refreshButton;
    private JButton editButton;
    private JButton deleteButton;
    private JPanel panel2;
    private JTable table1;
    private JScrollPane scroll;
    /**
     * Constructs a new ProductGUI object.
     */
    public ProductGUI() {

        super("ProductGUI");

        this.setContentPane(panel2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, 100));
        this.pack();

        // ActionListener for Refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ProductBLL productBLL = new ProductBLL();

                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                try {
                    productBLL.populateTable(model);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        // ActionListener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductBLL productBLL = new ProductBLL();

                Product aux = new Product();

                aux.setName(textField1.getText());
                aux.setPret(Integer.parseInt(textField2.getText()));
                aux.setStoc(Integer.parseInt(textField3.getText()));

                try {
                    productBLL.addProduct(aux);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        // ActionListener for Edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductBLL productBll = new ProductBLL();
                Product aux = new Product();

                aux.setId(Integer.parseInt(textField4.getText()));
                aux.setName(textField5.getText());
                aux.setPret(Integer.parseInt(textField6.getText()));
                aux.setStoc(Integer.parseInt(textField7.getText()));

                try {
                    productBll.updateProduct(aux);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ActionListener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductBLL productBLL = new ProductBLL();

                int deleted = Integer.parseInt(textField8.getText());

                productBLL.deleteProduct(deleted);
            }
        });
    }
}
