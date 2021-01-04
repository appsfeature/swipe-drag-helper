package com.droidhelios.swipedraghelper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidhelios.swipedrag.SwipeDragHelper;
import com.droidhelios.swipedraghelper.model.AdvanceListAdapter;
import com.droidhelios.swipedraghelper.model.User;
import com.droidhelios.swipedraghelper.model.UsersData;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class WebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview
        );


    }


}
