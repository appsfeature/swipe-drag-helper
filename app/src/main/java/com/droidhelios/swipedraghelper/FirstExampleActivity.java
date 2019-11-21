package com.droidhelios.swipedraghelper;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.util.CopySwipeDragHelper;
import com.droidhelios.swipedraghelper.model.User;
import com.droidhelios.swipedraghelper.model.UserListAdapter;
import com.droidhelios.swipedraghelper.model.UsersData;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class FirstExampleActivity extends AppCompatActivity {

    private static final String TAG = "FirstExampleActivity";
    private List<User> usersList;
    private UserListAdapter adapter;
    private CopySwipeDragHelper swipeAndDragHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        RecyclerView userRecyclerView = findViewById(R.id.recyclerview_user_list);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new UserListAdapter();
        swipeAndDragHelper = CopySwipeDragHelper.Builder(userRecyclerView, adapter)
                .setEnableSwipeOption(false);
        adapter.setSwipeAndDragHelper(swipeAndDragHelper);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_apply:
                if (usersList != null && swipeAndDragHelper != null) {
                    swipeAndDragHelper.getListUtil().saveHomePageList(this, usersList, new TypeToken<List<User>>() {
                    });
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
