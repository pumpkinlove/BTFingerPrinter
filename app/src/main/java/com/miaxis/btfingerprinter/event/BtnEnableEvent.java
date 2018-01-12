package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2018/1/8.
 */

public class BtnEnableEvent {
    private boolean enable;

    public BtnEnableEvent(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
