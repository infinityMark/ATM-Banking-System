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

    private void initBaseActionConfigs() {
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
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                System.out.println(controller.getCurrentPanelName() + ", view Balance");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.WITHDRAW_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left3", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                // controller.goToPanel(ATMUI.LOGIN_PANEL);
                controller.goToPanel(ATMUI.GREETING_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.TRANSFER_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transfer");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.goToPanel(ATMUI.HISTORY_PANEL);
                controller.onShowHistoryPanel();
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transactionHistory");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right3", e -> {
            if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            } else {
                controller.goToPanel("mainMenu");
            }
        });

        // Keypad number buttons (0-9) - Add withdrawal panel handling
        for (int i = 0; i <= 9; i++) {
            final int number = i;
            addButtonListener(ATMUI.KEYPAD_PANEL, String.valueOf(i), e -> {
                if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                    atmUI.getLoginGUI().handleNumberInput(String.valueOf(number));
                } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                    // Handle transfer panel number input
                    if (atmUI.transferUI != null) {
                        atmUI.transferUI.handleNumberInput(String.valueOf(number));
                    }
                } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                    // Handle withdrawal panel number input
                    if (atmUI.withdrawGUI != null) {
                        atmUI.withdrawGUI.handleNumberInput(String.valueOf(number));
                    }
                } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                    controller.switchToLoginPanel();
                }
            });
        }
        
        // Confirm button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, "confirm", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.loginGUI.confirmBT() == true) {
                    atmUI.createPanelAfterLogin();
                    controller.loginSuccess(atmUI.loginGUI.getAccountNumber());
                    controller.switchToMainMenuPanel();
                }
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel confirm
                if (atmUI.transferUI != null) {
                    atmUI.transferUI.handleConfirm();
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel confirm
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleConfirm();
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            }
        });
        
        // Delete button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, "delete", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleDelete();
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel delete
                if (atmUI.transferUI != null) {
                    atmUI.transferUI.handleDelete();
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel delete
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleDelete();
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            }
        });
        
        // Clear button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, "clear", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleClear();
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel clear
                if (atmUI.transferUI != null) {
                    atmUI.transferUI.handleClear();
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel clear - 无论是否点击输入框都可用
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleClear();
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            }
        });

        // Double zero button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, "00", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleNumberInput("00");
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel double zero
                if (atmUI.transferUI != null) {
                    atmUI.transferUI.handleNumberInput("00");
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel double zero
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleNumberInput("00");
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            }
        });
        
        // Decimal point button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, ".", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleNumberInput(".");
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel decimal point
                if (atmUI.transferUI != null) {
                    atmUI.transferUI.handleNumberInput(".");
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel decimal point (will be ignored in withdrawal)
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleNumberInput(".");
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToLoginPanel();
            }
        });
    }

    /**
     * 添加Listener
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