package radius.voice.assistant.Dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import radius.voice.assistant.Services.SwitchCases;
import radius.voice.assistant.Utility.Literal;

public class MainDao {
	
	/**
	 * Hold the class instance
	 */
	@Autowired
	private static MainDao instance;
	@Autowired
	private JdbcTemplate jdbctemplate;
	private SwitchCases switchcase ; 
	/**
	 * {@link Constructor}
	 */
	
	private MainDao() {
		switchcase = SwitchCases.getInstance() ;
		try {
            SimpleDriverDataSource ds = new SimpleDriverDataSource();
            ds.setDriver(new com.mysql.cj.jdbc.Driver());
            ds.setUrl("jdbc:mysql://localhost:3306/voice_assistant");
            ds.setUsername("root");
            ds.setPassword(null);
            this.jdbctemplate = new JdbcTemplate(ds);
		} catch (SQLException e) {
            e.printStackTrace();
    		}
	}

		public static MainDao getInstance() {
			/**
			 * Check for the null
			 */
			
			if (instance == null) {
				instance = new MainDao();
			}
			return instance;
		
		    }  
		
		/**
		 * Check the DB
		 * 
		 * @return
		 */
		public int checkanswerTable() {
			try {
				return jdbctemplate.queryForObject("Select count(*) from answers ", Integer.class);
			} catch (Exception e) {
				return -1;
			}
		}
		
		public void createanswerTable() {
			try {
				jdbctemplate.execute("CREATE TABLE `voice_assistant`.`answers` (\n"
						+ "  `ans_id` INT NOT NULL AUTO_INCREMENT,\n"
						+ "  `main_key` VARCHAR(128) NULL,\n"
						+ "  `status` VARCHAR(45) NULL,\n"
						+ "  `answer` VARCHAR(1024) NULL,\n"
						+ "  `api_url` VARCHAR(612) NULL,\n"
						+ "   `function` INT NULL,\n"
						+ "  PRIMARY KEY (`ans_id`),\n"
						+ "  UNIQUE INDEX `main_key_UNIQUE` (`main_key` ASC));");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Check the DB
		 * 
		 * @return
		 */
		public int checkquestionsTable() {
			try {
				return jdbctemplate.queryForObject("Select count(*) from questions ", Integer.class);
			} catch (Exception e) {
				return -1;
			}
		}
		
		public void createquestionsTable() {
			try {
				jdbctemplate.execute("CREATE TABLE `voice_assistant`.`questions` (\n"
						+ "  `ques_id` INT NOT NULL AUTO_INCREMENT,\n"
						+ "  `ans_id` VARCHAR(45) NULL,\n"
						+ "  `status` VARCHAR(45) NULL,\n"
						+ "  `text` VARCHAR(45) NULL,\n"
						+ "  `main_key` VARCHAR(124) NULL,\n"
						+ "  PRIMARY KEY (`ques_id`),\n"
						+ "  UNIQUE INDEX `ques_id_UNIQUE` (`ques_id` ASC),\n"
						+ "  UNIQUE INDEX `text_UNIQUE` (`text` ASC));");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Check the DB
		 * 
		 * @return
		 */
		public int checkKeywordsTable() {
			try {
				return jdbctemplate.queryForObject("Select count(*) from main_keywords ", Integer.class);
			} catch (Exception e) {
				return -1;
			}
		}
		
		public void createKeywordsTable() {
			try {
				jdbctemplate.execute("CREATE TABLE `voice_assistant`.`main_keywords` (\n"
						+ "  `key_id` INT NOT NULL AUTO_INCREMENT,\n"
						+ "  `main_key` VARCHAR(256) NULL,\n"
						+ "  `ans_id` INT NULL,\n"
						+ "  `type` VARCHAR(45) NULL,\n"
						+ "  `keywords` VARCHAR(256) NULL,\n"
						+ "  PRIMARY KEY (`key_id`),\n"
						+ "  UNIQUE INDEX `main_key_UNIQUE` (`main_key` ASC));");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * returns list of map contains full question table
		 * 
		 * @return
		 */
	
    	public List<Map<String, Object>> question_Table() {
    		
    		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    		 list =jdbctemplate.queryForList("SELECT  ques_id , text, ans_id ,status , main_key from questions; " ) ;
    		 return list  ;
    	}
    	public List<Map<String, Object>> keyword_Table() {
    		
   		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
   		 list =jdbctemplate.queryForList("SELECT  key_id , main_key, ans_id ,keywords from main_keywords ; " ) ;
   		 return list  ;
   	}
    	
    	/**
		 * returns list of map contains full answer table 
		 * 
		 * @return
		 */
    	public List<Map<String, Object>> answer_table() {
    	
    		 List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
    		 list1 =jdbctemplate.queryForList("select ans_id ,function , api_url ,answer,  main_key  from answers ; ") ;
    		 return list1 ; 
    		 
    	}

    	/**
		 * Updates the answer mapped to the question in mysql table
		 */

		public void answerMapping(int ques_id , int ans_id  ) {
			 
			 try {
				jdbctemplate.update( "update questions set ans_id='"+ ans_id + "', status ='y' where ques_id='" + ques_id+ "' ; ") ;
				 jdbctemplate.update("update main_keywords set ans_id='"+ ans_id + "' where key_id=" + ques_id+ " ; ");
			} catch (DataAccessException e) {
				e.printStackTrace();
			}
		}
		

    	/**
		 * Updates the answer mapped to the question in mysql table
    	 * @return 
		 */

		public String addNew_ans(Map<String, Object> req_map) {
	
		     jdbctemplate.execute("insert into answers(ans_id , main_key , answer , api_url , function) values(?,?,?,?,?)", new PreparedStatementCallback<String>(){  
		    	 @SuppressWarnings("unused")
				public String doInPreparedStatement1(PreparedStatement ps)  
		                throws SQLException, DataAccessException {  
		    		
		            ps.setInt(1,(int)Integer.parseInt( (String) req_map.get(Literal.ans_id)));  
		            ps.setString(2,(String) req_map.get(Literal.main_key));  
		            
		            if(req_map.get("answer") == null || req_map.get(Literal.answer).toString().trim().equals("")&&
		               req_map.get("api_url") == null || req_map.get(Literal.api_url).toString().trim().equals("")&&
		               req_map.get("function") == null || req_map.get(Literal.function).toString().trim().equals("")		
		            		) {
		            	return "Error!! Kindly enter the answer";
		            	
		            }
		            
		            if(req_map.get("answer") == null || req_map.get(Literal.answer).toString().trim().equals("")) {
		            	ps.setNull(3, Types.NULL);
		            }
		            else {
		            	ps.setString(3,(String) req_map.get(Literal.answer));  
		            }
		            if(req_map.get("api_url") == null || req_map.get(Literal.api_url).toString().trim().equals("")) {
		            	ps.setNull(4, Types.NULL);
		            }
		            else {
		            	ps.setString(4, (String) req_map.get(Literal.api_url));
		            }
      
		            if(req_map.get("function") == null || req_map.get(Literal.function).toString().trim().equals("")) {
		            	ps.setNull(5, Types.NULL);
		            }
		            else {
		            ps.setInt(5, (int) req_map.get(Literal.function));
		            }
		            
		             ps.execute();  
		             return "Answer added Successfully" ;
		                  
		        }

				@Override
				public String doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					
					 ps.setInt(1,(int)Integer.parseInt( (String) req_map.get(Literal.ans_id)));  
			            ps.setString(2,(String) req_map.get(Literal.main_key));  
			            if(req_map.get(Literal.answer) == null || req_map.get(Literal.answer).toString().trim().equals("")) {
			            	ps.setNull(3, Types.NULL);
			            }
			            else {
			            	ps.setString(3,(String) req_map.get(Literal.answer));  
			            }
			            if(req_map.get(Literal.api_url) == null || req_map.get(Literal.api_url).toString().trim().equals("")) {
			            	ps.setNull(4, Types.NULL);
			            }
			            else {
			            	ps.setString(4, (String) req_map.get(Literal.api_url));
			            }
			             
			            if(req_map.get(Literal.function) == null || req_map.get(Literal.function).toString().trim().equals("")) {
			            	ps.setNull(5, Types.NULL);
			            }
			            else {
			            	 ps.setInt(5,(int)Integer.parseInt( (String) req_map.get(Literal.function)));  			            }
			            
			             ps.execute();  
			             return "ans added ps" ;
				}

				
				
			
		        });  
		     return "ans added" ;
				}
//			
//			try {
//			 int func = Integer.parseInt((String) req_map.get("function")) ;
//			 String ans =null;
//			 String api_url=(String) req_map.get("api_url") ;
////			 if (req_map.get("answer") == null || req_map.get("api_url").toString().trim().equals("")) {
////				 String answer=null ;
////			 }
//			 
//			 String query = "insert into answers(ans_id , main_key , answer , api_url , function) values(\n"
//					 + "'" + Integer.parseInt((String) req_map.get("ans_id")) + "',\n" 
//					 + "'" +req_map.get("main_key") + "',\n" 
//					 + "'" + Types.NULL + "',\n" 
//					 + "'" +api_url + "',\n" 
//					 + "'" + func + "')" ;
//			 System.out.println(ans);
//				System.out.println(req_map.get("api_url"));
//			
//					 jdbctemplate.execute(query) ;
//					
//					return "Answer Added Successfully" ;
//			}
//			 catch(NumberFormatException ex){ // handle your exception
//				 String query = "insert into answers(ans_id , main_key , answer , api_url ) values(\n"
//						 + "" + Integer.parseInt((String) req_map.get("ans_id")) + ",\n" 
//						 + "'" +req_map.get("main_key") + "',\n" 
//						 + "'" +req_map.get("answer") + "',\n" 
//						 + "'" +req_map.get("api_url")  + "')"  ;
//						System.out.println(req_map.get("answer"));
//						System.out.println(req_map.get("api_url"));
//				 jdbctemplate.execute(query) ;
//				 return "Answer Added Successfully" ;
//				}
			
			
		/**
		 * returns answer from the mysql table 
		 * 
		 * @return
		 * @throws IOException 
		 * @throws UnsupportedEncodingException 
		 */
		
		public String getAnswerId(String query ,String main_key ,  String keywords) throws UnsupportedEncodingException, IOException {
			if(keyword_check(query ,main_key ,  keywords)) {
				System.out.println("calling no ans") ;
				no_available_ans( query ,main_key ,  keywords) ; 
			}
			
			List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
			list1 =jdbctemplate.queryForList( "SELECT ans_id FROM questions where main_key ='"+main_key+"'");
			
			Map<String,Object> map = new HashMap<>();
			
			if(list1.size()>=2) {
				
			for(int i = 0 ; i<list1.size();i++){
				 map=list1.get(i) ;
				 if(map.get(Literal.ans_id)==null||map.get(Literal.ans_id)=="" ) {
				 }
				 else{
					 List<Map<String, Object>> list_key  =  jdbctemplate.queryForList("select keywords FROM main_keywords where ans_id='"+(map.get("ans_id"))+"'");
					 Map<String,Object> map_key = new HashMap<>();
					
					 for(int j = 0 ; j<list_key.size();j++){
						 map_key=list_key.get(j) ;
						 
					 if(map_key.get(Literal.keywords).toString().trim().equals(keywords)) {
						 int ans_id= (int) map.get(Literal.ans_id);
						 return get_answer(ans_id , keywords);
					 }}
					
				 }
				
				
				
				
			}
			
			}
			else if(list1.size()==1){
				map=list1.get(0);
				System.out.println("error");
				if (map.get(Literal.ans_id)==null||map.get(Literal.ans_id).toString().trim().equals("")) {
					
					return  switchcase.gSearch(query);
				}
				 int ans_id= (int) map.get(Literal.ans_id);
				 return get_answer(ans_id , keywords);
		

			}
			
			else { 

				return "Unable to assist you at the moment ";
				}
			System.out.println("error2");
			return "Unable to assist you at the moment ";
		}
			
			
	private boolean keyword_check(String query, String mainkey, String keywords) throws UnsupportedEncodingException, IOException {
		
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();
		list1 =jdbctemplate.queryForList("SELECT main_key , keywords FROM main_keywords where main_key ='"+mainkey+"'");
		
		
		Iterator<Map<String, Object>> iterator = list1.iterator();
		  while(iterator.hasNext()) {

	            Map<String, Object> map = iterator.next();
	            System.out.println(map);
	            if(map.get(Literal.main_key).toString().trim().equals(mainkey)) {
	            	if(map.get(Literal.keywords).toString().trim().equals(keywords)) {	
	            		return false ;
	            	}
	            }
		  }
		  System.out.println("trueeeeee");
	return true;
	}

			public void no_available_ans(String query ,String main_key ,  String keywords) throws UnsupportedEncodingException, IOException {				
				try {
					System.out.println("updating question");
					jdbctemplate.execute("insert into questions(text , main_key ) values('"+ query + "','"+main_key+"')");
					jdbctemplate.execute("insert into main_keywords( main_key ,  keywords ) values('"+main_key+"','"  +keywords+"')");
					System.out.println("updating question");
					
				} catch (DataAccessException e) {
					
				}
		}

	private String get_answer(int ans_id, String keywords) {
		
		Map<String, Object> map= jdbctemplate.queryForMap("select answer , api_url , function FROM answers where ans_id = '" +ans_id+ "'" )	;
	
			int func_no;
			try {
				
				String url = (String) map.get(Literal.api_url);
				if(url==null) {
					 func_no = (int) map.get(Literal.function);
					return switchcase.functions(func_no, keywords) ; 
				 }
				func_no = (int) map.get(Literal.function);
				return switchcase.callingApi(func_no , keywords , url); 
			} catch (Exception e) {}

			
			try {
				
				func_no = (int) map.get(Literal.function);
				return switchcase.functions(func_no, keywords) ; 
			}catch (Exception e) {} 
			
			

			
			try {
				String ans = (String) map.get(Literal.answer);
				return ans ;
			} catch (Exception e) {} 
			
		
			return "sorry unable to assist you at the moment" ;

	
	}

	public void set_null_ans(int ques_id) {
	
		jdbctemplate.update("update questions set ans_id=NULL where ques_id ="+ques_id) ;
		
	}

	public String get_ans_bykey(String query, String keywords) throws UnsupportedEncodingException, IOException {
	
	 List<String> list = jdbctemplate.queryForList("select ans_id from main_keywords where keywords='"+ keywords+ "'; ", String.class);
	 
	 if (list.isEmpty()||list.get(0)==null) {
		
		 
		 return switchcase.gSearch(query) ;
	 }
	 int ans=Integer.parseInt(list.get(0));
	
		
	return get_answer(ans , keywords) ;
	
}


}
	

