import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class PySocket {
	public static void main (String[] args) {
		Socket client = null;
		try {
			client = new Socket("localhost",9999);
			DataInputStream input = new DataInputStream(client.getInputStream());
			DataOutputStream output = new DataOutputStream(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while(true){
				System.out.print("Message :");
				String mes = in.readLine();
				output.writeUTF(mes);
				output.flush();
				String rec = input.readUTF();
				System.out.println("Server s: "+rec);				
			}
		} catch (IOException e) {
			e.printStackTrace();
			try {
				client.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
