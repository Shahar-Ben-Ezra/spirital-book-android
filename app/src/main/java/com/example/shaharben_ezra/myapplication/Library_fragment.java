package com.example.shaharben_ezra.myapplication;

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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import core.Book;
import core.Library;
import core.MyInfoManager;

/**
 * this class Fragment show all the books that the user add to library
 */
public class Library_fragment extends Fragment {


    private RecyclerView recyclerView;
    private List<Library> listLibrary;
    private current_read_adapter adapter ;
    private List<Book> listBook= new ArrayList<Book>();

    public Library_fragment( ) {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V= inflater.inflate(R.layout.library_user_book,container,false);
        recyclerView = (RecyclerView) V.findViewById(R.id.recyclerView_user_library);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
         listLibrary =MyInfoManager.getInstance().getLibrary(Main.userName);

        TextView  TextView = (TextView) V.findViewById(R.id.TextView_LIBRARY);
        ImageView ImageView = (ImageView) V.findViewById(R.id.imageView_LIBRARY);
        if (listLibrary.isEmpty()){
            ImageView.setVisibility(View.VISIBLE);
            TextView.setVisibility(View.VISIBLE);
        }
        else {
            ImageView.setVisibility(View.GONE);
            TextView.setVisibility(View.GONE);
            for(Library l:listLibrary){
                Book b= MyInfoManager.getInstance().getBooksObjByBookId(l.getBookId());
                if(!listBook.contains(b))
                    listBook.add(b) ;
            }

            adapter = new current_read_adapter(listBook, this.getActivity());
            recyclerView.setAdapter(adapter);
        }
        FloatingActionButton fab = V.findViewById(R.id.fab_user_library);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             Intent intent1 = new Intent( getActivity(),Create_story.class);
                startActivity(intent1);
             }
        });
        recyclerView.setAdapter(adapter);


        return V;

    }

}
