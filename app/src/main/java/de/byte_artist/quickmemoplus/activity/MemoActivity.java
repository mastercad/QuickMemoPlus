package de.byte_artist.quickmemoplus.activity;

import android.app.Dialog;
import android.graphics.Color;
import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.rarepebble.colorpicker.ColorPickerView;

import java.util.List;

import de.byte_artist.quickmemoplus.R;
import de.byte_artist.quickmemoplus.db.MemoDbModel;
import de.byte_artist.quickmemoplus.db.MemoGroupDbModel;
import de.byte_artist.quickmemoplus.entity.MemoEntity;
import de.byte_artist.quickmemoplus.entity.MemoGroupEntity;
import de.byte_artist.quickmemoplus.service.RichEditor;

public class MemoActivity extends AppCompatActivity {

    private static final int INIT_COLOR_VALUE = 99999999;
    private MemoEntity memoEntity = null;
    private int fontColor = -16777216;
    private int backgroundColor = -1;
    private int currentTextColor = INIT_COLOR_VALUE;
    private int currentTextBackgroundColor = INIT_COLOR_VALUE;
    private RichEditor mEditor;
    private Dialog foregroundColorPickerDialog;
    private Dialog backgroundColorPickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_memo);
            this.initRichEditor();
        } catch (Exception exception) {
            Log.e("MemoActivity:", exception.getMessage(), exception);
        }
    }

    private void initRichEditor() {

        mEditor = findViewById(R.id.editor);
        mEditor.setDrawingCacheEnabled(false);
        final MemoActivity self = this;

        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(16);
        mEditor.setEditorFontColor(Color.WHITE);
        mEditor.setPadding(10, 10, 10, 10);

//        mEditor.loadUrl("file:///android_asset/editor.html");
//        mEditor.loadCSS("file:///android_asset/style.css");
        mEditor.loadCSS("file:///android_asset/normalize.css");

        mEditor.setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener() {
            @Override
            public void onStateChangeListener(String text, List<RichEditor.Type> types) {
                Log.i("Memo Activity", "OnDecorationChangeListener : "+text);
            }
        });

        final MemoEntity memoEntity = getIntent().getParcelableExtra("memoEntity");

        if (memoEntity != null) {
            this.memoEntity = memoEntity;
            mEditor.setHtml(memoEntity.getText());
        }

        findViewById(R.id.action_line).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertLine();
            }
        });

        findViewById(R.id.action_undo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.undo();
            }
        });

        findViewById(R.id.action_redo).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.redo();
            }
        });

        findViewById(R.id.action_remove_format).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.removeFormat();
            }
        });

        findViewById(R.id.action_bold).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBold();
            }
        });

        findViewById(R.id.action_italic).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setItalic();
            }
        });

        findViewById(R.id.action_subscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSubscript();
            }
        });

        findViewById(R.id.action_superscript).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setSuperscript();
            }
        });

        findViewById(R.id.action_strikethrough).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setStrikeThrough();
            }
        });

        findViewById(R.id.action_underline).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setUnderline();
            }
        });

        findViewById(R.id.action_heading1).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(1);
            }
        });

        findViewById(R.id.action_heading2).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(2);
            }
        });

        findViewById(R.id.action_heading3).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(3);
            }
        });

        findViewById(R.id.action_heading4).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(4);
            }
        });

        findViewById(R.id.action_heading5).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(5);
            }
        });

        findViewById(R.id.action_heading6).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setHeading(6);
            }
        });

        findViewById(R.id.action_txt_color).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                foregroundColorPickerDialog = new Dialog(self);
                foregroundColorPickerDialog.setContentView(R.layout.color_picker);

                final ColorPickerView picker = foregroundColorPickerDialog.findViewById(R.id.colorPicker);
                picker.setColor(currentTextColor);

                foregroundColorPickerDialog.show();

                foregroundColorPickerDialog.findViewById(R.id.colorPickerBtnOk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fontColor = picker.getColor();
                        currentTextColor = fontColor;
                        foregroundColorPickerDialog.dismiss();
                        mEditor.setTextColor(currentTextColor);
                    }
                });

                foregroundColorPickerDialog.findViewById(R.id.colorPickerBtnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        foregroundColorPickerDialog.dismiss();
                    }
                });

            }
        });

        findViewById(R.id.action_bg_color).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                backgroundColorPickerDialog = new Dialog(self);
                backgroundColorPickerDialog.setContentView(R.layout.color_picker);

                final ColorPickerView picker = backgroundColorPickerDialog.findViewById(R.id.colorPicker);
                picker.setColor(currentTextBackgroundColor);

                backgroundColorPickerDialog.show();

                backgroundColorPickerDialog.findViewById(R.id.colorPickerBtnOk).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        backgroundColor = picker.getColor();
                        currentTextBackgroundColor = backgroundColor;
                        backgroundColorPickerDialog.dismiss();
                        mEditor.setTextBackgroundColor(currentTextBackgroundColor);
                    }
                });

                backgroundColorPickerDialog.findViewById(R.id.colorPickerBtnCancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        backgroundColorPickerDialog.dismiss();
                    }
                });

            }
        });

        findViewById(R.id.action_indent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setIndent();
            }
        });

        findViewById(R.id.action_outdent).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setOutdent();
            }
        });

        findViewById(R.id.action_align_left).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignLeft();
            }
        });

        findViewById(R.id.action_align_center).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignCenter();
            }
        });

        findViewById(R.id.action_align_right).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setAlignRight();
            }
        });

        findViewById(R.id.action_blockquote).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBlockquote();
            }
        });

        findViewById(R.id.action_insert_bullets).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setBullets();
            }
        });

        findViewById(R.id.action_insert_numbers).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.setNumbers();
            }
        });

        findViewById(R.id.action_insert_image).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Dialog imageDialog = new Dialog(self);
                imageDialog.setContentView(R.layout.url_dialog);

                imageDialog.show();

                imageDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView urlTextView = imageDialog.findViewById(R.id.edit_url);
                        TextView urlNameTextView = imageDialog.findViewById(R.id.edit_name);
                        mEditor.insertImage(urlTextView.getText().toString(), urlNameTextView.getText().toString());
                        imageDialog.dismiss();
                    }
                });

                imageDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageDialog.dismiss();
                    }
                });
            }
        });

        findViewById(R.id.action_insert_link).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                final Dialog imageDialog = new Dialog(self);
                imageDialog.setContentView(R.layout.url_dialog);

                imageDialog.show();

                imageDialog.findViewById(R.id.btn_ok).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TextView urlTextView = imageDialog.findViewById(R.id.edit_url);
                        TextView urlNameTextView = imageDialog.findViewById(R.id.edit_name);
                        mEditor.insertLink(urlTextView.getText().toString(), urlNameTextView.getText().toString());
                        imageDialog.dismiss();
                    }
                });

                imageDialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        imageDialog.dismiss();
                    }
                });
            }
        });
/*
        findViewById(R.id.action_insert_checkbox).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mEditor.insertTodo();
            }
        });
*/
        mEditor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                Log.i("MemoActivity", "Focus Changed : "+hasFocus);
                }
            }
        );

/*
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if (INIT_COLOR_VALUE != currentTextColor) {
                    mEditor.setTextColor(currentTextColor);
                    currentTextColor = INIT_COLOR_VALUE;
                }
                if (INIT_COLOR_VALUE != currentTextBackgroundColor) {
                    mEditor.setTextBackgroundColor(currentTextBackgroundColor);
                    currentTextBackgroundColor = INIT_COLOR_VALUE;
                }
                manageTextChanges(text);
            }
        });
*/
    }

    private void manageTextChanges(String text) {

        Intent resultIntent = new Intent();

        if (0 < text.length()
            && !text.equals(memoEntity.getText())
            || INIT_COLOR_VALUE != currentTextColor
            || INIT_COLOR_VALUE != currentTextBackgroundColor
        ) {
            memoEntity.setText(text);
            MemoDbModel memoDbModel = new MemoDbModel(this);
            ensureMemoGroupIsSet(memoEntity);

            if (0 < memoEntity.getId()) {
                memoDbModel.update(memoEntity);
            } else {
                memoDbModel.insert(memoEntity);
            }
            resultIntent.putExtra("memoEntityChanged", true);
//        } else {
//            resultIntent.putExtra("memoEntityChanged", false);
        }
        // this leads that every view leads in a reordering of main activity memo list
        resultIntent.putExtra("memoEntityChanged", true);
        resultIntent.putExtra("memoEntity", memoEntity);

        setResult(Activity.RESULT_OK, resultIntent);
    }

    private void ensureMemoGroupIsSet(MemoEntity memoEntity) {
        if (null == memoEntity.getGroup()) {
            MemoGroupDbModel memoGroupDbModel = new MemoGroupDbModel(this);
            MemoGroupEntity memoGroupEntity = memoGroupDbModel.findMemoGroupById(1);

            memoEntity.setGroup(memoGroupEntity);
        }
    }

    @Override
    protected void onStop() {
        if (null != mEditor) {
            manageTextChanges(mEditor.getHtml());
        }
        super.onStop();
    }

    public void onBackPressed() {
        if (null != mEditor) {
            manageTextChanges(mEditor.getHtml());
        }
        super.onBackPressed();
    }


    @Override
    public void onDestroy() {
        if (null != mEditor) {
            mEditor.destroy();
            mEditor = null;
        }
        super.onDestroy();
    }
}
