package br.com.jera.nfc;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import br.com.jera.nfc.commands.AntiColl;
import br.com.jera.nfc.commands.AuthKey;
import br.com.jera.nfc.commands.Authenticate;
import br.com.jera.nfc.commands.ChangeToISO14443A;
import br.com.jera.nfc.commands.Command;
import br.com.jera.nfc.commands.LED;
import br.com.jera.nfc.commands.OpenRF;
import br.com.jera.nfc.commands.Read;
import br.com.jera.nfc.commands.Request;
import br.com.jera.nfc.commands.RequestInfo;
import br.com.jera.nfc.commands.Select;
import br.com.jera.nfc.commands.Write;

public class TestNFC implements Runnable, SerialPortEventListener {
	private static final int MAX_WAIT_FOR_NEXT_READ = 1000;
	static CommPortIdentifier portId;
	@SuppressWarnings("rawtypes")
	static Enumeration portList;
	InputStream inputStream;
	OutputStream outputStream;
	SerialPort serialPort;
	Thread readThread;
	
	String currentlyCardUID = null;
	private long waitTime = 0;
	
	public TestNFC() 
	{
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) 
			{
				if (portId.getName().equals("/dev/tty.SLAB_USBtoUART")) 
				{
					try {
						serialPort   = (SerialPort) portId.open("TestNFCApp", 2000);
						serialPort.setSerialPortParams(19200,
								SerialPort.DATABITS_8, 
								SerialPort.STOPBITS_1,
								SerialPort.PARITY_NONE);
						
						serialPort.enableReceiveTimeout(1000);
						
						inputStream  = serialPort.getInputStream();
						outputStream = serialPort.getOutputStream();
						
						executeAndRead(new RequestInfo());
						executeAndRead(new ChangeToISO14443A());
						executeAndRead(new OpenRF());
						
						do {
							Request request = new Request();
							executeAndRead(request, true);
							
							if(!request.wasError()) 
							{
								AntiColl anticoll = new AntiColl();
								executeAndRead(anticoll, true);
								
								int attempt = 0;
								if(!anticoll.wasError())
								{
									byte[] cardUID = anticoll.getResultData();
									if(!getHexString(cardUID).equals(currentlyCardUID) 
											|| (System.currentTimeMillis() - waitTime ) > MAX_WAIT_FOR_NEXT_READ) 
									{
										currentlyCardUID = getHexString(cardUID);
										System.out.println("Detected CARD's UID = " + currentlyCardUID);
										
										short sectorAddr = 0x00;
										short blockAddr  = 0x02;
										
										executeAndRead(new Select(cardUID));
										executeAndRead(new AuthKey(sectorAddr));
										executeAndRead(new Authenticate(sectorAddr));
										executeAndRead(new Read(blockAddr));
										executeAndRead(new Write(blockAddr,
												new short[] { 
														0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
														0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x81 }));

										// executeAndRead(new Beep());
										executeAndRead(new LED(LED.ON));
										executeAndRead(new LED(LED.OFF));
										
										attempt ++;
										waitTime = System.currentTimeMillis();
									}
								}
							}
						} while(true);
						
					} catch (Exception e) {
						e.printStackTrace();
					}  
				}
			}
		}
	}
	
	public void executeAndRead(Command command) throws Exception 
	{
		executeAndRead(command, false);
	}
	
	public void executeAndRead(Command command, boolean quiet) throws Exception 
	{
		byte[] msglen = new byte[1];
		if(!quiet)
			System.out.println("Request = " + getHexString(command.pack()));		
		
		outputStream.write(command.pack());
		outputStream.flush();
		if(!quiet)
			System.out.println("Reading "+ command.getClass().getName() + "...");
		inputStream.read(msglen);
		
		int len = msglen[0];
		byte[] result = new byte[len];
		
		inputStream.read(result);
		if(!quiet)
			System.out.println("Len = " + len + " Result = " + getHexString(result));
		
		command.setResult(len, result);
	}
	
	@Override
	public void serialEvent(SerialPortEvent event) { }

	@Override
	public void run() {}
	
	public static void main(String[] args) {
		new TestNFC();
	}
	
	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

}
