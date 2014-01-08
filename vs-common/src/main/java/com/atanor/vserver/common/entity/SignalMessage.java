package com.atanor.vserver.common.entity;

public class SignalMessage {
	
	private Signal signal;
	private String[] params;
	
	public SignalMessage(){}

	public SignalMessage(final Signal signal, final String ... params){
		this.signal = signal;
		this.params = params;
	}
	
	public Signal getSignal() {
		return signal;
	}

	public void setSignal(final Signal signal) {
		this.signal = signal;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(final String[] params) {
		this.params = params;
	}
	
}
