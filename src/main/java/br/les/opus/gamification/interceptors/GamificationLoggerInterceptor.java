package br.les.opus.gamification.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.les.opus.auth.core.domain.Token;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.services.TokenService;
import br.les.opus.gamification.GamificationException;
import br.les.opus.gamification.UserAction;
import br.les.opus.gamification.repositories.UserActionRepository;

@Transactional
public class GamificationLoggerInterceptor implements HandlerInterceptor {
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserActionRepository userActionDao;
	
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
			Token currentToken = tokenService.getAuthenticatedUser(request);
			User user = currentToken.getUser();
			
			String uri = request.getRequestURI().substring(request.getContextPath().length());
			String operation = request.getMethod();
			int status = response.getStatus();
			
			UserAction action = new UserAction(uri, operation, status, user);
			userActionDao.save(action);
		} catch (BadCredentialsException e) {
			logger.error("Action performed with no token informed", e);
			throw new GamificationException("Action performed with no token informed", e);
		} catch (Exception e) {
			logger.error("Unexpected error", e);
			throw e;
		}
	}

}
