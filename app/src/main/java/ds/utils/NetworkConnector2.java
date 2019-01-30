package ds.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import core.Book;
import core.BookList;
import core.Chapter;
import core.Comment;
import core.ListBookManyToMany;


public class NetworkConnector2 {

    private static NetworkConnector2 mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;


    private final String HOST_URL = "http://192.168.1.104:8080/server/";
    private  final String BASE_URL = HOST_URL + "projres";


    public static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    public static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";


    public static final String REQ = "req";

    // /NEW

    ///LIST BOOK
    public static final String GET_ALL_LISTSBOOK_JSON_REQ = "0";
    public static final String INSERT_LISTSBOOk_REQ = "1";
    private static final String DELETE_LISTSBOOK_REQ = "2";

    private static final String USER_NAME = "U_name";
    private static final String IDBOOK_LIST = "ID_BOOKLIST";
    private static final String COUNT_LIST= "C_LIST";
    private static final String LIST_NAME = "L_name";

   // public static final String GET_ALL_BOOK_JSON_REQ = "3";
    //BOOK

    public static final String INSERT_BOOK_REQ = "3";
    private static final String DELETE_BOOK_REQ = "4";
    public static final String GET_BOOK_IMAGE_REQ = "5";
    public static final String INSERT_BOOK_WITH_IMG_REQ = "6";///askkk
    private static final String GET_Book_JSON_REQ = "7";

    private static final String BOOK_ID = "b_id";
    private static final String BOOK_TITLE = "b_title";
    private static final String BOOK_numChapters = "b_numC";
    private static final String BOOK_PHASE = "b_phase";
    private static final String BOOK_UPDATEON = "b_updaeOn";
    private static final String	BOOK_LANGUAGE = "b_Language";
    private static final String BOOK_CATEGORYNAME = "b_CategoryName";
    private static final String	BOOK_VOTECOMMENT = "b_vote_comment";
    private static final String	BOOK_VOTEHEART = "b_vote_heart";
    private static final String BOOK_VOTELIST  = "b_vote_list ";
    private static final String BOOK_LENGTH  = "b_length ";

    //// list book many to many

    private static final String INSERT_ListBookManyToMany_REQ = "8";
    private static final String DELETE_ListBookManyToMany_REQ = "9";
    private static final String DELETE_ListBookManyToMany_SpecificRow_REQ = "10";
    private static final String GET_ListBookManyToMany = "11";

    ////chapter

    private static final String INSERT_CHAPTER_REQ = "12";
    private static final String DELETE_CHAPTER_BY_BOOKID_REQ = "13";
    private static final String DELETE_CHAPTER_REQ = "14";
    private static final String GET_CHAPTER_IMAGE_REQ = "15";
    private static final String GET_CHAPTER_JSON_REQ = "16";

    private static final String CHAPTER_id = "C_id";
    private static final String CHAPTER_name = "C_name";
    private static final String CHAPTER_LENGTH  = "C_length ";
    private static final String	CHAPTER_text = "C_text";
    private static final String CHAPTER_AuthorNote= "C_AuthorNote";
    private static final String CHAPTER_EndNote = "C_EndNote";
    /// COMMENT

    private static final String INSERT_COMMENT_REQ = "17";
    private static final String DELETE_COMMENT_REQ = "18";
    private static final String DELETE_COMMENT_SpecificRow_REQ = "19";
    private static final String GET_COMMENT = "20";
    //  chapterId, userName,

    private static final String COMMENT_id = "CO_id";
    private static final String COMMENT_description  = "CO_length ";
    private static final String	COMMENT_date = "CO_date";
    private static final String COMMENT_rate= "CO_rate";

    private NetworkConnector2() {

    }

    public static synchronized NetworkConnector2 getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkConnector2();
        }
        return mInstance;
    }

    public void initialize(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private void addToRequestQueue(String query, final NetworkResListener listener) {

        String reqUrl = BASE_URL + "?" + query;
        notifyPreUpdateListeners(listener);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, reqUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        notifyPostUpdateListeners(response, ResStatus.SUCCESS, listener);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        JSONObject err = null;
                        try {
                            err = new JSONObject(RESOURCE_FAIL_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListeners(err, ResStatus.FAIL, listener);
                        }

                    }
                });

        getRequestQueue().add(jsObjRequest);
    }

    private void addImageRequestToQueue(String query, final NetworkResListener listener){

        String reqUrl = BASE_URL + "?" + query;

        notifyPreUpdateListeners(listener);

        getImageLoader().get(reqUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bm = response.getBitmap();
                notifyPostBitmapUpdateListeners(bm, ResStatus.SUCCESS, listener);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                notifyPostBitmapUpdateListeners(null, ResStatus.FAIL, listener);
            }
        });
    }

    private ImageLoader getImageLoader() {
        return mImageLoader;
    }

//////////////////////////????

    private void uploadBookImage(final Book data, final NetworkResListener listener) {

        String reqUrl = HOST_URL + "web_item_manage?";
        notifyPreUpdateListeners(listener);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, reqUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            notifyPostUpdateListeners(obj, ResStatus.SUCCESS, listener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(RESOURCE_FAIL_TAG );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListeners(obj, ResStatus.FAIL, listener);
                        }

                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(BOOK_ID , data.getBook_id());
                params.put(BOOK_TITLE , data.getTitle());
                params.put(BOOK_numChapters, data.getnumChapters());
                params.put(BOOK_PHASE , data.getPhase());
                params.put(BOOK_UPDATEON , data.getUpdaeOn());
                params.put(BOOK_LANGUAGE , data.getLanguage());
                params.put(BOOK_CATEGORYNAME , data.getCategoryName());
                params.put(BOOK_VOTECOMMENT , data.getVote_comment());
                params.put(BOOK_VOTEHEART , data.getVote_heart());
                params.put(BOOK_VOTELIST , data.getVote_list());
                params.put(BOOK_LENGTH , data.getLength());
                params.put(USER_NAME , data.getUserName());
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                byte[] pic = Book.getBitmapAsByteArray(data.getImage());
                params.put("fileField", new DataPart(imagename + ".png", pic));
                return params;
            }
        };

        //adding the request to volley
        getRequestQueue().add(volleyMultipartRequest);
    }
///BOOK
        public void sendRequestToServer(String requestCode, Book data, NetworkResListener listener){

        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_BOOK_WITH_IMG_REQ:{

                uploadBookImage(data, listener);

                break;
            }
            case INSERT_BOOK_REQ:{

                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());
                builder.appendQueryParameter(BOOK_TITLE , data.getTitle());
                builder.appendQueryParameter(BOOK_numChapters, data.getnumChapters());
                builder.appendQueryParameter(BOOK_PHASE , data.getPhase());
                builder.appendQueryParameter(BOOK_UPDATEON , data.getUpdaeOn());
                builder.appendQueryParameter(BOOK_LANGUAGE , data.getLanguage());
                builder.appendQueryParameter(BOOK_CATEGORYNAME , data.getCategoryName());
                builder.appendQueryParameter(BOOK_VOTECOMMENT , data.getVote_comment());
                builder.appendQueryParameter(BOOK_VOTEHEART , data.getVote_heart());
                builder.appendQueryParameter(BOOK_VOTELIST , data.getVote_list());
                builder.appendQueryParameter(BOOK_LENGTH , data.getLength());
                builder.appendQueryParameter(USER_NAME , data.getUserName());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case   DELETE_BOOK_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }
            case GET_BOOK_IMAGE_REQ: {
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());

                String query = builder.build().getEncodedQuery();
                addImageRequestToQueue(query, listener);

                break;
            }


            case GET_Book_JSON_REQ: {

                builder.appendQueryParameter(REQ , requestCode);
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }

        }



    }



    /**
     * ListBookManyToMany
     * @param requestCode
     * @param data
     * @param listener
     */
    public void sendRequestToServer(String requestCode, ListBookManyToMany data, NetworkResListener listener){

        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_ListBookManyToMany_REQ:{

                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getId());
                builder.appendQueryParameter(IDBOOK_LIST , data.getIdList());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case   DELETE_ListBookManyToMany_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(IDBOOK_LIST , data.getIdList());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case   DELETE_ListBookManyToMany_SpecificRow_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getId());
                builder.appendQueryParameter(IDBOOK_LIST , data.getIdList());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case GET_ListBookManyToMany: {

                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(IDBOOK_LIST , data.getIdList());
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }

        }



    }
    public void update(NetworkResListener listener,String userName){

        Uri.Builder builder = new Uri.Builder();///////////ask marueann
        builder.appendQueryParameter(REQ , GET_ALL_LISTSBOOK_JSON_REQ);
        builder.appendQueryParameter(USER_NAME , userName);

        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);
    }
////////////////////////////// need to fix book list obj because i added a new attrubte
    /**
     * book list  send Request
     * @param requestCode
     * @param data
     * @param listener
     */
    public void sendRequestToServer(String requestCode, BookList data, NetworkResListener listener) {


        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_LISTSBOOk_REQ:{


                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(IDBOOK_LIST ,data.getIdBookList());
                builder.appendQueryParameter(COUNT_LIST , data.getCountBookList());
                builder.appendQueryParameter(LIST_NAME ,data.getBookListName());
                builder.appendQueryParameter(USER_NAME , data.getUserName());
                break;
            }
            case DELETE_LISTSBOOK_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(IDBOOK_LIST , data.getIdBookList());
                break;
            }
        }
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);

    }

    private void clean() {

    }


    private  void notifyPostBitmapUpdateListeners(final Bitmap res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPostUpdateListeners(final JSONObject res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPreUpdateListeners(final NetworkResListener listener) {


        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                        listener.onPreUpdate();
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }


    /**
     *Chapter
     * @param requestCode
     * @param data
     * @param listener
     */
        public void sendRequestToServer(String requestCode, Chapter data, NetworkResListener listener){

        if(data==null){
            return;
        }

            Uri.Builder builder = new Uri.Builder();

            switch (requestCode){
//                case INSERT_BOOK_WITH_IMG_REQ:{
//
//                    uploadBookImage(data, listener);
//
//                    break;
//                }

            case INSERT_CHAPTER_REQ:{

                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());
                builder.appendQueryParameter(CHAPTER_id , data.getChapter_id());
                builder.appendQueryParameter(CHAPTER_name , data.getChapter_name());
                builder.appendQueryParameter(CHAPTER_LENGTH , data.getLength());
                builder.appendQueryParameter(CHAPTER_text , data.getText());
                builder.appendQueryParameter(CHAPTER_AuthorNote , data.getAuthorNote());
                builder.appendQueryParameter(CHAPTER_EndNote , data.getEndNote());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }


            case   DELETE_CHAPTER_BY_BOOKID_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

             case   DELETE_CHAPTER_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(CHAPTER_id , data.getChapter_id());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case GET_CHAPTER_IMAGE_REQ: {
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(CHAPTER_id , data.getChapter_id());

                String query = builder.build().getEncodedQuery();
                addImageRequestToQueue(query, listener);

                break;
            }


            case GET_CHAPTER_JSON_REQ: {

                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(BOOK_ID , data.getBook_id());

                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }

        }



    }





    /**
     * Comment
     * @param requestCode
     * @param data
     * @param listener
     */
    public void sendRequestToServer(String requestCode, Comment data, NetworkResListener listener) {

        if (data == null) {
            return;
        }

        Uri.Builder builder = new Uri.Builder();


        switch (requestCode) {
            case INSERT_COMMENT_REQ: {

                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(COMMENT_id, data.getComment_id());
                builder.appendQueryParameter(USER_NAME, data.getUserName());
                builder.appendQueryParameter(CHAPTER_id, data.getChapter_id());
                builder.appendQueryParameter(COMMENT_description, data.getDescription());
                builder.appendQueryParameter(COMMENT_date, data.getDate());
                builder.appendQueryParameter(COMMENT_rate, data.getrate());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case DELETE_COMMENT_REQ: {
                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(CHAPTER_id, data.getChapter_id());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case DELETE_COMMENT_SpecificRow_REQ: {
                builder.appendQueryParameter(REQ, requestCode);
                builder.appendQueryParameter(COMMENT_id, data.getComment_id());
                builder.appendQueryParameter(USER_NAME, data.getUserName());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }

            case GET_COMMENT: {

                builder.appendQueryParameter(REQ, requestCode);
                String query = builder.build().getEncodedQuery();

                addToRequestQueue(query, listener);
                break;
            }

        }
    }
}
