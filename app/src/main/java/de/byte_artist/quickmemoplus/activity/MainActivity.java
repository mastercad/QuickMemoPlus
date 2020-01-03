package de.byte_artist.quickmemoplus.activity;

import android.app.Activity;
import android.content.Intent;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import de.byte_artist.quickmemoplus.R;
import de.byte_artist.quickmemoplus.adapter.MemoEntityAdapter;
import de.byte_artist.quickmemoplus.db.MemoDbModel;
import de.byte_artist.quickmemoplus.db.MemoGroupDbModel;
import de.byte_artist.quickmemoplus.definitions.ResultCode;
import de.byte_artist.quickmemoplus.entity.MemoEntity;
import de.byte_artist.quickmemoplus.entity.MemoGroupEntity;


public class MainActivity extends AppCompatActivity {

    private RelativeLayout mRelativeLayout;
    public ArrayList<MemoEntity> memoEntities;

    public RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        this.getApplicationContext().deleteDatabase(DbModel.DATABASE_NAME);

        // Request window feature action bar
//        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("MAINACTIVITY", "onCreate");

//        Toolbar myToolbar = findViewById(R.id.my_toolbar);
//        setSupportActionBar(myToolbar);

        // Change the action bar color
//        if (null != getSupportActionBar()) {
//            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.RED));
//        }

        // Get the widgets reference from XML layout
        mRelativeLayout = findViewById(R.id.rl);
        RecyclerView mRecyclerView = findViewById(R.id.recycler_view);

        final MemoDbModel memoDbModel = new MemoDbModel(this.getApplicationContext());
        this.memoEntities = memoDbModel.load();

        // Define a layout for RecyclerView
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mAdapter = new MemoEntityAdapter(this, this.memoEntities);

        // Set the adapter for RecyclerView
        mRecyclerView.setAdapter(mAdapter);

        final MainActivity self = this;

        FloatingActionButton fab = new FloatingActionButton(this);
//        fab.setId(View.generateViewId());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MemoEntity currentMemoEntity = new MemoEntity();

                MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(self);
                MemoGroupEntity memoGroupEntity = memoGroupDbModel.findMemoGroupById(1);
                currentMemoEntity.setGroup(memoGroupEntity);
                Intent memoIntent = new Intent(MainActivity.this, MemoActivity.class);
                memoIntent.putExtra("memoEntity", currentMemoEntity);

                MainActivity.this.startActivityForResult(memoIntent, ResultCode.RESULT_INSERT);
            }
        });
        fab.setImageResource(R.drawable.add);
        fab.setSize(FloatingActionButton.SIZE_MINI);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryDark)));
        fab.setRippleColor(getResources().getColor(R.color.blue));
        fab.setFocusable(true);

        RelativeLayout.LayoutParams layout = new RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layout.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        layout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        layout.setMargins(10,10,10,10);
        fab.setLayoutParams(layout);

        this.mRelativeLayout.addView(fab);
    }

    @Override
    public void onResume() {
        this.mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        boolean dataChanged = false;

        if (null != data
            && data.hasExtra("memoEntityChanged")
        ) {
            dataChanged = data.getBooleanExtra("memoEntityChanged", false);
        }

        Log.d("MEMOENTITYCHANGED?", ""+dataChanged);

        if (!dataChanged) {
            this.mAdapter.notifyDataSetChanged();
            return;
        }
        switch(requestCode) {
            case (ResultCode.RESULT_UPDATE) :
            case (ResultCode.RESULT_INSERT) : {
                if (resultCode == Activity.RESULT_OK) {
                    MemoEntity passedMemoEntity = data.getParcelableExtra("memoEntity");
                    MemoEntity foundEntity = null;
                    for (int i = 0; i < this.memoEntities.size(); i++) {
                        if (this.memoEntities.get(i).getId() == passedMemoEntity.getId()) {
                            foundEntity = this.memoEntities.get(i);
                            break;
                        }
                    }
                    this.memoEntities.add(0, passedMemoEntity);

                    if (null != foundEntity) {
                        this.memoEntities.remove(foundEntity);
                    }
                }
                break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_all_memos) {
            Toast.makeText(MainActivity.this, "Action clicked", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
