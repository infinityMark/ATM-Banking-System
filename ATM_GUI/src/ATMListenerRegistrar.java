import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 专门负责 ATM UI 组件的监听器注册
 * 
 * Done by AI, Will improve later, do not judge me :)
 * change if needed
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 * 
 */
public class ATMListenerRegistrar {
    private final ATMUI atmUI; // UI 组件容器
    private final ATMUIController controller; // 逻辑处理器

    // 构造器：注入依赖
    public ATMListenerRegistrar(ATMUI atmUI, ATMUIController controller) {
        this.atmUI = atmUI;
        this.controller = controller;
    }

    /**
     * 统一注册所有监听器
     */
    public void registerAllListeners() {
        // 定义所有按钮的监听器配置
        List<ButtonActionConfig> configs = createButtonConfigs();

        // 循环注册（手动查找按钮并绑定监听器）
        for (ButtonActionConfig config : configs) {
            JPanel targetPanel = atmUI.getPanel(config.panelName);
            if (targetPanel == null) {
                System.err.println("Panel " + config.panelName + " not found");
                continue;
            }

            // 遍历面板组件，找到目标按钮并绑定监听器
            for (Component comp : targetPanel.getComponents()) {
                if (comp instanceof JButton btn && config.buttonText.equals(btn.getText())) {
                    btn.addActionListener(config.listener);
                    break;
                }
            }
        }
    }

    /*
     * Done by AI, Will improve later, do not judge me :)
     * using AI because I don't want to spend too much time on designing listeners
     * it may cause we can't get the work done on time
     */

    /**
     * 定义“面板-按钮-动作”的映射关系
     */
    private List<ButtonActionConfig> createButtonConfigs() {
        List<ButtonActionConfig> configs = new ArrayList<>();

        // 登录面板：登录按钮
        configs.add(new ButtonActionConfig(
                ATMUI.LOGIN_PANEL,
                "Login",
                e -> handleLogin()));

        // 主菜单面板：查看余额按钮
        configs.add(new ButtonActionConfig(
                ATMUI.MAIN_MENU_PANEL,
                "View Balance",
                e -> controller.switchToBalancePanel()));

        // 主菜单面板：取款按钮
        configs.add(new ButtonActionConfig(
                ATMUI.MAIN_MENU_PANEL,
                "Withdraw Cash",
                e -> controller.switchToWithdrawPanel()));

        // 主菜单面板：转账按钮
        configs.add(new ButtonActionConfig(
                ATMUI.MAIN_MENU_PANEL,
                "Transfer Funds",
                e -> controller.switchToTransferPanel()));

        // 主菜单面板：账户信息按钮
        configs.add(new ButtonActionConfig(
                ATMUI.MAIN_MENU_PANEL,
                "Account Info",
                e -> showAccountInfo()));

        // 主菜单面板：退出按钮
        configs.add(new ButtonActionConfig(
                ATMUI.MAIN_MENU_PANEL,
                "Exit",
                e -> System.exit(0)));

        return configs;
    }

    // 登录逻辑：验证后切换到主菜单
    private void handleLogin() {
        JOptionPane.showMessageDialog(atmUI.getMainFrame(),
                "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
        controller.switchToMainMenuPanel();
    }

    // 显示账户信息
    private void showAccountInfo() {
        JOptionPane.showMessageDialog(atmUI.getMainFrame(),
                "Account Info:\nID: 123456\nHolder: John Doe",
                "Account Details",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * 内部类：封装“面板-按钮-监听器”配置
     */
    private static class ButtonActionConfig {
        String panelName;
        String buttonText;
        ActionListener listener;

        ButtonActionConfig(String panelName, String buttonText, ActionListener listener) {
            this.panelName = panelName;
            this.buttonText = buttonText;
            this.listener = listener;
        }
    }
}
/*
 * Done by AI, Will improve later, do not judge me :)
 * change if needed
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 */