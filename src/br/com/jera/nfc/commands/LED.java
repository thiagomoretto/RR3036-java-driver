package br.com.jera.nfc.commands;

public class LED extends AbstractCommand implements Command {

	public static final int ON  = 0x00;
	public static final int OFF = 0x01;

	public LED(int mode) {
		if(mode == LED.ON) {
			setHeader(new short[] { 0x08, 0x00, 0x00, 0x07, 0x05, 0x00, 0x05 });
		} else {
			setHeader(new short[] { 0x08, 0x00, 0x00, 0x07, 0x00, 0x05, 0x05 });			
		}
	}
}
