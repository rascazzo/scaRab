package it.er.transform;


import it.er.object.Logged;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public interface Ofelia {
	public ByteArrayOutputStream writeTo(Object target, Class<?> type) throws IOException;
	public Logged getLogged();
	public Object getContent();
}
