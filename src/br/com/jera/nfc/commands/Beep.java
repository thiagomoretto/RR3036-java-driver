package br.com.jera.nfc.commands;

public class Beep extends AbstractCommand implements Command {

	public Beep() {
		setHeader(new short[] { 0x08, 0x00, 0x00, 0x08, 0x05, 0x05, 0x05 });
	}
}
