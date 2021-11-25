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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.SwipeDragHelper;
import com.droidhelios.swipedraghelper.R;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * @author Created by Abhijit Rao on 20-11-2019.
 */

public class AdvanceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        SwipeDragHelper.ActionListener {
    private static final int USER_TYPE = 1;
    private static final int HEADER_TYPE = 2;
    private final Context context;
    private List<User> usersList;

    public AdvanceListAdapter(Context context, List<User> usersList) {
        this.context= context;
        this.usersList= usersList;
    }

    @Override
    @NonNull
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_user_list_section_header, parent, false);
                return new SectionHeaderViewHolder(view);
            case USER_TYPE:
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
            Picasso.get().load(usersList.get(position).getImageUrl()).into(viewHolder.userAvatar);
            viewHolder.setDragTouchListener(viewHolder, usersList.get(position).isChangePosition());
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

    private void startDragAnimation(View view) {
        swipeDragHelper.makeMeShake(view, 80, 5);
    }

    public class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
            , View.OnTouchListener{

        ImageView userAvatar;
        TextView username, tvChangePosition;

        SecondViewHolder(View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.imageview_profile);
            username = itemView.findViewById(R.id.textview_name);
            tvChangePosition = itemView.findViewById(R.id.tv_change_position);
            tvChangePosition.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getAdapterPosition();
            if(view.getId() == R.id.tv_change_position) {
                usersList.get(position).setChangePosition(!usersList.get(position).isChangePosition());
                //add this method for disable click when drag option is active
//                subAdapter.setChangePosition(usersList.get(position).isChangePosition());
                setDragTouchListener(SecondViewHolder.this, usersList.get(position).isChangePosition());
                if (usersList.get(position).isChangePosition()) {
                    Toast.makeText(context,"Drag and drop where you want.", Toast.LENGTH_SHORT).show();
                }
            }else {
                User item = usersList.get(getAdapterPosition());
                Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
            }
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
        public void setDragTouchListener(SecondViewHolder viewHolder, boolean isEnableTouch) {
            viewHolder.tvChangePosition.setText(isEnableTouch ? "Stop" : "Change Position");
            if (isEnableTouch) {
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
        User item = usersList.get(oldPosition).getClone();
        usersList.remove(oldPosition);
        usersList.add(newPosition, item);
        notifyItemMoved(oldPosition, newPosition);

        if (viewHolder instanceof SecondViewHolder) {
            ((SecondViewHolder) viewHolder).setDragTouchListener(((SecondViewHolder) viewHolder), false);
        }
    }

    @Override
    public void onStateChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            for (int i=0; i < usersList.size(); i++){
                usersList.get(i).setRanking(i + 1);
                usersList.get(i).setChangePosition(false);
            }
            swipeDragHelper.getListUtil().saveRankList(context, usersList,new TypeToken<List<User>>() {});
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
