package lol.item.entity;

public class itemclass {
    private String item_ID;
    private String item_name;
    private String item_type;
    private String item_cost;
    public String getItem_ID() {
        return item_ID;
    }
    public void setItem_ID(String item_ID) {
        this.item_ID = item_ID;
    }
    public String getItem_name() {
        return item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    public String getItem_cost() {
        return item_cost;
    }
    public void setItem_cost(String item_cost) {
        this.item_cost = item_cost;
    }
    public itemclass(String item_ID, String item_name, String item_type, String item_cost) {
        this.item_ID = item_ID;
        this.item_name = item_name;
        this.item_type = item_type;
        this.item_cost = item_cost;
    }
    public itemclass() {
    }
    @Override
    public String toString() {
        return "itemclass [item_ID=" + item_ID + ", item_name=" + item_name + ", item_type=" + item_type
                + ", item_cost=" + item_cost + ", getItem_ID()=" + getItem_ID() + ", getItem_name()=" + getItem_name()
                + ", getItem_type()=" + getItem_type() + ", getItem_cost()=" + getItem_cost() + ", getClass()="
                + getClass() + "]";
    }

}
