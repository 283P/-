package lol;

import lol.register.UserAuthDao;
import lol.server.DBClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class USGameAuthSystem extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;

    public USGameAuthSystem() {
        setTitle("USGame - 豪华宽屏认证系统");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // 修改为统一的深灰配色
                Color color1 = new Color(45, 45, 45);  // 原30,25,20
                Color color2 = new Color(60, 60, 60);  // 原60,50,40
                Color color3 = new Color(75, 75, 75);  // 原90,75,60

                // 顶部渐变层
                GradientPaint topGradient = new GradientPaint(
                        0, 0, color3,
                        getWidth(), 0, color3.darker()
                );
                g2d.setPaint(topGradient);
                g2d.fillRect(0, 0, getWidth(), getHeight()/3);

                // 中部渐变层
                GradientPaint midGradient = new GradientPaint(
                        0, getHeight()/3, color2,
                        getWidth(), getHeight()/3, color2.darker()
                );
                g2d.setPaint(midGradient);
                g2d.fillRect(0, getHeight()/3, getWidth(), getHeight()/3);

                // 底部渐变层
                GradientPaint bottomGradient = new GradientPaint(
                        0, 2*getHeight()/3, color1,
                        getWidth(), getHeight(), color1.darker()
                );
                g2d.setPaint(bottomGradient);
                g2d.fillRect(0, 2*getHeight()/3, getWidth(), getHeight()/3);

                // 保留原有的光晕效果
                Point2D center = new Point2D.Float(getWidth()/2, getHeight()/2);
                float radius = getWidth()/2;
                float[] dist = {0.0f, 0.8f, 1.0f};
                Color[] colors = {new Color(180, 150, 100, 30), new Color(180, 150, 100, 0), new Color(180, 150, 100, 0)};
                RadialGradientPaint rgp = new RadialGradientPaint(center, radius, dist, colors);
                g2d.setPaint(rgp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // 修改边框颜色
                g2d.setColor(new Color(100, 100, 100));  // 原180,150,100
                g2d.setStroke(new BasicStroke(5));
                g2d.drawRoundRect(10, 10, getWidth()-20, getHeight()-20, 20, 20);
            }
        };

        // 创建面板实例
        LoginPanel loginPanel = new LoginPanel(this);
        RegisterPanel registerPanel = new RegisterPanel(this);
        SetPlayerIDPanel setPlayerIDPanel = new SetPlayerIDPanel(this);

        cardPanel.add(loginPanel, "login");
        cardPanel.add(registerPanel, "register");
        cardPanel.add(setPlayerIDPanel, "setPlayerID");

        add(cardPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            USGameAuthSystem system = new USGameAuthSystem();
            system.setVisible(true);
        });
    }

    public void showPanel(String panelName) {
        cardLayout.show(cardPanel, panelName);
    }

    public void showSetPlayerIDPanel(String username) {
        SetPlayerIDPanel panel = (SetPlayerIDPanel) cardPanel.getComponent(2);
        panel.setUsername(username);
        cardLayout.show(cardPanel, "setPlayerID");
    }
}

class WideScreenLogo extends JPanel {
    private BufferedImage logoCache;

    public WideScreenLogo() {
        setPreferredSize(new Dimension(800, 180));
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        if (logoCache == null || logoCache.getWidth() != getWidth() || logoCache.getHeight() != getHeight()) {
            createLogoCache();
        }

        int x = (getWidth() - logoCache.getWidth()) / 2;
        int y = (getHeight() - logoCache.getHeight()) / 2;
        g2d.drawImage(logoCache, x, y, null);
    }

    private void createLogoCache() {
        logoCache = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = logoCache.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Paint basePaint = new GradientPaint(0, 0, new Color(80, 65, 45),
                getWidth(), 0, new Color(120, 100, 70));
        g2d.setPaint(basePaint);
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2d.setStroke(new BasicStroke(4));
        g2d.setColor(new Color(180, 150, 100));
        g2d.drawRoundRect(2, 2, getWidth()-4, getHeight()-4, 25, 25);

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(new Color(220, 190, 150));
        g2d.drawRoundRect(6, 6, getWidth()-12, getHeight()-12, 20, 20);

        Font font = new Font("Palatino Linotype", Font.BOLD | Font.ITALIC, 72);
        AffineTransform at = new AffineTransform();
        Font tiltedFont = font.deriveFont(at);
        g2d.setFont(tiltedFont);

        g2d.setColor(new Color(40, 30, 20));
        g2d.drawString("USGame", 52, 142);

        g2d.setColor(new Color(80, 60, 40));
        g2d.drawString("USGame", 50, 140);

        GradientPaint textGradient = new GradientPaint(
                0, 100, new Color(250, 240, 210),
                getWidth(), 100, new Color(220, 190, 150));
        g2d.setPaint(textGradient);
        g2d.drawString("USGame", 45, 135);

        g2d.setColor(new Color(255, 245, 225, 150));
        g2d.drawString("USGame", 47, 132);

        Stroke oldStroke = g2d.getStroke();
        g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(new Color(180, 150, 100));

        int[] xPointsLeft = {50, 70, 60, 80, 60, 80, 50};
        int[] yPointsLeft = {80, 85, 95, 100, 105, 115, 120};
        g2d.drawPolyline(xPointsLeft, yPointsLeft, xPointsLeft.length);

        int[] xPointsRight = {getWidth()-50, getWidth()-70, getWidth()-60, getWidth()-80,
                getWidth()-60, getWidth()-80, getWidth()-50};
        int[] yPointsRight = {80, 85, 95, 100, 105, 115, 120};
        g2d.drawPolyline(xPointsRight, yPointsRight, xPointsRight.length);

        g2d.setStroke(oldStroke);

        g2d.setPaint(new GradientPaint(0, 0, new Color(255, 240, 200, 80),
                0, 50, new Color(255, 240, 200, 0)));
        g2d.fillRoundRect(10, 10, getWidth()-20, 40, 20, 20);

        g2d.dispose();
    }
}

class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private USGameAuthSystem parent;

    public LoginPanel(USGameAuthSystem parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        add(new WideScreenLogo(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.weightx = 1.0;
        gbcCenter.weighty = 0.1;
        gbcCenter.anchor = GridBagConstraints.NORTH;
        gbcCenter.fill = GridBagConstraints.HORIZONTAL;

        JLabel subtitle = new JLabel("开启您的游戏之旅!", SwingConstants.CENTER);
        subtitle.setFont(new Font("Microsoft YaHei", Font.ITALIC, 20));
        subtitle.setForeground(new Color(200, 180, 150));
        centerPanel.add(subtitle, gbcCenter);

        gbcCenter.gridy = 1;
        gbcCenter.weighty = 1.0;
        gbcCenter.fill = GridBagConstraints.BOTH;
        gbcCenter.insets = new Insets(20, 0, 0, 0);

        JPanel formPanel = createFormPanel();
        centerPanel.add(formPanel, gbcCenter);

        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 现代阴影效果
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 25, 25);

                // 主面板背景
                g2d.setColor(new Color(45, 45, 45));
                g2d.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);

                // 边框效果
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(new Color(80, 80, 80));
                g2d.drawRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);
            }
        };

        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createStyledLabel("用户名:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        usernameField = new JTextField(30);
        styleTextField(usernameField);
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(createStyledLabel("密码:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        passwordField = new JPasswordField(30);
        styleTextField(passwordField);
        formPanel.add(passwordField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton loginButton = new JButton("登 录");
        styleButton(loginButton, new Color(180, 150, 50));
        loginButton.addActionListener(this::loginAction);

        JButton registerButton = new JButton("前往注册");
        styleButton(registerButton, new Color(120, 100, 60));
        registerButton.addActionListener(e -> parent.showPanel("register"));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void loginAction(ActionEvent e) {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        try {
            DBClient client = new DBClient();
            String authResult = client.authenticate(username, password);

            if ("AUTH_SUCCESS".equals(authResult)) {
                // 登录成功逻辑
                JOptionPane.showMessageDialog(this, "登录成功");

                // 关闭当前窗口
                Window window = SwingUtilities.getWindowAncestor(this);
                window.dispose();

                // 打开新的数据看板页面
                SwingUtilities.invokeLater(() -> {
                    DataDashboard dashboard = new DataDashboard(username);
                    dashboard.setVisible(true);
                });
            } else {
                JOptionPane.showMessageDialog(this, "用户名或密码错误");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "连接服务器失败: " + ex.getMessage());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 220, 220));
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(400, 45));
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(200, 200, 200));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        // 添加现代悬停效果
        button.addMouseListener(new MouseAdapter() {
            private final Color originalBg = button.getBackground();
            private final Color hoverBg = new Color(100, 100, 100);

            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(140, 140, 140), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }
        });
    }

    private Color brighter(Color color, float factor) {
        int r = Math.min((int)(color.getRed() * factor), 255);
        int g = Math.min((int)(color.getGreen() * factor), 255);
        int b = Math.min((int)(color.getBlue() * factor), 255);
        return new Color(r, g, b);
    }
}

class RegisterPanel extends JPanel {
    private final USGameAuthSystem parent;
    // 字段重命名（原usernameField改为userIdField）
    private JTextField userIdField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField nicknameField;

    RegisterPanel(USGameAuthSystem parent) {
        this.parent = parent;
        setLayout(new BorderLayout());  // 添加布局管理器
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        add(new WideScreenLogo(), BorderLayout.NORTH);  // 添加顶部LOGO

        // 添加中心表单面板
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(createFormPanel());
        add(centerPanel, BorderLayout.CENTER);
    }

    private void registerAction(ActionEvent e) {
        // 获取用户ID（原用户名字段）
        String userId = userIdField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());
        String nickname = nicknameField.getText().trim();

        // 修改校验逻辑
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "两次输入的密码不一致");
            return;
        }
        if (userId.isEmpty() || nickname.isEmpty()) {
            JOptionPane.showMessageDialog(this, "用户ID和昵称不能为空");
            return;
        }

        try {
            DBClient client = new DBClient();
            String hashedPassword = UserAuthDao.hashPassword(password);

            // 修正命令格式：REGISTER [用户ID] [昵称] [哈希密码]
            String command = String.format("REGISTER %s %s %s",
                userId,
                nickname,
                hashedPassword);

            String registerResult = client.sendCommand(command);

            // 添加结果处理
            if ("REGISTER_SUCCESS".equals(registerResult)) {
                JOptionPane.showMessageDialog(this, "注册成功");
                parent.showPanel("login");
            } else {
                JOptionPane.showMessageDialog(this, "注册失败: " + registerResult);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "连接服务器失败: " + ex.getMessage());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 现代阴影效果
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 25, 25);

                // 主面板背景
                g2d.setColor(new Color(45, 45, 45));
                g2d.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);

                // 边框效果
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(new Color(80, 80, 80));
                g2d.drawRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);
            }
        };

        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createStyledLabel("用户ID:"), gbc);  // 原"用户名:"

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        userIdField = new JTextField(30);  // 原usernameField
        styleTextField(userIdField);
        formPanel.add(userIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        formPanel.add(createStyledLabel("密码:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        passwordField = new JPasswordField(30);
        styleTextField(passwordField);
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        formPanel.add(createStyledLabel("确认密码:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        confirmPasswordField = new JPasswordField(30);
        styleTextField(confirmPasswordField);
        formPanel.add(confirmPasswordField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton registerButton = new JButton("注 册");
        styleButton(registerButton, new Color(180, 150, 50));
        registerButton.addActionListener(this::registerAction);

        JButton loginButton = new JButton("前往登录");
        styleButton(loginButton, new Color(120, 100, 60));
        loginButton.addActionListener(e -> parent.showPanel("login"));

        buttonPanel.add(registerButton);
        buttonPanel.add(loginButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        // 在原有表单字段后添加
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        formPanel.add(createStyledLabel("用户昵称:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        nicknameField = new JTextField(30);
        styleTextField(nicknameField);
        formPanel.add(nicknameField, gbc);

        // 调整按钮面板位置
        gbc.gridx = 0;
        gbc.gridy = 4;  // 原gridy=3改为4
        gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 210, 190));
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(400, 45));
        field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        field.setBackground(new Color(60, 60, 60));
        field.setForeground(Color.WHITE);
        field.setCaretColor(new Color(200, 200, 200));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        // 添加现代悬停效果
        button.addMouseListener(new MouseAdapter() {
            private final Color originalBg = button.getBackground();
            private final Color hoverBg = new Color(100, 100, 100);

            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(140, 140, 140), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }
        });
    }

    private Color brighter(Color color, float factor) {
        int r = Math.min((int)(color.getRed() * factor), 255);
        int g = Math.min((int)(color.getGreen() * factor), 255);
        int b = Math.min((int)(color.getBlue() * factor), 255);
        return new Color(r, g, b);
    }
}

class SetPlayerIDPanel extends JPanel {
    // 添加final修饰符
    private final USGameAuthSystem parent;
    private JTextField playerIDField;
    private String username;

    public SetPlayerIDPanel(USGameAuthSystem parent) {
        this.parent = parent; // 确保正确初始化
        setLayout(new BorderLayout());
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

        add(new WideScreenLogo(), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.gridx = 0;
        gbcCenter.gridy = 0;
        gbcCenter.weightx = 1.0;
        gbcCenter.weighty = 0.1;
        gbcCenter.anchor = GridBagConstraints.NORTH;
        gbcCenter.fill = GridBagConstraints.HORIZONTAL;

        JLabel subtitle = new JLabel("设置您的玩家ID", SwingConstants.CENTER);
        subtitle.setFont(new Font("Microsoft YaHei", Font.ITALIC, 20));
        subtitle.setForeground(new Color(200, 180, 150));
        centerPanel.add(subtitle, gbcCenter);

        gbcCenter.gridy = 1;
        gbcCenter.weighty = 1.0;
        gbcCenter.fill = GridBagConstraints.BOTH;
        gbcCenter.insets = new Insets(20, 0, 0, 0);

        JPanel formPanel = createFormPanel();
        centerPanel.add(formPanel, gbcCenter);

        add(centerPanel, BorderLayout.CENTER);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                // 现代阴影效果
                g2d.setColor(new Color(0, 0, 0, 50));
                g2d.fillRoundRect(3, 3, getWidth()-6, getHeight()-6, 25, 25);

                // 主面板背景
                g2d.setColor(new Color(45, 45, 45));
                g2d.fillRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);

                // 边框效果
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(new Color(80, 80, 80));
                g2d.drawRoundRect(0, 0, getWidth()-6, getHeight()-6, 25, 25);
            }
        };

        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(createStyledLabel("玩家ID:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        playerIDField = new JTextField(30);
        styleTextField(playerIDField);
        formPanel.add(playerIDField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        JLabel hintLabel = new JLabel("<html><div style='text-align:center;'>玩家ID将作为您在游戏中的昵称显示<br>可以使用中文、字母和数字组合</div></html>", SwingConstants.CENTER);
        hintLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        hintLabel.setForeground(new Color(180, 160, 140));
        formPanel.add(hintLabel, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 40, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        JButton confirmButton = new JButton("确 认");
        styleButton(confirmButton, new Color(180, 150, 50));
        confirmButton.addActionListener(this::confirmAction);

        JButton backButton = new JButton("返回登录");
        styleButton(backButton, new Color(120, 100, 60));
        backButton.addActionListener(e -> parent.showPanel("login"));

        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private void confirmAction(ActionEvent e) {
        String playerID = playerIDField.getText().trim();

        if (playerID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "玩家ID不能为空", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (playerID.length() < 3 || playerID.length() > 12) {
            JOptionPane.showMessageDialog(this, "玩家ID长度需在3-12个字符之间", "错误", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this,
                "<html><div style='text-align:center;'>玩家ID设置成功！<br>账户: " + username +
                        "<br>昵称: " + playerID + "</div></html>",
                "注册完成",
                JOptionPane.INFORMATION_MESSAGE);

        parent.showPanel("login");
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(new Color(220, 210, 190));
        label.setFont(new Font("Microsoft YaHei", Font.BOLD, 18));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        return label;
    }

    private void styleTextField(JTextField field) {        field.setPreferredSize(new Dimension(400, 45));
    field.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
    field.setBackground(new Color(60, 60, 60));
    field.setForeground(Color.WHITE);
    field.setCaretColor(new Color(200, 200, 200));
    field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(100, 100, 100), 1),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
    ));
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(80, 80, 80));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));

        // 添加现代悬停效果
        button.addMouseListener(new MouseAdapter() {
            private final Color originalBg = button.getBackground();
            private final Color hoverBg = new Color(100, 100, 100);

            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(140, 140, 140), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalBg);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(120, 120, 120), 1),
                        BorderFactory.createEmptyBorder(12, 30, 12, 30)
                ));
            }
        });
    }

    private Color brighter(Color color, float factor) {
        int r = Math.min((int)(color.getRed() * factor), 255);
        int g = Math.min((int)(color.getGreen() * factor), 255);
        int b = Math.min((int)(color.getBlue() * factor), 255);
        return new Color(r, g, b);
    }
}