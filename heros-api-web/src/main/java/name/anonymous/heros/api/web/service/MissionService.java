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
	private MissionRepository MissionRepository;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	public Iterable<MissionDto> findAll(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		Iterable<Mission> Missions = findAllEntities(buCode, hero, restPaginationCriteria);
		List<MissionDto> result = new ArrayList<>();
		for (Mission Mission : Missions) {
			result.add(dozerBeanMapper.map(Mission, MissionDto.class));
		}
		return result;
	}

	@Transactional
	public Iterable<Mission> findAllEntities(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return MissionRepository.findAll(buCode, hero, restPaginationCriteria);
	}

	@Transactional
	public Long getCountBeforeFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return MissionRepository.getCountBeforeFiltering(buCode, hero, restPaginationCriteria);
	}

	@Transactional
	public Long getCountAfterFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return MissionRepository.getCountAfterFiltering(buCode, hero, restPaginationCriteria);
	}

	public List<String> getSelectMissionEntityPropertyPaths() {
		return MissionRepository.getSelectMissionEntityPropertiesPath();
	}

	@Transactional
	public void newOrder(String buCode, MissionDto MissionDto) {
		Mission Mission = dozerBeanMapper.map(MissionDto, Mission.class);
		MissionRepository.newOrder(buCode, Mission);
	}

	@Transactional
	public void deleteOrder(String buCode, String orderId) {
		MissionRepository.deleteOrder(buCode, UUID.fromString(orderId));
	}

	@Transactional
	public void patchOrder(String MissionId, MissionDto MissionDtoPatch) {
		Mission Mission = dozerBeanMapper.map(MissionDtoPatch, Mission.class);
		MissionRepository.patchOrder(UUID.fromString(MissionId), Mission);
	}

	@Transactional
	public void putOrder(Mission Mission) {
		MissionRepository.putOrder(Mission);
	}
}
