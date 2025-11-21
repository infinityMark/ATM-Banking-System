import javax.swing.*;

/**
 * controller class for ATMUI
 */
public class ATMUIController {
    private ATMUI atmUI;
    private ATMListenerRegistrar listenerRegistrar;
    private int accountNumber;

    public ATMUIController() {
    }

    public ATMUIController(ATMUI atmUI) {
        this.atmUI = atmUI;
        this.listenerRegistrar = new ATMListenerRegistrar(atmUI, this);
    }

    public void run() {
        atmUI.initializeUI();
        setup(); // register all listeners
        atmUI.getMainFrame().setVisible(true); // show UI
    }

    // ------------- General method for switching panels -------------
    public void switchToPanel(String panelName) {
        refreshPanel(panelName);
        atmUI.switchPanel(panelName);
    }

    public void refreshPanel(String name) {
        if (atmUI.balanceGUI == null || atmUI.withdrawGUI == null || atmUI.transferGUI == null|| atmUI.historyGUI == null) {
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
                atmUI.transferGUI.updateBalanceInquiry(accountNumber);
                System.out.println("refresh withdraw");
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

    public void setup() {
        listenerRegistrar.registerAllListeners();
    }

    public String getCurrentPanelName() {
        return atmUI.getCurrentPanelName();
    }

    public void exitSystem() {
        switchToPanel(ATMUI.GREETING_PANEL);
        atmUI.disposePanel();
    }

    public void showInfo() {
        System.out.println("Unsuccessful Switch !!");
    }

    public void loginSuccess(int accountNunber) {
        this.accountNumber = accountNunber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }
}
