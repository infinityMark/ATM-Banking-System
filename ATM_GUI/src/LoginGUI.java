import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginGUI {
    private TextFields accounTF;
    private JPanel centerPanel;
    private Passwords passwordF;
    private JButton confirmButton, delButton, cancelButton;
    private String savedAccount;
    private boolean accountGot, accountPass, pwPass, invalidinput;
    private int currentAccountNumber, currentPin;
    private BankDatabase bankDatabase;
    private JLabel reminderL;
    private JPanel mainP;
    private static int passedAccount, passedPIN;

    public LoginGUI(boolean showLoginGUI) {
        // 使用 GridBagLayout 作为主布局
        mainP = new JPanel(new GridBagLayout());
        mainP.setBackground(new Color(0, 0, 139));

        centerPanel = new JPanel(new GridBagLayout());
        passwordF = new Passwords(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD, 30));
        accounTF = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD, 30));
        reminderL = new JLabel("Please Enter your Account number and PIN number ", SwingConstants.CENTER);

        setupMainPanel();

        accountGot = false;
        accountPass = false;
        pwPass = false;
        invalidinput = false;
        currentAccountNumber = 0;
        currentPin = 0;

        // 初始化按钮
        setupButtons();

        if (showLoginGUI) {
            SwingUtilities.invokeLater(() -> accounTF.requestFocusInWindow());
            mainP.setVisible(true);
        }
    }

    /**
     * 设置主面板的布局
     */
    private void setupMainPanel() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        // 顶部时间标签 - 第0行
        JLabel timeL = new JLabel("DATE: " + getTime(), SwingConstants.CENTER);
        font(timeL, 1, Color.WHITE, 20);
        timeL.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        timeL.setOpaque(true);
        backGroudColor(timeL);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1; // 顶部占10%高度
        gbc.insets = new Insets(5, 5, 5, 5);
        mainP.add(timeL, gbc);

        // 中心面板 - 第1行
        setupCenterPanel();
        gbc.gridy = 1;
        gbc.weighty = 0.7; // 中心占70%高度
        gbc.insets = new Insets(10, 20, 10, 20);
        mainP.add(centerPanel, gbc);

        // 按钮面板 - 第2行
        JPanel buttonPanel = createButtonPanel();
        gbc.gridy = 2;
        gbc.weighty = 0.1; // 按钮占10%高度
        gbc.insets = new Insets(5, 5, 5, 5);
        mainP.add(buttonPanel, gbc);

        // 底部提示标签 - 第3行
        font(reminderL, 3, Color.YELLOW, 16);
        reminderL.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        reminderL.setOpaque(true);
        backGroudColor(reminderL);

        gbc.gridy = 3;
        gbc.weighty = 0.1; // 底部占10%高度
        gbc.insets = new Insets(5, 5, 5, 5);
        mainP.add(reminderL, gbc);
    }

    /**
     * 设置中心面板（账号密码输入区域）
     */
    private void setupCenterPanel() {
        centerPanel.setBackground(new Color(0, 0, 139));

        GridBagConstraints gap = new GridBagConstraints();
        gap.insets = new Insets(20, 20, 20, 20); // 组件间距
        gap.anchor = GridBagConstraints.WEST; // 向左对齐
        gap.fill = GridBagConstraints.HORIZONTAL;

        JLabel accountL = new JLabel("ACCOUNT NUMBER: ");
        font(accountL, 1, Color.WHITE, 18);

        accounTF.setPreferredSize(new Dimension(400, 50));
        accounTF.setFont(new Font("DEFAULT", Font.PLAIN, 18));

        JLabel passwordL = new JLabel("PASSWORD: ");
        font(passwordL, 1, Color.WHITE, 18);

        passwordF.setPreferredSize(new Dimension(400, 50));
        passwordF.setFont(new Font("DEFAULT", Font.PLAIN, 18));

        // 账号标签 - 第0行
        gap.gridx = 0;
        gap.gridy = 0;
        gap.weightx = 0.3; // 标签占30%宽度
        centerPanel.add(accountL, gap);

        // 账号输入框 - 第0行
        gap.gridx = 1;
        gap.weightx = 0.7; // 输入框占70%宽度
        centerPanel.add(accounTF, gap);

        // 密码标签 - 第1行
        gap.gridx = 0;
        gap.gridy = 1;
        gap.weightx = 0.3;
        centerPanel.add(passwordL, gap);

        // 密码输入框 - 第1行
        gap.gridx = 1;
        gap.weightx = 0.7;
        centerPanel.add(passwordF, gap);
    }

    /**
     * 创建按钮面板
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(0, 0, 139));

        GridBagConstraints btnGbc = new GridBagConstraints();
        btnGbc.insets = new Insets(5, 10, 5, 10);
        btnGbc.fill = GridBagConstraints.HORIZONTAL;

        confirmButton = new JButton("Confirm");
        delButton = new JButton("Del");
        cancelButton = new JButton("Cancel");

        confirmButton.setFocusable(false);
        delButton.setFocusable(false);
        cancelButton.setFocusable(false);

        // Confirm 按钮 - 第0列
        btnGbc.gridx = 0;
        btnGbc.gridy = 0;
        btnGbc.weightx = 0.33; // 平均分配宽度
        buttonPanel.add(confirmButton, btnGbc);

        // Del 按钮 - 第1列
        btnGbc.gridx = 1;
        btnGbc.weightx = 0.33;
        buttonPanel.add(delButton, btnGbc);

        // Cancel 按钮 - 第2列
        btnGbc.gridx = 2;
        btnGbc.weightx = 0.34;
        buttonPanel.add(cancelButton, btnGbc);

        return buttonPanel;
    }

    /**
     * 设置按钮事件监听器
     */
    private void setupButtons() {
        confirmButton.addActionListener(e -> {
            confirmBT();
        });

        delButton.addActionListener(e -> {
            delBT();
        });

        cancelButton.addActionListener(e -> {
            if (!invalidinput)
                clearInput();
        });
    }

    // 以下方法保持不变
    private void clearInput() {
        passwordF.setText("");
        accounTF.setText("");
        accountGot = false;
        savedAccount = null;
        accounTF.requestFocusInWindow();
    }

    private Boolean ifisEmpty(String input) {
        if (input.isEmpty()) {
            reminderL.setText("Input cannot be empty!!!,press Confirm to continous");
            reminderL.setForeground(Color.RED);
            isinvalidinput();
            return true;
        }
        return false;
    }

    private void checkInput() {
        if (accounTF.hasFocus()) {
            try {
                currentAccountNumber = Integer.parseInt(savedAccount);
                accountPass = true;
            } catch (NumberFormatException e) {
                reminderL.setText("Invalid account number format!!!,press Confirm to continous");
                reminderL.setForeground(Color.RED);
                isinvalidinput();
            }
        } else {
            try {
                bankDatabase = new BankDatabase();
                currentPin = Integer.parseInt(new String(passwordF.getPassword()));
                boolean authenticated = bankDatabase.authenticateUser(currentAccountNumber, currentPin);
                if (authenticated) {
                    pwPass = true;
                } else {
                    reminderL.setText("Invalid account number or PIN!!! Please try again,press Confirm to continous");
                    reminderL.setForeground(Color.RED);
                    isinvalidinput();
                }
            } catch (NumberFormatException e) {
                reminderL.setText("Invalid PIN format!!! Please try again,press Confirm to continous");
                reminderL.setForeground(Color.RED);
                isinvalidinput();
            }
        }
    }

    private void isinvalidinput() {
        mainP.requestFocusInWindow();
        invalidinput = true;
    }

    private void confirmBT() {
        if (invalidinput) {
            reminderL.setText("Please Enter your Account number and PIN number ");
            reminderL.setForeground(Color.YELLOW);
            invalidinput = false;
            clearInput();
        } else {
            if (!accountGot) {
                savedAccount = accounTF.getText();
                if (!ifisEmpty(savedAccount)) {
                    checkInput();
                    if (accountPass) {
                        accountGot = true;
                        passwordF.requestFocusInWindow();
                    }
                }
            } else {
                String password = new String(passwordF.getPassword());
                if (!ifisEmpty(password)) {
                    checkInput();
                    if (pwPass) {
                        passedAccount = Integer.parseInt(accounTF.getText());
                        currentAccountNumber = passedAccount;
                        MainMenuGUI temp = new MainMenuGUI(false, currentAccountNumber);
                        passedPIN = Integer.parseInt(new String(passwordF.getPassword()));
                        pwPass = false;
                        accountGot = false;
                        clearInput();
                    }
                }
            }
        }
    }

    private void delBT() {
        if (!invalidinput) {
            if (accounTF.hasFocus()) {
                String text = accounTF.getText();
                if (!text.isEmpty())
                    accounTF.setText(text.substring(0, text.length() - 1));
                accounTF.requestFocusInWindow();
            } else {
                String text = new String(passwordF.getPassword());
                if (!text.isEmpty())
                    passwordF.setText(text.substring(0, text.length() - 1));
                passwordF.requestFocusInWindow();
            }
        }
    }

    private String getTime() {
        SimpleDateFormat T = new SimpleDateFormat("yyyy-MM-dd");
        String Time = T.format(new Date());
        return Time;
    }

    private void font(JLabel font, int type, Color color, int size) {
        if (type == 1)
            font.setFont(new Font("DEFAULT", Font.BOLD, size));
        if (type == 0)
            font.setFont(new Font("DEFAULT", Font.PLAIN, size));
        if (type == 2)
            font.setFont(new Font("DEFAULT", Font.ITALIC, size));
        font.setForeground(color);
    }

    private void backGroudColor(JLabel BGC) {
        BGC.setBackground(new Color(0, 0, 139));
    }

    public void resetlogin() {
        passedAccount = 0;
        passedPIN = 0;
    }

    public int getAccountNumber() {
        return passedAccount;
    }

    public JPanel getMainP() {
        return mainP;
    }

//    // 测试用的 main 方法
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            JFrame testFrame = new JFrame("Login Test");
//            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            testFrame.setSize(600, 500);
//            testFrame.setLocationRelativeTo(null);
//
//            LoginGUI loginGUI = new LoginGUI(true);
//            testFrame.add(loginGUI.getMainP());
//            testFrame.setVisible(true);
//        });
//    }
}