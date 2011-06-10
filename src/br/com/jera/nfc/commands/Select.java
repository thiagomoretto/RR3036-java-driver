package br.com.jera.nfc.commands;

public class Select extends AbstractCommand implements Command {

	public Select(byte[] UIDCard) {
		setHeader(new short[]{ 0x09, 0x00, 0x43, 0x10, UIDCard[0], UIDCard[1], UIDCard[2], UIDCard[3] });
		// setData(new byte[]{ UIDCard[0], UIDCard[1], UIDCard[2], UIDCard[3] });
	}
}