package name.anonymous.heros.api.web.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import name.anonymous.heros.api.web.dto.ProductLineItemDto;
import name.anonymous.heros.api.web.model.entity.ProductLineItem;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.repository.ProductLineItemRepository;

@Service
public class ProductLineItemService {
	@Autowired
	private ProductLineItemRepository productLineItemRepository;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Transactional
	public List<ProductLineItemDto> findAll(String buCode, String hero, String idMission,
			RestPaginationCriteria restPaginationCriteria) {
		Iterable<ProductLineItem> productLineItems = findAllEntities(buCode, hero, idMission,
				restPaginationCriteria);
		List<ProductLineItemDto> result = new ArrayList<>();
		for (ProductLineItem ProductLineItem : productLineItems) {
			result.add(dozerBeanMapper.map(ProductLineItem, ProductLineItemDto.class));
		}
		return result;
	}

	@Transactional
	public Iterable<ProductLineItem> findAllEntities(String buCode, String hero, String idMission,
			RestPaginationCriteria restPaginationCriteria) {
		return productLineItemRepository.findAll(buCode, hero, UUID.fromString(idMission), restPaginationCriteria);
	}

	public Long getCountBeforeFiltering(String buCode, String hero, String idMission,
			RestPaginationCriteria restPaginationCriteria) {
		return productLineItemRepository.getCountBeforeFiltering(buCode, hero, UUID.fromString(idMission),
				restPaginationCriteria);
	}

	public Long getCountAfterFiltering(String buCode, String hero, String idMission,
			RestPaginationCriteria restPaginationCriteria) {
		return productLineItemRepository.getCountAfterFiltering(buCode, hero, UUID.fromString(idMission),
				restPaginationCriteria);
	}

	public List<String> getSelectProductLineItemEntityPropertyPaths() {
		return productLineItemRepository.getSelectProductLineItemEntityPropertyPaths();
	}
}
