package com.droidhelios.swipedraghelper.dragger;//package com.droidhelios.dragger.dragger;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.graphics.Canvas;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.TranslateAnimation;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.ItemTouchHelper;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.droidhelios.dragger.BuildConfig;
//import com.google.gson.Gson;
//import com.google.gson.GsonBuilder;
//import com.google.gson.JsonSyntaxException;
//import com.google.gson.reflect.TypeToken;
//
//import java.util.List;
//
///**
// * @author Created by Abhijit on 20-11-2019.
// */
//
//public class SwipeDragHelper extends ItemTouchHelper.Callback {
//
//    private final ItemTouchHelper touchHelper;
//    private final RecyclerView recyclerView;
//    private final DragDropStateUtil dragDropStateUtil;
//    private SwipeDragActionListener contract;
//    private boolean isEnableSwipeOption = false;
//
//    private SwipeDragHelper(RecyclerView recyclerView, SwipeDragActionListener contract) {
//        this.contract = contract;
//        this.recyclerView = recyclerView;
//        this.touchHelper = new ItemTouchHelper(this);
//        this.dragDropStateUtil = new DragDropStateUtil(recyclerView.getContext());
//        attachToRecyclerView();
//    }
//
//    /**
//     * @param recyclerView = recyclerView reference
//     * @param adapter      = adapter reference
//     */
//    public static SwipeDragHelper Builder(RecyclerView recyclerView, SwipeDragActionListener adapter) {
//        return new SwipeDragHelper(recyclerView, adapter);
//    }
//
//    private void attachToRecyclerView() {
//        touchHelper.attachToRecyclerView(recyclerView);
//    }
//
//
//    public ItemTouchHelper getTouchHelper() {
//        return touchHelper;
//    }
//
//    public DragDropStateUtil getListUtil() {
//        return dragDropStateUtil;
//    }
//
//    @Override
//    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
//        // for disable swipe action for particular ViewHolder
////        if (viewHolder instanceof SectionHeaderViewHolder) {
////            return 0;
////        }
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags;
//        if (isEnableSwipeOption) {
//            swipeFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
//        } else {
//            swipeFlags = ItemTouchHelper.ACTION_STATE_DRAG;
//        }
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }
//
//    @Override
//    public boolean onMove(@NonNull RecyclerView recyclerView,@NonNull RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//        contract.onViewMoved(viewHolder, viewHolder.getAdapterPosition(), target.getAdapterPosition());
//        return true;
//    }
//
//    @Override
//    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//        contract.onViewSwiped(viewHolder.getAdapterPosition());
//    }
//
//    @Override
//    public boolean isLongPressDragEnabled() {
//        return false;
//    }
//
//    @Override
//    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//            float alpha = 1 - (Math.abs(dX) / recyclerView.getWidth());
//            viewHolder.itemView.setAlpha(alpha);
//        }
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//    }
//
//    public void makeMeShake(View view, int speed, int yOffset) {
//        makeMeShake(view, speed, 0, yOffset);
//    }
//
//    /**
//     * @param view : where animation perform
//     * @param speed : Best value is 100
//     * @param xOffset : Best value is 5
//     * @param yOffset : Best value is 5
//     */
//    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
//        Animation anim = new TranslateAnimation(xOffset, xOffset, -yOffset, yOffset);
//        anim.setDuration(speed);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(Animation.INFINITE);
//        view.startAnimation(anim);
//    }
//
////    public void makeMeShake(View view, int speed, int xOffset, int yOffset) {
////        yOffset = 5;
////        ObjectAnimator animator = ObjectAnimator.ofFloat(view,
////                View.TRANSLATION_Y,  yOffset,-yOffset);
////        animator.setDuration(100);
////        animator.setRepeatMode(ValueAnimator.REVERSE);
////        animator.setRepeatCount(Animation.INFINITE);
////        animator.setInterpolator(new CycleInterpolator(Animation.INFINITE));
////        animator.start();
////    }
//
//    public SwipeDragHelper setEnableSwipeOption(boolean isEnableSwipeOption) {
//        this.isEnableSwipeOption = isEnableSwipeOption;
//        return this;
//    }
//
//    public interface SwipeDragActionListener {
//        /**
//         * User targetUser = usersList.get(oldPosition);
//         * usersList.remove(oldPosition);
//         * usersList.add(newPosition, targetUser);
//         * notifyItemMoved(oldPosition, newPosition);
//         */
//        void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition);
//
//        void onViewSwiped(int position);
//    }
//
//    public class DragDropStateUtil {
//
//        private static final String HOME_PAGE_LIST = "home_page_list";
//        private static final String LIST_RESET_FLAG = "list_reset_flag";
//
//        private final Context context;
//
//        private DragDropStateUtil(Context context) {
//            this.context = context;
//        }
//
//        public <T> List<T> getSavedList(TypeToken<List<T>> typeCast) {
//            if(getLastVersion(context).equalsIgnoreCase(BuildConfig.VERSION_NAME)) {
//                return parseJson(getData(context, HOME_PAGE_LIST), typeCast);
//            }else {
//                return null;
//            }
//        }
//
//        private String getLastVersion(Context context) {
//            return getData(context, LIST_RESET_FLAG);
//        }
//
//
//        public <T> void saveHomePageList(final Context context, final List<T> jsonArray, TypeToken<List<T>> typeCast) {
//            Gson gson = new GsonBuilder()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .serializeNulls()
//                    .create();
//            String finalList = gson.toJson(jsonArray, typeCast.getType());
//            setData(context, HOME_PAGE_LIST, finalList);
//            setData(context, LIST_RESET_FLAG, BuildConfig.VERSION_NAME);
//        }
//
//        private <T> List<T> parseJson(String jsonContent, TypeToken<List<T>> typeCast) {
//            try {
//                return new Gson().fromJson(jsonContent, typeCast.getType());
//            } catch (JsonSyntaxException e) {
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//
//        private String getData(Context context, String key) {
//            if (context != null) {
//                return getDefaultSharedPref(context).getString(key, "");
//            } else {
//                return "";
//            }
//        }
//
//        private void setData(Context context, String key, String value) {
//            if (context != null && value != null) {
//                final SharedPreferences.Editor editor = getDefaultSharedPref(context).edit();
//                editor.putString(key, value);
//                editor.apply();
//            }
//        }
//
//        private SharedPreferences getDefaultSharedPref(Context context) {
//            return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
//        }
//    }
//
//}
