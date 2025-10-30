import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;
import java.awt.*;

public class interfaces {
    public static void main(String[] args) {
        JFrame frame = new JFrame("interface demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //setting icon for window
        ImageIcon icon = new ImageIcon(interfaces.class.getResource("/coaching.png"));
        frame.setIconImage(icon.getImage());

        //get screen size,after then set window size to 80% of screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();

        // obtain screen size and setting
        final double rate = 0.6;
        int screenWidth = (int) Math.round(dm.getWidth()*rate);
        int screenHeight = (int) Math.round(dm.getHeight()*rate);
        frame.setSize(screenWidth, screenHeight);

        frame.setLocationRelativeTo(null);//make it be center
//        frame.setAlwaysOnTop(true);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setPreferredSize(new Dimension(0, 40));
        menuBar.setBackground(StandardColor.Blue.getColor(1));
        ImageIcon lightMode = new ImageIcon(interfaces.class.getResource("/light_37dp_1F1F1F_FILL0_wght400_GRAD0_opsz40.png"));
        ImageIcon darkMode = new ImageIcon(interfaces.class.getResource("/light_off_100dp_FFFFFF_FILL0_wght400_GRAD0_opsz48.svg"));
        JLabel modeSwitchIcon = new JLabel(lightMode);
        modeSwitchIcon.setBorder(new EmptyBorder(0,10,0,10));

        // 创建"文件"菜单
        JMenu fileMenu = new JMenu("File(F)");

//        JTextField t;

        // 创建文件菜单的子项
        JMenuItem newItem = new JMenuItem("File(N)");
        JMenuItem openItem = new JMenuItem("Open(O)");
        JMenuItem saveItem = new JMenuItem("Save(S)");
        JMenuItem exitItem = new JMenuItem("Exit(X)");

        // 为退出项添加事件监听器
        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // 将子项添加到文件菜单
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator(); // 分隔线
        fileMenu.add(exitItem);

        Passwords abcd = new Passwords(500,40, StandardColor.Green.getColorMode(), StandardColor.Yellow.getColorMode(), StandardColor.GreyMiddle.getColor(1));


        // 创建"编辑"菜单
        JMenu editMenu = new JMenu("编辑(E)");
        editMenu.add(new JMenuItem("撤销(U)"));
        editMenu.add(new JMenuItem("重做(R)"));
        editMenu.addSeparator();
        editMenu.add(new JMenuItem("剪切(T)"));
        editMenu.add(new JMenuItem("复制(C)"));
        editMenu.add(new JMenuItem("粘贴(P)"));
        if(editMenu.isSelected())
            editMenu.setBackground(StandardColor.Indigo.getColorMode());

        // 将菜单添加到菜单栏
        menuBar.add(fileMenu);
        menuBar.add(Box.createHorizontalGlue());
//        menuBar.add(editMenu);
        menuBar.add(modeSwitchIcon);

        // 设置菜单栏到窗口
        frame.setJMenuBar(menuBar);

        // 显示窗口
        frame.setVisible(true);

//        RoundedButton button = new RoundedButton("Test button",ColorStandard.GreyHighest.getOppositeColorMode(),"#000000","#000000","#ffffff",120,40);
//        RoundedButton button1 = new RoundedButton("button","#FFFFFF","#000000","#000000","#ffffff",120,40);
//        RoundedButton button3 = new RoundedButton("To Dark Mode","to Light Mode",ColorStandard.GreyHighest.getColorMode(),ColorStandard.GreyHighest.getOppositeColorMode(),16,ColorStandard.GreyHighest.getOppositeColorMode(), ColorStandard.GreyHighest.getColorMode(),16,true,150,40);

        editMenu.setForeground(StandardColor.GreyHighest.getColor(0));
        fileMenu.setForeground(StandardColor.GreyHighest.getColor(0));

        //JLabel modeSwitchIcon = new JLabel(icon);
        modeSwitchIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                StandardColor.setIsLightMode( !(StandardColor.getIsLightMode()) );//it will automatically change isLightMode's value to opposite
                frame.getContentPane().setBackground(StandardColor.GreyMiddle.getColorMode());
//                frame.setVisible(true);
                menuBar.setBackground(StandardColor.Blue.getColorMode());
//                menuBar.setForeground(ColorStandard.GreyHighest.getOppositeColorMode());
                editMenu.setForeground(StandardColor.GreyHighest.getColor(0));
                fileMenu.setForeground(StandardColor.GreyHighest.getColor(0));
                fileMenu.setBackground(StandardColor.Indigo.getColorMode());
//                button3.setBackground(ColorStandard.GreyLower.getColorMode());

                if(StandardColor.getIsLightMode())
                    modeSwitchIcon.setIcon(darkMode);
                else
                    modeSwitchIcon.setIcon(lightMode);
            }
        });


//        button3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                ColorStandard.setIsLightMode( !(ColorStandard.getIsLightMode()) );//it will automatically change isLightMode's value to opposite
//                frame.getContentPane().setBackground(ColorStandard.GreyMiddle.getColorMode());
////                frame.setVisible(true);
//                menuBar.setBackground(ColorStandard.Blue.getColorMode());
////                menuBar.setForeground(ColorStandard.GreyHighest.getOppositeColorMode());
//                editMenu.setForeground(ColorStandard.GreyHighest.getColor(0));
//                fileMenu.setForeground(ColorStandard.GreyHighest.getColor(0));
//                fileMenu.setBackground(ColorStandard.Indigo.getColorMode());
//                button3.setBackground(ColorStandard.GreyLower.getOppositeColorMode());
//            }
//        });

        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 10, 10, 10);

        TextFields a = new TextFields(500,40, StandardColor.Blue.getColorMode(), StandardColor.Green.getColorMode(), StandardColor.Indigo.getColorMode());

        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        frame.add(a,gbc);
        RoundedButton ted = new RoundedButton("Show","Hidden", StandardColor.GreyHighest.getColor(0), StandardColor.Green.getColorMode(), StandardColor.GreyHighest.getColor(1), StandardColor.GreyHighest.getColorMode(), new Font("",Font.PLAIN,16),new Font("",Font.PLAIN,16),true,40,40);
//        frame.add(abcd);
        IndicateButton showpassword = new IndicateButton(abcd,ted,500,50);
        frame.add(showpassword);

        ted.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(abcd.getText());
            }
        });


        gbc.fill = GridBagConstraints.NONE;
        gbc.gridx=0;
        gbc.gridy=1;
//        frame.add(button,gbc);
//
//
//        gbc.gridy=2;
//        frame.add(button1,gbc);
        gbc.gridy=4;
//        frame.add(button3,gbc);

        frame.setVisible(true);
        if(editMenu.isSelected()){
            editMenu.setBackground(StandardColor.Indigo.getColorMode());
            frame.setVisible(true);
        }
    }

}