package name.anonymous.heros.api.web.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import name.anonymous.heros.api.web.dto.embeddable.Address;
import name.anonymous.heros.api.web.dto.embeddable.Person;
import name.anonymous.heros.api.web.dto.embeddable.Product;

public class ProductLineItemDto implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -5832331623428367459L;

	private UUID id;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * Numéro de la ligne de commande
	 */
	private Integer numLig;

	/**
	 * Date de livraison demandée
	 */
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dateLivSouh;

	/**
	 * Date de livraison
	 */
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dateLivAnnon;

	// hors flux
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate dateLivAccept;

	private Address delivryAddress;

	private Person buyer;

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

	public LocalDate getDateLivSouh() {
		return dateLivSouh;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Integer getNumLig() {
		return numLig;
	}

	public void setNumLig(Integer numLig) {
		this.numLig = numLig;
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

	public Person getBuyer() {
		return buyer;
	}

	public void setBuyer(Person buyer) {
		this.buyer = buyer;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
