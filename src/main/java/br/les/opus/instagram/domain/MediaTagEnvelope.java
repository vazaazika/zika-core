package br.les.opus.instagram.domain;

public class MediaTagEnvelope extends Envelope<Media, TagPagination> {

	@Override
	public String toString() {
		return "MediaTagEnvelope [getMeta()=" + getMeta()
				+ ", getPagination()=" + getPagination() + "]";
	}
}
