package core;


import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * author Shahar Ben-Ezra
 * Chapter obj
 */
public class Chapter {
  private  String Chapter_id;
  private  String Chapter_name;
  private  String length;
  private  String text;
  private  String AuthorNote;
  private  String EndNote;
  private String Book_id ;
  private Bitmap image = null;

    public Chapter(){}

  public Chapter(String chapter_id, String chapter_name, String length, String text, String authorNote, String endNote, String book_id, Bitmap image) {
        Chapter_id = chapter_id;
        Chapter_name = chapter_name;
        this.length = length;
        this.text = text;
        AuthorNote = authorNote;
        EndNote = endNote;
        Book_id = book_id;
        this.image = image;
  }

  public Bitmap getImage() {
    return image;
  }

  public void setImage(Bitmap image) {
    this.image = image;
  }

  public String getAuthorNote() {
    return AuthorNote;
  }

  public void setAuthorNote(String authorNote) {
    AuthorNote = authorNote;
  }

  public String getEndNote() {
    return EndNote;
  }

  public void setEndNote(String endNote) {
    EndNote = endNote;
  }

  public String getChapter_id() {
    return Chapter_id;
  }

  public void setChapter_id(String chapter_id) {
    Chapter_id = chapter_id;
  }

  public String getChapter_name() {
    return Chapter_name;
  }

  public void setChapter_name(String chapter_name) {
    Chapter_name = chapter_name;
  }

  public String getLength() {
    return length;
  }

  public void setLength(String length) {
    this.length = length;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getBook_id() {
    return Book_id;
  }

  public void setBook_id(String book_id) {
    Book_id = book_id;
  }



  /**
   * from json to list of Chapter obj
   * @param json
   * @return
   */

  public static List<Chapter> parseJson(JSONObject json) {

    List<Chapter> ChapterList = null;
    try {
      ChapterList = new ArrayList<Chapter>();
      JSONArray ChapterJsonArr = json.getJSONArray("Chapters");

      for (int i = 0; i < ChapterJsonArr.length(); i++) {
        try {
          JSONObject fObj = ChapterJsonArr.getJSONObject(i);
          Chapter  chapter = new Chapter();
          if(chapter.fromJson(fObj)){
            ChapterList.add(chapter);
          }
        } catch (Throwable e) {
          e.printStackTrace();
        }
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }

    return ChapterList;
  }

  /**
   * set each row at JSONObject to  new values attribute at Chapter obj depend to his row name
   * @param fObj
   * @return
   */
    public boolean fromJson(JSONObject fObj) {
      boolean res = false;
      try {
        setChapter_id(fObj.getString("chapterid"));
        setBook_id(fObj.getString("bookid"));
        setChapter_name(fObj.getString("chapterName"));
        setLength(fObj.getString("length"));
        setText(fObj.getString("text"));
        setAuthorNote(fObj.getString("AuthorNote"));
        setEndNote(fObj.getString("EndNote"));
         res = true;
      } catch (Throwable t) {
        t.printStackTrace();
      }
      return res;

    }
}
