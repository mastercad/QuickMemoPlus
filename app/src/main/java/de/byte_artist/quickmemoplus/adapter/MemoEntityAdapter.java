package de.byte_artist.quickmemoplus.adapter;

import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import de.byte_artist.quickmemoplus.R;
import de.byte_artist.quickmemoplus.activity.MainActivity;
import de.byte_artist.quickmemoplus.entity.MemoEntity;
import de.byte_artist.quickmemoplus.listener.MemoOnTouchListener;

public class MemoEntityAdapter extends RecyclerView.Adapter<MemoEntityAdapter.ViewHolder> {
    private final ArrayList<MemoEntity> mDataSet;
    private final MainActivity activity;
    private final Random mRandom = new Random();

    public MemoEntityAdapter(MainActivity activity, ArrayList<MemoEntity> dataSet){
        mDataSet = dataSet;
        this.activity = activity;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        final TextView mTextView;
        ViewHolder(View view){
            super(view);
            mTextView = view.findViewById(R.id.txt_line1);
        }
    }

    @Override
    public MemoEntityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Create a new View
        View view = LayoutInflater.from(activity.getApplicationContext()).inflate(R.layout.memo,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position){
        final MemoEntity memoEntity = mDataSet.get(position);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            viewHolder.mTextView.setText(Html.fromHtml(memoEntity.getText(),Html.FROM_HTML_MODE_LEGACY));
        } else {
            viewHolder.mTextView.setText(Html.fromHtml(memoEntity.getText()));
        }

        if (null != this.activity) {
            viewHolder.mTextView.setOnTouchListener(new MemoOnTouchListener(this.activity, memoEntity));
/*
            viewHolder.mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent memoIntent = new Intent(activity, MemoActivity.class);
                    memoIntent.putExtra("memoEntity", memoEntity);

                    activity.startActivityForResult(memoIntent, ResultCode.RESULT_UPDATE);
                }
            });
 */
        }

        // Set a random color for TextView background
//        viewHolder.mTextView.setBackgroundColor(getRandomHSVColor());
    }

    @Override
    public int getItemCount(){
        return mDataSet.size();
    }

    // Custom method to generate random HSV color
    private int getRandomHSVColor(){
        // Generate a random hue value between 0 to 360
        int hue = mRandom.nextInt(361);
        // We make the color depth full
        float saturation = 1.0f;
        // We make a full bright color
        float value = 1.0f;
        // We avoid color transparency
        int alpha = 25;
        // Finally, generate the color
        // Return the color
        return Color.HSVToColor(alpha, new float[]{hue, saturation, value});
    }
}
