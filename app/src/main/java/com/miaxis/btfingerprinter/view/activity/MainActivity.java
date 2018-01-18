package com.miaxis.btfingerprinter.view.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.scan.ListScanCallback;
import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.adapter.BTDeviceAdatpter;
import com.miaxis.btfingerprinter.event.ToastEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.rv_bt_device)
    RecyclerView rvBtDevice;
    @BindView(R.id.tv_search)
    TextView tvSearch;
    private BleManager bleManager;
    private List<ScanResult> resultList = new ArrayList<>();
    private BTDeviceAdatpter adatpter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initView();
        onSearch();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void initData() {
        bleManager = new BleManager(this);
        adatpter = new BTDeviceAdatpter(resultList, this);
    }

    @Override
    protected void initView() {
        getActionBar().setTitle(getString(R.string.MainActivity_title) + "  " + getVersionName());
        rvBtDevice.setLayoutManager(new LinearLayoutManager(this));
        rvBtDevice.setAdapter(adatpter);
        adatpter.setListener(new BTDeviceAdatpter.OnItemListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(MainActivity.this, PrintActivity.class);
                i.putExtra("ScanResult", resultList.get(position));
                startActivity(i);
            }
        });
    }

    String getVersionName() {
        PackageManager manager = getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "";
        }
    }

    @OnClick(R.id.tv_search)
    void onSearch() {
        tvSearch.setText(R.string.searching);
        tvSearch.setClickable(false);
        if (!bleManager.isSupportBle()) {
            EventBus.getDefault().post(new ToastEvent("Ble not support"));
            return;
        }
        if (!bleManager.isBlueEnable()) {
            bleManager.enableBluetooth();
            EventBus.getDefault().post(new ToastEvent("Starting Bluetooth..."));
        }
        bleManager.refreshDeviceCache();
        if (!bleManager.isInScanning()) {
            bleManager.closeBluetoothGatt();
            resultList.clear();
            adatpter.notifyDataSetChanged();
            bleManager.scanDevice(new ListScanCallback(5000) {

                @Override
                public void onScanning(ScanResult result) {
                    if (!TextUtils.isEmpty(result.getDevice().getName())) {
                        resultList.add(result);
                        adatpter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onScanComplete(ScanResult[] results) {
                    if (results == null || results.length == 0) {
                        EventBus.getDefault().post(new ToastEvent("No Bluetooth Device"));
                    }
                    tvSearch.setText(R.string.search);
                    tvSearch.setClickable(true);
                }
            });
        }
    }

}
