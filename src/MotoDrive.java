import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class MotoDrive {
	SerialPort serialPort ;
	private JFrame frame;

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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		final JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serialPort = new SerialPort("/dev/tty.usbmodem1421");
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
				btnConnect.setEnabled(false);
			}
		});
		btnConnect.setBounds(6, 22, 117, 29);
		frame.getContentPane().add(btnConnect);
		
		final JSlider slider = new JSlider();
		slider.setBounds(6, 79, 374, 29);
		slider.setMinimum(0); slider.setMaximum(255);
		slider.setValue(0);
		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					serialPort.writeInt(slider.getValue());
				} catch (SerialPortException e1) {
//					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(slider);
	}
}
