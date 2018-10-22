package name.anonymous.heros.api.web.dto.embeddable;

import java.io.Serializable;
import java.time.LocalDate;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Version implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1548463849352564432L;

	/**
	 * Date de commande
	 */
	private LocalDate date;

	/**
	 * Numéro de version
	 */
	private String num;

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
