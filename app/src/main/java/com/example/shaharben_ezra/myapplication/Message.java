package com.example.shaharben_ezra.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Message extends Fragment {

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
            cachedView = inflater.inflate(R.layout.message, container, false);
            // Setting ViewPager for each Tabs
            ViewPager viewPager = (ViewPager) cachedView.findViewById(R.id.containerMessage);
            setupViewPager(viewPager);
            // Set Tabs inside Toolbar
            TabLayout tabs = (TabLayout) cachedView.findViewById(R.id.tabMessage);
            tabs.setupWithViewPager(viewPager);
            tabs.setSelectedTabIndicatorColor(Color.parseColor("#e5ff00"));
        }
          return cachedView;

    }


    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {

        Discover.Adapter adapter = new Discover.Adapter(getFragmentManager());
        adapter.addFragment(new Message_frag(), "INBOX");
        adapter.addFragment(new Message_frag(), "SENT");
        adapter.addFragment(new Message_frag(), "TRASH");
        viewPager.setAdapter(adapter);

    }
}
