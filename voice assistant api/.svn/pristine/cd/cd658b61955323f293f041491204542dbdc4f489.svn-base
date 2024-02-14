package radius.voice.assistant.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AppInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/**
		 * Hold the Api Url
		 */
		StringBuffer api_url = request.getRequestURL();
		/**
		 * In case of API call
		 */
		if (api_url.length() == 0) {
			return false;
		} else if (api_url.indexOf("html") > 0 || api_url.indexOf("js") > 0 || api_url.indexOf("css") > 0) {
			System.out.println("Documnet URL : " + api_url + " :: Reuest Address : " + request.getRemoteAddr()
					+ " :: User-Agent : " + request.getHeader("User-Agent"));

		} else if (api_url.indexOf("error") > 0) {
			System.out.println("Error URL : " + api_url + " :: Reuest Address : " + request.getRemoteAddr()
					+ " :: User-Agent : " + request.getHeader("User-Agent"));
		} else {
			System.out.println("Api URL : " + api_url + " :: Reuest Address : " + request.getRemoteAddr()
					+ " :: User-Agent : " + request.getHeader("User-Agent"));

		}
		return true;
	}
}
