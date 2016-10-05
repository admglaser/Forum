package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.beans.helper.CriteriaHelper;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import java.util.List;

/**
 * The base class for entity beans.
 * @param <E> The entity class to work on.
 */
public abstract class BaseBean<E> {

    private final Class<E> entityType;

    public BaseBean(Class<E> entityType) {
        super();
        this.entityType = entityType;
    }

    protected abstract EntityManager getEntityManager();

    public CriteriaHelper<E> createQueryHelper(){
        return new CriteriaHelper<>(entityType, getEntityManager(), CriteriaHelper.CriteriaType.SELECT);
    }

    public List<E> executeQuery(CriteriaHelper<E> criteriaHelper) {
        return getEntityManager().createQuery(criteriaHelper.getCriteriaQuery()).getResultList();
    }

    public CriteriaHelper<E> createTupleQueryHelper(){
        return new CriteriaHelper<>(entityType, getEntityManager(), CriteriaHelper.CriteriaType.TUPLE_SELECT);
    }

    public List<Tuple> executeTupleQuery(CriteriaHelper<E> criteriaHelper) {
        return getEntityManager().createQuery(criteriaHelper.getCriteriaTupleQuery()).getResultList();
    }

    public E findEntities(SingularAttribute<E, ? extends Object> field, Object value){
        EntityManager entityManager = getEntityManager();

        // Initialize components
        CriteriaHelper<E> criteriaHelper = new CriteriaHelper<>(entityType, entityManager, CriteriaHelper.CriteriaType.SELECT);
        CriteriaQuery<E> criteria = criteriaHelper.getCriteriaQuery();
        CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();
        Root<E> rootEntity = criteriaHelper.getRootEntity();

        // Set the appropriate select (the whole entity)
        criteria.select(criteriaHelper.getRootEntity());

        // Set the where term
        criteria.where( criteriaBuilder.equal(rootEntity.get( field ) , value) );

        // Execute the select and return with the result
        return entityManager.createQuery(criteria).getSingleResult();
    }

    /**
     * Find an entity with the given ID.
     * @param field     The field of the ID. Using metamodel class's field is recommend
     * @param id        The value of the ID.
     * @return          The entity with the given ID.
     */
    public E findEntityById(SingularAttribute<E, ? extends Object> field, Object id){
        EntityManager entityManager = getEntityManager();

        // Initialize components
        CriteriaHelper<E> criteriaHelper = new CriteriaHelper<>(entityType, entityManager, CriteriaHelper.CriteriaType.SELECT);
        CriteriaQuery<E> criteria = criteriaHelper.getCriteriaQuery();
        CriteriaBuilder criteriaBuilder = criteriaHelper.getCriteriaBuilder();
        Root<E> rootEntity = criteriaHelper.getRootEntity();

        // Set the appropriate select (the whole entity)
        criteria.select(criteriaHelper.getRootEntity());

        // Set the where term
        criteria.where( criteriaBuilder.equal(rootEntity.get( field ) , id) );

        // Execute the select and return with the result
        return entityManager.createQuery(criteria).getSingleResult();
    }

    public List<E> findAllEntity() {
        EntityManager entityManager = getEntityManager();

        // Initialize components
        CriteriaHelper<E> criteriaHelper = new CriteriaHelper<>(entityType, entityManager, CriteriaHelper.CriteriaType.SELECT);
        CriteriaQuery<E> criteria = criteriaHelper.getCriteriaQuery();

        // Set the appropriate select (the whole entity)
        criteria.select( criteriaHelper.getRootEntity() );

        // Execute the select and return with the result
        return entityManager.createQuery(criteria).getResultList();
    }
}
