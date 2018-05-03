package com.keymantek.serialport.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import com.keymantek.serialport.utils.SerialPort;



public class SerialPortOpera {
    private SerialPort mSerialPort = null;
   	protected OutputStream mOutputStream;
   	protected InputStream mInputStream;

    public void openSerialPort(String uart_port_path,int baudrate,int nBits, char nEvent, int nStop) throws SecurityException, IOException, InvalidParameterException {
            if (mSerialPort == null) {
                    /* Check parameters */
                    if ( (uart_port_path.length() == 0) || (baudrate == -1)) {
                            throw new InvalidParameterException();
                    }
                    /* Open the serial port */
                    mSerialPort = new SerialPort(new File(uart_port_path), baudrate, nBits,nEvent,nStop);
                    mOutputStream = mSerialPort.getOutputStream();
        			mInputStream = mSerialPort.getInputStream();
            }
    }

    public void closeSerialPort() {
    	 	/*if (mReadThread != null)
 			mReadThread.interrupt();*/
            if (mSerialPort != null) {
                    mSerialPort.closeSerialport();
                    mSerialPort = null;
            }
    		mSerialPort = null;
    }
    public InputStream SerialPortReceiver() {
    	//String data=new String(buffer, 0, size);
    	if(mInputStream==null){
    		if(mSerialPort==null){
    			return null;
    		}
    		return mSerialPort.getInputStream();
    	}
    	return mInputStream;
    }
    public void SerialPortWrite(byte[] text) {
    	//int i;
       /* CharSequence t = data;
        char[] text = new char[t.length()];*/
        /*for (i=0; i<t.length(); i++) {
                text[i] = t.charAt(i);
        }*/
    	
        try {
                mOutputStream.write(text);
                //mOutputStream.write('\n');
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
   
}
