package core;


import android.graphics.Bitmap;

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

}
