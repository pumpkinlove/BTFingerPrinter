package com.miaxis.btfingerprinter.view.activity;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.exception.BleException;
import com.miaxis.btfingerprinter.R;
import com.miaxis.btfingerprinter.app.BTFP_App;
import com.miaxis.btfingerprinter.bean.User;
import com.miaxis.btfingerprinter.event.BtnEnableEvent;
import com.miaxis.btfingerprinter.event.ClearEvent;
import com.miaxis.btfingerprinter.event.ScrollPaperEvent;
import com.miaxis.btfingerprinter.event.SendPackEvent;
import com.miaxis.btfingerprinter.event.ServiceDiscoveredEvent;
import com.miaxis.btfingerprinter.event.ShowMsgEvent;
import com.miaxis.btfingerprinter.utils.BluetoothUUID;
import com.miaxis.btfingerprinter.utils.CodeUtil;
import com.miaxis.btfingerprinter.utils.DateUtil;
import com.miaxis.btfingerprinter.utils.OrderCode;
import com.miaxis.btfingerprinter.view.custom.RegisterDialog;
import com.miaxis.btfingerprinter.view.fragment.UserListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrintActivity extends BaseActivity implements UserListFragment.OnFragmentInteractionListener {

    private static final String TAG = "PrintActivity";
    private static final byte PROTOCOL_HEAD = 0x02;
    private static final byte PROTOCOL_END = 0x03;
    private static final int PRINT_SIZE = 24;

    @BindColor(R.color.red)
    int redColor;
    @BindColor(R.color.green_dark)
    int greenColor;
    @BindColor(R.color.dark)
    int darkColor;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.sv_message)
    ScrollView svMessage;
    @BindView(R.id.btn_reg_finger)
    Button btnRegFinger;
    @BindView(R.id.btn_search_finger)
    Button btnSearchFinger;
    @BindView(R.id.ll_buttons)
    LinearLayout llButtons;
    @BindView(R.id.fl_main)
    FrameLayout flMain;

    private BleManager bleManager;
    private int curOrderCode;
    private Menu menu;
    ScanResult result;

    private UserListFragment userListFragment;

    private byte[] cacheData = new byte[0];
    private String[] cachePrintContent = new String[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        bleManager.closeBluetoothGatt();

        super.onDestroy();
    }

    @Override
    protected void initData() {
        userListFragment = new UserListFragment();
        EventBus.getDefault().register(this);
        bleManager = new BleManager(this);
        result = getIntent().getParcelableExtra("ScanResult");
        if (result != null) {
            setTitle(result.getDevice().getName());
            bleManager.closeBluetoothGatt();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bleManager.connectDevice(result, true, callback);
            showMsg("Connecting...", darkColor);
        } else {
            showMsg("No Device", redColor);
        }
    }

    @Override
    protected void initView() {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_card_info, menu);

        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear:
                onClearEvent(new ClearEvent(true));
                break;

        }
        return true;
    }

    @Override
    public void onBackPressed() {

        if (userListFragment.isVisible()) {
            getFragmentManager().beginTransaction().remove(userListFragment).commit();
        } else {
            super.onBackPressed();
        }

    }

    private BleGattCallback callback = new BleGattCallback() {
        @Override
        public void onConnectError(BleException exception) {
            showMsg("Connect Error", redColor);
        }

        @Override
        public void onConnectSuccess(BluetoothGatt gatt, int status) {
            showMsg("Connected", greenColor);
        }

        @Override
        public void onDisConnected(BluetoothGatt gatt, int status, BleException exception) {
            showMsg("DisConnected", redColor);
        }

        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            showMsg("ConnectionStateChange " + status + " -> " + newState, darkColor);
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            showMsg("BluetoothServicesDiscovered", darkColor);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (status == BluetoothGatt.GATT_SUCCESS) {
                EventBus.getDefault().post(new ServiceDiscoveredEvent());
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        }
    };

    private void showMsg(String msg, int color) {
        EventBus.getDefault().post(new ShowMsgEvent(msg, color));
    }

    private boolean handleReturnSw1(byte sw1, StringBuilder reMsgSb) {
        switch (sw1) {
            case 0x00:
                reMsgSb.append("操作成功");
                return true;
            case 0x01:
                reMsgSb.append("操作失败");
                return false;
            case 0x04:
                reMsgSb.append("指纹数据库已满");
                return false;
            case 0x05:
                reMsgSb.append("无此用户");
                return false;
            case 0x07:
                reMsgSb.append("用户已存在");
                return false;
            case 0x08:
                reMsgSb.append("采集超时");
                return false;
            case 0x09:
                reMsgSb.append("空闲");
                return false;
            case 0x0A:
                reMsgSb.append("命令执行中");
                return false;
            case 0x0B:
                reMsgSb.append("有指纹按上");
                return false;
            case 0x0C:
                reMsgSb.append("无指纹按上");
                return false;
            case 0x0D:
                reMsgSb.append("指纹认证通过");
                return true;
            case 0x0E:
                reMsgSb.append("指纹认证失败");
                return false;
            case 0x0F:
                reMsgSb.append("处于安全等级1");
                return false;
            case 0x10:
                reMsgSb.append("处于安全等级2");
                return false;
            case 0x11:
                reMsgSb.append("处于安全等级3");
                return false;
            case 0x12:
                reMsgSb.append("处于安全等级4");
                return false;
            case 0x13:
                reMsgSb.append("处于安全等级5");
                return false;
            case 0x60:
                reMsgSb.append("无手指");
                return false;
            case 0x61:
                reMsgSb.append("搜索失败");
                return false;
            case 0x62:
                reMsgSb.append("生成模板失败");
                return false;
            case 0x63:
                reMsgSb.append("生成特征失败");
                return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onClearEvent(ClearEvent e) {
        if (e.isClearAll()) {
            tvMessage.setText("");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMsgEvent(ShowMsgEvent e) {
        SpannableString ss = new SpannableString(e.getMessage() + "\r\n");
        if (e.getColor() != 0) {
            ss.setSpan(new ForegroundColorSpan(e.getColor()), 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tvMessage.append(ss);
        svMessage.post(new Runnable() {
            public void run() {
                svMessage.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onServiceDiscoveredEvent(ServiceDiscoveredEvent e) {
        bleManager.notify(BluetoothUUID.SERVICE_UUID_STR, BluetoothUUID.CH_9601_UUID_STR, new BleCharacterCallback() {

            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                byte[] values = characteristic.getValue();
                showMsg(CodeUtil.hex2str(values) + "", darkColor);
                analysisData(values);
                enableBtns(true);
            }

            @Override
            public void onFailure(BleException exception) {
                showMsg("提醒失败", redColor);
            }

            @Override
            public void onInitiatedResult(boolean result) {
                if (result) {
                    showMsg("Notify Initialization Succeed", greenColor);
                } else {
                    showMsg("Notify Initialization Failed", redColor);
                }
            }

        });
    }

    private void sendOrder(int orderCode, byte[] orderCodeData, final String orderName) {
        curOrderCode = orderCode;
        orderCodeData = CodeUtil.splitReqBytes(orderCodeData);
        int dataLen = orderCodeData.length;
        if (dataLen <= 20) {
            writeData(orderCodeData, orderName);
        } else {
            final int packNum = dataLen % 20 == 0 ? (dataLen / 20) : (dataLen / 20 + 1);
            int lastPackLen = dataLen % 20;
            final byte[][] packs = new byte[packNum][20];
            for (int i = 0; i < packNum; i++) {
                if (i == packNum - 1) {
                    packs[i] = new byte[lastPackLen];
                    System.arraycopy(orderCodeData, i * 20, packs[i], 0, lastPackLen);
                } else {
                    System.arraycopy(orderCodeData, i * 20, packs[i], 0, 20);
                }
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < packNum; i++) {
                        try {
                            Thread.sleep(150);
                            EventBus.getDefault().post(new SendPackEvent(i, packs[i]));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSendPack(SendPackEvent pack) {
        writeData(pack.getData(), "打印" + pack.getPackNo());
    }

    private void writeData(final byte[] orderCodeData, final String orderName) {
        bleManager.writeDevice(BluetoothUUID.SERVICE_UUID_STR, BluetoothUUID.CH_9600_UUID_STR, orderCodeData, new BleCharacterCallback() {
            @Override
            public void onSuccess(BluetoothGattCharacteristic characteristic) {
                enableBtns(false);
                if (!orderName.startsWith("打印") && !orderName.startsWith("Scroll Paper")) {
                    showMsg("Start " + orderName, darkColor);
                }
            }

            @Override
            public void onFailure(BleException exception) {
                showMsg(orderName + "WriteData Failed", redColor);
            }

            @Override
            public void onInitiatedResult(boolean result) {
                if (!result) {
                    showMsg(orderName + "WriteData Initialization Failed", redColor);
                }
            }
        });
    }

    @OnClick(R.id.btn_reg_finger)
    void onRegFinger() {
        final RegisterDialog dialog = new RegisterDialog();
        dialog.setConfirmListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = dialog.getUser();
                BTFP_App.getInstance().getDaoSession().getUserDao().insert(user);
                dialog.dismiss();
            }
        });

        dialog.setCancelListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftInput(v);
                dialog.dismiss();
            }
        });

        dialog.setRegFingerListener(new RegisterDialog.RegFingerListener() {
            @Override
            public void onRegFinger(int fingerId) {
                getFinger();
            }
        });

        dialog.setCancelable(false);
        dialog.show(getFragmentManager(), "Reg");
    }

    @OnClick(R.id.btn_search_finger)
    void onSearchFinger() {
    }

    private void getFinger() {
        byte[] reqBytes = new byte[13];
        reqBytes[0] = PROTOCOL_HEAD;
        reqBytes[1] = (byte) 0x00;
        reqBytes[2] = (byte) 0x08;
        reqBytes[3] = (byte) 0x98;
        reqBytes[4] = (byte) 0x00;
        reqBytes[5] = (byte) 0x00;
        reqBytes[6] = (byte) 0x00;
        reqBytes[7] = (byte) 0x00;
        reqBytes[8] = (byte) 0x00;
        reqBytes[9] = (byte) 0x27;
        reqBytes[10] = (byte) 0x10;
        reqBytes[11] = CodeUtil.getXorCheckCode(reqBytes);
        reqBytes[12] = PROTOCOL_END;
        sendOrder(OrderCode.CMD_GET_FINGER, reqBytes, "采集指纹");
    }

    private void print(String content, int dataLen) {
        byte[] reqBytes = new byte[9 + dataLen];
        reqBytes[0] = PROTOCOL_HEAD;
        reqBytes[1] = 0x00;
        reqBytes[2] = (byte) (4 + dataLen);
        reqBytes[3] = (byte) 0xA0;
        reqBytes[4] = 0x00;
        reqBytes[5] = 0x00;
        reqBytes[6] = 0x00;
        for (int i = 0; i < content.length(); i++) {
            reqBytes[7 + i] = (byte) content.charAt(i);
        }
        for (int i = 7 + content.length(); i < 7 + dataLen; i++) {
            reqBytes[i] = 0x20;
        }
        reqBytes[7 + dataLen] = CodeUtil.getXorCheckCode(reqBytes);
        reqBytes[8 + dataLen] = PROTOCOL_END;
        sendOrder(OrderCode.CMD_PRINT, reqBytes, "打印");
    }

    private void analysisData(byte[] btData) {
        if (btData == null || btData.length == 0) {
            return;
        }
        boolean hasHead = false;
        for (int i = 0; i < btData.length; i++) {
            if (btData[i] == PROTOCOL_HEAD) {
                byte[] tempCacheData = new byte[btData.length - i];
                System.arraycopy(btData, i, tempCacheData, 0, tempCacheData.length);
                btData = tempCacheData;
                hasHead = true;
                break;
            }
        }
        boolean hasEnd = false;
        for (int i = 0; i < btData.length; i++) {
            if (btData[i] == PROTOCOL_END) {
                byte[] tempCacheData = new byte[i + 1];
                System.arraycopy(btData, 0, tempCacheData, 0, tempCacheData.length);
                btData = tempCacheData;
                hasEnd = true;
                break;
            }
        }

        if (hasHead) {
            cacheData = btData;
        } else {
            addToCache(btData);
        }

        if (hasEnd) {
            StringBuilder reMsgSb = new StringBuilder();
            showMsg("cacheDataLen = " + cacheData.length, darkColor);
            showMsg("len = " + CodeUtil.hex2str(new byte[]{cacheData[1], cacheData[2], cacheData[3], cacheData[4]}), darkColor);

            byte[] mergeCacheData = CodeUtil.mergeRetBytes(cacheData);
            if (CodeUtil.getXorCheckCode(mergeCacheData) != mergeCacheData[mergeCacheData.length - 2]) {
                showMsg("Check Code Error", redColor);
//                cacheData = null;
//                return;
            }
            if (handleReturnSw1(mergeCacheData[3], reMsgSb)) {
                switch (curOrderCode) {
                    case OrderCode.CMD_SEARCH_FINGER:
                        reMsgSb.append(" FingerId = ").append(mergeCacheData[5]);
                        showMsg(reMsgSb.toString(), greenColor);
                        printTest(mergeCacheData[5] + "");
                        break;
                    case OrderCode.CMD_PRINT:
                        printCache();
                        break;
                    case OrderCode.CMD_SCROLL_PAPER:

                        break;
                    case OrderCode.CMD_GET_FINGER:
                        showMsg("len = " + mergeCacheData.length, darkColor);
                        showMsg(CodeUtil.hex2str(mergeCacheData), darkColor);
                        break;
                    default:
                        showMsg(reMsgSb.toString(), greenColor);
                        break;
                }
            } else {
                showMsg(reMsgSb.toString(), redColor);
            }
        }

    }

    private void addToCache(byte[] values) {
        byte[] temp = new byte[cacheData.length + values.length];
        System.arraycopy(cacheData, 0, temp, 0, cacheData.length);
        System.arraycopy(values, 0, temp, cacheData.length, values.length);
        cacheData = temp;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBtnEnableEvent(BtnEnableEvent e) {
        btnRegFinger.setEnabled(e.isEnable());
        btnSearchFinger.setEnabled(e.isEnable());
    }

    private void enableBtns(boolean enable) {
        EventBus.getDefault().post(new BtnEnableEvent(enable));
    }

    private void printCache() {
        if (cachePrintContent.length == 0) {
            EventBus.getDefault().post(new ScrollPaperEvent(3));
            return;
        }
        print(cachePrintContent[0], PRINT_SIZE);
        String[] temp = new String[cachePrintContent.length - 1];
        if (cachePrintContent.length > 1) {
            System.arraycopy(cachePrintContent, 1, temp, 0, temp.length);
        }
        cachePrintContent = temp;
    }

    private void addPrintCache(String content) {
        String[] temp = new String[cachePrintContent.length + 1];
        System.arraycopy(cachePrintContent, 0, temp, 0, cachePrintContent.length);
        temp[cachePrintContent.length] = content;
        cachePrintContent = temp;
    }

    private void printTest(String username) {

        addPrintCache("    Welcome To Miaxis   ");
        addPrintCache("........................");
        addPrintCache("Tel:47515951Fax:12342234");
        addPrintCache("Shopping                ");
        addPrintCache("Date:" + DateUtil.getCurDateTime2());
        addPrintCache("Customer: " + username);
        addPrintCache("Item    Qty Price Amount");
        addPrintCache("........................");
        addPrintCache("381853  2   4     8     ");
        addPrintCache("334153  3   7     21    ");
        addPrintCache("........................");
        addPrintCache("Pay Amount        29    ");

        printCache();
    }

    private void scrollPaper(int num) {
        byte[] reqBytes = new byte[10];
        reqBytes[0] = PROTOCOL_HEAD;
        reqBytes[1] = 0x00;
        reqBytes[2] = 0x05;
        reqBytes[3] = (byte) 0xa1;
        reqBytes[4] = 0x00;
        reqBytes[5] = 0x00;
        reqBytes[6] = 0x00;
        reqBytes[7] = (byte) num;
        reqBytes[8] = CodeUtil.getXorCheckCode(reqBytes);
        reqBytes[9] = PROTOCOL_END;
        sendOrder(OrderCode.CMD_SCROLL_PAPER, reqBytes, "Scroll Paper " + num + " 行");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onScrollPaper(ScrollPaperEvent e) {
        scrollPaper(e.getNum());
    }

    @OnClick(R.id.btn_user_manage)
    void onUserManage() {
        getFragmentManager().beginTransaction().replace(R.id.fl_main, userListFragment).commit();
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }



}
