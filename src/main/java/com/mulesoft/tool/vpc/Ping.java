package com.mulesoft.tool.vpc;

import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class Ping implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		Map<String, String> inbound = (Map<String, String>) eventContext.getMessage().getProperty("http.query.params",
				PropertyScope.INBOUND);
		return call(inbound.get("host"));
	}

	public InputStream call(String host) throws Exception {
		ProcessBuilder pb = new ProcessBuilder("ping", "-c", "4", host);
		Process p = pb.start();
		SequenceInputStream s = new SequenceInputStream(p.getInputStream(), p.getErrorStream());
		return s;
	}

}
