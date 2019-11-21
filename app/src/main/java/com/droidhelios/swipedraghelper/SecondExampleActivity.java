package com.droidhelios.swipedraghelper;

import android.os.Bundle;

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


}
