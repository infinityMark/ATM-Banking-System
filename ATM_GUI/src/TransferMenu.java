import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransferMenu extends JFrame {

    public static void main(String[] args) {
        JFrame mains = new JFrame();
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 10));

        RoundedButton selectionOneBtn = new RoundedButton("1 - Input receiver account number", "1 - Input receiver account number",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);
        selectionOneBtn.setHorizontalAlignment(SwingConstants.LEFT);

        RoundedButton selectionTwoBtn = new RoundedButton("2 - Exit", "2 - Exit",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Yellow.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);
        selectionTwoBtn.setHorizontalAlignment(SwingConstants.LEFT);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel menuTitle = new JLabel("Transfer Menu:");
        menuTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        menuTitle.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        JLabel notification = new JLabel("Choose a function: ");
        notification.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        notification.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        JLabel errorMessage = new JLabel("Choose a function: ");
        notification.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        notification.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        selectionOneBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selectionOneBtn.setHorizontalAlignment(SwingConstants.CENTER);
//                selectionOneBtn.setHorizontalAlignment();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                selectionOneBtn.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });

        selectionTwoBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                selectionTwoBtn.setHorizontalAlignment(SwingConstants.CENTER);
//                selectionOneBtn.setHorizontalAlignment();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                selectionTwoBtn.setHorizontalAlignment(SwingConstants.LEFT);
            }
        });


        panel.add(menuTitle);
        panel.add(selectionOneBtn);
        panel.add(selectionTwoBtn);
        panel.add(notification);


        mains.add(panel);
        mains.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mains.setSize(400, 400);
        mains.setLocationRelativeTo(null);
        mains.setVisible(true);
    }
}