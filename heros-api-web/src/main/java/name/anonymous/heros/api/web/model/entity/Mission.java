package name.anonymous.heros.api.web.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import name.anonymous.heros.api.web.model.entity.embeddable.MissionState;
import name.anonymous.heros.api.web.model.entity.embeddable.Money;
import name.anonymous.heros.api.web.model.entity.embeddable.Version;

@Entity
public class Mission implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -5552250017420393207L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(unique=true)
	private UUID id;

	/**
	 * Numéro de commande
	 */
	@NaturalId
	@Column(nullable = false, unique = true)
	private String num;

	@Embedded
	@AttributeOverrides(value = {
            @AttributeOverride(name = "lastModified", column = @Column(name="etat_date")),
            @AttributeOverride(name = "value", column = @Column(name="etat"))
    })
	private MissionState state;

	@Embedded
	@AttributeOverrides(value = {
            @AttributeOverride(name = "num", column = @Column(name="version")),
            @AttributeOverride(name = "date", column = @Column(name="date"))
    })
	private Version version;

	@Embedded
	@AttributeOverrides(value = {
            @AttributeOverride(name = "ttc", column = @Column(name="totalAmount")),
    })
	private Money totalAmount;

	@OneToMany(mappedBy="mission", cascade=CascadeType.ALL)
	@OrderBy("num_lig ASC")
	private List<ProductLineItem> productLineItems;



	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn//(name="legal_supplier_id")
	private Hero hero;


	public Money getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Money totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<ProductLineItem> getProductLineItems() {
		return productLineItems;
	}

	public void setProductLineItems(List<ProductLineItem> ProductLineItems) {
		this.productLineItems = ProductLineItems;
	}

	public UUID getId() {
		return id;
	}

	public String getNum() {
		return num;
	}

	public MissionState getState() {
		return state;
	}

	public void setState(MissionState state) {
		this.state = state;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public void setId(UUID id) {
		this.id = id;
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
		Mission other = (Mission) obj;
		return new EqualsBuilder()
				.append(num, other.num)
				.isEquals();
	}
}
