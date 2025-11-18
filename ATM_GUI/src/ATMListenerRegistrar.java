
/**
 * ATMListenerRegistrar ：统一管理所有UI组件的监听器注册
 * 
 * 使用方法：
 * 调用addButtonListener()添加按钮监听器配置
 */

import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.FocusListener;
import java.awt.event.KeyListener;
import java.awt.Container;

public class ATMListenerRegistrar {
    private final ATMUI atmUI;
    private final ATMUIController controller;

    // A list to store all button action configurations
    private final List<Object[]> actionConfigs = new ArrayList<>();

    public ATMListenerRegistrar(ATMUI atmUI, ATMUIController controller) {
        this.atmUI = atmUI;
        this.controller = controller;

        initBaseActionConfigs();
    }

    /**
     * 在此可以加入你想要的Listener配置
     */
    private void initBaseActionConfigs() {
        // Login Panel
        addButtonListener(ATMUI.LOGIN_PANEL, "Login", e -> controller.handleLogin());
        // System.out.println("Login button listener added.");

        // Main Menu
        addButtonListener(ATMUI.MAIN_MENU_PANEL, "View Balance",
                e -> controller.switchToBalancePanel());
        addButtonListener(ATMUI.MAIN_MENU_PANEL, "Withdraw Cash",
                e -> controller.switchToWithdrawPanel());
        addButtonListener(ATMUI.MAIN_MENU_PANEL, "Transfer Funds",
                e -> controller.switchToTransferPanel());
        addButtonListener(ATMUI.MAIN_MENU_PANEL, "Account Info",
                e -> controller.showAccountInfo());
        addButtonListener(ATMUI.MAIN_MENU_PANEL, "Exit",
                e -> controller.exitSystem());

        addButtonListener(ATMUI.test,"test",
                e -> controller.switchToMainMenuPanel());

        addButtonListener("leftButtonPanel", "Left1", e -> controller.showInfo());
        // System.out.println("have link");

        addButtonListener("leftButtonPanel", "Left2", e -> controller.goToPanel("mainMenu"));
    
        addButtonListener("leftButtonPanel", "Left3", e -> controller.goToPanel("mainMenu"));
    }


    /**
     * 添加按钮Listener
     * 
     * @param panelName  面板名称（需与ATMUI中的面板名称常量一致）
     * @param buttonName 按钮名称（用于查找按钮）
     * @param listener   按钮点击监听器（可由组员实现具体逻辑）
     */
    // 1. 处理按钮点击事件
    public void addButtonListener(String panelName, String buttonName, ActionListener listener) {
        actionConfigs.add(new Object[] { panelName, buttonName, "action", listener });
    }

    // 2. 处理输入框焦点事件
    public void addTextFieldFocusListener(String panelName, String textFieldName, FocusListener listener) {
        actionConfigs.add(new Object[] { panelName, textFieldName, "focus", listener });
    }

    // 3. 处理键盘事件
    public void addKeyListener(String panelName, String componentName, KeyListener listener) {
        actionConfigs.add(new Object[] { panelName, componentName, "key", listener });
    }

    // 遍历所有配置，按类型绑定监听器
    public void registerAllListeners() {
        for (Object[] config : actionConfigs) {
            String panelName = (String) config[0];
            String componentName = (String) config[1];
            String type = (String) config[2];
            Object listener = config[3];

            JPanel panel = atmUI.getPanel(panelName);
            if (panel == null)
                continue;

            // 找组件（按名称找，比按文本可靠）
            Component comp = findComponentByName(panel, componentName);
            if (comp == null)
                continue;

            // 按类型绑定监听器
            if (type.equals("action") && comp instanceof JButton) {
                ((JButton) comp).addActionListener((ActionListener) listener);
            } else if (type.equals("focus") && comp instanceof JTextField) {
                ((JTextField) comp).addFocusListener((FocusListener) listener);
            } else if (type.equals("key")) {
                comp.addKeyListener((KeyListener) listener);
            }
        }
    }

    // 辅助方法：按组件名称查找（组件需用setName设置名称）
    private Component findComponentByName(Container container, String name) {
        for (Component comp : container.getComponents()) {
            if (name.equals(comp.getName())) {
                return comp;
            }
            // 递归找子组件（如果组件里还有面板）
            if (comp instanceof Container) {
                Component child = findComponentByName((Container) comp, name);
                if (child != null)
                    return child;
            }
        }
        return null;
    }
}
/*
 * Some part is done by AI, Will improve later, do not judge me :)
 * change if needed
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 */