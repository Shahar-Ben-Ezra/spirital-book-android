package core;

import java.util.ArrayList;

public class Type {

    private int type_id;
    private String Type_name;
    private ArrayList<Category> categoryArrayList;

    public Type(int type_id, String category_name, ArrayList<Category> categoryArrayList) {
        this.type_id = type_id;
        Type_name = category_name;
        this.categoryArrayList = categoryArrayList;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return Type_name;
    }

    public void setType_name(String type_name) {
        Type_name = type_name;
    }

    public ArrayList<Category> getCategoryArrayList() {
        return categoryArrayList;
    }

    public void setCategoryArrayList(ArrayList<Category> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
    }
}
