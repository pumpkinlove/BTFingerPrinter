package com.miaxis.btfingerprinter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            holder.tvUsercode.setText(user.getUsercode());
            holder.tvUsername.setText(user.getName());
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
        void onClick(View view, int position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_usercode)
        TextView tvUsercode;
        @BindView(R.id.tv_username)
        TextView tvUsername;
        @BindView(R.id.ll_user)
        LinearLayout llUser;

        private UserManageListener listener;

        ViewHolder(View view, UserManageListener listener) {
            super(view);
            ButterKnife.bind(this, view);
            this.listener = listener;
        }

        @OnClick(R.id.ll_user)
        void onClick(View view) {
            listener.onClick(view, getPosition());
        }


    }
}
