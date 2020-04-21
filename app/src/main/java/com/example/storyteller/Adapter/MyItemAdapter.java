package com.example.storyteller.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storyteller.DetailsActivity;
import com.example.storyteller.Interface.IItemClickListener;
import com.example.storyteller.LoginActivity;
import com.example.storyteller.MainActivity;
import com.example.storyteller.Model.ItemData;
import com.example.storyteller.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyItemAdapter extends RecyclerView.Adapter<MyItemAdapter.MyViewHolder> {

    private Context context;
    private List<ItemData> itemDataList;

    public MyItemAdapter(Context context, List<ItemData> itemDataList) {
        this.context = context;
        this.itemDataList = itemDataList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_item_title.setText(itemDataList.get(position).getName());
        Picasso.get().load(itemDataList.get(position).getImage()).into(holder.img_item);

        holder.setiItemClickListener(new IItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                context.startActivity(new Intent(new Intent(context, DetailsActivity.class)));
                Toast.makeText(context, ""+itemDataList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return (itemDataList!=null?itemDataList.size():0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView txt_item_title;
        ImageView img_item;

        IItemClickListener iItemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_item_title = (TextView) itemView.findViewById(R.id.imgTitle);
            img_item = (ImageView) itemView.findViewById(R.id.itemImage);

            itemView.setOnClickListener(this);
        }



        public void setiItemClickListener(IItemClickListener iItemClickListener){
            this.iItemClickListener = iItemClickListener;
        }

        @Override
        public void onClick(View view) {
            iItemClickListener.onItemClickListener(view,getAdapterPosition());
        }
    }
}
