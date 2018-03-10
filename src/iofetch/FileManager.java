package iofetch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import estrazioni.StoricoManager;

class FileManager {
	private static String sm_confEnvPath;	


	static String getConfigurationEnvironmentPath() {
		return sm_confEnvPath;
	}
	
	static List<String> fetchLines(String path){
		List<String> list = new ArrayList<>();
		
		URI uri = FileManager.retrieveURI(path);
		if ( uri == null ) return Collections.emptyList();

		try (Stream<String> stream = Files.lines(Paths.get(uri))) {
			stream.forEach(line -> {
				list.add(line);
			});
			return list;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		return Collections.emptyList();

	}
	
	static List<String> fetchLastLines(String path){
		
		List<String> tmpList = new ArrayList<>();
		List<String> storicoList = StoricoManager.getStoricoLines();
		
		String lastStoricoLine = null;

		if ( !storicoList.isEmpty() ){
			lastStoricoLine = storicoList.get(storicoList.size() - 1);
		}
		
		URI uri = FileManager.retrieveURI(path);
		if ( uri == null ) return Collections.emptyList();

		try (Stream<String> stream = Files.lines(Paths.get(uri))) {
			
			List<String> streamToList = stream
								.sorted(Comparator.reverseOrder())
								.collect(Collectors.toList());
			
			for (String line: streamToList ){
				if ( !line.equals(lastStoricoLine) ){
					tmpList.add(line);
				}
				else{
					break;
				}
			}
			return tmpList;
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
		
		return Collections.emptyList();

	}
	
	static void deleteFile(String filePath){
		File file = FileManager.retrieveFile(filePath);
		if ( file.exists() ){
			file.delete();
		}
	}

	/**
	 * Retrieves the canonical path of the file referred by the relative path taken in input.
	 * @param p_relPath
	 */
	static String retrieveCanonicalPath(final String p_relPath) {
		final StringBuilder l_string = new StringBuilder();
		final char l_fileSeparator = File.separatorChar;
		for ( char l_char: p_relPath.toCharArray() ){
			if ( l_char == '\\' || l_char == '/' ){
				l_string.append(l_fileSeparator);
			}
			else{
				l_string.append(l_char);
			}
		}

		String l_builderToString = l_string.toString();

		if ( !l_builderToString.startsWith(File.separator) ){
			l_builderToString = File.separator + l_string.toString();
		}

		return sm_confEnvPath + l_builderToString;
	}

	/**
	 * This method takes in input a string which refers to a relative path (from resources folder). It returns an 
	    instance of File the file referred from the input string.
	 * If no File object can be retrieved the exception is logged.
	 * 
	 * Note: the name of the input string should begin with File.separator. If it doesn't, the method puts it by 
	    itself.
	 * @param p_relativePath
	 */
	static File retrieveFile(final String p_relativePath) {
		final File l_file = new File(retrieveCanonicalPath(p_relativePath));
		return l_file;
	}

	/**
	 * This method takes in input a string which refers to a relative path (from resources folder). It returns an URL 
	    representing the complete path of the file referred from the input string.
	 * If no URL can be retrieved the exception is logged.
	 * 
	 * Note: the name of the input string should begin with "/".
	 * @param p_relativePath
	 */
	static URL retrieveURL(final String p_relativePath) {

		try{
			final File l_file = retrieveFile(p_relativePath);
			if ( l_file != null ){
				return l_file.toURI().toURL();
			}
			//else
			throw new IOException();
		}
		catch ( final IOException l_e ){
			System.err.println("IO ERROR: " + p_relativePath);

			return null;
		}
	}

	static URI retrieveURI(String path){

		try {
			URI uri = FileManager.retrieveURL(path).toURI();
			return uri;
		} catch (URISyntaxException e1) {
			System.err.println(e1.getMessage());
		}
		return null;

	}
	
	static FileOutputStream retrieveFileOutputStream(String path){
		String canPath = FileManager.retrieveCanonicalPath(path);
		try {
			return new FileOutputStream(canPath);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	static {
		File file = new File("resources");
		String absolutePath = file.getAbsolutePath();
		
		sm_confEnvPath = absolutePath;

	}
}
