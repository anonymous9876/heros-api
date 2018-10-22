package name.anonymous.heros.api.web.dto.embeddable;

import java.io.Serializable;
import java.time.LocalDate;

public class MissionState implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4955205605680549314L;

	/**
	 * Statut de la mission
	 */
	private String value;
	/**
	 * Date de dernière modification de la mission
	 */
	private LocalDate lastModified;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public LocalDate getLastModified() {
		return lastModified;
	}
	public void setLastModified(LocalDate lastModified) {
		this.lastModified = lastModified;
	}
}
