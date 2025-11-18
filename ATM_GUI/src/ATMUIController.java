import javax.swing.*;

/**
 * controller class for ATMUI
 */
public class ATMUIController {
    private ATMUI atmUI;
    private ATM atm;
    private ATMListenerRegistrar listenerRegistrar;

    public ATMUIController(ATMUI atmUI, ATM atm) {
        this.atmUI = atmUI;
        this.atm = atm;
        this.listenerRegistrar = new ATMListenerRegistrar(atmUI, this);
    }

    public ATMUIController(ATM atm) {
        this.atm = atm;
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
        atmUI.switchPanel(panelName);
    }

    // ------------- Convenience methods for switching panels -------------
    public void switchToLoginPanel() {
        atmUI.switchPanel("login");
    }

    public void switchToMainMenuPanel() {
        atmUI.switchPanel("mainMenu");
    }

    public void switchToBalancePanel() {
        atmUI.switchPanel("balance");
    }

    public void switchToWithdrawPanel() {
        atmUI.switchPanel("withdraw");
    }

    public void switchToTransferPanel() {
        atmUI.switchPanel("transfer");
    }

    public void showAccountInfo() {
        atmUI.switchPanel("accountInfo");
    }

    public void goToPanel(String panelName) {
        atmUI.switchPanel(panelName);
    }

    public JFrame getMainFrame() {
        return atmUI.getMainFrame();
    }

    public void setup() {
        System.out.println("Registering listeners...");
        listenerRegistrar.registerAllListeners();
    }

    // ------------- Handle logic parts for listeners -------------

    public void handleLogin() {
        switchToMainMenuPanel();
    }

    public void exitSystem() {
        atmUI.getMainFrame().dispose();
    }

    public void showInfo() {
        JOptionPane.showMessageDialog(atmUI.getMainFrame(), "聆听器示范");
    }
}
