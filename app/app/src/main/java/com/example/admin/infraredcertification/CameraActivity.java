package com.example.admin.infraredcertification;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keymantek.serialport.utils.HexUtils;
import com.keymantek.serialport.utils.SerialPortOpera;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import tw.com.prolific.pl2303multilib.PL2303MultiLib;

public class CameraActivity extends Activity implements SurfaceHolder.Callback {
    private UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private InputStream mBlueToothInputStream;
    private Camera mCamera;
    private SurfaceView mSurfaceView;
    private SurfaceHolder holder;// SurfaceView的控制器
    private Bitmap baseBitmap;
    private MyCanvas canvas;
    private ImageView iv_canvas;
    private TextView tvTime, tvLocation;
    private Paint paint;
    private android.hardware.Camera.Parameters mParams;
    private SerialPortOpera mSerialPortOpera;
    private InputStream mInputStream;
    private ReadThread thread;
    private String b1 = "0A000000";
    private String b2 = "0A";
    private String info;
    private boolean timeController = true;
    private int i = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private LocationManager locationManager;
    private Button mButtonCamera, mButtonVideo, mButtonList, mButtonBlueTooth,tvStrength;
    private PadyDatabaseHelper padyDatabaseHelper;
    private RelativeLayout relativeLayout;
    private int strength = 0;
    private MediaRecorder mediaRecorder;
    private boolean video = true;
    private static List<BluetoothDevice> list = new ArrayList<BluetoothDevice>();
    private static ArrayList<String> mArrayList;
    private static BluetoothAdapter mBluetoothAdapter;
    private Thread blueToothThread;
    private boolean key = true;
    private ConnectThread connectThread;
    private int[] array=new int[5];
    private int arrayKey=0;


    private static boolean bDebugMesg = true;

    PL2303MultiLib mSerialMulti;
    private static final int MENU_ABOUT = 0;

    private static enum DeviceOrderIndex {
        DevOrder1,
        DevOrder2,
        DevOrder3,
        DevOrder4
    };
    private static final int DeviceIndex1 = 0;
    private static final int DeviceIndex2 = 1;
    private static final int DeviceIndex3 = 2;
    //private static final int DeviceIndex4 = 3;

    private static final int MAX_DEVICE_COUNT = 4;
    private static final String ACTION_USB_PERMISSION = "com.prolific.pluartmultisimpletest.USB_PERMISSION";
    private UARTSettingInfo gUARTInfoList[];
    private int iDeviceCount = 0;
    private boolean bDeviceOpened[] = new boolean[MAX_DEVICE_COUNT];

    private boolean gThreadStop[] = new boolean[MAX_DEVICE_COUNT];
    private boolean gRunningReadThread[] = new boolean[MAX_DEVICE_COUNT];


    @SuppressLint("HandlerLeak")
    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Toast.makeText(CameraActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
            super.handleMessage(msg);
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                baseBitmap = Bitmap.createBitmap(1080,
                        810, Bitmap.Config.ARGB_8888);
                canvas = new MyCanvas(baseBitmap);
                canvas.drawColor(0x00000000);
                if ((!b1.startsWith(b2)) && b1.contains(b2)) {
                    b1 = b1.substring(b1.indexOf(b2));
                }
                if (b1.length() >= 8) {
                    info = b1.substring(0, 8);
                    Log.i("value of b:", b1);
                    b1 = b1.substring(8);
                    byte[] byteInfo = HexUtils.hexStringToByte(info);
                    Log.i("========", String.valueOf(((byteInfo[3] & 0x0FF))) + "=======" + String.valueOf(((byteInfo[1] & 0x0FF) + (byteInfo[2] & 0x0FF)) % 256));
                    if (((byteInfo[3] & 0x0FF)) == (((byteInfo[1] & 0x0FF) + (byteInfo[2] & 0x0FF)) % 256)) {
                        strength = (byteInfo[1] & 0x0FF) * 256 + (byteInfo[2] & 0x0FF);
                        array[arrayKey]=strength;
                        arrayKey++;
                        if(arrayKey==5){
                            arrayKey=0;
                            canvas.setXY(430, 350);
                            strength = HexUtils.GetMax(array,5);
                            canvas.draw(strength);
                            tvStrength.setText(String.valueOf((int)(strength/(8.0*256.0)*100))+"%"+"  "+String.valueOf(strength));
                            iv_canvas.setImageBitmap(baseBitmap);
                        }

                    } else {
//                        mSerialPortOpera.SerialPortWrite(new byte[]{0x0a, 0x0b});
                    }
//
                }
            } else if (msg.what == 3) {
                Timestamp ts = new Timestamp(System.currentTimeMillis());
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                tvTime.setText(sdf.format(ts));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkCameraHardware(this)) {
            Log.e("============", "摄像头存在");// 验证摄像头是否存在
        }
        /* 隐藏状态栏 */
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* 隐藏标题栏 */
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /* 设定屏幕显示为横向 */
        setContentView(R.layout.activity_camera);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        iv_canvas = (ImageView) findViewById(R.id.imageView);
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mSurfaceView.setDrawingCacheEnabled(true);

        holder = mSurfaceView.getHolder();
        holder.addCallback(this);
//        mSurfaceView.setOnTouchListener(touch);

        tvStrength=(Button)findViewById(R.id.tv_strength);
        tvTime = (TextView) findViewById(R.id.tv_time);
        Timestamp ts = new Timestamp(System.currentTimeMillis());
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        tvTime.setText(sdf.format(ts));
        tvLocation = (TextView) findViewById(R.id.tv_location);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (timeController) {
                    handler.sendEmptyMessage(3);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            getLocation();
//            //gps已打开
//        } else {
//            toggleGPS();
//            new Handler() {
//            }.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    getLocation();
//                }
//            }, 2000);
//
//        }

        //initBlueTooth();

        DumpMsg("Enter onCreate");

        mSerialMulti = new PL2303MultiLib((UsbManager) getSystemService(Context.USB_SERVICE),
                this, ACTION_USB_PERMISSION);
        gUARTInfoList = new UARTSettingInfo[MAX_DEVICE_COUNT];

        for(int i=0;i<MAX_DEVICE_COUNT;i++) {
            gUARTInfoList[i] = new UARTSettingInfo();
            gUARTInfoList[i].iPortIndex = i;
            gThreadStop[i] = false;
            gRunningReadThread[i] = false;
            bDeviceOpened[i] = false;
        }


        int iBaudRate=0, iSelected = 0;
        iSelected = DeviceIndex1;
        UARTSettingInfo info = new UARTSettingInfo();
        PL2303MultiLib.BaudRate rate;
        info.iPortIndex = iSelected;
        gUARTInfoList[iSelected] = info;
        try {
            mSerialMulti.PL2303SetupCOMPort(iSelected, PL2303MultiLib.BaudRate.B115200, info.mDataBits, info.mStopBits,
                    info.mParity, info.mFlowControl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gUARTInfoList[iSelected] = info;


        mButtonBlueTooth = (Button) findViewById(R.id.choose_bluetooth);
        mButtonBlueTooth.setText("打开");
        mButtonBlueTooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenUARTDevice(DeviceIndex1);
            }
        });
//        mButtonBlueTooth.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                key = false;
//                AlertDialog.Builder builder = new AlertDialog.Builder(CameraActivity.this, R.style.MyDialogTheme);
//                builder.setTitle("选择设备");
//                builder.setSingleChoiceItems((String[]) mArrayList.toArray(new String[mArrayList.size()]), 0, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Log.d("debug", list.get(which).getName());
//                        BluetoothDevice device1 = list.get(which);
//                        key = true;
//                        connectThread = new ConnectThread(device1);
//                        connectThread.start();
//                        dialog.dismiss();
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
//            }
//        });
        mButtonCamera = (Button) findViewById(R.id.picture);
        mButtonVideo = (Button) findViewById(R.id.video);
        mButtonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doTakePic();
//                GetandSaveCurrentImage();
//                LayoutInflater factory = LayoutInflater.from(CameraActivity.this);
//                View view = factory.inflate(R.layout.activity_camera, null);
//                view.setDrawingCacheEnabled(true);
//                Bitmap bitmap = getViewBitmap(view);
//                saveFile(bitmap);
//                padyScreenShoot()
            }
        });
        mButtonList = (Button) findViewById(R.id.list);
        mButtonList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CameraActivity.this, PictureActivity.class);
                startActivity(intent);
            }
        });

        mButtonVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (video) {
                    mButtonVideo.setText("结束");
                    video = false;
                    startRecord();
                } else {
                    mButtonVideo.setText("录制");
                    video = true;
                    stopRecord();
                }
            }
        });

        padyDatabaseHelper = new PadyDatabaseHelper(this, "pictures.db", null, 1);
        padyDatabaseHelper.getWritableDatabase();
        handler.sendEmptyMessage(1);

        final IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        Toast.makeText(getApplication(), "start to discover", Toast.LENGTH_SHORT).show();
        registerReceiver(mReceiver, filter);
    }

    // 检测摄像头是否存在的私有方法
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // 摄像头存在
            return true;
        } else {
            // 摄像头不存在
            return false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera = null;
            try {
                mCamera = Camera.open();//打开相机；在低版本里，只有open（）方法；高级版本加入此方法的意义是具有打开多个
                //摄像机的能力，其中输入参数为摄像机的编号
                //在manifest中设定的最小版本会影响这里方法的调用，如果最小版本设定有误（版本过低），在ide里将不允许调用有参的
                //open方法;
                //如果模拟器版本较高的话，无参的open方法将会获得null值!所以尽量使用通用版本的模拟器和API；
            } catch (Exception e) {
                Log.e("============", "摄像头被占用");
                e.printStackTrace();
            }
            mCamera.setPreviewDisplay(holder);//设置显示面板控制器
//            priviewCallBack pre = new priviewCallBack();//建立预览回调对象
//            mCamera.setPreviewCallback(pre); //设置预览回调对象
//            //mCamera.getParameters().setPreviewFormat(ImageFormat.JPEG);
//            mParams = mCamera.getParameters();
            mCamera.startPreview();//开始预览，这步操作很重要
        } catch (IOException exception) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        holder.removeCallback(this);
        stopCamera();
        mCamera.release();
        Log.i("MainActivity", "camera release=============");
    }

    // 每次cam采集到新图像时调用的回调方法，前提是必须开启预览
    class priviewCallBack implements Camera.PreviewCallback {

        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {
        }
    }

    @SuppressLint("NewApi")
    private void initCamera() {
        if (mCamera != null) {
            try {
                Camera.Parameters parameters = mCamera.getParameters();
                /*
                 * 设定相片大小为1024*768， 格式为JPG
                 */
                parameters.setPreviewSize(1280, 720);
                parameters.setRecordingHint(true);
                parameters.setPictureFormat(PixelFormat.JPEG);
                parameters.setPictureSize(1280, 720);
                mCamera.setParameters(parameters);
                /* 打开预览画面 */
                mCamera.startPreview();
                mediaRecorder = new MediaRecorder();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /* 停止相机的method */
    private void stopCamera() {
        if (mCamera != null) {
            try {
                /* 停止预览 */
                Log.i("MainActivity", "stopCamera ++++++++++++++");
                mCamera.stopPreview();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

//    private View.OnTouchListener touch = new View.OnTouchListener() {
//        float startX;
//        float startY;
//
//        @Override
//        public boolean onTouch(View v, MotionEvent event) {
//            switch (event.getAction()) {
//                case MotionEvent.ACTION_DOWN:
//                    if (baseBitmap == null) {
//                        baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(),
//                                iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
//                        canvas = new Canvas(baseBitmap);
//                        canvas.drawColor(0x00000000);
//                    }
//                    startX = event.getX();
//                    startY = event.getY();
//                    break;
//                case MotionEvent.ACTION_MOVE:
//                    float stopX = event.getX();
//                    float stopY = event.getY();
//                    canvas.drawLine(startX, startY, stopX, stopY, paint);
//                    startX = event.getX();
//                    startY = event.getY();
//                    iv_canvas.setImageBitmap(baseBitmap);
//                    break;
//                case MotionEvent.ACTION_UP:
//                    break;
//            }
//            return true;
//        }
//    };


    private class ReadThread1 extends ReadThread {
        public ReadThread1(InputStream mInputStream) {
            super(mInputStream);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            //handler处理msg;
            b1 += values[0];
            final Message msg = new Message();
//            TimerTask timerTask = new TimerTask() {
//                @Override
//                public void run() {
//                    msg.obj = HexUtils.hexStringToByte(b1.replace(" ", ""));
//                    msg.what = 0;
//                    handler.sendMessage(msg);
//                }
//            };
//            new Timer().schedule(timerTask, 1000L);
//            msg.obj = HexUtils.hexStringToByte(b1.replace(" ", ""));
            b1 = b1.replaceAll(" ", "");
            msg.what = 1;
            handler.sendMessage(msg);

        }
    }

//    private void toggleGPS() {
//        Intent gpsIntent = new Intent();
//        gpsIntent.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
//        gpsIntent.addCategory("android.intent.category.ALTERNATIVE");
//        gpsIntent.setData(Uri.parse("custom:3"));
//        try {
//            PendingIntent.getBroadcast(this, 0, gpsIntent, 0).send();
//        } catch (PendingIntent.CanceledException e) {
//            e.printStackTrace();
//            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
//            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
//            if (location1 != null) {
//                latitude = location1.getLatitude(); // 经度
//                longitude = location1.getLongitude(); // 纬度
//                Log.e("location", String.valueOf(latitude) + String.valueOf(longitude) + "++++++++++++++++++++++");
//            }
//        }
//    }

//    private void getLocation() {
//        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (location != null) {
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
//            Log.e("location", String.valueOf(latitude) + String.valueOf(longitude) + "++++++++++++++++++++++");
//
//        } else {
//            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);
//        }
//        tvLocation.setText("纬度：" + latitude + "   " + "经度：" + longitude);
//    }
//
//    LocationListener locationListener = new LocationListener() {
//        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
//        @Override
//        public void onStatusChanged(String provider, int status, Bundle extras) {
//        }
//
//        // Provider被enable时触发此函数，比如GPS被打开
//        @Override
//        public void onProviderEnabled(String provider) {
//            Log.e(TAG, provider);
//        }
//
//        // Provider被disable时触发此函数，比如GPS被关闭
//        @Override
//        public void onProviderDisabled(String provider) {
//            Log.e(TAG, provider);
//        }
//
//        // 当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
//        @Override
//        public void onLocationChanged(Location location) {
//            if (location != null) {
//                Log.e("Map", "Location changed : Lat: " + location.getLatitude() + " Lng: " + location.getLongitude());
//                latitude = location.getLatitude(); // 经度
//                longitude = location.getLongitude(); // 纬度
//                Log.e("location", String.valueOf(latitude) + String.valueOf(longitude) + "++++++++++++++++++++++");
//                tvLocation.setText("纬度：" + latitude + "   " + "经度：" + longitude);
//            }
//        }
//    };

    /**
     * 获取和保存当前屏幕的截图
     */
    private void GetandSaveCurrentImage() {
        // 1.构建Bitmap
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        // 2.获取屏幕
        View decorview = this.getWindow().getDecorView();
        decorview.setDrawingCacheEnabled(true);
        Bmp = decorview.getDrawingCache();
        String SavePath = this.getFilesDir().getPath().toString() + "/";

        // 3.保存Bitmap
        try {
            File path = new File(SavePath);
            // 文件
            try {
                String command = "chmod 777 " + this.getFilesDir().getPath().toString();
                Log.i("Pady", "command = " + command);
                Runtime runtime = Runtime.getRuntime();

                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("cnm", "chmod fail!!!!");
                e.printStackTrace();
            }
            String filepath = String.valueOf(System.currentTimeMillis()) + ".png";
            File file = new File(SavePath, filepath);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            try {
                String command = "chmod 777 " + file.getAbsolutePath();
                Log.i("zyl", "command = " + command);
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("zyl", "chmod fail!!!!");
                e.printStackTrace();
            }
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(this, "截屏文件已保存至" + this.getFilesDir().getPath().toString() + "下",
                        Toast.LENGTH_LONG).show();
            }
            SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("time", filepath);
            values.put("isPicture", true);
            db.insert("Picture", null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取SDCard的目录路径功能
     */
    private String getSDCardPath() {
        File sdcardDir = null;
        // 判断SDCard是否存在
        boolean sdcardExist = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        if (sdcardExist) {
            sdcardDir = Environment.getExternalStorageDirectory();
        }
        return sdcardDir.toString();
    }

    @Override
    protected void onPause() {
//        mSerialPortOpera.closeSerialPort();
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(getRequestedOrientation()!=ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        WindowManager wm = (WindowManager) this
                .getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        Log.i("mSurfaceView", "width:" + width + "================height:" + height);
        holder = mSurfaceView.getHolder();
        holder.addCallback(this);

        DumpMsg("Enter onResume");
        String action =  getIntent().getAction();
        DumpMsg("onResume:"+action);
        iDeviceCount = mSerialMulti.PL2303Enumerate();
        DumpMsg("enumerate Count="+iDeviceCount);
        if( 0==iDeviceCount ) {
            Toast.makeText(this, "no more devices found", Toast.LENGTH_SHORT).show();
        } else {
            DumpMsg("DevOpen[0]="+bDeviceOpened[DeviceIndex1]);
            DumpMsg("DevOpen[1]="+bDeviceOpened[DeviceIndex2]);
            DumpMsg("DevOpen[2]="+bDeviceOpened[DeviceIndex3]);
            if(!bDeviceOpened[DeviceIndex1]) {
                DumpMsg("iDeviceCount(=1)="+iDeviceCount);
            }
            if(iDeviceCount>=2) {
                DumpMsg("iDeviceCount(>=2)="+iDeviceCount);
                if(!bDeviceOpened[DeviceIndex2]) {
                }
                if(iDeviceCount==2) {
                }
            }
            if(iDeviceCount>=3){
                DumpMsg("iDeviceCount(>=3)="+iDeviceCount);
                if(!bDeviceOpened[DeviceIndex3]) {
                }
            }
            //register receiver for PL2303Multi_USB notification
            IntentFilter filter = new IntentFilter();
            filter.addAction(mSerialMulti.PLUART_MESSAGE);
            registerReceiver(PLMultiLibReceiver, filter);
            Toast.makeText(this, "The "+iDeviceCount+" devices are attached", Toast.LENGTH_SHORT).show();
        }//if( 0==iDevCnt )

        super.onResume();
        DumpMsg("Leave onResume");
    }

    public Bitmap getViewBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(256, View.MeasureSpec.EXACTLY));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap1 = view.getDrawingCache();
        return bitmap1;
    }

    public void saveFile(Bitmap bitmap) {

        String SavePath = this.getFilesDir().getPath().toString() + "/";
        try {
            File path = new File(SavePath);
            try {
                String command = "chmod 777 " + this.getFilesDir().getPath().toString();
                Log.i("zyl", "command = " + command);
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("zyl", "chmod fail!!!!");
                e.printStackTrace();
            }
            Log.i("11111", String.valueOf(path.setWritable(true)));
            String filepath = String.valueOf(System.currentTimeMillis()) + ".png";
            File file = new File(SavePath, filepath);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            try {
                String command = "chmod 777 " + file.getAbsolutePath();
                Log.i("zyl", "command = " + command);
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("zyl", "chmod fail!!!!");
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.flush();
                    fos.close();
                    Toast.makeText(this, "截屏文件已保存至" + this.getFilesDir().getPath().toString() + "下",
                            Toast.LENGTH_LONG).show();
                    SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("time", filepath);
                    values.put("isPicture", true);
                    db.insert("Picture", null, values);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void padyScreenShoot() {
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        int w = display.getWidth();
        int h = display.getHeight();
        Bitmap Bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        MyCanvas canvas = (MyCanvas) holder.lockCanvas();
        if (Bmp == null) {
            Log.i("Bitmap:", "null");
        }
        if (canvas == null) {
            Log.i("Canvas:", "null");
        }
        canvas.setBitmap(Bmp);
        canvas.draw(strength);
        Paint paint = new Paint(1);
        paint.setTextSize(25);
        paint.setColor(0xffffffff);
        canvas.drawText((String) tvTime.getText(), 20, 20, paint);
        String SavePath = this.getFilesDir().getPath().toString() + "/";
        try {
            File path = new File(SavePath);
            // 文件
            try {
                String command = "chmod 777 " + this.getFilesDir().getPath().toString();
                Log.i("zyl", "command = " + command);
                Runtime runtime = Runtime.getRuntime();

                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("zyl", "chmod fail!!!!");
                e.printStackTrace();
            }
            Log.i("11111", String.valueOf(path.setWritable(true)));
            String filepath = String.valueOf(System.currentTimeMillis()) + ".png";
            File file = new File(SavePath, filepath);
            FileOutputStream fos = null;
            fos = new FileOutputStream(file);
            try {
                String command = "chmod 777 " + file.getAbsolutePath();
                Log.i("zyl", "command = " + command);
                Runtime runtime = Runtime.getRuntime();
                Process proc = runtime.exec(command);
            } catch (IOException e) {
                Log.i("zyl", "chmod fail!!!!");
                e.printStackTrace();
            }
            if (null != fos) {
                Bmp.compress(Bitmap.CompressFormat.PNG, 90, fos);
                fos.flush();
                fos.close();
                Toast.makeText(this, "截屏文件已保存至" + this.getFilesDir().getPath().toString() + "下",
                        Toast.LENGTH_LONG).show();
            }
            SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("time", filepath);
            values.put("isPicture", true);
            db.insert("Picture", null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void doTakePic() {
        if (mCamera != null) {
            mCamera.takePicture(new ShutCallBackImpl(), null, new PicCallBacKImpl());
        }
    }

    private class ShutCallBackImpl implements Camera.ShutterCallback {
        @Override
        public void onShutter() {

        }
    }

    /**
     * 拍照后的最主要的返回
     */
    private class PicCallBacKImpl implements Camera.PictureCallback {
        @Override
        public void onPictureTaken(final byte[] data, Camera camera) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String fileName = String.valueOf(System.currentTimeMillis()) + ".jpg";
                    String SavePath = CameraActivity.this.getFilesDir().getPath().toString() + "/";
                    String tempPath = SavePath + "/temp" + fileName;
                    File file = new File(tempPath);
                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream(file, true);
                        fos.write(data);
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        fos.close();

                        Bitmap tempBitmap = BitmapFactory.decodeFile(tempPath).copy(Bitmap.Config.ARGB_8888, true);
                        MyCanvas canvas = new MyCanvas(tempBitmap);
                        canvas.setXY(430, 350);
                        canvas.draw(strength);
                        Paint paint = new Paint(1);
                        paint.setColor(0xffffffff);
                        paint.setTextSize(27);
                        canvas.drawText(String.valueOf(tvTime.getText()) + " FREQ:40kHZ", 20, 30, paint);
                        File file1 = new File(SavePath, fileName);
                        FileOutputStream fos1 = new FileOutputStream(file1, true);
                        tempBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos1);
                        fos1.flush();
                        fos1.close();
                        String command = "chmod 777 " + file.getAbsolutePath();
                        Runtime runtime = Runtime.getRuntime();
                        Process proc = runtime.exec(command);

                        SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put("time", fileName);
                        values.put("isPicture", true);
                        db.insert("Picture", null, values);
                        //CameraActivity.this.deleteFile("temp"+fileName);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            mCamera.startPreview();//重新开启预览 ，不然不能继续拍照
        }
    }

    private void startRecord() {
        //先释放被占用的camera，在将其设置为mediaRecorder所用的camera
        mCamera.unlock();
        mediaRecorder.setCamera(mCamera);

        mediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
        //设置音频的来源  麦克风
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        //设置视频的来源
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        //设置视频的输出格式  3gp
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        //设置视频中的声音和视频的编码格式
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.DEFAULT);
        //设置保存的路径
        mediaRecorder.setVideoSize(400 * 4, 400 * 3);
        String SavePath = CameraActivity.this.getFilesDir().getPath().toString() + "/";
        String fileName = String.valueOf(System.currentTimeMillis());
        mediaRecorder.setOutputFile(SavePath + fileName + ".3gp");
        //开始录制
        String command = "chmod 777 " + SavePath + fileName + ".3gp";
        Log.i("zyl", "command = " + command);
        Runtime runtime = Runtime.getRuntime();
        try {
            Process proc = runtime.exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SQLiteDatabase db = padyDatabaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("time", fileName + ".3gp");
        values.put("isPicture", false);
        db.insert("Picture", null, values);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecord() {
        //当结束录制之后，就将当前的资源都释放
        mediaRecorder.release();
        mediaRecorder = null;
        //然后再重新初始化所有的必须的控件和对象
        mediaRecorder = new MediaRecorder();
    }


    @Override
    protected void onStart() {
//        mSerialPortOpera = new SerialPortOpera();
//        try {
//            mSerialPortOpera.openSerialPort("/dev/ttyS7", 115200, 8, 'N', 1);
//            //获取输入流
//            mInputStream = mSerialPortOpera.SerialPortReceiver();
//            //创建读取的线程
//            thread = new ReadThread1(mInputStream);
//            thread.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onStart();
    }

    @Override
    protected void onStop() {
        if(connectThread!=null){
            connectThread.cancel();
        }
        key=false;
//        thread.cancel(true);
        super.onStop();
    }

    @Override
    protected void onRestart() {
        mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    if (list.contains(device))
                        Log.d("debug", device.getName());
                    else {
                        list.add(device);
                        mArrayList.add(device.getName());
                    }
                }
            }
        };
//        mSerialPortOpera = new SerialPortOpera();
//        try {
//            mSerialPortOpera.openSerialPort("/dev/ttyS7", 115200, 8, 'N', 1);
//            //获取输入流
//            mInputStream = mSerialPortOpera.SerialPortReceiver();
//            //创建读取的线程
//            thread = new ReadThread1(mInputStream);
//            thread.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        super.onRestart();
    }

    private BroadcastReceiver mReceiver;

    private void initBlueTooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(getApplication(), "not support bluetooth", Toast.LENGTH_SHORT).show();
        } else
//            Toast.makeText(getApplication(), "support bluetooth", Toast.LENGTH_SHORT).show();
            if (!mBluetoothAdapter.isEnabled()) {
                Toast.makeText(getApplication(), "is not enable", Toast.LENGTH_SHORT).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        mArrayList = new ArrayList<String>();
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        //获得已配对的设备
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                list.add(device);
                mArrayList.add(device.getName());
            }
        }
    }

    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;
        private BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            mmDevice = device;
            startConnect(mmDevice);
        }

        public void startConnect(BluetoothDevice device) {

            BluetoothSocket tmp = null;
            mmDevice = device;

            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mmSocket = tmp;
        }

        @Override
        public void run() {
            try {
                mmSocket.connect();
            } catch (IOException connectException) {
                connectException.printStackTrace();
                Log.d("debug", "cannot connect");
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                return;
            }
            Log.d("debug", "before manage");
            handler1.sendMessage(new Message());
            // Do work to manage the connection (in a separate thread)
            manageConnectedSocket(mmSocket);
        }

        /**
         * Will cancel an in-progress connection, and close the socket
         */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }

    private void manageConnectedSocket(BluetoothSocket mmSocket) {
        try {
            mBlueToothInputStream = mmSocket.getInputStream();
            blueToothThread = new Thread() {
                @Override
                public void run() {
                    while (key) {
                        int count = 0;
                        try {
                            while (count == 0) {
                                count = mBlueToothInputStream.available();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        byte[] buffer = new byte[count];
                        int readCount = 0; // 已经成功读取的字节的个数
                        while (readCount < count) {
                            try {
                                readCount += mBlueToothInputStream.read(buffer, readCount, count - readCount);
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ArrayIndexOutOfBoundsException e) {

                            }
                        }
//                        try {
//                            mBlueToothInputStream.read(buffer);
//                        } catch (IOException e1) {
//                            e1.printStackTrace();
//                        }
                        try{
                            String data = HexUtils.bytesToHexString(buffer,
                                    count);
                            b1 += data;
                            final Message msg = new Message();
                            b1 = b1.replaceAll(" ", "");
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }catch (ArrayIndexOutOfBoundsException e){
                        }

//                        int readCount = 0; // 已经成功读取的字节的个数
//                        boolean key1=false;
//                        while (readCount < count) {
//                            try {
//                                readCount += mBlueToothInputStream.read(buffer, readCount, count - readCount);
//                            } catch (IOException e) {
//                                break;
//                            }catch (ArrayIndexOutOfBoundsException e){
//                                key1=true;
//                                e.printStackTrace();
//                            }
//                        }
//                        if(key1){
//                            continue;
//                        }
//                        if (readCount > 0) {
//                            String data = HexUtils.bytesToHexString(buffer,
//                                    readCount);
//                            b1 += data;
//                            final Message msg = new Message();
//                            b1 = b1.replaceAll(" ", "");
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        } else {
//                            String data=new String(buffer, 0, readCount);
//                            b1 += data;
//                            final Message msg = new Message();
//                            b1 = b1.replaceAll(" ", "");
//                            msg.what = 1;
//                            handler.sendMessage(msg);
//                        }
                    }
                }
            };
            blueToothThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void ProgressUpdate(String... values) {
        //handler处理msg;
        b1 += values[0];
        final Message msg = new Message();
        b1 = b1.replaceAll(" ", "");
        msg.what = 1;
        handler.sendMessage(msg);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        DumpMsg("Enter onDestroy");
        if(mSerialMulti!=null) {
            for(int i=0;i<MAX_DEVICE_COUNT;i++) {
                gThreadStop[i] = true;
            }//First to stop app view-thread
            if(iDeviceCount>0)
                unregisterReceiver(PLMultiLibReceiver);
            mSerialMulti.PL2303Release();
            mSerialMulti = null;
        }
        super.onDestroy();
        DumpMsg("Leave onDestroy");
        try{
            unregisterReceiver(mReceiver);
        }catch (Exception e){

        }
        key = false;
    }

    private final BroadcastReceiver PLMultiLibReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(mSerialMulti.PLUART_MESSAGE)){
                Bundle extras = intent.getExtras();
                if(extras!=null) {
                    String str = (String)extras.get(mSerialMulti.PLUART_DETACHED);
                    DumpMsg("receive data:"+str);
                    int index = Integer.valueOf(str);
                    if(DeviceIndex1==index) {
                        bDeviceOpened[DeviceIndex1] = false;

                    } else if(DeviceIndex2==index) {

                        bDeviceOpened[DeviceIndex2] = false;
                    } else if(DeviceIndex3==index) {

                        bDeviceOpened[DeviceIndex3] = false;
                    }
                }
            }
        }//onReceive
    };

    private static void DumpMsg(Object s) {
        if(true==bDebugMesg) {
            Log.d("PL2303MultiUSBApp", ">==< " + s.toString() + " >==<");
        }
    }

    private void OpenUARTDevice(int index) {
        DumpMsg("Enter OpenUARTDevice");

        if(mSerialMulti==null)
            return;

        if(!mSerialMulti.PL2303IsDeviceConnectedByIndex(index))
            return;

        boolean res;
        UARTSettingInfo info = gUARTInfoList[index];
        res = mSerialMulti.PL2303OpenDevByUARTSetting(index, info.mBaudrate, info.mDataBits, info.mStopBits,
                info.mParity, info.mFlowControl);
        if( !res ) {
            DumpMsg("fail to setup");
            Toast.makeText(this, "Can't set UART correctly!", Toast.LENGTH_SHORT).show();
            return;
        }
        bDeviceOpened[index] = true;

        if(!gRunningReadThread[index]) {
            UpdateDisplayView(index);
        }

        DumpMsg("Leave OpenUARTDevice");
        Toast.makeText(this, "Open ["+ mSerialMulti.PL2303getDevicePathByIndex(index) +"] successfully!", Toast.LENGTH_SHORT).show();
        return;
    }//private void OpenUARTDevice(int index)

    private void UpdateDisplayView(int index) {
        gThreadStop[index] = false;
        gRunningReadThread[index] = true;

        if( DeviceIndex1==index ) {
            new Thread(ReadLoop1).start();
        }
    }
    private int ReadLen1;
    private byte[] ReadBuf1 = new byte[4096];
    Handler mHandler2 = new Handler();
    private Runnable ReadLoop1 = new Runnable() {
        public void run() {

            for (;;) {
                ReadLen1 = mSerialMulti.PL2303Read(DeviceIndex1, ReadBuf1);
                if (ReadLen1 > 0) {
                    //ReadBuf1[ReadLen1] = 0;
                    DumpMsg("Read  Length : " + ReadLen1);
                    b1=HexUtils.bytesToHexString(ReadBuf1,ReadLen1);
                    b1 = b1.replaceAll(" ", "");
                    DumpMsg(b1);
                    handler.sendEmptyMessage(1);
                }//if (len > 0)

                DelayTime(60);

                if (gThreadStop[DeviceIndex1]) {
                    gRunningReadThread[DeviceIndex1] = false;
                    return;
                }//if
            }//for(...)

        }//run
    };//Runnable
    private void DelayTime(int dwTimeMS) {
        //Thread.yield();
        long StartTime, CheckTime;

        if(0==dwTimeMS) {
            Thread.yield();
            return;
        }
        //Returns milliseconds running in the current thread
        StartTime = System.currentTimeMillis();
        do {
            CheckTime=System.currentTimeMillis();
            Thread.yield();
        } while( (CheckTime-StartTime)<=dwTimeMS);
    }
}

class UARTSettingInfo {
    public int iPortIndex = 0;
    public PL2303MultiLib.BaudRate mBaudrate = PL2303MultiLib.BaudRate.B115200;
    public PL2303MultiLib.DataBits mDataBits = PL2303MultiLib.DataBits.D8;
    public PL2303MultiLib.Parity mParity = PL2303MultiLib.Parity.NONE;
    public PL2303MultiLib.StopBits mStopBits = PL2303MultiLib.StopBits.S1;
    public PL2303MultiLib.FlowControl mFlowControl = PL2303MultiLib.FlowControl.OFF;
}//class UARTSettingInfo

