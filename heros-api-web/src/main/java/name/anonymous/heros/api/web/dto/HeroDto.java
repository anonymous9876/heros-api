package name.anonymous.heros.api.web.dto;

import java.io.Serializable;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class HeroDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 6922094392309060027L;

	private UUID id;

	private String num;

	private String name;

	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}

	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
