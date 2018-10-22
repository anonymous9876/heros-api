package name.anonymous.heros.batch.heros.model.entity.api.embeddable;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
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
	@Temporal(TemporalType.DATE)
	private Date lastModified;

	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
}
