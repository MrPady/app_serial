package com.example.admin.infraredcertification;

import android.os.AsyncTask;

import com.keymantek.serialport.utils.HexUtils;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by admin on 2016/4/20.
 */
public abstract class ReadThread extends AsyncTask<Void, String, String> {
    private InputStream mInputStream;

    public ReadThread(InputStream mInputStream) {
        this.mInputStream = mInputStream;
    }

    @Override
    protected String doInBackground(Void... params) {
        // TODO Auto-generated method stub
        while (!isCancelled()) {
            int count = 0;
            try {
                while (count == 0) {
                    count = mInputStream.available();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] buffer = new byte[4];
            int readCount = 0; // 已经成功读取的字节的个数
            boolean key=false;
            while (readCount < count) {
                try {
                    readCount += mInputStream.read(buffer, readCount, count - readCount);
                } catch (IOException e) {
                    break;
                }catch (ArrayIndexOutOfBoundsException e){
                   key=true;
                }
            }
            if(key){
                continue;
            }
            if (readCount > 0) {
                String data = HexUtils.bytesToHexString(buffer,
                        readCount);
                publishProgress(data);
            } else {
                publishProgress(new String(buffer, 0, readCount));
            }
        }
        return null;
    }

    protected abstract void onProgressUpdate(String... values);
}
