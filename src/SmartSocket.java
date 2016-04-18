import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SmartSocket {
	private Socket socket;
	private String name;
	private PrintWriter out;
	private BufferedReader in;
	
	public SmartSocket(Socket s,String name){
		socket = s;
		try {
			this.name = name;
			out = new PrintWriter(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.out.println("I/O cannot be initialized");
			e.printStackTrace();
		}
		sendMessage(name);
	}
	public Socket getSocket(){
		return socket;
	}
	public String getName(){
		return name;
	}
	public void sendMessage(String targetDevice, String message){
		out.println(targetDevice + "\t" + message);
		out.flush();
	}
	public void sendMessage(String message){
		out.println(message);
		out.flush();
	}
	public String getMessage() throws IOException {
		return in.readLine();
	}
	public BufferedReader getInput(){
		return in;
	}
	public PrintWriter getOutput(){
		return out;
	}
	public void close() throws IOException{
		socket.close();
	}
}
