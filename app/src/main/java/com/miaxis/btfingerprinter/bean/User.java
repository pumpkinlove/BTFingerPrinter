package com.miaxis.btfingerprinter.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by xu.nan on 2018/1/12.
 */

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private byte[] finger0;
    private byte[] finger1;
    private byte[] finger2;
    private byte[] finger3;
    private byte[] finger4;
    private byte[] finger5;
    private byte[] finger6;
    private byte[] finger7;
    private byte[] finger8;
    private byte[] finger9;
    @Generated(hash = 1048334036)
    public User(Long id, String name, byte[] finger0, byte[] finger1,
            byte[] finger2, byte[] finger3, byte[] finger4, byte[] finger5,
            byte[] finger6, byte[] finger7, byte[] finger8, byte[] finger9) {
        this.id = id;
        this.name = name;
        this.finger0 = finger0;
        this.finger1 = finger1;
        this.finger2 = finger2;
        this.finger3 = finger3;
        this.finger4 = finger4;
        this.finger5 = finger5;
        this.finger6 = finger6;
        this.finger7 = finger7;
        this.finger8 = finger8;
        this.finger9 = finger9;
    }
    @Generated(hash = 586692638)
    public User() {
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
    public byte[] getFinger0() {
        return this.finger0;
    }
    public void setFinger0(byte[] finger0) {
        this.finger0 = finger0;
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

    public int getFingerCount() {
        int count = 0;
        if (finger0 != null && finger0.length > 0) {
            count ++;
        }

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
        return count;
    }

}
