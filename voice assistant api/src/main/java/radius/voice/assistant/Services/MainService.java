package radius.voice.assistant.Services;


import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;


import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;



//import opennlp.tools.tokenize.TokenizerME;
//import opennlp.tools.tokenize.TokenizerModel;
import radius.voice.assistant.Dao.MainDao;
import radius.voice.assistant.Utility.Literal;
import radius.voice.assistant.Utility.Utility; 

public class MainService {
	/**
	 * Hold the Main DAO
	 */
	private Text_nlp text_process;
	
	private MainDao dao_mysql;
	/**
	 * Hold the Main service
	 */
	private static MainService instance;
	/**
	 * {@link Constructor}
	 * @throws IOException 
	 */
	private MainService() throws IOException {
		dao_mysql = MainDao.getInstance();
		text_process = Text_nlp.getInstance() ;
//		Text_nlp text_process = new Text_nlp() ;
	}

	/**
	 * @return
	 * @throws IOException 
	 */
	
 
	
	public static MainService getInstance() throws IOException {
		/**
		 * Check for the Null
		 */
		if (instance == null) {
			instance = new MainService();
		}
		return instance;
	}

	/**
	 * @return
	 */
	public int apiTest() {
		return 1;
	}

	/**
	 * @return
	 */
	public String getErrorLog() {
		try {
			/**
			 * Make the HTML to Return
			 */
			return Literal.ERRRO_HTML + new String(Files.readAllBytes(Paths.get(Utility.ERROR_HTML_PATH)))
					+ Literal.ERROR_HTML_TAIL;
		} catch (Exception e) {
			Utility.printStackTrace(e, this.getClass().getName());
		}
		return "No Error Found";
	}

	/**
	 * Returns Question table 
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getQuestion() {
			
			return dao_mysql.question_Table() ;
			
		}
	 public String createTableNeeded() {
	        try {
	            /**
	             * Check the site table
	             */
	            if (dao_mysql.checkanswerTable() == -1) {
	            	dao_mysql.createanswerTable();
	            }
	            /**
	             * Check the UPI table
	             */
	            if (dao_mysql.checkquestionsTable() == -1) {
	            	dao_mysql.createquestionsTable();
	            }
	            /**
	             * Check the NetBanking table
	             */
	            if (dao_mysql.checkKeywordsTable() == -1) {
	            	dao_mysql.createKeywordsTable();
	            }
	            
	        } catch (Exception e) {
	            Utility.printStackTrace(e, this.getClass().getName());
	        }
	        return "All tables created successfully ";
	    }
	
	/**
	 * Return the answer 
	 * 
	 * find the best answer for the query 
	 * 
	 * @return
	 */
	
	
	public String querySearch(String q) {
		try {
			System.out.println(q);
			long s1 = System.currentTimeMillis();
			String query=q.replace("genius", "").replace("xenius", "").replace("gf" ,"").replace("jennie" ,"").replace("jio" ,"").replace("jesus" ,"").trim() ;			System.out.println("before--------------------"+query);
			
		System.out.println(	"replacing" +Utility.apiMill(s1,  System.currentTimeMillis()));
	
			Map<String, List<String>> maps_lst = text_process.text_processing(query);
			System.out.println(maps_lst);
			
//			  InputStream inputStreamPOSTagger = getClass().getResourceAsStream("/models/en-pos-maxent.bin");
//			   POSModel posModel = new POSModel(inputStreamPOSTagger);
//			   POSTaggerME posTagger = new POSTaggerME(posModel);
//			   
//			   
//			   InputStream dictLemmatizer = getClass().getResourceAsStream("/models/en-lemmatizer.dict.txt");
//			    DictionaryLemmatizer lemmatizer = new DictionaryLemmatizer(dictLemmatizer);
			    
			    
			
			
			String[] stopwords = { "zee","gf" , "xenius" , ".", "see" , "him", "using", "neither", "since", "although", "two", "to", "well", "show", "never", "unless", "hence", "it", "within", "either", "the", "hereupon", "will", "down", "i","I", "still", "whole", "much", "thereupon", "she", "wherever", "in", "am", "even",   "whereupon",  "anything", "whoever", "has", "themselves", "until",  "being", "more", "with", "are", "done", "some", "might", "among","nor", "re", "else", "meanwhile", "without", "very", "six", "nowhere", "twenty",  "been", "rather", "sometimes", "amongst", "around", "over", "eight", "if", "seem", "herself", "m", "back", "only", "whereafter", "say", "mostly", "empty", "sometime", "yourselves", "myself", "almost", "name", "any", "mine", "latterly", "thru", "this", "sixty", "for", "toward", "or", "all", "onto", "already", "call", "moreover", "towards", "is", "becoming", "whereby", "between", "thence", "off", "besides", "serious", "hers", "formerly", "whither", "put", "next", "someone", "further", "bottom", "upon", "less", "same", "was", "due", "whatever", "former", "into", "and", "at", "that", "used", "above", "get", "somehow", "make", "again", "‘d", "because", "seeming", "another", "can", "together",  "yours", "he", "ours", "while", "against", "who","amount", "move", "those", "hereafter", "each", "most", "here", "often", "than", "wherein", "part", "noone", "nobody", "anyway", "ever", "thereafter", "under", "go", "my", "from", "five",  "were", "becomes", "seemed", "had", "three", "then", "ten", "not", "whom", "may", "fifteen", "could", "elsewhere", "ourselves", "everyone", "somewhere", "on", "nothing", "now", "full",  "throughout", "both", "one", "once", "regarding", "a", "several", "give", "latter", "itself", "below", "up", "ca", "became", "as", "no", "when",  "your", "our", "alone", "perhaps", "they",  "along", "always", "quite", "many",  "beforehand", "forty", "via", "of", "by", "please", "did", "after",  "anyhow", "though", "whence", "before", "through", "his", "become", "twelve", "top", "per",  "does", "side", "least", "whose", "whenever", "thereby", "its", "nine", "behind", "front", "these", "just", "last", "four", "own", "really" , "‘s", "but", "us", "made", "third", "beyond", "thus", "herein", "afterwards", "there", "whether", "others", "doing", "them", "other", "first", "should", "whereas", "beside", "do", "anyone", "enough", "keep", "hereby", "yet", "none", "out", "seems", "have", "which", "indeed", "therein", "during", "everything", "however", "about", "cannot", "would", "too", "an", "across", "anywhere", "we", "take", "must", "me", "her", "hundred", "few", "nevertheless", "’d", "himself", "eleven",     "therefore", "such", "their", "be", "also", "various", "everywhere", "except", "otherwise", "namely", "so", "every", "fifty"   } ;
			List<String> stopword_lst = new LinkedList<String>(Arrays.asList(stopwords)) ; 
			/*
			 * Tokenizing the query text  
			 */
//			long s1 = System.currentTimeMillis();	
//			InputStream inputStream = getClass().getResourceAsStream("/models/en-token.bin");
//				    TokenizerModel model = new TokenizerModel(inputStream);
//				    TokenizerME tokenizer = new TokenizerME(model);
//				    String[] tokens = tokenizer.tokenize(query);
//				    System.out.println(tokens) ;	
//				List<String> token_lst =new LinkedList<String>(Arrays.asList(tokens)) ;
				 
			List<String> token_lst=maps_lst.get("tokenlst") ;
			List<String> lemmatize_lst=maps_lst.get("lemmatize_lst") ;
			List<String> pos_lst=maps_lst.get("pos_lst") ;
				   System.out.println(Utility.apiMill(s1,  System.currentTimeMillis()));  
		
//				   long s1 = System.currentTimeMillis();
			    String result = "";
			    int i = 0 ; 
			    List<String> keys = new LinkedList<String>() ; 
			    for( ListIterator<String> iterator = lemmatize_lst.listIterator(); iterator.hasNext(); i++) {
			    	 pos_lst.get(i);
			  
			    	if(iterator.next()=="O") {
			    		iterator.remove() ;
			    		keys.add(token_lst.get(i)) ;
			    	}
			    	else  {
			    	}
			    		}	
			    List<String> copy_lst =new LinkedList<String>() ; 
			    if(lemmatize_lst.isEmpty()) {
			    	copy_lst.addAll(keys) ;	
			    }
			    System.out.println(keys) ;
			    System.out.println(lemmatize_lst) ;
			    System.out.println("print done");
			   
			    copy_lst.addAll(lemmatize_lst) ;
			    System.out.println(copy_lst) ;
			   lemmatize_lst.removeAll(stopword_lst) ;
			   System.out.println(lemmatize_lst) ;
//			   lemmatize_lst.addAll(copy_lst) ;
			   if(lemmatize_lst.isEmpty()) {
				   lemmatize_lst.addAll(copy_lst) ;
				  result =dao_mysql.getAnswerId(query, lemmatize_lst.toString() , keys.toString());					   System.out.println("inside if "+copy_lst) ;

//			    	dao_mysql.no_available_ans(query, lemmatize_lst.toString(),keys.toString()) ;
			    	
			    	return result.trim();
			    }
			   
			   System.out.println(lemmatize_lst) ;
			
			

				
			String msg  =dao_mysql.getAnswerId(query, lemmatize_lst.toString() , keys.toString());	
			
			result = result + msg ; 
			if (result.equalsIgnoreCase(Literal.EMPTY_STRING)) {
				return Literal.Ask_again;
			} else {
				System.out.println(result);
				return result.trim();
			}
		} catch (Exception e) {
			Utility.printStackTrace(e, this.getClass().getName());
		}
		return "sorry unable to assist you at the moment";
	}

	/**
	 * Returns full answer table 
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getAnswerTb() {
		return dao_mysql.answer_table()  ;
	}

	public List<Map<String , Object>> getkeytb() {
		return dao_mysql.keyword_Table() ;
		
	}
	
	
}
