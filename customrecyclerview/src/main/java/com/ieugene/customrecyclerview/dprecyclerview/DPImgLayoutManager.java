package com.ieugene.customrecyclerview.dprecyclerview;

import android.graphics.Rect;
import android.view.View.MeasureSpec;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DPImgLayoutManager extends RecyclerView.LayoutManager {

    private int singleMaxHeight, singleMinHeight;
    private int twoColumn, threeColumn;
    private int decorationW, decorationH;
    private int width;

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        width = measureWidth(widthSpec);
        int height = measureHeight(recycler, heightSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int defaultWidth = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.UNSPECIFIED:
                throw new RuntimeException("DPImageRecyclerView width not support this mode!");
        }
        return defaultWidth;
    }

    private int measureHeight(@NonNull RecyclerView.Recycler recycler, int measureSpec) {
        int defaultHeight = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                defaultHeight = getAtMostHeight(recycler);
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
        }
        return defaultHeight;
    }

    private int getAtMostHeight(@NonNull RecyclerView.Recycler recycler) {
        int height = 0;
        int count = getItemCount();

        View[] child = new View[count];
        for (int i = 0; i < count; i++) {
            child[i] = getChildView(recycler, i);
        }

        calculateDefaultValue(recycler, count);

        if (count == 1) {
            if (child[0].getMeasuredHeight() > singleMaxHeight) {
                height = singleMaxHeight;
            } else if (child[0].getMeasuredHeight() < singleMinHeight) {
                height = singleMinHeight;
            } else {
                height = child[0].getMeasuredHeight();
            }
        } else if (count == 2) {
            height = twoColumn;
        } else if (count == 3) {
            height = threeColumn * 2 + decorationH;
        } else if (count == 4 || count == 5) {
            height = twoColumn + threeColumn + decorationH;
        } else if (count == 6 || count == 9) {
            height = threeColumn * 3 + decorationH * 2;
        } else if (count == 7 || count == 8) {
            height = twoColumn + threeColumn * 2 + decorationH * 2;
        }
        return height + getPaddingTop() + getPaddingBottom();
    }

    private void calculateDefaultValue(@NonNull RecyclerView.Recycler recycler, int itemCount) {
        int pos = 0;
        if (itemCount == 4 || itemCount == 7) {
            pos = 1;
        }
        Rect rect = new Rect();
        calculateItemDecorationsForChild(recycler.getViewForPosition(pos), rect);

        decorationW = rect.width();
        decorationH = rect.height();
        twoColumn = (width - decorationW) / 2;
        threeColumn = (width - decorationW * 2) / 3;
        singleMaxHeight = twoColumn + (threeColumn + decorationH) * 3;
        singleMinHeight = twoColumn;
    }

    private View getChildView(RecyclerView.Recycler recycler, int index) {
        View child = recycler.getViewForPosition(index);
        addView(child);
        measureChildWithMargins(child, 0, 0);
        return child;
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (getChildCount() == 0 && state.isPreLayout()) {
            return;
        }
        detachAndScrapAttachedViews(recycler);
        layoutView(recycler, state, getItemCount());
    }

    private void layoutView(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        if (itemCount == 1) {
            layoutSingleView(recycler, state);
        } else if (itemCount == 2) {
            layout2View(recycler, state);
        } else if (itemCount == 3) {
            layout3View(recycler, state);
        } else if (itemCount == 4) {
            layout4View(recycler, state);
        } else if (itemCount == 5) {
            layout5View(recycler, state);
        } else if (itemCount == 6) {
            layout6View(recycler, state);
        } else if (itemCount == 7) {
            layout7View(recycler, state);
        } else if (itemCount == 8) {
            layout8View(recycler, state);
        } else if (itemCount == 9) {
            layout9View(recycler, state);
        }
    }

    private void layoutSingleView(RecyclerView.Recycler recycler, RecyclerView.State state) {
        View child = getChildView(recycler, 0);
        int h;
        if (child.getMeasuredHeight() > singleMaxHeight) {
            h = singleMaxHeight;
        } else if (child.getMeasuredHeight() < singleMinHeight) {
            h = singleMinHeight;
        } else {
            h = child.getMeasuredHeight();
        }
        layoutDecoratedWithMargins(child, 0, 0, 2 * (twoColumn + decorationH), h + decorationH);
    }

    private void layout2View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 2; i++) {
            View child = getChildView(recycler, i);
            layoutDecoratedWithMargins(child,
                    i * (twoColumn + decorationW),
                    0,
                    (i + 1) * (twoColumn + decorationW),
                    twoColumn + decorationH);
        }
    }

    private void layout3View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 3; i++) {
            View child = getChildView(recycler, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0,
                        (threeColumn + decorationH) * 2,
                        (threeColumn + decorationH) * 2);
            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * 2,
                        (i - 1) * (threeColumn + decorationH),
                        (threeColumn + decorationW) * 3,
                        i * (threeColumn + decorationH));
            }
        }
    }

    private void layout4View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 4; i++) {
            View child = getChildView(recycler, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0, (twoColumn + decorationW) * 2, twoColumn + decorationH);
            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 1),
                        twoColumn + decorationH,
                        (threeColumn + decorationW) * i,
                        threeColumn + twoColumn + 2 * decorationH);
            }
        }
    }

    private void layout5View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 5; i++) {
            View child = getChildView(recycler, i);
            if (i < 2) {
                layoutDecoratedWithMargins(child,
                        i * (twoColumn + decorationW),
                        0,
                        (i + 1) * (twoColumn + decorationW),
                        twoColumn + decorationH);

            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 2),
                        twoColumn + decorationH,
                        (threeColumn + decorationW) * (i - 1),
                        threeColumn + twoColumn + 2 * decorationH);
            }
        }
    }

    private void layout6View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 6; i++) {
            View child = getChildView(recycler, i);
            if (i < 3) {
                if (i == 0) {
                    layoutDecoratedWithMargins(child, 0, 0,
                            (threeColumn + decorationH) * 2,
                            (threeColumn + decorationH) * 2);
                } else {
                    layoutDecoratedWithMargins(child,
                            (threeColumn + decorationW) * 2,
                            (i - 1) * (threeColumn + decorationH),
                            (threeColumn + decorationW) * 3,
                            i * (threeColumn + decorationH));
                }
            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 3),
                        (threeColumn + decorationH) * 2,
                        (threeColumn + decorationW) * (i - 2),
                        (threeColumn + decorationH) * 3);
            }
        }
    }

    private void layout7View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 7; i++) {
            View child = getChildView(recycler, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0, (twoColumn + decorationW) * 2, twoColumn + decorationH);
            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * ((i - 1) % 3),
                        twoColumn + decorationH + ((threeColumn + decorationH) * ((i - 1) / 3)),
                        (threeColumn + decorationW) * ((i - 1) % 3) + threeColumn + decorationW,
                        twoColumn + ((threeColumn + decorationH) * ((i - 1) / 3)) + threeColumn + decorationH * 2);
            }
        }
    }

    private void layout8View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 8; i++) {
            View child = getChildView(recycler, i);
            if (i < 2) {
                layoutDecoratedWithMargins(child,
                        i * (twoColumn + decorationW),
                        0,
                        (i + 1) * (twoColumn + decorationW),
                        twoColumn + decorationH);

            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * ((i - 2) % 3),
                        twoColumn + decorationH + ((threeColumn + decorationH) * ((i - 2) / 3)),
                        (threeColumn + decorationW) * ((i - 2) % 3) + threeColumn + decorationW,
                        twoColumn + ((threeColumn + decorationH) * ((i - 2) / 3)) + threeColumn + decorationH * 2);
            }
        }
    }

    private void layout9View(RecyclerView.Recycler recycler, RecyclerView.State state) {
        for (int i = 0; i < 9; i++) {
            View child = getChildView(recycler, i);
            layoutDecoratedWithMargins(child,
                    (threeColumn + decorationW) * ((i) % 3),
                    ((threeColumn + decorationH) * ((i) / 3)),
                    (threeColumn + decorationW) * ((i) % 3) + threeColumn + decorationW,
                    ((threeColumn + decorationH) * ((i) / 3)) + threeColumn + decorationH);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean isAutoMeasureEnabled() {
        return super.isAutoMeasureEnabled();
    }

    @Override
    public boolean canScrollHorizontally() {
        return false;
    }

    @Override
    public boolean canScrollVertically() {
        return false;
    }
}
