package radius.voice.assistant.Services;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import opennlp.tools.lemmatizer.DictionaryLemmatizer;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

public class Text_nlp {
	
	
	POSTaggerME posTagger ;
	
	 DictionaryLemmatizer lemmatizer ;
	private static Text_nlp instance;
	

	
	public static Text_nlp getInstance() throws IOException {
		/**
		 * Check for the Null
		 */
		
		if (instance == null) {
			instance = new Text_nlp();
			
		}
		return instance;
	}


	

	
	
	
	public Text_nlp() throws IOException {
		
	
		
		 InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
		 POSModel posModel = new POSModel(inputStreamPOSTagger);
		    posTagger = new POSTaggerME(posModel);
		   
		    InputStream dictLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict.txt");
		     lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
	}
	
	
	public Map<String, List<String>> text_processing(String query) throws IOException {
		
		
		
//		  InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
//		   POSModel posModel = new POSModel(inputStreamPOSTagger);
//		   POSTaggerME posTagger = new POSTaggerME(posModel);
//		   
//		   
//		   InputStream dictLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict.txt");
//		    DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
		
		
		
		System.out.println(query);
		
		List<String> token_lst = new ArrayList<String>(Arrays.asList(query.split(" ")));
		   System.out.println(token_lst);
		   String[] tokens = token_lst.toArray(new String[0]);
		   
			/*
			 * part of speech 
			 * 	   
			 */
//				   long s1 = System.currentTimeMillis();
			
		  
				   
				String[] tags =posTagger.tag(tokens) ;
				   List<String> pos_lst =new LinkedList<String>(Arrays.asList(tags)) ; 
				 
		    /*
		     * lemmatization - converting the word to its root form 
		     */
						
		  
			String[] lemmas = lemmatizer.lemmatize(tokens, tags); 
			List<String>lemmatize_lst =new LinkedList<String>(Arrays.asList(lemmas)) ; 
			   
			  Map<String, List<String>> map =new HashMap<String, List<String>>();
			  map.put("tokenlst",token_lst);
			  map.put("lemmatize_lst", lemmatize_lst) ;
			  map.put("pos_lst", pos_lst) ;
			  return map;
		
	}
	
	
	
}



