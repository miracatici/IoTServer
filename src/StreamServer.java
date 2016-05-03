import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class StreamServer {
	public static void main (String[] arg) {
		try {
			ServerSocket server = new ServerSocket(5758);
			System.out.println("Server is established");
			Socket ss = server.accept();
			System.out.println("Client accepted");
			BufferedInputStream input = new BufferedInputStream(ss.getInputStream());
			SourceDataLine recLine  = null;
			byte[] buffer = new byte[256];
			try {
				recLine = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, new AudioFormat(44100f, 16, 1, true, false)));	
				recLine.open(new AudioFormat(44100f, 16, 1, true, false));
				recLine.start();
				while ((input.read(buffer, 0, 256)) > 0) {
					recLine.write(buffer,0,256);
				}
			} catch (LineUnavailableException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
