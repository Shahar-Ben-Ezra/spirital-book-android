package com.example.shaharben_ezra.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import core.Book;
import core.BookList;
import core.MyInfoManager;

/**
 * author Shahar Ben-Ezra
 *  a fragment that will show a list of reading book and can create a new list
 */
public class reading_list_fragment extends Fragment {

    private RecyclerView recyclerView;
    private List<BookList> listItems;
    private reading_list_adapter adapter ;

    public reading_list_fragment( ) { }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View V= inflater.inflate(R.layout.reading_list__frag,container,false);
        recyclerView = (RecyclerView) V.findViewById(R.id.recyclerView_readingLisT);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager( getActivity()));
        List<BookList> dataList= MyInfoManager.getInstance().getAllListBooks(Main.userName);

        if(!dataList.isEmpty()) {
            adapter = new reading_list_adapter(dataList, this.getContext()) ;
            recyclerView.setAdapter(adapter);
        }
        FloatingActionButton fab = V.findViewById(R.id.fab_reading_list);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage( R.string.dialog_mes );
                builder. setTitle(R.string.dialog_title);
                final EditText edittext = new EditText(getContext());
                builder.setView(edittext);
                builder.setPositiveButton( R.string.create ,new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {//user name
                        MyInfoManager.getInstance().AddListBook(new BookList( (String) edittext.getText().toString(),"0",Main.userName));
                        view.findViewById(android.R.id.content);
                        Snackbar snackbar = Snackbar
                                .make(view,R.string.list_created, Snackbar.LENGTH_LONG);
                        snackbar.show();

                        List<BookList> dataList= MyInfoManager.getInstance().getAllListBooks(Main.userName);
                        adapter = new reading_list_adapter(dataList, getContext()) ;
                        recyclerView.setAdapter(adapter);
                    }
                });
                builder.setNegativeButton(com.example.shaharben_ezra.myapplication.R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ///   Toast.makeText(getContext(), "list successfully deleted!", Toast.LENGTH_SHORT).show();

                    }
                });
                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return V;

    }


}