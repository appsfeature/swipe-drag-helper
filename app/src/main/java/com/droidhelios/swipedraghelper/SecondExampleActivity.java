package com.droidhelios.swipedraghelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedraghelper.model.AdvanceListAdapter;
import com.droidhelios.swipedraghelper.model.User;
import com.droidhelios.swipedraghelper.model.UsersData;
import com.droidhelios.swipedrag.SwipeDragHelper;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class SecondExampleActivity extends AppCompatActivity {

    private List<User> usersList;
    private AdvanceListAdapter adapter;
    private SwipeDragHelper swipeAndDragHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        ImageView imageView = findViewById(R.id.imageview_profile);
        RecyclerView userRecyclerView = findViewById(R.id.recyclerview_user_list);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdvanceListAdapter(this);
        swipeAndDragHelper = SwipeDragHelper.Builder(userRecyclerView, adapter)
                .setDisableDragPositionAt(0)
                .setEnableSwipeOption(false)
                .setEnableGridView(false);
        adapter.setSwipeDragHelper(swipeAndDragHelper);
        userRecyclerView.setAdapter(adapter);

        usersList = getHomePageList();
        adapter.setUserList(usersList);

//        imageView.setImageDrawable(findDrawableByName(this, "ic_action_reorder"));
    }

    private Drawable findDrawableByName(Context context, String drawableName) {
        Resources resources = context.getResources();
        final int resourceId = resources.getIdentifier(drawableName, "drawable",
                context.getPackageName());
        return ContextCompat.getDrawable(context, resourceId);
    }


    public List<User> getHomePageList() {
        HashMap<Integer, Integer> rankList = getRankList(this);
        List<User> homeList = new UsersData().getUsersList();
        if (homeList != null) {
            for(User item : homeList){
                Integer rank = rankList.get(item.getId());
                if(rank != null){
                    item.setRanking(rank);
                }
            }
            sortArrayList(homeList);
        }
        return homeList;
    }

    private void sortArrayList(List<User> list) {
        Collections.sort(list, new Comparator<User>() {
            @Override
            public int compare(User item, User item2) {
                Integer value = item.getRanking();
                Integer value2 = item2.getRanking();
                return value.compareTo(value2);
            }
        });
    }

    public HashMap<Integer, Integer> getRankList(Context context) {
        HashMap<Integer, Integer> map = new HashMap<>();
        List<User> rankList = SwipeDragHelper.getRankList(context, new TypeToken<List<User>>() {
        });
        if(rankList != null){
            for (User item : rankList){
                map.put(item.getId(), item.getRanking());
            }
        }
        return map;
    }


    public void onOpenClick(View view) {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}
