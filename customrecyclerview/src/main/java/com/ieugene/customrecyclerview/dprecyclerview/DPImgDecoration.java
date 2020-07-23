package com.ieugene.customrecyclerview.dprecyclerview;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DPImgDecoration extends RecyclerView.ItemDecoration {
    private int decorationWidth, decorationHeight;
    private Drawable drawable;

    public DPImgDecoration() {
    }

    public DPImgDecoration(int decorationWidth, int decorationHeight) {
        this.decorationWidth = decorationWidth;
        this.decorationHeight = decorationHeight;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public void setDecorationHeight(int decorationHeight) {
        this.decorationHeight = decorationHeight;
    }

    public void setDecorationWidth(int decorationWidth) {
        this.decorationWidth = decorationWidth;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int width = 0, height = 0;
        if (drawable == null) {
            width = this.decorationWidth;
            height = this.decorationHeight;
        } else {
            width = drawable.getIntrinsicWidth();
            height = drawable.getIntrinsicHeight();
        }
        outRect.set(0, 0, width, height);
    }
}
