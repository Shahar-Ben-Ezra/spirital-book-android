package com.example.shaharben_ezra.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.List;

import core.Book;
import core.MyInfoManager;


@SuppressLint("ValidFragment")
public class Book_frag extends Fragment {


    private RecyclerView recyclerView;
    private List<Book> listItems;
    private   MyAdapter adapter ;

    public Book_frag(List<Book> bookList ) {
        this.listItems=bookList;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View V= inflater.inflate(R.layout.fragment_blank,container,false);
        recyclerView = (RecyclerView) V.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        if(listItems.isEmpty()){
            Toast.makeText(getContext(), R.string.there_are_not, Toast.LENGTH_SHORT).show();

        }
         adapter = new MyAdapter(listItems, this.getActivity()) ;
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = V.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent1 = new Intent( getActivity(),Create_story.class);
                 startActivity(intent1);

            }
        });
        return V;

    }



}


