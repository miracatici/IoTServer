import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;

public class SmartServer {
	
	public static TreeMap<String,SmartSocket> deviceList;
	
	public static void main (String[] arg) {
		deviceList = new TreeMap<String,SmartSocket>();
		ServerSocket server = null;
		try {
			server = new ServerSocket(5758);
			System.out.println("Server is established");
			while(true){
				Socket s = server.accept();
				System.out.println("accepted");
				BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String name = in.readLine();
				System.out.println("name is "+ name);
				SmartSocket device = new SmartSocket(s,name);
				deviceList.put(name, device);
				System.out.println(name +" is connected");
				new SmartServerThread(device);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
