package name.anonymous.heros.api.web.dto.embeddable;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Money implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -148487747412915728L;

	/**
	 * Montant TTC
	 */
	private Double ttc;
	/**
	 * Devise
	 */
	private String currency;

	public Double getTtc() {
		return ttc;
	}

	public void setTtc(Double ttc) {
		this.ttc = ttc;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
