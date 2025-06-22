package lol;

import lol.champion.service.ChampionService;
import lol.champion.service.ChampionServiceImpl;
import lol.matchrecord.service.matchrecordServerImpl;
import lol.player.service.playerServerImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public  class DataDashboard extends JFrame {
    // 服务类示例
    private final playerServerImpl playerService = new playerServerImpl();
    private String username; // 从final改为普通变量，以便更新
    private final matchrecordServerImpl matchService = new matchrecordServerImpl();
    private final ChampionService championService = new ChampionServiceImpl();
    private JPanel cardContainer;
    // 表格组件（用于动态加载数据）
    private JTable matchRecordTable;
    private JTable championDataTable;
    private JTable playerProfileTable;
    private JTabbedPane tabbedPane;

    // 摘要卡片标签（用于更新数据）
    private JLabel matchCountLabel;
    private JLabel topChampionLabel;
    private JLabel rankLabel;
    private JTextField searchField;
    private JButton searchButton;
    public DataDashboard(String username) {
        this.username = username;
        setTitle("玩家数据看板 - " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 改为全屏模式
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            initComponents();
            loadSummaryData(); // 初始化时加载摘要数据
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "初始化失败: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 使用SwingUtilities确保在事件调度线程(EDT)中创建和操作GUI
        SwingUtilities.invokeLater(() -> {
            try {
                // 设置系统默认字体，确保中文正常显示
                UIManager.put("Label.font", new Font("SimHei", Font.PLAIN, 12));
                UIManager.put("Table.font", new Font("SimHei", Font.PLAIN, 12));
                UIManager.put("TableHeader.font", new Font("SimHei", Font.BOLD, 12));
                UIManager.put("Button.font", new Font("SimHei", Font.PLAIN, 12));
                UIManager.put("TabbedPane.font", new Font("SimHei", Font.PLAIN, 12));

                // 创建并显示数据看板
                DataDashboard dashboard = new DataDashboard("TestPlayer");
                dashboard.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "启动失败: " + e.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // 修改卡片创建和引用方式
    // 修改卡片创建方式，直接保留标签引用
    private void initComponents() throws SQLException {
        // 修改主布局初始化顺序
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // 先初始化搜索面板
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(15);
        searchButton = new JButton("搜索");
        searchButton.addActionListener(e -> searchPlayer());
        searchPanel.add(new JLabel("玩家ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        // 初始化卡片容器
        cardContainer = new JPanel(new GridLayout(1, 3, 20, 0));
        // 创建摘要卡片
        JPanel matchCard = createSummaryCard("总对局", "加载中...", new Color(52, 152, 219));
        matchCountLabel = (JLabel) matchCard.getComponent(1);
        cardContainer.add(matchCard);

        JPanel championCard = createSummaryCard("常用英雄", "加载中...", new Color(46, 204, 113));
        topChampionLabel = (JLabel) championCard.getComponent(1);
        cardContainer.add(championCard);

        JPanel rankCard = createSummaryCard("当前段位", "加载中...", new Color(241, 196, 15));
        rankLabel = (JLabel) rankCard.getComponent(1);
        cardContainer.add(rankCard);

        // 初始化表格和选项卡
        matchRecordTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"对局ID", "结果", "经济"}));
        championDataTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"英雄名称", "使用次数", "胜率"}));
        playerProfileTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"玩家名称", "玩家id", "段位"}));

        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("对局记录", wrapTablePanel(matchRecordTable));
        tabbedPane.addTab("英雄数据", wrapTablePanel(championDataTable));
        tabbedPane.addTab("玩家档案", wrapTablePanel(playerProfileTable));

        // 使用合理的布局结构
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(cardContainer, BorderLayout.NORTH);
        contentPanel.add(new JScrollPane(tabbedPane), BorderLayout.CENTER);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    //=== 数据加载方法 ===//
    private void loadSummaryData() {
        new Thread(() -> {
            try {
                // 添加调试日志
                System.out.println("开始加载摘要数据...");

                int totalMatches = matchService.getTotalMatches(username);
                String topChampion = championService.getTopChampion(username);
                String rank = playerService.getPlayerRank(username);

                // 验证数据获取
                System.out.println("获取到数据 - 对局数:" + totalMatches
                    + " 英雄:" + topChampion
                    + " 段位:" + rank);

                SwingUtilities.invokeLater(() -> {
                    // 添加组件状态验证
                    System.out.println("更新UI前标签状态 - matchCountLabel:" + matchCountLabel);

                    matchCountLabel.setText(String.valueOf(totalMatches));
                    topChampionLabel.setText(topChampion != null ? topChampion : "无数据");
                    rankLabel.setText(rank != null ? rank : "未定级");

                    // 添加刷新日志
                    System.out.println("UI更新完成，强制刷新...");
                    cardContainer.revalidate();
                    cardContainer.repaint();
                    // 强制刷新组件
                    matchCountLabel.revalidate();
                    matchCountLabel.repaint();
                    cardContainer.revalidate();
                });
            } catch (Exception e) {
                System.err.println("摘要数据异常: " + e.getMessage());
            }
        }).start();
    }

    private void loadMatchRecords() {
        new Thread(() -> {
            try {
                List<Map<String, Object>> records = matchService.getMatchRecords(username);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) matchRecordTable.getModel();
                    model.setRowCount(0);

                    records.forEach(record -> {
                        // 修正字段名匹配数据库返回的key
                        Object matchId = record.get("match_id");
                        Object result = record.get("result");
                        Object gold = record.get("goldearned");

                        model.addRow(new Object[]{matchId, result, gold});
                    });

                    model.fireTableDataChanged();
                });
            } catch (Exception e) {
                System.err.println("比赛记录异常: " + e.getMessage());
            }
        }).start();
    }

    private void loadChampionData() {
        new Thread(() -> {
            try {
                List<Map<String, Object>> stats = championService.getChampionStats(username);
                System.out.println("英雄数据原始响应：" + stats);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) championDataTable.getModel();
                    model.setRowCount(0); // 清空数据

                    stats.forEach(stat -> {
                        // 添加字段存在性检查
                        Object name = stat.getOrDefault("champion_name", "未知");
                        Object games = stat.getOrDefault("games_played", 0);
                        Object winRate = stat.getOrDefault("win_rate", 0.0);

                        System.out.println("处理英雄数据：" + name + "|" + games + "|" + winRate);

                        model.addRow(new Object[]{
                            name,
                            games,
                            String.format("%.1f%%", ((Number) winRate).doubleValue() * 100)
                        });
                    });

                    // 添加表格刷新逻辑
                    model.fireTableDataChanged();
                    championDataTable.revalidate();
                    championDataTable.repaint();
                    tabbedPane.revalidate(); // 新增：刷新选项卡容器
                    tabbedPane.repaint();
                });
            } catch (Exception e) {
                System.err.println("英雄数据异常: " + e.getMessage());
                e.printStackTrace(); // 添加堆栈跟踪
            }
        }).start();
    }

    private void loadPlayerProfile() {
        new Thread(() -> {
            try {
                Map<String, Object> profile = playerService.getplayerInfo(username);
                System.out.println("玩家档案原始数据：" + profile);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) playerProfileTable.getModel();
                    model.setRowCount(0);

                    if (profile != null && !profile.isEmpty()) {
                        Object name = profile.getOrDefault("player_name", "N/A");
                        Object id = profile.getOrDefault("player_id", "N/A");
                        Object rank = profile.getOrDefault("rank", "未定级");

                        System.out.println("加载玩家档案：" + name + "|" + id + "|" + rank);

                        model.addRow(new Object[]{name, id, rank});
                        playerProfileTable.repaint();
                    }
                });
            } catch (Exception e) {
                System.err.println("玩家档案异常: " + e.getMessage());
                e.printStackTrace(); // 添加堆栈跟踪
            }
        }).start();
    }

    //=== UI辅助方法 ===//
    private JPanel createSummaryCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 标题标签增加自动换行
        JLabel titleLabel = new JLabel("<html><center>" + title + "</center></html>");
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 24));

        // 数值标签使用动态字体大小
        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("微软雅黑", Font.BOLD, 36));

        // 确保添加顺序：先标题后数值
        card.add(titleLabel, BorderLayout.NORTH);  // 索引0
        card.add(valueLabel, BorderLayout.CENTER); // 索引1
        return card;
    }

    private void searchPlayer() {
        String playerId = searchField.getText().trim();
        if (!playerId.isEmpty()) {
            this.username = playerId;
            setTitle("玩家数据看板 - " + playerId);

            // 强制刷新所有标签页数据
            loadSummaryData();
            loadMatchRecords();  // 始终加载对局记录
            loadChampionData();  // 始终加载英雄数据
            loadPlayerProfile(); // 始终加载玩家档案
        }
    }
    private JPanel wrapTablePanel(JTable table) {
        table.setRowHeight(40); // 增加行高
        table.setFont(new Font("微软雅黑", Font.PLAIN, 18)); // 增大字体
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 50, 40));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("微软雅黑", Font.BOLD, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }
}
