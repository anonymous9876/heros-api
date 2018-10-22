package name.anonymous.heros.api.web.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
public class Buyer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -9076366455190200403L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(unique = true)
	private UUID id;

	@NaturalId
	private String ldap;
	private String lastName;
	private String firstName;
	private String email;

	@OneToMany
	private List<ProductLineItem> ProductLineItems ;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public List<ProductLineItem> getProductLineItems() {
		return ProductLineItems;
	}

	public void setProductLineItems(List<ProductLineItem> productLineItems) {
		ProductLineItems = productLineItems;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLdap() {
		return ldap;
	}

	public void setLdap(String ldap) {
		this.ldap = ldap;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
