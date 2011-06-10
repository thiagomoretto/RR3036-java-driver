package br.com.jera.nfc.commands;

import br.com.jera.nfc.utils.CRC;

public class AbstractCommand implements Command {
	private short[] header = new short[]{};
	private byte[] data   = new byte[]{};
	
	private byte[] result = null;
	private int resultLength = -1;
	
	@Override
	public byte[] pack() {
		short[] n = new short[header.length];
		for(int i=0;i<n.length;i++) {
			n[i]= header[i];
		}
		long crc = CRC.calc16(n);
		byte[] output = new byte[header.length + data.length + 2];
		
		int i=0;
		for(; i<header.length; i++)  {
			output[i] = (byte) (header[i] & 0xFF);
		}
		/*int j = 0;
		for(; i<header.length + data.length; i++)  {
			output[i] = data[j++];
		}*/
		
		output[i++] = (byte) (crc & 0x00FF); 
		output[i]   = (byte) ((crc >> 8) & 0x00FF);
		return output;
	}

	public void setHeader(short[] header) {
		this.header = header;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	public void setResult(int resultLength, byte[] result) {
		this.resultLength = resultLength;
		this.result 	  = result;
	}
	
	public int getResultLength() {
		return resultLength;
	}
	
	public byte[] getResultData() {
		if(result != null) {
			byte[] data = new byte[result.length - 0x04];
			int i=2,j=0;
			for(;i<result.length - 2;i++) {
				data[j++] = result[i];
			}
			return data;
		} else {
			return new byte[] {};
		}
	}

	@Override
	public boolean wasError() {
		return true;
	}
}
