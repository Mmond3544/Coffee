import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class sumary extends JFrame{
    private JPanel mainp;
    private JTable list;
    private JComboBox datecombo;
    private JComboBox menucombo;
    private JButton summit;
    private JLabel summary;
    private JLabel Total;

    int monthselect = 0;
    public int i = 0;
    public int price[] = new int[100];
    public String menu[] = new String[100];
    public String type[] = new String[100];
    public String date[] = new String[100];
    public int total[] = new int[100];
    public int amount[] = new int[100];
    public int amt = 0;
    public int ttl = 0;
    public void list(){
        DefaultTableModel model = (DefaultTableModel)list.getModel();
        for(int a = 0;a<i;a++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(date[a],a,0);
            model.setValueAt(menu[a],a,1);
            model.setValueAt(type[a],a,2);
            model.setValueAt(amount[a],a,3);
            model.setValueAt(price[a],a,4);
            model.setValueAt(total[a],a,5);
        }
    }

    public sumary() {
        summit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                monthselect = datecombo.getSelectedIndex();
                String l = String.valueOf(menucombo.getSelectedItem());
                String sql = "";
                Connection connect = null;
                Statement s = null;
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    connect =  DriverManager.getConnection("jdbc:mysql://localhost/project" +
                            "?user=root&password=");
                    s = connect.createStatement();
                    if(datecombo.getSelectedIndex() == 0 && menucombo.getSelectedIndex() == 0)
                    {
                        sql = "SELECT * FROM orders ORDER BY date ASC";
                    }
                    else if(datecombo.getSelectedIndex() == 0)
                    {
                        sql = "SELECT * FROM orders WHERE list = '"+l+"' ORDER BY date ASC";
                    }
                    else if(menucombo.getSelectedIndex() == 0)
                    {
                        sql = "SELECT * FROM orders WHERE MONTH(date) = "+monthselect;
                    }
                    else
                    {
                        sql = "SELECT * FROM orders WHERE MONTH(date) = "+monthselect+" && list = '"+l+"' ORDER BY date ASC";
                    }
                    ResultSet rec = s.executeQuery(sql);
                    DefaultTableModel model = (DefaultTableModel)list.getModel();
                    int rows = model.getRowCount();
                    for(int i = rows - 1; i >=0; i--)
                    {
                        model.removeRow(i);
                    }
                    int a = 0;
                    amt = 0;
                    ttl = 0;
                    while((rec!=null) && (rec.next()))
                    {
                        model.addRow(new Object[0]);
                        model.setValueAt(rec.getString("date"),a,0);
                        model.setValueAt(rec.getString("list"),a,1);
                        model.setValueAt(rec.getString("type"),a,2);
                        model.setValueAt(rec.getInt("amount"),a,3);
                        model.setValueAt(rec.getInt("price"),a,4);
                        model.setValueAt(rec.getInt("total"),a,5);
                        amount[a] = rec.getInt("amount");
                        total[a] = rec.getInt("total");
                        amt = amount[a] + amt;
                        ttl = total[a] + ttl;
                        i++;
                        a++;
                    }
                    summary.setText("จำนวนเครื่องดื่มที่ขายได้ : "+amt+" แก้ว");
                    Total.setText("รายได้รวม : "+ttl+" บาท");
                } catch (Exception c) {
                    // TODO Auto-generated catch block
                    c.printStackTrace();
                }
                // Close
                try {
                    if(connect != null){
                        connect.close();
                    }
                } catch (SQLException c) {
                    // TODO Auto-generated catch block
                    c.printStackTrace();
                }
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
            String sql = "SELECT * FROM orders ORDER BY date ASC";
            ResultSet rec = s.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel)list.getModel();
            int a = 0;
            while((rec!=null) && (rec.next()))
            {
                model.addRow(new Object[0]);
                model.setValueAt(rec.getString("date"),a,0);
                model.setValueAt(rec.getString("list"),a,1);
                model.setValueAt(rec.getString("type"),a,2);
                model.setValueAt(rec.getInt("amount"),a,3);
                model.setValueAt(rec.getInt("price"),a,4);
                model.setValueAt(rec.getInt("total"),a,5);
                amount[a] = rec.getInt("amount");
                total[a] = rec.getInt("total");
                amt = amount[a] + amt;
                ttl = total[a] + ttl;
                i++;
                a++;
            }
            summary.setText("จำนวนเครื่องดื่มที่ขายได้ : "+amt+" แก้ว");
            Total.setText("รายได้รวม : "+ttl+" บาท");
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
    public void dateadd()
    {
        datecombo.addItem("ทั้งหมด");
        datecombo.addItem("มกราคม");
        datecombo.addItem("กุมภาพันธ์");
        datecombo.addItem("มีนาคม");
        datecombo.addItem("เมษายน");
        datecombo.addItem("พฤษภาคม");
        datecombo.addItem("มิถุนายน");
        datecombo.addItem("กรกฎาคม");
        datecombo.addItem("สิงหาคม");
        datecombo.addItem("กันยายน");
        datecombo.addItem("ตุลาคม");
        datecombo.addItem("พฤศจิกายน");
        datecombo.addItem("ธันวาคม");
    }
    public void menuadd()
    {
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
            menucombo.addItem("ทั้งหมด");
            while((rec!=null) && (rec.next()))
            {
                menucombo.addItem(rec.getString("name"));
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
        setSize(1000, 700);
        setVisible(true);
        setContentPane(mainp);
        menuadd();
        dateadd();
        DefaultTableModel model = (DefaultTableModel) list.getModel();
        model.addColumn("Date");
        model.addColumn("List");
        model.addColumn("Type");
        model.addColumn("Amount");
        model.addColumn("Price");
        model.addColumn("Total");
    }
    public static void main(String[] args){
        sumary s = new sumary();
        s.start();
        s.connect();
    }
}
