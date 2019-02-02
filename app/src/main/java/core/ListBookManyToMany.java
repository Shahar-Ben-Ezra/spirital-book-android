package core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
/**
 * author Shahar Ben-Ezra
 * ListBookManyToMany obj
 * its means a new obj that have id from book and id for list
 * i need this obj because if i add book to a listbook its possible that
 * user will added the same book for more then one list book and because of that we need
 * 2 pk one for book and one for list
 */
public class ListBookManyToMany {


    private String id;// book id
    private String idList;// list id
    public ListBookManyToMany(){}
    public ListBookManyToMany(String id, String idList) {
        super();
        this.id = id;
        this.idList = idList;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }


    @Override
    public String toString() {
        return "ListBookManyToMany [id=" + id + ", idList=" + idList + "]";
    }


    /**
     * from json to list of ListBookManyToMany obj
     * @param json
     * @return
     */
    public static List<ListBookManyToMany> parseJson(JSONObject json) {

        List<ListBookManyToMany> ListBookManyToMany = null;
        try {
            ListBookManyToMany = new ArrayList<ListBookManyToMany>();
            JSONArray ChapterJsonArr = json.getJSONArray("ListBookManyToMany");

            for (int i = 0; i < ChapterJsonArr.length(); i++) {
                try {
                    JSONObject fObj = ChapterJsonArr.getJSONObject(i);
                    ListBookManyToMany  lbtm = new ListBookManyToMany();
                    if(lbtm.fromJson(fObj)){
                        ListBookManyToMany.add(lbtm);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }

        } catch (Throwable e) {
            e.printStackTrace();
        }

        return ListBookManyToMany;
    }



    /**
     * set each row at JSONObject to  new values attribute at ListBookManyToMany obj depend to his row name
     * @param fObj
     * @return
     */
    public boolean fromJson(JSONObject fObj) {
        boolean res = false;
        try {
            setId(fObj.getString("id"));
            setIdList(fObj.getString("idList"));
             res = true;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return res;

    }
}