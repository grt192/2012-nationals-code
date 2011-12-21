package com.grt192.networking;

public interface SocketListener {
	
	public void onConnect(SocketEvent e);
	public void onDisconnect(SocketEvent e);
	public void dataRecieved(SocketEvent e);
}
