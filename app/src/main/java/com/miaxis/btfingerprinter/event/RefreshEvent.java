package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2018/1/17.
 */

public class RefreshEvent {
    private boolean reLoadFromDb;

    public RefreshEvent(boolean reLoadFromDb) {
        this.reLoadFromDb = reLoadFromDb;
    }

    public boolean isReLoadFromDb() {
        return reLoadFromDb;
    }

    public void setReLoadFromDb(boolean reLoadFromDb) {
        this.reLoadFromDb = reLoadFromDb;
    }
}
