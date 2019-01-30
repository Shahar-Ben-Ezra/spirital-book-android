package com.example.shaharben_ezra.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

import core.Book;
import core.Category;
import core.MyInfoManager;

public class MainActivity extends AppCompatActivity  {
    private BottomNavigationView mMainNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        mMainNav = (BottomNavigationView)findViewById(R.id.bnv1);
        BottomNavigationViewHelper.disableShiftMode(mMainNav);
        MyInfoManager.getInstance().openDataBase(this);
        CreateCategory();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Discover");

        toolbar.setLogo(R.mipmap.ic_launcher_round);
        ft.replace(R.id.Maincontainer, new Discover(), "Discover");
        ft.commit();
        ft.addToBackStack(null);

        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()) {
                    case R.id.nav_discover: {

                        Discover fragB = (Discover) getSupportFragmentManager().findFragmentByTag("Discover");
                        ft.replace(R.id.Maincontainer, fragB, "Discover");
                        ft.commit();
                        ft.addToBackStack(null);
                        getSupportActionBar().setTitle("Discover");
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
        searchView.setQueryHint("searching ");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()

        {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
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

    protected void CreateCategory() {


        if (MyInfoManager.getInstance().getAllCategories().isEmpty()) {

            ArrayList<Category> c = new ArrayList<>();

            c.add(new Category("1", "Naruto", "Animes Mangas", "0"));
            c.add(new Category("2", "Pokemon", "Animes Mangas", "0"));
            c.add(new Category("3", "Fairly Tail", "Animes Mangas", "0"));
            c.add(new Category("4", "Vocaloid", "Animes Mangas", "0"));
            c.add(new Category("5", "Dragon Ball", "Animes Mangas", "0"));
            c.add(new Category("6", "EXO", "Bands Musicians", "0"));
            c.add(new Category("7", "Justin Bieber", "Bands Musicians", "0"));
            c.add(new Category("8", "One Direction", "Bands Musicians", "0"));
            c.add(new Category("9", "Got7", "Bands Musicians", "0"));
            c.add(new Category("10", "Twice", "Bands Musicians", "0"));
            c.add(new Category("11", "6Teen", "Cartons", "0"));
            c.add(new Category("12", "Batman", "Cartons", "0"));
            c.add(new Category("13", "Iron Man", "Cartons", "0"));
            c.add(new Category("14", "X-Men", "Cartons", "0"));
            c.add(new Category("15", "Witch", "Cartons", "0"));
            c.add(new Category("16", "Ariana Grande", "Celebrities", "0"));
            c.add(new Category("17", "Elvis Presely", "Celebrities", "0"));
            c.add(new Category("18", "50 cent", "Celebrities", "0"));
            c.add(new Category("19", "Lionel Messi", "Celebrities", "0"));
            c.add(new Category("20", "Big Sean", "Celebrities", "0"));
            c.add(new Category("21", "WWE", "Games", "0"));
            c.add(new Category("22", "Robin Hood", "Games", "0"));
            c.add(new Category("23", "Fallout", "Games", "0"));
            c.add(new Category("24", "Splatoon", "Games", "0"));
            c.add(new Category("25", "Bloodborne", "Games", "0"));
            c.add(new Category("26", "Harry Potter", "Movies", "0"));
            c.add(new Category("27", "Star Wars", "Movies", "0"));
            c.add(new Category("28", "Captain America", "Movies", "0"));
            c.add(new Category("29", "Dead pool", "Movies", "0"));
            c.add(new Category("30", "007", "Movies", "0"));

            for (Category Cate : c) {
                MyInfoManager.getInstance().AddCategory(Cate);
            }
        }


    }
}
