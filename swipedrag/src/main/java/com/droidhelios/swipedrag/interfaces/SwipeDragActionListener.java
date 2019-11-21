package com.droidhelios.swipedrag.interfaces;

import androidx.recyclerview.widget.RecyclerView;

public interface SwipeDragActionListener {
    /**
     * User targetUser = usersList.get(oldPosition);
     * usersList.remove(oldPosition);
     * usersList.add(newPosition, targetUser);
     * notifyItemMoved(oldPosition, newPosition);
     */
    void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition);

    void onViewSwiped(int position);
}
