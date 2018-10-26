package name.anonymous.heros.api.web.controller.mission;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import name.anonymous.heros.api.web.dto.MissionDto;
import name.anonymous.heros.api.web.service.MissionService;

@RestController
@RequestMapping("/api/business-units/{buCode}")
public class MissionController {
    @Autowired
    private MissionService missionService;

    @ApiOperation(value = "post")
    @ApiResponse(code = 201, message = "Success of the create")
    @PostMapping("mission-indirect-headers/create")
    public void postOrder(
	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
	    @RequestBody @Valid MissionDto missionDto) {
    	missionService.newMission(buCode, missionDto);
    }

    @ApiOperation(value = "put")
    @ApiResponse(code = 204, message = "Success of the update")
    @PutMapping("mission-indirect-headers/{missionId}")
    public void putOrder (
    	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
    	    @ApiParam(name = "missionId", value = "missionId", required = true) @PathVariable("missionId") String missionId,
    	    @RequestBody MissionDto mission) {
    	missionService.putMission(mission);
    }

    @ApiOperation(value = "patch")
    @ApiResponse(code = 204, message = "Success of the patch state")
    @PatchMapping("mission-indirect-headers/{missionId}")
    public void patchOrder (
    	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
    	    @ApiParam(name = "missionId", value = "missionId", required = true) @PathVariable("missionId") String missionId,
    	    @RequestBody MissionDto Mission) {
    	missionService.patchMission(missionId, Mission);
    }

    @ApiOperation(value = "delete")
    @ApiResponse(code = 204, message = "Success of the delete")
    @DeleteMapping("mission-indirect-headers/{missionId}")
    public void deleteOrder(
	    @ApiParam(name = "buCode", value = "buCode", required = true) @PathVariable("buCode") String buCode,
	    @ApiParam(name = "missionId", value = "missionId", required = true) @PathVariable("missionId") String missionId) {
    	missionService.deleteMission(buCode, missionId);
    }
}
