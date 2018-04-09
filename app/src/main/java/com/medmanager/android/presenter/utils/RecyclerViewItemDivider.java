package com.medmanager.android.presenter.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.medmanager.android.R;


/**
 * Created by ILENWABOR DAVID on 23/03/2018.
 * This class is used to provide a line divider between items of a recycler view.
 */

public class RecyclerViewItemDivider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;

    public RecyclerViewItemDivider(Context context) {
        mDivider = ContextCompat.getDrawable(context, R.drawable.recycler_view_divider);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}

