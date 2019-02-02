package com.example.shaharben_ezra.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

import core.Chapter;
import core.MyInfoManager;

/**
 * author Shahar Ben-Ezra
 * this class is chapter activity here you add the chapter to specific book
 */
public class ChapterActivity extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;

    ImageView iv;
    Bitmap B;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.chapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarChapter);
        setSupportActionBar(toolbar);
        String s = getIntent().getStringExtra("String");
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final EditText et_Chapter_Title = findViewById(R.id.et_Chapter_Title);
        final EditText et_Autors_Notes_Title = findViewById(R.id.et_Autors_Notes_Title);
        final EditText et_Story_Content = findViewById(R.id.et_Story_Content);
        final EditText et_Endnotes = findViewById(R.id.et_Endnotes);

        iv=(ImageView)findViewById(R.id.imageView1);
       // iv.setImageResource( R.drawable.abc_ab_share_pack_mtrl_alpha);
        iv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //perform your action here
                Toast.makeText(getApplicationContext(), "Story_characters",
                        Toast.LENGTH_SHORT).show();
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

            }
        });



        Button b = findViewById(R.id.SUBMIT_Chapter);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                String x = String.valueOf(random.nextInt(900) + 100);
                String bookId = getIntent().getStringExtra("int");
                Chapter c=    new Chapter(x,et_Chapter_Title.getText().toString(),"0",et_Story_Content.getText().toString(),
                        et_Autors_Notes_Title.getText().toString(),et_Endnotes.getText().toString(), bookId,B);
                MyInfoManager.getInstance().createChapter(c);
                Intent intent1 = new Intent( getBaseContext(),MainActivity.class);
                startActivity(intent1);

            }

        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        //Detects request codes
        if(requestCode==GET_FROM_GALLERY && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                B=bitmap;

                iv.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
