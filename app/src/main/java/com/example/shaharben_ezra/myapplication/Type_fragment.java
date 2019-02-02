package com.example.shaharben_ezra.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 *  author Shahar Ben-Ezra
 *  type fragment will show all the type at the app
 *
 */

public class Type_fragment extends Fragment {

    private ListView listView;
    private  ArrayAdapter<String> adapter;
    Context Context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      final View v= inflater.inflate(R.layout.type,container,false);

        Context=v.getContext();
        listView=(ListView)v.findViewById(R.id.listtype);
        String[] Typecategories;

        Typecategories=getResources().getStringArray(R.array.typeCategories);
        adapter=new ArrayAdapter<String>(this.getActivity() ,android.R.layout.simple_list_item_1,android.R.id.text1,Typecategories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String typeCategory= (String)listView.getItemAtPosition(position);
                Log.d("position",String.valueOf(position));
                Intent intent1 = new Intent( getActivity(),CategoryList.class);
                intent1.putExtra("int", typeCategory) ;
                startActivity(intent1);

            }
        });

        return v;
    }
}
