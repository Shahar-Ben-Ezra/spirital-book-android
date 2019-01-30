package core;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * this class manage all the information
 */
public class MyInfoManager {

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
     * get All Library Books
     * @return

    public List<Book> getAllLibraryBooks() {
        List<Book> result = new ArrayList<Book>();
        if (db != null) {
            result = db.getAllLibraryBooks();
        }
        return result;
    }*/
    /**
     * add book
     * @param b
     */
    public void AddBooks(Book b) {
        if (db != null) {
            db.createBook(b);
        }
    }

    /**
     * update book
     * @param b
     */
    public void updateBook(Book b) {
        db.updateBook(b);
    }


    /**
     * add  list book
     * @param b
     */
    public void AddListBook(BookList b) {
        if (db != null) {
            db.createListBook(b);
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
        }
    }


    public void  createManyToManyTableBook(String BookId,String listId){
        if (db != null) {

            db.createManyToManyTableBook(BookId, listId);
        }
    }
    /**
     * add  createChapter
     * @param C
     */
    public void createChapter(Chapter C) {
        if (db != null) {
            db.createChapter(C);
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
            return    db.updateBookList(B);
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
                db.createComment(c);
            }

        }

    /**
     *  delet Comment
     * @param c
     */
        public void deletComment(Comment c) {
            if (db != null) {
                db.deletComment(c);
            }
        }


    /**
     * create Library
     * @param l
     */
    public void createLibrary( Library l) {

        if (db != null) {
            db.insertLibrary(l.getBookId(),l.getUserName());
        }

    }

    /**
     *  delete Library
     * @param l
     */
    public void deleteLibrary(Library l) {
        if (db != null) {
            db.deleteLibrary(l);
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
            db.insertLike(l.getBookId(),l.getUserName());
        }

    }

    /**
     *  delete Like
     * @param l
     */
    public void deleteLike(Like l) {
        if (db != null) {
            db.deleteLike(l);
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

}

