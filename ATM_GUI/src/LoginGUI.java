import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginGUI {
    private JTextField accounTF;
    private JPanel centerPanel;
    private JPasswordField passwordF;
    private JButton confirmButton, delButton, cancelButton;
    private String savedAccount;
    private boolean accountGot, accountPass, pwPass, invalidinput;
    private int currentAccountNumber, currentPin;
    private BankDatabase bankDatabase;
    private JLabel reminderL;
    private JPanel mainP;
    private static int passedAccount, passedPIN;
    
    // Track which field is currently focused
    private boolean accountFieldFocused = true;

    public void goToPanel(String name) {
        Container parent = mainP.getParent();
        if (parent != null) {
            Container current = parent;
            while (current != null && !(current.getLayout() instanceof CardLayout)) {
                current = current.getParent();
            }

            if (current != null) {
                CardLayout layout = (CardLayout) current.getLayout();
                layout.show(current, name);

                current.revalidate();
                current.repaint();
            } else {
                System.err.println("CardLayout miss,无法切换面板");
            }
        } else {
            System.err.println("mainP = null,无法切换面板");
        }
    }

    public LoginGUI(boolean showLoginGUI) { // 測試借用的frame

        mainP = new JPanel();
        mainPanel();

        centerPanel = new JPanel(new GridBagLayout());
        passwordF = new JPasswordField();
        accounTF = new JTextField();
        reminderL = new JLabel("Please Enter your Account number and PIN number ", SwingConstants.CENTER);

        Toppanel();
        Bottompanel();
        Centerpannel();

        accountGot = false;
        accountPass = false;
        pwPass = false;
        invalidinput = false;
        currentAccountNumber = 0;
        currentPin = 0;
        
        // Set initial focus to account field
        accountFieldFocused = true;

        // Add focus listeners to track which field is focused
        accounTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                accountFieldFocused = true;
            }
        });
        
        passwordF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                accountFieldFocused = false;
            }
        });

        // button with keypad
        confirmButton = new JButton("Confirm");
        delButton = new JButton("Del");
        cancelButton = new JButton("Cancel");
        confirmButton.setFocusable(false);
        delButton.setFocusable(false);
        cancelButton.setFocusable(false);

        if (showLoginGUI) {
            SwingUtilities.invokeLater(() -> accounTF.requestFocusInWindow());
            mainP.setVisible(true);
        }
    }

    // NEW METHODS FOR KEYPAD INTEGRATION
    
    /**
     * Handles number input from keypad
     */
    public void handleNumberInput(String number) {
        if (accountFieldFocused) {
            String currentText = accounTF.getText();
            accounTF.setText(currentText + number);
        } else {
            String currentText = new String(passwordF.getPassword());
            passwordF.setText(currentText + number);
        }
    }
    
    /**
     * Handles delete action from keypad - deletes last character
     */
    public void handleDelete() {
        if (accountFieldFocused) {
            String currentText = accounTF.getText();
            if (!currentText.isEmpty()) {
                accounTF.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else {
            String currentText = new String(passwordF.getPassword());
            if (!currentText.isEmpty()) {
                passwordF.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
    }
    
    /**
     * Handles clear action from keypad - clears all input
     */
    public void handleClear() {
        clearInput();
    }
    
    /**
     * Handles confirm action from keypad - attempts login
     */
    public void handleConfirm() {
        confirmBT();
    }
    
    /**
     * Switches focus between account and password fields
     */
    public void switchFocus() {
        if (accountFieldFocused) {
            passwordF.requestFocus();
            accountFieldFocused = false;
        } else {
            accounTF.requestFocus();
            accountFieldFocused = true;
        }
    }

    private void mainPanel() {
        mainP.setLayout(new BorderLayout());
        mainP.setBackground(new Color(0, 0, 139));
    }

    protected JPanel createMainMenuPanels() {
        return mainP;
    }

    private void Toppanel() {
        JLabel timeL = new JLabel("DATE: " + getTime(), SwingConstants.CENTER);
        font(timeL, 1, Color.WHITE, 20);
        timeL.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        timeL.setOpaque(true);
        backGroudColor(timeL);
        mainP.add(timeL, BorderLayout.NORTH);
    }

    private void Bottompanel() {
        font(reminderL, 3, Color.YELLOW, 16);
        reminderL.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        reminderL.setOpaque(true);
        backGroudColor(reminderL);
        mainP.add(reminderL, BorderLayout.SOUTH);
    }

    private void Centerpannel() {
        centerPanel.setBackground(new Color(0, 0, 139));

        GridBagConstraints gap = new GridBagConstraints();
        gap.insets = new Insets(20, 20, 20, 20);// the gap bettwen the field
        gap.anchor = GridBagConstraints.WEST;// 向左對齊

        JLabel accountL = new JLabel("ACCOUNT NUMBER: ");
        font(accountL, 1, Color.WHITE, 18);

        accounTF.setPreferredSize(new Dimension(400, 50));
        accounTF.setFont(new Font("DEFAULT", Font.PLAIN, 18));

        JLabel passwordL = new JLabel("PASSWORD: ");
        font(passwordL, 1, Color.WHITE, 18);

        passwordF.setPreferredSize(new Dimension(400, 50));
        passwordF.setFont(new Font("DEFAULT", Font.PLAIN, 18));

        gap.gridx = 0;
        gap.gridy = 0;
        centerPanel.add(accountL, gap);
        gap.gridx = 1;
        gap.gridy = 0;
        centerPanel.add(accounTF, gap);

        gap.gridx = 0;
        gap.gridy = 1;
        centerPanel.add(passwordL, gap);
        gap.gridx = 1;
        gap.gridy = 1;
        centerPanel.add(passwordF, gap);

        mainP.add(centerPanel, BorderLayout.CENTER);
    }

    public void clearInput() {
        passwordF.setText("");
        accounTF.setText("");
        accountGot = false;
        savedAccount = null;
        accounTF.requestFocusInWindow();
        accountFieldFocused = true;
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

    public void checkInput() {
        if (!accountGot) {
            try {
                currentAccountNumber = Integer.parseInt(accounTF.getText().trim());
                accountPass = true;
            } catch (NumberFormatException e) {
                reminderL.setText("Invalid account number format!!!,press Confirm to continue");
                reminderL.setForeground(Color.RED);
                System.out.println("Invalid account number format");
                isinvalidinput();
            }
        } else {
            try {
                bankDatabase = new BankDatabase();
                currentPin = Integer.parseInt(new String(passwordF.getPassword()));
                boolean authenticated = bankDatabase.authenticateUser(currentAccountNumber, currentPin);
                System.out.println(currentAccountNumber + ", " + currentPin);
                System.out.println("Authentication result: " + authenticated);
                if (authenticated) {
                    pwPass = true;
                } else {
                    reminderL.setText("Invalid account number or PIN!!! Please try again,press Confirm to continue");
                    reminderL.setForeground(Color.RED);
                    isinvalidinput();
                    System.out.println("Invalid account number or PIN");
                }
            } catch (NumberFormatException e) {
                reminderL.setText("Invalid PIN format!!! Please try again,press Confirm to continue");
                reminderL.setForeground(Color.RED);
                isinvalidinput();
                System.out.println("Invalid PIN format");
            }
        }
    }

    
    
    private void isinvalidinput() {
        System.out.println("Invalid input detected");
        reminderL.requestFocusInWindow();
        invalidinput = true;
    }

    public boolean confirmBT() {
        if (invalidinput) {
            reminderL.setText("Please Enter your Account number and PIN number ");
            reminderL.setForeground(Color.YELLOW);
            invalidinput = false;
            clearInput();
            System.out.println("reset input");
            return false;
        } else {
            if (!accountGot) {
                String accountText = accounTF.getText().trim();
                if (!ifisEmpty(accountText)) {
                    checkInput();
                    if (accountPass) {
                        accountGot = true;
                        passwordF.requestFocusInWindow();
                        accountFieldFocused = false;
                    }
                }
            } else {
                String password = new String(passwordF.getPassword());

                if (!ifisEmpty(password)) {
                    checkInput();
                    if (pwPass) {
                        passedAccount = Integer.parseInt(accounTF.getText().trim());
                        currentAccountNumber = passedAccount;
                        passedPIN = Integer.parseInt(new String(passwordF.getPassword()));
                        pwPass = false;
                        accountGot = false;
                        clearInput();
                        System.out.println(currentAccountNumber);
                        System.out.println(passedPIN);
                        ATMUI.setCurrentAccountNumberATMUI(passedAccount);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void delBT() {
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
            handleDelete();
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

    public JPanel getMainPanel() {
        return mainP;
    }

    public int getAccountNumber() {
        return passedAccount;
    }
}