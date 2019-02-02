package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * author Shahar Ben-Ezra
 * bookList obj
 */
public class BookList {

  private  String idBookList;// id
  private  String BookListName;// name
  private String CountBookList; // how many books is in this book list
  private  String userName;// userName


  public BookList(  String bookListName, String countBookList,String userName1) {

    Random random = new Random();
    String x = String.valueOf(random.nextInt(900) + 100);

    this.idBookList = x;
    BookListName = bookListName;
    CountBookList = countBookList;
    userName=userName1;


  }
  public BookList(){

  }
  public String getIdBookList() {
    return idBookList;
  }

  public void setIdBookList(String idBookList) {
    this.idBookList = idBookList;
  }

  public String getBookListName() {
    return BookListName;
  }

  public void setBookListName(String bookListName) {
    BookListName = bookListName;
  }

  public String getCountBookList() {
    return CountBookList;
  }

  public void setCountBookList(String countBookList) {
    CountBookList = countBookList;
  }



  public String getUserName() {
    return userName;
  }
  public void setUserName(String userName1) {
    userName = userName1;
  }


  /**
   * from json to list of bookList obj
   * @param json
   * @return
   */

  public static List<BookList> parseJson(JSONObject json) {

    List<BookList> BookList = null;
    try {
      BookList = new ArrayList<BookList>();
      JSONArray foldersJsonArr = json.getJSONArray("lists");

      for (int i = 0; i < foldersJsonArr.length(); i++) {
        try {
          JSONObject fObj = foldersJsonArr.getJSONObject(i);
          BookList  list = new BookList();
          if(list.fromJson(fObj)){
            BookList.add(list);
          }
        } catch (Throwable e) {
          e.printStackTrace();
        }
      }

    } catch (Throwable e) {
      e.printStackTrace();
    }

    return BookList;
  }

  /**
   * set each row at JSONObject to  new values attribute at bookList obj depend to his row name
   * @param fObj
   * @return
   */
  public boolean fromJson(JSONObject fObj) {
    boolean res = false;
    try {
      setUserName(fObj.getString("userName"));
      setIdBookList(fObj.getString("idBookList"));
      setBookListName(fObj.getString("BookListName"));
      setCountBookList(fObj.getString("CountBookList"));

      res = true;
    } catch (Throwable t) {
      t.printStackTrace();
    }
    return res;

  }

}
