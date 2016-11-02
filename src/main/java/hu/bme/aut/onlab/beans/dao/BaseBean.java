package hu.bme.aut.onlab.beans.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;

/**
 * The base class for entity beans.
 * @param <E> The entity class to work on.
 */
public abstract class BaseBean<E> {

    protected Class<E> entityType;
    
    @PersistenceContext
	private EntityManager em;

    public BaseBean(Class<E> entityType) {
        super();
		this.entityType = entityType;
    }

    public CriteriaHelper<E> createQueryHelper(){
        return new CriteriaHelper<E>(entityType, em, CriteriaHelper.CriteriaType.SELECT);
    }

    public List<E> executeQuery(CriteriaHelper<E> criteriaHelper) {
        return em.createQuery(criteriaHelper.getCriteriaQuery()).getResultList();
    }

    public CriteriaHelper<E> createTupleQueryHelper(){
        return new CriteriaHelper<>(entityType, em, CriteriaHelper.CriteriaType.TUPLE_SELECT);
    }

    public List<Tuple> executeTupleQuery(CriteriaHelper<E> criteriaHelper) {
        return em.createQuery(criteriaHelper.getCriteriaTupleQuery()).getResultList();
    }

    /**
     * Find entities that have equality with the given value on the given field.
     *   Can be used for join field condition.
     * @param field     The field to check the equality on. Using metamodel class's field is recommend.
     * @param value     The value to equal with.
     * @return          The entity with the given equality.
     */
    public List<E> findEntitiesByEquality(SingularAttribute<E, ? extends Object> field, Object value){
        // Initialize components
        CriteriaHelper<E> criteriaHelper = new CriteriaHelper<>(entityType, em, CriteriaHelper.CriteriaType.SELECT);
        CriteriaQuery<E> criteria = criteriaHelper.getCriteriaQuery();
        CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();
        Root<E> rootEntity = criteriaHelper.getRootEntity();

        // Set the appropriate select (the whole entity)
        criteria.select(criteriaHelper.getRootEntity());

        // Set the where term
        criteria.where( criteriaBuilder.equal(rootEntity.get( field ) , value) );

        // Execute the select and return with the result
        return em.createQuery(criteria).getResultList();
    }

    /**
     * Find entity with the given ID.
     * @param id        The value of the ID.
     * @return          The entity with the given ID.
     */
    public E findEntityById(Object id) {
        return em.find(entityType, id);
    }

    public List<E> findAllEntity() {
        // Initialize components
        CriteriaHelper<E> criteriaHelper = new CriteriaHelper<>(entityType, em, CriteriaHelper.CriteriaType.SELECT);
        CriteriaQuery<E> criteria = criteriaHelper.getCriteriaQuery();

        // Set the appropriate select (the whole entity)
        criteria.select( criteriaHelper.getRootEntity() );

        // Execute the select and return with the result
        return em.createQuery(criteria).getResultList();
    }
}
