package br.les.opus.instagram.domain;

import java.util.List;

public class Envelope<T, P> {

	private Meta meta;
	
	private P pagination;
	
	private List<T> data;

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public P getPagination() {
		return pagination;
	}

	public void setPagination(P pagination) {
		this.pagination = pagination;
	}
	
}
