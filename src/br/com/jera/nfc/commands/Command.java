package br.com.jera.nfc.commands;

public interface Command {

	public byte[] pack();
	
	public void setResult(int resultLength, byte[] result);
	
	public byte[] getResultData();
	
	public boolean wasError();
	
}
