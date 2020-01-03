package de.byte_artist.quickmemoplus.service;

import android.content.Context;
import android.util.AttributeSet;

public class RichEditor extends jp.wasabeef.richeditor.RichEditor {

    public RichEditor(Context context) {
        super(context);
    }

    public RichEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void insertLine() {
        insertHTML("<hr />");
    }

    private void insertHTML(String html) {
        exec("javascript:RE.insertHTML('" + html + "');");
    }
}
