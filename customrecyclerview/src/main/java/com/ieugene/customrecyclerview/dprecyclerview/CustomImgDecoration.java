package com.ieugene.customrecyclerview.dprecyclerview;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomImgDecoration extends RecyclerView.ItemDecoration {
    private int decorationWidth, decorationHeight;
    private Drawable drawable;

    public CustomImgDecoration() {
    }

    public CustomImgDecoration(int decorationWidth, int decorationHeight) {
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
        int count = parent.getAdapter() == null ? 0 : parent.getAdapter().getItemCount();
        int pos = parent.getChildAdapterPosition(view);
        if (count == 1) {
            width = 0;
            height = 0;
        } else if (count == 2) {
            if (pos == 1) {
                width = 0;
            }
            height = 0;
        } else if (count == 3) {
            if (pos == 0) {
                height = 0;
            } else if (pos == 1) {
                width = 0;
            } else {
                height = 0;
                width = 0;
            }
        } else if (count == 4) {
            if (pos == 0) {
                width = 0;
            } else if (pos == 3) {
                width = 0;
                height = 0;
            } else {
                height = 0;
            }
        } else if (count == 5) {
            if (pos == 1) {
                width = 0;
            } else if (pos == 4) {
                width = 0;
                height = 0;
            } else {
                height = 0;
            }
        } else if (count == 6) {
            if (pos == 1 || pos == 2) {
                width = 0;
            } else if (pos == 3 || pos == 4) {
                height = 0;
            } else if (pos == 5) {
                height = 0;
                width = 0;
            }
        } else if (count == 7) {
            if (pos == 0 || pos == 3) {
                width = 0;
            } else if (pos == 5 || pos == 4) {
                height = 0;
            } else if (pos == 6) {
                height = 0;
                width = 0;
            }
        } else if (count == 8) {
            if (pos == 1 || pos == 4) {
                width = 0;
            } else if (pos == 5 || pos == 6) {
                height = 0;
            } else if (pos == 7) {
                height = 0;
                width = 0;
            }
        } else if (count == 9) {
            if (pos == 2 || pos == 5) {
                width = 0;
            } else if (pos == 6 || pos == 7) {
                height = 0;
            } else if (pos == 8) {
                width = 0;
                height = 0;
            }
        }
        outRect.set(0, 0, width, height);
    }
}
