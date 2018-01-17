package com.miaxis.btfingerprinter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.bean.User;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;

/**
 * Created by xu.nan on 2018/1/16.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private Context mContext;
    private UserManageListener listener;

    public UserAdapter(List<User> userList, Context mContext, @NonNull UserManageListener listener) {
        this.userList = userList;
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_user, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = userList.get(position);
        if (user != null) {
            holder.setIsRecyclable(false);
            holder.tvUserId.setText(user.getId() + "");
            holder.etUsername.setText(user.getName());
            holder.tvFingerCount.setText(user.getFingerCount() + " finger(s) collected");
            if (user.isModing()) {
                holder.btnModify.setVisibility(View.GONE);
                holder.btnDelete.setVisibility(View.GONE);
                holder.btnConfirm.setVisibility(View.VISIBLE);
                holder.btnCancel.setVisibility(View.VISIBLE);
                holder.etUsername.setEnabled(true);
            } else {
                holder.btnModify.setVisibility(View.VISIBLE);
                holder.btnDelete.setVisibility(View.VISIBLE);
                holder.btnConfirm.setVisibility(View.GONE);
                holder.btnCancel.setVisibility(View.GONE);
                holder.etUsername.setEnabled(false);
            }
            for (int i = 1; i <= 10; i ++) {
                byte[] fingerData = user.getFingerById(i);
                Button btnFinger = (Button) holder.glFinger.getChildAt(i - 1);
                if (fingerData != null && fingerData.length > 0) {
                    btnFinger.setTextColor(holder.colorGreenDark);
                } else {
                    btnFinger.setTextColor(holder.colorBlueDark3);
                }
                final int finalI = i;
                final int finalPosition = position;
                btnFinger.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onRegFinger(finalPosition, finalI);
                    }
                });
                btnFinger.setClickable(user.isModing());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        return 0;
    }

    public interface UserManageListener {
        //返回值判断事件是否传递
        boolean onModify(int position);
        boolean onDelete(int position);
        boolean onRegFinger(int position, int fingerId);
        boolean onConfirm(int position);
        boolean onCancel(int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_userId)
        TextView tvUserId;
        @BindView(R.id.et_username)
        EditText etUsername;
        @BindView(R.id.btn_modify)
        Button btnModify;
        @BindView(R.id.btn_delete)
        Button btnDelete;
        @BindView(R.id.btn_confirm)
        Button btnConfirm;
        @BindView(R.id.btn_cancel)
        Button btnCancel;
        @BindView(R.id.tv_finger_count)
        TextView tvFingerCount;
        @BindView(R.id.gl_fingers)
        GridLayout glFinger;

        @BindColor(R.color.blue_band_dark3)
        int colorBlueDark3;
        @BindColor(R.color.dark)
        int colorDark;
        @BindColor(R.color.green_dark)
        int colorGreenDark;

        private UserManageListener listener;

        ViewHolder(View view, UserManageListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.listener = listener;
        }

        @OnClick(R.id.btn_modify)
        void onModify(View view) {
            if (listener.onModify(getPosition())) {
                etUsername.setEnabled(true);
                etUsername.requestFocus();
                btnModify.setVisibility(View.GONE);
                btnDelete.setVisibility(View.GONE);
                btnConfirm.setVisibility(View.VISIBLE);
                btnCancel.setVisibility(View.VISIBLE);
                for (int i=0; i<glFinger.getChildCount(); i++) {
                    Button btnFinger = (Button) glFinger.getChildAt(i);
                    btnFinger.setClickable(true);
                }
            }
        }

        @OnClick(R.id.btn_delete)
        void onDelete() {
            listener.onDelete(getPosition());
        }

        @OnClick(R.id.btn_confirm)
        void onConfirm() {
            User user = userList.get(getPosition());
            user.setName(etUsername.getText().toString());

            if (listener.onConfirm(getPosition())) {
                etUsername.setEnabled(false);
                btnModify.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                for (int i=0; i<glFinger.getChildCount(); i++) {
                    Button btnFinger = (Button) glFinger.getChildAt(i);
                    btnFinger.setClickable(false);
                }
            }
        }

        @OnClick(R.id.btn_cancel)
        void onCancel() {
            if (listener.onCancel(getPosition())) {
                etUsername.setEnabled(false);
                btnModify.setVisibility(View.VISIBLE);
                btnDelete.setVisibility(View.VISIBLE);
                btnConfirm.setVisibility(View.GONE);
                btnCancel.setVisibility(View.GONE);
                for (int i=0; i<glFinger.getChildCount(); i++) {
                    Button btnFinger = (Button) glFinger.getChildAt(i);
                    btnFinger.setClickable(false);
                }
            }
        }

    }
}
