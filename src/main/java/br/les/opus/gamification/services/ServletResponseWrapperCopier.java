package br.les.opus.gamification.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class ServletResponseWrapperCopier extends HttpServletResponseWrapper{

	private final ByteArrayOutputStream capture;
	private ServletOutputStream output;
	private PrintWriter writer;

	public ServletResponseWrapperCopier(HttpServletResponse response) {
		super(response);
		capture = new ByteArrayOutputStream(response.getBufferSize());
	}

	@Override
	public ServletOutputStream getOutputStream() {
		if (writer != null) {
			throw new IllegalStateException(
					"getWriter() has already been called on this response.");
		}

		if (output == null) {
			output = new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					capture.write(b);
				}

				@Override
				public void flush() throws IOException {
					capture.flush();
				}

				@Override
				public void close() throws IOException {
					capture.close();
				}
			};
		}

		return output;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (output != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called on this response.");
		}

		if (writer == null) {
			writer = new PrintWriter(new OutputStreamWriter(capture,
					getCharacterEncoding()));
		}

		return writer;
	}

	public byte[] getCaptureAsBytes() throws IOException {
		if (writer != null) {
			writer.close();
		} else if (output != null) {
			output.close();
		}

		return capture.toByteArray();
	}

	public String getCaptureAsString() throws IOException {
		return new String(getCaptureAsBytes(), getCharacterEncoding());
	}
}