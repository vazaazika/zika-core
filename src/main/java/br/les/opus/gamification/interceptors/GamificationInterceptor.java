package br.les.opus.gamification.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.les.opus.gamification.GamificationException;
import br.les.opus.gamification.services.GamificationService;

@Transactional
public class GamificationInterceptor implements HandlerInterceptor {
	
	@Autowired
	private GamificationService gamificationService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
		try {
			// We only process the game performed task if the request was successful 
			int status = response.getStatus();
			HttpStatus.Series series = HttpStatus.valueOf(status).series();
			if (!series.equals(HttpStatus.Series.SUCCESSFUL)) {
				return;
			}
			gamificationService.processRequest(request);
		} catch (BadCredentialsException e) {
			logger.error("Action performed with no token informed", e);
			throw new GamificationException("Action performed with no token informed", e);
		} catch (Exception e) {
			logger.error("Unexpected error", e);
			throw e;
		}
	}

}
