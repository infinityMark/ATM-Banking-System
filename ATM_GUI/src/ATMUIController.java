import javax.swing.*;

/**
 * controller class for ATMUI
 */
public class ATMUIController {
    private ATMUI atmUI;

    public ATMUIController(ATMUI atmUI) {
        this.atmUI = atmUI;
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

    public JFrame getMainFrame() {
        return atmUI.getMainFrame();
    }

    // For testing only
    public static void main(String[] args) {
        ATMUI atmUI = new ATMUI();
        ATMUIController atm = new ATMUIController(atmUI);
        atm.switchToLoginPanel();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atm.switchToMainMenuPanel();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atm.switchToBalancePanel();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        atm.switchToTransferPanel();
    }
}
