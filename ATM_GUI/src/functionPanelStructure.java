import javax.swing.*;
import java.awt.*;

abstract public class functionPanelStructure extends JPanel {
    JPanel mainPanel = new JPanel();
    JPanel textPanel = new JPanel();
    JLabel inTextLabel = new JLabel();
    JLabel backButton = new JLabel(new ImageIcon(ClassLoader.getSystemResource("resources/main-menu.png")));

    public functionPanelStructure(String methodName){
        mainPanel.setLayout(new BorderLayout());

        // 标题标签
        JLabel label = new JLabel(methodName);
        label.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 32));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(label, BorderLayout.NORTH);

        textPanel.setLayout(new BorderLayout());
        inTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inTextLabel.setVerticalAlignment(SwingConstants.CENTER);
        textPanel.add(inTextLabel, BorderLayout.CENTER);

        mainPanel.add(textPanel, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    public JPanel getPanelUI(){
        return mainPanel;
    }

    public void passInformation(String information){
        inTextLabel.setText(information);
        inTextLabel.revalidate();
        inTextLabel.repaint();
    }
}