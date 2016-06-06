import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class SerialComm   {
	public static void main (String[] args){
		BufferedReader user = new BufferedReader(new InputStreamReader(System.in));
		final SerialPort serialPort ;
		serialPort = new SerialPort("/dev/tty.ACM0");
		try {
		    serialPort.openPort();

		    serialPort.setParams(SerialPort.BAUDRATE_9600,
		                         SerialPort.DATABITS_8,
		                         SerialPort.STOPBITS_1,
		                         SerialPort.PARITY_NONE);

		    serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | 
		                                  SerialPort.FLOWCONTROL_RTSCTS_OUT);

		    serialPort.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent event) {
					if (event.getEventType()  == 1){
						String ret;
						try {
							ret = serialPort.readString();
							System.out.println(ret);
						} catch (SerialPortException e) {
							e.printStackTrace();
						}
					} else {
						System.out.println(event.getEventType() + " "+ event.getPortName());
					}
				}
			}, SerialPort.MASK_RXCHAR);

		}
		catch (SerialPortException ex) {
//			ex.printStackTrace();
		    System.out.println("There are an error on writing string to port т: " + ex);
		}
		try {
			while(true){
				System.out.print("User input: ");
				String mes = user.readLine();
				serialPort.writeString(mes);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
