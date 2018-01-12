package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/12/4.
 */

public class SendPackEvent {
    private int packNo;
    private byte[] data;

    public SendPackEvent(int packNo, byte[] data) {
        this.packNo = packNo;
        this.data = data;
    }

    public int getPackNo() {
        return packNo;
    }

    public void setPackNo(int packNo) {
        this.packNo = packNo;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
