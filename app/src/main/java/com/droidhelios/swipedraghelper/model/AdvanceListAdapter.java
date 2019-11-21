package com.droidhelios.swipedraghelper.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.droidhelios.swipedraghelper.R;
import com.droidhelios.swipedrag.SwipeDragHelper;
import com.droidhelios.swipedrag.interfaces.SwipeDragActionListener;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class AdvanceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        SwipeDragActionListener {
    private static final int USER_TYPE = 1;
    private static final int HEADER_TYPE = 2;
    private final Context context;
    private List<User> usersList;

    public AdvanceListAdapter(Context context) {
        this.context= context;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case USER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_item_two, parent, false);
                return new SecondViewHolder(view);
            case HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_item_two, parent, false);
                return new SecondViewHolder(view);
        }
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        int itemViewType = getItemViewType(position);
        if (itemViewType == USER_TYPE) {
            SecondViewHolder viewHolder = (SecondViewHolder) holder;
            viewHolder.username.setText(usersList.get(position).getName());
            Glide.with(holder.itemView).load(usersList.get(position).getImageUrl()).into(viewHolder.userAvatar);

            if(isActiveChangePosition && changeItemViewHolder!=null){
                startDragAnimation(changeItemViewHolder.itemView);
            }
        } else {
            SectionHeaderViewHolder headerViewHolder = (SectionHeaderViewHolder) holder;
            headerViewHolder.sectionTitle.setText(usersList.get(position).getType());
        }
    }

    @Override
    public int getItemCount() {
        return usersList == null ? 0 : usersList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (TextUtils.isEmpty(usersList.get(position).getName())) {
            return HEADER_TYPE;
        } else {
            return USER_TYPE;
        }
    }

    public void setUserList(List<User> usersList) {
        this.usersList = usersList;
        notifyDataSetChanged();
    }

    public boolean isActiveChangePosition = false;
    private SecondViewHolder changeItemViewHolder;


    private void startDragAnimation(View view) {
        swipeDragHelper.makeMeShake(view, 80, 5);
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener{

        ImageView userAvatar;
        TextView username, tvChangePosition;
        int position;

        SecondViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.imageview_profile);
            username = itemView.findViewById(R.id.textview_name);
            tvChangePosition = itemView.findViewById(R.id.tv_change_position);
            tvChangePosition.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            isActiveChangePosition = !isActiveChangePosition;
            changeItemViewHolder = SecondViewHolder.this;
            setDragTouchListener(SecondViewHolder.this);
        }

        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                swipeDragHelper.getTouchHelper().startDrag(SecondViewHolder.this);
            }
            return false;
        }

        @SuppressLint("ClickableViewAccessibility")
        public void setDragTouchListener(SecondViewHolder viewHolder) {
            viewHolder.tvChangePosition.setText(isActiveChangePosition ? "Stop" : "Change Position");
            if (isActiveChangePosition) {
                viewHolder.itemView.setOnTouchListener(this);
                startDragAnimation(viewHolder.itemView);
            } else {
                viewHolder.itemView.setOnTouchListener(null);
                viewHolder.itemView.clearAnimation();
            }
        }
    }


    @Override
    public void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition) {
        User targetUser = usersList.get(oldPosition);
        User user = new User(targetUser);
        usersList.remove(oldPosition);
        usersList.add(newPosition, user);
        notifyItemMoved(oldPosition, newPosition);

        swipeDragHelper.getListUtil().saveHomePageList(context, usersList, new TypeToken<List<User>>() {
        });
        viewHolder.itemView.clearAnimation();
        if (viewHolder instanceof SecondViewHolder) {
            SecondViewHolder holder = (SecondViewHolder) viewHolder;
            isActiveChangePosition = false;
            holder.setDragTouchListener(holder);
        }
    }

    @Override
    public void onViewSwiped(int position) {
        usersList.remove(position);
        notifyItemRemoved(position);
    }

    private SwipeDragHelper swipeDragHelper;

    public void setSwipeDragHelper(SwipeDragHelper swipeDragHelper) {
        this.swipeDragHelper = swipeDragHelper;
    }
}
