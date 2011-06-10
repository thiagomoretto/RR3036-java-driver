package br.com.jera.nfc.commands;

public class AntiColl extends AbstractCommand implements Command {

	public AntiColl() {
		setHeader(new short[]{ 0x06, 0x00, 0x42, 0x10, 0x00 });
	}

	@Override
	public boolean wasError() {
		if(getResultLength() == 0x08) {
			return false;
		}
		return true;
	}
}
