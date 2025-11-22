import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import org.w3c.dom.events.MouseEvent;

public class MainMenuGUI {
    private JLabel timeL, bottomL;
    private JPanel mainP, centerP, leftP, rightP;
    private int currentAccountNumberMMG;
    private BankDatabase bankDatabase;
    private ATMUIController controller;

    public MainMenuGUI() {

    }

    public void createMainMenuGUI(boolean showMainMenu, int currentAccountNumber, ATMUIController controller) {
        mainP = new JPanel();
        mainPanel();
        currentAccountNumberMMG = currentAccountNumber;
        timeL = new JLabel("DATE: " + getTime(), SwingConstants.CENTER);
        bottomL = new JLabel("Please make a Selection....", SwingConstants.CENTER);
        centerP = new JPanel(new GridLayout(3, 1));
        leftP = new JPanel(new GridLayout(3, 1));
        rightP = new JPanel(new GridLayout(3, 1));
        bankDatabase = BankDatabase.getInstance();
        this.controller = controller;
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
        mainP.setBackground(StandardColor.Blue.getColorMode());
    }

    private void TopPanel() {
        font(timeL, 1, StandardColor.White.getColorMode(), 18);
        timeL.setBackground(StandardColor.Blue.getColorMode());
        timeL.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        mainP.add(timeL, BorderLayout.NORTH);

    }

    private void LeftPanel() {
        leftP.setBackground(StandardColor.Blue.getColorMode());
        leftP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        String[][]labels = {{ "View","Balance"},
                {"Withdraw","Cash"},
                {"Exit",""}};
        String[] targetPanels = {
                ATMUI.BALANCE_PANEL,
                ATMUI.WITHDRAW_PANEL,
                ATMUI.GREETING_PANEL
        };
        for (int i = 0; i < 3; i++) {
            JPanel cellPanel = new JPanel();
            cellPanel.setLayout(new BoxLayout(cellPanel, BoxLayout.Y_AXIS));
            cellPanel.setBackground(StandardColor.Blue.getColorMode());
            cellPanel.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
            cellPanel.add(Box.createVerticalGlue()); //left space at the top of the cells
            for(String line : labels[i])
            {
            JLabel LL = new JLabel( line, SwingConstants.CENTER);
            font(LL, 0, StandardColor.White.getColorMode(), 18);
            LL.setOpaque(false);
            LL.setBackground(StandardColor.Blue.getColorMode());
            LL.setAlignmentX(Component.CENTER_ALIGNMENT);

            cellPanel.add(LL);
            }

            // Add mouse listener
            final int index = i;
            cellPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Invoke the controller to switch to the target panel
                    if (controller != null) {
                        controller.switchToPanel(targetPanels[index]);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    cellPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    // Darken the background color to indicate
                    cellPanel.setBackground(StandardColor.Blue.getColorMode().darker());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    // Restore default settings when mouse leaves
                    cellPanel.setCursor(Cursor.getDefaultCursor());
                    cellPanel.setBackground(StandardColor.Blue.getColorMode());
                }
            });
            cellPanel.add(Box.createVerticalGlue()); //left space at the buttom of the cells
            leftP.add(cellPanel);
        }

        leftP.setPreferredSize(new Dimension(100, 0));
        mainP.add(leftP, BorderLayout.WEST);
    }

    private void RightPanel() {

        rightP.setBackground(StandardColor.Blue.getColorMode());
        rightP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        String[] []labels = { {"Transfer","Funds"},
                {"Transaction","history"}
        };

        String[] targetPanels = {
                ATMUI.TRANSFER_PANEL,
                ATMUI.HISTORY_PANEL
        };

        for (int i = 0; i < 2; i++) {
            JPanel cellPanel = new JPanel();
            cellPanel.setLayout(new BoxLayout(cellPanel, BoxLayout.Y_AXIS));
            cellPanel.setBackground(StandardColor.Blue.getColorMode());
            cellPanel.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
            cellPanel.add(Box.createVerticalGlue()); 
            for(String line : labels[i])
            {
            JLabel RL = new JLabel(line, SwingConstants.CENTER);
            font(RL, 0, StandardColor.White.getColorMode(), 18);
            RL.setOpaque(false);
            RL.setBackground(StandardColor.Blue.getColorMode());
            RL.setAlignmentX(Component.CENTER_ALIGNMENT);
            cellPanel.add(RL);
            }

            final int index = i;
            cellPanel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Invoke the controller to switch to the target panel
                    if (controller != null) {
                        controller.switchToPanel(targetPanels[index]);
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    cellPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    cellPanel.setBackground(StandardColor.Blue.getColorMode().darker());
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    cellPanel.setCursor(Cursor.getDefaultCursor());
                    cellPanel.setBackground(StandardColor.Blue.getColorMode());
                }
            });
            cellPanel.add(Box.createVerticalGlue()); 
            rightP.add(cellPanel);

        }
        JLabel emptyLabel = new JLabel("", SwingConstants.CENTER);
        emptyLabel.setOpaque(true);
        emptyLabel.setBackground(StandardColor.Blue.getColorMode());
        emptyLabel.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));
        rightP.add(emptyLabel);

        rightP.setPreferredSize(new Dimension(100, 0));
        mainP.add(rightP, BorderLayout.EAST);
    }

    private void CenterPanel() {
        centerP.setBackground(StandardColor.Blue.getColorMode());
        centerP.setBorder(BorderFactory.createLineBorder(StandardColor.White.getColorMode(), 1));

        JLabel titleL = new JLabel("Main Menu", SwingConstants.CENTER);
        font(titleL, 1, StandardColor.White.getColorMode(), 60);
        centerP.add(titleL);

        String accountType = bankDatabase.getAccountType(currentAccountNumberMMG);
        double rateOrLimit = bankDatabase.getRateOrLimit(currentAccountNumberMMG);

        JLabel accountTypeL = new JLabel("Account Type: " + accountType, SwingConstants.CENTER);
        font(accountTypeL, 0, StandardColor.White.getColorMode(), 40);
        centerP.add(accountTypeL);

        if (accountType.equals("Saving Account")) {
            JLabel interestL = new JLabel("Interest Rate: " + rateOrLimit * 100 + "% per annum", SwingConstants.CENTER);
            font(interestL, 0, StandardColor.White.getColorMode(), 40);
            centerP.add(interestL);
        }
        mainP.add(centerP, BorderLayout.CENTER);
    }

    private void BottomPanel() {
        font(bottomL, 1, StandardColor.Yellow.getColorMode(), 16);
        bottomL.setBackground(StandardColor.Blue.getColorMode());
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