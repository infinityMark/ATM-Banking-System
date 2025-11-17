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

    public JFrame getMainFrame() {
        return atmUI.getMainFrame();
    }

    public void setup() {
        listenerRegistrar.registerAllListeners();
    }

    // For testing only
    public static void main(String[] args) {
        EnhancedATMUI atmUI = new EnhancedATMUI();
        atmUI.initializeUI();
        ATM atm = new ATM();
        ATMUIController controller = new ATMUIController(atmUI, atm);

        controller.setup(); // register all listeners
        controller.getMainFrame().setVisible(true); // show UI
    }
}
