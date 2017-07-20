package com.mrtian.project.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mrtian.project.R;

/**
 * Created by tianxiying on 2017/4/19.
 */

public class CommonDialog extends Dialog {
    private String title = "";
    private String msg = "";
    private String negTitle = "";
    private String posTitle = "";
    private Context mContext;
    private ModifyDialogListener mDialogListener;
    private boolean ISEDITABLE = false;

    private TextView title_tv;
    private Button negative_btn;
    private Button positive_btn;
    private RelativeLayout content_rl;
    public static int NEGATIVE = 1;
    public static int POSITIVE = 2;
    private static DealWork dealWork;

    interface DealWork {
        void positiveWork();
    }

    public CommonDialog(Context context, ModifyDialogListener mDialogListener) {
        super(context);
        mContext = context;
        this.mDialogListener = mDialogListener;
    }

    public CommonDialog setTitleName(String title) {
        this.title = title;
        return this;
    }

    public CommonDialog setPosTitle(String posTitle) {
        this.posTitle = posTitle;
        return this;
    }

    public CommonDialog setNegTitle(String negTitle) {
        this.negTitle = negTitle;
        return this;
    }

    public CommonDialog setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_confirm);
        initView();

        title_tv.setText(title + "");
        positive_btn.setText(posTitle + "");
        negative_btn.setText(negTitle + "");

        negative_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialogListener.onResponse(NEGATIVE, null);
                hide();
            }
        });

        positive_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealWork.positiveWork();
            }
        });
    }

    private void initView() {
        title_tv = (TextView) findViewById(R.id.title_tv);
        negative_btn = (Button) findViewById(R.id.negative_btn);
        positive_btn = (Button) findViewById(R.id.positive_btn);
        content_rl = (RelativeLayout) findViewById(R.id.content_rl);
        if (ISEDITABLE) {
            final EditText editText = new EditText(mContext);
            addContentView(editText);
            dealWork = new DealWork() {
                @Override
                public void positiveWork() {
                    mDialogListener.onResponse(POSITIVE, editText.getText().toString());
                    hide();
                }
            };
        } else {
            TextView textView = new TextView(mContext);
            textView.setText(msg);
            textView.setTextSize(18);
            addContentView(textView);
            dealWork = new DealWork() {
                @Override
                public void positiveWork() {
                    mDialogListener.onResponse(POSITIVE, null);
                    hide();
                }
            };
        }

    }

    public CommonDialog setEditable() {
        ISEDITABLE = true;
        return this;
    }

    private void addContentView(View view) {
        if (content_rl.getVisibility() != View.VISIBLE) {
            content_rl.setVisibility(View.VISIBLE);
        }
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) content_rl.getLayoutParams();
        content_rl.addView(view, lp);
    }
}
