import javax.swing.*;
import java.awt.*;

abstract public class FunctionPanelStructure extends JPanel {
    JPanel mainPanel = new JPanel(new GridBagLayout());
    JPanel contentPanel = new JPanel();
    JLabel taskTitle = new JLabel();

    public FunctionPanelStructure(String methodName){
        mainPanel.setBackground(StandardColor.GreyHighest.getColorMode());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        taskTitle.setText(methodName);
        taskTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        taskTitle.setForeground(StandardColor.Blue.getColorMode());
        taskTitle.setHorizontalAlignment(SwingConstants.LEFT);

        gbc.weighty = 0.1;
        mainPanel.add(taskTitle, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(contentPanel, gbc);
    }

    @Override
    public void updateUI() {
        super.updateUI();
    }

    public JPanel getPanelUI(){
        return mainPanel;
    }

    public void passInformation(String information){
        taskTitle.setText(information);
        taskTitle.revalidate();
        taskTitle.repaint();
    }
}