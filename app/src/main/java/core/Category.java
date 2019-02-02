package core;


/**
 * author Shahar Ben-Ezra
 * category obj
 */
  public class Category {
    private String category_id;
    private String Category_name;
    private String Type_name;
    private String conut;

    public Category(){

    }
  public Category(String category_id, String category_name, String type_name, String conut) {
    this.category_id = category_id;
    Category_name = category_name;
    Type_name = type_name;
    this.conut = conut;
  }

  public String getCategory_id() {
    return category_id;
  }

  public void setCategory_id(String category_id) {
    this.category_id = category_id;
  }

  public String getCategory_name() {
    return Category_name;
  }

  public void setCategory_name(String category_name) {
    Category_name = category_name;
  }

  public String getType_name() {
    return Type_name;
  }

  public void setType_name(String type_name) {
    Type_name = type_name;
  }

  public String getConut() {
    return conut;
  }

  public void setConut(String conut) {
    this.conut = conut;
  }
}