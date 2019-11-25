# SwipeDragHelper 

An Android Library that provide drag & drop and swipe-to-dismiss with list state maintain functionality for RecyclerView items 

<p align="center">
  <img src="https://raw.githubusercontent.com/appsfeature/swipe-drag-helper/master/screenshots/preview_sample.gif" alt="Preview 1" width="200" />  
</p>

## Setup Project

Add this to your project build.gradle

Project-level build.gradle (<project>/build.gradle):

``` gradle 
allprojects {
    repositories {
        google()
        jcenter() 
        maven { url 'https://jitpack.io' } 
    }
}
```

Add this to your project build.gradle

Module-level build.gradle (<module>/build.gradle): 

#### [![](https://jitpack.io/v/appsfeature/swipe-drag-helper.svg)](https://jitpack.io/#appsfeature/swipe-drag-helper)
```gradle  

dependencies {
    implementation 'com.github.appsfeature:swipe-drag-helper:x.y'
} 
```

In your activity class:
#### Usage method
```java 
public class ExampleActivity extends AppCompatActivity {

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
                                
```

In your Adapter class:
#### Usage method
```java 
public class UserListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        CopySwipeDragHelper.SwipeDragActionListener { 
        
        ...
        ... 

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        ...
        ...
        ((UserViewHolder) holder).itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                        swipeAndDragHelper.getTouchHelper().startDrag(holder);
                    }
                    return false;
                }
            });
    } 

    @Override
    public void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition) {
        User targetUser = usersList.get(oldPosition);
        User user = new User(targetUser);
        usersList.remove(oldPosition);
        usersList.add(newPosition, user);
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onViewSwiped(int position) {
        usersList.remove(position);
        notifyItemRemoved(position);
    }

    private CopySwipeDragHelper swipeAndDragHelper;

    public void setSwipeAndDragHelper(CopySwipeDragHelper swipeAndDragHelper) {
        this.swipeAndDragHelper = swipeAndDragHelper;
    }
}
                                
```

#### Useful Links:
1. https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper
