package lol.champion.dao;

public class championDaoImpl implements championinter {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    @Override
    public void insert(championclass championclass) {
        String sql="insert into champion values(?,?,?,?,?)";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, championclass.getChampion_ID());
            pstmt.setString(2, championclass.getChampion_name());
            pstmt.setString(3, championclass.getRole());
            pstmt.setString(4, championclass.getAttacktype());
            pstmt.setString(5, championclass.getSkill_intro());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update(championclass championclass) {
        String sql="update champion set champion_name=?,role=?,attacktype=?,skill_intro=? where champion_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, championclass.getChampion_name());
            pstmt.setString(2, championclass.getRole());
            pstmt.setString(3, championclass.getAttacktype());
            pstmt.setString(4, championclass.getSkill_intro());
            pstmt.setString(5, championclass.getChampion_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void delete(championclass championclass) {
        String sql="delete from champion where champion_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, championclass.getChampion_ID());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public championclass select(String champion_ID) {
        String sql="select * from champion where champion_ID=?";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1, champion_ID);
            rs=pstmt.executeQuery();
            if(rs.next()){
                championclass championclass=new championclass();
                championclass.setChampion_ID(rs.getString("champion_ID"));
                championclass.setChampion_name(rs.getString("champion_name"));
                championclass.setRole(rs.getString("role"));
                championclass.setAttacktype(rs.getString("attacktype"));
                championclass.setSkill_intro(rs.getString("skill_intro"));
                return championclass;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public List<championclass> selectAll() {
        String sql="select * from champion";
        try {
            conn=DButil.getConnction();
            pstmt=conn.prepareStatement(sql);
            rs=pstmt.executeQuery();
            List<championclass> championclassList=new ArrayList<championclass>();
            while(rs.next()){
                championclass championclass=new championclass();
                championclass.setChampion_ID(rs.getString("champion_ID"));
                championclass.setChampion_name(rs.getString("champion_name"));
                championclass.setRole(rs.getString("role"));
                championclass.setAttacktype(rs.getString("attacktype"));
                championclass.setSkill_intro(rs.getString("skill_intro"));
                championclassList.add(championclass);
            }
            return championclassList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void close() {
        try {
            if(rs!=null) rs.close();
            if(pstmt!=null) pstmt.close();
            if(conn!=null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

