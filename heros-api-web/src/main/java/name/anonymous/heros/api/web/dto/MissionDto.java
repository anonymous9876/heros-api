package name.anonymous.heros.api.web.dto;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import name.anonymous.heros.api.web.dto.embeddable.Money;
import name.anonymous.heros.api.web.dto.embeddable.Version;
import name.anonymous.heros.api.web.model.entity.embeddable.MissionState;

public class MissionDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2384048045358591008L;

	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Numéro de mission
	 */

	private String num;

	private Version version;

	private Money totalAmount;

	private MissionState state;

	private HeroDto hero;

	public HeroDto gethero() {
		return hero;
	}

	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void sethero(HeroDto hero) {
		this.hero = hero;
	}

	public MissionState getState() {
		return state;
	}

	public void setState(MissionState state) {
		this.state = state;
	}

	public Version getVersion() {
		return version;
	}

	public void setVersion(Version version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(num)
				.toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MissionDto other = (MissionDto) obj;
		return new EqualsBuilder()
				.append(num, other.num)
				.isEquals();
	}
}
