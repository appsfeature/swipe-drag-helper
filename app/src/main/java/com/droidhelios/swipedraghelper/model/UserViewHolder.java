package com.droidhelios.swipedraghelper.model;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidhelios.swipedraghelper.R;


/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class UserViewHolder extends RecyclerView.ViewHolder {

    public ImageView userAvatar;
    public TextView username;
    public ImageView reorderView;

    public UserViewHolder(View itemView) {
        super(itemView);

        userAvatar = itemView.findViewById(R.id.imageview_profile);
        username = itemView.findViewById(R.id.textview_name);
        reorderView = itemView.findViewById(R.id.imageview_reorder);
    }
}
