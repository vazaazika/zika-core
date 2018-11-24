package br.les.opus.gamification.interceptors;

import java.util.ArrayList;
import java.util.List;

/**
 * Armazena dados da request do player em forma de objeto. Salvamos em uma ThreadLocal
 * para apenas o player autor da request poder acessar. Essa classe é chamada por 
 * ArgumentsAwareJackson2HttpMessageConverter da zika-api, que coloca os dados deserializados
 * da request nesse storage.
 * @author diego
 */
public class PlayerLocalStorage {

	private static PlayerLocalStorage singleton;
	
	private static ThreadLocal<List<Object>> storage;
	
	static {
		singleton = new PlayerLocalStorage();
		storage = new ThreadLocal<>();
	}
	
	private PlayerLocalStorage() {

	}
	
	public static PlayerLocalStorage getInstance() {
		return singleton;
	}
	
	public void addArgument(Object arg) {
		List<Object> argsList = storage.get();
		if (argsList == null) {
			argsList = new ArrayList<>();
		}
		argsList.add(arg);
		storage.set(argsList);
	}
	
	public List<Object> getArguments() {
		return storage.get();
	}
}
