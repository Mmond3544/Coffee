import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.*;

public class front extends JFrame{
    private JPanel mainp;
    private JComboBox menuCombo;
    private JRadioButton hot;
    private JRadioButton cool;
    private JSpinner spin;
    private JButton button1;
    private JTable Tlist;
    private JButton sent;
    private JLabel backend;
    private JButton delete;
    private JLabel imgbg;

    public int i = 0;
    public int price[] = new int[100];
    public int p[] = new int[100];
    public String menu[] = new String[100];
    public String type[] = new String[100];
    public int total[] = new int[100];
    public int amount[] = new int[100];
    public void list(){
        DefaultTableModel model = (DefaultTableModel)Tlist.getModel();
        for(int a = 0;a<i;a++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(a+1,a,0);
            model.setValueAt(menu[a],a,1);
            model.setValueAt(type[a],a,2);
            model.setValueAt(amount[a],a,3);
            model.setValueAt(price[a],a,4);
            model.setValueAt(total[a],a,5);
        }
    }
    public front() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu[i] = String.valueOf(menuCombo.getSelectedItem());
                price[i] = p[menuCombo.getSelectedIndex()];
                type[i] = "ร้อน";
                if(cool.isSelected() == true)
                {
                    price[i] = price[i] + 10;
                    type[i] = "เย็น";
                }
                amount[i] = Integer.parseInt(spin.getValue().toString());
                total[i] = amount[i] * price[i];
                i++;
                hot.setSelected(false);
                cool.setSelected(false);
                hot.setEnabled(true);
                cool.setEnabled(true);
                DefaultTableModel model = (DefaultTableModel)Tlist.getModel();
                model.setRowCount(0);
                list();

            }
        });
        hot.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(hot.isSelected() == true)
                {
                    cool.setEnabled(false);
                }
                else if(hot.isSelected() == false)
                {
                    cool.setEnabled(true);
                }
            }
        });
        cool.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(cool.isSelected() == true)
                {
                    hot.setEnabled(false);
                }
                else if(cool.isSelected() == false)
                {
                    hot.setEnabled(true);
                }
            }
        });
        sent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connect = null;
                Statement s = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect =  DriverManager.getConnection("jdbc:mysql://localhost/project" +
                            "?user=root&password=");
                    s = connect.createStatement();
                    for(int a = 0; a < i;a++) {
                        String sql = "INSERT INTO orders " +
                                "(date,list,type,amount,price,total) " +
                                "VALUES (curdate(),'"+menu[a]+"','"+type[a]+"',"+amount[a]+","+price[a]+","+total[a]+")";
                        s.execute(sql);
                    }
                    System.out.println("Record Inserted Successfully");
                    for(int a = 0;a < i;a++)
                    {
                        menu[a] = "";
                        type[a] = "";
                        amount[a] = 0;
                        price [a] = 0;
                        total [a] = 0;
                    }
                    i = 0;
                    DefaultTableModel model = (DefaultTableModel)Tlist.getModel();
                    model.setRowCount(0);
                    list();
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
        backend.addMouseListener(new MouseAdapter()
        {
            public void mouseClicked(MouseEvent e)
            {
                login l = new login();
                l.start();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int a = 0;a < i;a++)
                {
                    menu[a] = "";
                    type[a] = "";
                    amount[a] = 0;
                    price [a] = 0;
                    total [a] = 0;
                }
                i = 0;
                DefaultTableModel model = (DefaultTableModel)Tlist.getModel();
                model.setRowCount(0);
                list();
            }
        });
    }
    public void connect() {
        Connection connect = null;
        Statement s = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect =  DriverManager.getConnection("jdbc:mysql://localhost/project" +
                    "?user=root&password=");

            if(connect != null){
                System.out.println("Database Connected.");
            } else {
                System.out.println("Database Connect Failed.");
            }
            s = connect.createStatement();
            String sql = "SELECT * FROM menu ";
            ResultSet rec = s.executeQuery(sql);
            int i = 0;
            while((rec!=null) && (rec.next()))
            {
                menuCombo.addItem(rec.getString("name"));
                p[i] = rec.getInt("price");
                i++;
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
    public void start(){
        setSize(1150,650);
        setVisible(true);
        setContentPane(mainp);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        DefaultTableModel model = (DefaultTableModel)Tlist.getModel();
        imgbg.setIcon(new ImageIcon("./bgFront.jpg"));
        model.addColumn("No.");
        model.addColumn("List");
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Price");
        model.addColumn("Total");

    }
    public static void main(String[] args){

        front s = new front();
        s.start();
        s.connect();
    }
}