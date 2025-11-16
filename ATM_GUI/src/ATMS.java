// ATM.java
import javax.swing.*;
import java.awt.*;

public class ATMS extends JFrame {
    private boolean userAuthenticated;
    private int currentAccountNumber;
    private Screen screen;
    private Keypad keypad;
    private CashDispenser cashDispenser;
    private BankDatabase bankDatabase;

    // 布局管理
    private CardLayout cardLayout;
    private JPanel mainContainer;

    // 面板常量
    private static final String LOGIN_PANEL = "LOGIN";
    private static final String MAIN_MENU_PANEL = "MAIN_MENU";
    private static final String BALANCE_PANEL = "BALANCE";
    private static final String WITHDRAWAL_PANEL = "WITHDRAWAL";
    private static final String TRANSFER_PANEL = "TRANSFER";

    // 事务常量
    private static final int BALANCE_INQUIRY = 1;
    private static final int WITHDRAWAL = 2;
    private static final int TRANSFER = 3;
    private static final int EXIT = 5;
    private static final int RECORD = 4;

    public ATMS() {
        userAuthenticated = false;
        currentAccountNumber = 0;
        screen = new Screen();
        keypad = new Keypad();
        cashDispenser = new CashDispenser();
        bankDatabase = new BankDatabase();

        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("ATM GUI implement");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon icon = new ImageIcon(ClassLoader.getSystemResource("resources/atm-machine.png"));
        setIconImage(icon.getImage());

        // 设置窗口大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final double rate = 0.9;
        int screenWidth = (int) (screenSize.width * rate);
        int screenHeight = (int) (screenSize.height * rate);
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null);

        // 使用CardLayout管理面板切换
        cardLayout = new CardLayout();
        mainContainer = new JPanel(cardLayout);

        // 初始化所有面板
        initializePanels();

        // 设置主布局
        setLayout(new BorderLayout());
        add(mainContainer, BorderLayout.CENTER);

        // 默认显示登录面板
        showPanel(LOGIN_PANEL);
        setVisible(true);
    }

    private void initializePanels() {
        // 登录面板
        JPanel loginPanel = createLoginPanel();
        mainContainer.add(loginPanel, LOGIN_PANEL);

        // 主菜单面板
        JPanel mainMenuPanel = createMainMenuPanel();
        mainContainer.add(mainMenuPanel, MAIN_MENU_PANEL);

        // 其他面板占位符（会在需要时动态创建）
        mainContainer.add(new JPanel(), BALANCE_PANEL);
        mainContainer.add(new JPanel(), WITHDRAWAL_PANEL);
        mainContainer.add(new JPanel(), TRANSFER_PANEL);
    }

    // 面板切换方法
    public void showPanel(String panelName) {
        cardLayout.show(mainContainer, panelName);
    }

    // 创建登录面板
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // 标题
        JLabel titleLabel = new JLabel("ATM Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        // 账号输入
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        JTextField accountField = new JTextField(15);
        panel.add(accountField, gbc);

        // 密码输入
        gbc.gridy = 2; gbc.gridx = 0;
        panel.add(new JLabel("PIN:"), gbc);

        gbc.gridx = 1;
        JPasswordField pinField = new JPasswordField(15);
        panel.add(pinField, gbc);

        // 登录按钮
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
            try {
                int accountNumber = Integer.parseInt(accountField.getText());
                int pin = Integer.parseInt(new String(pinField.getPassword()));

                if (bankDatabase.authenticateUser(accountNumber, pin)) {
                    userAuthenticated = true;
                    currentAccountNumber = accountNumber;
                    showPanel(MAIN_MENU_PANEL);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid account number or PIN");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers");
            }
        });
        panel.add(loginButton, gbc);

        return panel;
    }

    // 创建主菜单面板
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 标题
        JLabel titleLabel = new JLabel("Main Menu", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // 功能按钮
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // 余额查询按钮
        JButton balanceButton = new JButton("View My Balance");
        balanceButton.addActionListener(e -> showBalanceInquiry());
        buttonPanel.add(balanceButton);

        // 取款按钮
        JButton withdrawButton = new JButton("Withdraw Cash");
        withdrawButton.addActionListener(e -> showWithdrawal());
        buttonPanel.add(withdrawButton);

        // 转账按钮
        JButton transferButton = new JButton("Transfer Funds");
        transferButton.addActionListener(e -> showTransfer());
        buttonPanel.add(transferButton);

        // 交易记录按钮
        JButton historyButton = new JButton("Transaction History");
        historyButton.addActionListener(e -> showTransactionHistory());
        buttonPanel.add(historyButton);

        // 退出按钮
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            userAuthenticated = false;
            currentAccountNumber = 0;
            showPanel(LOGIN_PANEL);
        });
        buttonPanel.add(exitButton);

        panel.add(buttonPanel, BorderLayout.CENTER);
        return panel;
    }

    // 显示余额查询
    private void showBalanceInquiry() {
        BalanceInquiry balanceInquiry = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);

        // 创建GUI面板
        JPanel balancePanel = createBalancePanel(balanceInquiry);

        // 动态更新面板
        updateDynamicPanel(BALANCE_PANEL, balancePanel);
        showPanel(BALANCE_PANEL);
    }

    // 显示取款界面
    private void showWithdrawal() {
        Withdrawal withdrawal = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);

        // 创建GUI面板（你需要为Withdrawal类添加类似的GUI方法）
        JPanel withdrawalPanel = createWithdrawalPanel(withdrawal);

        updateDynamicPanel(WITHDRAWAL_PANEL, withdrawalPanel);
        showPanel(WITHDRAWAL_PANEL);
    }

    // 显示转账界面
    private void showTransfer() {
        Transfer transfer = new Transfer(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);

        // 直接使用Transfer类继承的getPanelUI()方法
        JPanel transferPanel = transfer.getPanelUI();

        updateDynamicPanel(TRANSFER_PANEL, transferPanel);
        showPanel(TRANSFER_PANEL);
    }

    // 显示交易记录
    private void showTransactionHistory() {
        // 执行原有的命令行逻辑
        TransactionHistory.checkHistory(currentAccountNumber);

        // 也可以创建GUI版本
        JPanel historyPanel = createHistoryPanel();
        updateDynamicPanel("HISTORY", historyPanel);
        showPanel("HISTORY");
    }

    // 动态更新面板内容
    private void updateDynamicPanel(String panelName, JPanel newPanel) {
        // 移除旧面板
        Component[] components = mainContainer.getComponents();
        for (Component comp : components) {
            if (comp.getName() != null && comp.getName().equals(panelName)) {
                mainContainer.remove(comp);
                break;
            }
        }

        // 添加新面板
        newPanel.setName(panelName);
        mainContainer.add(newPanel, panelName);
    }

    // 创建余额查询面板
    private JPanel createBalancePanel(BalanceInquiry inquiry) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        // 执行查询获取数据
        inquiry.execute(); // 这会更新数据库状态，我们可以在GUI中显示结果

        // 从数据库获取最新余额
        double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
        double totalBalance = bankDatabase.getTotalBalance(currentAccountNumber);

        JLabel titleLabel = new JLabel("Balance Inquiry", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel balancePanel = new JPanel(new GridLayout(2, 1, 10, 10));
        balancePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        balancePanel.add(createBalanceItem("Available Balance:", String.format("$%.2f", availableBalance)));
        balancePanel.add(createBalanceItem("Total Balance:", String.format("$%.2f", totalBalance)));

        panel.add(balancePanel, BorderLayout.CENTER);

        // 返回按钮
        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> showPanel(MAIN_MENU_PANEL));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBalanceItem(String label, String value) {
        JPanel itemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel nameLabel = new JLabel(label);
        nameLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 18));
        valueLabel.setForeground(Color.BLUE);

        itemPanel.add(nameLabel);
        itemPanel.add(valueLabel);
        return itemPanel;
    }

    // 创建取款面板（类似余额查询）
    private JPanel createWithdrawalPanel(Withdrawal withdrawal) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Withdrawal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // 这里可以添加取款的具体GUI组件
        JLabel infoLabel = new JLabel("Withdrawal functionality will be implemented here", SwingConstants.CENTER);
        panel.add(infoLabel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> showPanel(MAIN_MENU_PANEL));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // 创建交易记录面板
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Transaction History", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));
        panel.add(titleLabel, BorderLayout.NORTH);

        // 这里可以显示交易记录
        JTextArea historyArea = new JTextArea(10, 30);
        historyArea.setEditable(false);
        // 可以调用TransactionHistory的方法来填充内容

        panel.add(new JScrollPane(historyArea), BorderLayout.CENTER);

        JButton backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> showPanel(MAIN_MENU_PANEL));
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    // 原有的run方法可以保留用于命令行模式
    public void run() {
        // 原有的命令行逻辑
        while (true) {
            while (!userAuthenticated) {
                screen.displayMessageLine("\nWelcome!");
                authenticateUser();
            }

            performTransactions();
            userAuthenticated = false;
            currentAccountNumber = 0;
            screen.displayMessageLine("\nThank you! Goodbye!");
        }
    }

    // 原有的命令行方法保持不变
    private void authenticateUser() {
        screen.displayMessage("\nPlease enter your account number: ");
        int accountNumber = keypad.getInput();
        screen.displayMessage("\nEnter your PIN: ");
        int pin = keypad.getInput();

        userAuthenticated = bankDatabase.authenticateUser(accountNumber, pin);

        if (!userAuthenticated) {
            screen.displayMessageLine("Invalid account number or PIN. Please try again.");
        }

        currentAccountNumber = accountNumber;
    }

    private void performTransactions() {
        Transaction currentTransaction;
        boolean userExited = false;

        while (!userExited) {
            int mainMenuSelection = displayMainMenu();

            switch (mainMenuSelection) {
                case BALANCE_INQUIRY:
                case WITHDRAWAL:
                case TRANSFER:
                    currentTransaction = createTransaction(mainMenuSelection);
                    currentTransaction.execute();
                    break;
                case RECORD:
                    TransactionHistory.checkHistory(currentAccountNumber);
                    break;
                case EXIT:
                    screen.displayMessageLine("\nExiting the system...");
                    userExited = true;
                    break;
                default:
                    screen.displayMessageLine("\nYou did not enter a valid selection. Try again.");
                    break;
            }
        }
    }

    private int displayMainMenu() {
        // 原有的显示主菜单逻辑
        String accountType = bankDatabase.getAccountType(currentAccountNumber);

        screen.displaySymbolicLine('-', 40);
        screen.displayMessageLine("Today is " + screen.currentDay());
        screen.displayMessageLine("\nMain menu:");
        screen.displayMessageLine(accountType);

        if (accountType.equals("Saving Account")) {
            double interestRate = bankDatabase.getRateOrLimit(currentAccountNumber);
            screen.displayMessageLine("Interest Rate: " + String.format("%.2f%%", interestRate * 100) + " per annum");
        }

        if (accountType.equals("Cheque Account")) {
            double chequeLimit = bankDatabase.getRateOrLimit(currentAccountNumber);
            screen.displayMessageLine("Limit per Cheque: " + String.format("%.2f", chequeLimit));
        }

        screen.displayMessageLine("\n1 - View my balance");
        screen.displayMessageLine("2 - Withdraw cash");
        screen.displayMessageLine("3 - Transfer funds");
        screen.displayMessageLine("4 - Show transaction history");
        screen.displayMessageLine("5 - Exit\n");
        screen.displaySymbolicLine('-', 40);
        screen.displayMessage("Enter a choice: ");
        return keypad.getInput();
    }

    private Transaction createTransaction(int type) {
        Transaction temp = null;
        switch (type) {
            case BALANCE_INQUIRY:
                temp = new BalanceInquiry(currentAccountNumber, screen, bankDatabase);
                break;
            case WITHDRAWAL:
                temp = new Withdrawal(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
            case TRANSFER:
                temp = new Transfer(currentAccountNumber, screen, bankDatabase, keypad, cashDispenser);
                break;
        }
        return temp;
    }
}