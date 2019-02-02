package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * author Shahar Ben-Ezra
 * Like obj
 */
public class Like {

    private String userName;
    private String bookId;

    public Like(){}
    public Like(String userName, String bookId) {
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
     * from json to list of Like obj
     * @param json
     * @return
     */
    public static List<Like> parseJson(JSONObject json) {

        List<Like> LikeList  = null;
        try {
            LikeList = new ArrayList<Like>();
            JSONArray LikeJsonArr = json.getJSONArray("Like");

            for (int i = 0; i < LikeJsonArr.length(); i++) {
                try {
                    JSONObject fObj = LikeJsonArr.getJSONObject(i);
                    Like   Like = new  Like();
                    if(Like.fromJson(fObj)){
                        LikeList.add(Like);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return LikeList;
    }

    /**
     * set each row at JSONObject to  new values attribute at Like obj depend to his row name
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
