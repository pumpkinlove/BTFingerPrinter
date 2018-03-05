package com.miaxis.btfingerprinter.view.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.bean.User;
import com.miaxis.btfingerprinter.view.fragment.UserDetailFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class FingerOpLayout extends LinearLayout {

    @BindView(R.id.tv_finger_id)
    TextView tvFingerId;
    @BindView(R.id.ll_finger_op)
    LinearLayout llFingerOp;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.iv_mod)
    ImageView ivMod;
    @BindView(R.id.iv_del)
    ImageView ivDel;
    private EventBus eventBus;
    @BindColor(R.color.gray_dark)
    int gray;
    @BindColor(R.color.blue_band_dark)
    int dark;
    private int fingerId;
    private User user;

    private UserDetailFragment.OnOperatingListener listener;

    public FingerOpLayout(Context context) {
        super(context);
        init();
    }

    public FingerOpLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FingerOpLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void init() {
        View v = inflate(getContext(), R.layout.view_finger_op, this);
        ButterKnife.bind(this, v);
    }

    public void setTvFingerId(int fingerId) {
        tvFingerId.setText("Finger " + fingerId);
    }

    public void setHasFinger(boolean hasFinger) {
        if (hasFinger) {
            llFingerOp.setBackground(getResources().getDrawable(R.drawable.green_gradient_bg));
            ivAdd.setVisibility(GONE);
            ivMod.setVisibility(VISIBLE);
            ivDel.setVisibility(VISIBLE);
            tvFingerId.setTextColor(gray);
        } else {
            llFingerOp.setBackground(null);
            tvFingerId.setTextColor(gray);
            ivAdd.setVisibility(VISIBLE);
            ivMod.setVisibility(GONE);
            ivDel.setVisibility(GONE);
        }
    }

    public void setOnOperatingListener(UserDetailFragment.OnOperatingListener listener, int fingerId, User user) {
        this.fingerId = fingerId;
        this.listener = listener;
        this.user = user;
    }

    @OnClick(R.id.iv_add)
    void onAddFinger() {
        if (listener != null) {
            listener.onAddFinger(user, fingerId);
        }
    }

    @OnClick(R.id.iv_mod)
    void onModFinger() {
        if (listener != null) {
            listener.onModFinger(user, fingerId);
        }
    }

    @OnClick(R.id.iv_del)
    void onDelFinger() {
        if (listener != null) {
            listener.onDelFinger(user, fingerId);
        }
    }

}
