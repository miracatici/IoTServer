import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class StreamTo {
	public static void main (String[] args)  {
		Socket ss =  null;
		BufferedOutputStream output = null;
		try {
			ss  = new Socket("localhost", 5758);
			output = new BufferedOutputStream(ss.getOutputStream());
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		TargetDataLine recLine  = null;
		byte[] buffer = new byte[256];
		try {
			recLine = (TargetDataLine) AudioSystem.getLine(new DataLine.Info(TargetDataLine.class, new AudioFormat(44100f, 16, 1, true, false)));	
			recLine.open(new AudioFormat(44100f, 16, 1, true, false));
			recLine.start();
			while ((recLine.read(buffer, 0, 256)) > 0) {
				output.write(buffer);
			}
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
