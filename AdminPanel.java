package stockManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminPanel extends JFrame {
    private final QueryManagement queryManagement;
    private final Font label;

    public AdminPanel(QueryManagement queryManagement) {
        super("Admin Panel");
        this.queryManagement = queryManagement;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 520));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);
        label = new Font("Arial", Font.BOLD, 24);

        JTabbedPane tabPanel = new JTabbedPane();
        tabPanel.setBounds(30, 100, 750, 390);
        tabPanel.add("User", allUsers());
        tabPanel.add("Products", allProducts());
        tabPanel.add("Purchases", allPurchase());
        tabPanel.add("Rent", allRent());
        tabPanel.add("OverDue", allOverDue());
        tabPanel.setForeground(Color.WHITE);
        tabPanel.setFont(label);

        tabPanel.setBackgroundAt(0, Color.BLACK);
        tabPanel.setBackgroundAt(1, Color.BLACK);
        tabPanel.setBackgroundAt(2, Color.BLACK);
        tabPanel.setBackgroundAt(3, Color.BLACK);
        tabPanel.setBackgroundAt(4, Color.BLACK);

        panel.add(tabPanel);

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("Images/Panels.png"));
        background.setBounds(new Rectangle(0, 0, 800, 500));
        panel.add(background);
    }

    private JPanel allOverDue() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Product Name", "User Name", "Rent Date", "Return Date", "Quantity", "Price"};
        String[][] data = queryManagement.getOverDue();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 10, 710, 310);
        panel.add(sp);
        return panel;
    }

    private JPanel allRent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Product Name", "User Name", "Rent Date", "Return Date", "Quantity", "Price"};
        String[][] data = queryManagement.getRent();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 10, 710, 310);
        panel.add(sp);
        return panel;
    }

    private JPanel allPurchase() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Product Name", "User Name", "Date", "Quantity", "Price"};
        String[][] data = queryManagement.getAllPurchase();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 10, 710, 310);
        panel.add(sp);
        return panel;
    }

    private JPanel allProducts() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Name", "Price", "Description", "Quantity"};
        String[][] data = queryManagement.getStocks();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 10, 400, 310);

        JLabel lN = new JLabel("Name");
        JLabel pr = new JLabel("Price");
        JLabel ds = new JLabel("Des");
        JLabel qn = new JLabel("Quantity");
        JLabel id = new JLabel("ID");
        JLabel qn2 = new JLabel("Quantity");

        lN.setBounds(420, 10, 100, 20);
        pr.setBounds(420, 35, 100, 20);
        qn.setBounds(420, 60, 100, 20);
        ds.setBounds(420, 85, 100, 20);
        id.setBounds(420, 250, 100, 20);
        qn2.setBounds(420, 275, 100, 20);

        JTextField tlN = new JTextField();
        JTextField tpr = new JTextField();
        JTextArea tds = new JTextArea();
        JTextField tqn = new JTextField();
        JTextField tid = new JTextField();
        JTextField tqn2 = new JTextField();

        tlN.setBounds(500, 10, 200, 20);
        tpr.setBounds(500, 35, 200, 20);
        tqn.setBounds(500, 60, 200, 20);
        tds.setBounds(418, 110, 300, 80);
        tid.setBounds(500, 250, 200, 20);
        tqn2.setBounds(500, 275, 200, 20);

        panel.add(lN);
        panel.add(pr);
        panel.add(qn);
        panel.add(ds);
        panel.add(id);
        panel.add(qn2);
        panel.add(tlN);
        panel.add(tpr);
        panel.add(tqn);
        panel.add(tds);
        panel.add(tid);
        panel.add(tqn2);

        JButton add = new JButton("Add");
        add.setBounds(520, 200, 150, 30);
        add.setBackground(Color.WHITE);

        JButton update = new JButton("Update");
        update.setBounds(520, 298, 150, 30);
        update.setBackground(Color.WHITE);

        add.addActionListener(e->{
            if(!tlN.getText().equals("") && !tpr.getText().equals("") && !tqn.getText().equals("")){
                if(tpr.getText().matches("[0-9]+") && tqn.getText().matches("[0-9]+")){
                    queryManagement.addProduct(tlN.getText(), Integer.parseInt(tpr.getText()), tds.getText(), Integer.parseInt(tqn.getText()));
                    updateJTable(jt, queryManagement.getStocks());
                    tlN.setText("");
                    tqn.setText("");
                    tds.setText("");
                    tpr.setText("");
                } else{
                    JOptionPane.showMessageDialog(null, "Quantity and price can only be number");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Form incomplete");
            }
        });

        update.addActionListener(e->{
            if(!tid.getText().equals("") && !tqn2.getText().equals("")){
                if(tqn2.getText().matches("[0-9]+") && tid.getText().matches("[0-9]+")){
                    queryManagement.updateProduct(Integer.parseInt(tid.getText()), Integer.parseInt(tqn2.getText()));
                    updateJTable(jt, queryManagement.getStocks());
                    tid.setText("");
                    tqn2.setText("");
                } else{
                    JOptionPane.showMessageDialog(null, "Quantity and id can only be number");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Form incomplete");
            }
        });
        panel.add(sp);
        panel.add(add);
        panel.add(update);
        return panel;
    }

    private JPanel allUsers() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Name", "Email", "Password", "Balance"};
        String[][] data = queryManagement.getAllUser();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        JTable jt = new JTable(model);
        JScrollPane sp = new JScrollPane(jt);
        sp.setBounds(10, 10, 710, 280);

        JLabel lbl = new JLabel("User ID: ");
        lbl.setBounds(30, 300, 290, 35);

        JTextField user = new JTextField();
        user.setFont(label);
        user.setBounds(130, 300, 290, 35);

        JButton delete = new JButton("Delete");
        delete.setFont(label);
        delete.setBounds(430, 298, 150, 40);
        delete.setBackground(Color.WHITE);

        delete.addActionListener(e->{
            String txt = user.getText();
            if(txt.matches("[0-9]+")){
                int id = Integer.parseInt(txt);
                int opt = JOptionPane.showConfirmDialog(null, "All the records of the user will be deleted. Are you sure you want to delete user?");
                if(opt == 0){
                    if(queryManagement.userExist(id)){
                        queryManagement.deleteUser(id);
                        updateJTable(jt, queryManagement.getAllUser());

                    }else{
                        JOptionPane.showMessageDialog(null, "No user exist with current ID");
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null, "ID can only be number");
            }
        });

        panel.add(lbl);
        panel.add(user);
        panel.add(delete);
        panel.add(sp);
        return panel;
    }

    private void updateJTable(JTable tb, String[][] data) {
        DefaultTableModel model = (DefaultTableModel) tb.getModel();
        model.setRowCount(0);
        for (String[] row : data)
            model.addRow(row);
    }

}
