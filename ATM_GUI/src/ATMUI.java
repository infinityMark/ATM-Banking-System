import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionListener;
import java.net.URL;

public class ATMUI extends ATMUIController {
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

    protected static final Font FONT_TITLE_LARGE = new Font(Font.SANS_SERIF, Font.BOLD, 45);
    protected static final Font FONT_NORMAL = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    protected static final Font FONT_SMALL = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    protected static final Font FONT_BUTTON = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    // ------------- Instance GUI --------------------
    private String currentPanelName = GREETING_PANEL;
    public JPanel keypadPanel;
    public GreetingUI greetingGUI;
    public LoginGUI loginGUI;
    public MainMenuGUI mainMenuGUI;
    public TransactionHistoryUI historyGUI;
    public WithdrawalUI withdrawGUI;
    public BalanceInquiryUI balanceGUI;
    public TransferUI transferGUI;

    // Array to store all panels
    private JPanel[] allPanels = new JPanel[20];
    private int panelCount = 0;

    // Main frame and panels

    private CardLayout centerCardLayout;
    private JPanel mainUpperPanel;
    //private JPanel leftButtonPanel;
    private JPanel centerPanel;
    //private JPanel rightButtonPanel;
    private JPanel lowerPanel;
    private JFrame mainframe;

    // ------------- Side panel buttons -------------
/* 
    // left side panel buttons
    private JButton Button1;
    private JButton Button2;
    private JButton Button3;

    // Right side panel buttons
    private JButton Button5;
    private JButton Button6;
    private JButton Button7;

    private JButton[] rightButton = new JButton[3];
    private JButton[] leftButton = new JButton[3];

    public JButton[] getLeftButton() {
        return leftButton;
    }

    public JButton[] getRightButton() {
        return rightButton;
    }
*/
    private ATMUIController controller;

    public ATMUIController getController() {
        return controller;
    }

    public void setController(ATMUIController controller) {
        this.controller = controller;
    }

    static private int currentAccountNumber;

    static public void setCurrentAccountNumber(int currentAccountNumber1) {
        currentAccountNumber = currentAccountNumber1;
    }

    static public int getCurrentAccountNumber() {
        return currentAccountNumber;
    }

    /*
     * protected JButton getButton1() {
     * return Button1;
     * }
     */

    // public boolean isAutoSize = false;

    JPanel mainPanel = new JPanel(new GridBagLayout());
    JPanel contentPanel = new JPanel();
    JLabel taskTitle = new JLabel();

    protected static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 40);
    public static final Font NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 30);
    protected static final Font SMALL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);

    public ATMUI() {
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

        

        JPanel mainContainer = new JPanel(new BorderLayout(0, 10));
        createUpperAndLowerPanels();
        //addButtonsToSidePanels();
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

        mainframe.setMinimumSize(new Dimension(800, 600));
        mainframe.setSize(1200, 900);
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        mainframe.setLocationRelativeTo(null);

    }

    private void createUpperAndLowerPanels() {
        // upper Panel with GridBagLayout
        mainUpperPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        mainUpperPanel.setBorder(BorderFactory.createTitledBorder("Function Area"));
/* 
        // left button panel (small proportion)
        leftButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        leftButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftButtonPanel.setName("leftButtonPanel");
        leftButtonPanel.setMinimumSize(new Dimension(100, 200));
        registerPanel(leftButtonPanel);
*/
        // center content panel (large proportion, using CardLayout)
        centerCardLayout = new CardLayout();
        centerPanel = new JPanel(centerCardLayout);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        centerPanel.setPreferredSize(new Dimension(900, 400));
/* 
        // right button panel (small proportion)
        rightButtonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        rightButtonPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightButtonPanel.setName("rightButtonPanel");
        rightButtonPanel.setMinimumSize(new Dimension(100, 200));
        registerPanel(rightButtonPanel);
*/
        // Set grid layout constraints
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;

/*         // left panel occupies 20%
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.15;
        mainUpperPanel.add(leftButtonPanel, gbc);
*/
        // center panel occupies 60%
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        mainUpperPanel.add(centerPanel, gbc);
/* 
        // right panel occupies 20%
        gbc.gridx = 2;
        gbc.weightx = 0.15;
        mainUpperPanel.add(rightButtonPanel, gbc);
*/
        // lower keypad panel
        lowerPanel = createKeypadPanel();
        lowerPanel.setBorder(BorderFactory.createTitledBorder("Keypad"));
        lowerPanel.setMinimumSize(new Dimension(0, 200));
        lowerPanel.setPreferredSize(new Dimension(750, 200));
    }

    static public TextFields createInputField(int fontSize) {
        return new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
    }

 /*    protected void addButtonsToSidePanels() {
        Button1 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_right.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button1.setName("Left1");
        Button2 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_right.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button2.setName("Left2");
        Button3 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_right.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button3.setName("Left3");

        // initialize right buttons, change if needed
        Button5 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_left.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button5.setName("Right1");
        Button6 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_left.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button6.setName("Right2");
        Button7 = new RoundedButton(setImageSafety(
                "https://raw.githubusercontent.com/infinityMark/SEHH2242-OOP_Group_Project_Part1_Group_101A-G03_UML_Showing/refs/heads/main/arrow_circle_left.png"),
                new Dimension(Integer.MAX_VALUE, 50));
        Button7.setName("Right3");

        // left buttons
        leftButtonPanel.add(Button1);
        leftButtonPanel.add(Button2);
        leftButtonPanel.add(Button3);

        // right buttons
        rightButtonPanel.add(Button5);
        rightButtonPanel.add(Button6);
        rightButtonPanel.add(Button7);

        leftButton[0] = Button1;
        leftButton[1] = Button2;
        leftButton[2] = Button3;

        rightButton[0] = Button5;
        rightButton[1] = Button6;
        rightButton[2] = Button7;
    }
*/
    private void insertToCenterPanel() {
        JPanel greetingPanel = createGreetingPanel();
        centerPanel.add(greetingPanel, GREETING_PANEL);
        registerPanel(greetingPanel);

        JPanel loginPanel = createLoginPanel();
        centerPanel.add(loginPanel, LOGIN_PANEL);
        registerPanel(loginPanel);
    }

    public void createPanelAfterLogin() {
        String[] transactionPanels = {
                MAIN_MENU_PANEL,
                BALANCE_PANEL,
                WITHDRAW_PANEL,
                TRANSFER_PANEL,
                HISTORY_PANEL
        };

        for (String panelName : transactionPanels) {
            removePanelIfExists(panelName);
        }

        if (getPanel(GREETING_PANEL) == null) {
            JPanel greetingPanel = createGreetingPanel();
            centerPanel.add(greetingPanel, GREETING_PANEL);
            registerPanel(greetingPanel);
        }
        if (getPanel(LOGIN_PANEL) == null) {
            JPanel loginPanel = createLoginPanel();
            centerPanel.add(loginPanel, LOGIN_PANEL);
            registerPanel(loginPanel);
        }

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

    public void disposePanel() {
        mainMenuGUI.getMainPanel().removeAll();
        balanceGUI.getMainPanel().removeAll();
        historyGUI.getMainPanel().removeAll();
        withdrawGUI.getMainPanel().removeAll();
        transferGUI.getMainPanel().removeAll();
    }

    // -------------functional panels creation methods-------------

    protected JPanel createGreetingPanel() {
        greetingGUI = new GreetingUI();
        JPanel panel = greetingGUI.getMainPanel();
        panel.setName(GREETING_PANEL);
        return panel;
    }

    protected JPanel createLoginPanel() {
        loginGUI = new LoginGUI(true);
        loginGUI.createMainMenuPanels();
        loginGUI.getMainPanel().setName(LOGIN_PANEL);

        return loginGUI.getMainPanel();
    }

    protected JPanel createMainMenuPanel() {
        mainMenuGUI = new MainMenuGUI();
        mainMenuGUI.createMainMenuGUI(true, loginGUI.getAccountNumber(),getController());
        mainMenuGUI.getMainP().setName(MAIN_MENU_PANEL);
        return mainMenuGUI.getMainP();
    }

    protected JPanel createBalancePanel() {
        balanceGUI = new BalanceInquiryUI(loginGUI.getAccountNumber(),
                // getLeftButton(), getRightButton(),
                getController());
        balanceGUI.getMainPanel().setName(BALANCE_PANEL);
        return balanceGUI.getMainPanel();
    }

    public JPanel createHistoryPanel() {
        historyGUI = new TransactionHistoryUI();
        historyGUI.createTransactionHistoryUI(loginGUI.getAccountNumber());
        historyGUI.getMainPanel().setName(HISTORY_PANEL);
        return historyGUI.getMainPanel();
    }

    public void refreshHistoryPanel() {
        if (historyGUI != null) {
            historyGUI.refreshHistory(getCurrentAccountNumber());
        }
    }

    protected JPanel createWithdrawPanel() {
        withdrawGUI = new WithdrawalUI(loginGUI.getAccountNumber(),
                // getLeftButton(), getRightButton(),
                getController());
        withdrawGUI.getMainPanel().setName(WITHDRAW_PANEL);
        return withdrawGUI.getMainPanel();
    }

    // ------------- Waiting for improvement -------------
    Screen screen = new Screen();
    BankDatabase bankDatabase = BankDatabase.getInstance();
    Keypad atmKeypad;
    CashDispenser atmCashDispenser;

    static public RoundedButton createActionButton(String text, Color backgroundColor) {
        return new RoundedButton(text, text,
                backgroundColor,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                new Font(Font.SANS_SERIF, Font.PLAIN, 20),
                true, 180, 50);
    }

    protected JPanel createTransferPanel() {
        transferGUI = new TransferUI(loginGUI.getAccountNumber(),bankDatabase, getController());
        transferGUI.getMainPanel().setName(TRANSFER_PANEL);
//        transferGUI.resetToInitialState();
        return transferGUI.getMainPanel();
    }

    protected JPanel createKeypadPanel() {
        keypadPanel = new JPanel(new GridLayout(4, 4, 5, 5)); // 4行3列网格
        keypadPanel.setName(KEYPAD_PANEL);
        registerPanel(keypadPanel);

        String[] keys = { "7", "8", "9", "Confirm", "4", "5", "6", "Delete", "1", "2", "3", "Clear", "0", ".", "00" ,"Back to Main menu"};
        for (String key : keys) {
            RoundedButton btn = new RoundedButton(key);
            btn.setFont(ATMUI.NORMAL_FONT);
            btn.setName(key.toLowerCase());
            keypadPanel.add(btn);
        }
        return keypadPanel;
    }

    // -------------Getter&Setter methods-------------

    public JPanel getPanel(String panelName) {
        for (int i = 0; i < panelCount; i++) {
            JPanel panel = allPanels[i];
            if (panel != null && panelName.equals(panel.getName())) {
                return panel;
            }
        }
        return null;

    }
/* 
    public void removeAllButtonListenersCompletely(JButton[] leftButton, JButton[] rightButton) {
        for (int i = 0; i < leftButton.length; i++) {
            if (leftButton[i] != null) {
                ActionListener[] listeners = leftButton[i].getActionListeners();
                for (ActionListener listener : listeners) {
                    leftButton[i].removeActionListener(listener);
                }
                System.out.println("左侧按钮 " + i + " 移除了 " + listeners.length + " 个监听器");
            }
        }

        // 清理右侧按钮
        for (int i = 0; i < rightButton.length; i++) {
            if (rightButton[i] != null) {
                ActionListener[] listeners = rightButton[i].getActionListeners();
                for (ActionListener listener : listeners) {
                    rightButton[i].removeActionListener(listener);
                }
                System.out.println("右侧按钮 " + i + " 移除了 " + listeners.length + " 个监听器");
            }
        }
        System.out.println("=== 清理完成 ===");
    }
*/
    // switch panel by name
    public void switchPanel(String name) {
        JPanel targetPanel = getPanel(name);
        if (targetPanel != null) {
            System.out.println(currentPanelName + " switch to " + name);// CardLayout作用于centerPanel
            this.currentPanelName = name;
            centerCardLayout.show(centerPanel, name);
            if (WITHDRAW_PANEL.equals(name) && withdrawGUI != null) {
                withdrawGUI.resetToInitialState();
            }
        } else {
            // Error handling: panel not found
            System.err.println("Error: Panel with name '" + name + "' does not exist.");
        }
    }

    public CardLayout getCenterCardLayout() {
        return centerCardLayout;
    }

    // get main frame
    public JFrame getMainFrame() {
        return mainframe;
    }
/* 
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
    }*/

    // get current visible upper panel
    /*public JPanel getCurrentUpperPanel() {
        for (Component comp : centerPanel.getComponents()) {
            if (comp.isVisible()) {
                return (JPanel) comp;
            }
        }
        return null;
    }*/

    public LoginGUI getLoginGUI() {
        return loginGUI;
    }

    public GreetingUI getGreetingUI() {
        return greetingGUI;
    }

    public MainMenuGUI getMainmenuGUI() {
        return mainMenuGUI;
    }

    public String getCurrentPanelName() {
        return currentPanelName;
    }

    // ------------- Side panel buttons getters -------------

    /*public JButton getButton(int index) {
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
    }*/

    // Function

    static public ImageIcon setImageSafety(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon icon = new ImageIcon(url);

            return icon;

        } catch (java.net.MalformedURLException e) {
            System.out.println("URL invalid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Image failure: " + e.getMessage());
        }
        return null;
    }

    static public void setImageSafety(JPanel panel, GridBagConstraints gbc, String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            ImageIcon icon = new ImageIcon(url);
            JLabel imageLabel = new JLabel(icon);
            panel.add(imageLabel, gbc);

            System.out.println("Successful");

        } catch (java.net.MalformedURLException e) {
            System.out.println("URL invalid: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Image failure: " + e.getMessage());
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

    private void removePanelIfExists(String panelName) {
        JPanel panel = getPanel(panelName);
        if (panel != null) {
            centerPanel.remove(panel);           
            for (int i = 0; i < panelCount; i++) {
                if (allPanels[i] == panel) {                  
                    for (int j = i; j < panelCount - 1; j++) {
                        allPanels[j] = allPanels[j + 1];
                    }
                    allPanels[panelCount - 1] = null;
                    panelCount--;
                    break;
                }
            }
        }
    }

}