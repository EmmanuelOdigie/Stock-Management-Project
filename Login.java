package stockManagement;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private final QueryManagement queryManagement;

    public Login() throws HeadlessException {
        super("Stock Management");
        queryManagement = new QueryManagement();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(800, 520));
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        setContentPane(panel);

        JLabel[] labels = new JLabel[7];
        labels[0] = new JLabel("Login", SwingConstants.CENTER);
        labels[1] = new JLabel("Sign Up", SwingConstants.CENTER);
        labels[2] = new JLabel("Email:    ");
        labels[3] = new JLabel("Password: ");
        labels[4] = new JLabel("Name:     ");
        labels[5] = new JLabel("Email:    ");
        labels[6] = new JLabel("Password: ");

        Font label = new Font("Arial", Font.BOLD, 24);
        Font title = new Font("Gotham", Font.BOLD, 40);
        for (int i = 0; i < 7; i++) {
            labels[i].setSize(180, 70);
            labels[i].setForeground(Color.WHITE);
            if (i < 2)
                labels[i].setFont(title);
            else
                labels[i].setFont(label);
        }

        labels[0].setLocation(120, 105);
        labels[2].setLocation(65, 165);
        labels[3].setLocation(65, 245);
        labels[1].setLocation(500, 105);
        labels[4].setLocation(440, 145);
        labels[5].setLocation(440, 225);
        labels[6].setLocation(440, 300);

        for (int i = 0; i < 7; i++)
            panel.add(labels[i]);

        JTextField logEmail = getTextField(label, 62, 220);
        panel.add(logEmail);

        JTextField sigName = getTextField(label, 437, 200);
        panel.add(sigName);

        JTextField sigEmail = getTextField(label, 437, 280);
        panel.add(sigEmail);

        JPasswordField logPass = getPasswordField(label, 62, 300);
        panel.add(logPass);

        JPasswordField sigPass = getPasswordField(label, 437, 355);
        panel.add(sigPass);

        JButton login = new JButton("Login");
        login.setFont(label);
        login.setBounds(130, 370, 150, 50);
        login.setBackground(Color.WHITE);
        panel.add(login);

        JButton signUp = new JButton("Sign Up");
        signUp.setFont(label);
        signUp.setBounds(500, 400, 150, 50);
        login.setBackground(Color.WHITE);
        panel.add(signUp);

        JLabel background = new JLabel();
        background.setIcon(new ImageIcon("Images/Background.png"));
        background.setBounds(new Rectangle(0, 0, 800, 500));
        panel.add(background);

        login.addActionListener(e -> {
            if (!logEmail.getText().equals("") && !new String(logPass.getPassword()).equals("")) {
                if (logEmail.getText().equalsIgnoreCase("Admin") && new String(logPass.getPassword()).equalsIgnoreCase("Admin")) {
                    AdminPanel panel1 = new AdminPanel(queryManagement);
                    panel1.setVisible(true);
                    dispose();
                }else if (queryManagement.authenticateUser(logEmail.getText(), new String(logPass.getPassword()))) {
                    UserPanel panel1 = new UserPanel(queryManagement);
                    panel1.setVisible(true);
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong Email and Password!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Enter email and password!!");
            }
        });

        signUp.addActionListener(e->{
            if(!sigName.getText().equals("") && !sigEmail.getText().equals("") && !new String(sigPass.getPassword()).equals("")){
                if(sigEmail.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
                    if(sigPass.getPassword().length >= 8){
                        queryManagement.addUser(sigName.getText(), sigEmail.getText(), new String(sigPass.getPassword()));
                        JOptionPane.showMessageDialog(null, "Account created");
                    }else {
                        JOptionPane.showMessageDialog(null, "Password Length should be greater then 8");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Wrong Email address");
                }
            }else{
                JOptionPane.showMessageDialog(null, "Form incomplete");
            }
        });
    }

    private JTextField getTextField(Font font, int x, int y) {
        JTextField text = new JTextField();
        text.setFont(font);
        text.setBounds(x, y, 290, 35);
        return text;
    }

    private JPasswordField getPasswordField(Font font, int x, int y) {
        JPasswordField text = new JPasswordField();
        text.setFont(font);
        text.setBounds(x, y, 290, 35);
        return text;
    }
}
