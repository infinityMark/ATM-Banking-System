import javax.swing.*;
import java.awt.*;

public class SignIn {
    public static void main(String[] args){
        JFrame frame = new JFrame("Sign In or Enrollment demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting icon for window
        ImageIcon icon = new ImageIcon(interfaces.class.getResource("/coaching.png"));
        frame.setIconImage(icon.getImage());

        //get screen size,after then set window size to 80% of screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();

        // obtain screen size and setting
        final double rate = 0.6;
        int screenWidth = (int) Math.round(dm.getWidth()*rate);
        int screenHeight = (int) Math.round(dm.getHeight()*rate);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);//make it be center
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 5;
        gbc.gridy = 10;
        gbc.insets = new Insets(10, 10, 10, 10); // 外边距

        /*

        frame setting finish before this comment!

         */



        DivComponent signIn = new DivComponent(StandardColor.GreyHighest.getColorMode(), StandardColor.Blue.getColorMode());
        signIn.setPreferredSize(new Dimension(500,200));

        JLabel a = new JLabel("Sign in");
        signIn.add(a);
        frame.add(signIn,gbc);

        frame.setVisible(true);
    }
}