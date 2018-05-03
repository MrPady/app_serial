//package com.example.admin.infraredcertification;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.SpinnerAdapter;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.keymantek.serialport.utils.HexUtils;
//import com.keymantek.serialport.utils.SerialPortOpera;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.InvalidParameterException;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class OtherDataActivity extends AppCompatActivity implements View.OnClickListener{
//    private String banrate;
//    private ExcelUtil exccelUtil;
//    private String bit;
//    private EditText userNumber,userName,tableNumber;
//    private TextView mainI,AI,BI,CI,mainU,AU,BU,CU,mainP,AP,BP,CP,ALU,BLU,CLU,ALI,BLI,CLI,BCFP,BCBP,B1,B2,B3,B4,ACFP,ACBP,A1,A2,A3,A4,BTime,ATime;
//    private String event;
//    private String stops;
//    private String bh = "AAAAAAAAAAAA";
//    private TextView text_result1;
//    private Button ad;
//    private boolean isShow = false;
//        private String b1 = "";
//    private String show;
//    private CheckBox checkBox;
//    private Button output;
//    private ReadThread1 thread;
//    private int temp = 3;
//    private InputStream mInputStream;
//    private SerialPortOpera mSerialPortOpera;
//    private String strFPart = "FE FE FE FE";
//    private String strMPart = "AA AA AA AA AA AA";
//    private String strLPart0 = "68 11 04 35 37 33 37";// 读表号
//    private String strLPart1 = "68 11 04 33 33 34 36";// 失压记录
//    private String strLPart2 = "68 11 04 33 33 3E 36";// 失流记录
//    private String strLPart3 = "68 11 04 33 32 35 35";// 读电流
//    private String strLPart4 = "68 11 04 33 32 34 35";// 读电压
//    private String strLPart5 = "68 11 04 33 32 39 35";// 功率因数
//    private String strLPart6 = "68 11 04 33 40 63 36";// 开盖总次数
//    private String strLPart7 = "68 11 04 34 40 63 36";// 上次开盖
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_other_data);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
////        getActionBar().hide();
////        getSupportActionBar().hide();
////        setSupportActionBar(toolbar);
//        show = "false";
//        toolbar.inflateMenu(R.menu.main);
//        getSupportActionBar().hide();
//        toolbar.setTitle("激光抄表");
////        setSupportActionBar(toolbar);
//        toolbar.setOnMenuItemClickListener(new android.support.v7.widget.Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                if (id == R.id.action_settings) {
//                    //设置串口参数
//                    //加载设置界面
//                    View view = View.inflate(getApplication(), R.layout.activity_setting, null);
//                    //获取四个可选的下拉框Spinner
//                    Spinner spinner_banrate = (Spinner) view.findViewById(R.id.spinner_banrate);
//                    Spinner spinner_bit = (Spinner) view.findViewById(R.id.spinner_bit);
//                    Spinner spinner_event = (Spinner) view.findViewById(R.id.spinner_event);
//                    Spinner spinner_stop = (Spinner) view.findViewById(R.id.spinner_stop);
//                    Spinner spinner_show = (Spinner) view.findViewById(R.id.spinner_show);
//                    //设置串口参数默认值
//                    setSpinnerItemSelectedByValue(spinner_banrate, banrate);
//                    setSpinnerItemSelectedByValue(spinner_bit, bit);
//                    setSpinnerItemSelectedByValue(spinner_event, event);
//                    setSpinnerItemSelectedByValue(spinner_stop, stops);
//                    setSpinnerItemSelectedByValue(spinner_show,show);
//                    //设置四个spinner的选择监听，获取设置后的串口参数，保存到全局变量中
//                    spinner_stop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                                   int arg2, long arg3) {
//                            stops = getResources().getStringArray(R.array.nStop)[arg2];
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> arg0) {
//                        }
//                    });
//                    spinner_show.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                                   int arg2, long arg3) {
//                            show = getResources().getStringArray(R.array.tf)[arg2];
////                            Toast.makeText(OtherDataActivity.this, show, Toast.LENGTH_SHORT).show();
//                            if(show.equals("true")){
//                                isShow = true;
//                                text_result1.setVisibility(View.VISIBLE);
//                            }else{
//                                isShow = false;
//                                text_result1.setVisibility(View.GONE);
//                            }
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> parent) {
//                        }
//                    });
//                    spinner_banrate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                                   int arg2, long arg3) {
//                            banrate = getResources().getStringArray(R.array.banrate)[arg2];
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> arg0) {
//                        }
//                    });
//                    spinner_bit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                                   int arg2, long arg3) {
//                            bit = getResources().getStringArray(R.array.nBits)[arg2];
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> arg0) {
//                        }
//                    });
//                    spinner_event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                        @Override
//                        public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                                   int arg2, long arg3) {
//                            event = getResources().getStringArray(R.array.nEvent)[arg2];
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> arg0) {
//                        }
//                    });
//                    //设置串口参数对话框
//                    AlertDialog.Builder build = new AlertDialog.Builder(OtherDataActivity.this);
//                    build.setView(view).setTitle("设置");
//                    build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Log.d("params:", "banrate:" + banrate + "\tevent:" + event + "\tbit:" + bit + "\tstop:" + stops);
//                            mSerialPortOpera.closeSerialPort();
//                            mSerialPortOpera = new SerialPortOpera();
//                            try {
//
//                                mSerialPortOpera.openSerialPort("/dev/ttyMT3", Integer.parseInt(banrate), Integer.parseInt(bit), event.toCharArray()[0], Integer.parseInt(stops));
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    });
//                    build.create().show();
//                    return true;
//                }
//                return false;
//            }
//        });
//        // 设置默认串口参数
//        banrate = "1200";
//        bit = "8";
//        event = "E";
//        stops = "1";
//        mSerialPortOpera = new SerialPortOpera();
//
//        checkBox = (CheckBox) findViewById(R.id.checkbox3);
//        // 获取界面组件
//        init();
//        text_result1 = (TextView) findViewById(R.id.content);
//        ad = (Button) findViewById(R.id.queryN);
//        ad.setOnClickListener(this);
//        Button bt2 = (Button) findViewById(R.id.queryI), bt3 = (Button) findViewById(R.id.queryU), bt4 = (Button) findViewById(R.id.queryP), bt5 = (Button) findViewById(R.id.queryLU), bt6 = (Button) findViewById(R.id.queryLI);
//        Button bt7 = (Button)findViewById(R.id.queryRecord);
////        Button bt8 = (Button)findViewById(R.id.bt8);
////        Button bt9 = (Button)findViewById(R.id.bt9);
//        bt2.setOnClickListener(this);
//        bt3.setOnClickListener(this);
//        bt4.setOnClickListener(this);
//        bt5.setOnClickListener(this);
//        bt6.setOnClickListener(this);
//        bt7.setOnClickListener(this);
////        bt8.setOnClickListener(this);
////        bt9.setOnClickListener(this);
////        text_result1.setText("");
//        try {
//            // 打开串口并设置串口参数
//            mSerialPortOpera.openSerialPort("/dev/ttyMT3", Integer.parseInt(banrate), Integer.parseInt(bit), event.toCharArray()[0], Integer.parseInt(stops));
//            //获取输入流
//            mInputStream = mSerialPortOpera.SerialPortReceiver();
//            //创建读取的线程
//            thread = new ReadThread1(mInputStream);
//            thread.execute();
//        } catch (SecurityException e) {
//            displayToast("安全问题");
//        } catch (IOException e) {
//            displayToast("获取流问题");
//        } catch (InvalidParameterException e) {
//            displayToast("变量参数问题");
//        }
//    }
//    private void init(){
////      private EditText userNumber,userName,tableNumber;
////      private TextView mainI,AI,BI,CI,mainU,AU,BU,CU,mainP,AP,BP,CP,ALU,BLU,CLU,ALI,BLI,CLI,BCFP,BCBP,B1,B2,B3,B4,ACFP,ACBP,A1,A2,A3,A4,BTime,ATime;
//        userNumber = (EditText) findViewById(R.id.userNumber);
//
//        userName = (EditText) findViewById(R.id.userName);
//        tableNumber = (EditText) findViewById(R.id.table_number);
//        output = (Button) findViewById(R.id.output);
//        mainI = (TextView) findViewById(R.id.mainI);
//        mainU = (TextView) findViewById(R.id.mainU);
//        AI = (TextView) findViewById(R.id.AI);
//        BI = (TextView) findViewById(R.id.BI);
//        CI = (TextView) findViewById(R.id.CI);
//        AU = (TextView) findViewById(R.id.AU);
//        BU = (TextView) findViewById(R.id.BU);
//        CU = (TextView) findViewById(R.id.CU);
//        mainP = (TextView) findViewById(R.id.mainP);
//        AP = (TextView) findViewById(R.id.AP);
//        BP = (TextView) findViewById(R.id.BP);
//        CP = (TextView) findViewById(R.id.CP);
//        ALU = (TextView) findViewById(R.id.ALU);
//        BLU = (TextView) findViewById(R.id.BLU);
//        CLU = (TextView) findViewById(R.id.CLU);
//        // ALI,BLI,CLI,BCFP,BCBP,B1,B2,B3,B4,ACFP,ACBP,A1,A2,A3,A4,BTime,ATime;
//        ALI = (TextView) findViewById(R.id.ALI);
//        BLI = (TextView) findViewById(R.id.BLI);
//        CLI = (TextView) findViewById(R.id.CLI);
//        BCFP = (TextView) findViewById(R.id.BCFP);
//        BCBP = (TextView) findViewById(R.id.BCBP);
//        B1 = (TextView) findViewById(R.id.B1);
//        B2 = (TextView) findViewById(R.id.B2);
//        B3 = (TextView) findViewById(R.id.B3);
//        B4 = (TextView) findViewById(R.id.B4);
//        A1 = (TextView) findViewById(R.id.A1);
//        A2 = (TextView) findViewById(R.id.A2);
//        A3 = (TextView) findViewById(R.id.A3);
//        A4 = (TextView) findViewById(R.id.A4);
//        ACFP = (TextView) findViewById(R.id.ACFP);
//        ACBP = (TextView) findViewById(R.id.ACBP);
//        BTime = (TextView) findViewById(R.id.BTime);
//        ATime = (TextView) findViewById(R.id.ATime);
//        output.setOnClickListener(this);
////        A4.setText("124354");
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        int id = v.getId();
//        String stTemp;
//        String strMessage;
//        byte[] buffers;
//        switch (id){
////            case R.id.bt1:
////                stTemp = strFPart + " 68 " + strMPart + " " + strLPart0;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart0) + " 16";
////                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                mSerialPortOpera.SerialPortWrite(buffers);
////                break;
////            case R.id.bt2:
////                Toast.makeText(OtherDatas.this, "bh:" + bh, Toast.LENGTH_SHORT).show();
////                String stTemp1 = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart3;
////                Log.d("temp1", stTemp1);
////                strMessage = stTemp1
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
////                        + strLPart3) + " 16";
////                Log.d("debug", strMessage);
////                temp = 3;
////                byte[] buffers1 = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                mSerialPortOpera.SerialPortWrite(buffers1);
////                break;
////            case R.id.bt3:
////                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart4;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
////                        + strLPart4) + " 16";
////                temp = 2;
////                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                if(checkBox.isChecked())
////                    text_result1.append("tx:"+strMessage + "\n");
////                mSerialPortOpera.SerialPortWrite(buffers);
////                break;
////            case R.id.bt4:
////                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart5;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
////                        + strLPart5) + " 16";
////                temp = 2;
////                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                if(checkBox.isChecked())
////                    text_result1.append("tx:"+strMessage + "\n");
////                mSerialPortOpera.SerialPortWrite(buffers);
////                break;
////            case R.id.bt5:
////                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart1;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
////                        + strLPart1) + " 16";
////                temp = 3;
////                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                if(checkBox.isChecked())
////                    text_result1.append("tx:"+strMessage + "\n");
////                mSerialPortOpera.SerialPortWrite(buffers);
////                break;
////            case R.id.bt6:
////                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart2;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
////                        + strLPart2) + " 16";
////                temp = 3;
////                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////                if(checkBox.isChecked())
////                    text_result1.append("tx:"+strMessage + "\n");
////                mSerialPortOpera.SerialPortWrite(buffers);
////                break;
//            case R.id.queryN:
//                stTemp = strFPart + " 68 " + strMPart + " " + strLPart0;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
//                        + strLPart0) + " 16";
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.queryI:
//                String stTemp1 = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart3;
//                strMessage = stTemp1
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart3) + " 16";
//                Log.d("debug", strMessage);
//                //TODO:报文的内容
//                temp = 3;
//                if(isShow)
//                    text_result1.append("tx:"+strMessage + "\n");
//                byte[] buffers1 = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                mSerialPortOpera.SerialPortWrite(buffers1);
//                break;
//            case R.id.queryU:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart4;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart4) + " 16";
//                temp = 2;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(isShow)
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.queryP:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart5;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart5) + " 16";
//                temp = 2;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(isShow)
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.queryLU:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart1;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart1) + " 16";
//                temp = 3;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(isShow)
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.queryLI:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart2;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart2) + " 16";
//                temp = 3;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(isShow)
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.queryRecord:
//                stTemp = strFPart
//                        + "68 "
//                        + HexUtils.bytesToHexString(
//                        HexUtils.hexStringToByte(bh),
//                        HexUtils.hexStringToByte(bh).length)
//                        + strLPart7;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 "
//                        + HexUtils.bytesToHexString(
//                        HexUtils.hexStringToByte(bh),
//                        HexUtils.hexStringToByte(bh).length)
//                        + strLPart7) + " 16";
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(isShow)
//                    text_result1.append("tx:" + strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.output:
//                List<MyDatas> list = new ArrayList<>();
//                list.add(getMyDatas());
//                try {
//                    SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
//                    String s = df.format(new Date());
//                    ExcelUtil.writeExcel(getApplicationContext(),list,"data");
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//        }
//    }
//    private void displayToast(String s) {
//        Toast.makeText(getApplication(), s, Toast.LENGTH_SHORT).show();
//    }
//
//    //读取的线程
//    private class ReadThread1 extends ReadThread {
//        public ReadThread1(InputStream mInputStream) {
//            super(mInputStream);
//        }
//
//        @Override
//        protected void onProgressUpdate(String... values) {
//            //handler处理msg;
//            if(isShow)
//                text_result1.append(values[0]);
//            b1 += values[0];
//            Log.d("Pdebug4",b1);
//            final Message msg = new Message();
//            TimerTask timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    msg.obj = HexUtils.hexStringToByte(b1.replace(" ", ""));
//                    msg.what = 0;
//                    handler.sendMessage(msg);
//                }
//            };
//            new Timer().schedule(timerTask, 1000L);
//        }
//    }
//    //handler处理数据
//    final Handler handler = new Handler() {
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            byte[] bsData = null;
//            if (msg.what == 0) {
//                try{
//                    bsData = (byte[]) msg.obj;
//                    int[] iArray = new int[bsData.length];
//                    for (int i = 0; i < bsData.length; i++) {
//                        if (bsData[i] < 0) {
//                            iArray[i] = bsData[i] + 256;
//                        } else {
//                            iArray[i] = bsData[i];
//                        }
//                    }
//                    String accept_show = bytesToHexString(bsData);
//                    Log.d("Pdebug3",accept_show);
//                    int bt_length;
//                    try {
//                        bt_length = Integer.parseInt(accept_show.substring(39, 41), 16);
//                    } catch (Exception e) {
//                        bt_length = 0;
//                        Log.d("debug", "返回信息格式错误1");
//                        return;
//                    }
//                    int resk = -1;
//                    try {
//                        resk = check(iArray);
//                    } catch (Exception e) {
//                        Log.d("debug", "返回信息格式错误");
//                        return;
//                    }
//                    String recstr = "";
//                    String str0 = "表号：";
//                    String str1 = "----------------电表电流数据----------------\n";
//                    String str2 = "----------------电表电压数据----------------\n";
//                    String str3 = "---------------- 功率因数 --------------------\n";
//                    String str4 = "----------------失压次数统计----------------\n";
//                    String str5 = "----------------失流次数统计----------------\n";
//                    String str6 = "----------------开盖总次数------------------\n";
//                    String str7 = "---------------上一次开盖记录-----------------\n";
//                    Log.d("debug",""+resk+","+iArray[resk+8]);
//                    if (resk != -1) {
//                        if(iArray[resk+8] ==0xD1){
//                            text_result1.append("电表异常回答,请重试"+"\n");
//                        }
//                        //35 37 33 37
//                        if (iArray[resk + 10] == 0x35 && iArray[resk + 11] == 0x37 && iArray[resk + 12] == 0x33 && iArray[resk + 13] == 0x37) {
//                                String biaohao1 = "", biaohao2 = "", biaohao3 = "", biaohao4 = "", biaohao5 = "";
////                        String biaohao0 = Integer.toHexString(iArray[resk + 19] - 0x33);
////                        biaohao1 = Integer.toHexString(iArray[resk + 18] - 0x33);
////                        biaohao2 = Integer.toHexString(iArray[resk + 17] - 0x33);
////                        biaohao3 = Integer.toHexString(iArray[resk + 16] - 0x33);
////                        biaohao4 = Integer.toHexString(iArray[resk + 15] - 0x33);
////                        biaohao5 = Integer.toHexString(iArray[resk + 14] - 0x33);
//                                String biaohao0 = Integer.toHexString(iArray[resk + 1]);
//                                biaohao1 = Integer.toHexString(iArray[resk + 2]);
//                                biaohao2 = Integer.toHexString(iArray[resk + 3]);
//                                biaohao3 = Integer.toHexString(iArray[resk + 4]);
//                                biaohao4 = Integer.toHexString(iArray[resk + 5]);
//                                biaohao5 = Integer.toHexString(iArray[resk + 6]);
//                                if (Integer.parseInt(biaohao0) < 10) {
//                                    biaohao0 = "0" + biaohao0;
//                                }
//                                if (Integer.parseInt(biaohao1)< 10) {
//                                    biaohao1 = "0" + biaohao1;
//                                }
//                                if (Integer.parseInt(biaohao2) < 10) {
//                                    biaohao2 = "0" + biaohao2;
//                                }
//                                if (Integer.parseInt(biaohao3) < 10) {
//                                    biaohao3 = "0" + biaohao3;
//                                }
//                                if (Integer.parseInt(biaohao4) < 10) {
//                                    biaohao4 = "0" + biaohao4;
//                                }
//                                if (Integer.parseInt(biaohao5)< 10) {
//                                    biaohao5 = "0" + biaohao5;
//                                }
//                            String biaohao = biaohao0 + biaohao1 + biaohao2 + biaohao3 + biaohao4 + biaohao5;
////                        bh = biaohao0 + biaohao1 + biaohao2 + biaohao3 + biaohao4 + biaohao5;
//                            Log.d("b5 b4 b3 b2 b1 b0:",biaohao5+","+biaohao4+","+biaohao3+","+biaohao2+","+biaohao1+","+biaohao0);
////                        bh = biaohao5 + biaohao4 + biaohao3 + biaohao2 + biaohao1 + biaohao0;
//                            bh = biaohao ;
//                            text_result1.setText(text_result1.getText() + str0 + biaohao5 + biaohao4 + biaohao3 + biaohao2 + biaohao1 + biaohao0 + "\n" + "\n");
//                            tableNumber.setText(biaohao5+" "+biaohao4+" "+biaohao3+" "+biaohao2+" "+biaohao1+" "+biaohao0);
//                        }
//
//                        // 33 32 35 35  电流
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x32 && iArray[resk + 12] == 0x35 && iArray[resk + 13] == 0x35) {
//
//                            try {
//                                String Ia = "IA:" + DataFormat3(iArray[resk + 14], iArray[resk + 15], iArray[resk + 16]);
//                                String Ib = "IB:" + DataFormat3(iArray[resk + 17], iArray[resk + 18], iArray[resk + 19]);
//                                String Ic = "IC:" + DataFormat3(iArray[resk + 20], iArray[resk + 21], iArray[resk + 22]);
//                                text_result1.setText(text_result1.getText() + str1 + Ia + "\n" + Ib + "\n" + Ic + "\n");
//                                AI.setText(DataFormat3(iArray[resk + 14], iArray[resk + 15], iArray[resk + 16])+"A");
//                                BI.setText(DataFormat3(iArray[resk + 17], iArray[resk + 18], iArray[resk + 19])+"A");
//                                CI.setText(DataFormat3(iArray[resk + 20], iArray[resk + 21], iArray[resk + 22])+"A");
//                            } catch (Exception e) {
//                                displayToast("解析过程出现问题,请检查电表是否支持三相");
//                                String I = "电流:" + DataFormat3(iArray[resk + 14], iArray[resk + 15], iArray[resk + 16]);
//                                text_result1.setText(text_result1.getText() + str1 + I + "\n");
//                                mainI.setText(DataFormat3(iArray[resk + 14], iArray[resk + 15], iArray[resk + 16])+"A");
//                            }
////                        savaData();
//                        }
//                        //33 32 34 35 电压
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x32 && iArray[resk + 12] == 0x34 && iArray[resk + 13] == 0x35) {
//
////                            if ((bt_length - 4) / temp == 3) {
////                                String Ua = "UA:" + DataFormat4(iArray[resk + 14], iArray[resk + 15]);
////                                String Ub = "UB:" + DataFormat4(iArray[resk + 16], iArray[resk + 17]);
////                                String Uc = "UC:" + DataFormat4(iArray[resk + 18], iArray[resk + 19]);
////                                text_result1.setText(text_result1.getText() + str2 + Ua + "\n" + Ub + "\n" + Uc + "\n");
////                            } else {
////                                String U = "电压:" + DataFormat4(iArray[resk + 14], iArray[resk + 15]);
////                                text_result1.setText(text_result1.getText() + str2 + U + "\n");
////                            }
//                            try {
//                                String Ua = "UA:" + DataFormat4(iArray[resk + 14], iArray[resk + 15]);
//                                String Ub = "UB:" + DataFormat4(iArray[resk + 16], iArray[resk + 17]);
//                                String Uc = "UC:" + DataFormat4(iArray[resk + 18], iArray[resk + 19]);
//                                text_result1.setText(text_result1.getText() + str2 + Ua + "\n" + Ub + "\n" + Uc + "\n");
//                                AU.setText(DataFormat4(iArray[resk + 14], iArray[resk + 15])+"V");
//                                BU.setText(DataFormat4(iArray[resk + 16], iArray[resk + 17])+"V");
//                                CU.setText(DataFormat4(iArray[resk + 18], iArray[resk + 19])+"V");
//                            } catch (Exception e){
//                                displayToast("解析过程出现问题,请检查电表是否支持三相");
//                                String U = "电压:" + DataFormat4(iArray[resk + 14], iArray[resk + 15]);
//                                text_result1.setText(text_result1.getText() + str2 + U + "\n");
//                                mainU.setText(DataFormat4(iArray[resk + 14], iArray[resk + 15])+"V");
//                            }
////                        savaData();
//                        }
//                        //33 32 39 35总功率因数
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x32 && iArray[resk + 12] == 0x39 && iArray[resk + 13] == 0x35) {
////                            if ((bt_length - 4) / temp == 4) {
////                                String Wz = "总功率因数:" + DataFormat5(iArray[resk + 14], iArray[resk + 15]);
////                                String Wa = "A相功率因数:" + DataFormat5(iArray[resk + 16], iArray[resk + 17]);
////                                String Wb = "B相功率因数:" + DataFormat5(iArray[resk + 18], iArray[resk + 19]);
////                                String Wc = "C相功率因数:" + DataFormat5(iArray[resk + 20], iArray[resk + 21]);
////                                text_result1.setText(text_result1.getText() + str3 + Wz + "\n" + Wa + "\n" + Wb + "\n" + Wc + "\n");
////                            } else {
////                                String Wz = "总功率因数:" + DataFormat5(iArray[resk + 14], iArray[resk + 15]);
////                                String W = "功率因数:" + DataFormat5(iArray[resk + 16], iArray[resk + 17]);
////                                text_result1.setText(text_result1.getText() + str3 + Wz + "\n" + W + "\n");
////                            }
//                            try{
//                                String Wz = "总功率因数:" + DataFormat5(iArray[resk + 14], iArray[resk + 15]);
//                                String Wa = "A相功率因数:" + DataFormat5(iArray[resk + 16], iArray[resk + 17]);
//                                String Wb = "B相功率因数:" + DataFormat5(iArray[resk + 18], iArray[resk + 19]);
//                                String Wc = "C相功率因数:" + DataFormat5(iArray[resk + 20], iArray[resk + 21]);
//                                text_result1.setText(text_result1.getText() + str3 + Wz + "\n" + Wa + "\n" + Wb + "\n" + Wc + "\n");
//                                mainP.setText(DataFormat5(iArray[resk + 14], iArray[resk + 15]));
//                                AP.setText(DataFormat5(iArray[resk + 16], iArray[resk + 17]));
//                                BP.setText(DataFormat5(iArray[resk + 18], iArray[resk + 19]));
//                                CP.setText(DataFormat5(iArray[resk + 20], iArray[resk + 21]));
//                            } catch (Exception e){
//                                displayToast("解析过程出现问题,请检查电表是否支持三相");
//                                String Wz = "总功率因数:" + DataFormat5(iArray[resk + 14], iArray[resk + 15]);
//                                String W = "功率因数:" + DataFormat5(iArray[resk + 16], iArray[resk + 17]);
//                                text_result1.setText(text_result1.getText() + str3 + Wz + "\n" + W + "\n");
//                                mainP.setText(DataFormat5(iArray[resk + 14], iArray[resk + 15]));
//                            }
////                        savaData();
//                        }
//                        //33 33 34 36失压记录
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x33 && iArray[resk + 12] == 0x34 && iArray[resk + 13] == 0x36) {
//                            int redata = DataFormat1(iArray[14], iArray[15], iArray[16]);
//                            String lua = redata+"次 ";
//                            str4 += "\nA相失压总次数为" + redata + "次\n";
//                            redata = DataFormat1(iArray[17], iArray[18], iArray[19]);
//                            lua += redata+"分";
//                            str4 += "\nA相失压累计时间为" + redata + "分\n";
//                            try {
//                                String lub,luc;
//                                redata = DataFormat1(iArray[20], iArray[21], iArray[22]);
//                                lub = redata+"次 ";
//                                str4 += "\nB相失压总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[23], iArray[24], iArray[25]);
//                                lub += redata+"分";
//                                str4 += "\nB相失压累计时间为" + redata + "分\n";
//                                redata = DataFormat1(iArray[26], iArray[27], iArray[28]);
//                                luc = redata + "次 ";
//                                str4 += "\nC相失压总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[29], iArray[30], iArray[31]);
//                                luc += redata + "分";
//                                str4 += "\nC相失压累计时间为" + redata + "分\n";
//                                ALU.setText(lua);
//                                BLU.setText(lub);
//                                CLU.setText(luc);
//                            }catch(Exception e){}
//                            text_result1.setText(text_result1.getText() + str4);
//                            displayToast("解析过程出现问题,请检查电表是否支持三相");
//                            ALU.setText(lua);
//                        }
//                        //失流
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x33 && iArray[resk + 12] == 0x3E && iArray[resk + 13] == 0x36) {
//                            int redata = DataFormat1(iArray[14], iArray[15], iArray[16]);
//                            str5 += "\nA相失流总次数为" + redata + "次\n";
//                            String lia  = redata + "次 ";
//                            redata = DataFormat1(iArray[17], iArray[18], iArray[19]);
//                            lia += redata + "分";
//                            str5 += "\nA相失流累计时间为" + redata + "分\n";
//                            try {
//                                String lib,lic;
//                                redata = DataFormat1(iArray[20], iArray[21], iArray[22]);
//                                lib = redata + "次 ";
//                                str5 += "\nB相失流总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[23], iArray[24], iArray[25]);
//                                lib += redata + "分";
//                                str5 += "\nB相失流累计时间为" + redata + "分\n";
//                                redata = DataFormat1(iArray[26], iArray[27], iArray[28]);
//                                lic = redata + "次 ";
//                                str5 += "\nC相失流总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[29], iArray[30], iArray[31]);
//                                lic += redata + "分";
//                                str5 += "\nC相失流累计时间为" + redata + "分\n";
//                                ALI.setText(lia);
//                                BLI.setText(lib);
//                                CLI.setText(lic);
//                            }catch(Exception e){
//                                text_result1.setText(text_result1.getText() + str5);
//                                displayToast("解析过程出现问题,请检查电表是否支持三相");
//                                ALI.setText(lia);
//                            }
//
//                        }else if (iArray[resk + 10] == 0x33
//                                && iArray[resk + 11] == 0x40
//                                && iArray[resk + 12] == 0x63
//                                && iArray[resk + 13] == 0x36) {
//                            int redata = DataFormat6(iArray[resk + 14],
//                                    iArray[resk + 15], iArray[resk + 16]);
//                            str6 += "总开盖次数为 " + redata + "次\n";
//                            text_result1.append(str6);
//                        } else if (iArray[resk + 10] == 0x34
//                                && iArray[resk + 11] == 0x40
//                                && iArray[resk + 12] == 0x63
//                                && iArray[resk + 13] == 0x36) {
//                            text_result1.append("上次开盖发生时刻:"
//                                    + DataFormatDate(iArray[resk + 14],
//                                    iArray[resk + 15], iArray[resk + 16],
//                                    iArray[resk + 17], iArray[resk + 18],
//                                    iArray[resk + 19]) + "\n");
//                            BTime.setText(DataFormatDate(iArray[resk + 14],
//                                    iArray[resk + 15], iArray[resk + 16],
//                                    iArray[resk + 17], iArray[resk + 18],
//                                    iArray[resk + 19]));
//                            text_result1.append("开盖结束时间:"
//                                    + DataFormatDate(iArray[resk + 20],
//                                    iArray[resk + 21], iArray[resk + 22],
//                                    iArray[resk + 23], iArray[resk + 24],
//                                    iArray[resk + 25]) + "\n");
//                            ATime.setText(DataFormatDate(iArray[resk + 20],
//                                    iArray[resk + 21], iArray[resk + 22],
//                                    iArray[resk + 23], iArray[resk + 24],
//                                    iArray[resk + 25]));
//                            text_result1.append("开盖前正向有功总电能:"
//                                    + DataFormat(iArray[resk + 26],
//                                    iArray[resk + 27], iArray[resk + 28],
//                                    iArray[resk + 29]) + "kwh\n");
//                            BCFP.setText(DataFormat(iArray[resk + 26],
//                                    iArray[resk + 27], iArray[resk + 28],
//                                    iArray[resk + 29]) + "kwh");
//                            text_result1.append("开盖前反向有功总电能:"
//                                    + DataFormat(iArray[resk + 30],
//                                    iArray[resk + 31], iArray[resk + 32],
//                                    iArray[resk + 33]) + "kwh\n");
//                            BCBP.setText(DataFormat(iArray[resk + 30],
//                                    iArray[resk + 31], iArray[resk + 32],
//                                    iArray[resk + 33]) + "kwh");
//                            text_result1.append("开表盖前第一象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 34],
//                                    iArray[resk + 35], iArray[resk + 36],
//                                    iArray[resk + 37]) + "kvarh\n");
//                            B1.setText(DataFormat(iArray[resk + 34],
//                                    iArray[resk + 35], iArray[resk + 36],
//                                    iArray[resk + 37]) + "kvarh");
//                            text_result1.append("开表盖前第二象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 38],
//                                    iArray[resk + 39], iArray[resk + 40],
//                                    iArray[resk + 41]) + "kvarh\n");
//                            B2.setText(DataFormat(iArray[resk + 38],
//                                    iArray[resk + 39], iArray[resk + 40],
//                                    iArray[resk + 41]) + "kvarh");
//                            text_result1.append("开表盖前第三象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 42],
//                                    iArray[resk + 43], iArray[resk + 44],
//                                    iArray[resk + 45]) + "kvarh\n");
//                            B3.setText(DataFormat(iArray[resk + 42],
//                                    iArray[resk + 43], iArray[resk + 44],
//                                    iArray[resk + 45]) + "kvarh");
//                            text_result1.append("开表盖前第四象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 46],
//                                    iArray[resk + 47], iArray[resk + 48],
//                                    iArray[resk + 49]) + "kvarh\n");
//                            B4.setText(DataFormat(iArray[resk + 46],
//                                    iArray[resk + 47], iArray[resk + 48],
//                                    iArray[resk + 49]) + "kvarh");
//
//
//                            text_result1.append("开盖后正向有功总电能:"
//                                    + DataFormat(iArray[resk + 50],
//                                    iArray[resk + 51], iArray[resk + 52],
//                                    iArray[resk + 53]) + "kwh\n");
//                            ACFP.setText(DataFormat(iArray[resk + 50],
//                                    iArray[resk + 51], iArray[resk + 52],
//                                    iArray[resk + 53]) + "kwh");
//
//                            text_result1.append("开盖后反向有功总电能:"
//                                    + DataFormat(iArray[resk + 54],
//                                    iArray[resk + 55], iArray[resk + 56],
//                                    iArray[resk + 57]) + "kwh\n");
//                            ACBP.setText(DataFormat(iArray[resk + 54],
//                                    iArray[resk + 55], iArray[resk + 56],
//                                    iArray[resk + 57]) + "kwh");
//                            text_result1.append("开表盖后第一象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 58],
//                                    iArray[resk + 59], iArray[resk + 60],
//                                    iArray[resk + 61]) + "kvarh\n");
//                            A1.setText(DataFormat(iArray[resk + 58],
//                                    iArray[resk + 59], iArray[resk + 60],
//                                    iArray[resk + 61]) + "kvarh");
//                            text_result1.append("开表盖后第二象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 62],
//                                    iArray[resk + 63], iArray[resk + 64],
//                                    iArray[resk + 65]) + "kvarh\n");
//                            A2.setText(DataFormat(iArray[resk + 62],
//                                    iArray[resk + 63], iArray[resk + 64],
//                                    iArray[resk + 65]) + "kvarh");
//                            text_result1.append("开表盖后第三象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 66],
//                                    iArray[resk + 67], iArray[resk + 68],
//                                    iArray[resk + 69]) + "kvarh\n");
//                            A3.setText(DataFormat(iArray[resk + 66],
//                                    iArray[resk + 67], iArray[resk + 68],
//                                    iArray[resk + 69]) + "kvarh");
//                            text_result1.append("开表盖后第四象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 70],
//                                    iArray[resk + 71], iArray[resk + 72],
//                                    iArray[resk + 73]) + "kvarh\n");
//                            A4.setText(DataFormat(iArray[resk + 70],
//                                    iArray[resk + 71], iArray[resk + 72],
//                                    iArray[resk + 73]) + "kvarh");
//                        } else {
//                            recstr += "没有解析到有效信息\n";
//                            displayToast("没有解析到有效数据");
//                        }
//                    } else {
//                        Log.d("debug", "没有解析到有效信息");
//                        recstr += "没有解析到有效信息\n";
//                    }
//                    String ss = text_result1.getText() + recstr;
//                    text_result1.setText(ss);
//                }catch (Exception e){}
//
//            } else if (msg.what == 1) {
//                Toast.makeText(getApplicationContext(), "地址：" + msg.obj.toString(), Toast.LENGTH_SHORT).show();
//            } else if (msg.what == 2) {
//                Toast.makeText(getApplicationContext(), "共计" + msg.obj.toString() + "个电表抄读完成，可以查看测试记录", Toast.LENGTH_SHORT).show();
//            } else if (msg.what == 4) {
//
//            } else {
//
//            }
//            b1 = "";
//        }
//    };
//    public int DataFormat6(int iArray1, int iArray2, int iArray3) {
//        int itmp = 0;
//        int iData1 = iArray1 == 0x32 ? 0 : iArray1 - 0x33;
//        int iData2 = iArray2 == 0x32 ? 0 : iArray2 - 0x33;
//        int iData3 = iArray3 == 0x32 ? 0 : iArray3 - 0x33;
//        itmp = (iData1) * 10 / 16 + ((iData1) % 16) + (iData2) * 1000 / 16
//                + ((iData2 * 100) % 16) + iData3 * 100000 / 16
//                + ((iData3 * 10000) % 16);
//        return itmp;
//    }
//    public String DataFormat(int bytesData1, int bytesData2, int bytesData3,
//                             int bytesData4) {
//        double ftmp = 0.0F;
//        int iData1 = bytesData1 == 0x32 ? 0 : bytesData1 - 0x33;
//        int iData2 = bytesData2 == 0x32 ? 0 : bytesData2 - 0x33;
//        int iData3 = bytesData3 == 0x32 ? 0 : bytesData3 - 0x33;
//        int iData4 = bytesData4 == 0x32 ? 0 : bytesData4 - 0x33;
//        ftmp = ((iData1) / 16) * 0.1 + ((iData1) % 16) * 0.01 + ((iData2) / 16)
//                * 10 + ((iData2) % 16) * 1 + (iData3 / 16) * 1000
//                + (iData3 % 16) * 100 + (iData4 / 16) * 100000 + (iData4 % 16)
//                * 10000;
//        DecimalFormat dcmFmt = new DecimalFormat("0.00");
//        return dcmFmt.format(ftmp);
//    }
//
//    //格式处理
//    public int DataFormat1(int iArray1, int iArray2, int iArray3) {
//        int itmp = 0;
//        int iData1 = iArray1 == 0x32 ? 0 : iArray1 - 0x33;
//        int iData2 = iArray2 == 0x32 ? 0 : iArray2 - 0x33;
//        int iData3 = iArray3 == 0x32 ? 0 : iArray3 - 0x33;
//        itmp = ((iData1) / 16) * 10 + ((iData1) % 16)
//                + ((iData2) / 16) * 1000 + ((iData2) % 16) * 100
//                + ((iData3) / 16) * 100000 + ((iData3) / 16) * 10000;
//        return itmp;
//    }
//
//    public String DataFormat3(int bytesData1, int bytesData2, int bytesData3) {
//        double ftmp = 0.0F;
//        int iData1 = bytesData1 == 0x32 ? 0 : bytesData1 - 0x33;
//        int iData2 = bytesData2 == 0x32 ? 0 : bytesData2 - 0x33;
//        int iData3 = bytesData3 == 0x32 ? 0 : bytesData3 - 0x33;
//        int i = ((bytesData3 >> 7) & 1) == 1 ? -1 : 1;
//        ftmp = ((iData1) / 16) * 0.01 + ((iData1) % 16) * 0.001
//                + ((iData2) / 16) * 1 + ((iData2) % 16) * 0.1
//                + ((iData3 & 0x7F) / 16) * 100 + ((iData3 & 0x7F) / 16) * 10;
//        DecimalFormat dcmFmt = new DecimalFormat("0.00");
//        return dcmFmt.format(ftmp * i);
//    }
//
//    public String DataFormat4(int bytesData1, int bytesData2) {
//        double ftmp = 0.0F;
//        int iData1 = bytesData1 == 0x32 ? 0 : bytesData1 - 0x33;
//        int iData2 = bytesData2 == 0x32 ? 0 : bytesData2 - 0x33;
//        int i = ((bytesData2 >> 7) & 1) == 1 ? -1 : 1;
//        ftmp = ((iData1) / 16) + ((iData1) % 16) * 0.1
//                + ((iData2 & 0x7F) / 16) * 100 + ((iData2 & 0x7F) % 16) * 10;
//        DecimalFormat dcmFmt = new DecimalFormat("0.00");
//        return dcmFmt.format(ftmp * i);
//    }
//
//    public String DataFormat5(int bytesData1, int bytesData2) {
//        double ftmp = 0.00F;
//        int iData1 = bytesData1 == 0x32 ? 0 : bytesData1 - 0x33;
//        int iData2 = bytesData2 == 0x32 ? 0 : bytesData2 - 0x33;
//        int i = ((iData2 >> 7) & 1) == 1 ? -1 : 1;
//        ftmp = ((iData1) / 16) * 0.01 + ((iData1) % 16) * 0.001
//                + ((iData2 & 0x7F) / 16) + ((iData2 & 0x7F) % 16) * 0.1;
//        DecimalFormat dcmFmt = new DecimalFormat("0.00");
//        return dcmFmt.format(ftmp * i);
//    }
//
//    public String StrFormate(String str) {
//        String rs = str;
//        if (str.length() == 1) {
//            rs = "0" + rs;
//        }
//        return rs;
//    }
//    public String DataFormatDate(int ss, int mm, int hh, int DD, int MM, int YY) {
//        StringBuilder date = new StringBuilder("");
//        int d_ss = ss == 0x32 ? 0 : ss - 0x33;
//        int d_mm = mm == 0x32 ? 0 : mm - 0x33;
//        int d_hh = hh == 0x32 ? 0 : hh - 0x33;
//        int d_DD = DD == 0x32 ? 0 : DD - 0x33;
//        int d_MM = MM == 0x32 ? 0 : MM - 0x33;
//        int d_YY = YY == 0x32 ? 0 : YY - 0x33;
//        date.append(Integer.toHexString(d_YY) + " 年 "
//                + Integer.toHexString(d_MM) + " 月 " + Integer.toHexString(d_DD)
//                + " 号 " + Integer.toHexString(d_hh) + ":"
//                + Integer.toHexString(d_mm) + ":" + Integer.toHexString(d_ss));
//        return date.toString();
//    }
//    //检查68位置等
//    public int check(int[] checkdata) throws Exception {
//        int result = 0;  //记录校验和
//        int n = 0;   //记录 0X68  起始位置
//        if (checkdata.length < 13) {
//            return -1;
//        }
//        for (int i = 0; i < checkdata.length; i++) {
//            if ((checkdata[0 + i] == 0X68) && (checkdata[7 + i] == 0X68)) {
//                break;
//            } else {
//                n++;
//            }
//        }
//        if (n == checkdata.length) {
//            return -1;
//        }
//        if (checkdata[0 + n] != 0X68 || checkdata[7 + n] != 0X68) {
//            return -1;
//        }
//
//        // 数据长度
//        int len = checkdata[9 + n];
//        if (checkdata[11 + len + n] != 0X16) {
//            return -1;
//        }
//        for (int i = n; i < (len + 10 + n); i++) {
//            result += checkdata[i];
//        }
//        result = result & 0XFF;
//
//        if (checkdata[10 + len + n] != result) {
//            return -1;// 校验和判定
//        }
//        return n;
//    }
//    @Override
//    protected void onDestroy() {
//        if (thread != null)
//            thread.cancel(true);
//        mSerialPortOpera.closeSerialPort();
//        super.onDestroy();
//    }
//
//
//    public static String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder(" ");
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        for (int i = 0; i < src.length; i++) {
//            int v = src[i] >= 0 ? src[i] : src[i] + 256;
//            String hv = Integer.toHexString(v);
//            if (hv.length() < 2) {
//                stringBuilder.append(0);
//            }
//            stringBuilder.append(hv + " ");
//        }
//        return stringBuilder.toString().trim();
//    }
//    public static void setSpinnerItemSelectedByValue(Spinner spinner, Object value) {
//        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
//        int k = apsAdapter.getCount();
//        for (int i = 0; i < k; i++) {
//            if (value.equals(apsAdapter.getItem(i).toString())) {
//                spinner.setSelection(i, true);// 默认选中
//                break;
//            }
//        }
//    }
//    private MyDatas getMyDatas(){
//        MyDatas myDatas = new MyDatas();
////        userNumber = (EditText) findViewById(R.id.userNumber);
//        myDatas.setUserNumber(userNumber.getText().toString());
////        userName = (EditText) findViewById(R.id.userName);
//        myDatas.setUserName(userName.getText().toString());
////        tableNumber = (EditText) findViewById(R.id.table_number);
//        myDatas.setTableNumber(tableNumber.getText().toString());
////        mainI = (TextView) findViewById(R.id.mainI);
//        myDatas.setMainI(mainI.getText().toString());
////        mainU = (TextView) findViewById(R.id.mainU);
//        myDatas.setMainU(mainU.getText().toString());
////        AI = (TextView) findViewById(R.id.AI);
//        myDatas.setAI(AI.getText().toString());
////        BI = (TextView) findViewById(R.id.BI);
//        myDatas.setBI(BI.getText().toString());
////        CI = (TextView) findViewById(R.id.CI);
//        myDatas.setCI(CI.getText().toString());
////        AU = (TextView) findViewById(R.id.AU);
//        myDatas.setAU(AU.getText().toString());
////        BU = (TextView) findViewById(R.id.BU);
//        myDatas.setBU(BU.getText().toString());
////        CU = (TextView) findViewById(R.id.CU);
//        myDatas.setCU(CU.getText().toString());
////        mainP = (TextView) findViewById(R.id.mainP);
//        myDatas.setMainP(mainP.getText().toString());
////        AP = (TextView) findViewById(R.id.AP);
//        myDatas.setAP(AP.getText().toString());
////        BP = (TextView) findViewById(R.id.BP);
//        myDatas.setBP(BP.getText().toString());
////        CP = (TextView) findViewById(R.id.CP);
//        myDatas.setCP(CP.getText().toString());
////        ALU = (TextView) findViewById(R.id.ALU);
//        myDatas.setALU(ALU.getText().toString());
////        BLU = (TextView) findViewById(R.id.BLU);
//        myDatas.setBLU(BLU.getText().toString());
////        CLU = (TextView) findViewById(R.id.CLU);
//        myDatas.setCLU(CLU.getText().toString());
////        // ALI,BLI,CLI,BCFP,BCBP,B1,B2,B3,B4,ACFP,ACBP,A1,A2,A3,A4,BTime,ATime;
////        ALI = (TextView) findViewById(R.id.ALI);
//        myDatas.setALI(ALI.getText().toString());
////        BLI = (TextView) findViewById(R.id.BLI);
//        myDatas.setBLI(BLI.getText().toString());
////        CLI = (TextView) findViewById(R.id.CLI);
//        myDatas.setCLI(CLI.getText().toString());
////        BCFP = (TextView) findViewById(R.id.BCFP);
//        myDatas.setBCFP(BCFP.getText().toString());
////        BCBP = (TextView) findViewById(R.id.BCBP);
//        myDatas.setBCBP(BCBP.getText().toString());
////        B1 = (TextView) findViewById(R.id.B1);
//        myDatas.setB1(B1.getText().toString());
////        B2 = (TextView) findViewById(R.id.B2);
//        myDatas.setB2(B2.getText().toString());
////        B3 = (TextView) findViewById(R.id.B3);
//        myDatas.setB3(B3.getText().toString());
////        B4 = (TextView) findViewById(R.id.B4);
//        myDatas.setB4(B4.getText().toString());
////        A1 = (TextView) findViewById(R.id.A1);
//        myDatas.setA1(A1.getText().toString());
////        A2 = (TextView) findViewById(R.id.A2);
//        myDatas.setA2(A2.getText().toString());
////        A3 = (TextView) findViewById(R.id.A3);
//        myDatas.setA3(A3.getText().toString());
////        A4 = (TextView) findViewById(R.id.A4);
//        myDatas.setA4(A4.getText().toString());
////        ACFP = (TextView) findViewById(R.id.ACFP);
//        myDatas.setACFP(ACFP.getText().toString());
////        ACBP = (TextView) findViewById(R.id.ACBP);
//        myDatas.setACBP(ACBP.getText().toString());
////        BTime = (TextView) findViewById(R.id.BTime);
//        myDatas.setBTime(BTime.getText().toString());
////        ATime = (TextView) findViewById(R.id.ATime);
//        myDatas.setATime(ATime.getText().toString());
//
//        return myDatas;
//    }/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//}
