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
    private JButton addButton;
    private JButton editButton;
    private JButton deleteButton;

    public DataDashboard(String username) {
        this.username = username;
        setTitle("玩家数据看板 - " + username);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // 改为全屏模式
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            initComponents();
            loadSummaryData();
            loadPlayerProfile();// 初始化时加载摘要数据
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
    // 在initComponents方法中修正按钮初始化
    private void initComponents() throws SQLException {
        // 修改主布局初始化顺序
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));

        // 扩展搜索面板添加操作按钮
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchField = new JTextField(15);
        searchButton = new JButton("搜索");
        addButton = new JButton("新增");    // 原代码使用局部变量
        editButton = new JButton("编辑");    // 原代码使用局部变量
        deleteButton = new JButton("删除");  // 原代码使用局部变量

        // 添加按钮样式设置
        styleButton(searchButton, new Color(52, 152, 219));
        styleButton(addButton, new Color(46, 204, 113));
        styleButton(editButton, new Color(241, 196, 15));
        styleButton(deleteButton, new Color(231, 76, 60));

        // 添加按钮事件监听
        searchButton.addActionListener(e -> searchPlayer());
        addButton.addActionListener(e -> showAddPlayerDialog());
        editButton.addActionListener(e -> showEditPlayerDialog());
        deleteButton.addActionListener(e -> deletePlayer());

        searchPanel.add(new JLabel("玩家ID:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(addButton);
        searchPanel.add(editButton);
        searchPanel.add(deleteButton);

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
        playerProfileTable = new JTable(new DefaultTableModel(new Object[][]{}, new String[]{"玩家名称", "玩家ID", "段位"})); // 移除了 champion_id 相关列

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
                System.out.println("开始加载摘要数据...");

                // 添加同步调试日志
                System.out.println("当前玩家ID: " + username);

                int totalMatches = matchService.getTotalMatches(username);
                String topChampion = championService.getTopChampion(username);
                String rank = playerService.getPlayerRank(username);

                // 添加数据验证
                System.out.println("获取到数据 - 对局数:" + totalMatches
                        + " 英雄:" + (topChampion != null ? topChampion : "null")
                        + " 段位:" + (rank != null ? rank : "null"));

                SwingUtilities.invokeLater(() -> {
                    // 强制重置卡片容器布局
                    cardContainer.removeAll();

                    // 重新创建卡片保证数据刷新
                    JPanel matchCard = createSummaryCard("总对局", String.valueOf(totalMatches), new Color(52, 152, 219));
                    JPanel championCard = createSummaryCard("常用英雄", topChampion != null ? topChampion : "无数据", new Color(46, 204, 113));
                    JPanel rankCard = createSummaryCard("当前段位", rank != null ? rank : "未定级", new Color(241, 196, 15));

                    cardContainer.add(matchCard);
                    cardContainer.add(championCard);
                    cardContainer.add(rankCard);

                    // 添加卡片刷新逻辑
                    cardContainer.revalidate();
                    cardContainer.repaint();
                    System.out.println("卡片已强制刷新");
                });
            } catch (Exception e) {
                System.err.println("摘要数据异常: " + e.getMessage());
                e.printStackTrace(); // 添加堆栈跟踪
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

    //=== 数据加载方法 ===//
    private void loadPlayerProfile() {
        new Thread(() -> {
            try {
                List<Map<String, Object>> allPlayers = playerService.getAllPlayers();
                System.out.println("全部玩家数据：" + allPlayers);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) playerProfileTable.getModel();
                    model.setRowCount(0);

                    if (allPlayers != null && !allPlayers.isEmpty()) {
                        allPlayers.forEach(player -> {
                            Object name = player.getOrDefault("player_name", "N/A");
                            Object id = player.getOrDefault("player_id", "N/A");
                            Object rank = player.getOrDefault("rank", "未定级");

                            System.out.println("加载玩家数据：" + name + "|" + id + "|" + rank);
                            model.addRow(new Object[]{name, id, rank});
                        });
                        playerProfileTable.revalidate();
                        playerProfileTable.repaint();
                    }
                });
            } catch (Exception e) {
                System.err.println("玩家档案异常: " + e.getMessage());
                e.printStackTrace();
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
        // 修复方法体闭合问题
        table.setRowHeight(40);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 50, 40));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("微软雅黑", Font.BOLD, 14));

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    } // 添加缺失的方法闭合括号

    //=== 玩家管理方法 ===//
    // 添加缺失的删除玩家方法
    private void deletePlayer() {
        String playerId = searchField.getText().trim();
        if (playerId.isEmpty()) {
            showError("请先输入要删除的玩家ID");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
            "确定要删除玩家 " + playerId + " 吗？",
            "确认删除", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new Thread(() -> {
                try {
                    String result = playerService.deletePlayer(playerId);
                    showSuccess(result);
                    refreshData();
                } catch (Exception e) {
                    showError("删除失败: " + e.getMessage());
                }
            }).start();
        }
    }

    private void showAddPlayerDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField rankField = new JTextField();

        Object[] message = {
                "玩家ID（必填）:", idField,
                "玩家名称（必填）:", nameField,
                "初始段位:", rankField
        };

        int option = JOptionPane.showConfirmDialog(this, message, "新增玩家", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // 添加输入验证
            if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                showError("玩家ID和名称不能为空");
                return;
            }

            new Thread(() -> {
                try {
                    String result = playerService.addPlayer(
                            idField.getText().trim(),
                            nameField.getText().trim(),
                            rankField.getText().trim()
                    );
                    showSuccess(result);
                    refreshData();
                } catch (Exception e) {
                    showError("添加失败: " + e.getMessage());
                }
            }).start();
        }
    }

    private void showEditPlayerDialog() {
        try {
            Map<String, Object> profile = playerService.getplayerInfo(username);
            if (profile == null) {
                showError("找不到当前玩家信息");
                return;
            }

            JTextField nameField = new JTextField(profile.get("player_name").toString());
            JTextField rankField = new JTextField(profile.get("rank").toString());

            Object[] message = {
                "玩家名称:", nameField,
                "新段位:", rankField
            };

            int option = JOptionPane.showConfirmDialog(this, message,
                "编辑玩家信息", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                new Thread(() -> {
                    try {
                        String result = playerService.updatePlayer(
                            username,
                            nameField.getText().trim(),
                            rankField.getText().trim()
                        );
                        showSuccess(result);
                        refreshData();
                    } catch (Exception e) {
                        showError("更新失败: " + e.getMessage());
                    }
                }).start();
            }
        } catch (Exception e) {
            showError("获取玩家信息失败: " + e.getMessage());
        }
    }

    //=== 提示方法 ===//
    // 在showSuccess方法前添加错误提示方法
    private void showError(String message) {
        SwingUtilities.invokeLater(() ->
            JOptionPane.showMessageDialog(
                this,
                message,
                "错误",
                JOptionPane.ERROR_MESSAGE
            )
        );
    }

    private void showSuccess(String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(
                    this,
                    message,
                    "操作成功",
                    JOptionPane.INFORMATION_MESSAGE
                ));
    }

    // 在initComponents方法中优化按钮样式


    // 新增按钮样式统一方法
    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("微软雅黑", Font.BOLD, 14));
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    // 在refreshData方法中添加加载状态提示
    private void refreshData() {
        SwingUtilities.invokeLater(() -> {
            // 显示加载状态
            matchCountLabel.setText("加载中...");
            topChampionLabel.setText("加载中...");
            rankLabel.setText("加载中...");

            // 执行数据加载
            loadPlayerProfile();
            loadSummaryData();
            loadChampionData();
            loadMatchRecords();
        });
    }
}
