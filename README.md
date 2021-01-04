# SwipeDragHelper 

An Android Library that provide drag & drop and swipe-to-dismiss with list state maintain functionality for RecyclerView items 

<p align="center">
  <img src="https://raw.githubusercontent.com/appsfeature/swipe-drag-helper/master/screenshots/preview_sample.gif" alt="Preview 1" width="300" />  
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
    ext {
        appcompat = '1.1.0-alpha01'
        retrofit_version = '2.3.0'
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
        SwipeDragActionListener { 
        ...
        ... 
        
    private SwipeDragHelper swipeDragHelper;

    public void setSwipeDragHelper(SwipeDragHelper swipeDragHelper) {
        this.swipeDragHelper = swipeDragHelper;
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        SecondViewHolder viewHolder = (SecondViewHolder) holder;
        ...
        ...
        
        viewHolder.setDragTouchListener(viewHolder, usersList.get(position));
    } 
    
    public class SecondViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener{

        ...
        ...
        TextView tvChangePosition;

        SecondViewHolder(View itemView) {
            super(itemView); 
            ...
            ...
            tvChangePosition = itemView.findViewById(R.id.tv_change_position);
            tvChangePosition.setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            final int position = getAdapterPosition();
            if(view.getId() == R.id.tv_change_position) {
                usersList.get(position).setChangePosition(!usersList.get(position).isChangePosition());
                //add this method for disable click when drag option is active
//                subAdapter.setChangePosition(usersList.get(position).isChangePosition());
                setDragTouchListener(SecondViewHolder.this, usersList.get(position));
                if (usersList.get(position).isChangePosition()) {
                    Toast.makeText(context,"Drag and drop where you want.", Toast.LENGTH_SHORT).show();
                }
            }else {
                User item = usersList.get(getAdapterPosition());
                Toast.makeText(context, item.getName(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        @SuppressLint("ClickableViewAccessibility")
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                swipeDragHelper.getTouchHelper().startDrag(SecondViewHolder.this);
            }
            return false;
        }

        @SuppressLint("ClickableViewAccessibility")
        public void setDragTouchListener(SecondViewHolder viewHolder, User item) {
            viewHolder.tvChangePosition.setText(item.isChangePosition() ? "Stop" : "Change Position");
            if (item.isChangePosition()) {
                viewHolder.itemView.setOnTouchListener(this);
                startDragAnimation(viewHolder.itemView);
            } else {
                viewHolder.itemView.setOnTouchListener(null);
                viewHolder.itemView.clearAnimation();
            }
        }
    }

    @Override
    public void onViewMoved(RecyclerView.ViewHolder viewHolder, int oldPosition, int newPosition) {
        User newItem = usersList.get(oldPosition);
        User oldItem = usersList.get(newPosition);
        int oldPos = oldItem.getRanking();
        oldItem.setRanking(newItem.getRanking());
        newItem.setRanking(oldPos);
        newItem.setChangePosition(false);

        User item = newItem.getClone();
        usersList.remove(oldPosition);
        usersList.add(newPosition, item);
        notifyItemMoved(oldPosition, newPosition);

        swipeDragHelper.getListUtil().saveHomePageList(context, usersList, new TypeToken<List<User>>() {
        });
        if (viewHolder instanceof SecondViewHolder) {
            SecondViewHolder holder = (SecondViewHolder) viewHolder;
            holder.itemView.clearAnimation();
//            holder.subAdapter.setChangePosition(false);
            holder.setDragTouchListener(holder, newItem);
        }
    }

    @Override
    public void onViewSwiped(int position) {
        usersList.remove(position);
        notifyItemRemoved(position);
    }

}
                                
```

#### Useful Links:
1. https://developer.android.com/reference/androidx/recyclerview/widget/ItemTouchHelper
