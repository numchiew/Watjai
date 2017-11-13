package com.example.android.bluetoothlegatt.View;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bluetoothlegatt.R;
import com.example.android.bluetoothlegatt.View.state.BundleSavedState;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class HistoryListItem extends BaseCustomViewGroup {

    TextView tvMeasuringId;
    TextView tvHeartRate;
    TextView tvDateTime;

    public HistoryListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public HistoryListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public HistoryListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public HistoryListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.history_description, this);
    }

    private void initInstances() {
        // findViewById here
        tvMeasuringId = (TextView) findViewById(R.id.tvMeasuringId);
        tvHeartRate = (TextView) findViewById(R.id.tvHeartRate);
        tvDateTime = (TextView) findViewById(R.id.tvDateTime);

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
        int height = width * 2/5;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);

        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);

        setMeasuredDimension(width,height);
    }

    public void setMeasuringId(String text){
        tvMeasuringId.setText(text);
    }

    public void setHeartRate(String text){
        tvHeartRate.setText(text);
    }

    public void setDateTime(String text){
        tvDateTime.setText(text);
    }

}
