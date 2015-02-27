package de.unihamburg.informatik.nats.jwcdg.predictors;

public class TurboWrapper {
	public static final String LIB_TURBO_PATH = "libturbo_path";
	public static final String LIB_TURBO_PMODEL = "libturbo_parser_model";
	public static final String LIB_TURBO_TMODEL = "libturbo_tagger_model";
	
	private long parserhandle;
	private long taggerhandle;
	
	/**
	 * Initializes a wrapper according to the configuration
	 * the shared library will be loaded as well.
	 * @param conf
	 */
	public TurboWrapper() {
		System.load(System.getProperty("LIB_TURBO_PATH", "/tmp/libturbo.so"));
		parserhandle = initialize(System.getProperty("TURBO_PMODEL", "/tmp/wsj-incr.model"));
		taggerhandle = initializeTagger(System.getProperty("TURBO_TMODEL", "/tmp/tagger-increments.model"));
	}
	
	/**
	 * Initializes a wrapper with the given Models.
	 * Note: libturbo.so needs to be loaded already!
	 * @param parserModel
	 * @param taggerModel
	 */
	public TurboWrapper(String parserModel, String taggerModel) {
		parserhandle = initialize(parserModel);
		taggerhandle = initializeTagger(taggerModel);
	}
	
	public String tag(String sentence) {
		return tag(taggerhandle, sentence);
	}
	public String tagAndParse(String sentence) {
		return parse(tag(sentence));
	}	
	public String parse(String sentence) {
		return parse(parserhandle, sentence);
	}
	
	public String extend(String sentence) {
		return extend(parserhandle, sentence);
	}
	
	public native long initialize(String modelFilePath);
	public native long initializeTagger(String modelFilePath);
	private native String parse(long pipe, String sentence);
	private native String tag(long pipe, String sentence);
	private native String extend(long pipe, String word);

}
