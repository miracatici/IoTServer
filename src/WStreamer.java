
import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;


public class WStreamer  {
	public static void main (String[] args) throws InterruptedException{
		try {
			ServerSocket server = new ServerSocket(5758);
			Webcam webcam = Webcam.getDefault();
			webcam.setViewSize(new Dimension(320,240));
			Socket ss = null;
			System.out.println("Server ...");
			ss = server.accept();
			System.out.println("Client connected");
			BufferedOutputStream output = new BufferedOutputStream(ss.getOutputStream());
			webcam.open();
			
			while (true){
				ImageIO.write(webcam.getImage(), "jpg", output);
				webcam.getImage();
				Thread.sleep(250);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
