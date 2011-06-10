package br.com.jera.nfc.utils;

public class CRC 
{
	public static long calc16(short[] data) {
		final int POLYNOMIAL   = 0x8408; // x^16 + x^12 + x^5 + 1
		final int PRESET_VALUE = 0xFFFF;

		int current_crc_value = PRESET_VALUE;
		for (int i = 0; i < data.length; i++) {
			current_crc_value = current_crc_value ^ (data[i] & 0xFF);
			for (int j = 0; j < 8; j++) {
				if ((current_crc_value & 0x0001)  != 0) {
					current_crc_value = (current_crc_value >>> 1) ^ POLYNOMIAL;
				} else {
					current_crc_value = (current_crc_value >>> 1);
				}
			}
		}
		return current_crc_value;
	}
}
