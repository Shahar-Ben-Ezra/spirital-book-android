package core;

public class Library {


    private String userName;
    private String bookId;


    public Library(String userName, String bookId) {
        super();
        this.userName = userName;
        this.bookId = bookId;
    }


    public String getUserName() {
        return userName;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public String getBookId() {
        return bookId;
    }


    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
