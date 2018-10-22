package name.anonymous.heros.batch.heros.model.entity.api.embeddable;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Embeddable
public class Address implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -4313647883946813803L;

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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
