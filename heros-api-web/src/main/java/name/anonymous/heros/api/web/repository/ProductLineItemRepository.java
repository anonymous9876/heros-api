package name.anonymous.heros.api.web.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;

import name.anonymous.heros.api.web.model.entity.Mission;
import name.anonymous.heros.api.web.model.entity.ProductLineItem;
import name.anonymous.heros.api.web.pagination.query.builder.jpa.criteria.model.result.JpaQueryResult;
import name.anonymous.heros.api.web.pagination.rest.RestPaginationCriteria;
import name.anonymous.heros.api.web.pagination.rest.util.hibernate.HibernatePaginationService;
import name.anonymous.heros.api.web.repository.configuration.hibernate.integrator.MetadataExtractorIntegrator;

@Repository
public class ProductLineItemRepository {// extends CrudRepository<ProductLineItem, UUID>
	@Autowired
	private EntityManager em;

	@Autowired
	private HibernatePaginationService hpc;
	
	@Autowired
	private ObjectMapper objectMapper;

	private static final String ALIAS_ORDER = "o";
	private static final String ALIAS_PRODUCT = "p";
	private static final String QUERY_FROM = " FROM " + ProductLineItem.class.getName() + " " + ALIAS_PRODUCT + " ";
	private static final String QUERY_WHERE = ALIAS_PRODUCT + ".mission.num = :idMission ";

//	public Iterable<ProductLineItem> findAll(String buCode, String hero, UUID idMission,
//			RestPaginationCriteria restPaginationCriteria) {
//		TypedQuery<ProductLineItem> typedQuery = em
//				.createQuery("select " + ALIAS_PRODUCT
//						+ QUERY_FROM
//						+ " JOIN FETCH " + ALIAS_PRODUCT +".mission" + " " + ALIAS_ORDER + " "
//						+ hpc.formatWhere(hpc.and(QUERY_WHERE,
//								hpc.getWhereQuery(ALIAS_PRODUCT, restPaginationCriteria),
//								hpc.getGlobalSearchQuery(ALIAS_ORDER, restPaginationCriteria,
//								getSelectProductLineItemEntityPropertyPaths()))
//						)
//						+ hpc.getOrderBy(ALIAS_PRODUCT, restPaginationCriteria),
//						ProductLineItem.class);
//				hpc.addWhereParams(ALIAS_PRODUCT, restPaginationCriteria, typedQuery);
//				hpc.addGlobalSearchParams(restPaginationCriteria, typedQuery);
//				return typedQuery
//						.setParameter("idMission", idMission)
//						.setFirstResult(restPaginationCriteria.getOffset())
//						.setMaxResults(restPaginationCriteria.getLimit())
//						.getResultList();
//	}
	
	
	public Iterable<ProductLineItem> findAll(String buCode, String hero, UUID idMission,
			RestPaginationCriteria restPaginationCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProductLineItem> cq = cb.createQuery(ProductLineItem.class);
		Root<ProductLineItem> r = cq.from(ProductLineItem.class);
		r.fetch("mission", JoinType.LEFT);
		JpaQueryResult jpaQueryResult = restPaginationCriteria.getJpaQueryResult(cb, r, cq, objectMapper);
		Predicate where = cb.conjunction();
		where.getExpressions().add(cb.equal(r.get("mission"), em.getReference(Mission.class, idMission)));
		if (jpaQueryResult != null) {
			where.getExpressions().add(jpaQueryResult.getPredicate());
		}
		cq.where(where);
		
		TypedQuery<ProductLineItem> q = em.createQuery(cq)
			.setFirstResult(restPaginationCriteria.getOffset())
			.setMaxResults(restPaginationCriteria.getLimit());
		return q.getResultList();
	}

	public Long getCountBeforeFiltering(String buCode, String hero, UUID idMission,
			RestPaginationCriteria restPaginationCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductLineItem> r = cq.from(ProductLineItem.class);
		cq.select(cb.count(r));
		Predicate where = cb.conjunction();
		cq.where(where);
		where.getExpressions().add(cb.equal(r.get("mission"), em.getReference(Mission.class, idMission)));
		return em.createQuery(cq)
				.getSingleResult();
	}

//	public Long getCountAfterFiltering(String buCode, String hero, UUID idMission,
//			RestPaginationCriteria restPaginationCriteria) {
//		TypedQuery<Long> typedQuery = em.createQuery("select count(*)" + QUERY_FROM +
//				hpc.formatWhere(hpc.and(QUERY_WHERE,
//						hpc.getWhereQuery(ALIAS_PRODUCT, restPaginationCriteria),
//				hpc.getGlobalSearchQuery(ALIAS_ORDER, restPaginationCriteria,
//				getSelectProductLineItemEntityPropertyPaths()))
//		), Long.class);
//		hpc.addWhereParams(ALIAS_PRODUCT, restPaginationCriteria, typedQuery);
//		hpc.addGlobalSearchParams(restPaginationCriteria, typedQuery);
//		return typedQuery.setParameter("idMission", idMission)
//				.getSingleResult();
//	}

	public Long getCountAfterFiltering(String buCode, String hero, UUID idMission, RestPaginationCriteria restPaginationCriteria) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = cb.createQuery(Long.class);
		Root<ProductLineItem> r = cq.from(ProductLineItem.class);
		cq.select(cb.count(r));
		JpaQueryResult jpaQueryResult = restPaginationCriteria.getJpaQueryResult(cb, r, cq, objectMapper);
		Predicate where = cb.conjunction();
		cq.where(where);
		where.getExpressions().add(cb.equal(r.get("mission"), em.getReference(Mission.class, idMission)));
		if (jpaQueryResult != null) {
			where.getExpressions().add(jpaQueryResult.getPredicate());
		}
		return em.createQuery(cq).getSingleResult();
	}
	
	public List<String> getSelectProductLineItemEntityPropertyPaths() {
		return MetadataExtractorIntegrator.INSTANCE.getEntityPropertyPaths(ProductLineItem.class);
	}

	public void changeProductLineItemShippingAddress(UUID ProductLineItemId, LocalDate shippingAddress) {
		ProductLineItem ProductLineItem = em.find(ProductLineItem.class, ProductLineItemId);
		ProductLineItem.setDateLivAnnon(shippingAddress);
		em.merge(ProductLineItem);
	}
}
