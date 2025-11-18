import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

//login is not complete can't check is the code is corr or not.

public class BalanceInquiryUI extends JPanel {
    private JLabel timeLabel, titleLabel, accountTypeLabel, availableBalanceLabel, 
                  totalBalanceLabel, interestLabel, bottomLabel;
    private JButton backButton;
    private BankDatabase bankDatabase;
    private int currentAccountNumber;
    private static final Color DARK_BLUE = new Color(0, 0, 139);
    private static final Font TITLE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 32);
    private static final Font NORMAL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    private static final Font SMALL_FONT = new Font(Font.SANS_SERIF, Font.PLAIN, 16);

    public BalanceInquiryUI(int accountNumber) {
        this.currentAccountNumber = accountNumber;
        this.bankDatabase = new BankDatabase();
        initializeUI();
        updateBalanceInfo();
    }

    private void initializeUI() {
        setLayout(new BorderLayout());
        setBackground(DARK_BLUE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        createTopPanel();
        createCenterPanel();
        createBottomPanel();
    }

    private void createTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(DARK_BLUE);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        // Time label
        timeLabel = new JLabel("DATE: " + getCurrentTime(), SwingConstants.CENTER);
        styleLabel(timeLabel, Color.WHITE, NORMAL_FONT);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Title label
        titleLabel = new JLabel("BALANCE INQUIRY", SwingConstants.CENTER);
        styleLabel(titleLabel, Color.YELLOW, TITLE_FONT);

        topPanel.add(timeLabel, BorderLayout.NORTH);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
    }

    private void createCenterPanel() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(DARK_BLUE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Account Type
        JLabel accountTypeTitle = new JLabel("Account Type:");
        styleLabel(accountTypeTitle, Color.WHITE, NORMAL_FONT);
        centerPanel.add(accountTypeTitle, gbc);

        gbc.gridx = 1;
        accountTypeLabel = new JLabel();
        styleLabel(accountTypeLabel, Color.CYAN, NORMAL_FONT);
        centerPanel.add(accountTypeLabel, gbc);

        // Available Balance
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel availableBalanceTitle = new JLabel("Available Balance:");
        styleLabel(availableBalanceTitle, Color.WHITE, NORMAL_FONT);
        centerPanel.add(availableBalanceTitle, gbc);

        gbc.gridx = 1;
        availableBalanceLabel = new JLabel();
        styleLabel(availableBalanceLabel, Color.GREEN, NORMAL_FONT);
        centerPanel.add(availableBalanceLabel, gbc);

        // Total Balance
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel totalBalanceTitle = new JLabel("Total Balance:");
        styleLabel(totalBalanceTitle, Color.WHITE, NORMAL_FONT);
        centerPanel.add(totalBalanceTitle, gbc);

        gbc.gridx = 1;
        totalBalanceLabel = new JLabel();
        styleLabel(totalBalanceLabel, Color.GREEN, NORMAL_FONT);
        centerPanel.add(totalBalanceLabel, gbc);

        // Interest/Limit Information
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        interestLabel = new JLabel("", SwingConstants.CENTER);
        styleLabel(interestLabel, Color.ORANGE, SMALL_FONT);
        centerPanel.add(interestLabel, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(DARK_BLUE);
        bottomPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE));

        // Instruction label
        bottomLabel = new JLabel("Press 'Back to Menu' to return to main menu", SwingConstants.CENTER);
        styleLabel(bottomLabel, Color.YELLOW, SMALL_FONT);
        bottomLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // Back button
        backButton = new RoundedButton("Back to Menu", "Returning...",
                StandardColor.Blue.getColorMode(), 
                StandardColor.Green.getColorMode(),
                Color.WHITE, Color.BLACK,
                NORMAL_FONT, NORMAL_FONT, 
                true, 200, 50);
        
        backButton.setName("Back To Menu");
        backButton.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(DARK_BLUE);
        buttonPanel.add(backButton);

        bottomPanel.add(bottomLabel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateBalanceInfo() {
        if (bankDatabase != null && currentAccountNumber != 0) {
            // Get account information
            String accountType = bankDatabase.getAccountType(currentAccountNumber);
            double availableBalance = bankDatabase.getAvailableBalance(currentAccountNumber);
            double totalBalance = bankDatabase.getTotalBalance(currentAccountNumber);
            double rateOrLimit = bankDatabase.getRateOrLimit(currentAccountNumber);

            // Update labels
            accountTypeLabel.setText(accountType);
            availableBalanceLabel.setText(String.format("HK$ %.2f", availableBalance));
            totalBalanceLabel.setText(String.format("HK$ %.2f", totalBalance));

            //// Update interest/limit information
            //if (accountType.equals("Saving Account")) {
            //     interestLabel.setText(String.format("Interest Rate: %.2f%% per annum", rateOrLimit * 100));
            //} else if (accountType.equals("Cheque Account")) {
            //     interestLabel.setText(String.format("Cheque Limit: HK$ %.2f per transaction", rateOrLimit));
            // }
        } else {
            // Default values when no account is set
            accountTypeLabel.setText("Not Available");
            availableBalanceLabel.setText("HK$ 0.00");
            totalBalanceLabel.setText("HK$ 0.00");
            interestLabel.setText("Please log in to view account information");
        }
    }

    private void styleLabel(JLabel label, Color color, Font font) {
        label.setFont(font);
        label.setForeground(color);
        label.setOpaque(false);
    }

    private String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(new Date());
    }

    public JButton getBackButton() {
        return backButton;
    }

    public void refreshBalance() {
        updateBalanceInfo();
        timeLabel.setText("DATE: " + getCurrentTime());
    }

    // Method to update account number
    public void setAccountNumber(int accountNumber) {
        this.currentAccountNumber = accountNumber;
        refreshBalance();
    }
}