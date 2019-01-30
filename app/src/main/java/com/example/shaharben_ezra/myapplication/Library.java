package com.example.shaharben_ezra.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class Library extends Fragment {

private  View cachedView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (cachedView == null) {
            // Inflate and populate
            cachedView = inflater.inflate(R.layout.library_fragment, container, false);

            // Setting ViewPager for each Tabs
            ViewPager viewPager = (ViewPager) cachedView.findViewById(R.id.containerlibrary );
            setupViewPager(viewPager);
            // Set Tabs inside Toolbar
            TabLayout tabs = (TabLayout) cachedView.findViewById(R.id.tabLibrary);
            tabs.setupWithViewPager(viewPager);
             tabs.setSelectedTabIndicatorColor(Color.parseColor("#e5ff00"));
        }


        return cachedView;

    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Discover.Adapter adapter = new   Discover.Adapter(getFragmentManager());
        adapter.addFragment(new Library_fragment(), "CURRENT READS");
        adapter.addFragment(new Archive_frag(), "ARCHIVE");
        adapter.addFragment(new reading_list_fragment(), "READING");
        viewPager.setAdapter(adapter);



    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}