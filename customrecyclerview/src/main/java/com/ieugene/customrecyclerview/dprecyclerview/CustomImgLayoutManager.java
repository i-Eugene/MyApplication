package com.ieugene.customrecyclerview.dprecyclerview;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class CustomImgLayoutManager extends RecyclerView.LayoutManager {

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
        View[] children = addChildren(recycler, count);
        calculateDefaultValue(recycler, count);
        for (int i = 0; i < count; i++) {
            children[i] = measureChildren(children, count, i);
        }
        if (count == 1) {
            height = getSingleHeight(children[0]);
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

    private View[] addChildren(@NonNull RecyclerView.Recycler recycler, int count) {
        View[] children = new View[count];
        for (int i = 0; i < count; i++) {
            children[i] = recycler.getViewForPosition(i);
            addView(children[i]);
        }
        return children;
    }

    private int getSingleHeight(View child) {
        int height;
        ViewGroup.LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        if (child.getMeasuredHeight() > singleMaxHeight) {
            height = singleMaxHeight;
        } else if (child.getMeasuredHeight() < singleMinHeight) {
            height = singleMinHeight;
        } else {
            height = child.getMeasuredHeight();
        }
        params.height = height;
        child.setLayoutParams(params);
        return height;
    }

    private void calculateDefaultValue(@NonNull RecyclerView.Recycler recycler, int itemCount) {
        int pos = 0;
        Rect rect = new Rect();
        calculateItemDecorationsForChild(recycler.getViewForPosition(pos), rect);
        decorationW = rect.width();
        decorationH = rect.height();
        if (decorationW == 0 && itemCount > 2) {
            pos += 1;
            calculateItemDecorationsForChild(recycler.getViewForPosition(pos), rect);
            decorationW = rect.width();
        }
        if (decorationH == 0 && itemCount > 2) {
            pos += 1;
            calculateItemDecorationsForChild(recycler.getViewForPosition(pos), rect);
            decorationH = rect.height();
        }

        twoColumn = (width - decorationW) / 2;
        threeColumn = (width - decorationW * 2) / 3;
        singleMaxHeight = twoColumn + (threeColumn + decorationH) * 3;
        singleMinHeight = twoColumn;
        Log.d(getClass().getSimpleName(), "itemCount=" + itemCount + ", singleMinHeight=" + singleMinHeight + ", decorationW=" + decorationW + ", decorationH=" + decorationH + ", twoColumn=" + twoColumn + ", threeColumn=" + threeColumn + ", width=" + width);
    }

    private View measureChildren(View[] children, int count, int index) {
        View child = children[index];
        int widthUsed = 0, heightUsed = 0;
        if (count == 2) {
            widthUsed = index == 0 ? twoColumn : twoColumn + decorationW;
        } else if (count == 3) {
            if (index == 0) {
                widthUsed = threeColumn;
            } else if (index == 1) {
                widthUsed = 2 * (threeColumn + decorationW);
                heightUsed = threeColumn;
            } else {
                widthUsed = 2 * (threeColumn + decorationW);
                heightUsed = threeColumn + decorationH;
            }
        } else if (count == 4) {
            if (index == 0) {
                heightUsed = threeColumn;
            } else {
                widthUsed = 2 * threeColumn + decorationW;
                if (index == 3) widthUsed += decorationW;
                heightUsed = twoColumn + decorationH;
            }
        } else if (count == 5) {
            if (index < 2) {
                widthUsed = index == 0 ? twoColumn : twoColumn + decorationW;
                heightUsed = index == 0 ? threeColumn + decorationH : threeColumn;
            } else {
                widthUsed = 2 * threeColumn + decorationW;
                if (index == 4) widthUsed += decorationW;
                heightUsed = twoColumn + decorationH;
            }
        } else if (count == 6) {
            if (index == 0) {
                widthUsed = threeColumn;
                heightUsed = threeColumn;
            } else if (index == 1 || index == 2) {
                widthUsed = 2 * threeColumn + 2 * decorationW;
                heightUsed = 2 * threeColumn + decorationH;
            } else {
                widthUsed = 2 * threeColumn + decorationW;
                if (index == 5) widthUsed += decorationW;
                heightUsed = 2 * threeColumn + 2 * decorationH;
            }
        } else if (count == 7) {
            if (index == 0) {
                heightUsed = threeColumn * 2;
            } else {
                widthUsed = 2 * threeColumn + decorationW;
                if (index % 3 == 0) widthUsed += decorationW;
                heightUsed = threeColumn + twoColumn + decorationH + ((index - 1) / 3) * decorationH;
            }
        } else if (count == 8) {
            if (index < 2) {
                widthUsed = index == 0 ? twoColumn : twoColumn + decorationW;
                heightUsed = threeColumn * 2 + decorationH;
            } else {
                widthUsed = 2 * threeColumn + decorationW;
                if ((index - 1) % 3 == 0) widthUsed += decorationW;
                heightUsed = threeColumn + twoColumn + decorationH + ((index - 2) / 3) * decorationH;
            }
        } else if (count == 9) {
            widthUsed = 2 * threeColumn + decorationW;
            if ((index + 1) % 3 == 0) {
                widthUsed += decorationW;
            }
            heightUsed = 2 * threeColumn + decorationH;
            if (index / 3 == 2) {
                heightUsed += decorationH;
            }

        }
        measureChildWithMargins(child, widthUsed, heightUsed);
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
            layoutSingleView(recycler, state, itemCount);
        } else if (itemCount == 2) {
            layout2View(recycler, state, itemCount);
        } else if (itemCount == 3) {
            layout3View(recycler, state, itemCount);
        } else if (itemCount == 4) {
            layout4View(recycler, state, itemCount);
        } else if (itemCount == 5) {
            layout5View(recycler, state, itemCount);
        } else if (itemCount == 6) {
            layout6View(recycler, state, itemCount);
        } else if (itemCount == 7) {
            layout7View(recycler, state, itemCount);
        } else if (itemCount == 8) {
            layout8View(recycler, state, itemCount);
        } else if (itemCount == 9) {
            layout9View(recycler, state, itemCount);
        }
    }

    private void layoutSingleView(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        View child = measureChildren(children, itemCount, 0);
        int h = getSingleHeight(child);
        layoutDecoratedWithMargins(child, 0, 0, 2 * (twoColumn + decorationH), h + decorationH);
    }

    private void layout2View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 2; i++) {
            View child = measureChildren(children, itemCount, i);
            int right = (i + 1) * twoColumn + decorationW;
            layoutDecoratedWithMargins(child,
                    i * (twoColumn + decorationW),
                    0,
                    right,
                    twoColumn + decorationH);
        }
    }

    private void layout3View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 3; i++) {
            View child = measureChildren(children, itemCount, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0,
                        (threeColumn + decorationH) * 2,
                        (threeColumn) * 2 + decorationH);
            } else {
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * 2,
                        (i - 1) * (threeColumn + decorationH),
                        (threeColumn) * 3 + decorationW * 2,
                        i * (threeColumn) + decorationH);
            }
        }
    }

    private void layout4View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 4; i++) {
            View child = measureChildren(children, itemCount, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0, (twoColumn) * 2 + decorationW, twoColumn + decorationH);
            } else {
                int right = (threeColumn + decorationW) * i;
                if (i == 3) {
                    right -= decorationW;
                }
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 1),
                        twoColumn + decorationH,
                        right,
                        threeColumn + twoColumn + decorationH);
            }
        }
    }

    private void layout5View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 5; i++) {
            View child = measureChildren(children, itemCount, i);
            int right = 0;
            int bottom = 0;
            if (i < 2) {
                right = (i + 1) * (twoColumn + decorationW);
                bottom = twoColumn;
                if (i == 1) {
                    right -= decorationW;
                    bottom += decorationH;
                }
                layoutDecoratedWithMargins(child,
                        i * (twoColumn + decorationW),
                        0,
                        right,
                        bottom);
            } else {
                right = (threeColumn + decorationW) * (i - 1);
                if (i == 4) {
                    right -= decorationW;
                }
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 2),
                        twoColumn + decorationH,
                        right,
                        threeColumn + twoColumn + decorationH);
            }
        }
    }

    private void layout6View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 6; i++) {
            View child = measureChildren(children, itemCount, i);
            int right = 0, bottom = 0;
            if (i < 3) {
                if (i == 0) {
                    layoutDecoratedWithMargins(child, 0, 0,
                            (threeColumn + decorationH) * 2,
                            (threeColumn + decorationH) * 2);
                } else {
                    layoutDecoratedWithMargins(child,
                            (threeColumn + decorationW) * 2,
                            (i - 1) * (threeColumn + decorationH),
                            (threeColumn) * 3 + decorationW * 2,
                            i * (threeColumn + decorationH));
                }
            } else {
                right = (threeColumn + decorationW) * (i - 2);
                if (i == 5) {
                    right -= decorationW;
                }
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * (i - 3),
                        (threeColumn + decorationH) * 2,
                        right,
                        (threeColumn) * 3 + decorationH * 2);
            }
        }
    }

    private void layout7View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 7; i++) {
            View child = measureChildren(children, itemCount, i);
            if (i == 0) {
                layoutDecoratedWithMargins(child, 0, 0, (twoColumn) * 2 + decorationW, twoColumn + decorationH);
            } else {
                int right = (threeColumn + decorationW) * ((i - 1) % 3) + threeColumn + decorationW;
                if (i % 3 == 0) {
                    right -= decorationW;
                }
                int bottom = twoColumn + ((threeColumn + decorationH) * ((i - 1) / 3)) + threeColumn + decorationH * 2;
                if ((i - 1) / 3 > 0) {
                    bottom -= decorationH;
                }
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * ((i - 1) % 3),
                        twoColumn + decorationH + ((threeColumn + decorationH) * ((i - 1) / 3)),
                        right,
                        bottom);
            }
        }
    }

    private void layout8View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 8; i++) {
            View child = measureChildren(children, itemCount, i);
            int right = 0, bottom = 0;
            if (i < 2) {
                right = (i + 1) * (twoColumn + decorationW);
                if (i == 1) right -= decorationW;
                layoutDecoratedWithMargins(child,
                        i * (twoColumn + decorationW),
                        0,
                        right,
                        twoColumn + decorationH);

            } else {
                right = (threeColumn + decorationW) * ((i - 2) % 3) + threeColumn + decorationW;
                bottom = twoColumn + ((threeColumn + decorationH) * ((i - 2) / 3)) + threeColumn + decorationH * 2;
                if (i == 4 || i == 7) {
                    right -= decorationW;
                }
                if (i > 4) {
                    bottom -= decorationH;
                }
                layoutDecoratedWithMargins(child,
                        (threeColumn + decorationW) * ((i - 2) % 3),
                        twoColumn + decorationH + ((threeColumn + decorationH) * ((i - 2) / 3)),
                        right,
                        bottom);
            }
        }
    }

    private void layout9View(RecyclerView.Recycler recycler, RecyclerView.State state, int itemCount) {
        View[] children = addChildren(recycler, itemCount);
        for (int i = 0; i < 9; i++) {
            View child = measureChildren(children, itemCount, i);
            int right = (threeColumn + decorationW) * ((i) % 3) + threeColumn + decorationW;
            int bottom = ((threeColumn + decorationH) * ((i) / 3)) + threeColumn + decorationH;
            if ((i + 1) % 3 == 0) {
                right -= decorationW;
            }
            if (i > 5) {
                bottom -= decorationH;
            }
            layoutDecoratedWithMargins(child,
                    (threeColumn + decorationW) * ((i) % 3),
                    ((threeColumn + decorationH) * ((i) / 3)),
                    right,
                    bottom);
        }
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.MATCH_PARENT);
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
