package com.ieugene.customrecyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ieugene.customrecyclerview.adapter.CustomRecyclerAdapter;
import com.ieugene.customrecyclerview.dprecyclerview.CustomImgDecoration;
import com.ieugene.customrecyclerview.dprecyclerview.CustomImgLayoutManager;
import com.ieugene.customrecyclerview.dprecyclerview.CustomRecyclerView;

import java.util.Locale;

public class TestRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this);
//        recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(new DebugAdapter());
        DividerItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
    }

    private static class DebugAdapter extends RecyclerView.Adapter<DebugHolder> {

        @NonNull
        @Override
        public DebugHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DebugHolder(new TextView(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(@NonNull DebugHolder holder, int position) {
            holder.textView.setText(String.format(Locale.getDefault(), "Position: %d", position));
        }

        @Override
        public int getItemCount() {
            return 10;
        }
    }

    private static class DebugHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public DebugHolder(@NonNull View itemView) {
            super(itemView);
            textView = (TextView) itemView;
        }
    }

    private static class Adapter extends RecyclerView.Adapter<ItemHolder> {
        Context context;

        public Adapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ItemHolder(LayoutInflater.from(this.context).inflate(R.layout.item_test_recyclerview, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            holder.recyclerView.setAdapter(new CustomRecyclerAdapter(context, position + 1));
        }

        @Override
        public int getItemCount() {
            return 9;
        }
    }

    private static class ItemHolder extends RecyclerView.ViewHolder {
        CustomRecyclerView recyclerView;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.custom_recyclerview);
            recyclerView.addItemDecoration(new CustomImgDecoration(30, 30));
            recyclerView.setLayoutManager(new CustomImgLayoutManager());
        }

    }
}
