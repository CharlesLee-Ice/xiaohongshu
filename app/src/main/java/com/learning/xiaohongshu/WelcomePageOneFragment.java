package com.learning.xiaohongshu;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

/**
 * Created by liqilin on 2016/12/12.
 */

public class WelcomePageOneFragment extends WelcomeBaseFragment {

    private int PHONE_CONTENT_TOP_MARGIN_PIXELS = 150;
    private int PHONE_CONTENT_BUTTON_MARGIN_PIXELS = 85;
    private ImageView mImagePhone;
    private ImageView mImagePhoneContent;

    private ScrollView mPhoneContentScroll;
    private int mImagePhoneRealHeight;
    private int mImagePhoneContentTotalHeight;
    private float mImageScaleRatio;

    private static WelcomePageOneFragment sWeclomePageOne;
    public static WelcomePageOneFragment newInstance() {
        if (sWeclomePageOne == null) {
            sWeclomePageOne = new WelcomePageOneFragment();
        }
        return sWeclomePageOne;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.welcome_page_one, container, false);
        mImagePhone = (ImageView) layout.findViewById(R.id.image_phone);
        mImagePhoneContent = (ImageView) layout.findViewById(R.id.image_phone_content);

        int destPhoneWidth = getPhoneBitmapRealWidth();

        Bitmap phone = BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_phone);
        Bitmap scalePhone = bitmapScaleWithWidth(destPhoneWidth, phone);
        mImagePhoneRealHeight = scalePhone.getHeight();
        mImagePhone.setScaleType(ImageView.ScaleType.MATRIX);
        mImagePhone.setImageBitmap(scalePhone);

        Bitmap phoneContent = BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_frist_scrollbg);
        Bitmap sizedPhoneContent = bitmapScaleWithWidth(destPhoneWidth, phoneContent);
        mImagePhoneContent.setScaleType(ImageView.ScaleType.MATRIX);
        mImagePhoneContent.setImageBitmap(sizedPhoneContent);

        mPhoneContentScroll = (ScrollView) layout.findViewById(R.id.scroll_phone_content);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mPhoneContentScroll.getLayoutParams();
        mImageScaleRatio = getPhoneScaleRatio(destPhoneWidth);
        int marginTopOffset = (int) (mImageScaleRatio * PHONE_CONTENT_TOP_MARGIN_PIXELS);
        int marginBottom = getPhoneContentBottomMargin();
        layoutParams.topMargin += marginTopOffset;
        layoutParams.bottomMargin = marginBottom > 0 ? marginBottom : 0;
        mPhoneContentScroll.setLayoutParams(layoutParams);

        mImagePhoneContentTotalHeight = sizedPhoneContent.getHeight();
        return layout;
    }

    @Override
    public void playAnimation() {
        ObjectAnimator animator = ObjectAnimator.ofInt(mPhoneContentScroll, "scrollY", 0, mImagePhoneContentTotalHeight);
        animator.setInterpolator(new FastOutSlowInInterpolator());
        animator.setDuration(2000);
        animator.start();
    }

    protected Bitmap bitmapScaleWithWidth(int destWidth, Bitmap bitmap) {
        float ratio = destWidth / (float)bitmap.getWidth();
        Matrix matrix = new Matrix();
        matrix.postScale(ratio, ratio);
        Bitmap destBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return destBitmap;
    }

    protected int getPhoneBitmapRealWidth() {
        int screenWidth = DisplayUtils.getDisplayWidthPixels(getActivity());
        return screenWidth - (int)(2 * getResources().getDimension(R.dimen.phone_margin));
    }

    private float getPhoneScaleRatio(int destWidth) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        BitmapFactory.decodeResource(getResources(), R.drawable.welcomeanim_phone, options);
        return destWidth / (float) options.outWidth;
    }

    private int getPhoneContentBottomMargin() {
        int total = DisplayUtils.getDisplayHeightPixels(getActivity());
        int topMargin = (int) getResources().getDimension(R.dimen.phone_margin);
        int left = total - topMargin - mImagePhoneRealHeight;
        if (left <= 0) {
            return 0;
        }
        return left + (int) (mImageScaleRatio * PHONE_CONTENT_BUTTON_MARGIN_PIXELS);
    }
}
