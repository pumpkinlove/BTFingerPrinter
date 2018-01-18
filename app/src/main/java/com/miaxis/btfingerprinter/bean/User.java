package com.miaxis.btfingerprinter.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 用户
 * Created by xu.nan on 2018/1/12.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String usercode;
    private String name;
    private byte[] finger1;
    private byte[] finger2;
    private byte[] finger3;
    private byte[] finger4;
    private byte[] finger5;
    private byte[] finger6;
    private byte[] finger7;
    private byte[] finger8;
    private byte[] finger9;
    private byte[] finger10;
    @Transient
    private boolean isModing;

    @Generated(hash = 1252626552)
    public User(Long id, String usercode, String name, byte[] finger1,
            byte[] finger2, byte[] finger3, byte[] finger4, byte[] finger5,
            byte[] finger6, byte[] finger7, byte[] finger8, byte[] finger9,
            byte[] finger10) {
        this.id = id;
        this.usercode = usercode;
        this.name = name;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.finger3 = finger3;
        this.finger4 = finger4;
        this.finger5 = finger5;
        this.finger6 = finger6;
        this.finger7 = finger7;
        this.finger8 = finger8;
        this.finger9 = finger9;
        this.finger10 = finger10;
    }

    @Generated(hash = 586692638)
    public User() {
    }

    public int getFingerCount() {
        int count = 0;

        if (finger1 != null && finger1.length > 0) {
            count ++;
        }

        if (finger2 != null && finger2.length > 0) {
            count ++;
        }

        if (finger3 != null && finger3.length > 0) {
            count ++;
        }

        if (finger4 != null && finger4.length > 0) {
            count ++;
        }

        if (finger5 != null && finger5.length > 0) {
            count ++;
        }

        if (finger6 != null && finger6.length > 0) {
            count ++;
        }

        if (finger7 != null && finger7.length > 0) {
            count ++;
        }

        if (finger8 != null && finger8.length > 0) {
            count ++;
        }

        if (finger9 != null && finger9.length > 0) {
            count ++;
        }

        if (finger10 != null && finger10.length > 0) {
            count ++;
        }
        return count;
    }

    public byte[] getFingerById(int fingerId) {
        switch (fingerId) {
            case 1:
                return finger1;
            case 2:
                return finger2;
            case 3:
                return finger3;
            case 4:
                return finger4;
            case 5:
                return finger5;
            case 6:
                return finger6;
            case 7:
                return finger7;
            case 8:
                return finger8;
            case 9:
                return finger9;
            case 10:
                return finger10;
            default:
                return null;
        }
    }

    public void setFingerById(byte[] fingerData, int fingerId) {
        switch (fingerId) {
            case 1:
                finger1 = fingerData;
                break;
            case 2:
                finger2 = fingerData;
                break;
            case 3:
                finger3 = fingerData;
                break;
            case 4:
                finger4 = fingerData;
                break;
            case 5:
                finger5 = fingerData;
                break;
            case 6:
                finger6 = fingerData;
                break;
            case 7:
                finger7 = fingerData;
                break;
            case 8:
                finger8 = fingerData;
                break;
            case 9:
                finger9 = fingerData;
                break;
            case 10:
                finger10 = fingerData;
                break;
        }
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getFinger1() {
        return this.finger1;
    }

    public void setFinger1(byte[] finger1) {
        this.finger1 = finger1;
    }

    public byte[] getFinger2() {
        return this.finger2;
    }

    public void setFinger2(byte[] finger2) {
        this.finger2 = finger2;
    }

    public byte[] getFinger3() {
        return this.finger3;
    }

    public void setFinger3(byte[] finger3) {
        this.finger3 = finger3;
    }

    public byte[] getFinger4() {
        return this.finger4;
    }

    public void setFinger4(byte[] finger4) {
        this.finger4 = finger4;
    }

    public byte[] getFinger5() {
        return this.finger5;
    }

    public void setFinger5(byte[] finger5) {
        this.finger5 = finger5;
    }

    public byte[] getFinger6() {
        return this.finger6;
    }

    public void setFinger6(byte[] finger6) {
        this.finger6 = finger6;
    }

    public byte[] getFinger7() {
        return this.finger7;
    }

    public void setFinger7(byte[] finger7) {
        this.finger7 = finger7;
    }

    public byte[] getFinger8() {
        return this.finger8;
    }

    public void setFinger8(byte[] finger8) {
        this.finger8 = finger8;
    }

    public byte[] getFinger9() {
        return this.finger9;
    }

    public void setFinger9(byte[] finger9) {
        this.finger9 = finger9;
    }

    public byte[] getFinger10() {
        return this.finger10;
    }

    public void setFinger10(byte[] finger10) {
        this.finger10 = finger10;
    }

    public boolean isModing() {
        return isModing;
    }

    public void setModing(boolean moding) {
        isModing = moding;
    }

    public String getUsercode() {
        return this.usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

}
