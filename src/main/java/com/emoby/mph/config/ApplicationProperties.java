package com.emoby.mph.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Emoby MPH.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
	
    public String emobyMPHServiceURL;
    public Integer textCleanTimeout;
    public Integer emobyMatchingTimeout;

    public String mphDynamicsServiceURL;

    public String proxyHost;
    public Integer proxyPort;

    public String from;
    public String baseUrl;

	public Integer getTextCleanTimeout() {
		return textCleanTimeout;
	}

	public void setTextCleanTimeout(Integer textCleanTimeout) {
		this.textCleanTimeout = textCleanTimeout;
	}


	public Integer getEmobyMatchingTimeout() {
		return emobyMatchingTimeout;
	}

	public void setEmobyMatchingTimeout(Integer emobyMatchingTimeout) {
		this.emobyMatchingTimeout = emobyMatchingTimeout;
	}

	public String getMphDynamicsServiceURL() {
		return mphDynamicsServiceURL;
	}

	public void setMphDynamicsServiceURL(String mphDynamicsServiceURL) {
		this.mphDynamicsServiceURL = mphDynamicsServiceURL;
	}

	public String getEmobyMPHServiceURL() {
		return emobyMPHServiceURL;
	}

	public void setEmobyMPHServiceURL(String emobyMPHServiceURL) {
		this.emobyMPHServiceURL = emobyMPHServiceURL;
	}

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}


	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}


}
