import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class MotoDrive {
	SerialPort serialPort ;
	private JFrame frame;
	boolean up  = false, down  = false, left  = false, right = false;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MotoDrive window = new MotoDrive();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		final JSlider speed = new JSlider();
		speed.setEnabled(false);
		speed.setOrientation(SwingConstants.VERTICAL);
		speed.setBounds(135, 6, 36, 266);
		speed.setMinimum(-255); speed.setMaximum(255);
		speed.setValue(0);
		speed.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					int speedVal = speed.getValue();
					if (speed.getValue()>0){
						serialPort.writeByte(f);
					} else {
						serialPort.writeByte(b);
					}
					serialPort.writeString(String.valueOf(Math.abs(speedVal)));
//					System.out.println(speedVal);
				} catch (SerialPortException e1) {
//					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(speed);
		
		final JSlider rotation = new JSlider();
		rotation.setEnabled(false);
		rotation.setBounds(158, 126, 286, 29);
		rotation.setMinimum(-255); rotation.setMaximum(255);
		rotation.setValue(0);
		rotation.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					int speedVal = rotation.getValue();
					if (rotation.getValue()>0){
						serialPort.writeByte(r);
					} else {
						serialPort.writeByte(l);
					}
					serialPort.writeString(String.valueOf(Math.abs(speedVal)));
//					System.out.println(speedVal);
				} catch (SerialPortException e1) {
//					e1.printStackTrace();
				}
			}
		});

		frame.getContentPane().add(rotation);
		
		final JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serialPort = new SerialPort("/dev/tty.usbmodem1411");
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
				    speed.setEnabled(true);
				    rotation.setEnabled(true);
				}
				catch (SerialPortException ex) {
				    System.out.println("There are an error on writing string to port т: " + ex);
				}
				btnConnect.setEnabled(false);
			}
		});
		btnConnect.setBounds(6, 22, 117, 29);
		frame.getContentPane().add(btnConnect);
		
		JButton btnNavigation = new JButton("Navigation");
		btnNavigation.setFocusable(true);
		btnNavigation.setBounds(222, 22, 117, 29);
		frame.getContentPane().add(btnNavigation);
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					serialPort.writeByte(s);
				} catch (SerialPortException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnStop.setBounds(6, 92, 58, 29);
		frame.getContentPane().add(btnStop);
		
		btnNavigation.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			        	if(!up){
			        		up = true;
				        	System.out.println("Up Pres");
				        	break;
			        	}
			        case KeyEvent.VK_DOWN:
			        	if(!down){
			        		down = true;
				        	System.out.println("Down Pres");
				        	break;
			        	}
			        case KeyEvent.VK_LEFT:
			        	if(!left){
			        		left = true;
				        	System.out.println("Left Pres");
				        	break;
			        	}
			        case KeyEvent.VK_RIGHT :
			        	if(!right){
			        		right = true;
				        	System.out.println("Right Pres");
				        	break;
			        	}
			    }
			}

			@Override
			public void keyReleased(KeyEvent e) {
				int keyCode = e.getKeyCode();
			    switch( keyCode ) { 
			        case KeyEvent.VK_UP:
			        	if(up){
			        		up = false;
				        	System.out.println("Up Rels");
				        	break;
			        	}
			        case KeyEvent.VK_DOWN:
			        	if(down){
			        		down = false;
				        	System.out.println("Down Rels");
				        	break;
			        	}
			        case KeyEvent.VK_LEFT:
			        	if(left){
			        		left = false;
				        	System.out.println("Left Rels");
				        	break;
			        	}
			        case KeyEvent.VK_RIGHT :
			        	if(right){
			        		right = false;
				        	System.out.println("Right Rels");
				        	break;
			        	}
			    }
			}
		});
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

