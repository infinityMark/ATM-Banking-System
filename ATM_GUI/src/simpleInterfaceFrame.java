import javax.swing.*;
import java.awt.*;

public class simpleInterfaceFrame extends JFrame{
    private JMenuBar menu;

    public simpleInterfaceFrame(){
        super("This is first user interface test demo");

        menu=new JMenuBar();
        add(menu);
    }
}