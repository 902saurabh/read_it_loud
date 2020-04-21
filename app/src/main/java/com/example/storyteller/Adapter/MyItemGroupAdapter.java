package com.example.storyteller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.storyteller.Model.ItemData;
import com.example.storyteller.Model.ItemGroup;
import com.example.storyteller.R;

import java.util.List;

public class MyItemGroupAdapter extends RecyclerView.Adapter<MyItemGroupAdapter.MyViewHolder> {

    private Context context;
    private List<ItemGroup> dataList;

    public MyItemGroupAdapter(Context context, List<ItemGroup> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context).inflate(R.layout.layout_group,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {


        holder.item_title.setText(dataList.get(position).getHeaderTitle());
        List<ItemData> itemData = dataList.get(position).getListItem();
        MyItemAdapter itemListAdapter = new MyItemAdapter(context,itemData);

        holder.recycler_view_item_list.setHasFixedSize(true);
        holder.recycler_view_item_list.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false));

        holder.recycler_view_item_list.setAdapter(itemListAdapter);
        holder.recycler_view_item_list.setNestedScrollingEnabled(false);

        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Button More: "+holder.item_title.getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return (dataList!= null ? dataList.size():0);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView item_title;
        RecyclerView recycler_view_item_list;
        Button btn_more;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_title = (TextView) itemView.findViewById(R.id.itemTitle);
            btn_more = (Button) itemView.findViewById(R.id.btnMore);
            recycler_view_item_list = (RecyclerView) itemView.findViewById(R.id.recycle_view_list);
        }
    }
}
