package com.example.ourwardrobe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UsersAdapterVh> {

    private List<UserModel> userModelList;
    private Context context;

    public RecyclerViewAdapter(List<UserModel> userModelList) {
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
        UserModel userModel = userModelList.get(position);

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
