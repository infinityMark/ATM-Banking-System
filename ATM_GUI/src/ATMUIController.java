
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
        atmUI.switchPanel(panelName);
    }

    // ------------- Convenience methods for switching panels -------------
    public void switchToGreetingPanel() {
        atmUI.switchPanel("greeting");
    }

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

    public void showTransactionHistory() {
        atmUI.switchPanel("transactionHistory");
        TransactionHistoryUI historyUI = atmUI.historyGUI;
        if (historyUI == null) {
            historyUI.refreshHistory(accountNumber);
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
        switchToLoginPanel();
    }

    public void exitSystem() {
        switchToGreetingPanel();
        atmUI.disposePanel();
    }

    public void showInfo() {
        // JOptionPane.showMessageDialog(atmUI.getMainFrame(), "啊哦，Listener出问题咯！");
        System.out.println("跳转失败！！");
    }

    public void loginSuccess(int accountNunber) {
        this.accountNumber = accountNunber;
    }

    // update balance inquiry
    public void updateBalancePanel(int accountNumber) {
        JPanel balancePanel = atmUI.getPanel(ATMUI.BALANCE_PANEL);
        if (balancePanel instanceof BalanceInquiryUI) {
            ((BalanceInquiryUI) balancePanel).setAccountNumber(accountNumber);
            ((BalanceInquiryUI) balancePanel).refreshBalance();
        }
    }
}
