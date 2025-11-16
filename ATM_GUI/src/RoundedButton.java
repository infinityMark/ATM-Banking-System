import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.*;

public class RoundedButton extends JButton{
//    private int borderWidth = 50;
    private boolean isroundedStatus;
    private int cornerRadius = 15;

    public void setroundedStatus(boolean roundedStatuss){
        isroundedStatus = roundedStatuss;
    }

    public RoundedButton(String content,String changedContent,Color defaultBackgroundColor,Color changedBackgroundColor,Color defaultFontColor,
                         Color changedFontColor,Font fontDefaultStyle,Font fontChangedSize,boolean roundedStatus,int widths,int heights) {

        super(content);
        setOpaque(false);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBackground(defaultBackgroundColor);
        setForeground(defaultFontColor);

        setFont(fontDefaultStyle);
//        setPreferredSize(new Dimension(widths,heights));
        this.setMaximumSize(new Dimension(widths,heights));


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setFont(fontChangedSize);
                setForeground(changedFontColor);
                setBackground(changedBackgroundColor);
                setText(changedContent);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setFont(fontDefaultStyle);
                setForeground(defaultFontColor);
                setBackground(defaultBackgroundColor);
                setText(content);
            }
        });

        setroundedStatus(roundedStatus);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(getBackground());
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

        // 绘制边框
//        g2d.setColor(borderColor);
//        g2d.setStroke(new BasicStroke(borderWidth));
//        g2d.drawRoundRect(borderWidth / 2, borderWidth / 2,
//                getWidth() - borderWidth, getHeight() - borderWidth,
//                cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

    public void setBorderColor(Color color) {
//        this.borderColor = color;
//        this.defaultBorderColor = color;
        repaint();
    }

}