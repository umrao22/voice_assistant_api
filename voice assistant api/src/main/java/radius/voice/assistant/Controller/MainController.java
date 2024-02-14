package radius.voice.assistant.Controller;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import radius.voice.assistant.Dao.MainDao;
import radius.voice.assistant.Services.MainService;

import radius.voice.assistant.Services.TokenService;
import radius.voice.assistant.Utility.Literal;
import radius.voice.assistant.Utility.Utility;

/**
 * @author HRIDENDRA
 */
@Controller
@CrossOrigin("*")
public class MainController {
	/**
	 * Hold the main service and token service
	 */
	private MainDao dao_mysql;
	private MainService mainservice;
	private TokenService tokenservice;
	
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * {@link Constructor}
	 * @throws IOException 
	 */
	public MainController() throws IOException {
		mainservice = MainService.getInstance();
		tokenservice = TokenService.getInstance();
		dao_mysql = MainDao.getInstance();
	}

	/**
	 * Get the application error log
	 * 
	 * @return
	 */
	@RequestMapping(value = "getErrorLog", method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE, headers = "Accept=application/json")
	public String getERROR() {
		try {
			return mainservice.getErrorLog();
		} catch (Exception e) {
			return Literal.ERRRO_HTML + Literal.ERROR_HTML_TAIL;
		}
	}

	/**
	 * Get the full question table 
	 * 
	 * returns model and view object 
	 * 
	 * @return
	 */
	@RequestMapping(value = "getQues", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public ModelAndView  getData() {
				
		ModelAndView mv = new ModelAndView() ;
		
		mv.setViewName("in") ;
		
		return mv ;
	}
	@RequestMapping(value = "getDt", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public Map<String,Object>  getQuestions() {
		Map<String , Object > ret_map = new HashMap<>(6) ;
		
			try {

				ret_map.put("Status", "Success") ;
				ret_map.put("questionData",mainservice.getQuestion()) ;
				ret_map.put("answerData",mainservice.getAnswerTb()) ;
				
				return ret_map ;
				
			} catch (Exception e) {
				ret_map.put("Status", "Error") ;
				ret_map.put("Message", e.getMessage()) ;
				e.printStackTrace();
				return ret_map ;
			}
			}
	
	
	@RequestMapping(value = "keytb", method = RequestMethod.GET, headers = "Accept=application/json")
	@ResponseBody
	public Map<String,Object>  keytable() {
		Map<String , Object > ret_map = new HashMap<>(6) ;
		
			try {

				ret_map.put("Status", "Success") ;
				ret_map.put("keywordtable",mainservice.getkeytb()) ;
				return ret_map ;
				
			} catch (Exception e) {
				ret_map.put("Status", "Error") ;
				ret_map.put("Message", e.getMessage()) ;
				e.printStackTrace();
				return ret_map ;	
			}
			}
	
	/**
	 * Maps the question to the specific answer 
	 * @return 
	 * 
	 */
	@RequestMapping(value = "setAns", method = RequestMethod.POST, headers = Literal.APPLICATION_JSON)
	@ResponseBody
	public String  set_ans(@RequestBody Map<String, Object> req_map) {
		
		try {
			dao_mysql.answerMapping(Integer.parseInt( (String) req_map.get("question_id")),Integer.parseInt( (String) req_map.get("Answer_id"))) ;
		} catch (NumberFormatException e) {
			System.out.println(e) ;
			 dao_mysql.set_null_ans(Integer.parseInt( (String) req_map.get("question_id")));
		} 
		return "Update successful " ;
	}
	/**
	 * Adds answer to the db 
	 * @return 
	 * 	
	 */
	@RequestMapping(value = "addAns", method = RequestMethod.POST, headers = Literal.APPLICATION_JSON)
	@ResponseBody
	public String  add_Ans(@RequestBody Map<String, Object> req_map) {
		
		return dao_mysql.addNew_ans(req_map) ;  
	}
	
	@RequestMapping(value = "apiTest", method = RequestMethod.GET)
	public String apiTest() {
		try {
			if (mainservice.apiTest() < 0) {
				return "API working and Mysql not working.";
			}
			return "I am On";
		} catch (Exception e) {
			Utility.printStackTrace(e, this.getClass().getName());
			return Literal.SOMETHING_WENT_WORNG;
		}
	}
	/**
	 * /**
	 * @param req_map {"query":"hello how are you"}
	 *                <table border=1px>
	 *                <tr>
	 *                <th>Key</th>
	 *                <th>Sample Data</th>
	 *                <th>Data Type</th>
	 *                <th>Constraint</th>
	 *                <th>Description</th>
	 *                </tr>
	 *                <tr>
	 *                <td>query</td>
	 *                <td>hello how are you</td>
	 *                <td>String</td>
	 *                <td>Mandatory</td>
	 *                <td>Anything</td>
	 *                </tr>
	 *                </table>
	 * @param Headers
	 *                <table border=1px>
	 *                <tr>
	 *                <th>Header Key</th>
	 *                <th>Sample Data</th>
	 *                <th>Data Type</th>
	 *                <th>Constraint</th>
	 *                <th>Description</th>
	 *                </tr>
	 *                <tr>
	 *                <td>User-Agent</td>
	 *                <td>Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36</td>
	 *                <td>String</td>
	 *                <td>Mandatory</td>
	 *                <td>user agent</td>
	 *                </tr>
	 *                <tr>
	 *                <td>Content-Type</td>
	 *                <td>application/json</td>
	 *                <td>String</td>
	 *                <td>Mandatory</td>
	 *                <td>Request content type.</td>
	 *                </tr>
	 *                </table>
	 *                <br>
	 * @see <b>Functionality: </b> This API is used give result on the basis of search
	 * @return <b>SUCCESS MESSAGE:</b> <br>
	 * 		   {"MESSAGE":"","STATUS":"Success"} <br>
	 *
	 *         <b>ERROR MESSAGE:</b>
	 *         {"MESSAGE":"Invalid Parameter.","STATUS":"Error","REQUEST_DATA":{"query":"hello"} <br>
	 *
	 *         @apiNote : Name must be unique 
	 */
	@RequestMapping(value = "qrySrch", method = RequestMethod.POST, headers = Literal.APPLICATION_JSON)
	@ResponseBody
	public Map<String, Object> querySearch(@RequestBody Map<String, Object> req_map) {
		
		Map<String, Object> ret_map = new HashMap<String, Object>(Literal.EIGHT);
		try {
			long s1 = System.currentTimeMillis();
			/**
			 * Check for the Null query
			 */
			if (req_map.get(Literal.query) == null || req_map.get(Literal.query).toString().trim().equals("")) {
				ret_map.put(Literal.STATUS, Literal.ERROR);
				ret_map.put(Literal.MESSAGE, Literal.INVALID_PARAMETER);
				ret_map.put(Literal.REQUEST_DATA,req_map);
				return ret_map;
			}
			/**
			 * return success data
			 */
			ret_map.put(Literal.STATUS, Literal.SUCCESS);
			ret_map.put(Literal.MESSAGE, mainservice.querySearch(req_map.get(Literal.query).toString().trim()));
			ret_map.put(Literal.api_time_ms,Utility.apiMill(s1,  System.currentTimeMillis()) );
			
			return ret_map;
		} catch (Exception e) {
			ret_map.put(Literal.STATUS, Literal.ERROR);
			ret_map.put(Literal.MESSAGE, Literal.SOMETHING_WENT_WORNG);
			ret_map.put(Literal.EXCEPTION_MESAGE, e.getStackTrace());
			ret_map.put(Literal.REQUEST_DATA, req_map);
			Utility.printStackTrace(e, this.getClass().getName());
			return ret_map;
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "createTableNeeded", method = RequestMethod.GET, headers = Literal.APPLICATION_JSON)
	public String createTableNeeded() {
		try {
			return mainservice.createTableNeeded();
		} catch (Exception e) {
			return Literal.ERRRO_HTML + Literal.ERROR_HTML_TAIL;
		}
	}

}
