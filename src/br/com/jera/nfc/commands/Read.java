package br.com.jera.nfc.commands;

public class Read extends AbstractCommand implements Command {

	public Read(short blockAddr) {
		setHeader(new short[]{ 0x06, 0x00, 0x46, 0x10, blockAddr });
	}
}
