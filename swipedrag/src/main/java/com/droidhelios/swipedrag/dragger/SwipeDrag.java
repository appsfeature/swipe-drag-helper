package com.droidhelios.swipedrag.dragger;

import android.graphics.Canvas;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.SwipeDragHelper;
import com.droidhelios.swipedrag.animation.SDAnimation;

import java.util.List;

/**
 * @author Created by Abhijit on 20-11-2019.
 */

public class SwipeDrag extends ItemTouchHelper.Callback implements SwipeDragHelper {

    private final ItemTouchHelper touchHelper;
    private final RecyclerView recyclerView;
    private final SwipeDragStatePreference dragDropStateUtil;
    private final SDAnimation sdAnimation;
    private final ActionListener contract;
    private boolean isEnableSwipeOption = false;
    private boolean isEnableGridView = false;
    private int disableDragPositionAt = -1;
    private List<Integer> disableDragPositionList;
    private boolean isLongPressDragEnabled = false;

    private SwipeDrag(RecyclerView recyclerView, ActionListener contract) {
        this.contract = contract;
        this.recyclerView = recyclerView;
        this.touchHelper = new ItemTouchHelper(this);
        this.sdAnimation = new SDAnimation();
        this.dragDropStateUtil = new SwipeDragStatePreference(recyclerView.getContext());
        attachToRecyclerView();
    }

    /**
     * @param recyclerView = recyclerView reference
     * @param adapter      = adapter reference
     */
    public static SwipeDragHelper Builder(RecyclerView recyclerView, ActionListener adapter) {
        return new SwipeDrag(recyclerView, adapter);
    }

    @Override
    public void attachToRecyclerView() {
        touchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public ItemTouchHelper getTouchHelper() {
        return touchHelper;
    }

    @Override
    public SwipeDragStatePreference getListUtil() {
        return dragDropStateUtil;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags;
        if(isEnableGridView) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        }else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        }
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
        Log.d("@Alpha", "onMove");
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

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        Log.d("@Alpha", "onSelectedChanged");

        contract.onStateChanged(viewHolder, actionState);
    }

    @Override
    public void makeMeShake(View view, int speed, int yOffset) {
        makeMeShake(view, speed, 0, yOffset);
    }

    /**
     * @param view : where animation perform
     * @param speed : Best value is 100
     * @param xOffset : Best value is 5
     * @param yOffset : Best value is 5
     */
    @Override
    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
        sdAnimation.makeMeShake(view,speed,xOffset,yOffset);
    }


    @Override
    public SwipeDrag setEnableSwipeOption(boolean isEnableSwipeOption) {
        this.isEnableSwipeOption = isEnableSwipeOption;
        return this;
    }

    @Override
    public SwipeDrag setEnableGridView(boolean enableGridView) {
        isEnableGridView = enableGridView;
        return this;
    }

    @Override
    public SwipeDrag setDisableDragPositionAt(int disableDragPositionAt) {
        this.disableDragPositionAt = disableDragPositionAt;
        return this;
    }

    @Override
    public SwipeDrag setDisableDragPositionList(List<Integer> disableDragPositionList) {
        this.disableDragPositionList = disableDragPositionList;
        return this;
    }

    @Override
    public SwipeDrag setLongPressDragEnabled(boolean longPressDragEnabled) {
        isLongPressDragEnabled = longPressDragEnabled;
        return this;
    }

}
