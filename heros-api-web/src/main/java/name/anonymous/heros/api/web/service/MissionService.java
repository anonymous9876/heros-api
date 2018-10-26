package name.anonymous.heros.api.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import name.anonymous.heros.api.web.dto.MissionDto;
import name.anonymous.heros.api.web.model.entity.Mission;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.repository.MissionRepository;

@Service
public class MissionService {
	@Autowired
	private MissionRepository missionRepository;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Transactional
	public Iterable<MissionDto> findAll(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		Iterable<Mission> missions = findAllEntities(buCode, hero, restPaginationCriteria);
		List<MissionDto> result = new ArrayList<>();
		for (Mission Mission : missions) {
			result.add(dozerBeanMapper.map(Mission, MissionDto.class));
		}
		return result;
	}

	@Transactional
	public Iterable<Mission> findAllEntities(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return missionRepository.findAll(buCode, hero, restPaginationCriteria);
	}

	@Transactional
	public Long getCountBeforeFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return missionRepository.getCountBeforeFiltering(buCode, hero, restPaginationCriteria);
	}

	@Transactional
	public Long getCountAfterFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return missionRepository.getCountAfterFiltering(buCode, hero, restPaginationCriteria);
	}

	public List<String> getSelectMissionEntityPropertyPaths() {
		return missionRepository.getSelectMissionEntityPropertiesPath();
	}

	@Transactional
	public void newMission(String buCode, MissionDto missionDto) {
		Mission mission = dozerBeanMapper.map(missionDto, Mission.class);
		missionRepository.newMission(buCode, mission);
	}

	@Transactional
	public void deleteMission(String buCode, String orderId) {
		missionRepository.deleteMission(buCode, UUID.fromString(orderId));
	}

	@Transactional
	public void patchMission(String missionId, MissionDto missionDtoPatch) {
		Mission mission = dozerBeanMapper.map(missionDtoPatch, Mission.class);
		missionRepository.patchMission(UUID.fromString(missionId), mission);
	}

	@Transactional
	public void putMission(Mission mission) {
		missionRepository.putMission(mission);
	}
}
