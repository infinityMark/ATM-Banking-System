import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/*
 * Done by AI, Will improve later, do not judge me :)
 * change if needed
 * using AI because I don't want to spend too much time on designing listeners
 * it may cause we can't get the work done on time
 */
// 增强版ATM UI，继承自基础ATMUI
public class EnhancedATMUI extends ATMUI {

    public EnhancedATMUI() {
        super();
    }

    @Override
    protected JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 5, 5));
        panel.setName(MAIN_MENU_PANEL);

        // 余额查询按钮
        JButton balanceBtn = new JButton("View Balance");
        balanceBtn.setName("View Balance"); // 设置名称匹配监听器

        // 取款按钮
        JButton withdrawBtn = new JButton("Withdraw Cash");
        withdrawBtn.setName("Withdraw Cash"); // 设置名称匹配监听器

        // 转账按钮
        JButton transferBtn = new JButton("Transfer Funds");
        transferBtn.setName("Transfer Funds"); // 设置名称匹配监听器

        // 账户信息按钮
        //JButton infoBtn = new JButton("Account Info");
        //infoBtn.setName("Account Info"); // 设置名称匹配监听器

        JButton historyBtn = new JButton("Transaction History");
        historyBtn.setName("Transaction History"); // 设置名称匹配监听器

        // 退出按钮
        JButton exitBtn = new JButton("Exit");
        exitBtn.setName("Exit"); // 设置名称匹配监听器

        // 添加所有按钮到面板
        panel.add(balanceBtn);
        panel.add(withdrawBtn);
        panel.add(transferBtn);
        panel.add(historyBtn);
        panel.add(exitBtn);

        return panel;
    }

    /*
     * Done by AI, Will improve later, do not judge me :)
     * using AI because I don't want to spend too much time on designing listeners
     * it may cause we can't get the work done on time
     */

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

        // 登录按钮（设置名称与监听器匹配）
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginBtn = new JButton("Login");
        loginBtn.setName("Login");
        panel.add(loginBtn, gbc);

        // 账号输入框焦点效果（保持不变）
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