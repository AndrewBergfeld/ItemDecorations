package com.example.andrew.itemdecorators;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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
        mList.addItemDecoration(new PartitioningItemDecorator(this, 50, 10, 15, android.R.color.primary_text_dark,
                new PartitioningItemDecorator.Partitioner() {
                    @Override
                    public boolean hasHeader(View view, int position) {
                        return position % 5 == 0;
                    }

                    @Override
                    public String getHeaderTextForPosition(View view, int position) {
                        return "Header Title " + position;
                    }
                }));
        mList.setAdapter(new DummyAdapter());
    }


    private class DummyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dummy, parent, false));
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        }

        @Override
        public int getItemCount() {
            return 100;
        }
    }
}
