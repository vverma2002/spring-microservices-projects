package $package;

import javax.servlet.Filter;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration(proxyBeanMethods = false)
public class SleuthConfiguration {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	// Servlet Filter to add the trace id in Http Headers
	@Bean
	Filter traceIdInResponseFilter(Tracer tracer) {
		return (request, response, chain) -> {
			Span currentSpan = tracer.currentSpan();
			if (currentSpan != null) {
				HttpServletResponse resp = (HttpServletResponse) response;
				// putting trace id value in [mytraceid] response header
				resp.addHeader("mytraceid", currentSpan.context().traceId());
			}
			chain.doFilter(request, response);
		};
	}
}
