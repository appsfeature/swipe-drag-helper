package com.droidhelios.swipedrag;

import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.dragger.SwipeDrag;
import com.droidhelios.swipedrag.dragger.SwipeDragStatePreference;
import com.droidhelios.swipedrag.model.RankModel;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.List;

/**
 * @author Created by Abhijit on 20-11-2019.
 */

public interface SwipeDragHelper {

    interface ActionListener {
        /**
         * User targetUser = usersList.get(oldPosition);
         * usersList.remove(oldPosition);
         * usersList.add(newPosition, targetUser);
         * notifyItemMoved(oldPosition, newPosition);
         */
        void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition);

        /**
         * @param viewHolder – The new ViewHolder that is being swiped or dragged. Might be null if it is cleared.
         * @param actionState – One of ItemTouchHelper.ACTION_STATE_IDLE, ACTION_STATE_DRAG or ACTION_STATE_SWIPE.
         */
        void onStateChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState);

        default void onViewSwiped(int position) {

        }
    }

    static SwipeDragHelper Builder(RecyclerView recyclerView, ActionListener adapter) {
        return SwipeDrag.Builder(recyclerView, adapter);
    }
    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {}
     */
    static <T> List<T> getRankList(Context context, TypeToken<List<T>> typeCast) {
        return new SwipeDragStatePreference(context).getRankList(typeCast);
    }
    /**
     * @param typeCast : new TypeToken<List<ModelName>>() {}
     */
    static <T> HashMap<Integer, Integer> getRankHashMap(Context context, TypeToken<List<T>> typeCast) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<T> rankList = new SwipeDragStatePreference(context).getRankList(typeCast);
        if(rankList != null) {
            for (T item : rankList) {
                if (item instanceof RankModel) {
                    map.put(((RankModel) item).getId(), ((RankModel) item).getRank());
                }
            }
        }
        return map;
    }
    void attachToRecyclerView();

    ItemTouchHelper getTouchHelper();

    SwipeDragStatePreference getListUtil();

    void makeMeShake(View view, int speed, int yOffset);

    /**
     * @param view : where animation perform
     * @param speed : Best value is 100
     * @param xOffset : Best value is 5
     * @param yOffset : Best value is 5
     */
    void makeMeShake(View view, int speed, int xOffset, int yOffset);

    SwipeDragHelper setEnableSwipeOption(boolean isEnableSwipeOption);

    SwipeDragHelper setEnableGridView(boolean enableGridView);

    SwipeDragHelper setDisableDragPositionAt(int disableDragPositionAt);

    SwipeDragHelper setDisableDragPositionList(List<Integer> disableDragPositionList);

    SwipeDragHelper setLongPressDragEnabled(boolean longPressDragEnabled);

    void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState);
}
