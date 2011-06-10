package br.com.jera.nfc.commands;

public class ChangeToISO14443A extends AbstractCommand implements Command {

	public ChangeToISO14443A() {
		setHeader(new short[]{ 0x05, 0x00, 0x00, 0x05 });
	}
}
