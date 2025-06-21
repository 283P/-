package lol;

import lol.champion.service.ChampionService;
import lol.champion.service.ChampionServiceImpl;
import lol.matchrecord.service.matchrecordServerImpl;
import lol.player.service.playerServerImpl;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;

public class DataDashboard extends JFrame {
    private final String username;

    // 服务类示例（可按需初始化）
    private final playerServerImpl playerService = new playerServerImpl();
    private final matchrecordServerImpl matchService = new matchrecordServerImpl();
    private final ChampionService championService = new ChampionServiceImpl();

    public DataDashboard(String username) {
        this.username = username;
        setTitle("玩家数据看板 - " + username);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            initComponents();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "初始化失败: " + e.getMessage());
        }
    }

    private void initComponents() throws SQLException {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        JPanel cardContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        cardContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        cardContainer.add(createSummaryCard("总对局", getMatchCount(), new Color(52, 152, 219)));
        cardContainer.add(createSummaryCard("常用英雄", getTopChampion(), new Color(46, 204, 113)));
        cardContainer.add(createSummaryCard("当前段位", getRank(), new Color(241, 196, 15)));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("对局记录", createMatchRecordPanel());
        tabbedPane.addTab("英雄数据", createChampionDataPanel());
        tabbedPane.addTab("玩家档案", createPlayerProfilePanel());

        mainPanel.add(cardContainer, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createSummaryCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setPreferredSize(new Dimension(0, 80));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 16));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(Color.WHITE);
        valueLabel.setFont(new Font("微软雅黑", Font.PLAIN, 20));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createMatchRecordPanel() {
        JTable table = new JTable(new Object[][]{}, new String[]{"对局ID", "时间", "结果"});
        // TODO: 调用 matchService 获取对局数据并填充表格
        return wrapTablePanel(table);
    }

    private JPanel createChampionDataPanel() {
        JTable table = new JTable(new Object[][]{}, new String[]{"英雄名称", "使用次数", "胜率"});
        // TODO: 调用 championService 获取英雄数据并填充表格
        return wrapTablePanel(table);
    }

    private JPanel createPlayerProfilePanel() {
        JTable table = new JTable(new Object[][]{}, new String[]{"玩家名称", "注册时间", "段位"});
        // TODO: 调用 playerService.getPlayerProfile(username) 获取数据并填充
        return wrapTablePanel(table);
    }

    private JPanel wrapTablePanel(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 50, 40));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("微软雅黑", Font.BOLD, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    // 数据占位方法，可自行实现实际逻辑
    private String getMatchCount() {
        // TODO: 获取对局总数
        return "加载中...";
    }

    private String getTopChampion() {
        // TODO: 获取常用英雄
        return "加载中...";
    }

    private String getRank() {
        // TODO: 获取当前段位
        return "加载中...";
    }
}
