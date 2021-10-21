package com.droidhelios.swipedrag;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.animation.SDAnimation;
import com.droidhelios.swipedrag.dragger.SwipeDragStatePreference;
import com.droidhelios.swipedrag.util.SDConstants;

import java.util.List;

/**
 * @author Created by Abhijit on 20-11-2019.
 */

public class SwipeDragHelper extends ItemTouchHelper.Callback {

    private final ItemTouchHelper touchHelper;
    private final RecyclerView recyclerView;
    private final SwipeDragStatePreference dragDropStateUtil;
    private final SDAnimation sdAnimation;
    private ActionListener contract;
    private boolean isEnableSwipeOption = false;
    private boolean isEnableGridView = false;
    private int disableDragPositionAt = -1;
    private List<Integer> disableDragPositionList;
    private boolean isLongPressDragEnabled = false;
    private String versionName = SDConstants.EMPTY;

    private SwipeDragHelper(RecyclerView recyclerView, ActionListener contract) {
        this.contract = contract;
        this.recyclerView = recyclerView;
        this.touchHelper = new ItemTouchHelper(this);
        this.sdAnimation = new SDAnimation();
        this.dragDropStateUtil = new SwipeDragStatePreference(recyclerView.getContext(), versionName);
        attachToRecyclerView();
    }

    /**
     * @param recyclerView = recyclerView reference
     * @param adapter      = adapter reference
     */
    public static SwipeDragHelper Builder(RecyclerView recyclerView, ActionListener adapter) {
        return new SwipeDragHelper(recyclerView, adapter);
    }

    private void attachToRecyclerView() {
        touchHelper.attachToRecyclerView(recyclerView);
    }

    public interface ActionListener {
        /**
         * User targetUser = usersList.get(oldPosition);
         * usersList.remove(oldPosition);
         * usersList.add(newPosition, targetUser);
         * notifyItemMoved(oldPosition, newPosition);
         */
        void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition);

        void onViewSwiped(int position);
    }

    public ItemTouchHelper getTouchHelper() {
        return touchHelper;
    }

    public SwipeDragStatePreference getListUtil() {
        return dragDropStateUtil;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int swipeFlags;
        if (isEnableSwipeOption) {
            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        } else {
            swipeFlags = ItemTouchHelper.ACTION_STATE_DRAG;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean canDropOver(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder current, @NonNull RecyclerView.ViewHolder target) {
        if (disableDragPositionAt >= 0) {
            int position = recyclerView.getChildAdapterPosition(target.itemView);
            if (position == disableDragPositionAt) {
                return false;
            }
        }
        if (disableDragPositionList != null) {
            int position = recyclerView.getChildAdapterPosition(target.itemView);
            if(disableDragPositionList.contains(position)){
                return false;
            }
        }
        return super.canDropOver(recyclerView, current, target);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        contract.onViewMoved(viewHolder, viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        contract.onViewSwiped(viewHolder.getAdapterPosition());
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
            viewHolder.itemView.setAlpha(alpha);
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void makeMeShake(View view, int speed, int yOffset) {
        makeMeShake(view, speed, 0, yOffset);
    }

    /**
     * @param view : where animation perform
     * @param speed : Best value is 100
     * @param xOffset : Best value is 5
     * @param yOffset : Best value is 5
     */
    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
        sdAnimation.makeMeShake(view,speed,xOffset,yOffset);
    }

    public SwipeDragHelper setEnableSwipeOption(boolean isEnableSwipeOption) {
        this.isEnableSwipeOption = isEnableSwipeOption;
        return this;
    }

    public SwipeDragHelper setEnableGridView(boolean enableGridView) {
        isEnableGridView = enableGridView;
        return this;
    }

    public SwipeDragHelper setEnableResetSavedList(String versionName) {
        this.versionName = versionName;
        return this;
    }

    public SwipeDragHelper setDisableDragPositionAt(int disableDragPositionAt) {
        this.disableDragPositionAt = disableDragPositionAt;
        return this;
    }

    public SwipeDragHelper setDisableDragPositionList(List<Integer> disableDragPositionList) {
        this.disableDragPositionList = disableDragPositionList;
        return this;
    }

    public SwipeDragHelper setLongPressDragEnabled(boolean longPressDragEnabled) {
        isLongPressDragEnabled = longPressDragEnabled;
        return this;
    }

}
