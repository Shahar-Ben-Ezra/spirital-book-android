package com.example.shaharben_ezra.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import core.Book;
import core.MyInfoManager;

public class ReadingListStories extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Book> listItems;
    private MyAdapter adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);

        setContentView(R.layout.readinglist_stories);
        recyclerView = (RecyclerView)  findViewById(R.id.recyclerView_readingList_stories);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( this));
        String bookList_id= getIntent().getStringExtra("int" );
        String s=  getIntent().getStringExtra("name");
            listItems= MyInfoManager.getInstance().getAllTheBooksAtList(bookList_id);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReadingListStories_VIEW);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(listItems.size()+" Stories");
         getSupportActionBar().setTitle(s);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         adapter = new MyAdapter(listItems, this ) ;
        recyclerView.setAdapter(adapter);
        TextView TextView = (TextView)  findViewById(R.id.TextView_readingList_stories);
        ImageView ImageView = (ImageView)  findViewById(R.id.imageView_readingList_stories);

        if (listItems.isEmpty()){
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
}
