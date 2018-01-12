package com.miaxis.btfingerprinter.event;

/**
 * Created by xu.nan on 2017/8/21.
 */

public class AnalysisDoneEvent {
    private boolean success;
    private int orderCode;
    private String reMsg;
    private byte[] reBytes;

    public AnalysisDoneEvent(boolean success, int orderCode, String reMsg, byte[] reBytes) {
        this.success = success;
        this.orderCode = orderCode;
        this.reMsg = reMsg;
        this.reBytes = reBytes;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(int orderCode) {
        this.orderCode = orderCode;
    }

    public String getReMsg() {
        return reMsg;
    }

    public void setReMsg(String reMsg) {
        this.reMsg = reMsg;
    }

    public byte[] getReBytes() {
        return reBytes;
    }

    public void setReBytes(byte[] reBytes) {
        this.reBytes = reBytes;
    }
}
