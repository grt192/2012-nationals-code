package rpc;

public class RPCMessage {

	private final int key;
	private final String data;

	public RPCMessage(int key, double data) {
		this.key = key;
		this.data = "" + data;
	}

        public RPCMessage(int key, String data){
            this.key = key;
            this.data = data;
        }
        
	public int getKey() {
		return key;
	}

	public String getData() {
		return data;
	}

	public String toString() {
		return "RPCMessage:" + key + ":" + data;
	}

}
