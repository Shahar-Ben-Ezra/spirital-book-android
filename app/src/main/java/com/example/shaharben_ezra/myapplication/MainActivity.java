package com.example.shaharben_ezra.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import core.Book;
import core.MyInfoManager;


public class MainActivity extends AppCompatActivity  {
    private BottomNavigationView mMainNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.main_activity);
            mMainNav = (BottomNavigationView) findViewById(R.id.bnv1);
            BottomNavigationViewHelper.disableShiftMode(mMainNav);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


            getSupportActionBar().setTitle("Messages");

            toolbar.setLogo(R.mipmap.ic_launcher_round);
            ft.replace(R.id.Maincontainer, new Message(), "Messages");
            ft.commit();
            ft.addToBackStack(null);
             mMainNav.setSelectedItemId(R.id.nav_mes);



            mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    switch (menuItem.getItemId()) {
                        case R.id.nav_discover: {
                            getSupportActionBar().setTitle("Discover");
                            Discover frag  = (Discover) getSupportFragmentManager().findFragmentByTag("Discover");
                            if (frag != null) {

                                ft.replace(R.id.Maincontainer, frag, "Discover");
                                ft.commit();
                                ft.addToBackStack(null);
                            }
                            else {
                                ft.replace(R.id.Maincontainer, new Discover(), "Discover");
                                ft.commit();
                                ft.addToBackStack(null);

                            }
                            return true;
                        }

                        case R.id.nav_library: {

                            getSupportActionBar().setTitle("Library");
                            Library frag = (Library) getSupportFragmentManager().findFragmentByTag("Library");
                            if (frag != null) {
                                ft.replace(R.id.Maincontainer, frag, "Library");
                                ft.commit();
                                ft.addToBackStack(null);

                            } else {
                                ft.replace(R.id.Maincontainer, new Library(), "Library");
                                ft.commit();
                                ft.addToBackStack(null);

                            }
                            return true;
                        }
                        case R.id.nav_act: {

                            getSupportActionBar().setTitle("Activities");
                            Activities frag = (Activities) getSupportFragmentManager().findFragmentByTag("Activities");
                            if (frag != null) {
                                ft.replace(R.id.Maincontainer, frag, "Activities");
                                ft.commit();
                                ft.addToBackStack(null);

                            } else {
                                ft.replace(R.id.Maincontainer, new Activities(), "Activities");
                                ft.commit();
                                ft.addToBackStack(null);


                            }
                            return true;
                        }
                        case R.id.nab_myStories: {

                            getSupportActionBar().setTitle("My Stories");
                            My_stories frag = (My_stories) getSupportFragmentManager().findFragmentByTag("My Stories");
                            if (frag != null) {
                                ft.replace(R.id.Maincontainer, frag, "My Stories");
                                ft.commit();
                                ft.addToBackStack(null);

                            } else {
                                ft.replace(R.id.Maincontainer, new My_stories(), "My Stories");
                                ft.commit();
                                ft.addToBackStack(null);


                            }
                            return true;
                        }
                        case R.id.nav_mes: {
                            getSupportActionBar().setTitle("Messages");
                            Message frag = (Message) getSupportFragmentManager().findFragmentByTag("Messages");
                            if (frag != null) {
                                ft.replace(R.id.Maincontainer, frag, "Messages");
                                ft.commit();
                                ft.addToBackStack(null);

                            } else {
                                ft.replace(R.id.Maincontainer, new Message(), "Messages");
                                ft.commit();
                                ft.addToBackStack(null);


                            }
                        }
                    }
                    return true;
                }
            });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem m = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) m.getActionView();
        searchView.setQueryHint(getString(R.string.searching_for_books));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!query.isEmpty()&& query!=null){
                    List<Book> listBook=new ArrayList<>();
                    for (Book book : MyInfoManager.getInstance().getAllBooks()) {
                        if (book.getTitle().contains(query)) {
                            listBook.add(book);
                            break;
                        }
                    }
                    if(listBook.isEmpty()){
                        Toast.makeText(getBaseContext(),R.string.no_result, Toast.LENGTH_LONG).show();

                    }
                    else {

                         Intent intent= new Intent(getBaseContext(),searchActivity.class);
                         intent.putExtra("query",query);
                         startActivity(intent);

                    }
                    return true;

                }
                else{
                    Toast.makeText(getBaseContext(),R.string.search_again, Toast.LENGTH_LONG).show();
                    return false;

                }
             }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
//
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_alart) {
            Toast.makeText(this, "dont need to do", Toast.LENGTH_SHORT).show();

            return true;
        }


        if (id == R.id.Profile) {
            Intent intent= new Intent(this,Profile.class);
            startActivity(intent);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }
//
//    @Override
//    protected void onPause() {
//        MyInfoManager.getInstance().closeDataBase();
//        super.onPause();
//    }




}
