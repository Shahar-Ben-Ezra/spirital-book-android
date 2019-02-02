package com.example.shaharben_ezra.myapplication;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import core.MyInfoManager;

/**
 * author Shahar Ben-Ezra
 * this profile activity will show to user his own book stories is reading list and updated
 */
public class Profile extends AppCompatActivity {
//windowActionBar to false in your theme to use a Toolbar instead.
    private Profile.SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        TextView tv_user = (TextView) findViewById(R.id.textViewUser);
        tv_user.setText(Main.userName);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSectionsPagerAdapter = new Profile.SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container_profile);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabprofile);
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor(getString(R.string.YELLOW)));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        TextView stories_num = (TextView) findViewById(R.id.stories_num);
        stories_num.setText(""+MyInfoManager.getInstance().getAllBooksByUserName(Main.userName).size());

        TextView reading_list_num = (TextView) findViewById(R.id.reading_list_num);
        reading_list_num.setText(""+MyInfoManager.getInstance().getAllListBooks(Main.userName).size());

        TextView followers_num = (TextView) findViewById(R.id.followers_num);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            View v2=(View)findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(v2, R.string.dont_need, Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }
        if (id == R.id.action_SHARE1) {
            View v2=(View)findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(v2, R.string.dont_need, Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }


        if (id == R.id.open_in_browser) {
            View v2=(View)findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(v2, R.string.dont_need, Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }

        if (id == R.id.app_item_copy_link) {
            View v2=(View)findViewById(android.R.id.content);
            Snackbar snackbar = Snackbar
                    .make(v2, R.string.dont_need, Snackbar.LENGTH_LONG);

            snackbar.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case  0:
                    return new Book_frag(MyInfoManager.getInstance().getAllBooksByUserName(Main.userName),false) ;
                case  1:
                    return   new Book_frag(MyInfoManager.getInstance().getAllBooksByUserName(Main.userName),false)   ;
                case  2:
                    return  new  reading_list_fragment() ;

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }


    }

}

