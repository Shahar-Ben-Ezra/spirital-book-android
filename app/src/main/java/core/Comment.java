package core;


public class Comment {

  private String date;
  private  String  description;
  private  String Comment_id ;
  private  String UserName;
  private  String Chapter_id ;
  private  String rate;

  public Comment(String date, String description, String comment_id, String userName, String chapter_id,String rate) {
    this.date = date;
    this.description = description;
    Comment_id = comment_id;
    UserName = userName;
    Chapter_id = chapter_id;
    this.rate=rate;
  }
  public Comment(){}

  public String getrate() {
    return rate;
  }

  public void setrate(String rate1) {

    rate = rate1;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getComment_id() {
    return Comment_id;
  }

  public void setComment_id(String comment_id) {
    Comment_id = comment_id;
  }

  public String getUserName() {
    return UserName;
  }

  public void setUserName(String userName) {
    UserName = userName;
  }

  public String getChapter_id() {
    return Chapter_id;
  }

  public void setChapter_id(String chapter_id) {
    Chapter_id = chapter_id;
  }
}
