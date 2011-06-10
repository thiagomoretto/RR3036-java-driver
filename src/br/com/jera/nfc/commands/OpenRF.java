package br.com.jera.nfc.commands;

public class OpenRF extends AbstractCommand implements Command {

	short[] HEADER = new short[] { 0x05, 0x00, 0x00, 0x02 };
	
	public OpenRF() {
		setHeader(HEADER);
	}
}
