import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/*
 * Done by AI, Will improve later, do not judge me :)
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 */
// 增强版ATM UI，继承自基础ATMUI
public class EnhancedATMUI extends ATMUI {

    // 重写主菜单面板，添加账户信息和退出按钮
    @Override
    protected JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5)); // 5行1列容纳所有按钮
        panel.setName(MAIN_MENU_PANEL); // 使用父类的面板名称常量

        // 原有按钮
        JButton balanceBtn = new JButton("View Balance");
        JButton withdrawBtn = new JButton("Withdraw Cash");
        JButton transferBtn = new JButton("Transfer Funds");

        // 新增按钮（与ATMListenerRegistrar中的配置文本一致）
        JButton infoBtn = new JButton("Account Info");
        JButton exitBtn = new JButton("Exit");

        // 添加所有按钮到面板（顺序与布局对应）
        panel.add(balanceBtn);
        panel.add(withdrawBtn);
        panel.add(transferBtn);
        panel.add(infoBtn);
        panel.add(exitBtn);

        return panel;
    }

    /*
     * Done by AI, Will improve later, do not judge me :)
     * using AI because I don't want to spend too much time on designing listeners
     * it may cause we can't get the work done on time
     */

    // 重写登录面板，优化登录界面
    @Override
    protected JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setName(LOGIN_PANEL);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // 账号输入
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Account Number:"), gbc);

        gbc.gridx = 1;
        JTextField accField = new JTextField(15);
        panel.add(accField, gbc);

        // PIN输入
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("PIN:"), gbc);

        gbc.gridx = 1;
        JPasswordField pinField = new JPasswordField(15);
        panel.add(pinField, gbc);

        // 登录按钮（文本与监听器配置一致）
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Login");
        panel.add(loginBtn, gbc);

        // 账号输入框焦点效果
        accField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                accField.setBackground(Color.LIGHT_GRAY);
            }

            @Override
            public void focusLost(FocusEvent e) {
                accField.setBackground(Color.WHITE);
            }
        });

        return panel;
    }
}
/*
 * Done by AI, Will improve later, do not judge me :)
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 */