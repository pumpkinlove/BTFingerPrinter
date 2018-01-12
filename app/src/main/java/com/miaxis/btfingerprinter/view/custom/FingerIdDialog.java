package com.miaxis.btfingerprinter.view.custom;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.miaxis.btfingerprinter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by xu.nan on 2016/10/14.
 */

public class FingerIdDialog extends DialogFragment {


    Unbinder unbinder;
    @BindView(R.id.et_finger_id)
    EditText etFingerId;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.btn_cancel)
    Button btnCancel;

    private View.OnClickListener confirmListener;
    private View.OnClickListener cancelListener;

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
        View view = inflater.inflate(R.layout.dialog_alert, container);
        unbinder = ButterKnife.bind(this, view);
        btnConfirm.setOnClickListener(confirmListener);
        btnCancel.setOnClickListener(cancelListener);
        return view;
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

    public int getFingerId() {
        String idStr = etFingerId.getText().toString().trim();
        if (TextUtils.isEmpty(idStr)) {
            return 0;
        }
        return Integer.valueOf(idStr);
    }

}
