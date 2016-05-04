import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;

public class WClient {
	public static void main (String[] arsg){
		JFrame panel = new JFrame();
		JLabel label = new JLabel();
		label.setSize(new Dimension(320,240));
		panel.setSize(new Dimension(320,240));
		panel.add(label);
		panel.setVisible(true);
		Socket ss =  null;
		BufferedInputStream input = null;
		try {
			ss  = new Socket("localhost", 5758);
			input = new BufferedInputStream(ss.getInputStream());
			while(true){
				BufferedImage img = Sanselan.getBufferedImage(input);
				if(img !=null){
					label.setIcon(new ImageIcon(img));
					label.repaint();
				}
				Thread.sleep(160);	
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ImageReadException e) {
			e.printStackTrace();
		} 
	}
}
