package com.release.architecture.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.core.content.ContextCompat;

import com.release.architecture.base.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author yancheng
 * @create 2019/3/22
 * @Describe
 */
public class StateLayout extends FrameLayout {

    public static final int STATUS_HIDE = 1001;
    public static final int STATUS_SHOW = 1002;
    public static final int STATUS_LOADING = 1;
    public static final int STATUS_ERROR = 2;
    public static final int STATUS_NO_DATA = 3;
    private Context mContext;
    private OnRetryListener mOnRetryListener;
    private int mEmptyStatus = STATUS_HIDE;
    private int mBgColor;
    private String msg;

    FrameLayout mEmptyLayout;
    TextView mTvEmptyMessage;
    View mRlEmptyContainer;
    ImageView mDefIv, mLoadingIv;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    /**
     * 初始化
     */
    private void init(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        try {
            mBgColor = a.getColor(R.styleable.EmptyLayout_background_color, Color.TRANSPARENT);
        } finally {
            a.recycle();
        }
        View.inflate(mContext, R.layout.base_layout_state_view, this);
        mEmptyLayout = findViewById(R.id.fl_empty_layout);
        mRlEmptyContainer = findViewById(R.id.rl_empty_container);
        mTvEmptyMessage = findViewById(R.id.tv_no_data);
        mLoadingIv = findViewById(R.id.vLoadingIv);
        mDefIv = findViewById(R.id.vDefIv);
        mEmptyLayout.setBackgroundColor(mBgColor);
        _switchEmptyView();

        mTvEmptyMessage.setOnClickListener(v -> {
            if (mOnRetryListener != null) {
                mOnRetryListener.onRetry();
            }
        });
    }

    /**
     * 设置背景颜色
     * @param color
     */
    public void setBgColor(@ColorInt int color){
        mEmptyLayout.setBackgroundColor(color);
    }

    /**
     * 隐藏视图
     */
    public void hide() {
        mEmptyStatus = STATUS_HIDE;
        _switchEmptyView();
    }

    /**
     * 显示视图
     */
    public void show() {
        mEmptyStatus = STATUS_SHOW;
        _switchEmptyView();
    }

    /**
     * 设置状态
     *
     * @param emptyStatus
     */
    public void setEmptyStatus(@EmptyStatus int emptyStatus) {
        mEmptyStatus = emptyStatus;
        _switchEmptyView();
    }

    /**
     * 获取状态
     *
     * @return 状态
     */
    public int getEmptyStatus() {
        return mEmptyStatus;
    }

    /**
     * 设置异常消息
     *
     * @param msg 显示消息
     */
    public void setStateMessage(String msg) {
        this.msg = msg;
        mTvEmptyMessage.setText(msg);
    }


    /**
     * 设置图片
     *
     * @param resid
     */
    public void setStateIcon(@DrawableRes int resid) {
        mDefIv.setImageDrawable(ContextCompat.getDrawable(mContext, resid));
    }


    /**
     * 切换视图
     */
    private void _switchEmptyView() {
        switch (mEmptyStatus) {
            case STATUS_LOADING:
                setVisibility(VISIBLE);
                mRlEmptyContainer.setVisibility(GONE);
                mLoadingIv.setVisibility(VISIBLE);
                break;
            case STATUS_NO_DATA:
                setVisibility(VISIBLE);
                setStateMessage(mContext.getResources().getString(R.string.base_no_data));
                setStateIcon(R.drawable.base_ic_default_empty);
                mLoadingIv.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_ERROR:
                setVisibility(VISIBLE);
                setStateMessage(mContext.getResources().getString(R.string.base_data_error));
                setStateIcon(R.drawable.base_ic_default_error);
                mLoadingIv.setVisibility(GONE);
                mRlEmptyContainer.setVisibility(VISIBLE);
                break;
            case STATUS_HIDE:
                setVisibility(GONE);
                break;
            case STATUS_SHOW:
                setVisibility(VISIBLE);
                break;
        }
    }

    /**
     * 设置重试监听器
     *
     * @param retryListener 监听器
     */
    public void setRetryListener(OnRetryListener retryListener) {
        this.mOnRetryListener = retryListener;
    }

    /**
     * 点击重试监听器
     */
    public interface OnRetryListener {
        void onRetry();
    }


    @Retention(RetentionPolicy.SOURCE)
    @IntDef({STATUS_LOADING, STATUS_ERROR, STATUS_NO_DATA})
    public @interface EmptyStatus {
    }
}