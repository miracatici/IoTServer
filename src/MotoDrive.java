import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class MotoDrive {
	BufferedReader input;
	SerialPort serialPort ;
	boolean up  = false, down  = false, left  = false, right = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotoDrive window = new MotoDrive();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MotoDrive() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final byte f = 'f';  final byte b = 'b';  final byte r = 'r';  final byte l = 'l'; final byte s = 's';
		serialPort = new SerialPort("/dev/ttyACM0"); ///dev/tty.usbmodem1a1241 /dev/cu.usbmodem1a1241
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
					if(event.getEventType() == 1){
						try {
							System.out.print(serialPort.readString());
						} catch (SerialPortException e) {
							e.printStackTrace();
						}
					}
//							System.out.println(event.getEventType() + " "+ event.getPortName());
				}
			}, SerialPort.MASK_RXCHAR);
		}
		catch (SerialPortException ex) {
			System.out.println("There are an error on writing string to port т: " + ex);
		}

		
		try {
			ServerSocket server = new ServerSocket(8890);
			System.out.println("Waiting");
			Socket socket = server.accept();
			System.out.println("Connected");
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			new Thread(new Runnable(){
				@Override
				public void run() {
					while(true){
						try {
							String mes = input.readLine();
							String[] line = mes.split("\t");
							switch (line[0]){
								case "f":
									serialPort.writeByte(f);
									serialPort.writeString(line[1]);
									break;
								case "b":
									serialPort.writeByte(b);
									serialPort.writeString(line[1]);
									break;
								case "s":
									serialPort.writeByte(s);
									break;
							}
						} catch (IOException | SerialPortException e) {
							e.printStackTrace();
							break;
						}
					}
				}
			}).start();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("sistem inital bitti");
	}

	public void forward(int a){
		if(a == 0){
			
		} else {
			
		}
	}
	public void backward(int a){
		if(a == 0){
			
		} else {
			
		}
	}
	public void left(int a){
		if(a == 0){
			
		} else {
			
		}	
	}
	public void right(int a){
		if(a == 0){
			
		} else {
			
		}
	}
}

