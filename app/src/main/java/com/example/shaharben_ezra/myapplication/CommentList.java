package com.example.shaharben_ezra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import core.Comment;
import core.MyInfoManager;

/**
 * this class show all the comment list for specific chapter and can add a comment
 */
public class CommentList extends AppCompatActivity {

    private ListView itemsList;
    private CommentListAdapter adapter;
     private Context ctx;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_list);

        itemsList = (ListView)findViewById(R.id.CommentList);
        ctx=getBaseContext();

        final  String ChapterId=  getIntent().getStringExtra ("chapter");
        String BookTitle=  getIntent().getStringExtra("BookTitle" );
        int chapterNumber=  getIntent().getIntExtra ("chapterNumber",0);
        final String book_id=getIntent().getStringExtra ("BookID");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarCommentList);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("Chapter"+chapterNumber+" -Comments");
         getSupportActionBar().setTitle(BookTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        List<Comment> list = MyInfoManager.getInstance().getAllCommentByChapterId(ChapterId);
        adapter = new CommentListAdapter(ctx, R.layout.comment, list);
        itemsList.setAdapter(adapter);

        TextView TextView = (TextView)  findViewById(R.id.TextView_CommentsLIST);
        ImageView ImageView = (ImageView)  findViewById(R.id.imageView_comment_list);
        TextView ET_click = (TextView)  findViewById(R.id.ET_click);

        ET_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getBaseContext(), CommentActivity.class);
                intent.putExtra("chapter",ChapterId);
                intent.putExtra("BookID",book_id) ;

                startActivity(intent);
            }
        });
        if (list.isEmpty()){
            ImageView.setVisibility(View.VISIBLE);
            TextView.setVisibility(View.VISIBLE);
        }
        else {
            ImageView.setVisibility(View.GONE);
            TextView.setVisibility(View.GONE);
        }
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
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        final  String ChapterId=  getIntent().getStringExtra ("chapter");
        List<Comment> list = MyInfoManager.getInstance().getAllCommentByChapterId(ChapterId);
        adapter = new CommentListAdapter(ctx, R.layout.comment, list);
        itemsList.setAdapter(adapter);
    }


}
