package iofetch;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

class Connector {


	static boolean downloadStorico(String outputFileName){
		String url = LocalConfigurationManager.getConfProperties().getProperty("storico_url");
		return downloadStorico(url, outputFileName);
	}
	
	static boolean downloadStorico(String url, String outputFileName){

		Long transferred = null;
		Long time = null;
		try{
			long initTime = System.currentTimeMillis();
			URL website = new URL(url);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = FileManager.retrieveFileOutputStream(outputFileName);
			transferred = fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			time = System.currentTimeMillis() - initTime;
			fos.close();
		}
		catch ( IOException e){
			System.err.println("ERROR DOWNLOADING FROM: " + url + "\n" + e.getMessage());
			return false;
		}
		
		System.out.println("Correctly downloaded " + transferred/1024 +" KB in "+ time/1000 + " secs");
		return true;
	}
}
