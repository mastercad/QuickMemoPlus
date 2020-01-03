package de.byte_artist.quickmemoplus.listener;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import de.byte_artist.quickmemoplus.activity.MainActivity;
import de.byte_artist.quickmemoplus.entity.MemoEntity;

public class MemoOnTouchListener implements View.OnTouchListener {

    private final GestureDetector gestureDetector;

    public MemoOnTouchListener(MainActivity activity, MemoEntity memoEntity) {
        gestureDetector = new GestureDetector(activity, new MemoGestureListener(activity, memoEntity));
    }

    public boolean onTouch(final View view, final MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            view.performClick();
        }
        return gestureDetector.onTouchEvent(motionEvent);
    }
}
