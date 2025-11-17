import javax.swing.*;
import java.awt.*;

public class ATMUI {
    // ------------- Panel name constants -------------
    public static final String LOGIN_PANEL = "login";
    public static final String MAIN_MENU_PANEL = "mainMenu";
    public static final String BALANCE_PANEL = "balance";
    public static final String WITHDRAW_PANEL = "withdraw";
    public static final String TRANSFER_PANEL = "transfer";
    public static final String KEYPAD_PANEL = "keypad";

    // Main frame and panels

    private CardLayout centerCardLayout;
    private JPanel mainUpperPanel;
    private JPanel leftButtonPanel;
    private JPanel centerPanel;
    private JPanel rightButtonPanel;
    private JPanel lowerPanel;// keypad panel
    private JFrame mainframe;

    // ------------- Side panel buttons -------------

    // left side panel buttons
    private JButton Button1;
    private JButton Button2;
    private JButton Button3;
    private JButton Button4;

    // Right side panel buttons
    private JButton Button5;
    private JButton Button6;
    private JButton Button7;
    private JButton Button8;

    public boolean isAutoSize = false;

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JPanel contentPanel = new JPanel();
    JLabel taskTitle = new JLabel();

    protected static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    protected static final Font NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
    protected static final Font SMALL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    public ATMUI() {
        // initializeUI();
    }

    public ATMUI(String methodName){
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        taskTitle.setText(methodName);
        taskTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        taskTitle.setForeground(StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.weighty = 0.1;
        mainPanel.add(taskTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(contentPanel, gbc);
    }

    public void initializeUI() {
        baseSetup();
        mainframe.setLocationRelativeTo(null);

        JPanel mainContainer = new JPanel(new BorderLayout(0, 10));
        createUpperAndLowerPanels();
        addButtonsToSidePanels();
        insertToCenterPanel();

        // Assemble the main container
        mainContainer.add(mainUpperPanel, BorderLayout.CENTER);
        mainContainer.add(lowerPanel, BorderLayout.SOUTH);
        mainframe.add(mainContainer);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("resources/atm-machine.png"));
        mainframe.setIconImage(icon.getImage());

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double rate = 0.9;
        int screenWidth = (int) (screenSize.width * rate);
        int screenHeight = (int) (screenSize.height * rate);
        mainframe.setSize(screenWidth, screenHeight);
        mainframe.setLocationRelativeTo(null);

        mainframe.setVisible(true);
    }

    private void baseSetup() {
        mainframe = new JFrame("ATM System");
        if (isAutoSize) {
            mainframe.pack();
        } else {
            mainframe.setSize(600, 800);
        }
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createUpperAndLowerPanels() {
        // upper Panel with GridBagLayout
        mainUpperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainUpperPanel.setBorder(BorderFactory.createTitledBorder("Function Area"));

        // left button panel (small proportion)
        leftButtonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        leftButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // center content panel (large proportion, using CardLayout)
        centerCardLayout = new CardLayout();
        centerPanel = new JPanel(centerCardLayout);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // right button panel (small proportion)
        rightButtonPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Set grid layout constraints
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // left panel occupies 20%
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        mainUpperPanel.add(leftButtonPanel, gbc);

        // center panel occupies 60%
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        mainUpperPanel.add(centerPanel, gbc);

        // right panel occupies 20%
        gbc.gridx = 2;
        gbc.weightx = 0.2;
        mainUpperPanel.add(rightButtonPanel, gbc);

        // lower keypad panel
        lowerPanel = createKeypadPanel();
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Keypad"));
        lowerPanel.setPreferredSize(new Dimension(750, 200));
    }

    protected void addButtonsToSidePanels() {
        // initialize left buttons, change if needed
        Button1 = new JButton("Left 1");
        Button2 = new JButton("Left 2");
        Button3 = new JButton("Left 3");
        Button4 = new JButton("Left 4");

        // initialize right buttons, change if needed
        Button5 = new JButton("Right 1");
        Button6 = new JButton("Right 2");
        Button7 = new JButton("Right 3");
        Button8 = new JButton("Right 4");

        // left buttons
        leftButtonPanel.add(Button1);
        leftButtonPanel.add(Button2);
        leftButtonPanel.add(Button3);
        leftButtonPanel.add(Button4);

        // right buttons
        rightButtonPanel.add(Button5);
        rightButtonPanel.add(Button6);
        rightButtonPanel.add(Button7);
        rightButtonPanel.add(Button8);
    }

    private void insertToCenterPanel() {
        // 功能面板应添加到中间的centerPanel（使用CardLayout的面板）
        centerPanel.add(createLoginPanel(), LOGIN_PANEL);
        centerPanel.add(createMainMenuPanel(), MAIN_MENU_PANEL);
        centerPanel.add(createBalancePanel(), BALANCE_PANEL);
        centerPanel.add(createWithdrawPanel(), WITHDRAW_PANEL);
        centerPanel.add(createTransferPanel(), TRANSFER_PANEL);
    }

    // -------------functional panels creation methods-------------

    protected JPanel createLoginPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName(LOGIN_PANEL);
        panel.add(new JLabel("a"));
        panel.add(new JTextField(15));
        panel.add(new JPasswordField(15));
        panel.add(new JButton("a"));
        return panel;
    }

    protected JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 1, 5, 5));
        panel.setName(MAIN_MENU_PANEL);
        panel.add(new JButton("View Balance"));
        panel.add(new JButton("Withdraw Cash"));
        panel.add(new JButton("Transfer Funds"));
        panel.add(new JButton("Exit"));
        return panel;
    }

    protected JPanel createBalancePanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName(BALANCE_PANEL);
        panel.add(new JLabel("Current Balance:"));
        panel.add(new JLabel("¥ 10000.00"));
        panel.add(new JButton("Back to Menu"));
        return panel;
    }

    protected JPanel createWithdrawPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setName(WITHDRAW_PANEL);
        panel.add(new JLabel("Amount:"));
        panel.add(new JTextField(10));
        panel.add(new JButton("Confirm"));
        panel.add(new JButton("Cancel"));
        return panel;
    }

    Screen screen = new Screen();
    BankDatabase bankDatabase = new BankDatabase();
    Keypad atmKeypad;
    CashDispenser atmCashDispenser;


    public void switchPanels(String name) {
        if (TRANSFER_PANEL.equals(name)) {
            Component[] components = centerPanel.getComponents();
            for (Component comp : components) {
                if (TRANSFER_PANEL.equals(comp.getName())) {
                    centerPanel.remove(comp);
                }
            }

            JPanel transferPanel = createTransferPanel();
            centerPanel.add(transferPanel, TRANSFER_PANEL);
        }

        centerCardLayout.show(centerPanel, name);
    }

    protected JPanel createTransferPanel() {
        JPanel panel = new TransferUI(21111, screen, bankDatabase, atmKeypad, atmCashDispenser).transferLayout();
        panel.setName(TRANSFER_PANEL);
        return panel;
    }

    protected JPanel createKeypadPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 3, 5, 5)); // 4行3列网格
        panel.setName(KEYPAD_PANEL);
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

    // -------------Getter&Setter methods-------------

    // get panel by name（从中间面板查找）
    public JPanel getPanel(String name) {
        for (Component comp : centerPanel.getComponents()) {
            if (comp.getName() != null && comp.getName().equals(name)) {
                return (JPanel) comp;
            }
        }
        return null;
    }

    // switch center panel by name（切换中间面板）
    public void switchPanel(String name) {
        JPanel targetPanel = getPanel(name);
        if (targetPanel != null) {
            centerCardLayout.show(centerPanel, name); // CardLayout作用于centerPanel
        } else {
            // Error handling: panel not found
            System.err.println("Error: Panel with name '" + name + "' does not exist.");
        }
    }

    // get main frame
    public JFrame getMainFrame() {
        return mainframe;
    }

    // get lower keypad panel
    public JPanel getLowerPanel() {
        return lowerPanel;
    }

    // Get left button panel
    public JPanel getLeftButtonPanel() {
        return leftButtonPanel;
    }

    // Get right button panel
    public JPanel getRightButtonPanel() {
        return rightButtonPanel;
    }

    // get current visible upper panel
    public JPanel getCurrentUpperPanel() {
        for (Component comp : centerPanel.getComponents()) {
            if (comp.isVisible()) {
                return (JPanel) comp;
            }
        }
        return null;
    }

    // ------------- Side panel buttons getters -------------

    public JButton getButton(int index) {
        switch (index) {
            case 1:
                return Button1;
            case 2:
                return Button2;
            case 3:
                return Button3;
            case 4:
                return Button4;
            case 5:
                return Button5;
            case 6:
                return Button6;
            case 7:
                return Button7;
            case 8:
                return Button8;
            default:
                return null;
        }
    }

    public JPanel getPanelUI(){
        return mainPanel;
    }

    public void passInformation(String information){
        taskTitle.setText(information);
        taskTitle.revalidate();
        taskTitle.repaint();
    }

    public void goBackToMainPanel() {
        Container parent = mainPanel.getParent();
        if (parent != null) {
            Container current = parent;
            while (current != null && !(current.getLayout() instanceof CardLayout)) {
                current = current.getParent();
            }

            if (current != null) {
                CardLayout layout = (CardLayout) current.getLayout();
                layout.show(current, "mainMenu");
            }
        }
    }

    protected RoundedButton createStyledButton(String content,String changedContent,Color defaultBackgroundColor,Color changedBackgroundColor,Color defaultFontColor,
                                               Color changedFontColor,Font fontDefaultStyle,Font fontChangedSize, boolean roundedStatus,int widths,int heights) {
        RoundedButton button = new RoundedButton(content, changedContent,
                defaultBackgroundColor,
                changedBackgroundColor,
                defaultFontColor,
                changedFontColor,
                fontDefaultStyle,
                fontChangedSize, roundedStatus, widths, heights);

        button.setHorizontalAlignment(SwingConstants.LEFT);
        return button;
    }

}