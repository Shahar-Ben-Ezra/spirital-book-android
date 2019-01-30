package core;

import java.util.Random;

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
}
