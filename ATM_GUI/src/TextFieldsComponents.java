//import javax.swing.*;
//import java.awt.*;
//
//public class TextFieldsComponents extends JPanel {
//    private JLabel label;
//    private TextFields textField;
//    private JLabel statusLabel;
//
//    public TextFieldsComponents(String labelText, Font labelFont,
//                                 int fieldWidth, int fieldHeight,
//                                 Color backgroundColor, Color hoverColor, Color borderColor,
//                                 String statusText, Font statusFont) {
//
//        JFrame mains = new JFrame();
//        JPanel panel = new JPanel(new GridLayout(7, 1, 5, 10));
//        TextFieldsComponents test;
//
//        RoundedButton confirmationButton = new RoundedButton("Confirm", "Confirm",
//                StandardColor.GreyHighest.getColorMode(),
//                StandardColor.Green.getColorMode(),
//                StandardColor.GreyHighest.getOppositeColorMode(),
//                StandardColor.GreyHighest.getColorMode(),
//                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
//                new Font(Font.SANS_SERIF, Font.PLAIN, 16),
//                true, 200, 10);
//
//        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
//
//        receiverAccountTextField = new TextFields(200, 30,
//                StandardColor.GreyHighest.getColor(0),
//                StandardColor.Blue.getColor(0),
//                StandardColor.GreyHighest.getColor(1));
//
//        TextFields amountTextField = new TextFields(200, 30,
//                StandardColor.GreyHighest.getColor(0),
//                StandardColor.Blue.getColor(0),
//                StandardColor.GreyHighest.getColor(1));
//
//        JLabel receiver = new JLabel("Receiver account:");
//        receiver.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//
//        JLabel amount = new JLabel("Amount:");
//        amount.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//
//
//
//        panel.add(receiver);
//        panel.add(receiverAccountTextField);
//        panel.add(amount);
//        panel.add(amountTextField);
//
//        confirmationButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//        panel.add(confirmationButton);
//
//        TextShowing.ButtonHandler handler = new TextShowing.ButtonHandler();
//        confirmationButton.addActionListener(handler);
//
//        mains.add(panel);
//        mains.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        mains.setSize(400, 400);
//        mains.setLocationRelativeTo(null);
//        mains.setVisible(true);
//    }
//    }
//
//    // Getter方法
//    public TextFields getTextField() {
//        return textField;
//    }
//
//    public String getText() {
//        return textField.getText();
//    }
//
//    public void setText(String text) {
//        textField.setText(text);
//    }
//
//    public void setStatus(String status, Color color) {
//        statusLabel.setText(status);
//        statusLabel.setForeground(color);
//    }
//}