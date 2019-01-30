package com.example.shaharben_ezra.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import core.Book;
import core.Category;
import core.MyInfoManager;

/**
 * this class is activity that create book story
 */
public class Create_story extends AppCompatActivity {
    public static final int GET_FROM_GALLERY = 3;
    ImageView iv;
    Bitmap B;
    TextView  category_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_story);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);
        final Spinner spinner = findViewById(R.id.spinner);
         getSupportActionBar().setTitle(R.string.Submit_story);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      final   EditText et_Story_Title= findViewById(R.id.et_Story_Title);

            category_selected= findViewById(R.id.category_selected);

        Button b = findViewById(R.id.SUBMIT_STORY);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                String x = String.valueOf(random.nextInt(900) + 100);
                String user= Main.userName;

                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                final String spinerLan=spinner.getSelectedItem().toString();
                String category=category_selected.getText().toString();
                Category c= MyInfoManager.getInstance().getCategoryobj(category);
                c.setConut(String.valueOf(Integer.parseInt(c.getConut())+1));//update Category count++;
                MyInfoManager.getInstance().updateCategory( c);///update at sql;
                MyInfoManager.getInstance().AddBooks(new Book( x, et_Story_Title.getText().toString(),user,"Completed",
                       "length",currentDateTimeString,spinerLan,category_selected.getText().toString(),
                        "1","0","0","0",B) );
                Intent intent1 = new Intent( getBaseContext(),ChapterActivity.class);
                intent1.putExtra("String", et_Story_Title.getText().toString()) ;
                intent1.putExtra("int", x) ;

                startActivity(intent1);

            }
        });
        TextView tv=(TextView)findViewById(R.id.Story_category);
        TextView tv1=(TextView)findViewById(R.id.Story_characters);

        tv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent in = new Intent(getBaseContext(),Chose_Categories.class);
                startActivityForResult(in,1);

                ///category_selected

            }
        });



        tv1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), R.string.dont,
                        Toast.LENGTH_SHORT).show();

            }
        });
          iv=(ImageView)findViewById(R.id.imageView1);
         iv.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //perform your action here

                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);

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

        if( requestCode==1 && resultCode ==  1){
            if(data.getStringExtra("String")!=null)
             category_selected.setText(data.getStringExtra("String"));
        }

    }
}
