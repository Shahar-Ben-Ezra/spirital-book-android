package core;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.ByteArrayOutputStream;
import java.util.Objects;

public class Book {

    private String Book_id;
    private String title;
    private String UserName ;
    private String phase;// in progress completed
    private String length;/// this  length is total length all of the chapters together
    private String updaeOn;
    private String Language;
    private String CategoryName;//// its just one category at each book
    private String numChapters;
    private String vote_comment;// how many user comment_activity on  this book
    private String vote_heart;// how many user like this book
    private String vote_list;// how many users the book is in there list
    private Bitmap image = null; ;
    public Book(){}



    public Book(String book_id, String title, String userName, String phase, String length, String updaeOn, String language, String categoryName, String numChapters, String vote_comment, String vote_heart, String vote_list, Bitmap image) {
        Book_id = book_id;
        this.title = title;
        UserName = userName;
        this.phase = phase;
        this.length = length;
        this.updaeOn = updaeOn;
        Language = language;
        CategoryName = categoryName;
        this.numChapters = numChapters;
        this.vote_comment = vote_comment;
        this.vote_heart = vote_heart;
        this.vote_list = vote_list;
        this.image = image;
    }

    public String getBook_id() {
        return Book_id;
    }

    public void setBook_id(String book_id) {
        Book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getUpdaeOn() {
        return updaeOn;
    }

    public void setUpdaeOn(String updaeOn) {
        this.updaeOn = updaeOn;
    }

    public String getLanguage() {
        return Language;
    }

    public void setLanguage(String language) {
        Language = language;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getnumChapters() {
        return numChapters;
    }

    public void setDescription(String numChapters) {
        this.numChapters = numChapters;
    }

    public String getVote_comment() {
        return vote_comment;
    }

    public void setVote_comment(String vote_comment) {
        this.vote_comment = vote_comment;
    }

    public String getVote_heart() {
        return vote_heart;
    }

    public void setVote_heart(String vote_heart) {
        this.vote_heart = vote_heart;
    }

    public String getVote_list() {
        return vote_list;
    }

    public void setVote_list(String vote_list) {
        this.vote_list = vote_list;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }


    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }

    @SuppressLint("NewApi")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(Book_id, book.Book_id) &&
                Objects.equals(title, book.title) &&
                Objects.equals(UserName, book.UserName) &&
                Objects.equals(phase, book.phase) &&
                Objects.equals(length, book.length) &&
                Objects.equals(updaeOn, book.updaeOn) &&
                Objects.equals(Language, book.Language) &&
                Objects.equals(CategoryName, book.CategoryName) &&
                Objects.equals(numChapters, book.numChapters) &&
                Objects.equals(vote_comment, book.vote_comment) &&
                Objects.equals(vote_heart, book.vote_heart) &&
                Objects.equals(vote_list, book.vote_list) &&
                Objects.equals(image, book.image);
    }

}
