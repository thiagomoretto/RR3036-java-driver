package br.com.jera.nfc.commands;

public class Authenticate extends AbstractCommand implements Command {

	public Authenticate(short sectorAddr) {
		setHeader(new short[]{ 0x07, 0x00, 0x44, 0x10, 0x00, sectorAddr });
	}
}
