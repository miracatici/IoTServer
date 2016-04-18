import java.io.IOException;

public class SmartServerThread extends Thread {
	private SmartSocket socket;
	
	public SmartServerThread(SmartSocket socket){
		this.socket = socket;
		start();
	}
	public void run(){
		while(true){ 
			try {
				String mes = socket.getMessage();
				String[] line = mes.split("\t");
				SmartServer.deviceList.get(line[0]).sendMessage(line[1]);
			} catch (IOException e) {
				System.out.println("Message cannot be received");
				try {
					socket.getSocket().close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
		}
	}
}
