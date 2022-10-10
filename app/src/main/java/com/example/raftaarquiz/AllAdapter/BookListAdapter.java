package com.example.raftaarquiz.AllAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.raftaarquiz.Common.DataBaseHelper;
import com.example.raftaarquiz.Model.BookListData;
import com.example.raftaarquiz.R;

import java.util.List;

public class BookListAdapter extends RecyclerView.Adapter<BookListAdapter.MyViewHolder> {
    List<BookListData> list;
    Context context;
    DataBaseHelper dataBaseHelper;

    public BookListAdapter(List<BookListData> list, Context context) {
        this.list = list;
        this.context = context;
        dataBaseHelper=new DataBaseHelper(context);
    }

    @NonNull
    @Override
    public BookListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_list_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookListAdapter.MyViewHolder holder, int position) {
        Glide.with(context)
                .load( list.get(position).getImage())
                .into(holder.cat_Image_View);

        Log.d("Amit","Value Check "+list.get(position).getImage());

        holder.cat_Name.setText("" + list.get(position).getTitle());
        holder.subCategory_name.setText("" + list.get(position).getDescription());
        if(dataBaseHelper.getFavouriteById(list.get(position).getId(),DataBaseHelper.TABLE_FAVOURITE)){
            holder.favrouite.setImageResource(R.drawable.img_over);
        }

        holder.favrouite.setOnClickListener(v -> {
            ContentValues fav = new ContentValues();
            if(dataBaseHelper.getFavouriteById(list.get(position).getId(), DataBaseHelper.TABLE_FAVOURITE)){
                dataBaseHelper.removeFavouriteById(list.get(position).getId(), DataBaseHelper.TABLE_FAVOURITE);
                Toast.makeText(context, "Favourite has been removed", Toast.LENGTH_SHORT).show();
                holder.favrouite.setImageResource(R.drawable.img);
            }
            else{
                fav.put(DataBaseHelper.FAVOURITE_ID, list.get(position).getId());
                fav.put(DataBaseHelper.FAVOURITE_NAME, list.get(position).getTitle());
                fav.put(DataBaseHelper.FAVOURITE_IMAGE, list.get(position).getImage());
                dataBaseHelper.addFavourite(DataBaseHelper.TABLE_FAVOURITE, fav, null);
                holder.favrouite.setImageResource(R.drawable.img_over);
                Toast.makeText(context, "Favourite has been added", Toast.LENGTH_SHORT).show();
            }

        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView cat_Image_View,favrouite;
        TextView cat_Name;
        TextView subCategory_name;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cat_Image_View=itemView.findViewById(R.id.cat_Image_View);
            favrouite=itemView.findViewById(R.id.favrouite);
            cat_Name=itemView.findViewById(R.id.cat_Name);
            subCategory_name=itemView.findViewById(R.id.subCategory_name);

        }
    }
}
