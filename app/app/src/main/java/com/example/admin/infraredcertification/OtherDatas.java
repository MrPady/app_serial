//package com.example.admin.infraredcertification;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.SpinnerAdapter;
//import android.widget.Toast;
//
//import com.keymantek.serialport.utils.HexUtils;
//import com.keymantek.serialport.utils.SerialPortOpera;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.security.InvalidParameterException;
//import java.text.DecimalFormat;
//import java.util.Timer;
//import java.util.TimerTask;
//
//public class OtherDatas extends AppCompatActivity implements View.OnClickListener {
//
//    private String banrate;
//    private String bit;
//    private String event;
//    private String stops;
//    private String bh = "AAAAAAAAAAAA";
//    private EditText text_result1;
//    private Button ad;
//    private String b1 = "";
//    private CheckBox checkBox;
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
//        setContentView(R.layout.other_datas);
//        // 设置默认串口参数
//        banrate = "1200";
//        bit = "8";
//        event = "E";
//        stops = "1";
//        mSerialPortOpera = new SerialPortOpera();
//
//        checkBox = (CheckBox) findViewById(R.id.checkbox3);
//        // 获取界面组件
//        text_result1 = (EditText) findViewById(R.id.text_result_other);
//        ad = (Button) findViewById(R.id.bt1);
//        ad.setOnClickListener(this);
//        Button bt2 = (Button) findViewById(R.id.bt2), bt3 = (Button) findViewById(R.id.bt3), bt4 = (Button) findViewById(R.id.bt4), bt5 = (Button) findViewById(R.id.bt5), bt6 = (Button) findViewById(R.id.bt6);
//        Button bt7 = (Button)findViewById(R.id.bt7);
//        Button bt8 = (Button)findViewById(R.id.bt8);
//        Button bt9 = (Button)findViewById(R.id.bt9);
//        bt2.setOnClickListener(this);
//        bt3.setOnClickListener(this);
//        bt4.setOnClickListener(this);
//        bt5.setOnClickListener(this);
//        bt6.setOnClickListener(this);
//        bt7.setOnClickListener(this);
//        bt8.setOnClickListener(this);
//        bt9.setOnClickListener(this);
//        text_result1.setText("");
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
//
//    //单击事件
//    @Override
//    public void onClick(View v) {
//        String stTemp;
//        String strMessage;
//        byte[] buffers;
//        switch (v.getId()) {
//            case R.id.bt1:
//                stTemp = strFPart + " 68 " + strMPart + " " + strLPart0;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
//                        + strLPart0) + " 16";
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt2:
//                Toast.makeText(OtherDatas.this, "bh:" + bh, Toast.LENGTH_SHORT).show();
//                String stTemp1 = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart3;
//                Log.d("temp1", stTemp1);
//                strMessage = stTemp1
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart3) + " 16";
//                Log.d("debug", strMessage);
//                temp = 3;
//                String s = "tx:" + strMessage;
//                if(checkBox.isChecked())
//                    text_result1.append(s + "\n");
//                byte[] buffers1 = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                mSerialPortOpera.SerialPortWrite(buffers1);
//                break;
//            case R.id.bt3:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart4;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart4) + " 16";
//                temp = 2;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(checkBox.isChecked())
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt4:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart5;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart5) + " 16";
//                temp = 2;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(checkBox.isChecked())
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt5:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart1;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart1) + " 16";
//                temp = 3;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(checkBox.isChecked())
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt6:
//                stTemp = strFPart + " 68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length) + strLPart2;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 " + HexUtils.bytesToHexString(HexUtils.hexStringToByte(bh), HexUtils.hexStringToByte(bh).length)
//                        + strLPart2) + " 16";
//                temp = 3;
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(checkBox.isChecked())
//                    text_result1.append("tx:"+strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt7:
//                //设置串口参数
//                //加载设置界面
//                View view = View.inflate(getApplication(), R.layout.activity_setting, null);
//                //获取四个可选的下拉框Spinner
//                Spinner spinner_banrate = (Spinner) view.findViewById(R.id.spinner_banrate);
//                Spinner spinner_bit = (Spinner) view.findViewById(R.id.spinner_bit);
//                Spinner spinner_event = (Spinner) view.findViewById(R.id.spinner_event);
//                Spinner spinner_stop = (Spinner) view.findViewById(R.id.spinner_stop);
//                //设置串口参数默认值
//                setSpinnerItemSelectedByValue(spinner_banrate, banrate);
//                setSpinnerItemSelectedByValue(spinner_bit, bit);
//                setSpinnerItemSelectedByValue(spinner_event, event);
//                setSpinnerItemSelectedByValue(spinner_stop, stops);
//                //设置四个spinner的选择监听，获取设置后的串口参数，保存到全局变量中
//                spinner_stop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                               int arg2, long arg3) {
//                        stops = OtherDatas.this.getResources().getStringArray(R.array.nStop)[arg2];
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                    }
//                });
//                spinner_banrate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                               int arg2, long arg3) {
//                        banrate = OtherDatas.this.getResources().getStringArray(R.array.banrate)[arg2];
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                    }
//                });
//                spinner_bit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                               int arg2, long arg3) {
//                        bit = OtherDatas.this.getResources().getStringArray(R.array.nBits)[arg2];
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                    }
//                });
//                spinner_event.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//
//                    @Override
//                    public void onItemSelected(AdapterView<?> arg0, View arg1,
//                                               int arg2, long arg3) {
//                        event = OtherDatas.this.getResources().getStringArray(R.array.nEvent)[arg2];
//                    }
//
//                    @Override
//                    public void onNothingSelected(AdapterView<?> arg0) {
//                    }
//                });
//                //设置串口参数对话框
//                AlertDialog.Builder build = new AlertDialog.Builder(OtherDatas.this);
//                build.setView(view).setTitle("设置");
//                build.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("params:", "banrate:" + banrate + "\tevent:" + event + "\tbit:" + bit + "\tstop:" + stops);
//                        mSerialPortOpera.closeSerialPort();
//                        mSerialPortOpera = new SerialPortOpera();
//                        try {
//                            mSerialPortOpera.openSerialPort("/dev/ttyMT3", Integer.parseInt(banrate), Integer.parseInt(bit), event.toCharArray()[0], Integer.parseInt(stops));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//                build.create().show();
//                break;
//            case R.id.bt8://开盖总次数
//                stTemp = strFPart
//                        + "68 "
//                        + HexUtils.bytesToHexString(
//                        HexUtils.hexStringToByte(bh),
//                        HexUtils.hexStringToByte(bh).length)
//                        + strLPart6;
//                strMessage = stTemp
//                        + " "
//                        + FrameFormat.StingtoGetCS("68 "
//                        + HexUtils.bytesToHexString(
//                        HexUtils.hexStringToByte(bh),
//                        HexUtils.hexStringToByte(bh).length)
//                        + strLPart6) + " 16";
//                buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
//                if(checkBox.isChecked())
//                    text_result1.append("tx:" + strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//            case R.id.bt9:
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
//                if(checkBox.isChecked())
//                    text_result1.append("tx:" + strMessage + "\n");
//                mSerialPortOpera.SerialPortWrite(buffers);
//                break;
//        }
//    }
//
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
//            if(checkBox.isChecked())
//                text_result1.append(values[0]);
//            b1 += values[0];
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
//
//    //SendAMessage
////    public void sendAMessage(String addr, int p) {
////        String strFPart = "FE FE FE FE";
////        String strMPart = "AA AA AA AA AA AA";
////        String strLPart0 = "68 11 04 35 37 33 37";// 读表号
////        String strLPart1 = "68 11 04 33 33 34 36";// 失压记录
////        String strLPart2 = "68 11 04 33 33 3E 36";// 失流记录
////        String strLPart3 = "68 11 04 33 32 35 35";// 读电流
////        String strLPart4 = "68 11 04 33 32 34 35";// 读电压
////        String strLPart5 = "68 11 04 33 32 39 35";// 功率因数
////        String strMessage = "";
////        if (addr != "" && addr.length() == 8) {
////            strMPart = FrameFormat.Stringspace(addr);
////        }
////        // 读表号
////        if (progress == -1) {
////            String stTemp = strFPart + " 68 " + strMPart + " " + strLPart0;
////            strMessage = stTemp
////                    + " "
////                    + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                    + strLPart0) + " 16";
//////            progress++;
//////            sendAMessage("",progress);
////        }
////
////        // 电流记录
////        else if (progress == 2) {
////            if (chs[progress] == true) {
////                String stTemp = strFPart + " 68 " + strMPart + " " + strLPart3;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart3) + " 16";
////                temp = 3;
////                read = 2;
////            } else {
////                return;
////            }
////            progress++;
////        }
////        // 电压记录
////        else if (progress == 3) {
////            if (chs[progress] == true) {
////                String stTemp = strFPart + " 68 " + strMPart + " " + strLPart4;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart4) + " 16";
////                temp = 2;
////                read = 3;
////            } else {
////                return;
////            }
////            progress++;
////        }
////        // 功率因数
////        else if (progress == 4) {
////            if (chs[progress] == true) {
////                String stTemp = strFPart + " 68 " + strMPart + " " + strLPart5;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart5) + " 16";
////                temp = 2;
////                read = 4;
////            } else {
////                return;
////            }
////            progress++;
////        }
////        // 失压记录
////        else if (progress == 0) {
////            if (chs[progress] == true) {
////                String stTemp = strFPart + " 68 " + strMPart + " " + strLPart1;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart1) + " 16";
////                temp = 3;
////                read = 0;
////            } else {
////                return;
////            }
////            progress++;
////        }
////        // 失流记录
////        else if (progress == 1) {
////            if (chs[progress] == true) {
////                String stTemp = strFPart + " 68 " + strMPart + " " + strLPart2;
////                strMessage = stTemp
////                        + " "
////                        + FrameFormat.StingtoGetCS("68 " + strMPart + " "
////                        + strLPart2) + " 16";
////                temp = 3;
////                read = 1;
////            } else {
////                return;
////            }
////            progress++;
////        } else {
//////            bflag = false;
//////            read_other.setText("读取");
//////            read_other.setEnabled(true);
////            Message msg = new Message();
////            msg.what = 4;
////            handler.sendMessage(msg);
//////            mSerialPortOpera.closeSerialPort();
////            return;
////            // poweroff();
//////            bopen = true;
////        }
////        TimerTask timerTask = new TimerTask() {
////            @Override
////            public void run() {
////                sendAMessage("", progress);
////            }
////        };
////        new Timer().schedule(timerTask, 30000L);
//////        byte[] buffers = HexStringTwoTobytes(strMessage);
////        byte[] buffers = HexUtils.hexStringToByte(strMessage.replace(" ", ""));
////        mSerialPortOpera.SerialPortWrite(buffers);
////    }
//
//    //HexToBytes
//    public static final byte[] HexStringTwoTobytes(String str) {
//        String sTemp[] = str.split(" ");
//        byte[] rec = new byte[sTemp.length];
//        for (int i = 0; i < sTemp.length; i++) {
//            rec[i] = (byte) (Integer.parseInt(sTemp[i], 16));
//        }
//        return rec;
//    }
//
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
//                    int bt_length;
//                    try {
//                        bt_length = Integer.parseInt(accept_show.substring(39, 41), 16);
//                    } catch (Exception e) {
//                        bt_length = 0;
//                        Log.d("debug", "返回信息格式错误");
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
//                            String biaohao1 = "", biaohao2 = "", biaohao3 = "", biaohao4 = "", biaohao5 = "";
////                        String biaohao0 = Integer.toHexString(iArray[resk + 19] - 0x33);
////                        biaohao1 = Integer.toHexString(iArray[resk + 18] - 0x33);
////                        biaohao2 = Integer.toHexString(iArray[resk + 17] - 0x33);
////                        biaohao3 = Integer.toHexString(iArray[resk + 16] - 0x33);
////                        biaohao4 = Integer.toHexString(iArray[resk + 15] - 0x33);
////                        biaohao5 = Integer.toHexString(iArray[resk + 14] - 0x33);
//                            String biaohao0 = Integer.toHexString(iArray[resk + 1]);
//                            biaohao1 = Integer.toHexString(iArray[resk + 2]);
//                            biaohao2 = Integer.toHexString(iArray[resk + 3]);
//                            biaohao3 = Integer.toHexString(iArray[resk + 4]);
//                            biaohao4 = Integer.toHexString(iArray[resk + 5]);
//                            biaohao5 = Integer.toHexString(iArray[resk + 6]);
//                            if (Integer.parseInt(biaohao0) < 10) {
//                                biaohao0 = "0" + biaohao0;
//                            }
//                            if (Integer.parseInt(biaohao1)< 10) {
//                                biaohao1 = "0" + biaohao1;
//                            }
//                            if (Integer.parseInt(biaohao2) < 10) {
//                                biaohao2 = "0" + biaohao2;
//                            }
//                            if (Integer.parseInt(biaohao3) < 10) {
//                                biaohao3 = "0" + biaohao3;
//                            }
//                            if (Integer.parseInt(biaohao4) < 10) {
//                                biaohao4 = "0" + biaohao4;
//                            }
//                            if (Integer.parseInt(biaohao5)< 10) {
//                                biaohao5 = "0" + biaohao5;
//                            }
//                            String biaohao = biaohao0 + biaohao1 + biaohao2 + biaohao3 + biaohao4 + biaohao5;
////                        bh = biaohao0 + biaohao1 + biaohao2 + biaohao3 + biaohao4 + biaohao5;
//                            Log.d("b5 b4 b3 b2 b1 b0:",biaohao5+","+biaohao4+","+biaohao3+","+biaohao2+","+biaohao1+","+biaohao0);
////                        bh = biaohao5 + biaohao4 + biaohao3 + biaohao2 + biaohao1 + biaohao0;
//                            bh = biaohao ;
//                            text_result1.setText(text_result1.getText() + str0 + biaohao5 + biaohao4 + biaohao3 + biaohao2 + biaohao1 + biaohao0 + "\n" + "\n");
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
//                            } catch (Exception e) {
//                                String I = "电流:" + DataFormat3(iArray[resk + 14], iArray[resk + 15], iArray[resk + 16]);
//                                text_result1.setText(text_result1.getText() + str1 + I + "\n");
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
//                            } catch (Exception e){
//                                String U = "电压:" + DataFormat4(iArray[resk + 14], iArray[resk + 15]);
//                                text_result1.setText(text_result1.getText() + str2 + U + "\n");
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
//                            } catch (Exception e){
//                                String Wz = "总功率因数:" + DataFormat5(iArray[resk + 14], iArray[resk + 15]);
//                                String W = "功率因数:" + DataFormat5(iArray[resk + 16], iArray[resk + 17]);
//                                text_result1.setText(text_result1.getText() + str3 + Wz + "\n" + W + "\n");
//                            }
////                        savaData();
//                        }
//                        //33 33 34 36失压记录
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x33 && iArray[resk + 12] == 0x34 && iArray[resk + 13] == 0x36) {
//                            int redata = DataFormat1(iArray[14], iArray[15], iArray[16]);
//                            str4 += "\nA相失压总次数为" + redata + "次\n";
//                            redata = DataFormat1(iArray[17], iArray[18], iArray[19]);
//                            str4 += "\nA相失压累计时间为" + redata + "分\n";
//                            try {
//                                redata = DataFormat1(iArray[20], iArray[21], iArray[22]);
//                                str4 += "\nB相失压总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[23], iArray[24], iArray[25]);
//                                str4 += "\nB相失压累计时间为" + redata + "分\n";
//                                redata = DataFormat1(iArray[26], iArray[27], iArray[28]);
//                                str4 += "\nC相失压总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[29], iArray[30], iArray[31]);
//                                str4 += "\nC相失压累计时间为" + redata + "分\n";
//                            }catch(Exception e){}
//                            text_result1.setText(text_result1.getText() + str4);
//                        }
//                        //失流
//                        else if (iArray[resk + 10] == 0x33 && iArray[resk + 11] == 0x33 && iArray[resk + 12] == 0x3E && iArray[resk + 13] == 0x36) {
//                            int redata = DataFormat1(iArray[14], iArray[15], iArray[16]);
//                            str5 += "\nA相失流总次数为" + redata + "次\n";
//                            redata = DataFormat1(iArray[17], iArray[18], iArray[19]);
//                            str5 += "\nA相失流累计时间为" + redata + "分\n";
//                            if ((bt_length - 4) / temp == 6) {
//                                redata = DataFormat1(iArray[20], iArray[21], iArray[22]);
//                                str5 += "\nB相失流总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[23], iArray[24], iArray[25]);
//                                str5 += "\nB相失流累计时间为" + redata + "分\n";
//                                redata = DataFormat1(iArray[26], iArray[27], iArray[28]);
//                                str5 += "\nC相失流总次数为" + redata + "次\n";
//                                redata = DataFormat1(iArray[29], iArray[30], iArray[31]);
//                                str5 += "\nC相失流累计时间为" + redata + "分\n";
//                            }
//                            text_result1.setText(text_result1.getText() + str5);
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
//                            text_result1.append("开盖结束时间:"
//                                    + DataFormatDate(iArray[resk + 20],
//                                    iArray[resk + 21], iArray[resk + 22],
//                                    iArray[resk + 23], iArray[resk + 24],
//                                    iArray[resk + 25]) + "\n");
//                            text_result1.append("开盖前正向有功总电能:"
//                                    + DataFormat(iArray[resk + 26],
//                                    iArray[resk + 27], iArray[resk + 28],
//                                    iArray[resk + 29]) + "kwh\n");
//                            text_result1.append("开盖前反向有功总电能:"
//                                    + DataFormat(iArray[resk + 30],
//                                    iArray[resk + 31], iArray[resk + 32],
//                                    iArray[resk + 33]) + "kwh\n");
//                            text_result1.append("开表盖前第一象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 34],
//                                    iArray[resk + 35], iArray[resk + 36],
//                                    iArray[resk + 37]) + "kvarh\n");
//                            text_result1.append("开表盖前第二象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 38],
//                                    iArray[resk + 39], iArray[resk + 40],
//                                    iArray[resk + 41]) + "kvarh\n");
//                            text_result1.append("开表盖前第三象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 42],
//                                    iArray[resk + 43], iArray[resk + 44],
//                                    iArray[resk + 45]) + "kvarh\n");
//                            text_result1.append("开表盖前第四象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 46],
//                                    iArray[resk + 47], iArray[resk + 48],
//                                    iArray[resk + 49]) + "kvarh\n");
//                            text_result1.append("开盖后正向有功总电能:"
//                                    + DataFormat(iArray[resk + 50],
//                                    iArray[resk + 51], iArray[resk + 52],
//                                    iArray[resk + 53]) + "kwh\n");
//                            text_result1.append("开盖后反向有功总电能:"
//                                    + DataFormat(iArray[resk + 54],
//                                    iArray[resk + 55], iArray[resk + 56],
//                                    iArray[resk + 57]) + "kwh\n");
//                            text_result1.append("开表盖后第一象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 58],
//                                    iArray[resk + 59], iArray[resk + 60],
//                                    iArray[resk + 61]) + "kvarh\n");
//                            text_result1.append("开表盖后第二象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 62],
//                                    iArray[resk + 63], iArray[resk + 64],
//                                    iArray[resk + 65]) + "kvarh\n");
//                            text_result1.append("开表盖后第三象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 66],
//                                    iArray[resk + 67], iArray[resk + 68],
//                                    iArray[resk + 69]) + "kvarh\n");
//                            text_result1.append("开表盖后第四象限无功总电能 :"
//                                    + DataFormat(iArray[resk + 70],
//                                    iArray[resk + 71], iArray[resk + 72],
//                                    iArray[resk + 73]) + "kvarh\n");
//                        } else {
//                            recstr += "没有解析到有效信息\n";
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
//    public static void setSpinnerItemSelectedByValue(Spinner spinner, Object value) {
//        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
//        int k = apsAdapter.getCount();
//        for (int i = 0; i < k; i++) {
//            if (value.equals(apsAdapter.getItem(i).toString())) {
//                spinner.setSelection(i, true);// 默认选中
//
//                break;
//            }
//        }
//    }
//    @Override
//    protected void onDestroy() {
//        if (thread != null)
//            thread.cancel(true);
//        mSerialPortOpera.closeSerialPort();
//        super.onDestroy();
//    }
//}
