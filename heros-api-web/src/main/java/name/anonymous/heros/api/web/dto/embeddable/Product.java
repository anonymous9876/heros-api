package name.anonymous.heros.api.web.dto.embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class Product {
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
