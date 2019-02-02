package core;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * author Shahar Ben-Ezra
 * Comment obj
 */
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



  /**
   * from json to list of Comment obj
   * @param json
   * @return
   */
  public static List<Comment> parseJson(JSONObject json) {

    List<Comment> Comment = null;
    try {
      Comment = new ArrayList<Comment>();
      JSONArray CommentJsonArr = json.getJSONArray("comments");

      for (int i = 0; i < CommentJsonArr.length(); i++) {
        try {
          JSONObject fObj = CommentJsonArr.getJSONObject(i);
          Comment  c = new Comment();
          if(c.fromJson(fObj)){
            Comment.add(c);
          }
        } catch (Throwable e) {
          e.printStackTrace();
        }
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }

    return Comment;
  }

  /**
   * set each row at JSONObject to  new values attribute at Comment obj depend to his row name
   * @param fObj
   * @return
   */
  public boolean fromJson(JSONObject fObj) {
    boolean res = false;
    try {
      setComment_id(fObj.getString("commentid"));
      setChapter_id(fObj.getString("chapterId"));
      setDescription(fObj.getString("description"));
      setDate(fObj.getString("date"));
      setrate(fObj.getString("rate"));
      setUserName(fObj.getString("userName"));


      res = true;
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return res;

  }
}
