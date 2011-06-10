package br.com.jera.nfc.commands;


public class RequestInfo extends AbstractCommand implements Command
{
	static short[] HEADER = new short[] { 0x05, 0x00, 0x00, 0x00 };
	
	public RequestInfo() {
		setHeader(HEADER);
	}
}
