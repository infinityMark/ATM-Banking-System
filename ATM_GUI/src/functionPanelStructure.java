import javax.swing.*;

public class functionPanelStructure extends JPanel {
    JPanel mainPanel = new JPanel();

    public functionPanelStructure(String methodName){
        JLabel title = new JLabel(methodName);

        mainPanel.add(title);
    }
}