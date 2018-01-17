package com.miaxis.btfingerprinter.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.adapter.UserAdapter;
import com.miaxis.btfingerprinter.app.BTFP_App;
import com.miaxis.btfingerprinter.bean.User;
import com.miaxis.btfingerprinter.event.GetFingerEvent;
import com.miaxis.btfingerprinter.view.custom.SimpleDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserListFragment extends Fragment {

    @BindView(R.id.rv_user)
    RecyclerView rvUser;
    Unbinder unbinder;
    private OnFragmentInteractionListener mListener;

    private List<User> userList;
    private UserAdapter userAdapter;

    public UserListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initData();
        initView();
        return view;
    }

    private void initData() {
        userList = BTFP_App.getInstance().getDaoSession().getUserDao().loadAll();
        userAdapter = new UserAdapter(userList, getActivity(), new UserAdapter.UserManageListener() {
            @Override
            public boolean onModify(int position) {
                userList.get(position).setModing(true);
                return true;
            }

            @Override
            public boolean onDelete(final int position) {
                final SimpleDialog sd = new SimpleDialog();
                sd.setMessage("Are you sure to delete User " + userList.get(position).getName() + " ? ");
                sd.setConfirmListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        BTFP_App.getInstance().getDaoSession().getUserDao().delete(userList.get(position));
                        userList.remove(position);
                        userAdapter.notifyDataSetChanged();
                        sd.dismiss();

                    }
                });
                sd.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sd.dismiss();
                    }
                });
                sd.show(getFragmentManager(), "delete");
                return true;
            }

            @Override
            public boolean onRegFinger(int position, int fingerId) {
                mListener.onRegFinger(userList.get(position), fingerId);
                return true;
            }

            @Override
            public boolean onConfirm(int position) {
                userList.get(position).setModing(false);
                BTFP_App.getInstance().getDaoSession().getUserDao().save(userList.get(position));
                userAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onCancel(int position) {
                userList.get(position).setModing(false);
                userList = BTFP_App.getInstance().getDaoSession().getUserDao().loadAll();
                userAdapter.setUserList(userList);
                userAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    private void initView() {
        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUser.setAdapter(userAdapter);
        rvUser.setItemViewCacheSize(100);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onRegFinger(User user, int fingerId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGetFingerEvent(GetFingerEvent e) {
        Log.e("fragment", "getfinger");

        userAdapter.notifyDataSetChanged();


    }

}
