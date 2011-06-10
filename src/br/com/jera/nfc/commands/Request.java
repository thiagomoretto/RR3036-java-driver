package br.com.jera.nfc.commands;

public class Request extends AbstractCommand implements Command {

	public Request() {
		setHeader(new short[]{ 0x06, 0x00, 0x41, 0x10, 0x01 });
	}
	
	@Override
	public boolean wasError() {
		if(getResultLength() == 0x06) {
			return false;
		}
		return true;
	}
}
