package iofetch;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Properties;

@SuppressWarnings("serial")
class PropertiesParser extends Properties {

	private String m_filepath;		

	private final HashSet<Object> m_keys = new LinkedHashSet<>();

	PropertiesParser(final String p_path) {
		try( final InputStream l_inputStream = FileManager.retrieveURL(p_path).openStream(); ){

			this.load(l_inputStream);

			this.m_filepath = p_path;

		} catch (final IOException l_e) {

			final String l_fatalMessage = "IO ERROR: Relative path of file: " + p_path;
			System.err.println(l_fatalMessage);
		}        

	}

	public Iterable<Object>  getOrderedKeys() {
		return Collections.list(this.keys());
	}

	@Override
	public String getProperty(final String p_property) {
		final String l_property = super.getProperty(p_property);

		if ( l_property == null ){
			System.err.println("Property not found: " + p_property + " inside file: " + this.m_filepath);
		}

		return l_property;
	}

	@Override
	public synchronized Enumeration<Object>  keys() {
		return Collections.<Object>enumeration(this.m_keys);
	}

	@Override
	public synchronized Object put(final Object p_key, final Object p_value) {
		this.m_keys.add(p_key);
		return super.put(p_key, p_value);
	}


}
