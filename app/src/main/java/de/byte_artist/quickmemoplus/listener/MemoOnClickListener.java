package de.byte_artist.quickmemoplus.listener;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import de.byte_artist.quickmemoplus.activity.MemoActivity;
import de.byte_artist.quickmemoplus.definitions.ResultCode;
import de.byte_artist.quickmemoplus.entity.MemoEntity;

class MemoOnClickListener implements View.OnClickListener {

    private final MemoEntity memoEntity;
    private final AppCompatActivity activity;

    public MemoOnClickListener(AppCompatActivity activity, MemoEntity memoEntity) {
        this.memoEntity = memoEntity;
        this.activity = activity;
    }

    @Override
    public void onClick(View view) {
        Intent memoIntent = new Intent(activity, MemoActivity.class);
        memoIntent.putExtra("memoEntity", this.memoEntity);

        activity.startActivityForResult(memoIntent, ResultCode.RESULT_UPDATE);
    }
}
