package com.phelix.winbetadmin;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity
        extends AppCompatActivity
{
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    protected void onCreate(Bundle paramBundle)
    {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        this.mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        this.mViewPager = findViewById(R.id.container);
        this.mViewPager.setAdapter(this.mSectionsPagerAdapter);
        ((TabLayout)findViewById(R.id.tabs)).setupWithViewPager(this.mViewPager);
    }



    public boolean onOptionsItemSelected(MenuItem paramMenuItem)
    {
        paramMenuItem.getItemId();
        return super.onOptionsItemSelected(paramMenuItem);
    }

    public class SectionsPagerAdapter
            extends FragmentPagerAdapter
    {
        public SectionsPagerAdapter(FragmentManager paramFragmentManager)
        {
            super(paramFragmentManager);
        }

        public int getCount()
        {
            return 2;
        }

        public Fragment getItem(int paramInt)
        {
            switch (paramInt)
            {
                case 0:
                    return new NewPost();
                case 1:
                    return new ManagePosts();
            }
            return new ManagePosts();
        }

        public CharSequence getPageTitle(int paramInt)
        {
            switch (paramInt)
            {

                case 0:
                    return "ADD POST";
                case 1:
                    return "MANAGE POSTS";
            }
            return "MANAGE POSTS";
        }
    }
}

