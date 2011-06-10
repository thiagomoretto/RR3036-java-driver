package br.com.jera.nfc.commands;

public class ChangeToISO15693 extends AbstractCommand implements Command {

	public ChangeToISO15693() {
		setHeader(new short[]{ 0x05, 0x00, 0x00, 0x06 });
	}
}
