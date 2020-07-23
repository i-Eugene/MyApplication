package com.ieugene.customrecyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ieugene.customrecyclerview.R;


public class CustomRecyclerAdapter extends RecyclerView.Adapter<CustomRecyclerAdapter.ItemViewHolder> {

    Context context;
    int count;

    public CustomRecyclerAdapter(Context context, int count) {
        this.context = context;
        this.count = count;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_custom_recyclerview, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        if (count == 1) {
            ViewGroup.LayoutParams params = holder.imageView.getLayoutParams();
            if (params == null) {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
            }
            params.height = 200;
            holder.imageView.setLayoutParams(params);
        }
        holder.imageView.setImageResource(com.ieugene.resourselibrary.R.drawable.timg);
    }

    @Override
    public int getItemCount() {
        return count;
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
