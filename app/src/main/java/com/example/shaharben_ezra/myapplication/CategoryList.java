package com.example.shaharben_ezra.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import core.BookList;
import core.Category;
import core.MyInfoManager;

/**
 * author Shahar Ben-Ezra
 * activity that show all the categories that belong to specific type
 */
public class CategoryList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Category> listItems;
    private CategoryAdapter adapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String s=  getIntent().getStringExtra("int");
        setTitle(s);
        recyclerView = (RecyclerView)  findViewById(R.id.CategoryRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getBaseContext()));

        listItems= MyInfoManager.getInstance().getCategoryByType_name(s);
            if(listItems.isEmpty()){
                Toast.makeText(getBaseContext(), R.string.no_data, Toast.LENGTH_SHORT).show();

            }
            else {
                adapter = new CategoryAdapter(listItems, getBaseContext());
                recyclerView.setAdapter(adapter);
            }



    }
}