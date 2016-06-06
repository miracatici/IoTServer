import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class WClient {
	public static void main (String[] arsg){
		JFrame panel = new JFrame();
		JLabel label = new JLabel();
		label.setSize(new Dimension(500,500));
		panel.setSize(new Dimension(500,500));
		panel.add(label);
		panel.setVisible(true);
		Socket ss =  null;
		BufferedInputStream input = null;
		try {
			ss  = new Socket("192.168.1.5", 5758);
			input = new BufferedInputStream(ss.getInputStream());
			while(true){
				BufferedImage img = ImageIO.read(input);
				if(img !=null){
					label.setIcon(new ImageIcon(img));
					label.repaint();
//				} else {
//					System.out.println("Image null");
				}
//				Thread.sleep(50);	
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ImageReadException e) {
//			e.printStackTrace();
		} 
	}
}
