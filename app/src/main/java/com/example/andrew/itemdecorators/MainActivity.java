package com.example.andrew.itemdecorators;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainActivity extends Activity {

    private RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mList = (RecyclerView) findViewById(R.id.recycler_view);
        mList.setLayoutManager(new LinearLayoutManager(this));
        mList.addItemDecoration(new AwesomeItemDecorator());
        mList.setAdapter(new DummyAdapter());
    }

    private class AwesomeItemDecorator extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildPosition(view);

            if (matchesRule(position)) {
                outRect.top += TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, getResources().getDisplayMetrics());
            }
        }

        @Override
        public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
            float paddingTop = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
            float paddingRight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            Rect rekt = new Rect();
            View view;
            TextPaint paint = new TextPaint(TextPaint.ANTI_ALIAS_FLAG | TextPaint.DITHER_FLAG );
            paint.setColor(getResources().getColor(android.R.color.primary_text_dark));
            paint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));

            for (int i = 0; i < parent.getChildCount(); i++) {
                view = parent.getChildAt(i);

                if (matchesRule(parent.getChildPosition(view))) {
                    //get rekt
                    view.getDrawingRect(rekt);

                    canvas.drawText("Title Header " + parent.getChildPosition(view),
                            view.getLeft() + paddingRight,
                            view.getTop() - paddingTop,
                            paint);
                }
            }
        }

        private boolean matchesRule(int position) {
            //Some arbitrary grouping rule
            return position % 5 == 0;
        }
    }

    private class DummyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private class ViewHolder extends RecyclerView.ViewHolder {
            private int mPosition;

            public ViewHolder(View itemView) {
                super(itemView);
            }

            public void setPosition(int position) {
                mPosition = position;
            }

        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dummy, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((ViewHolder) holder).setPosition(position);
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}
