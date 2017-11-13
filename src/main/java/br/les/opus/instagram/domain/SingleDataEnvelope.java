package br.les.opus.instagram.domain;


public class SingleDataEnvelope<T> {
	
	private Meta meta;
	
	private T data;

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
