package name.anonymous.heros.api.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;

public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {
	static final Logger LOGGER = LoggerFactory.getLogger(LoggingRequestInterceptor.class);

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		traceRequest(request, body);
		ClientHttpResponse response = execution.execute(request, body);
		traceResponse(response);
		return response;
	}

	private void traceRequest(HttpRequest request, byte[] body) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format(
					new StringBuilder()
							.append("%n===========================request begin================================================%n")
							.append("URI         : %s%n")
							.append("Method      : %s%n")
							.append("Headers     : %n%s%n%n")
							.append("Request body: %n%s%n")
							.append("==========================request end================================================")
							.toString(),
					request.getURI(),
					request.getMethod(),
					request.getHeaders(),
					new String(body, "UTF-8")));
		}
	}

	private void traceResponse(ClientHttpResponse response) throws IOException {
		if (LOGGER.isDebugEnabled()) {
			final InputStream bodyCopy = new ByteArrayInputStream(StreamUtils.copyToByteArray(response.getBody()));
			LOGGER.debug(String.format(
					new StringBuilder()
							.append("%n============================response begin==========================================%n")
							.append("Status code  : %s%n")
							.append("Status text  : %s%n")
							.append("Headers      : %n%s%n%n")
							.append("Response body: %n%s%n")
							.append("=======================response end=================================================")
							.toString(),
					response.getStatusCode(),
					response.getStatusText(),
					response.getHeaders(),
					IOUtils.toString(bodyCopy, Charset.forName("UTF-8"))));
			bodyCopy.close();
		}
	}
}
