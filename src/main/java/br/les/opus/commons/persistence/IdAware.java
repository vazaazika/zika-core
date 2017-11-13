package br.les.opus.commons.persistence;

public interface IdAware<T> {
	T getId();
	void setId(T id);
}
