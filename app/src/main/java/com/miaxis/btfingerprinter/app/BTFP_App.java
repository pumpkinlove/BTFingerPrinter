package com.miaxis.btfingerprinter.app;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.miaxis.btfingerprinter.event.ToastEvent;
import com.miaxis.btfingerprinter.greendao.GreenDaoContext;
import com.miaxis.btfingerprinter.greendao.gen.DaoMaster;
import com.miaxis.btfingerprinter.greendao.gen.DaoSession;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BTFP_App extends Application {

    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private static BTFP_App app;

    public static BTFP_App getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        app = this;
        initDbHelp();
    }

    private void initDbHelp() {
        mHelper = new DaoMaster.DevOpenHelper(new GreenDaoContext(this), "BtFingerPrinter.db", null);
        db = mHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onToastEvent(ToastEvent e) {
        Toast.makeText(this, e.getToast(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        EventBus.getDefault().unregister(this);
    }

    public DaoSession getDaoSession() {
        mDaoSession.clear();
        return mDaoSession;
    }
    public SQLiteDatabase getDb() {
        return db;
    }
}
