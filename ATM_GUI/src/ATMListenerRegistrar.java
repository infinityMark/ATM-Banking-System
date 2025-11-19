
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
import java.awt.event.MouseListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
        // addButtonListener(ATMUI.GREETING_PANEL, "greeting", e ->
        // controller.handleGreeting());
        // System.out.println("Login button listener added.");

        // addButtonListener(ATMUI.LOGIN_PANEL, "Login", e -> controller.handleLogin());
        // System.out.println("Login button listener added.");

        // Main Menu
        /*
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "View Balance",
         * e -> controller.switchToBalancePanel());
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "Withdraw Cash",
         * e -> controller.switchToWithdrawPanel());
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "Transfer Funds",
         * e -> controller.switchToTransferPanel());
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "Account Info",
         * e -> controller.showAccountInfo());
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "Transaction History",
         * e -> controller.showTransactionHistory());
         * addButtonListener(ATMUI.MAIN_MENU_PANEL, "Exit",
         * e -> controller.switchToGreetingPanel());
         * // addButtonListener(ATMUI.test, "test",
         * // e -> controller.switchToMainMenuPanel());
         */

        addMouseListener(
                GreetingUI.GREETING_PANEL,
                GreetingUI.GREETING_PANEL,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        controller.switchToLoginPanel();
                        System.out.println("Clicked");
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((Component) e.getSource()).setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                        System.out.println("Entred");
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((Component) e.getSource()).setCursor(java.awt.Cursor.getDefaultCursor());
                        System.out.println("Exited");
                    }
                });

        addButtonListener("leftButtonPanel", "Left1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.BALANCE_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", view Balance");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.WITHDRAW_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left3", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                // controller.goToPanel(ATMUI.LOGIN_PANEL);
                controller.goToPanel(ATMUI.GREETING_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.TRANSFER_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transfer");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.HISTORY_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transactionHistory");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right3", e -> controller.goToPanel("mainMenu"));

        // Keypad number buttons (0-9)
        for (int i = 0; i <= 9; i++) {
            final int number = i;
            addButtonListener(ATMUI.KEYPAD_PANEL, String.valueOf(i), e -> {
                if (atmUI.getLoginGUI() != null) {
                    atmUI.getLoginGUI().handleNumberInput(String.valueOf(number));
                }
            });
        }
        
        
        
        addButtonListener(ATMUI.KEYPAD_PANEL, "confirm", e -> {
            if (atmUI.getLoginGUI() != null) {
                atmUI.getLoginGUI().handleConfirm();
            } else {
                System.out.println("LoginGUI is null");
            }
        });

        addButtonListener(ATMUI.KEYPAD_PANEL, "delete", e -> {
            if (atmUI.getLoginGUI() != null) {
                atmUI.getLoginGUI().handleDelete();
            } else {
                System.out.println("LoginGUI is null");
            }
        });

        addButtonListener(ATMUI.KEYPAD_PANEL, "clear", e -> {
            if (atmUI.getLoginGUI() != null) {
                atmUI.getLoginGUI().handleClear();
            } else {
                System.out.println("LoginGUI is null");
            }
        });
        
        // Double zero button
        addButtonListener(ATMUI.KEYPAD_PANEL, "00", e -> {
            if (atmUI.getLoginGUI() != null) {
                atmUI.getLoginGUI().handleNumberInput(".");
            }
        });
        
        // Double zero button
        addButtonListener(ATMUI.KEYPAD_PANEL, "00", e -> {
            if (atmUI.getLoginGUI() != null) {
                atmUI.getLoginGUI().handleNumberInput("00");
            }
        });
        addButtonListener(ATMUI.GREETING_PANEL, ATMUI.GREETING_PANEL, e -> {
            controller.switchToLoginPanel();
        });
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

    // 4. 处理鼠标事件
    public void addMouseListener(String panelName, String componentName, MouseListener listener) {
        actionConfigs.add(new Object[] { panelName, componentName, "mouse", listener });
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
            } else if (type.equals("mouse")) { // 处理鼠标监听器
                comp.addMouseListener((MouseListener) listener);
            }
        }
    }

    // 辅助方法：按组件名称查找（组件需用setName设置名称）
    /*private Component findComponentByName(Container container, String name) {
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
    }*/

    private Component findComponentByName(Container container, String name) {
        // 先检查容器本身是否匹配名称
        if (name.equals(container.getName())) {
            return container;
        }

        // 然后查找子组件
        for (Component comp : container.getComponents()) {
            if (name.equals(comp.getName())) {
                return comp;
            }
            // 递归查找子组件
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