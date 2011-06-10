package br.com.jera.nfc.commands;

public class Write extends AbstractCommand implements Command {

	public Write(short blockAddr, short[] data) {
		short[] hdr  = new short[] { 0x16, 0x00, 0x47, 0x10, blockAddr };
		short[] full = new short[hdr.length + 16];
		
		for(int i=0;i<hdr.length;i++)
			full[i] = hdr[i];
		
		for(int i=0;i<data.length;i++)
			full[i+5] = data[i];
		
		setHeader(full);
	}
}
