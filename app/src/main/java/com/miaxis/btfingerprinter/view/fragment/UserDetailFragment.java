package com.miaxis.btfingerprinter.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.bean.User;
import com.miaxis.btfingerprinter.view.custom.FingerOpLayout;
import com.miaxis.btfingerprinter.view.custom.SimpleDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class UserDetailFragment extends Fragment {

    private static final String PARAM1_USER = "User";
    @BindView(R.id.et_username)
    EditText etUsername;
    @BindView(R.id.tv_usercode)
    TextView tvUsercode;
    @BindView(R.id.gl_fingers)
    GridLayout glFingers;
    @BindView(R.id.btn_delete)
    Button btnDelete;
    Unbinder unbinder;

    private User mUser;

    private OnOperatingListener mListener;

    public UserDetailFragment() {
        // Required empty public constructor
    }

    public static UserDetailFragment newInstance(User mUser) {
        UserDetailFragment fragment = new UserDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM1_USER, mUser);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = (User) getArguments().getSerializable(PARAM1_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        etUsername.setText(mUser.getName());
        tvUsercode.setText(mUser.getUsercode());
        for (int i = 1; i <= 10; i ++) {
            FingerOpLayout fol = (FingerOpLayout) glFingers.getChildAt(i - 1);
            fol.setTvFingerId(i);
            byte[] fingerI = mUser.getFingerById(i);
            if (fingerI != null && fingerI.length > 0) {
                fol.setHasFinger(true);
            } else {
                fol.setHasFinger(false);
            }
            fol.setOnOperatingListener((OnOperatingListener) getActivity(), i, mUser);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnOperatingListener) {
            mListener = (OnOperatingListener) context;
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

    public interface OnOperatingListener {
        void onAddFinger(User user, int fingerId);
        void onModFinger(User user, int fingerId);
        void onDelFinger(User user, int fingerId);
    }
}
