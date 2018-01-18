package com.miaxis.btfingerprinter.view.custom;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.bean.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xu.nan on 2016/10/14.
 */

public class RegisterDialog extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.gl_fingers)
    GridLayout glFingers;
    @BindView(R.id.et_usercode)
    EditText etUsercode;

    private User user;

    private View.OnClickListener confirmListener;
    private View.OnClickListener cancelListener;
    private RegFingerListener regFingerListener;

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            Window w = dialog.getWindow();
            if (w != null) {
                w.setLayout((int) (dm.widthPixels * 0.9), ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.dialog_register, container);
        unbinder = ButterKnife.bind(this, view);
        btnConfirm.setOnClickListener(confirmListener);
        btnCancel.setOnClickListener(cancelListener);
        initView();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        etUsername.setText("");
        etUsercode.setText("");
    }

    private void initView() {
        int count = glFingers.getChildCount();
        for (int i = 0; i < count; i++) {
            Button btn = (Button) glFingers.getChildAt(i);
            final int finalI = i;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (regFingerListener == null) {
                        return;
                    }
                    regFingerListener.onRegFinger(finalI + 1); //fingerId 1-10
                }
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void setConfirmListener(View.OnClickListener listener) {
        confirmListener = listener;
    }

    public void setCancelListener(View.OnClickListener listener) {
        cancelListener = listener;
    }

    public void setRegFingerListener(RegFingerListener listener) {
        regFingerListener = listener;
    }

    public String getUsername() {
        return etUsername.getText().toString();
    }

    public String getUsercode() {
        return etUsercode.getText().toString();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public interface RegFingerListener {
        void onRegFinger(int fingerId);
    }

    public void setFingerColor(int fingerId) {
        Button btnFinger = (Button) glFingers.getChildAt(fingerId - 1);
        btnFinger.setTextColor(getActivity().getResources().getColor(R.color.green_dark));
    }

}
