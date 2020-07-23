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

import com.ieugene.customrecyclerview.adapter.CustomRecyclerAdapter;
import com.ieugene.customrecyclerview.dprecyclerview.CustomImgDecoration;
import com.ieugene.customrecyclerview.dprecyclerview.CustomImgLayoutManager;
import com.ieugene.customrecyclerview.dprecyclerview.CustomRecyclerView;

public class TestRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_recycler_view);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);
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
            recyclerView.addItemDecoration(new CustomImgDecoration(30,30));
            recyclerView.setLayoutManager(new CustomImgLayoutManager());
        }

    }
}
