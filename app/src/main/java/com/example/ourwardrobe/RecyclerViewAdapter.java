package com.example.ourwardrobe;

import android.annotation.SuppressLint;
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

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UsersAdapterVh> {

    private List<userModel> userModelList;
    private Context context;

    public RecyclerViewAdapter(List<userModel> userModelList) {
        this.userModelList = userModelList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.layout_listitem, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.UsersAdapterVh holder, int position) {
        userModel userModel = userModelList.get(position);

        String username = userModel.getUserName();
        String prefix = userModel.getUserName().substring(0,1);

        holder.tvUsername.setText(username);
        holder.tvPrefix.setText(prefix);
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public class UsersAdapterVh extends RecyclerView.ViewHolder {

        TextView tvPrefix;
        TextView tvUsername;
        ImageView imIcon;
        public UsersAdapterVh(@NonNull View itemView) {
            super(itemView);
            tvPrefix = itemView.findViewById(R.id.prefix);
            tvUsername = itemView.findViewById(R.id.userText);
        }
    }
}
