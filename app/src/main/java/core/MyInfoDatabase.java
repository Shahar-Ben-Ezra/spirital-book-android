package core;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * author Shahar Ben-Ezra
 * this class create insert delete updated a tables at sql lite
 */
public class MyInfoDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MyDB1";

    // Comment table
    private static final String TABLE_Comment = "Comment";
    private static final String Comment_Comment_id = "Comment_id";
    private static final String Comment_User_name = "user_name";
    private static final String Comment_Description = "description";
    private static final String Comment_Chapter_id = "Chapter_id";
    private static final String Comment_Date = "date";
    private static final String Comment_rate ="rate";

    private static final String[] TABLE_Comment_COLUMNS = {Comment_Comment_id, Comment_User_name, Comment_Description, Comment_Chapter_id, Comment_Date, Comment_rate};

    // Chapter table

    private static final String TABLE_Chapter = "Chapter";
    private static final String Chapter_COLUMN_Chapter_id = "id";
    private static final String Chapter_COLUMN_NAME = "name";
    private static final String Chapter_COLUMN_length = "length";
    private static final String Chapter_COLUMN_text = "text";
    private static final String Chapter_COLUMN_Book_id = "Book_id";
    private static final String Chapter_COLUMN_AuthorNote = "AuthorNote";
    private static final String Chapter_COLUMN_EndNote = "EndNote";
    private static final String Chapter_COLUMN_IMAGE = "pic";

    private static final String[] TABLE_Chapter_COLUMNS = {Chapter_COLUMN_Chapter_id, Chapter_COLUMN_NAME, Chapter_COLUMN_length,
            Chapter_COLUMN_text, Chapter_COLUMN_Book_id,Chapter_COLUMN_AuthorNote,Chapter_COLUMN_EndNote,Chapter_COLUMN_IMAGE};

    // Book table

    private static final String TABLE_Book = "Book";
    private static final String Book_COLUMN_Bookid = "id";
    private static final String Book_COLUMN_title = "name";
    private static final String Book_COLUMN_UserName = "UserName";
    private static final String Book_COLUMN_phase = "phase";
    private static final String Book_COLUMN_lengthBook = "length";
    private static final String Book_COLUMN_updaetOn = "updaetOn";
    private static final String Book_COLUMN_Language = "Language";//CategoryName
    private static final String Book_COLUMN_CategoryName = "CategoryName";
    private static final String Book_COLUMN_numChapters = "numChapters";
    private static final String Book_COLUMN_vote_comment = "vote_comment";
    private static final String Book_COLUMN_vote_heart = "vote_heart";
    private static final String Book_COLUMN_vote_list = "vote_list";
    private static final String Book_COLUMN_IMAGE = "pic";

    private static final String[] TABLE_Book_COLUMNS = {Book_COLUMN_Bookid,
            Book_COLUMN_title,Book_COLUMN_UserName, Book_COLUMN_phase, Book_COLUMN_lengthBook,
            Book_COLUMN_updaetOn, Book_COLUMN_Language, Book_COLUMN_CategoryName, Book_COLUMN_numChapters,
            Book_COLUMN_vote_comment, Book_COLUMN_vote_heart, Book_COLUMN_vote_list,
             Book_COLUMN_IMAGE};

    // Booklist table

    private static final String TABLE_Booklist= "Booklist";
    private static final String Booklist_COLUMN_idBookList = "id";
    private static final String Booklist_COLUMN_BookListName= "name";
    private static final String Booklist_COLUMN_CountBookList = "Count";
    private static final String Booklist_COLUMN_UserName = "UserName";

    private static final String[] TABLE_Booklist_COLUMNS = { Booklist_COLUMN_idBookList,
            Booklist_COLUMN_BookListName,Booklist_COLUMN_CountBookList,Booklist_COLUMN_UserName};


    //  many to many  Booklist  and books table

    private static final String TABLE_BookManyList= "BookManyList";
    private static final String BookManyList_COLUMN_idBook = "id";
    private static final String BookManyList_COLUMN_idBookList= "idList";

    private static final String[] TABLE_BookManyList_COLUMNS = { BookManyList_COLUMN_idBook,
            BookManyList_COLUMN_idBookList};


    //  Library table

    private static final String TABLE_Library= "Library";
    private static final String Library_COLUMN_idBook = "id";
    private static final String Library_COLUMN_userName= "userName";

    private static final String[] TABLE_Library_COLUMNS = { Library_COLUMN_userName,
            Library_COLUMN_idBook};


    //  Like table

    private static final String TABLE_Like= "Like1";
    private static final String Like_COLUMN_idBook = "id";
    private static final String Like_COLUMN_userName= "userName";

    private static final String[] TABLE_Like_COLUMNS = { Like_COLUMN_userName,
            Like_COLUMN_idBook};



    //  Category table

    private static final String TABLE_Category= "Category";
    private static final String Category_COLUMN_category_id = "id";
    private static final String Category_COLUMN_Category_name= "CategoryName";
    private static final String Category_COLUMN_conut= "count";
    private static final String Category_COLUMN_Type_name= "TypeName";

    private static final String[] TABLE_Category_COLUMNS = { Category_COLUMN_category_id,
            Category_COLUMN_Category_name,Category_COLUMN_conut,Category_COLUMN_Type_name};


    private SQLiteDatabase db = null;

    public MyInfoDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * create all the table that will be at sqlite
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // SQL statement to create   table
            if (!isTableExist(TABLE_Chapter, db)) {
                String CREATE_Chapter_TABLE = "create table if not exists " + TABLE_Chapter + " ( "
                        + Chapter_COLUMN_Chapter_id + " TEXT PRIMARY KEY , "
                        + Chapter_COLUMN_NAME + " TEXT, "
                        + Chapter_COLUMN_length + " TEXT, "
                        + Chapter_COLUMN_text + " TEXT, "
                        + Chapter_COLUMN_Book_id + " TEXT, "
                        + Chapter_COLUMN_AuthorNote + " TEXT, "
                        + Chapter_COLUMN_EndNote + " TEXT, "
                        + Chapter_COLUMN_IMAGE + " BLOB)";

                db.execSQL(CREATE_Chapter_TABLE);
            }
            if (!isTableExist(TABLE_Comment, db)) {
                 String CREATE_Comment_TABLE = "create table if not exists " + TABLE_Comment + " ( "
                        + Comment_Comment_id + " TEXT PRIMARY KEY, "
                        + Comment_User_name + " TEXT,"
                        + Comment_Description + " TEXT,"
                         + Comment_Chapter_id + " TEXT,"
                         + Comment_Date + " TEXT,"
                         + Comment_rate + " TEXT)";

                db.execSQL(CREATE_Comment_TABLE);
            }
            if (!isTableExist(TABLE_Book, db)) {
                String CREATE_Book_TABLE = "create table if not exists " + TABLE_Book + " ( "
                        + Book_COLUMN_Bookid + " TEXT PRIMARY KEY , "
                        + Book_COLUMN_title + " TEXT, "
                        + Book_COLUMN_UserName + " TEXT, "
                        + Book_COLUMN_phase+ " TEXT, "
                        + Book_COLUMN_lengthBook + " TEXT, "
                        + Book_COLUMN_updaetOn + " TEXT, "
                        + Book_COLUMN_Language + " TEXT, "
                        + Book_COLUMN_CategoryName + " TEXT, "
                        + Book_COLUMN_numChapters + " TEXT, "
                        + Book_COLUMN_vote_comment + " TEXT, "
                        + Book_COLUMN_vote_heart + " TEXT, "
                        + Book_COLUMN_vote_list + " TEXT, "
                        + Book_COLUMN_IMAGE + " BLOB)";
                db.execSQL(CREATE_Book_TABLE);
            }

            if (!isTableExist(TABLE_Booklist, db)) {
                String CREATE_Booklist_TABLE = "create table if not exists " + TABLE_Booklist + " ( "
                        + Booklist_COLUMN_idBookList + " TEXT PRIMARY KEY, "
                        + Booklist_COLUMN_BookListName + " TEXT,"
                        + Booklist_COLUMN_CountBookList + " TEXT,"
                        + Booklist_COLUMN_UserName + " TEXT)";

                db.execSQL(CREATE_Booklist_TABLE);
            }

            if (!isTableExist(TABLE_BookManyList, db)) {
                String CREATE_Booklist_TABLE = "create table if not exists " + TABLE_BookManyList + " ( "
                        + BookManyList_COLUMN_idBook + " TEXT ,"
                        + BookManyList_COLUMN_idBookList + " TEXT , "
                         + "  PRIMARY KEY( " + BookManyList_COLUMN_idBook + ", " + BookManyList_COLUMN_idBookList + "))";

                db.execSQL(CREATE_Booklist_TABLE);
     }

            if (!isTableExist(TABLE_Library, db)) {
                String CREATE_Library_TABLE = "create table if not exists " + TABLE_Library + " ( "
                        + Library_COLUMN_userName + " TEXT,"
                        + Library_COLUMN_idBook + " TEXT,"
                        +"  PRIMARY KEY(" + Library_COLUMN_userName +  ", " + Library_COLUMN_idBook +  ") )";

                db.execSQL(CREATE_Library_TABLE);
            }

            if (!isTableExist(TABLE_Category, db)) {
                String CREATE_Category_TABLE = "create table if not exists " + TABLE_Category + " ( "
                        + Category_COLUMN_category_id + " TEXT PRIMARY KEY, "
                        + Category_COLUMN_Category_name + " TEXT,"
                        + Category_COLUMN_conut + " TEXT,"
                        + Category_COLUMN_Type_name + " TEXT)";

                db.execSQL(CREATE_Category_TABLE);
            }

            if (!isTableExist(TABLE_Like, db)) {
                String CREATE_Like_TABLE = "create table if not exists " + TABLE_Like + " ( "
                        + Like_COLUMN_userName + " TEXT ,"
                        + Like_COLUMN_idBook + " TEXT,"
                        +"  PRIMARY KEY(" + Like_COLUMN_userName +  ", " + Like_COLUMN_idBook +  ") )";



                db.execSQL(CREATE_Like_TABLE);
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            // drop item table if already exists
            //db.execSQL("DROP TABLE IF EXISTS items");
            //db.execSQL("DROP TABLE IF EXISTS folders");
        } catch (Throwable t) {
            t.printStackTrace();
        }
        //onCreate(db);
    }


    /**
     * check if the table existent before he create a table
     * @param name
     * @param db
     * @return
     */
    private boolean isTableExist(String name, SQLiteDatabase db) {

        Cursor cursor = db.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+ name + "'", null);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            }
            cursor.close();
        }
        return false;
    }

    /**
     * create  a new Book row at the table in sqlite
     * @param b
     * @return
     */
    public boolean createBook(Book b) {
        boolean result=false;
        try {
             // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Book_COLUMN_Bookid,  b.getBook_id());
            values.put(Book_COLUMN_title,  b.getTitle());
            values.put(Book_COLUMN_UserName,  b.getUserName());
            values.put(Book_COLUMN_phase,  b.getPhase());
            values.put(Book_COLUMN_lengthBook,  b.getLength());
            values.put(Book_COLUMN_updaetOn,  b.getUpdaeOn());
            values.put(Book_COLUMN_Language,  b.getLanguage());
            values.put(Book_COLUMN_CategoryName,  b.getCategoryName());
            values.put(Book_COLUMN_numChapters,  b.getnumChapters());
            values.put(Book_COLUMN_vote_comment, b.getVote_comment());
            values.put(Book_COLUMN_vote_heart, b.getVote_heart());
            values.put(Book_COLUMN_vote_list, b.getVote_list());

        //images
            Bitmap image1 = b.getImage();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(Book_COLUMN_IMAGE, data);
                }
            }

            // insert Book
                long res =  db.insert(TABLE_Book, null, values);
                if(res != -1){
                    result = true;
                }

        } catch (Throwable t) {
        //    t.printStackTrace();
        }
        return result;


    }

    /**
     * create  a new List Book row at the table in sqlite
     * @param b
     * @return
     */
        public boolean createListBook(BookList b) {
            boolean result = false;

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Booklist_COLUMN_idBookList,  b.getIdBookList());
            values.put(Booklist_COLUMN_BookListName,  b.getBookListName());
            values.put(Booklist_COLUMN_CountBookList,  b.getCountBookList());
            values.put(Booklist_COLUMN_UserName,  b.getUserName());


            // insert ListBook
            long res= db.insert(TABLE_Booklist, null, values);
            if(res != -1){
                result = true;
            }

        } catch (Throwable t) {
        }

            return result;


    }

    /**
     * create  a new Category row at the table in sqlite
     * @param category
     * @return
     */
    public void createCategory(Category category) {

        try {
            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Category_COLUMN_category_id,  category.getCategory_id());
            values.put(Category_COLUMN_Category_name,  category.getCategory_name());
            values.put(Category_COLUMN_conut,  category.getConut());
            values.put(Category_COLUMN_Type_name, category.getType_name());


            // insert Category
            db.insert(TABLE_Category, null, values);

        } catch (Throwable t) {
            t.printStackTrace();
        }


    }


    /**
     * geting all the Categories that in the app
     * @return
     */
    public List<Category> getAllCategories() {
        List<Category> result = new ArrayList<Category>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_Category, TABLE_Category_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Category category = new Category();
                category.setCategory_id((cursor.getString(0)));
                category.setCategory_name(cursor.getString(1));
                category.setConut(cursor.getString(2));
                category.setType_name(cursor.getString(3));
                result.add(category);
                    cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    private byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }


    /**
     * geting all the books that in the app
     * @return
     */
    public List<Book> getAllBook() {
        List<Book> result = new ArrayList<Book>();
        Cursor cursor = null;
        try {
            cursor = db.query(TABLE_Book, TABLE_Book_COLUMNS, null, null,
                    null, null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Book book = cursorToBook(cursor);
                result.add(book);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    private Book cursorToBook(Cursor cursor) {
        Book result = new Book();
        try {
            result.setBook_id((cursor.getString(0)));
            result.setTitle(cursor.getString(1));
            result.setUserName(cursor.getString(2));
            result.setPhase(cursor.getString(3));
            result.setLength((cursor.getString(4)));
            result.setUpdaeOn(cursor.getString(5));
            result.setLanguage(cursor.getString(6));
            result.setCategoryName(cursor.getString(7));
            result.setnumChapters(cursor.getString(8));
            result.setVote_comment((cursor.getString(9)));
            result.setVote_heart((cursor.getString(10)));
            result.setVote_list((cursor.getString(11)));

            //images
            byte[] img1Byte = cursor.getBlob(12);
            if (img1Byte != null && img1Byte.length > 0) {
                Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
                if (image1 != null) {
                    result.setImage(image1);
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    private BookList cursorToListBook(Cursor cursor) {
        BookList result = new BookList();
        try {
            result.setIdBookList((cursor.getString(0)));
            result.setBookListName(cursor.getString(1));
            result.setCountBookList((cursor.getString(2)));
            result.setUserName((cursor.getString(3)));


        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }


    private Chapter cursorToChapter(Cursor cursor) {
            Chapter result = new Chapter();
        try {

            result.setChapter_id( (cursor.getString(0)));
            result.setChapter_name(cursor.getString(1));
            result.setLength( (cursor.getString(2)));
            result.setText(cursor.getString(3));
            result.setBook_id( (cursor.getString(4)));
            result.setAuthorNote(cursor.getString(5));
            result.setEndNote(cursor.getString(6));
            //images
            byte[] img1Byte = cursor.getBlob(7);
            if (img1Byte != null && img1Byte.length > 0) {
                Bitmap image1 = BitmapFactory.decodeByteArray(img1Byte, 0, img1Byte.length);
                if (image1 != null) {
                    result.setImage(image1);
                }
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }
    /**
     * update book
     * @param b
     * @return
     */
    public int updateBook(Book b) {
        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Book_COLUMN_Bookid,  b.getBook_id());
            values.put(Book_COLUMN_title,  b.getTitle());
            values.put(Book_COLUMN_UserName,  b.getUserName());
            values.put(Book_COLUMN_phase,  b.getPhase());
            values.put(Book_COLUMN_lengthBook,  b.getLength());
            values.put(Book_COLUMN_updaetOn,  b.getUpdaeOn());
            values.put(Book_COLUMN_Language,  b.getLanguage());
            values.put(Book_COLUMN_CategoryName,  b.getCategoryName());
            values.put(Book_COLUMN_numChapters,  b.getnumChapters());
            values.put(Book_COLUMN_vote_comment, b.getVote_comment());
            values.put(Book_COLUMN_vote_heart, b.getVote_heart());
            values.put(Book_COLUMN_vote_list, b.getVote_list());
            //images
            Bitmap image1 = b.getImage();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(Book_COLUMN_IMAGE, data);
                }
            }
            else{
                values.putNull(Book_COLUMN_IMAGE);
            }

            // update
            cnt = db.update(TABLE_Book, values, Book_COLUMN_Bookid+" = ?",
                    new String[] { String.valueOf(b.getBook_id()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }

    /**
     * delete book
     * @param B
     */
    public void deleteBook(Book B) {
        boolean succeded = false;
        try {

            // delete book
            int rowAffected = db.delete(TABLE_Book, Book_COLUMN_Bookid + " = ?",
                    new String[] { String.valueOf(B.getBook_id()) });
            if(rowAffected>0) {
                succeded = true;
            }

        } catch (Throwable t) {
            succeded = false;
            t.printStackTrace();
        } finally {
            if(succeded){
             ///   deleteFolderItems(B);  THIS ONE JUST
            }
        }

    }

    /**
     * open db
     */
    public void open() {
        try {
            db = getWritableDatabase();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /**
     * close db
     */
    public void close() {
        try {
            db.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }


    /**
     * geting all the books that in the app
     * @return
     */
    public List<BookList> getAllListBook(String userName) {
        List<BookList> result = new ArrayList<BookList>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Booklist,
                            TABLE_Booklist_COLUMNS, Booklist_COLUMN_UserName + " = ?",
                            new String[] { String.valueOf(userName) }, null, null,
                            null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                BookList book = cursorToListBook(cursor);
                result.add(book);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }



    /**
     * delete book list
     * @param B
     */
    public void deleteBooklist(BookList B) {
         try {
             List<Book> listB=  getAllTheBooksAtList(B.getIdBookList());;

               db.delete(TABLE_Booklist, Booklist_COLUMN_idBookList + " = ?",
                    new String[] { String.valueOf(B.getIdBookList()) });


            if(!listB.isEmpty()) {
                for (Book bookU : listB) {
                    int x= Integer.parseInt(bookU.getVote_list())-1;
                    bookU.setVote_list(String.valueOf(x));
                    updateBook(bookU);
                }
            }
        } catch (Throwable t) {
             t.printStackTrace();
        }


    }

    /**
     * create  a new  ManyToMany row at the table in sqlite
     * @param BookId
     * @param listId
     */
    public void createManyToManyTableBook(String BookId,String listId) {

        try {

                // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(BookManyList_COLUMN_idBook,  BookId);
            values.put(BookManyList_COLUMN_idBookList, listId);

            // insert item
            db.insert(TABLE_BookManyList, null, values);


        } catch (Throwable t) {
         //   t.printStackTrace();
        }


    }


    /**
     * create  a new  Chapter row at the table in sqlite
     * @param C
     */
    public boolean createChapter(Chapter C) {
        boolean result=false;
        try {
            // make values to be inserted
            ContentValues values = new ContentValues();



            values.put(Chapter_COLUMN_Chapter_id,  C.getChapter_id());
            values.put(Chapter_COLUMN_NAME,   C.getChapter_name());
            values.put(Chapter_COLUMN_length,   C.getLength());
            values.put(Chapter_COLUMN_text,   C.getText());
            values.put(Chapter_COLUMN_Book_id,   C.getBook_id());
            values.put(Chapter_COLUMN_AuthorNote,   C.getAuthorNote());
            values.put(Chapter_COLUMN_EndNote,   C.getEndNote());
             //images
            Bitmap image1 =  C.getImage();
            if (image1 != null) {
                byte[] data = getBitmapAsByteArray(image1);
                if (data != null && data.length > 0) {
                    values.put(Chapter_COLUMN_IMAGE, data);
                }
            }

            // insert Chapter
            long res =  db.insert(TABLE_Chapter, null, values);
             if(res != -1){
                result = true;
            }

        } catch (Throwable t) {
       //     t.printStackTrace();
        }
        return result;

    }
    /**
     *  get   Chapter By  Chapter-Id
     * @param Chapterid
     * @return
     */
    public Chapter getChapterByChapterId(String Chapterid) {
         Chapter  result=null ;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Chapter,
                            TABLE_Chapter_COLUMNS, Chapter_COLUMN_Chapter_id + " = ?",
                            new String[] { String.valueOf(Chapterid) }, null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                result = cursorToChapter(cursor);

                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    /**
     *  get All Chapter By  Book-Id
     * @param bookid
     * @return
     */
    public List<Chapter> getAllChapterByBookId(String bookid) {
        List<Chapter> result = new ArrayList<Chapter>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Chapter,
                            TABLE_Chapter_COLUMNS, Chapter_COLUMN_Book_id + " = ?",
                            new String[] { String.valueOf(bookid) }, null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Chapter c = cursorToChapter(cursor);
                result.add(c);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    /**
     * GET  BOOK OBJECT BY BOOK ID
     * @param bookid
     * @return
     */
    public Book getBooksObjByBookId(String bookid) {
        Book result = new Book();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Book,
                            TABLE_Book_COLUMNS, Book_COLUMN_Bookid + " = ?",
                            new String[] { String.valueOf(bookid) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            if(cursor!=null && cursor.getCount()>0){

                cursor.moveToFirst();
                result=cursorToBook(cursor);
                if(result.getBook_id()=="0"&& result.getCategoryName()==null)
                    result=null;
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    /**
     *  get All Books By  List-Id
     *  With list id i will get that all the book id
     *  return list of book obj
     * @param Listid
     * @return
     */
    public List<Book> getAllTheBooksAtList(String Listid) {
        List<Book> result = new ArrayList<Book>();
         Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_BookManyList,
                            TABLE_BookManyList_COLUMNS, BookManyList_COLUMN_idBookList + " = ?",
                            new String[] { String.valueOf(Listid) }, null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String x =  (cursor.getString(0));
                     Book  b=getBooksObjByBookId(x);
                    if(b!=null) {
                        result.add(b);
                    }

                    cursor.moveToNext();

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }



    /**
     * update Book List
     * @param b
     * @return
     */
    public int updateBookList(BookList b) {
        int cnt = 0;
        try {  // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Booklist_COLUMN_idBookList,  b.getIdBookList());
            values.put(Booklist_COLUMN_BookListName,  b.getBookListName());
            values.put(Booklist_COLUMN_CountBookList,  b.getCountBookList());
            values.put(Booklist_COLUMN_UserName,  b.getUserName());

            // update
            cnt = db.update(TABLE_Booklist, values, Booklist_COLUMN_idBookList+" = ?",
                    new String[] { String.valueOf(b.getIdBookList()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }


    /**
     * create  a new Comment row at the table in sqlite
     * @param c
     */

    public boolean createComment( Comment c) {
    boolean result=false;
        try {

             // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Comment_Comment_id,  c.getComment_id());
            values.put(Comment_User_name,  c.getUserName());
            values.put(Comment_Description,  c.getDescription());
            values.put(Comment_Chapter_id,c.getChapter_id() );
            values.put(Comment_Date, String.valueOf(c.getDate()));
            values.put(Comment_rate,  c.getrate());

            // insert Comment
           long res= db.insert(TABLE_Comment, null, values);
            if(res != -1)
                result = true;

        } catch (Throwable t) {

         }
        return  result;
    }

    /**
     * update comment
     * @param c
     * @return
     */
    public int updateComment( Comment c) {
        int cnt = 0;
        try {  // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Comment_Comment_id,  c.getComment_id());
            values.put(Comment_User_name,  c.getUserName());
            values.put(Comment_Description,  c.getDescription());
            values.put(Comment_Chapter_id,c.getChapter_id() );
            values.put(Comment_Date, String.valueOf(c.getDate()));
            values.put(Comment_rate,  c.getrate());

            // update
            cnt = db.update(TABLE_Comment, values, Comment_Comment_id+" = ?",
                    new String[] { String.valueOf(c.getComment_id()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }

    /**
     * get Comment By Chapter Id
     * @param ChapterId
     * @return
     */
    public List<Comment> getAllCommentByChapterId(String ChapterId) {
      //  Comment result = new Comment();

             List<Comment> result = new ArrayList<Comment>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Comment,
                            TABLE_Comment_COLUMNS, Comment_Chapter_id+ " = ?",
                            new String[] { String.valueOf(ChapterId) }, null, null,
                            null, null);

            // if results !=null, parse the first one
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                     result.add(cursorToComment(cursor));
                cursor.moveToNext();
             }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    private Comment cursorToComment(Cursor cursor) {
        Comment result = new Comment();
        try {

            result.setComment_id( (cursor.getString(0)));
            result.setUserName(cursor.getString(1));
            result.setDescription(cursor.getString(2));
            result.setChapter_id( (cursor.getString(3)));
            result.setDate(  cursor.getString(4) );
            result.setrate( (cursor.getString(5)));


        } catch (Throwable t) {
            t.printStackTrace();
        }

        return result;
    }

    /**
     * delete comment
     * @param c
     */
    public void deleteComment(Comment c) {
        boolean succeeded = false;
        try {

            // delete Comment
            int rowAffected = db.delete(TABLE_Comment, Comment_Comment_id + " = ?",
                    new String[] { String.valueOf(c.getComment_id()) });
            if(rowAffected>0) {
                succeeded = true;
            }

        } catch (Throwable t) {
            succeeded = false;
            t.printStackTrace();
        } finally {
            if(succeeded){
                ///   deleteFolderItems(B);  THIS ONE JUST
            }
        }

    }

    /**insert Library
     *
     * @param BookId
     * @param userName
     */
    public boolean insertLibrary(String BookId,String userName) {
        boolean result=false;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Library_COLUMN_idBook ,  BookId);
            values.put(Library_COLUMN_userName, userName);

            // insert Library
            long res = db.insert(TABLE_Library, null, values);

             if(res != -1){
                result = true;
            }

        } catch (Throwable t) {
        //    t.printStackTrace();
        }

        return  result;
    }


    /**
     * delete library by user name and specific book id
     * @param c
     */
    public boolean deleteLibrary(Library c) {
        boolean succeded = false;
        try {

            // delete Library
            int rowAffected = db.delete(TABLE_Library, Library_COLUMN_idBook + " = ?" +"AND "+ Library_COLUMN_userName + " = ?",
                    new String[] { String.valueOf(c.getBookId()),String.valueOf(c.getUserName()) });
            if(rowAffected>0) {
                succeded = true;
            }

        } catch (Throwable t) {
           //  t.printStackTrace();
        }
        return succeded;
    }




    /**insert Like
     *
     * @param BookId
     * @param userName
     */
    public boolean  insertLike(String BookId,String userName) {
        boolean result=false;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Like_COLUMN_idBook ,  BookId);
            values.put(Like_COLUMN_userName, userName);

            // insert Like
            long res = db.insert(TABLE_Like, null, values);
             if(res != -1) {
                 result = true;
             }
        } catch (Throwable t) {
          //  t.printStackTrace();
        }

        return result;
    }


    /**
     * delete Like by user name and specific book id
     * @param c
     */
    public boolean deleteLike(Like c) {
        boolean succeded = false;
        try {

            // delete Like
            int rowAffected = db.delete(TABLE_Like, Like_COLUMN_idBook + " = ?" +"AND "+ Like_COLUMN_userName + " = ?",
                    new String[] { String.valueOf(c.getBookId()),String.valueOf(c.getUserName()) });
            if(rowAffected>0) {
                succeded = true;
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        return succeded;
    }




    /**
     *  get  Like By  userName and bookId
     * @param userName
     * @return
     */
    public  Boolean  getLikes(String userName,String bookId ) {
         Like  result = null;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Like,
                            TABLE_Like_COLUMNS, Like_COLUMN_idBook + " = ?" +"AND "+ Like_COLUMN_userName + " = ?",
                            new String[] { String.valueOf(bookId),String.valueOf(userName) } , null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String user =  (cursor.getString(0));
                String bookid =  (cursor.getString(1));
                result = new Like(user,bookid);

                cursor.moveToNext();

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
             if(cursor!=null){
                cursor.close();
            }
        }

        return result==null?false:true;
    }


    /**
     *  get All Library By  userName
     * @param userName
     * @return
     */
    public List<Library> getAllLibrary(String userName  ) {
        List<Library> result = new ArrayList<Library>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Library,
                            TABLE_Library_COLUMNS,   Library_COLUMN_userName + " = ?",
                            new String[] {  String.valueOf(userName) } , null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String user =  (cursor.getString(0));
                String bookid =  (cursor.getString(1));
                Library L = new Library(user,bookid);
                if(L!=null) {
                    result.add(L);
                }

                cursor.moveToNext();

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    /**
     *  get   Library By  userName and bookId
     * @param userName
     * @return
     */
    public  Library  getLibrary(String userName,String bookId ) {
        Library  result = null;
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Library,
                            TABLE_Library_COLUMNS, Library_COLUMN_idBook + " = ?" +"AND "+ Library_COLUMN_userName + " = ?",
                            new String[] { String.valueOf(bookId),String.valueOf(userName) } , null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String user =  (cursor.getString(0));
                String bookid =  (cursor.getString(1));
                result = new Library(user,bookid);

                cursor.moveToNext();

            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    /**
     * GET  BOOK OBJECT BY userName
     * @param userName
     * @return
     */
    public List<Book> getBooksObjByUserName(String userName) {
        List<Book> result =  new ArrayList<Book>();

            Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Book,
                            TABLE_Book_COLUMNS, Book_COLUMN_UserName + " = ?",
                            new String[] { String.valueOf(userName) }, null, null,
                            null, null);

            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Book book = cursorToBook(cursor);
                result.add(book);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }


    /**
     * get All Category At Type
     * @return
     */
        public List<Category> getAllCategoryAtType(String Type_name) {
        List<Category> result = new ArrayList<Category>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Category,
                            TABLE_Category_COLUMNS, Category_COLUMN_Type_name + " = ?",
                            new String[] { String.valueOf(Type_name) }, null, null,
                            null, null);


            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                Category category = new Category();
                category.setCategory_id((cursor.getString(0)));
                category.setCategory_name(cursor.getString(1));
                category.setConut(cursor.getString(2));
                category.setType_name(cursor.getString(3));
                result.add(category);
                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return result;
    }

    /**
     * update Category
     * @param category
     * @return
     */
    public int updateCategory(Category category) {
        int cnt = 0;
        try {

            // make values to be inserted
            ContentValues values = new ContentValues();
            values.put(Category_COLUMN_category_id,  category.getCategory_id());
            values.put(Category_COLUMN_Category_name,  category.getCategory_name());
            values.put(Category_COLUMN_conut,  category.getConut());
            values.put(Category_COLUMN_Type_name, category.getType_name());            // update

            cnt = db.update(TABLE_Category, values, Category_COLUMN_Category_name+" = ?",
                    new String[] { String.valueOf(category.getCategory_name()) });
        } catch (Throwable t) {
            t.printStackTrace();
        }

        return cnt;
    }


    /**
     * get All Category obj
     * @return
     */
    public  Category  getCategoryobj(String Category_name) {
        Category category = new Category();

        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Category,
                            TABLE_Category_COLUMNS, Category_COLUMN_Category_name+ " = ?",
                            new String[] { String.valueOf(Category_name) }, null, null,
                            null, null);


            cursor.moveToFirst();

            while (!cursor.isAfterLast()) {

                category.setCategory_id((cursor.getString(0)));
                category.setCategory_name(cursor.getString(1));
                category.setConut(cursor.getString(2));
                category.setType_name(cursor.getString(3));

                cursor.moveToNext();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }
        finally {
            // make sure to close the cursor
            if(cursor!=null){
                cursor.close();
            }
        }

        return category;
    }



    /**
     *  get All Books By  CategoryName
     * @param CategoryName
     * @return
     */
    public List<Book> getAllTheBooksCategoryName(String CategoryName) {
        List<Book> result = new ArrayList<Book>();
        Cursor cursor = null;
        try {
            cursor = db
                    .query(TABLE_Book,
                            TABLE_Book_COLUMNS, Book_COLUMN_CategoryName + " = ?",
                            new String[]{String.valueOf(CategoryName)}, null, null,
                            null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Book book = cursorToBook(cursor);
                result.add(book);
                cursor.moveToNext();


            }

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            // make sure to close the cursor
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }
}