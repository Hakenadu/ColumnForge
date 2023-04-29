package com.github.hakenadu.columnforge.filter;

import java.io.IOException;

import org.springframework.http.HttpHeaders;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public final class OpenAiApiKeyFilter implements Filter {

	private static final String BEARER_PREFIX = "Bearer ";

	private static final ThreadLocal<String> API_KEY_THREAD_LOCAL = new ThreadLocal<>();

	public static String getApiKey() {
		return API_KEY_THREAD_LOCAL.get();
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		final String authorizationHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
		if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
			final String apiKey = authorizationHeader.substring(BEARER_PREFIX.length());
			API_KEY_THREAD_LOCAL.set(apiKey);
		}

		try {
			chain.doFilter(request, response);
		} finally {
			API_KEY_THREAD_LOCAL.remove();
		}
	}
}