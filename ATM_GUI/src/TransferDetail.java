import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransferDetail extends JFrame {


    static public JPanel getSelectionMenu(String title, String firstSelection, String secondSelection, int fontSize, Font font){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 15, 10, 15);


        RoundedButton selectionOneBtn = new RoundedButton(firstSelection, firstSelection,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                true, 200, 10);
        selectionOneBtn.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        selectionOneBtn.setHorizontalAlignment(SwingConstants.LEFT);

        RoundedButton selectionTwoBtn = new RoundedButton(secondSelection, secondSelection,
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Yellow.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                new Font(Font.SANS_SERIF, Font.PLAIN, fontSize),
                true, 200, 10);
        selectionTwoBtn.setHorizontalAlignment(SwingConstants.LEFT);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel menuTitle = new JLabel(title);
        menuTitle.setFont(font);
        menuTitle.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(menuTitle, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 50, 0));
        buttonPanel.add(selectionOneBtn);
        buttonPanel.add(selectionTwoBtn);

        gbc.gridy = 1;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(buttonPanel, gbc);

        return panel;
    }

    static public JPanel confirmationStep(String  previous, String title, String firstSelection, String secondSelection, int fontSize, Font font){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel previousLabel = new JLabel(previous);
        previousLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        previousLabel.setForeground(StandardColor.GreyHighest.getOppositeColorMode());


        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 15, 10, 15);
        panel.add(previousLabel, gbc);

        gbc.weighty = 0.4;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        JPanel selectionMenu = getSelectionMenu(title,firstSelection,secondSelection,fontSize,font);
        selectionMenu.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
        panel.add(selectionMenu, gbc);

        return panel;
    }

    static public JPanel receiveTransferInformation(String remainAmount){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.fill = GridBagConstraints.BOTH;

        JLabel remainAmountTitle = new JLabel(remainAmount);
        remainAmountTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 20));
        remainAmountTitle.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        RoundedButton confirmationButton = new RoundedButton("Confirm", "Confirm",
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        TextFields receiverAccountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        TextFields amountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        JLabel receiver = new JLabel("Receiver account");
        receiver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        JLabel amount = new JLabel("Amount");
        amount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        TextFields amountSy = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));
        amountSy.setText("HK$ ");
        amountSy.setEnabled(false);

        JPanel amountDisplay = new JPanel(new GridBagLayout());
        GridBagConstraints gbcAmountDisplay = new GridBagConstraints();
        gbcAmountDisplay.gridy = 0;
        gbcAmountDisplay.gridx = 0;
        gbcAmountDisplay.weighty = 1.0;
        gbcAmountDisplay.weightx = 0.001;

        gbcAmountDisplay.insets = new Insets(0,5,0,5);

        gbcAmountDisplay.fill = GridBagConstraints.BOTH;
        amountDisplay.add(amountSy,gbcAmountDisplay);
        gbcAmountDisplay.weightx = 0.9;
        gbcAmountDisplay.gridx = 1;
        amountDisplay.add(amountTextField,gbcAmountDisplay);


        gbc.gridy = 0;
        gbc.weighty = 0.01;
        panel.add(remainAmountTitle,gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,0,10);
        panel.add(receiver,gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,10,10);
        panel.add(receiverAccountTextField,gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,0,10);
        panel.add(amount,gbc);

        gbc.gridy = 4;
        gbc.weighty = 0.01;
        gbc.insets = new Insets(0,10,10,10);
        panel.add(amountDisplay,gbc);

        confirmationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (receiverAccountTextField.getText().equals("1")){
                    receiverAccountTextField.setBackground(StandardColor.Red.getColor(1));
                }
            }
        });

        gbc.gridy = 5;
        gbc.weightx = 0.01;
        gbc.weighty = 0.01;

        gbc.insets = new Insets(30,10,10,10);
        panel.add(confirmationButton, gbc);

        return panel;
    }

    static public JPanel afterTransaction(String transactionInformation, Font font){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel transactionInformationLabel = new JLabel(transactionInformation);
        transactionInformationLabel.setFont(font);
        transactionInformationLabel.setForeground(StandardColor.Green.getColorMode());
        transactionInformationLabel.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        JLabel endTransaction = new JLabel("The transfer successfully.");
        endTransaction.setFont(font);
        endTransaction.setForeground(StandardColor.GreyHighest.getOppositeColorMode());

        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        gbc.gridy = 0;
        gbc.weighty = 0.1;
        panel.add(transactionInformationLabel, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.1;
        panel.add(endTransaction, gbc);
        gbc.gridy = 2;
        gbc.weighty = 0.8;
//        panel.add(getSelectionMenu("Do you want to make a remark for the transfer?","Yes","No", 20, font), gbc);
        panel.add(getSelectionMenu("Do you want transfer to another?","Yes, go back transfer","No, go back ATM menu", 30, font), gbc);
        return panel;
    }

    static public JPanel showRemarkInformation(){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        RoundedButton selectionOneBtn = new RoundedButton("", "",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 30),
                new Font(Font.SANS_SERIF, Font.PLAIN, 30),
                true, 200, 10);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;

        RoundedButton[] roundedButtons = new RoundedButton[8];

        return panel;
    }


    public static void main(String[] args) {
        JFrame mains = new JFrame();
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.setBackground(StandardColor.GreyHighest.getColorMode());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel taskTitle = new JLabel("Transfer");
        taskTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        taskTitle.setForeground(StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        RoundedButton selectionOneBtn = new RoundedButton("1 - Input receiver account number", "1 - Input receiver account number",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);
        selectionOneBtn.setHorizontalAlignment(SwingConstants.LEFT);

        RoundedButton selectionTwoBtn = new RoundedButton("2 - Exit", "2 - Exit",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Yellow.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);
        selectionTwoBtn.setHorizontalAlignment(SwingConstants.LEFT);

        selectionTwoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("fowjfe");
            }
        });

        panel.setBorder(BorderFactory.createEmptyBorder(5, 20, 5, 20));

        JLabel remainAmountDisplay = new JLabel("Currently asset in your account HKD$ ");
        remainAmountDisplay.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        remainAmountDisplay.setForeground(StandardColor.GreyHighest.getOppositeColorMode());
        remainAmountDisplay.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(taskTitle, gbc);
        gbc.gridy = 1;
        gbc.weighty = 0.9;
//        panel.add(getSelectionMenu("Menu","1 - Input receiver account number","2 - Exit",30, new Font(Font.SANS_SERIF, Font.PLAIN,30)), gbc);
        panel.add(receiveTransferInformation("Currently asset in your account HKD$1000.00"), gbc);
//        panel.add(confirmationStep("The system is going to transfer 33.00 to account number: 21111","Transfer Confirmation Operation:","1- Confirm the transfer","2-Cancel the transfer",40, new Font(Font.SANS_SERIF, Font.PLAIN,30)), gbc);
//        panel.add(afterTransaction("Total HK$ 33.00 transfers to account 21111.", new Font(Font.SANS_SERIF, Font.PLAIN,30)), gbc);

        mains.add(panel);
        mains.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        final double rate = 0.9;
        int screenWidth = (int) (screenSize.width * rate);
        int screenHeight = (int) (screenSize.height * rate);
        mains.setSize(screenWidth, screenHeight);
        mains.setLocationRelativeTo(null);
        mains.setVisible(true);
    }
}