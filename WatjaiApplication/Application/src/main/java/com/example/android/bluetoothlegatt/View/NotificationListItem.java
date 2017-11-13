package com.example.android.bluetoothlegatt.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.View.BaseCustomViewGroup;
import com.example.android.bluetoothlegatt.View.state.BundleSavedState;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class NotificationListItem extends BaseCustomViewGroup {

    TextView tvTime;
    TextView tvDescription;
    ImageView readStatus;

    public NotificationListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public NotificationListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public NotificationListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public NotificationListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.alert_description, this);
    }

    private void initInstances() {
        // findViewById here
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvDescription = (TextView) findViewById(R.id.tvNotificationDescription);
        readStatus = (ImageView) findViewById(R.id.readStatus);

    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width * 1/3;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(width,height);
    }

    public void setTimeText(String text){
        tvTime.setText(text);
    }

    public void setDescriptionText(String text){
        tvDescription.setText(text);
    }

    public void setReadStatus() {
        readStatus.setImageResource(R.drawable.bg_listnotificationpng);
    }
}
