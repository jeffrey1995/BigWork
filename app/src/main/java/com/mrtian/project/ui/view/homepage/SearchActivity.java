package com.mrtian.project.ui.view.homepage;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mrtian.project.R;
import com.mrtian.project.ui.view.base.BaseActivity;
import com.mrtian.project.utils.AppUtils;


public class SearchActivity extends BaseActivity {
    private EditText search_edt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMainView(R.layout.activity_search);
        initView();
    }

    private void initView() {
        search_edt = (EditText) findViewById(R.id.search_edt);
        final Drawable img_right = this.getResources().getDrawable(R.drawable.gray_close);
        final Drawable img_left = this.getResources().getDrawable(R.drawable.search_input);
        final Drawable img_bottom = this.getResources().getDrawable(R.drawable.line);
        search_edt.setCompoundDrawablesWithIntrinsicBounds(img_left, null, null, img_bottom);
        search_edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("".equals(search_edt.getText().toString())) {
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(img_left, null, null, img_bottom);
                } else {
                    search_edt.setCompoundDrawablesWithIntrinsicBounds(img_left, null, img_right, img_bottom);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        search_edt.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // et.getCompoundDrawables()得到一个长度为4的数组，分别表示左右上下四张图片
                Drawable drawable = search_edt.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > search_edt.getWidth()
                        - search_edt.getPaddingRight()
                        - drawable.getIntrinsicWidth()) {
                    search_edt.setText("");
                }
                return false;
            }
        });

        search_edt.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {
                if (arg1 == EditorInfo.IME_ACTION_DONE) {
                    AppUtils.toast("你点了软键盘回车按钮", Toast.LENGTH_SHORT);
                }
                return false;
            }
        });

        findViewById(R.id.cancel_tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        setmTitleBarEnable(false);
    }
}
