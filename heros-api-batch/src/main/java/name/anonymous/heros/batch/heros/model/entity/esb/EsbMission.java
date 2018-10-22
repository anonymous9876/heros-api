package name.anonymous.heros.batch.heros.model.entity.esb;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
public class EsbMission implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 296106964676321931L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID id;

	/**
	 * Numéro de BU
	 */
	@NaturalId
	@Column(nullable = false)
	private Integer numBu;
	/**
     * Numéro de commande
     */
    @NaturalId
    @Column(nullable = false)
    private String num;
	/**
	 * Date de commande
	 */
	private Date date;
	/**
	 * Numéro de version
	 */
	private String version;
	/**
	 * Statut de la commande
	 */
	private String etat;
	/**
	 * Date de dernière modification de la commande (n'apparait pas à  l'écran)
	 */
	private Date etatDate;
	/**
	 * Code du hero
	 */
	private String heroNum;
	/**
	 * Nom du hero
	 */
	private String heroName;
	/**
	 * Montant TTC de la commande
	 */
	private Double totalAmount;
	/**
	 * Devise
	 */
	private String currency;

	/**
	 * Date de livraison demandée
	 */
	private Date dateLivSouh;
	/**
	 * Date de livraison
	 */
	private Date dateLivAnnon;
	/**
	 * Ligne d'adresse 1 de l'adresse associée au lieu de facturation de la ligne de
	 * commande
	 */
	private String adrLibAdr001;
	/**
	 * Ligne d'adresse 2 de l'adresse associée au lieu de facturation de la ligne de
	 * commande
	 */
	private String adrLibAdr002;
	/**
	 * Ligne d'adresse 3 de l'adresse associée au lieu de facturation de la ligne de
	 * commande
	 */
	private String adrLibAdr003;
	/**
	 * Code postal de l'adresse associée au lieu de facturation de la ligne de
	 * commande
	 */
	private String adrCodPst;
	/**
	 * Ville de l'adresse associée au lieu de facturation de la ligne de commande
	 */
	private String adrLibVil;
	/**
	 * Pays de l'adresse associée au lieu de facturation de la commande
	 */
	private String adrNumPay;
	/**
	 * Nom du demandeur
	 */
	private String firstName;
	/**
	 * Prénom du demandeur
	 */
	private String lastName;
	/**
	 * Email du demandeur
	 */
	private String email;
	/**
	 * Nom d'utilisateur Oracle de du demandeur
	 */
	private String ldap;
	/**
	 * Numéro de la ligne de commande
	 */
	private Integer numLig;
	/**
	 * Code de l'article codifié
	 */
	private String numArtInt;
	/**
	 * Référence du numéro d'article fournisseur
	 */
	private String numArtFou;
	/**
	 * Libellé de l'article non traduit
	 */
	private String descriptionArt;
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

	public Integer getNumBu() {
		return numBu;
	}

	public void setNumBu(Integer numBu) {
		this.numBu = numBu;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Date getEtatDate() {
		return etatDate;
	}

	public void setEtatDate(Date etatDate) {
		this.etatDate = etatDate;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getHeroNum() {
		return heroNum;
	}

	public void setHeroNum(String heroNum) {
		this.heroNum = heroNum;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public Date getDateLivSouh() {
		return dateLivSouh;
	}

	public void setDateLivSouh(Date dateLivSouh) {
		this.dateLivSouh = dateLivSouh;
	}

	public Date getDateLivAnnon() {
		return dateLivAnnon;
	}

	public void setDateLivAnnon(Date dateLivAnnon) {
		this.dateLivAnnon = dateLivAnnon;
	}

	public String getAdrLibAdr001() {
		return adrLibAdr001;
	}

	public void setAdrLibAdr001(String adrLibAdr001) {
		this.adrLibAdr001 = adrLibAdr001;
	}

	public String getAdrLibAdr002() {
		return adrLibAdr002;
	}

	public void setAdrLibAdr002(String adrLibAdr002) {
		this.adrLibAdr002 = adrLibAdr002;
	}

	public String getAdrLibAdr003() {
		return adrLibAdr003;
	}

	public void setAdrLibAdr003(String adrLibAdr003) {
		this.adrLibAdr003 = adrLibAdr003;
	}

	public String getAdrCodPst() {
		return adrCodPst;
	}

	public void setAdrCodPst(String adrCodPst) {
		this.adrCodPst = adrCodPst;
	}

	public String getAdrLibVil() {
		return adrLibVil;
	}

	public void setAdrLibVil(String adrLibVil) {
		this.adrLibVil = adrLibVil;
	}

	public String getAdrNumPay() {
		return adrNumPay;
	}

	public void setAdrNumPay(String adrNumPay) {
		this.adrNumPay = adrNumPay;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getDemEmail() {
		return email;
	}

	public void setDemEmail(String email) {
		this.email = email;
	}

	public String getDemLdap() {
		return ldap;
	}

	public void setDemLdap(String ldap) {
		this.ldap = ldap;
	}

	public Integer getNumLig() {
		return numLig;
	}

	public void setNumLig(Integer numLig) {
		this.numLig = numLig;
	}

	public String getNumArtInt() {
		return numArtInt;
	}

	public void setNumArtInt(String numArtInt) {
		this.numArtInt = numArtInt;
	}

	public String getNumArtFou() {
		return numArtFou;
	}

	public void setNumArtFou(String numArtFou) {
		this.numArtFou = numArtFou;
	}

	public String getDescriptionArt() {
		return descriptionArt;
	}

	public void setDescriptionArt(String descriptionArt) {
		this.descriptionArt = descriptionArt;
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder()
				.append(numBu)
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
		EsbMission other = (EsbMission) obj;
		return new EqualsBuilder()
				.append(numBu, other.numBu)
				.append(num, other.num)
				.isEquals();
	}

}
