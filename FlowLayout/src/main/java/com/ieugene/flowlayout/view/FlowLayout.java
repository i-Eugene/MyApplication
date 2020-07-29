package com.ieugene.flowlayout.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

public class FlowLayout extends ViewGroup {

    private int spaceH, spaceV;

    private BaseFlowAdapter adapter;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    public void setAdapter(BaseFlowAdapter adapter) {
        this.adapter = adapter;
        if (adapter != null) {
            int childCount = getChildCount();
            int adapterCount = adapter.getItemCount();
            if (adapterCount > 0) {
                int i;
                for (i = 0; i < adapterCount; i++) {
                    View child;
                    if (i < childCount) {
                        child = getChildAt(i);
                    } else {
                        child = adapter.onCreateChild(this, i);
                        addView(child);
                    }
                    adapter.onBindChild(child, i);
                }
                if (childCount > adapterCount) {
                    removeViews(i, childCount - adapterCount);
                }
            } else {
                removeAllViews();
            }
        }
    }

    public BaseFlowAdapter getAdapter() {
        return adapter;
    }

    public static abstract class BaseFlowAdapter {

        abstract int getItemCount();

        abstract View onCreateChild(View parent, int position);

        abstract void onBindChild(View child, int position);
    }
}
