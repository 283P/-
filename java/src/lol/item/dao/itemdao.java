package lol.item.dao;

public interface itemdao {
    /** 插入 */
    public void insert(itemclass itemclass);
    /** 更新 */
    public void update(itemclass itemclass);
    /** 删除 */
    public void delete(itemclass itemclass);
    /** 查询 */
    public itemclass select(String item_ID);
    /** 查询所有 */
    public List<itemclass> selectAll();
}
