package com.example.shaharben_ezra.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import core.Book;
import core.Chapter;
import core.Like;
import core.MyInfoManager;

public class ChapterView extends AppCompatActivity {
    private BottomNavigationView mMainNav;
     int count=1;
    TextView Autors_Notes_view_comment,Endnotes_comment,Story_Content;
    ImageView iv;
    Book b;
    Like like;
    List<Chapter> result = new ArrayList<>();
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_view);
        mMainNav = (BottomNavigationView)findViewById(R.id.NavigationView);
        BottomNavigationViewHelper.disableShiftMode(mMainNav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChapter_VIEW);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Chapter"+count);
        String s = getIntent().getStringExtra("int");
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      final  String book_id = getIntent().getStringExtra("book_id");


      MenuItem menuItem = mMainNav.getMenu().findItem(R.id.nav_love);
        b = MyInfoManager.getInstance().getBooksObjByBookId(book_id);

        like=new Like(Main.userName,book_id);
      if(MyInfoManager.getInstance().getLike(like)) {
          menuItem.setIcon(R.drawable.ic_favorite_white_24dp);

      }
      else {
          menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);

      }
      menuItem.setTitle(""+b.getVote_heart());

      MenuItem menuItem1 = mMainNav.getMenu().findItem(R.id.message2);
      menuItem1.setTitle(""+b.getVote_comment());
      result=MyInfoManager.getInstance().getAllChapterByBookId(book_id);
      if(!result.isEmpty())
        setText(result.get(count-1));

      final Button previousChapter = findViewById(R.id.previousChapter);
      if ( count==1) {
          previousChapter.setVisibility(View.GONE);
      }
      else{
          previousChapter.setVisibility(View.VISIBLE);

      }
      previousChapter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              count--;
              if ( count==1) {
                  previousChapter.setVisibility(View.GONE);
              }
              setText(result.get(count));


          }
      });

      final Button nextChapter = findViewById(R.id.nextChapter);

      nextChapter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              count++;
              previousChapter.setVisibility(View.VISIBLE);
              setText(result.get(count));

          }
      });



            mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch (menuItem.getItemId()) {
                        case R.id.share: {
                            Intent shareIntent;
                            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)+"share.png";
                            OutputStream out = null;
                            File file=new File(path);
                            try {
                                out = new FileOutputStream(file);
                                b.getImage().compress(Bitmap.CompressFormat.PNG, 100, out);
                                out.flush();
                                out.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            path=file.getPath();
                            Uri bmpUri = Uri.parse("content://"+path);
                            shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                            shareIntent.putExtra(Intent.EXTRA_TEXT,"Hey you must read this book "+ b.getTitle());
                            shareIntent.setType("image/png");
                             startActivity(Intent.createChooser(shareIntent,"Send..."));
                            return true;


                        }
                        case R.id.nav_love: {
                              b = MyInfoManager.getInstance().getBooksObjByBookId(book_id);
                             if(MyInfoManager.getInstance().getLike(like)) {//// if there are a like from this user  and this book
                                menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);
                                MyInfoManager.getInstance().deleteLike(like);// its mean that the user didnt like the book
                               String x = b.getVote_heart();
                               b.setVote_heart(String.valueOf(Integer.parseInt(x) - 1));
                                menuItem.setTitle(""+b.getVote_heart());
                                MyInfoManager.getInstance().updateBook(b);

                            }
                            else {
                                menuItem.setIcon(R.drawable.ic_favorite_white_24dp);
                                MyInfoManager.getInstance().createLike(like);// its mean that there now a like for this book
                                String x = b.getVote_heart();
                                b.setVote_heart(String.valueOf(Integer.parseInt(x) + 1));
                                menuItem.setTitle(""+b.getVote_heart());
                                MyInfoManager.getInstance().updateBook(b);                            }
                            return true;

                        }
                        case R.id.message2: {
                            try {


                           Intent intent2 = new Intent(getBaseContext(),CommentList.class);
                            intent2.putExtra("chapter",result.get(count-1).getChapter_id()) ;
                            intent2.putExtra("BookTitle", b.getTitle()) ;
                            intent2.putExtra("chapterNumber",count) ;
                            intent2.putExtra("BookID", b.getBook_id()) ;


                            startActivity(intent2);
                            }
                            catch (Exception e){
                                Toast.makeText(getBaseContext(),R.string.there_are_no  , Toast.LENGTH_SHORT).show();

                            }

                            return true;

                        }

                    }
              return true;

                }
                });

        }


  public void setText(Chapter c ){

      Autors_Notes_view_comment= findViewById(R.id.Autors_Notes_view_comment);
      Endnotes_comment= findViewById(R.id.Endnotes_comment);
      Story_Content = findViewById(R.id.Story_Content);
      iv =  findViewById(R.id.imageView1);

      Autors_Notes_view_comment.setText(c.getAuthorNote());
      iv.setImageBitmap(c.getImage());
      Endnotes_comment.setText(c.getEndNote());
      Story_Content.setText(c.getText());


  }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
