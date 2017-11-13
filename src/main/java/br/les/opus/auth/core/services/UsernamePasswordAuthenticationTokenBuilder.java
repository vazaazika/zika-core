package br.les.opus.auth.core.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

/**
 * Classe responsável por construir um token de autenticação com base no header Authorization
 * usando Basic Authentication do HTTP
 * 
 * @author Diego Cedrim
 */
@Service
public class UsernamePasswordAuthenticationTokenBuilder {

	private String credentialsCharset = "UTF-8";

	private Logger logger = Logger.getLogger(getClass());
	
	private AuthenticationDetailsSource<HttpServletRequest,?> authenticationDetailsSource = new WebAuthenticationDetailsSource();

	protected String getCredentialsCharset(HttpServletRequest httpRequest) {
		return credentialsCharset;
	}

	/**
	 * Decodifica os dados no header de autorização e retorna como um array de strings
	 * @param header valor do cabeçaho Authorization
	 * @param request Requisição HTTP feita para início de autenticação
	 * @return um array de strings com os valores do cabeçalho
	 * @throws IOException disparada em caso de falha na tentativa de decodificar os valores do cabeçalho
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request) throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		} catch (IllegalArgumentException e) {
			throw new BadCredentialsException("Failed to decode basic authentication token");
		}

		String token = new String(decoded, getCredentialsCharset(request));

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] {token.substring(0, delim), token.substring(delim + 1)};
	}

	public UsernamePasswordAuthenticationToken build(HttpServletRequest request) {
		try {
			String header = request.getHeader("Authorization");

			if (header == null || !header.startsWith("Basic ")) {
				throw new BadCredentialsException("Cabeçalho Authorization não definido ou definido de forma diferente do Basic HTTP Auth.");
			}

			String[] tokens = extractAndDecodeHeader(header, request);
			assert tokens.length == 2;

			String username = tokens[0];
			logger.info("Basic Authentication Authorization header found for user '" + username + "'");
			
			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, tokens[1]); 
			authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
			return authRequest;
		} catch (IOException e) {
			throw new BadCredentialsException(e.getMessage(), e);
		}
	}
}
