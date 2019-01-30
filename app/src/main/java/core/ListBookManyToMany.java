package core;

public class ListBookManyToMany {


    private String id;// book id
    private String idList;// list id

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
}