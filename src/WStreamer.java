import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.output.CountingOutputStream;
import org.apache.sanselan.ImageFormat;
import org.apache.sanselan.ImageWriteException;
import org.apache.sanselan.Sanselan;

import com.github.sarxos.webcam.Webcam;

public class WStreamer  {
	public static void main (String[] args) {
		ServerSocket server = null;
		Webcam webcam = null;
		Socket ss = null;
		CountingOutputStream output = null;
		BufferedImage img = null;

		try {
			server = new ServerSocket(5758);
			webcam = Webcam.getDefault();
			webcam.setViewSize(new Dimension(320,240));
			System.out.println("Server ...");
			ss = server.accept();
			System.out.println("Client connected");
			output = new CountingOutputStream(ss.getOutputStream());
			webcam.open();
			while (true){
				img = webcam.getImage();
				byte[] b = Sanselan.writeImageToBytes(img, ImageFormat.IMAGE_FORMAT_GIF, null);
				output.write(b);
				output.flush();
				System.out.format("Transfered: %f Megabyte%n",output.getByteCount()/1000000f);
				Thread.sleep(250);
			} 
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ImageWriteException e) {
			e.printStackTrace();
		} finally {
			try {
				webcam.close();
				output.close();
				ss.close();
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
