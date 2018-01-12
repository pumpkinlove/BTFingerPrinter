package com.miaxis.btfingerprinter.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xu.nan on 2017/8/23.
 */

public class DateUtil {

    public static final String calcTimeMillis(long time) {
        long hour = time / 1000 / 3600;
        long restSec = (time / 1000) % 3600;
        long min = restSec / 60;
        restSec = restSec % 60;
        return hour + " 小时 " + min + " 分钟 " + restSec + "秒";
    }

    public static String getCurDateTime() {
        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return myFmt.format(now);
    }

    public static String getCurDateTime2() {
        Date now = new Date();
        SimpleDateFormat myFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return myFmt.format(now);
    }
}
