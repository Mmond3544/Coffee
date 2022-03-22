import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Addmenu extends JFrame{
    private JPanel mainp;
    private JButton summit;
    private JTextField menutext;
    private JTextField pricetext;
    public int i = 0;
    public Addmenu() {
        summit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String menu = menutext.getText();
                int price = Integer.parseInt(pricetext.getText());
                i = i+1;
                Connection connect = null;
                Statement s = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect =  DriverManager.getConnection("jdbc:mysql://localhost/project" +
                            "?user=root&password=");
                    s = connect.createStatement();
                        String sql = "INSERT INTO menu " +
                                "(No,name,price) " +
                                "VALUES ('"+i+"','"+menu+"',"+price+")";
                        s.execute(sql);
                        JOptionPane.showMessageDialog(null,"Record Inserted Successfully");
                        menutext.setText("");
                        pricetext.setText("");
                } catch (Exception c) {
                    // TODO Auto-generated catch block
                    c.printStackTrace();
                }
                // Close
                try {
                    if(connect != null){
                        s.close();
                        connect.close();
                    }
                } catch (SQLException c) {
                    // TODO Auto-generated catch block
                    c.printStackTrace();
                }
            }
        });
    }

    public void connect()
    {
        Connection connect = null;
        Statement s = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect =  DriverManager.getConnection("jdbc:mysql://localhost/project" +
                    "?user=root&password=");
            s = connect.createStatement();
            String sql = "SELECT No FROM menu ";
            ResultSet rec = s.executeQuery(sql);
            while((rec!=null) && (rec.next())) {
                i = rec.getInt("No");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Close
        try {
            if(connect != null){
                connect.close();
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void start() {
        setSize(400, 350);
        setVisible(true);
        setContentPane(mainp);
    }
    public static void main(String[] args)
    {
        Addmenu a = new Addmenu();
        a.start();
        a.connect();
    }
}
