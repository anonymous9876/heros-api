package name.anonymous.heros.api.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import name.anonymous.heros.api.web.controller.bean.PaginatedRequest;
import name.anonymous.heros.api.web.pagination.rest.PaginableRestResult;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.pagination.rest.facet.Facet;

@Service
public class ParamToRestResultsService {
	private ParamToRestResultsService() {
	}

	/**
	 * @param resultMappings                         map pour faire le mapping entre
	 *                                               les pathProperties du json
	 *                                               envoyé au front avec celui de
	 *                                               l'API pour le tri des colonnes
	 * @param getModelListWithRestPaginationCriteria
	 * @param paginatedRequest                       bean contenant les critères de
	 *                                               recherche
	 * @return objet contenant la liste des données demandés avec des informations
	 *         supplèmentaires
	 * @throws IOException
	 */
	public <T> PaginableRestResult<T> getRestPaginationCriteria(
			List<String> propertyPaths,
			Function<RestPaginationCriteria, List<T>> getModelListWithRestPaginationCriteria,
			Function<RestPaginationCriteria, Long> getCountBeforeFiltering,
			Function<RestPaginationCriteria, Long> getCountAfterFiltering,
			PaginatedRequest paginatedRequest) {
		RestPaginationCriteria restPaginationCriteria = new RestPaginationCriteria(paginatedRequest);
		List<T> modelList = getModelListWithRestPaginationCriteria.apply(restPaginationCriteria);

		PaginableRestResult<T> restResult = new PaginableRestResult<>();
		restResult.setData(modelList);

		restResult.setRecordsTotal(getCountBeforeFiltering.apply(restPaginationCriteria));
		if (restPaginationCriteria.hasFiltering()) {
			restResult.setRecordsFiltered(getCountAfterFiltering.apply(restPaginationCriteria));
		} else {
			restResult.setRecordsFiltered(restResult.getRecordsTotal());
		}

		Facet facet = new Facet();
		facet.setPropertyPaths(new ArrayList<String>(propertyPaths));
		restResult.setFacet(facet);
		return restResult;
	}
}
