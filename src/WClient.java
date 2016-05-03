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
		panel.setSize(new Dimension(320,240));
		panel.setVisible(true);
		Socket ss =  null;
		BufferedInputStream input = null;
		try {
			ss  = new Socket("localhost", 5758);
			input = new BufferedInputStream(ss.getInputStream());
			while(true){
				BufferedImage img = ImageIO.read(input);
				if (img != null){
					panel.getContentPane().add(new JLabel(new ImageIcon(img)));
					panel.pack();
					panel.repaint();
					System.out.println("refresh");
				}
//				Thread.sleep(100);
			}
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} 

	}
}
