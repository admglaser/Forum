package hu.bme.aut.onlab.bean.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

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

    /**
     * Find entities that have equality with the given value on the given field.
     *   Can be used for join field condition.
     * @param field     The field to check the equality on. Using metamodel class's field is recommend.
     * @param value     The value to equal with.
     * @return          The entity with the given equality.
     */
    public List<E> findEntitiesByEquality(SingularAttribute<E, ? extends Object> field, Object value){
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<E> query = builder.createQuery(entityType);
        Root<E> entityRoot = query.from(entityType);

        query.where(builder.equal(entityRoot.get(field) , value));

        query.select(entityRoot);

        return em.createQuery(query).getResultList();
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
    	CriteriaBuilder builder = em.getCriteriaBuilder();
    	CriteriaQuery<E> query = builder.createQuery(entityType);
        Root<E> entityRoot = query.from(entityType);

        query.select(entityRoot);

        return em.createQuery(query).getResultList();
    }
    
    public void add(E entity) {
    	em.persist(entity);
    }
    
    public E merge(E entity) {
    	return em.merge(entity);
    }

    public void remove(E entity) {
        // Merge is necessary to make the entity attached.
        em.remove( em.merge(entity) );
    }

    public void flush() {
        em.flush();
    }
    
}
