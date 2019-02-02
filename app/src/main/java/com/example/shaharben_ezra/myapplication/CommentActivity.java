package com.example.shaharben_ezra.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;
import java.util.Random;

import core.Book;
import core.Comment;
import core.MyInfoManager;

/**
 * this class give the option to comment to  a book
 */
public class CommentActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState );
        setContentView(R.layout.comment_activity);

        Button b= (Button)findViewById(R.id.send_comment);
        final Spinner s = findViewById(R.id.spinner2);
        final EditText etComment=findViewById(R.id.etComment);
        final String ChapterId=  getIntent().getStringExtra ("chapter");
        final String book_id=getIntent().getStringExtra ("BookID");

        final Book book = MyInfoManager.getInstance().getBooksObjByBookId(book_id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarComment_activity);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.write_comment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String spinerItem=s.getSelectedItem().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                Random random = new Random();
                String Comment_id = String.valueOf(random.nextInt(900) + 100);
                String userName= Main.userName;
                MyInfoManager.getInstance().createComment(new Comment(currentDateTimeString,etComment.getText().toString().trim(),Comment_id,userName,ChapterId, (spinerItem)));
                book.setVote_comment(String.valueOf(Integer.parseInt(book.getVote_comment())+1));
                MyInfoManager.getInstance().updateBook(book);
                View v2=(View)findViewById(android.R.id.content);
                Snackbar snackbar = Snackbar
               .make(v2, "comment successfully sent!", Snackbar.LENGTH_LONG);
                snackbar.show();
                finish();

            }
        });

    }


    /**
     * go back to the last activity
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if ( id == android.R.id.home ) {
            AlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Alert Dialog
     */
    public  void AlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // 2. Chain together various setter methods to set the dialog characteristics
        builder.setMessage(R.string.are_you);

        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finish();
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        // 3. Get the AlertDialog from create()
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
