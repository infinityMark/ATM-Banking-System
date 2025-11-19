import javax.swing.*;
import java.awt.*;

public class ATMUI {
    // ------------- Panel name constants -------------
    public static final String GREETING_PANEL = "greeting";
    public static final String LOGIN_PANEL = "login";
    public static final String MAIN_MENU_PANEL = "mainMenu";
    public static final String BALANCE_PANEL = "balance";
    public static final String WITHDRAW_PANEL = "withdraw";
    public static final String TRANSFER_PANEL = "transfer";
    public static final String HISTORY_PANEL = "history";
    public static final String KEYPAD_PANEL = "keypad";
    public static final String test = "test";

    private String currentPanelName;

    // ------------- Instance GUI --------------------
    private LoginGUI loginGUI;
    private MainMenuGUI mainMenuGUI;

    // Array to store all panels
    private JPanel[] allPanels = new JPanel[20];
    private int panelCount = 0;

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

    // private JButton confirmButton;

    // Right side panel buttons
    private JButton Button5;
    private JButton Button6;
    private JButton Button7;

    public boolean isAutoSize = false;

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JPanel contentPanel = new JPanel();
    JLabel taskTitle = new JLabel();

    protected static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static final Font NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
    protected static final Font SMALL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    public ATMUI() {
        // initializeUI();
    }

    public ATMUI(String methodName) {

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

        try {
            ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("resources/atm-machine.png"));
            mainframe.setIconImage(icon.getImage());
        } catch (NullPointerException nullPointerException) {
            System.out.println("Invalid image path");
        }

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
        leftButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        leftButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftButtonPanel.setName("leftButtonPanel");
        registerPanel(leftButtonPanel);

        // center content panel (large proportion, using CardLayout)
        centerCardLayout = new CardLayout();
        centerPanel = new JPanel(centerCardLayout);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // right button panel (small proportion)
        rightButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightButtonPanel.setName("rightButtonPanel");
        registerPanel(rightButtonPanel);

        // Set grid layout constraints
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

        // left panel occupies 20%
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        mainUpperPanel.add(leftButtonPanel, gbc);

        // center panel occupies 60%
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainUpperPanel.add(centerPanel, gbc);

        // right panel occupies 20%
        gbc.gridx = 2;
        gbc.weightx = 0.15;
        mainUpperPanel.add(rightButtonPanel, gbc);

        // lower keypad panel
        lowerPanel = createKeypadPanel();
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Keypad"));
        lowerPanel.setPreferredSize(new Dimension(750, 200));
    }

    protected void addButtonsToSidePanels() {
        // initialize left buttons, change if needed
        Button1 = new JButton(" ");
        Button1.setName("Left1");
        Button2 = new JButton(" ");
        Button2.setName("Left2");
        Button3 = new JButton(" ");
        Button3.setName("Left3");
        // Button4 = new JButton("Left4");
        // Button4.setName("Left4");

        // initialize right buttons, change if needed
        Button5 = new JButton(" ");
        Button5.setName("Right1");
        Button6 = new JButton(" ");
        Button6.setName("Right2");
        Button7 = new JButton("跳过登录(直达Main menu)");
        Button7.setName("Right3");
        // Button8 = new JButton("Right4");
        // Button8.setName("Right4");

        // left buttons
        leftButtonPanel.add(Button1);
        leftButtonPanel.add(Button2);
        leftButtonPanel.add(Button3);
        // leftButtonPanel.add(Button4);

        // right buttons
        rightButtonPanel.add(Button5);
        rightButtonPanel.add(Button6);
        rightButtonPanel.add(Button7);
        // rightButtonPanel.add(Button8);
    }

    private void insertToCenterPanel() {
        JPanel greetingPanel = createGreetingPanel();
        centerPanel.add(greetingPanel, LOGIN_PANEL);
        registerPanel(greetingPanel);

        JPanel loginPanel = createLoginPanel();
        centerPanel.add(loginPanel, LOGIN_PANEL);
        registerPanel(loginPanel);

        JPanel mainMenuPanel = createMainMenuPanel();
        centerPanel.add(mainMenuPanel, MAIN_MENU_PANEL);
        registerPanel(mainMenuPanel);

        JPanel balancePanel = createBalancePanel();
        centerPanel.add(balancePanel, BALANCE_PANEL);
        registerPanel(balancePanel);

        JPanel withdrawPanel = createWithdrawPanel();
        centerPanel.add(withdrawPanel, WITHDRAW_PANEL);
        registerPanel(withdrawPanel);

        JPanel transferPanel = createTransferPanel();
        centerPanel.add(transferPanel, TRANSFER_PANEL);
        registerPanel(transferPanel);

        JPanel historyPanel = createHistoryPanel();
        centerPanel.add(historyPanel, HISTORY_PANEL);
        registerPanel(historyPanel);
    }

    // Method to register panels
    public void registerPanel(JPanel panel) {
        if (panelCount < allPanels.length) {
            allPanels[panelCount] = panel;
            panelCount++;
        }
    }

    // -------------functional panels creation methods-------------

    protected JPanel createGreetingPanel() {
        GreetingUI greetingUI = new GreetingUI();
        JPanel panel = greetingUI.getMainPanel();
        panel.setName(GREETING_PANEL);
        return panel;
    }

    protected JPanel createLoginPanel() {
        loginGUI = new LoginGUI(true);
        JPanel panel = loginGUI.createMainMenuPanels();
        panel.setName(LOGIN_PANEL);

        JButton confirmButton = new JButton();
        confirmButton.setName("Confirm");
        confirmButton = new JButton("Confirm");
        confirmButton.setName(test);
        return panel;
    }

    protected JPanel createMainMenuPanel() {
        mainMenuGUI = new MainMenuGUI(true, 21111);
        JPanel panel = mainMenuGUI.getMainP();
        panel.setName(MAIN_MENU_PANEL);
        return panel;
    }

    // method of createBalancePanel
    protected JPanel createBalancePanel() {
        // default balance panel with account number 0
        BalanceInquiryUI balancePanel = new BalanceInquiryUI(0);
        balancePanel.setName(BALANCE_PANEL);

        // Add action listener for the back button
        balancePanel.getBackButton().addActionListener(e -> {
            switchPanel(MAIN_MENU_PANEL);
        });

        return balancePanel;
    }

    public JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        return panel;
    }

    // update the balance panel with the current user's account can't check error
    public void updateBalancePanel(int accountNumber) {
        JPanel balancePanel = getPanel(BALANCE_PANEL);
        if (balancePanel instanceof BalanceInquiryUI) {
            ((BalanceInquiryUI) balancePanel).setAccountNumber(accountNumber);
        }
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

    protected JPanel createTransferPanel() {
        TransferUI transferUI = new TransferUI(21111, screen, bankDatabase, atmKeypad, atmCashDispenser);
        JPanel panel = transferUI.transferLayout();
        panel.setName(TRANSFER_PANEL);
        transferUI.resetToInitialState();
        return panel;
    }

    protected JPanel createKeypadPanel() {
        JPanel panel = new JPanel(new GridLayout(4, 4, 5, 5)); // 4行3列网格
        panel.setName(KEYPAD_PANEL);
        registerPanel(panel);

        String[] keys = { "7", "8", "9", "Confirm", "4", "5", "6", "Delete", "1", "2", "3", "Clear", "0", ".", "00",
                "" };
        for (String key : keys) {
            RoundedButton btn = new RoundedButton(key);
            btn.setFont(ATMUI.NORMAL_FONT);
            btn.setName(key.toLowerCase());
            panel.add(btn);
        }
        return panel;
    }

    // ------------- Waiting for improvement -------------
    Screen screen = new Screen();
    BankDatabase bankDatabase = new BankDatabase();
    Keypad atmKeypad;
    CashDispenser atmCashDispenser;

    // -------------Getter&Setter methods-------------

    public JPanel getPanel(String panelName) {
        for (int i = 0; i < panelCount; i++) {
            JPanel panel = allPanels[i];
            if (panel != null && panelName.equals(panel.getName())) {
                return panel;
            }
        }
        return null; // 没找到返回null
    }

    // switch center panel by name
    public void switchPanel(String name) {
        JPanel targetPanel = getPanel(name);
        if (targetPanel != null) {
            this.currentPanelName = name;
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

    public LoginGUI getLoginGUI() {
        return loginGUI;
    }

    public String getCurrentPanelName() {
        return currentPanelName;
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
            // case 4:
            // return Button4;
            case 5:
                return Button5;
            case 6:
                return Button6;
            case 7:
                return Button7;
            // case 8:
            // return Button8;
            default:
                return null;
        }
    }

    public JPanel getPanelUI() {
        return mainPanel;
    }

    public void passInformation(String information) {
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

    protected RoundedButton createStyledButton(String content, String changedContent, Color defaultBackgroundColor,
            Color changedBackgroundColor, Color defaultFontColor,
            Color changedFontColor, Font fontDefaultStyle, Font fontChangedSize, boolean roundedStatus, int widths,
            int heights) {
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