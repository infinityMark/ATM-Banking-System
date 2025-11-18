import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainMenuGUI{
    private JLabel timeL,bottomL;
    private JPanel mainP,centerP,leftP,rightP;
    private int currentAccountNumberMMG;
    private BankDatabase bankDatabase;
    private static final Color DARK_BLUE = new Color(0, 0, 139);

    public MainMenuGUI (boolean showMainMenu , int currentAccountNumber)
    {

        mainP = new JPanel();
        mainPanel();
        currentAccountNumberMMG = currentAccountNumber;
        timeL = new JLabel("DATE: "+ getTime(),SwingConstants.CENTER);
        bottomL = new JLabel("Please make a Selection....",SwingConstants.CENTER);
        centerP = new JPanel(new GridLayout(3,1));
        leftP = new JPanel(new GridLayout(3,1));
        rightP = new JPanel(new GridLayout(3,1));
        bankDatabase = new BankDatabase();

        TopPanel();
        LeftPanel();
        RightPanel();
        CenterPanel();
        BottomPanel();

        if (showMainMenu)
            mainP.setVisible(true);
    }
    public JPanel getMainP(){
        return mainP;
    }
    private void mainPanel(){
        mainP.setLayout(new BorderLayout());
        mainP.setBackground(new Color(0,0,139));
    }
    private void TopPanel(){
        font(timeL,1,Color.WHITE,18);
        timeL.setBackground(DARK_BLUE);
        timeL.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainP.add(timeL, BorderLayout.NORTH);

    }
    private void LeftPanel(){
        leftP.setBackground(DARK_BLUE);
        leftP.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        String[] labels = {"<html><center>View</center>Balance</html>",
                "<html>Withdraw<br><center>Cash</center></html>",
                "Exit"};
        for (int i = 0; i < 3; i++)
        {
            JLabel LL = new JLabel(labels[i],SwingConstants.CENTER);
            font(LL, 0, Color.WHITE, 18);
            LL.setOpaque(true);
            LL.setBackground(DARK_BLUE);
            LL.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            leftP.add(LL);
        }
        leftP.setPreferredSize(new Dimension(100, 0));
        mainP.add(leftP, BorderLayout.WEST);
    }
    private void RightPanel(){
        rightP.setBackground(DARK_BLUE);
        rightP.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        String[] labels={"<html>Transfer<br><center>Funds</center></html>",
                "<html>Transaction<br><center>history</center></html>"
        };
        for (int i = 0; i < 2; i++)
        {
            JLabel RL = new JLabel(labels[i],SwingConstants.CENTER);
            font(RL, 0, Color.WHITE, 18);
            RL.setOpaque(true);
            RL.setBackground(DARK_BLUE);
            RL.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
            rightP.add(RL);
        }
        rightP.setPreferredSize(new Dimension(100, 0));
        mainP.add(rightP, BorderLayout.EAST);
    }
    private void CenterPanel(){
        centerP.setBackground(DARK_BLUE);
        centerP.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel titleL = new JLabel("Main Menu",SwingConstants.CENTER);
        font(titleL, 1, Color.WHITE, 40);
        centerP.add(titleL);

        String accountType = bankDatabase.getAccountType(currentAccountNumberMMG);
        double rateOrLimit = bankDatabase.getRateOrLimit(currentAccountNumberMMG);

        JLabel accountTypeL = new JLabel("Account Type: "+accountType, SwingConstants.CENTER);
        font(accountTypeL, 0,Color.WHITE, 16);
        centerP.add(accountTypeL);

        if(accountType.equals("Saving Account"))
        {
            JLabel interestL = new JLabel("Interest Rate: "+rateOrLimit*100+"% per annum" , SwingConstants.CENTER);
            font(interestL, 0, Color.WHITE, 16);
            centerP.add(interestL);
        }
        mainP.add(centerP, BorderLayout.CENTER);
    }
    private void BottomPanel(){
        font(bottomL, 1, Color.YELLOW, 16);
        bottomL.setBackground(DARK_BLUE);
        bottomL.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        mainP.add(bottomL, BorderLayout.SOUTH);
    }

    private void font(JLabel font,int type,Color color,int size)
    {   if(type==1)
        font.setFont(new Font("DEFAULT", Font.BOLD, size));
        if(type==0)
            font.setFont(new Font("DEFAULT", Font.PLAIN, size));
        if(type==2)
            font.setFont(new Font("DEFAULT", Font.ITALIC, size));
        font.setForeground(color);
    }

    public int getAccountNumber(){
        return currentAccountNumberMMG;
    }
    private String getTime(){
        SimpleDateFormat T=new SimpleDateFormat("yyyy-MM-dd");
        String Time = T.format(new Date());
        return Time;
    }
    public JPanel getMainPanel() {
        return mainP;
    }


//    AI 寫的mian用來測試的
//    public static void main(String[] args) {
//    SwingUtilities.invokeLater(() -> {
//    JFrame frame = new JFrame("ATM Main Menu");
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setSize(900, 600);
//    frame.setLocationRelativeTo(null);
//     MainMenuGUI menu = new MainMenuGUI(true, 22222);
//    frame.setContentPane(menu.mainP);
//     frame.setVisible(true);
//    });
//     }

}