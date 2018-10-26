package name.anonymous.heros.api.web.controller.mission;

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
import name.anonymous.heros.api.web.dto.MissionDto;
import name.anonymous.heros.api.web.pagination.rest.PaginableRestResult;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.service.MissionService;
import name.anonymous.heros.api.web.service.ParamToRestResultsService;

@RestController
@RequestMapping("/api/business-units/{buCode}/legal-supplier/{hero}")
public class MissionByHeroController {
    @Autowired
    private ParamToRestResultsService paramToRestResultsService;

    @Autowired
    private MissionService missionService;

    @ApiOperation(value = "Retrieves a list")
    @ApiResponse(code = 200, message = "Success of the recovery", response = PaginableRestResult.class)
    @GetMapping("mission-indirect-headers")
    @ResponseBody
    public PaginableRestResult<MissionDto> listOrderPaginated(
	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
	    @ApiParam(name = "hero", value = "hero", required = true) @PathVariable("hero") String hero,
	    @Valid PaginatedRequest paginatedRequest) {
	return paramToRestResultsService.getRestPaginationCriteria(
		missionService.getSelectMissionEntityPropertyPaths(),
		(RestPaginationCriteria restPaginationCriteria) -> missionService.findAll(buCode, hero, restPaginationCriteria),
		(RestPaginationCriteria restPaginationCriteria) -> missionService.getCountBeforeFiltering(buCode, hero, restPaginationCriteria),
		(RestPaginationCriteria restPaginationCriteria) -> missionService.getCountAfterFiltering(buCode, hero, restPaginationCriteria),
		paginatedRequest);
    }
}
