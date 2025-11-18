import javax.swing.*;
import java.awt.*;

public class GreetingUI {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
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

        return mainPanel;
    }

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        mainPanel = GreetingUI();
        jFrame.add(mainPanel);

        jFrame.setTitle("ATM 系统"); // 设置窗口标题
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭时退出程序
        jFrame.setSize(1600, 1600); // 设置窗口大小
        jFrame.setLocationRelativeTo(null); // 窗口居中显示
        jFrame.setResizable(false); // 禁止调整大小（可选）

        jFrame.setVisible(true);
    }
}