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

/**
 * author Shahar Ben-Ezra
 * Chapter View activity will show the info for each chapter
 */
public class ChapterView extends AppCompatActivity {

    private BottomNavigationView mMainNav;// BottomNavigationView include button  for like,share,comment
    private int count=0;
    private TextView Autors_Notes_view_comment,Endnotes_comment,Story_Content,Cover_Image_Chapter;
    private ImageView iv;
    private Book b;
    private Like like;
    private List<Chapter> result = new ArrayList<>();// chapter list
    private String BookTitle;
  protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chapter_view);
        mMainNav = (BottomNavigationView)findViewById(R.id.NavigationView);
        BottomNavigationViewHelper.disableShiftMode(mMainNav);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChapter_VIEW);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Chapter"+String.valueOf(count+1));/// Subtitle chapter
        BookTitle = getIntent().getStringExtra("int");
        getSupportActionBar().setTitle(BookTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final  String book_id = getIntent().getStringExtra("book_id");


        MenuItem loveItem = mMainNav.getMenu().findItem(R.id.nav_love);
        b = MyInfoManager.getInstance().getBooksObjByBookId(book_id);
        like=new Like(Main.userName,book_id);
        // will show a entire heart if the user does allready like for this book
        if(MyInfoManager.getInstance().getLike(like)) {
            loveItem.setIcon(R.drawable.ic_favorite_white_24dp);

        }
         else {
            loveItem.setIcon(R.drawable.ic_favorite_border_black_24dp);

        }

      loveItem.setTitle(""+b.getVote_heart());// set number of like for this book

      MenuItem messageItem  = mMainNav.getMenu().findItem(R.id.message2);
      messageItem.setTitle(""+b.getVote_comment());// set number of message for this chapter

      result=MyInfoManager.getInstance().getAllChapterByBookId(book_id);
      // make chapter information visible
      if(!result.isEmpty())
        setText(result.get(count));
      else{
          Toast.makeText(getBaseContext(),getString(R.string.there_are), Toast.LENGTH_LONG).show();

      }

      final Button previousChapter = findViewById(R.id.previousChapter);
      // make this button available if there another chapter
      if ( count<1) {
          previousChapter.setVisibility(View.GONE);
      }
      else{
          previousChapter.setVisibility(View.VISIBLE);

      }
      // if i clicked this previousChapter  Button set new text and set previousChapter unavailable
      previousChapter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              count--;
              if ( count<1) {
                  previousChapter.setVisibility(View.GONE);
              }
              setText(result.get(count));


          }
      });

      final Button nextChapter = findViewById(R.id.nextChapter);
      // if i clicked this nextChapter  Button set new text and set previousChapter  available

      nextChapter.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (result.size()<=count+1){
                  Toast.makeText(getBaseContext(),getString(R.string.there_are), Toast.LENGTH_LONG).show();

              }
              else {
                  count++;
                  previousChapter.setVisibility(View.VISIBLE);
                  setText(result.get(count));
              }
          }
      });



            mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override

                // if onNavigationItemSelected
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
                                menuItem.setIcon(R.drawable.ic_favorite_border_black_24dp);// update love icon
                                MyInfoManager.getInstance().deleteLike(like);// its mean that the user didnt like the book
                                String vote_heart = b.getVote_heart();
                                b.setVote_heart(String.valueOf(Integer.parseInt(vote_heart) - 1));// update book
                                menuItem.setTitle(""+b.getVote_heart());// update  love Title
                                MyInfoManager.getInstance().updateBook(b);/// update book at sqlite

                            }
                            else {
                                 menuItem.setIcon(R.drawable.ic_favorite_white_24dp);
                                 MyInfoManager.getInstance().createLike(like);// its mean that there now a like for this book
                                 String vote_heart = b.getVote_heart();
                                 b.setVote_heart(String.valueOf(Integer.parseInt(vote_heart) + 1));// update book
                                 menuItem.setTitle("" + b.getVote_heart());// update  love Title
                                 MyInfoManager.getInstance().updateBook(b);   /// update book at sqlite                         }
                                 return true;
                             }
                        }
                        case R.id.message2: {
                            try {

                           Intent intent2 = new Intent(getBaseContext(),CommentList.class);
                            intent2.putExtra("chapter",result.get(count).getChapter_id()) ;
                            intent2.putExtra("BookTitle", b.getTitle()) ;
                            intent2.putExtra("chapterNumber",count+1) ;
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

    /**
     *  make chapter information visible
     * @param c
     */
  public void setText(Chapter c ){

      Autors_Notes_view_comment= findViewById(R.id.Autors_Notes_view_comment);
      Endnotes_comment= findViewById(R.id.Endnotes_comment);
      Story_Content = findViewById(R.id.Story_Content);
      iv =  findViewById(R.id.imageView1);
      if(c.getImage()!=null) {
          iv.setImageBitmap(c.getImage());
      }
      Cover_Image_Chapter= findViewById(R.id.Cover_Image_Chapter);
      Cover_Image_Chapter.setText("Chapter "+String.valueOf(count+1)+" - "+BookTitle);

      Autors_Notes_view_comment.setText(c.getAuthorNote());
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
