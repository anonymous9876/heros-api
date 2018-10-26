package name.anonymous.heros.api.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import name.anonymous.heros.api.web.controller.bean.PaginatedRequest;
import name.anonymous.heros.api.web.dto.ProductLineItemDto;
import name.anonymous.heros.api.web.pagination.rest.PaginableRestResult;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.service.ParamToRestResultsService;
import name.anonymous.heros.api.web.service.ProductLineItemService;

/**
 * {"field":"productLineItems", "operator":"exists", "value":{"field": "buyer.lastName", "operator":"{"field":"productLineItems", "operator":"exists", "value":{"field": "buyer.lastName", "operator":"equal", "value": "PEACH"}}", "value": "PEACH"}}
 * @author cduhaupas
 *
 */
@RestController
@RequestMapping("/api/business-units/{buCode}/hero/{hero}")
public class ProductLineItemController {
    @Autowired
    private ParamToRestResultsService paramToRestResultsService;

    @Autowired
    private ProductLineItemService ProductLineItemService;

    @ApiOperation(value = "Retrieves a list")
    @ApiResponse(code = 200, message = "Success of the recovery", response = PaginableRestResult.class)
    @GetMapping("mission-indirect-headers/{idMission}/mission-indirect-details")
    @ResponseBody
    public PaginableRestResult<ProductLineItemDto> listheroPaginated(
	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
	    @ApiParam(name = "hero", value = "hero", required = true) @PathVariable("hero") String hero,
	    @ApiParam(name = "idMission", value = "idMission", required = true) @PathVariable("idMission") String idMission,
	    @Valid PaginatedRequest paginatedRequest) {
	return paramToRestResultsService.getRestPaginationCriteria(
		ProductLineItemService.getSelectProductLineItemEntityPropertyPaths(),
		(RestPaginationCriteria restPaginationCriteria) -> ProductLineItemService.findAll(buCode, hero, idMission, restPaginationCriteria),
		(RestPaginationCriteria restPaginationCriteria) -> ProductLineItemService.getCountBeforeFiltering(buCode, hero, idMission, restPaginationCriteria),
		(RestPaginationCriteria restPaginationCriteria) -> ProductLineItemService.getCountAfterFiltering(buCode, hero, idMission, restPaginationCriteria),
		paginatedRequest)
		;
    }
}
