package org.winplus.serial;

import org.winplus.serial.utils.SerialPort;
import org.winplus.serial.utils.SerialPortFinder;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;

public class Application extends android.app.Application {
	public SerialPortFinder mSerialPortFinder = new SerialPortFinder();
    private SerialPort mSerialPort = null;

    public SerialPort getSerialPort() throws SecurityException, IOException, InvalidParameterException {
            if (mSerialPort == null) {

                    /* Open the serial port */
                    mSerialPort = new SerialPort(new File("dev/ttyS7"), 9600, 1);
            }
            return mSerialPort;
    }

    public void closeSerialPort() {
            if (mSerialPort != null) {
                    mSerialPort.close();
                    mSerialPort = null;
            }
    }
}
