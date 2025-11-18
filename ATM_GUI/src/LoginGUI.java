import javax.swing.*;
import java.awt.*;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class LoginGUI extends JFrame {
    private TextFields accounTF;
    private JPanel centerPanel;
    private JPasswordField passwordF;
    private JButton confirmButton,delButton,cancelButton;
    private String savedAccount;
    private boolean accountGot,accountPass,pwPass,invalidinput;
    private int currentAccountNumber,currentPin;
    private BankDatabase bankDatabase;
    private JLabel reminderL; 
    
    public LoginGUI()
    
    {
        super("Main Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(0,0,139)); 

        centerPanel = new JPanel(new GridBagLayout());
        passwordF = new Passwords(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD,30));
        accounTF = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),
                new Font(Font.SANS_SERIF, Font.BOLD,30));

        reminderL = new JLabel("Please Enter your Account number and PIN number ",SwingConstants.CENTER);

        Toppanel();
        Bottompanel();
        Centerpannel();
        
        accountGot = false;
        accountPass = false;
        pwPass = false;
        invalidinput=false;
        currentAccountNumber=0;
        currentPin=0;

        //button with keypad
        confirmButton = new JButton("Confirm");
        delButton = new JButton("Del");
        cancelButton = new JButton("Cancel");
        confirmButton.setFocusable(false);
        delButton.setFocusable(false);
        cancelButton.setFocusable(false);

        confirmButton.addActionListener(e -> 
        {   
            confirmBT();
        });
        delButton.addActionListener(e -> 
        {
            delBT();
        });
        cancelButton.addActionListener(e -> 
        {
            clearInput();
        });

        //只是用來測試的，這裏是keypad的三個按鈕。
        add(confirmButton,BorderLayout.WEST);
        add(delButton,BorderLayout.EAST);
        add(cancelButton,BorderLayout.NORTH);

        //buttin with keypad!!!

        SwingUtilities.invokeLater(() -> accounTF.requestFocusInWindow());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void Toppanel()
    {   
        JLabel timeL= new JLabel("DATE: "+ getTime(),SwingConstants.CENTER);
        font(timeL,1,Color.WHITE,20);
        timeL.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        timeL.setOpaque(true);
        backGroudColor(timeL);
        add(timeL,BorderLayout.NORTH);
    }
    private void Bottompanel()
    {
        font(reminderL,3,Color.YELLOW,16);
        reminderL.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
        reminderL.setOpaque(true);
        backGroudColor(reminderL);
        add(reminderL,BorderLayout.SOUTH);
    }
    private void Centerpannel()
    {
        
        centerPanel.setBackground(new Color(0,0,139));

        GridBagConstraints gap = new GridBagConstraints();
        gap.insets = new Insets(20,20,20,20);//the gap bettwen the field
        gap.anchor = GridBagConstraints.WEST;//向左對齊

        JLabel accountL = new JLabel("ACCOUNT NUMBER: ");
        font(accountL,1,Color.WHITE,18);

       
        accounTF.setPreferredSize(new Dimension(400, 50)); 
        accounTF.setFont(new Font("DEFAULT", Font.PLAIN, 18));

        JLabel passwordL = new JLabel("PASSWORD: ");
        font(passwordL,1,Color.WHITE,18);

       
        passwordF.setPreferredSize(new Dimension(400,50));
        passwordF.setFont(new Font("DEFAULT",Font.PLAIN,18));

        gap.gridx = 0 ; gap.gridy = 0;
        centerPanel.add(accountL,gap);
        gap.gridx = 1 ; gap.gridy = 0;
        centerPanel.add(accounTF,gap);

        gap.gridx = 0 ; gap.gridy = 1;
        centerPanel.add(passwordL,gap);
        gap.gridx = 1 ; gap.gridy = 1;
        centerPanel.add(passwordF,gap);

        add(centerPanel,BorderLayout.CENTER);
    } 
    private void clearInput()
    {
        passwordF.setText("");
        accounTF.setText("");
        accountGot = false;
        savedAccount = null;
        accounTF.requestFocusInWindow();
    }
    private Boolean ifisEmpty(String input){
        if(input.isEmpty()){
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
            } catch(NumberFormatException e) {
                reminderL.setText("Invalid account number format!!!,press Confirm to continous");
                reminderL.setForeground(Color.RED);
                isinvalidinput();
            }
        } else {
            try {   
                bankDatabase = new BankDatabase(); 
                currentPin = Integer.parseInt(new String(passwordF.getPassword()));
                boolean authenticated = bankDatabase.authenticateUser(currentAccountNumber,currentPin);
                if(authenticated) {
                    pwPass=true;
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
    private void isinvalidinput()
    {
        this.requestFocusInWindow();
        invalidinput = true;
    }
    private void confirmBT()
    {   if(invalidinput)
    	{
        reminderL.setText("Please Enter your Account number and PIN number ");
        reminderL.setForeground(Color.YELLOW);
	    invalidinput = false;
	    clearInput();
        }
        else 
        {
            if(!accountGot)
            {
                savedAccount=accounTF.getText();
                if(!ifisEmpty(savedAccount))
                {
                    checkInput();
                    if(accountPass)
                    {
                        accountGot= true;
                        passwordF.requestFocusInWindow();
                    }
                }
            }
            else
            {
                String password = new String(passwordF.getPassword()); 
                if(!ifisEmpty(password))
                {
                    checkInput();
                    if(pwPass)
                    {
                        currentAccountNumber = Integer.parseInt(accounTF.getText());
                        currentPin =Integer.parseInt(new String(passwordF.getPassword()));
                        pwPass = false;
                        accountGot= false;
                        clearInput();
                    }
                }
            }
        }
        

    }
    private void delBT(){
        if(accounTF.hasFocus())
        {
            String text = accounTF.getText();
            if(!text.isEmpty())
            accounTF.setText(text.substring(0,text.length()-1));
            accounTF.requestFocusInWindow();
        }
        else 
        {
            String text = new String(passwordF.getPassword());
            if(!text.isEmpty())
            passwordF.setText(text.substring(0, text.length() - 1));
            passwordF.requestFocusInWindow();
        }




    }
    


    private String getTime(){
        SimpleDateFormat T=new SimpleDateFormat("yyyy-MM-dd");
        String Time = T.format(new Date());
        return Time;
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
    private void backGroudColor(JLabel BGC)
    { 
        BGC.setBackground(new Color(0,0,139));
    }
    public int getAccountNumber(){     
        return currentAccountNumber;
    }
    //測試用的
    public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new LoginGUI());
    }

}
