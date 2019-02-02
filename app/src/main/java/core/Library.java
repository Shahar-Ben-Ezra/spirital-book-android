package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * author Shahar Ben-Ezra
 * Library obj
 */
public class Library {


    private String userName;
    private String bookId;

    public Library(){}
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




    /**
     * from json to list of Library obj
     * @param json
     * @return
     */
    public static List<Library> parseJson(JSONObject json) {

        List<Library> ListLibrary = null;
        try {
            ListLibrary = new ArrayList<Library>();
            JSONArray ChapterJsonArr = json.getJSONArray("Library");

            for (int i = 0; i < ChapterJsonArr.length(); i++) {
                try {
                    JSONObject fObj = ChapterJsonArr.getJSONObject(i);
                    Library  library = new Library();
                    if(library.fromJson(fObj)){
                        ListLibrary.add(library);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return ListLibrary;
    }




    /**
     * set each row at JSONObject to  new values attribute at Library obj depend to his row name
     * @param fObj
     * @return
     */
    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setUserName(fObj.getString("userName"));
            setBookId(fObj.getString("bookid"));
            res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }
}
