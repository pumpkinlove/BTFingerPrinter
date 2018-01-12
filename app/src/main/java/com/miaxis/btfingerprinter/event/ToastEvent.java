package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/8/8.
 */

public class ToastEvent {
    private String toast;

    public ToastEvent(String toast) {
        this.toast = toast;
    }

    public String getToast() {
        return toast;
    }

    public void setToast(String toast) {
        this.toast = toast;
    }
}
