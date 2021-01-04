package com.droidhelios.swipedraghelper;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedraghelper.model.AdvanceListAdapter;
import com.droidhelios.swipedraghelper.model.User;
import com.droidhelios.swipedraghelper.model.UsersData;
import com.droidhelios.swipedrag.SwipeDragHelper;
import com.google.gson.reflect.TypeToken;

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
                .setEnableResetSavedList(BuildConfig.VERSION_NAME)
                .setEnableSwipeOption(true);
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
        return resources.getDrawable(resourceId);
    }


    public List<User> getHomePageList() {
        List<User> homeList = swipeAndDragHelper.getListUtil().getSavedList(new TypeToken<List<User>>() {
        });
        if (homeList == null) {
            UsersData usersData = new UsersData();
            homeList = usersData.getUsersList();
            swipeAndDragHelper.getListUtil().saveHomePageList(this, homeList, new TypeToken<List<User>>() {
            });
        }
        return homeList;
    }


    public void onOpenClick(View view) {
        startActivity(new Intent(this, WebViewActivity.class));
    }
}
