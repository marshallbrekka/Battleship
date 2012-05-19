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
	
	
	
	private Reciever reciever;
	public Channel(Socket socket, Controller main) {
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			reciever = new Reciever(socket, main);
			new Thread(reciever).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void stop() {
		reciever.stop();
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
		private Socket socket;
		
		public Reciever(Socket socket, Controller main) throws IOException {
			this.socket = socket;
			this.main = main;
			in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
		}
		
		public void stop() {
			active = false;
			try {
				socket.close();
			} catch (IOException e) {
				System.out.println("exception in Client.stop");
				e.printStackTrace();
			}
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
						main.exitGameCallback();
					} else if(code == SocketLanguage.MESSAGE_CODES.FIRE) {
						ShotLocation shot = (ShotLocation) in.readObject();
						main.recieveShotCallback(shot);
					} else if(code == SocketLanguage.MESSAGE_CODES.FLEET) {
						Fleet fleet = (Fleet) in.readObject();
						main.recieveFleetCallback(fleet);
					} else if(code == SocketLanguage.MESSAGE_CODES.GAME_JOINED) {
						main.gameJoinedCallback();
					} else if(code == SocketLanguage.MESSAGE_CODES.GAME_REJECTED) {
						main.gameRejectedCallback();
					} else if(code == SocketLanguage.MESSAGE_CODES.JOIN_GAME) {
						
						String name = (String) in.readObject();
						main.joinGameCallback(name);
					} else if(code == SocketLanguage.MESSAGE_CODES.PLAY_AGAIN) {
						main.playAgainCallback();
					}
	
				} catch (IOException e) {
					System.out.println("caught that fucker");
					// TODO Auto-generated catch block
					e.printStackTrace();
					stop();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("caught that fucker");
				}
			}
			
		}
		
	}
}
