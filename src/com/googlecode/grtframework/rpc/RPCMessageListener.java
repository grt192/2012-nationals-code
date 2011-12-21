package com.googlecode.grtframework.rpc;

import com.grt192.core.EventListener;


/**
 * 
 * @author ajc
 * 
 */
public interface RPCMessageListener extends EventListener {

	public void messageReceived(RPCMessage message);
}
