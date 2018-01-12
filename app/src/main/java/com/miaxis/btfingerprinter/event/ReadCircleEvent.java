package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/8/22.
 */

public class ReadCircleEvent {
    private int step;

    public ReadCircleEvent(int step) {
        this.step = step;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
