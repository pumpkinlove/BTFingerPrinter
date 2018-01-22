package com.miaxis.btfingerprinter.view.custom;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xu.nan on 2017/7/10.
 */

public class FingerDialog extends DialogFragment {

    @BindView(R.id.gif_finger)
    GifView gifFinger;
    @BindView(R.id.iv_finger_result)
    ImageView ivFingerResult;
    Unbinder unbinder;
    @BindBitmap(R.mipmap.finger_succes)
    Bitmap bmpFingerSuccess;
    @BindBitmap(R.mipmap.finger_fail)
    Bitmap bmpFingerFail;
    @BindView(R.id.tv_show_info)
    TextView tvShowInfo;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.7), ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_finger, container);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    private void initView() {
        gifFinger.setMovieResource(R.raw.put_finger);
        gifFinger.setVisibility(View.VISIBLE);
        ivFingerResult.setVisibility(View.GONE);
        tvShowInfo.setText("请按指纹");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder.unbind();
    }

    public void setVerifyResult(boolean success) {
        gifFinger.setVisibility(View.GONE);
        ivFingerResult.setVisibility(View.VISIBLE);
        if (success) {
            ivFingerResult.setImageBitmap(bmpFingerSuccess);
            tvShowInfo.setText("比对通过");
        } else {
            ivFingerResult.setImageBitmap(bmpFingerFail);
            tvShowInfo.setText("比对失败");
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        EventBus.getDefault().post(new DeviceCancelEvent());
    }
}
