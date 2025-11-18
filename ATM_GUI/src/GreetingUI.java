import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GreetingUI {
    private static JPanel mainPanel;

    static protected GridBagConstraints createDefaultGridBagConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        return gbc;
    }

    static protected JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    static protected JPanel reminderUI(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        JLabel label = createStyledLabel("Take out your card after operation", ATMUI.NORMAL_FONT, StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel imageLabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/atm.png")));
        panel.add(label, gbc);
        panel.add(imageLabel,gbc);

        return panel;
    }

    static protected JPanel createGreetingUI(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = createDefaultGridBagConstraints();
        gbc.insets = new Insets(10, 15, 10, 15);

        JLabel label = createStyledLabel("Welcome to use ATM system", ATMUI.NORMAL_FONT, StandardColor.GreyHighest.getColor(1));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel imageLabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/atm-machine.png")));
        panel.add(label, gbc);
        panel.add(imageLabel,gbc);

        return panel;
    }

    static public void goToPanel(String name) {
        Container parent = mainPanel.getParent();
        if (parent != null) {
            Container current = parent;
            while (current != null && !(current.getLayout() instanceof CardLayout)) {
                current = current.getParent();
            }

            if (current != null) {
                CardLayout layout = (CardLayout) current.getLayout();
                layout.show(current, name);
            }
        }
    }

    static public JPanel GreetingUI() {
        mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = createDefaultGridBagConstraints();

        JLabel taskTitle = createStyledLabel("Transfer", TransferUI.FONT_TITLE_LARGE, StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JPanel atmPhoto = createGreetingUI();
        JPanel reminderPanel = reminderUI();

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.weighty = 1;
        mainPanel.add(atmPhoto, gbc);
        gbc.gridx = 1;
        mainPanel.add(reminderPanel, gbc);

        mainPanel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                goToPanel(ATMUI.LOGIN_PANEL);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        return mainPanel;
    }

//    public static void main(String[] args) {
//        JFrame jFrame = new JFrame();
//        mainPanel = GreetingUI();
//        jFrame.add(mainPanel);
//
//        jFrame.setTitle("ATM 系统");
//        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        jFrame.setSize(1600, 1600);
//        jFrame.setLocationRelativeTo(null);
//        jFrame.setResizable(false);
//
//        jFrame.setVisible(true);
//    }
}