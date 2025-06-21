package lol;

import lol.DButil.DButil;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

public class DataDashboard extends JFrame {
    private String username;

    public DataDashboard(String username) {
        this.username = username;
        setTitle("玩家数据看板 - " + username);
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 初始化数据库连接
        try (Connection conn = DButil.getConnection()) {
            initComponents(conn);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "数据库连接失败: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DataDashboard dashboard = new DataDashboard("testUser");
            dashboard.setVisible(true);
        });
    }

    private void initComponents(Connection conn) {
        // 主面板使用分层布局
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP); // 合并初始化

        JLayeredPane mainPane = new JLayeredPane();
        mainPane.setPreferredSize(new Dimension(1000, 600));

        // 背景渐变层
        JPanel bgPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color startColor = new Color(30, 25, 20);
                Color endColor = new Color(60, 50, 40);
                GradientPaint gp = new GradientPaint(0, 0, startColor, getWidth(), getHeight(), endColor);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        bgPanel.setBounds(0, 0, 1000, 600);

        // 数据卡片容器
        JPanel cardContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        cardContainer.setOpaque(false);
        cardContainer.setBounds(50, 30, 900, 200);

        // 添加数据卡片
        cardContainer.add(createSummaryCard("总对局", "36 场", new Color(52, 152, 219)));
        cardContainer.add(createSummaryCard("常用英雄", "亚索", new Color(46, 204, 113)));
        cardContainer.add(createSummaryCard("当前段位", "钻石Ⅰ", new Color(241, 196, 15)));

        tabbedPane.addTab("对局记录", createTablePanel(conn, "matchrecord"));
        tabbedPane.addTab("英雄数据", createTablePanel(conn, "champion"));
        tabbedPane.addTab("玩家档案", createTablePanel(conn, "player"));

        // 主内容面板
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.setBounds(50, 250, 900, 300);

        // 选项卡面板
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);

        contentPanel.add(tabbedPane, BorderLayout.CENTER);

        // 操作工具栏
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        // 纯文本按钮
        JButton refreshBtn = new JButton("刷新数据");
        toolBar.add(refreshBtn);

        JButton analysisBtn = new JButton("数据分析");
        toolBar.add(analysisBtn);

        toolBar.add(Box.createHorizontalGlue());

        // 简化用户信息显示
        JLabel userLabel = new JLabel("玩家: " + username);
        userLabel.setFont(new Font("微软雅黑", Font.BOLD, 14));
        userLabel.setForeground(new Color(200, 200, 200));
        userLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));

        toolBar.add(userLabel);


        // 组装组件
        mainPane.add(bgPanel, JLayeredPane.DEFAULT_LAYER);
        mainPane.add(cardContainer, JLayeredPane.PALETTE_LAYER);
        mainPane.add(contentPanel, JLayeredPane.PALETTE_LAYER);
        mainPane.add(toolBar, JLayeredPane.PALETTE_LAYER);

        add(mainPane);
    }

// 创建数据卡片的方法
        private JPanel createSummaryCard(String title, String value, Color color) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(new Color(40, 35, 30));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(color.darker(), 2),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));
            titleLabel.setForeground(color.brighter());

            JLabel valueLabel = new JLabel(value);
            valueLabel.setFont(new Font("微软雅黑", Font.PLAIN, 24));
            valueLabel.setForeground(Color.WHITE);

            card.add(titleLabel, BorderLayout.NORTH);
            card.add(valueLabel, BorderLayout.CENTER);

            return card;
        }

// 创建带样式的表格面板
        private JPanel createTablePanel(Connection conn, String dataType) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);

            // 表格样式
            JTable table = new JTable();
            table.setRowHeight(30);
            table.setSelectionBackground(new Color(52, 152, 219));
            table.setFont(new Font("微软雅黑", Font.PLAIN, 14));

            // 表格头样式
            JTableHeader header = table.getTableHeader();
            header.setBackground(new Color(60, 50, 40));
            header.setForeground(Color.WHITE);
            header.setFont(new Font("微软雅黑", Font.BOLD, 14));

            // 加载数据

            panel.add(new JScrollPane(table), BorderLayout.CENTER);
            return panel;

    }
}