package Presentation;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Business_Logic.ClientBLL;
import Data_Access.AbstractDAO;
import Model.Client;
/**
 * This class represents the graphical user interface for managing client data.
 */
public class ClientGUI extends JFrame{
    private JButton refreshButton;
    private JTextField textField1;
    private JTextField textField2;
    private JButton addButton;
    private JPanel panel1;
    private JTextField textField3;
    private JButton editButton;
    private JTextField textField4;
    private JButton deleteButton;
    private JTextField textField5;
    private JTextField textField6;
    private JTable table1;
    private JScrollPane scroll;
    /**
     * Constructs a new ClientGUI object.
     */
    public ClientGUI(){
        super("ClientGUI");

        this.setContentPane(panel1);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        scroll.setPreferredSize(new Dimension(scroll.getPreferredSize().width, 100));
        this.pack();

        // ActionListener for Refresh button
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ClientBLL clientBLL = new ClientBLL();

                DefaultTableModel model = (DefaultTableModel) table1.getModel();

                JTableHeader header;

                try {
                    header = clientBLL.populateTable(model);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }

                table1.setTableHeader(header);

                System.out.println(header.getName());

            }
        });

        // ActionListener for Add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();

                Client aux = new Client();
                aux.setName(textField1.getText());
                aux.setPrenume(textField2.getText());

                try {
                    clientBLL.addClient(aux);
                } catch (IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        // ActionListener for Edit button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();

                Client aux = new Client();
                aux.setId(Integer.parseInt(textField3.getText()));
                aux.setPrenume(textField6.getText());
                aux.setName(textField5.getText());

                try {
                    clientBLL.updateClient(aux);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });

        // ActionListener for Delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientBLL clientBLL = new ClientBLL();

                int deleted = Integer.parseInt(textField4.getText());

                clientBLL.deleteClient(deleted);


            }
        });

    }



}
