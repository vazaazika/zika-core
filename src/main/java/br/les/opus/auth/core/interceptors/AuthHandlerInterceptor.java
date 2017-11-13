package br.les.opus.auth.core.interceptors;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import br.les.opus.auth.core.domain.Resource;
import br.les.opus.auth.core.domain.Token;
import br.les.opus.auth.core.repositories.ResourceRepository;
import br.les.opus.auth.core.repositories.TokenRepository;
import br.les.opus.auth.core.services.TokenService;

/**
 * Filtro que deve ser usado apenas por outras aplicações protegidas pela API de autenticação.
 * Esse filtro intercepta uma requisição HTTP e verifica se ela é válida para o usuário em questão (via token).
 * As aplicações que usam a API de autenticação só precisam colocar esse filtro no topo da cadeia para proteger
 * seus recursos de acordo com as regras definidas no banco
 * @author Diego Cedrim
 */
@Transactional
public class AuthHandlerInterceptor implements HandlerInterceptor {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private TokenRepository tokenRepository;
	
	@Autowired
	private ResourceRepository resourceDao;
	
	private boolean isPublic(Resource resource) {
		List<Resource> publicResources = resourceDao.findAllOpen();
		for (Resource openResource : publicResources) {
			if (openResource.matches(resource)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isThereValidToken(Resource resource, HttpServletRequest request, HttpServletResponse response) {
		String hashedToken = tokenService.getHashedTokenFromRequest(request);
		if (StringUtils.isEmpty(hashedToken)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		
		Token token = tokenRepository.findByHash(hashedToken);
		if (token == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		
		if (!token.hasGrantedPermission(resource)) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			return false;
		}
		return true;
	}

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		/**
		 * Extrai da URI o nome do contexto e só retorna o caminho que vem depois
		 */
		String uri = request.getRequestURI().substring(request.getContextPath().length());
		String operation = request.getMethod();
		
		Resource resource = new Resource();
		resource.setUri(uri);
		resource.setOperation(operation);
		
		if (this.isPublic(resource)) {
			return true;
		} else {
			return isThereValidToken(resource, request, response);
		}
		
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
