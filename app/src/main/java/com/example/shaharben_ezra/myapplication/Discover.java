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

import core.MyInfoManager;

/**
 * Discover fragment that includes his tabs
 *  CATEGORIES,GENRES,NEWEST,RECOMMENDED and for each one he have is own fragment
 */

public class Discover extends Fragment {
    private  View cachedView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        if(cachedView==null) {
              cachedView = inflater.inflate(R.layout.discover_fragment, container, false);
            // Setting ViewPager for each Tabs
            ViewPager viewPager = (ViewPager) cachedView.findViewById(R.id.containerDiscover);
            setupViewPager(viewPager);
            // Set Tabs inside Toolbar
            TabLayout tabs = (TabLayout) cachedView.findViewById(R.id.tabDiscover);
            tabs.setupWithViewPager(viewPager);
            tabs.setSelectedTabIndicatorColor(Color.parseColor(getString(R.string.YELLOW)));
        }

        return cachedView;

    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Adapter adapter = new Adapter(getFragmentManager());
        adapter.addFragment(new Type_fragment(), "CATEGORIES");
        adapter.addFragment(new Book_frag(MyInfoManager.getInstance().getAllBooks(),false), "RECOMMENDED");
        adapter.addFragment(new Book_frag(MyInfoManager.getInstance().getAllBooks(),false), "NEWEST");
        adapter.addFragment(new Genre_frag(), "GENRES");
        viewPager.setAdapter(adapter);



    }

    /**
     *  a adapter class the handel FragmentPagerAdapter
     */
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
