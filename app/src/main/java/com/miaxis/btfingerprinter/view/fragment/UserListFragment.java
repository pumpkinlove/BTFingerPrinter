package com.miaxis.btfingerprinter.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.adapter.UserAdapter;
import com.miaxis.btfingerprinter.app.BTFP_App;
import com.miaxis.btfingerprinter.bean.User;

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
        initData();
        initView();
        return view;
    }

    private void initData() {
        userList = BTFP_App.getInstance().getDaoSession().getUserDao().loadAll();
        userAdapter = new UserAdapter(userList, getActivity(), new UserAdapter.UserManageListener() {
            @Override
            public boolean onModify(int position) {
                return true;
            }

            @Override
            public boolean onDelete(int position) {
                return true;
            }

            @Override
            public boolean onRegFinger(int position, int fingerId) {
                return true;
            }

            @Override
            public boolean onConfirm(int position) {
                return true;
            }

            @Override
            public boolean onCancel(int position) {
                return true;
            }
        });
    }

    private void initView() {
        rvUser.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvUser.setAdapter(userAdapter);
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
