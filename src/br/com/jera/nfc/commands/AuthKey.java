package br.com.jera.nfc.commands;

public class AuthKey extends AbstractCommand implements Command {

	public AuthKey(short sectorAddr) {
		setHeader(new short[] { 0x0D, 0x00, 0x73, 0x10, 0x00, sectorAddr, 
								0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF });
	}
}
