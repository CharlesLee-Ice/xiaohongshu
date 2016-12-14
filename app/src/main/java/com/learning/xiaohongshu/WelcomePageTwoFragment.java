package com.learning.xiaohongshu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by liqilin on 2016/12/13.
 */

public class WelcomePageTwoFragment extends WelcomeBaseFragment {

    private ImageView mLogo;
    private LinearLayout mLoginAndRegister;
    private boolean mAlreadyIn = false;

    private static WelcomePageTwoFragment sWelcomePageTwo;
    public static WelcomePageTwoFragment newInstance() {
        if (sWelcomePageTwo == null) {
            sWelcomePageTwo = new WelcomePageTwoFragment();
        }
        return sWelcomePageTwo;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.welcome_page_two, container, false);
        mLogo = (ImageView) layout.findViewById(R.id.logo);
        mLoginAndRegister = (LinearLayout) layout.findViewById(R.id.login_and_register);

        final ImageView indicatorLogin = (ImageView) layout.findViewById(R.id.indicator_login);
        final ImageView indicatorRegister = (ImageView) layout.findViewById(R.id.indicator_register);

        ViewPager viewPager = (ViewPager) layout.findViewById(R.id.view_pager_login_register);
        viewPager.setAdapter(new LoginRegisterPagerAdapter(getActivity().getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    indicatorLogin.setVisibility(View.VISIBLE);
                    indicatorRegister.setVisibility(View.INVISIBLE);
                } else if (position == 1) {
                    indicatorLogin.setVisibility(View.INVISIBLE);
                    indicatorRegister.setVisibility(View.VISIBLE);
                }
            }
        });

        return layout;
    }

    @Override
    public void playAnimation() {
        if (!mAlreadyIn) {
            ViewCompat.animate(mLogo)
                    .scaleX(0.6f)
                    .scaleY(0.6f)
                    .translationY(-600f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(500)
                    .start();

            mLoginAndRegister.setVisibility(View.VISIBLE);
            float translationY = DisplayUtils.getDisplayHeightPixels(getActivity()) - getResources().getDimension(R.dimen.login_register_margin_top);
            mLoginAndRegister.setTranslationY(translationY);
            ViewCompat.animate(mLoginAndRegister)
                    .translationYBy(-translationY)
                    .setDuration(500)
                    .start();
            mAlreadyIn = true;
        }
    }

    private class LoginRegisterPagerAdapter extends FragmentPagerAdapter {

        public LoginRegisterPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new LoginFragment();
                case 1:
                    return new RegisterFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
