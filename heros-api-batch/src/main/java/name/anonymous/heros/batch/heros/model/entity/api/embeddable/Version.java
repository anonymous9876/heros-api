package name.anonymous.heros.batch.heros.model.entity.api.embeddable;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Version implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1548463849352564432L;

	/**
	 * Date de commande
	 */
	@Temporal(TemporalType.DATE)
	private Date date;

	/**
	 * Numéro de version
	 */
	private String num;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
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
