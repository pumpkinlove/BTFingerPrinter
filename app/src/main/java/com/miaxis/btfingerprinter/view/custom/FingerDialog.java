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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;

import butterknife.BindBitmap;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_finger_id)
    TextView tvFingerId;
    @BindView(R.id.ll_username)
    LinearLayout llUsername;
    @BindView(R.id.ll_finger_id)
    LinearLayout llFingerId;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private View.OnClickListener cancelListener;

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
        initView();
        return view;
    }

    private void initView() {
        gifFinger.setMovieResource(R.raw.put_finger);
        gifFinger.setVisibility(View.VISIBLE);
        btnCancel.setVisibility(View.VISIBLE);
        ivFingerResult.setVisibility(View.GONE);
        tvShowInfo.setText("Press fingerprints");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setVerifyResult(boolean success, String username, int fingerId) {
        gifFinger.setVisibility(View.GONE);
        btnCancel.setVisibility(View.GONE);
        ivFingerResult.setVisibility(View.VISIBLE);
        if (success) {
            llUsername.setVisibility(View.VISIBLE);
            llFingerId.setVisibility(View.VISIBLE);
            tvShowInfo.setVisibility(View.GONE);
            tvUsername.setText(username);
            tvFingerId.setText(fingerId + "");
            ivFingerResult.setImageBitmap(bmpFingerSuccess);
        } else {
            llUsername.setVisibility(View.GONE);
            llFingerId.setVisibility(View.GONE);
            tvShowInfo.setVisibility(View.VISIBLE);
            tvShowInfo.setText(R.string.verify_failed);
            ivFingerResult.setImageBitmap(bmpFingerFail);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @OnClick(R.id.btn_cancel)
    void onCancel(View view) {
        if (cancelListener != null) {
            cancelListener.onClick(view);
        }
    }

    public void setCancelListener(View.OnClickListener cancelListener) {
        this.cancelListener = cancelListener;
    }
}
