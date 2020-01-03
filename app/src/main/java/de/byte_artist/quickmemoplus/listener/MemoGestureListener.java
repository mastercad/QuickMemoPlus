package de.byte_artist.quickmemoplus.listener;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import de.byte_artist.quickmemoplus.activity.MainActivity;
import de.byte_artist.quickmemoplus.activity.MemoActivity;
import de.byte_artist.quickmemoplus.db.MemoDbModel;
import de.byte_artist.quickmemoplus.definitions.ResultCode;
import de.byte_artist.quickmemoplus.entity.MemoEntity;

class MemoGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    private final MainActivity activity;
    private final MemoEntity memoEntity;

    MemoGestureListener(MainActivity activity, MemoEntity memoEntity) {
        this.activity = activity;
        this.memoEntity = memoEntity;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onClick();
        return super.onSingleTapUp(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        onDoubleClick();
        return super.onDoubleTap(e);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        onLongClick();
        super.onLongPress(e);
    }

    // Determines the fling velocity and then fires the appropriate swipe event accordingly
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        boolean result = false;
        try {
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        result = onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                }
            } else {
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeDown();
                    } else {
                        onSwipeUp();
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    /*
     * deactivate
     */
    private boolean onSwipeRight() {

        Toast.makeText(activity, "DELETED!", Toast.LENGTH_SHORT).show();
        MemoDbModel luggageDbModel = new MemoDbModel(this.activity);
        luggageDbModel.delete(memoEntity);
        this.activity.mAdapter.notifyDataSetChanged();
        this.activity.memoEntities.remove(memoEntity);

        return true;
    }

    /*
     * activate
     */
    private void onSwipeLeft() {
//        if (!luggageEntity.isActive()) {
//            LuggageDbModel luggageDbModel = new LuggageDbModel(this.activity);
//            luggageEntity.setActive(true);
//            luggageDbModel.update(luggageEntity);
//            activity.refresh();
//        }
    }

    private void onSwipeUp() {

    }

    private void onSwipeDown() {
    }

    private void onClick() {
        /*
        CustomDialog dialog = new CustomDialog(activity, R.style.AlertDialogTheme, CustomDialog.TYPE_INFO);
        dialog.setTitle(R.string.title_information);
        dialog.setMessage(String.format(activity.getResources().getString(R.string.text_luggage_information), luggageEntity.getName(), luggageEntity.getCategoryEntity().getName(), luggageEntity.getWeight()));
        dialog.setButton(CustomDialog.BUTTON_POSITIVE, activity.getResources().getString(R.string.text_close), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.create();
        dialog.show();
         */
        Intent memoIntent = new Intent(activity, MemoActivity.class);
        memoIntent.putExtra("memoEntity", this.memoEntity);

        activity.startActivityForResult(memoIntent, ResultCode.RESULT_UPDATE);
    }

    private void onDoubleClick() {

    }

    private void onLongClick() {
    }
}
