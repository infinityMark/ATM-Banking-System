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

        // back to main menu button
        /*addButtonListener(ATMUI.KEYPAD_PANEL, "back to main menu", e -> {
            String currentPanel = controller.getCurrentPanelName();
            System.out.println("Back to Main menu pressed from: " + currentPanel);
            if (ATMUI.GREETING_PANEL.equals(currentPanel)) {
                controller.switchToPanel(ATMUI.LOGIN_PANEL);
            } else if (!ATMUI.MAIN_MENU_PANEL.equals(currentPanel) &&
                    !ATMUI.LOGIN_PANEL.equals(currentPanel)) {
                controller.switchToPanel(ATMUI.MAIN_MENU_PANEL);
            }
        });*/
    }

    // 1. Handling button click events
    public void addButtonListener(String panelName, String buttonName, ActionListener listener) {
        actionConfigs.add(new Object[] { panelName, buttonName, "action", listener });
    }

    // 2. Handling mouse events
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