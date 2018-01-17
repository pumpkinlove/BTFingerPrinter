package com.miaxis.btfingerprinter.event;

import com.miaxis.btfingerprinter.bean.User;

/**
 * Created by xu.nan on 2018/1/17.
 */

public class GetFingerEvent {

    private int fingerId;
    private User mUser;

    public GetFingerEvent() {
    }

    public GetFingerEvent(int fingerId, User mUser) {
        this.fingerId = fingerId;
        this.mUser = mUser;
    }

    public int getFingerId() {
        return fingerId;
    }

    public void setFingerId(int fingerId) {
        this.fingerId = fingerId;
    }

    public User getmUser() {
        return mUser;
    }

    public void setmUser(User mUser) {
        this.mUser = mUser;
    }
}
