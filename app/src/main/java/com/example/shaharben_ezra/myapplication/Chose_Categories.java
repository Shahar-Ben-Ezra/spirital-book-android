package com.example.shaharben_ezra.myapplication;

import android.content.Intent;
 import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

import core.Category;
import core.MyInfoManager;

public class Chose_Categories extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_categories);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarC);
        setSupportActionBar(toolbar);
        final Spinner s1 = findViewById(R.id.spinner_Type);
        getSupportActionBar().setTitle(R.string.CHOOSE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Spinner s2 = findViewById(R.id.spinner21);
        s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<String> categories = new ArrayList<>();

                List<Category> CList  =MyInfoManager.getInstance().getCategoryByType_name(s1.getSelectedItem().toString());

                for(Category c:CList){
                    categories.add(c.getCategory_name());
                }
                //Style and populate the spinner
                ArrayAdapter<String> dataAdapter;
                dataAdapter = new ArrayAdapter(getBaseContext(),android.R.layout.simple_spinner_item, categories);

                //Dropdown layout style
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                //attaching data adapter to spinner
                s2.setAdapter(dataAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button b = findViewById(R.id.ADD_CATEGORY);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reIntent=new Intent();

                reIntent.putExtra("String",s2.getSelectedItem().toString());
                setResult(1,reIntent);
                finish();
            }
        });

     }
}
