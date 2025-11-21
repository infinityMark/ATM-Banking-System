import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenuGUI {
    private JLabel timeL, bottomL;
    private JPanel mainP, centerP, leftP, rightP;
    private int currentAccountNumberMMG;
    private BankDatabase bankDatabase;

    public MainMenuGUI() {

    }

    public void createMainMenuGUI(boolean showMainMenu, int currentAccountNumber) {
        mainP = new JPanel();
        mainPanel();
        currentAccountNumberMMG = currentAccountNumber;
        timeL = new JLabel("DATE: " + getTime(), SwingConstants.CENTER);
        bottomL = new JLabel("Please make a Selection....", SwingConstants.CENTER);
        centerP = new JPanel(new GridLayout(3, 1));
        leftP = new JPanel(new GridLayout(3, 1));
        rightP = new JPanel(new GridLayout(3, 1));
        bankDatabase = BankDatabase.getInstance();

        TopPanel();
        LeftPanel();
        RightPanel();
        CenterPanel();
        BottomPanel();

        if (showMainMenu)
            mainP.setVisible(true);
    }

    public JPanel getMainP() {
        return mainP;
    }

    private void mainPanel() {
        mainP.setLayout(new BorderLayout());
        mainP.setBackground(StandardColor.DarkBlue.getColorMode());
    }

    private void TopPanel() {
        font(timeL, 1, StandardColor.White.getColorMode(), 18);
        timeL.setBackground(StandardColor.DarkBlue.getColorMode());
        timeL.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        mainP.add(timeL, BorderLayout.NORTH);

    }

    private void LeftPanel() {
        leftP.setBackground(StandardColor.DarkBlue.getColorMode());
        leftP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        String[] labels = { "<html><center>View</center>Balance</html>",
                "<html>Withdraw<br><center>Cash</center></html>",
                "Exit" };
        for (int i = 0; i < 3; i++) {
            JLabel LL = new JLabel(labels[i], SwingConstants.CENTER);
            font(LL, 0, StandardColor.White.getColorMode(), 18);
            LL.setOpaque(true);
            LL.setBackground(StandardColor.DarkBlue.getColorMode());
            LL.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
            leftP.add(LL);
        }
        leftP.setPreferredSize(new Dimension(100, 0));
        mainP.add(leftP, BorderLayout.WEST);
    }

    private void RightPanel() {
        rightP.setBackground(StandardColor.DarkBlue.getColorMode());
        rightP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        String[] labels = { "<html>Transfer<br><center>Funds</center></html>",
                "<html>Transaction<br><center>history</center></html>"
        };
        for (int i = 0; i < 2; i++) {
            JLabel RL = new JLabel(labels[i], SwingConstants.CENTER);
            font(RL, 0, StandardColor.White.getColorMode(), 18);
            RL.setOpaque(true);
            RL.setBackground(StandardColor.DarkBlue.getColorMode());
            RL.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
            rightP.add(RL);
        }
        rightP.setPreferredSize(new Dimension(100, 0));
        mainP.add(rightP, BorderLayout.EAST);
    }

    private void CenterPanel() {
        centerP.setBackground(StandardColor.DarkBlue.getColorMode());
        centerP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));

        JLabel titleL = new JLabel("Main Menu", SwingConstants.CENTER);
        font(titleL, 1, StandardColor.White.getColorMode(), 40);
        centerP.add(titleL);

        String accountType = bankDatabase.getAccountType(currentAccountNumberMMG);
        double rateOrLimit = bankDatabase.getRateOrLimit(currentAccountNumberMMG);

        JLabel accountTypeL = new JLabel("Account Type: " + accountType, SwingConstants.CENTER);
        font(accountTypeL, 0, StandardColor.White.getColorMode(), 16);
        centerP.add(accountTypeL);

        if (accountType.equals("Saving Account")) {
            JLabel interestL = new JLabel("Interest Rate: " + rateOrLimit * 100 + "% per annum", SwingConstants.CENTER);
            font(interestL, 0, StandardColor.White.getColorMode(), 16);
            centerP.add(interestL);
        }
        mainP.add(centerP, BorderLayout.CENTER);
    }

    private void BottomPanel() {
        font(bottomL, 1, StandardColor.Yellow.getColorMode(), 16);
        bottomL.setBackground(StandardColor.DarkBlue.getColorMode());
        bottomL.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        mainP.add(bottomL, BorderLayout.SOUTH);
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

    public int getAccountNumber() {
        return currentAccountNumberMMG;
    }

    private String getTime() {
        SimpleDateFormat T = new SimpleDateFormat("yyyy-MM-dd");
        String Time = T.format(new Date());
        return Time;
    }

    public JPanel getMainPanel() {
        return mainP;
    }
}