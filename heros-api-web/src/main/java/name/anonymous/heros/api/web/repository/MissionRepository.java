package name.anonymous.heros.api.web.repository;

import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.dozer.DozerBeanMapper;
import org.hibernate.query.criteria.internal.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.model.entity.Mission;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result.JpaQueryResult;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.parser.AbstractJpaRuleParser;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.pagination.rest.SortOrder;
import name.anonymous.heros.api.web.pagination.rest.util.hibernate.HibernatePaginationService;
import name.anonymous.heros.api.web.repository.configuration.hibernate.integrator.MetadataExtractorIntegrator;

@Repository
public class MissionRepository {
	@Autowired
	private EntityManager em;

	@Autowired
	private HibernatePaginationService hpc;

	@Autowired
	private DozerBeanMapper dozerBeanMapper;

	@Autowired
	private ObjectMapper objectMapper;

	private static final String ALIAS_ORDER = "o";

	private static final String QUERY_FROM =
			" FROM " + Mission.class.getName() + " " + ALIAS_ORDER + " ";

//	public Iterable<Mission> findAll(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
//		TypedQuery<Mission> typedQuery = em
//				.createQuery(
//						"SELECT " + ALIAS_ORDER + " " +
//						QUERY_FROM
//						+ " LEFT JOIN FETCH "+ ALIAS_ORDER +".hero"
//								+ hpc.formatWhere(
//										hpc.and(
//												" exists ( from o.productLineItems pli where pli.buyer.lastName = 'PEACH' )",
//												hpc.getWhereQuery(ALIAS_ORDER, restPaginationCriteria),
//												hpc.getGlobalSearchQuery(ALIAS_ORDER,restPaginationCriteria, getSelectMissionEntityPropertiesPath())
//										)
//								)
//								+ hpc.getOrderBy(ALIAS_ORDER, restPaginationCriteria),
//						Mission.class)
//				.setFirstResult(restPaginationCriteria.getOffset())
//				.setMaxResults(restPaginationCriteria.getLimit());
//		hpc.addWhereParams(ALIAS_ORDER, restPaginationCriteria, typedQuery);
//		hpc.addGlobalSearchParams(restPaginationCriteria, typedQuery);
//		return typedQuery.getResultList();
//	}

	private CriteriaQuery<Mission> getCriteriaQueryFindAll(RestPaginationCriteria restPaginationCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Mission> cq = cb.createQuery(Mission.class);
		Root<Mission> r = cq.from(Mission.class);
		r.fetch("productLineItems", JoinType.LEFT);
		JpaQueryResult jpaQueryResult = restPaginationCriteria.getJpaQueryResult(cb, r, cq, objectMapper);
		if (jpaQueryResult != null) {
			cq.where(jpaQueryResult.getPredicate());
		}
		for (Entry<String, SortOrder> entry: restPaginationCriteria.getSortBy().getSortBys().entrySet()) {
			cq.orderBy(new OrderImpl((AbstractJpaRuleParser.getPath(r, entry.getKey())), SortOrder.ASC.equals(entry.getValue())));
		}

		return cq;
	}

	private CriteriaQuery<Long> getCriteriaQueryCountFindAll(RestPaginationCriteria restPaginationCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<Mission> r = cq.from(Mission.class);
		cq.select(cb.count(r));
		JpaQueryResult jpaQueryResult = restPaginationCriteria.getJpaQueryResult(cb, r, cq, objectMapper);
		if (jpaQueryResult != null) {
			cq.where(jpaQueryResult.getPredicate());
		}
		return cq;
	}

	public Iterable<Mission> findAll(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		CriteriaQuery<Mission> cq = getCriteriaQueryFindAll(restPaginationCriteria);
		TypedQuery<Mission> q = em.createQuery(cq)
			.setFirstResult(restPaginationCriteria.getOffset())
			.setMaxResults(restPaginationCriteria.getLimit());
		return q.getResultList();
	}

	public Long getCountBeforeFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return em.createQuery("select count(*) " + QUERY_FROM, Long.class)
				.getSingleResult();
	}

//	public Long getCountAfterFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
//		TypedQuery<Long> typedQuery = em.createQuery("select count(*)" + QUERY_FROM
//				+ hpc.formatWhere(hpc.and(hpc.getWhereQuery(ALIAS_ORDER, restPaginationCriteria),
//						hpc.getGlobalSearchQuery(ALIAS_ORDER, restPaginationCriteria, getSelectMissionEntityPropertiesPath()))),
//				Long.class);
//		hpc.addWhereParams(ALIAS_ORDER, restPaginationCriteria, typedQuery);
//		hpc.addGlobalSearchParams(restPaginationCriteria, typedQuery);
//		return typedQuery.getSingleResult();
//	}

	public Long getCountAfterFiltering(String buCode, String hero, RestPaginationCriteria restPaginationCriteria) {
		return em.createQuery(getCriteriaQueryCountFindAll(restPaginationCriteria)).getSingleResult();
	}

	public List<String> getSelectMissionEntityPropertiesPath() {
		return MetadataExtractorIntegrator.INSTANCE.getEntityPropertyPaths(Mission.class);
	}

	public void newMission(String buCode, Mission Mission) {
		em.persist(Mission);
	}

	public void deleteMission(String buCode, UUID MissionId) {
		em.remove(em.getReference(Mission.class, MissionId));
	}

	public void putMission(Mission Mission) {
		em.merge(Mission);
	}

	public void patchMission(UUID MissionId, Mission MissionPatch) {
		Mission Mission = em.find(Mission.class, MissionId);
		dozerBeanMapper.map(MissionPatch, Mission);
		em.merge(Mission);
	}
}
