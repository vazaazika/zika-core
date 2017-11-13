package br.les.opus.auth.core.services;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import br.les.opus.auth.core.domain.Token;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.repositories.TokenRepository;

@Service
public class TokenService {
	
	/**
	 * Número de horas que devemos esperar antes de remover
	 * um token inativo
	 */
	public static final Integer HOURS_LIMIT = 6;
	
	public static final String TOKEN_PARAMETER_NAME = "token";

	public static final String TOKEN_HEADER_NAME = "X-Auth-User-Token";
	
	@Autowired
	private TokenRepository tokenDao;
	
	@Autowired
	private TokenRepository repository;
	
	public Token generateToken(User user, Boolean longLasting) {
		Token token = new Token(user);
		token.setUser(user);
		token.setLongLasting(longLasting);
		token = tokenDao.save(token);
		return token;
	}
	
	public boolean hasAuthenticatedUser(HttpServletRequest request) {
		try {
			return getAuthenticatedUser(request) != null;
		} catch (BadCredentialsException e) {
			return false;
		}
	}
	
	public Token getAuthenticatedUser(HttpServletRequest request) {
		String hashedToken = this.getHashedTokenFromRequest(request);
		if (hashedToken == null) {
			throw new BadCredentialsException("Nenhum token informado. "
					+ "Informe um tokem através do parâmetro '" + TokenService.TOKEN_PARAMETER_NAME +  "' da requisição "
					+ "ou a partir do cabeçalho " + TokenService.TOKEN_HEADER_NAME);
		}

		Token token = repository.findByHash(hashedToken);
		if (token == null) {
			throw new BadCredentialsException("O token informado é inválido");
		}
		
		/*
		 * Atualiza a última vez usada do token
		 * e remove tokens de vida curta sem uso
		 */
		this.updateLastTimeUsed(token);
		this.removeUnusedTokens(token.getUser());
		
		return token;
	}

	public String getHashedTokenFromRequest(HttpServletRequest request) {
		String parameterValue = request.getParameter(TOKEN_PARAMETER_NAME);
		String headerValue = request.getHeader(TOKEN_HEADER_NAME);
		
		if (StringUtils.isEmpty(parameterValue) && StringUtils.isEmpty(headerValue)) {
			return null;
		}
		
		String hashedToken = (parameterValue != null)? parameterValue : headerValue;
		return hashedToken;
	}
	
	public void updateLastTimeUsed(Token token) {
		token.setLastTimeUsed(new Date());
		repository.save(token);
	}
	
	public void removeUnusedTokens(User user) {
		DateTime datetime = new DateTime();
		datetime = datetime.minusHours(HOURS_LIMIT);
		repository.deleteUsedBefore(user, datetime.toDate());
	}
	
}
