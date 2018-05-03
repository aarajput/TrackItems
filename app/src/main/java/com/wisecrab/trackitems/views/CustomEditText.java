package com.wisecrab.trackitems.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import com.wisecrab.trackitems.R;

public class CustomEditText extends AppCompatEditText {

    private int textInputLayoutId =-1;
    private TextInputLayout textInputLayout=null;

    public CustomEditText(Context context) {
        super(context);
        init(null);
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void init(@Nullable AttributeSet attrs) {
        if(attrs!=null) {
            TypedArray ta = this.getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
            textInputLayoutId = ta.getResourceId(R.styleable.CustomEditText_text_input_style_id,-1);
            ta.recycle();
        }

        this.addTextChangedListener(new TextChangerListener());
    }



    private @Nullable TextInputLayout getTextInputLayout() {
        if (textInputLayoutId!=-1) {
            if (textInputLayout==null)
            {
                View v = this.getRootView().findViewById(textInputLayoutId);
                if (v instanceof TextInputLayout)
                return (TextInputLayout) v;
            }
        }
        return null;
    }

    private class TextChangerListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (getTextInputLayout()!=null)
                getTextInputLayout().setError(null);
        }
    }

}
