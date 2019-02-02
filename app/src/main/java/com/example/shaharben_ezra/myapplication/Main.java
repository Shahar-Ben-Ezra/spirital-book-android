package com.example.shaharben_ezra.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

import core.Category;
import core.MyInfoManager;
import ds.utils.NetworkConnector;
import ds.utils.NetworkResListener;
import ds.utils.ResStatus;

public class Main extends AppCompatActivity implements NetworkResListener {

    public  static  String userName="";
    public static ProgressDialog progressDialog = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users);
        setTitle("MAIN");

    }

    /**
     * a func that to gather all event clicked
     * @param v
     */
    public void PressBtbActivity(View v) {
        Intent intent= new Intent(this,MainActivity.class);;

        if (v.getId() == R.id.user_1) {
              userName=getString(R.string.user1);

        } else if (v.getId() == R.id.user_2) {
             userName=getString(R.string.user2);

        } else if (v.getId() == R.id.user_3) {
             userName=getString(R.string.user3);

        } else if (v.getId() == R.id.user_4) {
             userName=getString(R.string.user4);
        }
        MyInfoManager.getInstance().openDataBase(this);
        CreateCategory();

        NetworkConnector.getInstance().initialize(this);
        NetworkConnector.getInstance().update(this,Main.userName);
        startActivity(intent);

    }


    @Override
    public  void onPreUpdate() {
        if(progressDialog==null) {

//            if (!progressDialog.isShowing()) {/// if its showing i dont need to create another progressDialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle(getString(R.string.updating));
            progressDialog.setMessage(getString(R.string.please));
            progressDialog.setCancelable(false);
            progressDialog.show();
//            }
        }
    }


    @Override
    public void onPostUpdate(byte[] res, ResStatus status) {

    }

    @Override
    public synchronized void onPostUpdate(JSONObject res, ResStatus status, String requestNum) {

        if(status == ResStatus.SUCCESS){
    //        Toast.makeText(this, "download ok...", Toast.LENGTH_LONG).show();
            if(requestNum=="0")//update all the  list book
                MyInfoManager.getInstance().updateResources(res);

            else if(requestNum=="20")// update all the comment
                MyInfoManager.getInstance().updateResourcesComment(res);

            else if(requestNum=="7")// update all the comment
                MyInfoManager.getInstance().updateResourcesBook(res);

            else if(requestNum=="16")// update all the chapter
                MyInfoManager.getInstance().updateResourcesChapter(res);

            else if(requestNum=="11")// update all the List Book Many To Many
                MyInfoManager.getInstance().updateResourcesListBookManyToMany(res);

            else if(requestNum=="26")// update all the like the specific user choose
                MyInfoManager.getInstance().updateResourcesLike(res);

            else if(requestNum=="21") {// update all the library the specific user choose
                MyInfoManager.getInstance().updateResourcesLibrary(res);

            }

        }
        else{
            Toast.makeText(this,R.string.download_failed, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPostUpdate(Bitmap res, ResStatus status) {

    }

    /**
     * creating category list , this list the user cannot change
     * its will happend only one time when the user enter first time enter to the app
     */
    protected void CreateCategory() {


        if (MyInfoManager.getInstance().getAllCategories().isEmpty()) {

            ArrayList<Category> c = new ArrayList<>();

            c.add(new Category("1", "Naruto", "Animes Mangas", "0"));
            c.add(new Category("2", "Pokemon", "Animes Mangas", "0"));
            c.add(new Category("3", "Fairly Tail", "Animes Mangas", "0"));
            c.add(new Category("4", "Vocaloid", "Animes Mangas", "0"));
            c.add(new Category("5", "Dragon Ball", "Animes Mangas", "0"));
            c.add(new Category("6", "EXO", "Bands Musicians", "0"));
            c.add(new Category("7", "Justin Bieber", "Bands Musicians", "0"));
            c.add(new Category("8", "One Direction", "Bands Musicians", "0"));
            c.add(new Category("9", "Got7", "Bands Musicians", "0"));
            c.add(new Category("10", "Twice", "Bands Musicians", "0"));
            c.add(new Category("11", "6Teen", "Cartons", "0"));
            c.add(new Category("12", "Batman", "Cartons", "0"));
            c.add(new Category("13", "Iron Man", "Cartons", "0"));
            c.add(new Category("14", "X-Men", "Cartons", "0"));
            c.add(new Category("15", "Witch", "Cartons", "0"));
            c.add(new Category("16", "Ariana Grande", "Celebrities", "0"));
            c.add(new Category("17", "Elvis Presely", "Celebrities", "0"));
            c.add(new Category("18", "50 cent", "Celebrities", "0"));
            c.add(new Category("19", "Lionel Messi", "Celebrities", "0"));
            c.add(new Category("20", "Big Sean", "Celebrities", "0"));
            c.add(new Category("21", "WWE", "Games", "0"));
            c.add(new Category("22", "Robin Hood", "Games", "0"));
            c.add(new Category("23", "Fallout", "Games", "0"));
            c.add(new Category("24", "Splatoon", "Games", "0"));
            c.add(new Category("25", "Bloodborne", "Games", "0"));
            c.add(new Category("26", "Harry Potter", "Movies", "0"));
            c.add(new Category("27", "Star Wars", "Movies", "0"));
            c.add(new Category("28", "Captain America", "Movies", "0"));
            c.add(new Category("29", "Dead pool", "Movies", "0"));
            c.add(new Category("30", "007", "Movies", "0"));

            for (Category Cate : c) {
                MyInfoManager.getInstance().AddCategory(Cate);
            }
        }

    }
}
