package core;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import com.example.shaharben_ezra.myapplication.Main;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ds.utils.NetworkConnector;
import ds.utils.NetworkResListener;
import ds.utils.ResStatus;

/**
 * this class manage all the information
 */
public class MyInfoManager implements NetworkResListener {
     private static MyInfoManager instance = null;
    private Context context = null;
    private MyInfoDatabase db = null;
    private Book b = null;


    public static MyInfoManager getInstance() {
        if (instance == null) {
            instance = new MyInfoManager();
        }
        return instance;
    }


    public Context getContext() {
        return context;

    }

    public void openDataBase(Context context) {
        this.context = context;
        if (context != null) {
            db = new MyInfoDatabase(context);
            db.open();
        }
    }
    public void closeDataBase() {
        if(db!=null){
            db.close();
        }
    }

    /**
     * get all  books
     * @return
     */
    public List<Book> getAllBooks() {
        List<Book> result = new ArrayList<Book>();
        if (db != null) {
            result = db.getAllBook();
        }
        return result;
    }

    /**
     *  get All Books By User Name
     * @param UserName
     * @return
     */
    public List<Book> getAllBooksByUserName(String UserName) {
        List<Book> result = new ArrayList<Book>();
        if (db != null) {
            result = db.getBooksObjByUserName(UserName);
        }
        return result;
    }

    /**
     * add book
     * @param b
     */
    public void AddBooks(Book b) {
        if (db != null) {
         if(   db.createBook(b))

             NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_BOOK_REQ, b, instance);

        }
    }

    /**
     * update book
     * @param b
     */
    public void updateBook(Book b) {

        if (db != null) {
            if (db.updateBook(b) != 0)

                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_BOOK_REQ, b, instance);

        }
    }


    /**
     * add  list book
     * @param b
     */
    public void AddListBook(BookList b) {
        if (db != null) {
            db.createListBook(b);
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_LISTSBOOk_REQ,b,instance);
        }
    }

    /**
     * get All list Books
     * @return
     */
    public List<BookList> getAllListBooks(String userName) {
        List<BookList> result = new ArrayList<BookList>();
        if (db != null) {
            result = db.getAllListBook(userName);
        }
        return result;
    }
    /**
     * get All The Books At BookList by book list id
     * @return
     */
    public List<Book> getAllTheBooksAtList(String id ) {
        List<Book> result = new ArrayList<Book>();
        if (db != null) {
            result = db.getAllTheBooksAtList(id);
        }
        return result;
    }


    /**
     * delete Listbook
     * @param B
     */
    public void deleteListbook(BookList B)
    {

        if (db != null) {
            db.deleteBooklist(B);
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_LISTSBOOK_REQ, B, instance);

        }
    }

    /**
     * create Many To Many Table Book
     * @param BookId
     * @param listId
     */

    public void  createManyToManyTable(String BookId,String listId){
        if (db != null) {

            db.createManyToManyTableBook(BookId, listId);

            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_ListBookManyToMany_REQ, new  ListBookManyToMany(BookId,listId), instance);

        }
    }
    /**
     * add  createChapter
     * @param C
     */
    public void createChapter(Chapter C) {
        if (db != null) {
            if(db.createChapter(C))
               NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_CHAPTER_REQ, C, instance);

        }
    }

    /**
     *  get All Chapter By Book Id
     * @param C
     */
    public List<Chapter> getAllChapterByBookId(String C) {
        List<Chapter> result = new ArrayList<Chapter>();

        if (db != null) {
            result= db.getAllChapterByBookId(C);
        }
        return result;

    }

    /**
     * get Book Obj By Book-Id
     * @param B
     */
    public Book getBooksObjByBookId(String B) {
        if (db != null) {
         return    db.getBooksObjByBookId(B);
        }
        return null;
    }


    /**
     * update Book List
     * @param B
     */
    public int updateBookList(BookList B) {
        if (db != null) {
             if ( db.updateBookList(B) !=0 ){
                 NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_LISTSBOOk_REQ, B, instance);

                 return  -1;
            }
        }
        return 0;
    }


    /**
     * get All Comment By ChapterId
     * @param ChapterId
     * @return
     */
    public List<Comment> getAllCommentByChapterId(String ChapterId) {
        List<Comment> result = new ArrayList<Comment>();

        if (db != null) {
            result=(db.getAllCommentByChapterId(ChapterId))   ;
        }
        return result;
    }

    /**
     * update Comment
     * @param c
     */
    public void updateComment( Comment c) {

        if (db != null) {
            db.updateComment(c);
        }

    }

    /**
     * create Comment
     * @param c
     */
        public void createComment( Comment c) {

            if (db != null) {
                if(db.createComment(c))
                NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_COMMENT_REQ, c, instance);


            }

        }

    /**
     *  delet Comment
     * @param c
     */
        public void deleteComment(Comment c) {
            if (db != null) {
                db.deleteComment(c);
            }
        }


    /**
     * create Library
     * @param l
     */
    public void createLibrary( Library l) {

        if (db != null) {
            db.insertLibrary(l.getBookId(),l.getUserName());
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_Library_REQ,l.getUserName(),l.getBookId(), instance);

        }

    }

    /**
     *  delete Library
     * @param l
     */
    public void deleteLibrary(Library l) {
        if (db != null) {
           if( db.deleteLibrary(l))
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_Library_REQ,l.getUserName(),l.getBookId(), instance);

        }
    }


    /**
     *  get Library
     * @param userName
     */
    public List<Library> getLibrary(String userName) {
        if (db != null) {
         return    db.getAllLibrary(userName);
        }
        return null;
    }


    /**
     * create Like
     * @param l
     */
    public void createLike( Like l) {
         if (db != null) {
           if( db.insertLike(l.getBookId(),l.getUserName()))
             NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.INSERT_Like_REQ,l.getUserName(),l.getBookId(), instance);

         }

    }

    /**
     *  delete Like
     * @param l
     */
    public void deleteLike(Like l) {
        if (db != null) {
         if(   db.deleteLike(l))
            NetworkConnector.getInstance().sendRequestToServer(NetworkConnector.DELETE_Like_REQ,l.getUserName(),l.getBookId(), instance);

        }
    }


    /**
     *  Find if the user like this book
     * @param l
     */
    public  Boolean  getLike(Like l) {
        if (db != null) {
            return db.getLikes(l.getUserName(),l.getBookId());
        }
        return false;
    }

    /**
     *  Find if the book is in the user library
     * @param l
     */
    public  Library  getLibrary(Library l) {
        if (db != null) {
            return db.getLibrary(l.getUserName(),l.getBookId());
        }
        return null;
    }

    /**
     * add Category
     * @param c
     */
       public void AddCategory(Category c) {
        if (db != null) {
            db.createCategory(c);
        }
    }
    /**
     * get all  Categories
     * @return
     */
    public List<Category> getAllCategories() {
        List<Category> result = new ArrayList<Category>();
        if (db != null) {
            result = db.getAllCategories();
        }
        return result;
    }


    /**
     *  Find All The Categories that them type is type_name
     * @param Type_name
     */
    public  List<Category>  getCategoryByType_name(String Type_name) {
        if (db != null) {
            return db.getAllCategoryAtType(Type_name );
        }
        return null;
    }

    /**
     * update Category
     * @param c
     */
    public void updateCategory(Category c) {
        db.updateCategory(c);
    }

    /**
     * get Category obj
     * @param Category_name
     */
    public Category getCategoryobj(String Category_name) {
       return db.getCategoryobj(Category_name);
    }


    /**
     * get All The Books   by CategoryName
     * @return
     */
    public List<Book> getAllTheBooksCategoryName(String CategoryName ) {
        List<Book> result = new ArrayList<Book>();
        if (db != null) {
            result = db.getAllTheBooksCategoryName(CategoryName);
        }
        return result;
    }



    /**
     * get a new JSONObject pare it and
     * update/insert BookList obj to BookList table  if it necessary
     * @param content
     */
    public void updateResources(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<BookList> list = BookList.parseJson(content);
            if(list!=null && list.size()>0){
                for(BookList bookList:list){
                     syncBookList(bookList) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    public Chapter getChapterByChapterId(String Chapterid) {

        return   db.getChapterByChapterId(Chapterid);
    }

    /**
     * get a new JSONObject pare it and
     * update/insert Comment obj to Comment table  if it necessary
     * @param content
     */
    public void updateResourcesComment(JSONObject content) {
        if(content==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Comment> list = Comment.parseJson(content);
            if(list!=null && list.size()>0){
                for(Comment Comment:list){
                    syncComment(Comment) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    /**
     * callback method which called when the resources update is started
     */
    @Override
    public void onPreUpdate() {

            Toast.makeText(context,"Sync stated...",Toast.LENGTH_SHORT).show();
    }

    /**
     * callback method which called after resources update is finished
     *
     * @param res    - the data
     * @param status - the status of the update process
     */
    @Override
    public void onPostUpdate(byte[] res, ResStatus status) {
            if(res!=null){
                Toast.makeText(context,"Sync finished...status " + res.toString(),Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(context, "Sync finished...status " + status.toString(), Toast.LENGTH_SHORT).show();
            }
    }

    @Override
    public void onPostUpdate(JSONObject res, ResStatus status, String requestNum) {

    }

    @Override
    public void onPostUpdate(Bitmap res, ResStatus status) {

    }

    /**
     * get a new JSONObject pare it and
     * update/insert Book obj to Book table  if it necessary
     * @param B
     */
    public void updateResourcesBook(JSONObject B) {
        if(B==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Book> list = Book.parseJson(B);
            if(list!=null && list.size()>0){
                for(Book book:list){
                    syncBook(book) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    /**
     * get a new JSONObject pare it and
     * update/insert Chapter obj to Chapter table  if it necessary
     * @param B
     */
    public void updateResourcesChapter(JSONObject B) {
        if(B==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<Chapter> list = Chapter.parseJson(B);
            if(list!=null && list.size()>0){
                for(Chapter chapter:list){
                    syncChapter(chapter) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    /**
     * get a new JSONObject pare it and
     * update/insert BookManyToMany obj to BookManyToMany table  if it necessary
     * @param B
     */
    public void updateResourcesListBookManyToMany(JSONObject B) {
        if(B==null){
            return;
        }
        try {
            //String content = new String(res, "UTF-8");
            List<ListBookManyToMany> list = ListBookManyToMany.parseJson(B);
            if(list!=null && list.size()>0){
                for(ListBookManyToMany lbtm:list){
                    synclbtm(lbtm) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
    }

    }

    /**
     * get a new JSONObject pare it and
     * update/insert Library obj to Library table  if it necessary
     * @param B
     */
    public void updateResourcesLibrary(JSONObject B) {
        if(B==null){
            return;
        }
        try {
             List<Library> list = Library.parseJson(B);
            if(list!=null && list.size()>0){
                for(Library library:list){
                    syncLibrary(library) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }
        Main.progressDialog.dismiss();

    }

    /**
     * get a new JSONObject pare it and
     * update/insert Like obj to like table  if it necessary
     * @param B
     */
    public void updateResourcesLike(JSONObject B) {
        if(B==null){
            return;
        }
        try {
            List<Like> list = Like.parseJson(B);
            if(list!=null && list.size()>0){
                for(Like Like:list){
                    syncLike(Like) ;

                }
            }
        }
        catch(Throwable t){
            t.printStackTrace();
        }

    }

    /**
     * syn  Book table
     * @param B
     * @return
     */
    private boolean syncBook(Book B) {
        boolean res = false;
        if (db != null) {
            if( db.createBook (B)){

                res = true;

            }
            else{
                db.updateBook(B);
                 res = true;
            }
        }
        return res;
    }

    /**
     * syn  Like table
     * @param l
     * @return
     */
    private boolean syncLike(Like l) {
        boolean res = false;
        if (db != null) {
            if( db.insertLike (l.getBookId(),l.getUserName()) ){

                res = true;

            }
        }
        return res;
    }

    /**
     * syn  Library table
     * @param l
     * @return
     */
    private boolean syncLibrary(Library l) {
        boolean res = false;
        if (db != null) {
            if( db.insertLibrary (l.getBookId(),l.getUserName())){

                res = true;

            }
        }
        return res;
    }

    /**
     * syn   Chapter table
     * @param chapter
     * @return
     */
    private boolean syncChapter(Chapter chapter) {
        boolean res = false;
        if (db != null) {
            if( db.createChapter (chapter)){

                res = true;

            }
        }
        return res;
    }

    /**
     * syn  ListBookManyToMany table
     * @param lbtm
     * @return
     */
    private boolean synclbtm(ListBookManyToMany lbtm) {
        boolean res = false;
        if (db != null) {
             db.createManyToManyTableBook (lbtm.getId(),lbtm.getIdList());

                res = true;

            }

        return res;
    }


    /**
     * syn  Comment table
     * @param c
     * @return
     */
    private boolean syncComment(Comment c) {
        boolean res = false;
        if (db != null) {
            if( db.createComment(c)){

                res = true;

            }
            else{
                db.updateComment(c);
//                Chapter chapter =getChapterByChapterId(c.getChapter_id())  ;
//
//                Book b= getBooksObjByBookId(chapter.getBook_id());
//
//                b.setVote_comment(String.valueOf(Integer.parseInt(b.getVote_comment()+1)));
                res = true;
            }
        }
        return res;
    }

    /**
     * syn  BookList table
     * @param BookList
     * @return
     */
    private boolean syncBookList(BookList BookList) {
        boolean res = false;
        if (db != null) {
            if(!db.createListBook(BookList)){
                db.updateBookList(BookList);
                res = true;
            }
            else{
                res = true;
            }
        }
        return res;
    }
}

