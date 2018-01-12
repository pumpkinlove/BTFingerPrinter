package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2018/1/10.
 */

public class ScrollPaperEvent {
    private int num;

    public ScrollPaperEvent(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
