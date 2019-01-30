package com.example.shaharben_ezra.myapplication;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import core.Category;
import core.MyInfoManager;

public class My_stories extends Fragment {

    private View cachedView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (cachedView == null) {
            // Inflate and populate
            cachedView = inflater.inflate(R.layout.my_stories, container, false);

            // Setting ViewPager for each Tabs
            ViewPager viewPager = (ViewPager) cachedView.findViewById(R.id.container_my_stories );
            setupViewPager(viewPager);
            // Set Tabs inside Toolbar
            TabLayout tabs = (TabLayout) cachedView.findViewById(R.id.tabMyStories);
            tabs.setupWithViewPager(viewPager);
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#e5ff00"));
        }


        return cachedView;

    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Discover.Adapter adapter = new  Discover.Adapter(getFragmentManager());
        adapter.addFragment(new Book_frag(MyInfoManager.getInstance().getAllBooksByUserName(Main.userName)), "MY STORIES");

        adapter.addFragment(new Message_frag(), "DELETED STORIES");
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


    public static class CategoryListAdapter extends ArrayAdapter<Category> {

        private List<Category> dataList = null;
        private Activity context = null;
        public CategoryListAdapter(Context context, List<Category> dataList) {
            super(context, R.layout.category, dataList);
            this.dataList = dataList;
            this.context = (Activity)context;
        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Category getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater=context.getLayoutInflater();
            View rowView=inflater.inflate(R.layout.category, null,false);

            TextView CategoryName = (TextView) rowView.findViewById(R.id.CategoryName1);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            TextView storiesCount = (TextView) rowView.findViewById(R.id.storiesCount);
            Category c = dataList.get(position);
             CategoryName.setText(c.getCategory_name());
              storiesCount.setText("1"+"Stories ");
            return rowView;

        };
    }
}