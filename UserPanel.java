package stockManagement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class UserPanel extends JFrame {
    private final QueryManagement queryManagement;
    private final Font label;
    private JTable pr;
    private JTable rt;

    public UserPanel(QueryManagement queryManagement) {
        super("Welcome " + queryManagement.getSelectedUser().getName());
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
        tabPanel.add("Products", allProducts());
        tabPanel.add("Purchases", allPurchase());
        tabPanel.add("Rent", allRent());
        tabPanel.setForeground(Color.WHITE);
        tabPanel.setFont(label);

        tabPanel.setBackgroundAt(0, Color.BLACK);
        tabPanel.setBackgroundAt(1, Color.BLACK);
        tabPanel.setBackgroundAt(2, Color.BLACK);

        panel.add(tabPanel);

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("Images/Panels.png"));
        background.setBounds(new Rectangle(0, 0, 800, 500));
        panel.add(background);
    }

    private JPanel allRent() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Product Name", "Rent Date", "Return Date", "Quantity", "Price"};
        String[][] data = queryManagement.getUserRent();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        rt = new JTable(model);
        JScrollPane sp = new JScrollPane(rt);
        sp.setBounds(10, 10, 710, 280);

        JLabel lbl = new JLabel("Rent ID: ");
        lbl.setBounds(30, 300, 290, 35);

        JTextField user = new JTextField();
        user.setFont(label);
        user.setBounds(130, 300, 290, 35);

        JButton delete = new JButton("Return");
        delete.setBounds(430, 298, 150, 40);
        delete.setBackground(Color.WHITE);

        delete.addActionListener(e->{
            String txt = user.getText();
            if(txt.matches("[0-9]+")){
                int id = Integer.parseInt(txt);
                int opt = JOptionPane.showConfirmDialog(null, "Are you sure you want to return the product?");
                if(opt == 0){
                    if(queryManagement.rentExist(id)){
                        queryManagement.returnRent(id);
                        updateJTable(rt, queryManagement.getUserRent());

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

    private JPanel allPurchase() {
        JPanel panel = new JPanel();
        panel.setLayout(null);

        String[] column = {"ID", "Product Name", "Date", "Quantity", "Price"};
        String[][] data = queryManagement.getUserPurchase();

        DefaultTableModel model = new DefaultTableModel(column, 0);
        for (String[] row : data)
            model.addRow(row);

        pr = new JTable(model);
        JScrollPane sp = new JScrollPane(pr);
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

        JLabel lN = new JLabel("Name: ");
        JLabel uN = new JLabel(queryManagement.getSelectedUser().getName());
        JLabel lE = new JLabel("Email: ");
        JLabel uE = new JLabel(queryManagement.getSelectedUser().getEmail());
        JLabel lB = new JLabel("Balance: ");
        JLabel uB = new JLabel("" + queryManagement.getSelectedUser().getBalance());

        lN.setBounds(420, 10, 150, 20);
        uN.setBounds(500, 10, 150, 20);
        lE.setBounds(420, 40, 150, 20);
        uE.setBounds(500, 40, 150, 20);
        lB.setBounds(420, 70, 150, 20);
        uB.setBounds(500, 70, 150, 20);

        panel.add(lN);
        panel.add(uN);
        panel.add(lE);
        panel.add(uE);
        panel.add(lB);
        panel.add(uB);

        JLabel id = new JLabel("ID");
        JLabel qn2 = new JLabel("Quantity");
        id.setBounds(420, 120, 100, 20);
        qn2.setBounds(420, 145, 100, 20);

        JTextField tid = new JTextField();
        JTextField tqn2 = new JTextField();
        tid.setBounds(500, 120, 200, 20);
        tqn2.setBounds(500, 145, 200, 20);

        JButton purchase = new JButton("Purchase");
        purchase.setBounds(420, 178, 150, 40);
        purchase.setBackground(Color.WHITE);

        JButton rent = new JButton("Rent");
        rent.setBounds(570, 178, 150, 40);
        rent.setBackground(Color.WHITE);

        panel.add(rent);
        panel.add(purchase);
        panel.add(id);
        panel.add(qn2);
        panel.add(tid);
        panel.add(tqn2);

        JLabel bl = new JLabel("Add balance");
        bl.setBounds(420, 250, 150, 20);
        JTextField tbl = new JTextField();
        tbl.setBounds(500, 250, 200, 20);

        JButton bbl = new JButton("Add Balance");
        bbl.setBounds(510, 280, 150, 40);
        bbl.setBackground(Color.WHITE);

        bbl.addActionListener(e -> {
            if (!tbl.getText().equals("")) {
                if (tbl.getText().matches("[0-9]+")) {
                    queryManagement.updateUser(Integer.parseInt(tbl.getText()));
                    uB.setText("" + queryManagement.getSelectedUser().getBalance());
                    tbl.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Enter Balance");
            }
        });

        purchase.addActionListener(e -> {
            if (!tid.getText().equals("") && !tqn2.getText().equals("")) {
                if (tid.getText().matches("[0-9]+") && tqn2.getText().matches("[0-9]+")) {
                    int pid = Integer.parseInt(tid.getText());
                    int quan = Integer.parseInt(tqn2.getText());
                    int[] quantityLeft = queryManagement.checkProduct(pid);
                    if (quantityLeft != null) {
                        if (quan <= quantityLeft[0]) {
                            if (quan * quantityLeft[1] < queryManagement.getSelectedUser().getBalance()) {
                                int total = quan * quantityLeft[1];
                                queryManagement.addPurchase(pid, quan, quan * quantityLeft[1],
                                        quantityLeft[0] - quan, queryManagement.getSelectedUser()
                                                .getBalance() - total);
                                queryManagement.getSelectedUser().setBalance(queryManagement.getSelectedUser()
                                        .getBalance() - total);
                                updateJTable(jt, queryManagement.getStocks());
                                updateJTable(pr, queryManagement.getUserPurchase());
                                uB.setText("" + queryManagement.getSelectedUser().getBalance());
                            } else {
                                JOptionPane.showMessageDialog(null,
                                    "Your balance is less then total. Total is: %d".formatted(quan * quantityLeft[1]));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                            "Quantity left for selected product is %d and you want %d".formatted(quantityLeft[0], quan));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong product ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID and quantity can only be number");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Enter ID and Quantity");
            }
        });

        rent.addActionListener(e -> {
            if (!tid.getText().equals("") && !tqn2.getText().equals("")) {
                if (tid.getText().matches("[0-9]+") && tqn2.getText().matches("[0-9]+")) {
                    int pid = Integer.parseInt(tid.getText());
                    int quan = Integer.parseInt(tqn2.getText());
                    int[] quantityLeft = queryManagement.checkProduct(pid);
                    if (quantityLeft != null) {
                        if (quan <= quantityLeft[0]) {
                            if (quan * quantityLeft[1] < queryManagement.getSelectedUser().getBalance()) {
                                int total = quan * quantityLeft[1];
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                Calendar c = Calendar.getInstance();
                                c.add(Calendar.DATE, 15);
                                queryManagement.addRent(pid, DateManagement.getDatabaseDate(c.getTime().getYear(),
                                        c.getTime().getMonth(), c.getTime().getDate()), quan, quan * quantityLeft[1],
                                        quantityLeft[0] - quan, queryManagement.getSelectedUser().getBalance() - total);
                                queryManagement.getSelectedUser().setBalance(queryManagement.getSelectedUser().getBalance() - total);
                                updateJTable(jt, queryManagement.getStocks());
                                updateJTable(rt, queryManagement.getUserRent());
                                uB.setText("" + queryManagement.getSelectedUser().getBalance());
                            } else {
                                JOptionPane.showMessageDialog(null,
                                    "Your balance is less then total. Total is: %d".formatted(quan * quantityLeft[1]));
                            }
                        } else {
                            JOptionPane.showMessageDialog(null,
                            "Quantity left for selected product is %d and you want %d".formatted(quantityLeft[0], quan));
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Wrong product ID");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "ID and quantity can only be number");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Enter ID and Quantity");
            }
        });

        panel.add(bl);
        panel.add(tbl);
        panel.add(bbl);
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
