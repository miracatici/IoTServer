import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ClientGUI {
	private Socket s;
	private static JTextPane chatPane; 
	private SmartSocket smartSocket;
	private JFrame frame;
	private JTextField txtName;
	private JTextField txtTarget;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI window = new ClientGUI();
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
	public ClientGUI() {
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
		
		JLabel lblDevice = new JLabel("Your Device Name");
		lblDevice.setBounds(6, 11, 117, 16);
		frame.getContentPane().add(lblDevice);
		
		txtName = new JTextField();
		txtName.setBounds(135, 6, 130, 26);
		frame.getContentPane().add(txtName);
		txtName.setColumns(10);
		
		final JTextArea txtMessage = new JTextArea();
		txtMessage.setEnabled(false);
		txtMessage.setText("Message");
		txtMessage.setToolTipText("Type your message");
		txtMessage.setBounds(112, 63, 164, 209);
		frame.getContentPane().add(txtMessage);
	
		chatPane = new JTextPane();
		chatPane.setEditable(false);
		chatPane.setBounds(280, 63, 164, 209);
		frame.getContentPane().add(chatPane);
		
		final JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smartSocket.sendMessage(txtTarget.getText(), txtMessage.getText());
				setChat(txtMessage.getText());
			}
		});
		btnSend.setEnabled(false);
		btnSend.setBounds(6, 114, 71, 29);
		frame.getContentPane().add(btnSend);
		
		final Thread messageThread = new Thread (new Runnable(){
			@Override
			public void run() {
				while(true){
					try {
						setChat(smartSocket.getMessage());
					} catch (IOException e) {
						setChat("Error occurs");
						e.printStackTrace();
					}
		
				}
			}	
		});
		final JButton btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(smartSocket !=null){
					try {
						messageThread.interrupt();
						smartSocket.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnStop.setBounds(296, 34, 71, 29);
		frame.getContentPane().add(btnStop);
		final JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(268, 6, 117, 29);
		
		JLabel lblTarget = new JLabel("Target Device Name");
		lblTarget.setBounds(6, 39, 130, 16);
		frame.getContentPane().add(lblTarget);
		
		txtTarget = new JTextField();
		txtTarget.setBounds(135, 33, 130, 26);
		frame.getContentPane().add(txtTarget);
		txtTarget.setColumns(10);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnStop.doClick();
				System.exit(0);
			}
		});
		btnExit.setBounds(385, 6, 65, 29);
		frame.getContentPane().add(btnExit);
		try {
			s  = new Socket("178.62.193.160", 5758);
		} catch (UnknownHostException e2) {
			JOptionPane.showMessageDialog(null,"Host is unknown","Error", JOptionPane.WARNING_MESSAGE);
			e2.printStackTrace();
		} catch (IOException e2) {
			JOptionPane.showMessageDialog(null,"Connection cannot established","Error", JOptionPane.WARNING_MESSAGE);
			e2.printStackTrace();
		}
		btnConnect.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				setChat("Connection is done");
				smartSocket = new SmartSocket(s,txtName.getText());
				messageThread.start();
				btnConnect.setEnabled(false);
				txtMessage.setEnabled(true);
				btnSend.setEnabled(true);
				btnStop.setEnabled(true);
			}			
		});
		frame.getContentPane().add(btnConnect);
	}
	public static void setChat(String mes){
		String lines = chatPane.getText();
		lines+="\n"+mes;
		chatPane.setText(lines);
	}
}
