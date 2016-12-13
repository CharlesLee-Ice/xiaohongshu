package com.learning.xiaohongshu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /***
         * http://stackoverflow.com/questions/27856603/lollipop-draw-behind-statusbar-with-its-color-set-to-transparent
         *
         * To achieve a completely transparent status bar, you have to use statusBarColor, which is only available on API 21 and above.
         * windowTranslucentStatus is available on API 19 and above, but it adds a tinted background for the status bar.
         * However, setting windowTranslucentStatus does achieve one thing that changing statusBarColor to transparent does not: it sets the SYSTEM_UI_FLAG_LAYOUT_STABLE and SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN flags.
         * The easiest way to get the same effect is to manually set these flags, which effectively disables the insets imposed by the Android layout system and leaves you to fend for yourself.
         */
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        final WelcomeFragmentAdapter fragmentAdapter = new WelcomeFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentAdapter);
        final ViewPager.SimpleOnPageChangeListener pageChangeListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                Fragment currentFragment = fragmentAdapter.getItem(position);
                if (currentFragment instanceof WelcomeBaseFragment) {
                    ((WelcomeBaseFragment) currentFragment).playAnimation();
                }
            }
        };
        viewPager.addOnPageChangeListener(pageChangeListener);
        viewPager.post(new Runnable() {
            @Override
            public void run() {
                pageChangeListener.onPageSelected(0);
            }
        });
    }

    class WelcomeFragmentAdapter extends FragmentStatePagerAdapter {

        public WelcomeFragmentAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return WelcomePageOneFragment.newInstance();
                case 1:
                    return WelcomePageTwoFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
