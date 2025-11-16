import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextShowing extends JFrame {
    public static TextFields receiverAccountTextField;

    private static class ButtonHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String text = receiverAccountTextField.getText();
            if ("1".equals(text)) {
                receiverAccountTextField.setBackground(StandardColor.Red.getColor(0));
            }
        }
    }

    public static void main(String[] args) {
        JFrame mains = new JFrame();
        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 10));
//        TextFieldsComponents test;

        // 创建按钮
        RoundedButton confirmationButton = new RoundedButton("Confirm", "Confirm",
                StandardColor.GreyHighest.getColorMode(),
                StandardColor.Green.getColorMode(),
                StandardColor.GreyHighest.getOppositeColorMode(),
                StandardColor.GreyHighest.getColorMode(),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
                true, 200, 10);

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        receiverAccountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        TextFields amountTextField = new TextFields(200, 30,
                StandardColor.GreyHighest.getColor(0),
                StandardColor.Blue.getColor(0),
                StandardColor.GreyHighest.getColor(1),new Font(Font.SANS_SERIF, Font.BOLD, 40));

        JLabel receiver = new JLabel("Receiver account:");
        receiver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        JLabel amount = new JLabel("Amount:");
        amount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));



        panel.add(receiver);
        panel.add(receiverAccountTextField);
        panel.add(amount);
        panel.add(amountTextField);

        confirmationButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(confirmationButton);

        ButtonHandler handler = new ButtonHandler();
        confirmationButton.addActionListener(handler);

        mains.add(panel);
        mains.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mains.setSize(400, 400);
        mains.setLocationRelativeTo(null);
        mains.setVisible(true);
    }
}