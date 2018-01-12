package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/8/22.
 */

public class ShowMsgEvent {
    private String message;
    private int color;

    public ShowMsgEvent(String message, int color) {
        this.message = message;
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
