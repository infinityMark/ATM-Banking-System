import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.annotation.Repeatable;

public class IndicateButton extends JPanel{
    private boolean indicatePasswordJudgement = false;
    private final JPasswordField passwordField;
    private final JButton toggleButton;

    public void setIndicatePasswordJudgement(boolean changed){
        indicatePasswordJudgement = changed;
    }

    public boolean getindicatePasswordJudgement(){
        return indicatePasswordJudgement;
    }

    public IndicateButton(Passwords passwordField, RoundedButton button,int width, int height){

        this.passwordField = passwordField;
        this.toggleButton = button;

        setLayout(new BorderLayout(10,0));
        setPreferredSize(new Dimension(width, height));
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        add(passwordField, BorderLayout.CENTER);
        add(button, BorderLayout.EAST);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTextInPasswordFiel(getindicatePasswordJudgement());
            }
        });

        setupButtonHoverEffects();
    }

    public void setupButtonHoverEffects(){
        toggleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                showTextInPasswordFiel(getindicatePasswordJudgement());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                showTextInPasswordFiel(getindicatePasswordJudgement());
            }
        });
    }

    public void showTextInPasswordFiel(boolean status){
        setIndicatePasswordJudgement( !(indicatePasswordJudgement) );
        if(indicatePasswordJudgement == true){
            passwordField.setEchoChar('\0');
        }else{
            passwordField.setEchoChar('•');
        }
    }

    @Override
    public String getToolTipText() {
        return super.getToolTipText();
    }
}