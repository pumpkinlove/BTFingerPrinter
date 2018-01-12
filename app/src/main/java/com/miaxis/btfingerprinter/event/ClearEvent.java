package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/8/22.
 */

public class ClearEvent {

    private boolean clearAll;

    public ClearEvent(boolean clearAll) {
        this.clearAll = clearAll;
    }

    public boolean isClearAll() {
        return clearAll;
    }

    public void setClearAll(boolean clearAll) {
        this.clearAll = clearAll;
    }
}
