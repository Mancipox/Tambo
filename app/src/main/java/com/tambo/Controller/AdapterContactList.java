package com.tambo.Controller;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tambo.Model.Class;
import com.tambo.Model.User;
import com.tambo.R;

import java.util.ArrayList;


public class AdapterContactList extends RecyclerView.Adapter<AdapterContactList.ViewHolder> {

    private ArrayList<User> userInfo;
    private Context mcontex;

    public AdapterContactList(ArrayList<User> userInfo, Context mcontex) {
        this.userInfo = userInfo;
        this.mcontex = mcontex;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.layout_contactlist,parent,false);
        final ViewHolder mViewHolder = new ViewHolder(contactView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        User mUser = userInfo.get(i);
        viewHolder.email.setText(mUser.getEmail());
        viewHolder.phone.setText(mUser.getPhone());
        viewHolder.mImage.setImageResource(R.drawable.boys);
        viewHolder.username.setText(mUser.getUserName());


    }

    @Override
    public int getItemCount() {

        return userInfo.size();
    }
    public void setItem(User mUser){
        this.userInfo.add(mUser);
        this.notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView email;
        TextView username;
        TextView phone;
        ImageView mImage;
        RelativeLayout mRelativeLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            email= itemView.findViewById(R.id.contact_email);
            username=itemView.findViewById(R.id.contact_username);
            phone=itemView.findViewById(R.id.contact_number);
            mImage=itemView.findViewById(R.id.contact_imageview);
            mRelativeLayout= itemView.findViewById(R.id.layout_contact);

        }
    }
}
