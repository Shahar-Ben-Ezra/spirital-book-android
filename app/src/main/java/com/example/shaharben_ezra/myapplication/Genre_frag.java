package com.example.shaharben_ezra.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Genre_frag extends Fragment {


    private ListView listView;
    private ArrayList<String> GenreArrayList;
    private ArrayAdapter<String> adapter;
    android.content.Context Context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.type,container,false);
        Context=v.getContext();
        listView=(ListView)v.findViewById(R.id.listtype);
        GenreArrayList=new ArrayList<String>();
        String[] Gener;

        Gener=getResources().getStringArray(R.array.generlist);
         adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_list_item_1,android.R.id.text1,Gener);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String genre= (String)listView.getItemAtPosition(position);
                Toast.makeText( Context,"dont need to do that",Toast.LENGTH_LONG).show();

                Intent intent1 = new Intent( getActivity(),BooksByCategoryTabs.class);
                intent1.putExtra("int", genre) ;
                startActivity(intent1);



            }
        });
        return v;
    }
}