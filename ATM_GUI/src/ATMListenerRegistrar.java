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
                
        // 2. Configure the keypad buttons (number keys, confirm, delete, etc.).
        configureKeypadButtons();
        
        
        // ... Other mouse events
        /*addButtonListener("leftButtonPanel", "Left1", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.BALANCE_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
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
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
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
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
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
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transfer");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right2", e -> {
            if (ATMUI.MAIN_MENU_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.HISTORY_PANEL);
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else {
                System.out.println(controller.getCurrentPanelName() + ", transactionHistory");
                controller.showInfo();
            }
        });

        addButtonListener("rightButtonPanel", "Right3", e -> {
            if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (ATMUI.BALANCE_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else if (ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            } else {
                // controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            }
        });*/

        // 3. Configure the WithdrawalUI side buttons (remove the common configuration from other panels).
        //configureWithdrawalSideButtons();
        //configureTransferSideButtons();

    }

    private void configureKeypadButtons() {
        // Confirm button
        addButtonListener(ATMUI.KEYPAD_PANEL, "confirm", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.loginGUI.confirmBT() == true) {
                    atmUI.createPanelAfterLogin();
                    controller.loginSuccess(atmUI.loginGUI.getAccountNumber());
                    controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
                }
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel confirm
                if (atmUI.transferGUI != null) {
                    atmUI.transferGUI.handleConfirm();
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

        // Delete button
        addButtonListener(ATMUI.KEYPAD_PANEL, "delete", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleDelete();
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel delete
                if (atmUI.transferGUI != null) {
                    atmUI.transferGUI.handleDelete();
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

        // Clear button
        addButtonListener(ATMUI.KEYPAD_PANEL, "clear", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleClear();
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel clear
                if (atmUI.transferGUI != null) {
                    atmUI.transferGUI.handleClear();
                }
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle withdrawal panel clear
                if (atmUI.withdrawGUI != null) {
                    atmUI.withdrawGUI.handleClear();
                }
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            }
        });

        // Double zero button
        addButtonListener(ATMUI.KEYPAD_PANEL, "00", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleNumberInput("00");
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                // Handle transfer panel double zero
                if (atmUI.transferGUI != null) {
                    atmUI.transferGUI.handleNumberInput("00");
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

        // Decimal point button
        addButtonListener(ATMUI.KEYPAD_PANEL, ".", e -> {
            if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                atmUI.getLoginGUI().handleNumberInput(".");
            } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.transferGUI != null)
                    atmUI.transferGUI.handleNumberInput(".");
            } else if (ATMUI.WITHDRAW_PANEL.equals(controller.getCurrentPanelName())) {
                if (atmUI.withdrawGUI != null)
                    atmUI.withdrawGUI.handleNumberInput(".");
            } else if (ATMUI.GREETING_PANEL.equals(controller.getCurrentPanelName())) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            }
        });

        // Keypad number buttons (0-9)
        for (int i = 0; i <= 9; i++) {
            final int number = i;
            addButtonListener(ATMUI.KEYPAD_PANEL, String.valueOf(i), e -> {
                if (ATMUI.LOGIN_PANEL.equals(controller.getCurrentPanelName())) {
                    atmUI.getLoginGUI().handleNumberInput(String.valueOf(number));
                } else if (ATMUI.TRANSFER_PANEL.equals(controller.getCurrentPanelName())) {
                    // Handle transfer panel number input
                    if (atmUI.transferGUI != null) {
                        atmUI.transferGUI.handleNumberInput(String.valueOf(number));
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

        //back to main menu button
        addButtonListener(ATMUI.KEYPAD_PANEL, "back to main menu", e -> {
            String currentPanel = controller.getCurrentPanelName();
            System.out.println("Back to Main menu pressed from: " + currentPanel);
            if (ATMUI.GREETING_PANEL.equals(currentPanel)) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (!ATMUI.MAIN_MENU_PANEL.equals(currentPanel) && 
            !ATMUI.LOGIN_PANEL.equals(currentPanel)) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            }
        });
    }
    /*
    private void configureWithdrawalSideButtons() {
        atmUI.removeAllButtonListenersCompletely(atmUI.getLeftButton(), atmUI.getRightButton());
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

        String currentCard = atmUI.withdrawGUI.getCurrentCardName();

        if ("CUSTOM".equals(currentCard)) {
            // 左侧按钮：确认输入的金额
            if (buttonName.startsWith("Left")) {
                atmUI.withdrawGUI.handleConfirm();
                System.out.println("自定义输入金额——成功");
            }
            // 右侧按钮：返回取款菜单，放弃自定义输入
            else if (buttonName.startsWith("Right")) {
                atmUI.withdrawGUI.resetToInitialState(); // 返回取款菜单
                atmUI.withdrawGUI.deactivateCustomAmountPanel(); // 停用自定义面板状态
                System.out.println("自定义输入金额——返回");
            }
            return;
        }

        // 检查是否在结果页面
        if (atmUI.withdrawGUI.isOnResultScreen()) {
            atmUI.withdrawGUI.continueFromResultScreen();
            controller.refreshPanel("balance");
            controller.refreshHistoryPanel();
            System.out.println("onResultScreen");
            return;
        }

        if ("CONFIRMATION".equals(currentCard)) {
            controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            return;
        }

        if (atmUI.withdrawGUI.isOnConfirmationScreen()) {

            if (buttonName.startsWith("Left")) {
                atmUI.withdrawGUI.confirmWithdrawalFromSideButton();
                System.out.println("确认");

            } else {
                atmUI.withdrawGUI.cancelWithdrawalFromSideButton();
                System.out.println("取消");

            }
            return;
        }

        atmUI.withdrawGUI.deactivateCustomAmountPanel();

        switch (buttonName) {
            case "Left1":
                atmUI.withdrawGUI.selectAmount200();
                System.out.println("200");
                break;
            case "Left2":
                atmUI.withdrawGUI.selectAmount800();
                System.out.println("800");
                break;
            case "Left3":
                atmUI.withdrawGUI.selectCustomAmount();
                System.out.println("customAmount");
                break;
            case "Right1":
                atmUI.withdrawGUI.selectAmount400();
                System.out.println("400");
                break;
            case "Right2":
                atmUI.withdrawGUI.selectAmount1000();
                System.out.println("1000");
                break;
            case "Right3":
                atmUI.withdrawGUI.cancelWithdrawal();
                System.out.println("Cancel");
                break;
        }
    }

    private void configureTransferSideButtons() {
        atmUI.removeAllButtonListenersCompletely(atmUI.getLeftButton(), atmUI.getRightButton());

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

            // 获取当前激活的卡片名称
            String currentCard = transferUI.getCurrentCardName();

            JButton sourceButton = (JButton) e.getSource();
            String buttonName = sourceButton.getName();
            boolean isLeftButton = buttonName.startsWith("Left");

            // 根据当前卡片和按钮位置执行相应操作
            switch (currentCard) {
                case TransferUI.CARD_MENU:
                    if (isLeftButton) {
                        // 左侧按钮：跳转到输入信息页面
                        transferUI.showCard(TransferUI.CARD_INFO);
                    } else {
                        // 右侧按钮：返回主菜单
                        returnToMainMenu(transferUI);
                    }
                    break;

                case TransferUI.CARD_AFTER_TRANSACTION:
                    if (isLeftButton) {
                        // 左侧按钮：开始新的转账，重置状态
                        transferUI.resetToInitialState();
                        transferUI.showCard(TransferUI.CARD_INFO);
                    } else {
                         //右侧按钮：返回主菜单
                        returnToMainMenu(transferUI);
                        //ATMUI atmui = new ATMUI();
                    }
                    break;

                case TransferUI.CARD_INFO:
                    // INFO 页面侧边按钮无功能（由界面内部确认/返回按钮处理）
                    break;

                case TransferUI.CARD_CONFIRMATION:
                    if (isLeftButton) {
                        // 左侧按钮：确认转账，执行并跳转到完成页面
                        transferUI.executeTransfer();
                        transferUI.showCard(TransferUI.CARD_AFTER_TRANSACTION);
                        // 如果失败，保持在确认页面显示错误信息
                    } else {
                        // 右侧按钮：取消，返回菜单
                        transferUI.showCard(TransferUI.CARD_MENU);
                    }
                    break;

                default:
                    returnToMainMenu(transferUI);
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
    */
    /**
     * Unified handling of the logic for returning to the main menu
     */
    private void returnToMainMenu(TransferUI transferUI) {
        System.out.println("=== Start execute returnToMainMenu ===");
        System.out.println("1. Is transferUI null: " + (transferUI == null));
        System.out.println("2. Is the controller null: " + (controller == null));
        System.out.println("3. Current panel:: " + controller.getCurrentPanelName());

        // 1. Refresh relevant panel data
        System.out.println("4. Refresh History Panel...");
        controller.refreshHistoryPanel();
        System.out.println("Refresh complete");

        // 2. Reset transfer UI status
        if (transferUI != null) {
            System.out.println("5. Try resetting the transfer UI state..");
            try {
                // First check if there is a reset method.
                java.lang.reflect.Method resetMethod = transferUI.getClass().getMethod("resetToInitialState");
                if (resetMethod != null) {
                    transferUI.resetToInitialState();
                    System.out.println("The reset method call was successful.");
                }
            } catch (NoSuchMethodException e) {
                System.out.println("The resetToInitialState method was not found; showCard was used instead.");
                transferUI.showCard(TransferUI.CARD_MENU);
            } catch (Exception e) {
                System.out.println("Reset method call exception: " + e.getMessage());
                transferUI.showCard(TransferUI.CARD_MENU);
            }
        } else {
            System.out.println("5. If transferUI is null, skip the reset.");
        }

        // 3. Switch to main menu panel
        System.out.println("6. Preparing to switch to the main menu panel...");
        try {
            controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            System.out.println("7. The panel switching method call is complete.");
            System.out.println("Switch to the current panel: " + controller.getCurrentPanelName());
        } catch (Exception e) {
            System.out.println("An error occurred while switching panels.: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("=== returnToMainMenu Execution completed ===");
    }

    private void configureHistorySideButtons() {
        String[] leftButtons = { "Left1", "Left2", "Left3" };
        String[] rightButtons = { "Right1", "Right2", "Right3" };

        // Unified TransferUI button handler
        ActionListener transferButtonHandler = e -> {
            // Check if you are currently in the Transfer panel.
            if (!ATMUI.HISTORY_PANEL.equals(controller.getCurrentPanelName())) {
                return; // If you're not in the Transfer panel, go directly back.
            }

            TransactionHistoryUI transactionHistoryUI = atmUI.historyGUI;
            if (transactionHistoryUI == null)
                return;

            JButton sourceButton = (JButton) e.getSource();
            // buttonName = sourceButton.getName();

            controller.refreshHistoryPanel();
            controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
        };

        // Register a unified processor for all side buttons
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
     * @param panelName Panel name (must match the panel name constant in ATMUI)
     * @param buttonName Button name (used to find the button
     * @param listener Button click listener (specific logic can be implemented by team members)
     */
    // 1. Handling button click events
    public void addButtonListener(String panelName, String buttonName, ActionListener listener) {
        actionConfigs.add(new Object[] { panelName, buttonName, "action", listener });
    }

    // 2. 处理输入框焦点事件
    /*public void addTextFieldFocusListener(String panelName, String textFieldName, FocusListener listener) {
        actionConfigs.add(new Object[] { panelName, textFieldName, "focus", listener });
    }*/

    // 3. 处理键盘事件
    /*public void addKeyListener(String panelName, String componentName, KeyListener listener) {
        actionConfigs.add(new Object[] { panelName, componentName, "key", listener });
    }*/

    // 4. Handling mouse events
    public void addMouseListener(String panelName, String componentName, MouseListener listener) {
        actionConfigs.add(new Object[] { panelName, componentName, "mouse", listener });
    }

    // Iterate through all configurations and bind listeners by type.
    public void registerAllListeners() {
        for (Object[] config : actionConfigs) {
            String panelName = (String) config[0];
            String componentName = (String) config[1];
            String type = (String) config[2];
            Object listener = config[3];

            JPanel panel = atmUI.getPanel(panelName);
            if (panel == null)
                continue;

            // Find components (searching by name is more reliable than searching by text).
            Component comp = findComponentByName(panel, componentName);
            if (comp == null)
                continue;

            // Bind listeners by type
            if (type.equals("action") && comp instanceof JButton) {
                ((JButton) comp).addActionListener((ActionListener) listener);
            } else if (type.equals("focus") && comp instanceof JTextField) {
                ((JTextField) comp).addFocusListener((FocusListener) listener);
            } else if (type.equals("key")) {
                comp.addKeyListener((KeyListener) listener);
            } else if (type.equals("mouse")) { // Handling mouse listeners
                comp.addMouseListener((MouseListener) listener);
            }
        }
    }

    private Component findComponentByName(Container container, String name) {
        // First check if the container itself matches the name.
        if (name.equals(container.getName())) {
            return container;
        }

        // Then find the child components.
        for (Component comp : container.getComponents()) {
            if (name.equals(comp.getName())) {
                return comp;
            }
            // Recursive search for subcomponents
            if (comp instanceof Container) {
                Component child = findComponentByName((Container) comp, name);
                if (child != null)
                    return child;
            }
        }
        return null;
    }
}