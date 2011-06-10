package br.com.jera.nfc.commands;

public class AntiColl2 extends AbstractCommand implements Command {

	public AntiColl2() {
		setHeader(new short[] { 0x07, 0x00, 0x71, 0x10, 0x00, 0x00 });
	}
}
