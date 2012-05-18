package networking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.Fleet;
import model.ShotLocation;
import controller.Controller;

public class Channel {
	private ObjectOutputStream out;
	private Socket socket;
	
	
	private Reciever reciever;
	public Channel(Socket socket, Controller main) {
		this.socket = socket;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			reciever = new Reciever(socket, main);
			new Thread(reciever).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendFleet(Fleet fleet) {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.FLEET));
			out.writeObject(fleet);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendShot(ShotLocation shot) {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.FIRE));
			out.writeObject(shot);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void joinGame(String name) {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.JOIN_GAME));
			out.writeObject(name);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exitGame() {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.EXIT_GAME));
			out.flush();
			reciever.stop();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void playAgain() {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.PLAY_AGAIN));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameJoined() {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.GAME_JOINED));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void gameRejected() {
		try {
			out.writeInt(SocketLanguage.createCode(SocketLanguage.MESSAGE_CODES.GAME_REJECTED));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private class Reciever implements Runnable {
		private ObjectInputStream in; 
		private Controller main;
		private boolean active = true;
		
		public Reciever(Socket socket, Controller main) throws IOException {
			this.main = main;
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		
		public void stop() {
			active = false;
		}
		

		@Override
		public void run() {
			int bytes;
			while(active) {
				
				try {
					bytes = in.available();
					if(bytes == 0) continue;
					SocketLanguage.MESSAGE_CODES code = SocketLanguage.getCode(in.readInt());
					
					if(code == SocketLanguage.MESSAGE_CODES.EXIT_GAME) {
						active = false;
						main.exitGame();
					} else if(code == SocketLanguage.MESSAGE_CODES.FIRE) {
						ShotLocation shot = (ShotLocation) in.readObject();
						main.recieveShot(shot);
					} else if(code == SocketLanguage.MESSAGE_CODES.FLEET) {
						Fleet fleet = (Fleet) in.readObject();
						main.recieveFleet(fleet);
					} else if(code == SocketLanguage.MESSAGE_CODES.GAME_JOINED) {
						main.gameJoined();
					} else if(code == SocketLanguage.MESSAGE_CODES.GAME_REJECTED) {
						main.gameRejected();
					} else if(code == SocketLanguage.MESSAGE_CODES.JOIN_GAME) {
						
						String name = (String) in.readObject();
						main.joinGame(name);
					} else if(code == SocketLanguage.MESSAGE_CODES.PLAY_AGAIN) {
						main.playAgain();
					}
	
				} catch (IOException e) {
					System.out.println("caught that fucker");
					// TODO Auto-generated catch block
					e.printStackTrace();
					active = false;
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("caught that fucker");
				}
			}
			
		}
		
	}
}
