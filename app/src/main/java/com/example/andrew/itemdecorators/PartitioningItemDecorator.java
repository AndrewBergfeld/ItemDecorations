package com.example.andrew.itemdecorators;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.TypedValue;
import android.view.View;

public class PartitioningItemDecorator extends RecyclerView.ItemDecoration  {

    private TextPaint mTextPaint;
    private float mVerticalOffset;
    private float mHorizontalOffset;
    private float mHeaderHeight;
    private Partitioner mPartitioner;

    public PartitioningItemDecorator(Context context, int headerHeight, int headerLeftOffset, int textSize, int textColor, Partitioner partitioner) {
        int verticalOffset = headerHeight / 2 - textSize / 2;

        Resources resources = context.getResources();
        
        mPartitioner = partitioner;
        mVerticalOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, verticalOffset, resources.getDisplayMetrics());
        mHorizontalOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, headerLeftOffset, resources.getDisplayMetrics());
        mHeaderHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, headerHeight, resources.getDisplayMetrics());

        mTextPaint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG | TextPaint.DITHER_FLAG );
        mTextPaint.setColor(resources.getColor(textColor));
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, textSize, resources.getDisplayMetrics()));

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildPosition(view);

        if (mPartitioner.hasHeader(view, position)) {
            outRect.top += mHeaderHeight;
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        View view;
        int childPosition;
        for (int i = 0; i < parent.getChildCount(); i++) {
            view = parent.getChildAt(i);
            childPosition = parent.getChildPosition(view);
            
            if (mPartitioner.hasHeader(view, childPosition)) {
                canvas.drawText(mPartitioner.getHeaderTextForPosition(view, childPosition),
                        view.getLeft() + mHorizontalOffset,
                        view.getTop() - mVerticalOffset,
                        mTextPaint);
            }
        }
    }

    public interface Partitioner {
        boolean hasHeader(View view, int position);
        String getHeaderTextForPosition(View view, int position);
    }
    
}
