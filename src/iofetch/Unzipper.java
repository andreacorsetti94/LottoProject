package iofetch;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

class Unzipper {

	static void unzip(String source, String destFolder){

	    try {
	         ZipFile zipFile = new ZipFile(FileManager.retrieveFile(source));
	         
	         String path = FileManager.retrieveCanonicalPath(destFolder);
	         
	         zipFile.extractAll(path);
	    } catch (ZipException e) {
	    	System.out.println("EXCEPTION: " + e.getMessage());
	    }
	}
}
