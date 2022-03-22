import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class back extends JFrame{
    private JPanel Main;
    private JButton button1;
    private JButton addmenu;

    public back() {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sumary s  = new sumary();
                s.start();
                s.connect();
            }
        });
        addmenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Addmenu a = new Addmenu();
                a.start();
                a.connect();
            }
        });
    }
    public void start() {
        setSize(300, 400);
        setVisible(true);
        setContentPane(Main);
    }
    public static void main(String[] args){
        back b = new back();
        b.start();
    }
}
