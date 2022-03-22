import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class login extends JFrame{
    private JPanel mainp;
    private JButton button1;
    private JTextField username;
    private JPasswordField password;

    public login() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = "admin";
                String myPass=String.valueOf(password.getPassword());
                String Uname=String.valueOf(username.getText());
                String pass = "0000";
                System.out.println(Uname+"   "+myPass);
                if(Uname.equals(name) && myPass.equals(pass))
                {
                    back b = new back();
                    b.start();
                    start();
                    setVisible(false);
                }
                else
                {
                    JOptionPane.showMessageDialog(null,"Username or Password incorrect");
                }
            }
        });
    }
    public void start()
    {
        setSize(400, 250);
        setVisible(true);
        setContentPane(mainp);
    }
    public static void main(String[] args)
    {
        login l = new login();
        l.start();
    }
}
