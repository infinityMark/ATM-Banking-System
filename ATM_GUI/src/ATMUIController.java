
//import java.awt.CardLayout;
//import java.awt.Container;
import javax.swing.*;

/**
 * controller class for ATMUI
 */
public class ATMUIController {
    // private Container mainContainer;
    // private CardLayout cardLayout;
    private ATMUI atmUI;
    // private ATM atm;
    private ATMListenerRegistrar listenerRegistrar;
    private int accountNumber;

    /*
     * public ATMUIController(ATMUI atmUI, ATM atm) {
     * this.atmUI = atmUI;
     * this.atm = atm;
     * this.listenerRegistrar = new ATMListenerRegistrar(atmUI, this);
     * }
     */

    public ATMUIController(){}

    public ATMUIController(ATMUI atmUI) {
        this.atmUI = atmUI;
        this.listenerRegistrar = new ATMListenerRegistrar(atmUI, this);
    }

    public void run() {
        atmUI.initializeUI();
        setup(); // register all listeners
        getMainFrame().setVisible(true); // show UI
        // atm.run();
    }

    // ------------- General method for switching panels -------------
    public void switchToPanel(String panelName) {
        refreshPanel(panelName);
        atmUI.switchPanel(panelName);
    }

    public void refreshHistoryPanel() {
        atmUI.historyGUI.refreshHistory(accountNumber);
    }

    public void refreshPanel(String name) {
        if (atmUI.balanceGUI == null || atmUI.withdrawGUI == null || atmUI.historyGUI == null) {
            System.err.println("Panels not initialized. ");
        }
        switch (name) {
            case "balance":
                atmUI.balanceGUI.refreshBalance(accountNumber);
                System.out.println("refresh balance");
                break;
            case "withdraw":
                atmUI.withdrawGUI.refreshBalancePanel(accountNumber);
                System.out.println("refresh withdraw");
                break;
            case "transfer":

                break;
            case "history":
                atmUI.historyGUI.refreshHistory(accountNumber);
                System.out.println("refresh history");
                break;
            default:
                System.out.println("Error to refrshPanel: " + name);
                break;
        }
    }

    public JFrame getMainFrame() {
        return atmUI.getMainFrame();
    }

    public void setup() {
        listenerRegistrar.registerAllListeners();
    }

    public String getCurrentPanelName() {
        return atmUI.getCurrentPanelName();
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    // ------------- Handle logic parts for listeners -------------
    public void handleGreeting() {
        switchToPanel(ATMUI.LOGIN_PANEL);
    }

    public void exitSystem() {
        switchToPanel(ATMUI.GREETING_PANEL);
        atmUI.disposePanel();
    }

    public void showInfo() {
        System.out.println("Unsuccessful Swich ！！");
    }

    public void loginSuccess(int accountNunber) {
        this.accountNumber = accountNunber;
    }

    public void updateBalancePanel(int accountNumber) {
        JPanel balancePanel = atmUI.getPanel(ATMUI.BALANCE_PANEL);
        if (balancePanel instanceof BalanceInquiryUI) {
            ((BalanceInquiryUI) balancePanel).setAccountNumber(accountNumber);
            ((BalanceInquiryUI) balancePanel).refreshBalance(accountNumber);
        }
    }
}
