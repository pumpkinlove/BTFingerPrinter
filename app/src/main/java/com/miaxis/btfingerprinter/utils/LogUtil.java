package com.miaxis.btfingerprinter.utils;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by xu.nan on 2017/9/21.
 */

public class LogUtil {
    public static void writeLog(String logContent) {
        BufferedWriter bw = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "蓝牙二代证日志.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            bw = new BufferedWriter(new FileWriter(file, true));
            bw.write(DateUtil.getCurDateTime() + "     " + logContent + "\r\n");
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
