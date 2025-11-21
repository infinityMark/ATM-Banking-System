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
        // 1. 只保留 Greeting 面板的点击事件（因为它不是按钮，而是整个面板）
        addMouseListener(
                GreetingUI.GREETING_PANEL,
                GreetingUI.GREETING_PANEL,
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        controller.switchToPanel(ATMUI.LOGIN_PANEL);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        ((Component) e.getSource()).setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        ((Component) e.getSource()).setCursor(java.awt.Cursor.getDefaultCursor());
                    }
                });
        // ... 其他鼠标事件
        addButtonListener("leftButtonPanel", "Left1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.BALANCE_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", view Balance");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.WITHDRAW_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("leftButtonPanel", "Left3", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.GREETING_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", withdraw");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.TRANSFER_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transfer");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.HISTORY_PANEL);
                controller.switchToPanel(ATMUI.HISTORY_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transactionHistory");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right3", e -> {
            if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else {
                //controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            }
        });

        // 2. 配置 Keypad 按钮（数字键、确认、删除等）
        configureKeypadButtons();

        // 3. 配置 WithdrawalUI 侧边按钮（删除其他面板的通用配置）
        configureWithdrawalSideButtons();
        configureTransferSideButtons();
        configureHistorySideButtons();

        // 4. 为其他面板（如 Balance、Transfer 等）按需添加专用配置

    }

    private void configureKeypadButtons() {
        // Confirm button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, "confirm", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.loginGUI.confirmBT() == true) {
                    atmUI.createPanelAfterLogin();
                    controller.loginSuccess(atmUI.loginGUI.getAccountNumber());
                    controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
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
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
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
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
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
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
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
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            }
        });

        // Decimal point button - Add withdrawal panel handling
        addButtonListener(ATMUI.KEYPAD_PANEL, ".", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleNumberInput(".");
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.transferUI != null)
                    atmUI.transferUI.handleNumberInput(".");
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.withdrawGUI != null)
                    atmUI.withdrawGUI.handleNumberInput(".");
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
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
                    controller.switchToPanel(ATMUI.LOGIN_PANEL);
                }
            });
        }
    }

    private void configureWithdrawalSideButtons() {
        // 在通用逻辑中，先检查当前面板是否为 WITHDRAW_PANEL
        // 如果是，则直接返回，不执行通用逻辑
        ActionListener withdrawalAwareListener = e -> {
            if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // 如果在 WithdrawalUI 面板，不执行任何操作
                // 让专用监听器处理
                return;
            }
        };
        // 否则执行原逻辑
        // 为每个侧边按钮配置 WithdrawalUI 专用逻辑
        String[] leftButtons = { "Left1", "Left2", "Left3" };
        String[] rightButtons = { "Right1", "Right2", "Right3" };

        for (String btnName : leftButtons) {
            addButtonListener("leftButtonPanel", btnName,
                    e -> handleWithdrawalPanel(btnName));
        }

        for (String btnName : rightButtons) {
            addButtonListener("rightButtonPanel", btnName,
                    e -> handleWithdrawalPanel(btnName));
        }
    }

    private void handleWithdrawalPanel(String buttonName) {
        // 检查是否在 WithdrawalUI 面板
        if (!ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName()) || atmUI.withdrawGUI == null) {
            return; // 不在正确的面板，直接返回
        }

        // 检查是否在结果页面
        if (atmUI.withdrawGUI.isOnResultScreen()) {
            atmUI.withdrawGUI.continueFromResultScreen();
            controller.refreshPanel("balance");
            controller.refreshHistoryPanel();
            // controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            System.out.println("onResultScreen");
            return;
        }

        // 检查是否在确认页面（关键修复：防止重复扣款）
        if (atmUI.withdrawGUI.isOnConfirmationScreen()) {
            // 确保只执行一次提款操作
            if (buttonName.startsWith("Left")) {
                atmUI.withdrawGUI.confirmWithdrawalFromSideButton();
            } else {
                atmUI.withdrawGUI.cancelWithdrawalFromSideButton();
            }
            return;
        }

        // 在菜单页面，根据按钮选择功能
        // 确保在切换卡片前停用自定义金额面板
        atmUI.withdrawGUI.deactivateCustomAmountPanel();

        switch (buttonName) {
            case "Left1":
                atmUI.withdrawGUI.selectAmount200();
                break;
            case "Left2":
                atmUI.withdrawGUI.selectAmount800();
                break;
            case "Left3":
                atmUI.withdrawGUI.selectCustomAmount();
                break;
            case "Right1":
                atmUI.withdrawGUI.selectAmount400();
                break;
            case "Right2":
                atmUI.withdrawGUI.selectAmount1000();
                break;
            case "Right3":
                atmUI.withdrawGUI.cancelWithdrawal();
                break;
        }
    }

    private void configureTransferSideButtons() {
        String[] leftButtons = { "Left1", "Left2", "Left3" };
        String[] rightButtons = { "Right1", "Right2", "Right3" };

        // 统一的 TransferUI 按钮处理器
        ActionListener transferButtonHandler = e -> {
            // 检查当前是否在 Transfer 面板
            if (!ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                return; // 不在 Transfer 面板，直接返回
            }

            TransferUI transferUI = atmUI.transferUI;
            if (transferUI == null)
                return;

            // 获取当前激活的卡片名称（需要在 TransferUI 中实现 getCurrentCardName() 方法）
            String currentCard = transferUI.getCurrentCardName();

            JButton sourceButton = (JButton) e.getSource();
            String buttonName = sourceButton.getName();
            boolean isLeftButton = buttonName.startsWith("Left");

            // 根据当前卡片和按钮位置执行相应操作
            switch (currentCard) {
                case TransferUI.CARD_MENU:
                case TransferUI.CARD_AFTER_TRANSACTION: // "也是同理" - 与 MENU 行为一致
                    if (isLeftButton) {
                        // 左侧按钮：跳转到输入信息页面
                        transferUI.showCard(TransferUI.CARD_INFO);
                    } else {
                        // 右侧按钮：返回主菜单（带刷新）
                        controller.refreshHistoryPanel();
                        controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
                    }
                    break;

                case TransferUI.CARD_INFO:
//                     INFO 页面侧边按钮无功能（由界面内部确认/返回按钮处理）
                    break;

                case TransferUI.CARD_CONFIRMATION:
                    if (isLeftButton) {
                        // 左侧按钮：确认转账，执行并跳转到完成页面
                        transferUI.executeTransfer();
                        transferUI.showCard(TransferUI.CARD_AFTER_TRANSACTION);
                    } else {
                        // 右侧按钮：取消，返回菜单
                        transferUI.showCard(TransferUI.CARD_MENU);
                    }
                    break;
            }
        };

        // 为所有侧边按钮注册统一的处理器
        for (String btnName : leftButtons) {
            addButtonListener("leftButtonPanel", btnName, transferButtonHandler);
        }
        for (String btnName : rightButtons) {
            addButtonListener("rightButtonPanel", btnName, transferButtonHandler);
        }
    }

    private void configureHistorySideButtons() {
        String[] leftButtons = { "Left1", "Left2", "Left3" };
        String[] rightButtons = { "Right1", "Right2", "Right3" };

        // 统一的 TransferUI 按钮处理器
        ActionListener transferButtonHandler = e -> {
            // 检查当前是否在 Transfer 面板
            if (!ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                return; // 不在 Transfer 面板，直接返回
            }

            TransactionHistoryUI transactionHistoryUI = atmUI.historyGUI;
            if (transactionHistoryUI == null)
                return;

            JButton sourceButton = (JButton) e.getSource();
//            String buttonName = sourceButton.getName();

            controller.refreshHistoryPanel();
            controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
        };

        // 为所有侧边按钮注册统一的处理器
        for (String btnName : leftButtons) {
            addButtonListener("leftButtonPanel", btnName, transferButtonHandler);
        }
        for (String btnName : rightButtons) {
            addButtonListener("rightButtonPanel", btnName, transferButtonHandler);
        }
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