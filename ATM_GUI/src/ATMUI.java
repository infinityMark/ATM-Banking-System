import javax.swing.*;
import java.awt.*;

public class ATMUI {
    private CardLayout leftCardLayout;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JFrame mainframe;
    public boolean isAutoSize = false;

    public ATMUI() {
        initializeUI();
    }

    private void initializeUI() {

        baseSetup();

        mainframe.setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel(new BorderLayout(10, 0));

        createLeftAndRightPanel();

        insertToLeftPanel();

        // Assemble the main container
        mainContainer.add(leftPanel, BorderLayout.CENTER);
        mainContainer.add(rightPanel, BorderLayout.EAST);

        // Add main container to the frame
        mainframe.add(mainContainer);

        mainframe.setVisible(true);
    }

    private void baseSetup() {
        mainframe = new JFrame("ATM System");
        if (isAutoSize) {
            mainframe.pack();
        } else {
            mainframe.setSize(800, 600);
        }
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createLeftAndRightPanel() {
        // Left Panel
        leftCardLayout = new CardLayout();
        leftPanel = new JPanel(this.leftCardLayout);
        leftPanel.setBorder(BorderFactory.createTitledBorder("Function Area"));

        // Right Panel
        rightPanel = createKeypadPanel();
        rightPanel.setBorder(BorderFactory.createTitledBorder("Keypad"));
    }

    private void insertToLeftPanel() {
        // Adding different function panels to the left panel
        leftPanel.add(createLoginPanel(), "login");
        leftPanel.add(createMainMenuPanel(), "mainMenu");
        leftPanel.add(createBalancePanel(), "balance");
        leftPanel.add(createWithdrawPanel(), "withdraw");
        leftPanel.add(createTransferPanel(), "transfer");

        /*
         * // choosing which panel to show based on chosenFunction
         * switch (chosenFunction) {
         * case 0:
         * leftCardLayout.show(leftPanel, "mainMenu");
         * break;
         * case 1:
         * leftCardLayout.show(leftPanel, "login");
         * break;
         * case 2:
         * leftCardLayout.show(leftPanel, "balance");
         * break;
         * case 3:
         * leftCardLayout.show(leftPanel, "withdraw");
         * break;
         * case 4:
         * leftCardLayout.show(leftPanel, "transfer");
         * break;
         * default:
         * // Default to showing the login panel
         * leftCardLayout.show(leftPanel, "login");
         * break;
         * }
         */
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName("login");
        panel.add(new JLabel("a"));
        panel.add(new JTextField(15));
        panel.add(new JPasswordField(15));
        panel.add(new JButton("a"));
        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setName("mainMenu");
        panel.add(new JButton("a"));
        panel.add(new JButton("a"));
        panel.add(new JButton("a"));
        panel.add(new JButton("a"));
        return panel;
    }

    private JPanel createBalancePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName("balance");
        panel.add(new JLabel("a"));
        panel.add(new JLabel("¥ 10000.00"));
        panel.add(new JButton("a"));
        return panel;
    }

    private JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName("withdraw");
        panel.add(new JLabel("a："));
        panel.add(new JTextField(10));
        panel.add(new JButton("a"));
        panel.add(new JButton("a"));
        return panel;
    }

    private JPanel createTransferPanel() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        panel.setName("transfer");
        panel.add(new JLabel("a："));
        panel.add(new JTextField(15));
        panel.add(new JLabel("a："));
        panel.add(new JTextField(10));
        panel.add(new JButton("a"));
        panel.add(new JButton("a"));
        return panel;
    }

    private JPanel createKeypadPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5)); // 4行3列网格
        panel.setName("keypad");
        panel.add(new JButton("1"));
        panel.add(new JButton("2"));
        panel.add(new JButton("3"));
        panel.add(new JButton("4"));
        panel.add(new JButton("5"));
        panel.add(new JButton("6"));
        panel.add(new JButton("7"));
        panel.add(new JButton("8"));
        panel.add(new JButton("9"));
        panel.add(new JButton("Clean"));
        panel.add(new JButton("0"));
        panel.add(new JButton("Confirm"));
        return panel;
    }

    // get panel by name
    public JPanel getPanel(String name) {
        for (Component comp : leftPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                return (JPanel) comp;
            }
        }
        return null;
    }

    // switch left panel by name
    public void switchPanel(String name) {
        leftCardLayout.show(leftPanel, name);
    }

    // get main frame
    public JFrame getMainFrame() {
        return mainframe;
    }

    // get right keypad panel
    public JPanel getRightPanel() {
        return rightPanel;
    }

    // get current visible left panel
    public JPanel getCurrentLeftPanel() {
        for (Component comp : leftPanel.getComponents()) {
            if (comp.isVisible()) {
                return (JPanel) comp;
            }
        }
        return null;
    }

    // For testing only
    /*
     * public static void main(String[] args) {
     * // new ATMUI(2);
     * }
     */

}