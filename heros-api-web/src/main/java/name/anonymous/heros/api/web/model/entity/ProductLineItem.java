package name.anonymous.heros.api.web.model.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

import name.anonymous.heros.api.web.model.entity.embeddable.Address;
import name.anonymous.heros.api.web.model.entity.embeddable.Product;

@Entity
public class ProductLineItem implements Serializable {
	/**
	*
	*/
	private static final long serialVersionUID = -8606783608969488496L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(unique=true)
	private UUID id;

	/**
	 * commande
	 */
	@ManyToOne(optional = false)
	@JoinColumn(nullable=false)
	private Mission mission;

	/**
	 * Numéro de la ligne de commande
	 */
	@NaturalId
	@Column(nullable = false)
	private Integer numLig;

	/**
	 * Date de livraison demandée
	 */
//	@Temporal(TemporalType.DATE)
	private LocalDate dateLivSouh;

	/**
	 * Date de livraison
	 */
//	@Temporal(TemporalType.DATE)
	private LocalDate dateLivAnnon;

	// hors flux
//	@Temporal(TemporalType.DATE)
	private LocalDate dateLivAccept;

	@Embedded
	private Address delivryAddress;

	@ManyToOne
	private Buyer buyer;

	@Embedded
	private Product product;

	/**
	 * Code de l'unité de mesure (UOM_CODE) Ex: Ea
	 */
	private String unite;
	/**
	 * Quantité commandée
	 */
	private Integer qte;
	/**
	 * Prix unitaire
	 */
	private Double pu;
	/**
	 * Montant de la ligne
	 */
	private Double mntLig;
	/**
	 * Type de ligne (Montant ou QTE)
	 */
	private String typeLig;

	private Integer qteRcpt;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

	public Integer getNumLig() {
		return numLig;
	}

	public void setNumLig(Integer numLig) {
		this.numLig = numLig;
	}

	public LocalDate getDateLivSouh() {
		return dateLivSouh;
	}

	public void setDateLivSouh(LocalDate dateLivSouh) {
		this.dateLivSouh = dateLivSouh;
	}

	public LocalDate getDateLivAnnon() {
		return dateLivAnnon;
	}

	public void setDateLivAnnon(LocalDate dateLivAnnon) {
		this.dateLivAnnon = dateLivAnnon;
	}

	public LocalDate getDateLivAccept() {
		return dateLivAccept;
	}

	public void setDateLivAccept(LocalDate dateLivAccept) {
		this.dateLivAccept = dateLivAccept;
	}

	public Address getDelivryAddress() {
		return delivryAddress;
	}

	public void setDelivryAddress(Address delivryAddress) {
		this.delivryAddress = delivryAddress;
	}

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public Integer getQte() {
		return qte;
	}

	public void setQte(Integer qte) {
		this.qte = qte;
	}

	public Double getPu() {
		return pu;
	}

	public void setPu(Double pu) {
		this.pu = pu;
	}

	public Double getMntLig() {
		return mntLig;
	}

	public void setMntLig(Double mntLig) {
		this.mntLig = mntLig;
	}

	public String getTypeLig() {
		return typeLig;
	}

	public void setTypeLig(String typeLig) {
		this.typeLig = typeLig;
	}

	public Integer getQteRcpt() {
		return qteRcpt;
	}

	public void setQteRcpt(Integer qteRcpt) {
		this.qteRcpt = qteRcpt;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
