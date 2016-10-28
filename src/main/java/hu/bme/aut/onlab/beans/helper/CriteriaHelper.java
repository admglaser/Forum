package hu.bme.aut.onlab.beans.helper;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.criteria.*;

/**
 * Helper class to manage the Criteria API variables together and initialize them by its type.
 * @param <E> The entity class to work on.
 */
public class CriteriaHelper<E> {
    // Private final variables //
    /**
     * The type (class) of the entity.
     */
    private final Class<E> entityType;
    /**
     * The type of the criteria.
     */
    private final CriteriaType criteriaType;

    // Private variables //
    /**
     * The criteria builder of the criteria.
     */
    private CriteriaBuilder criteriaBuilder;
    /**
     * The criteria query of the criteria.
     *   If the type of the criteria is not {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#SELECT}, it will be {@code null}.
     */
    private CriteriaQuery<E> criteriaQuery;
    /**
     * The criteria tuple query of the criteria (query on {@link Tuple} objects).
     *   If the type of the criteria is not {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#TUPLE_SELECT}, it will be {@code null}.
     */
    private CriteriaQuery<Tuple> criteriaTupleQuery;
    /**
     * The criteria update of the criteria.
     *   If the type of the criteria is not {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#UPDATE}, it will be {@code null}.
     */
    private CriteriaUpdate<E> criteriaUpdate;
    /**
     * The criteria delete of the criteria.
     *   If the type of the criteria is not {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#DELETE}, it will be {@code null}.
     */
    private CriteriaDelete<E> criteriaDelete;
    /**
     * The root entity of the criteria.
     */
    private Root<E> rootEntity;

    /**
     * Creates an instance of the class.
     * @param entityType    The type (class) of the entity working on.
     * @param entityManager The {@link EntityManager} to work with.
     * @param criteriaType  The type of the criteria.
     */
    public CriteriaHelper(Class<E> entityType, EntityManager entityManager, CriteriaType criteriaType){
        // Set final variables
        this.criteriaType = criteriaType;
        this.entityType = entityType;
        // Execute the common part of the different kinds of the criteria
        criteriaBuilder = entityManager.getCriteriaBuilder();

        if (this.criteriaType == CriteriaType.SELECT) {
            // Select initialization
            initializeSelect();
        } else if (this.criteriaType == CriteriaType.TUPLE_SELECT) {
            // Tuple select initialization
            initializeTupleSelect();
        } else if (this.criteriaType == CriteriaType.UPDATE){
            // Update initialization
            initializeUpdate();
        } else {
            // Delete initialization
            initializeDelete();
        }
    }

    /**
     * The type-specific part of the initialization of a select criteria.
     */
    private void initializeSelect(){
        criteriaQuery = criteriaBuilder.createQuery(entityType);
        rootEntity = criteriaQuery.from(entityType);
    }

    /**
     * The type-specific part of the initialization of a tuple select criteria (query on {@link Tuple} objects).
     */
    private void initializeTupleSelect(){
        criteriaTupleQuery = criteriaBuilder.createTupleQuery();
        rootEntity = criteriaTupleQuery.from(entityType);
    }

    /**
     * The type-specific part of the initialization of an update criteria.
     */
    private void initializeUpdate(){
        criteriaUpdate = criteriaBuilder.createCriteriaUpdate(entityType);
        rootEntity = criteriaUpdate.from(entityType);
    }

    /**
     * The type-specific part of the initialization of a delete criteria.
     */
    private void initializeDelete(){
        criteriaDelete = criteriaBuilder.createCriteriaDelete(entityType);
        rootEntity = criteriaDelete.from(entityType);
    }

    /**
     * Returns the type of the criteria.
     * @return The type of the criteria.
     */
    public CriteriaType getCriteriaType() {
        return criteriaType;
    }

    /**
     * Returns the criteria builder of the criteria.
     * @return The criteria builder of the criteria.
     */
    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    /**
     * Returns the criteria query of the criteria.
     *   Can only be used if the type of the criteria is {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#SELECT}.
     * @return The criteria query of the criteria.
     */
    public CriteriaQuery<E> getCriteriaQuery() {
        if (criteriaType == CriteriaType.SELECT) {
            return criteriaQuery;
        } else {
            throw new IllegalStateException("Cannot use this kind of criteria as the helper is the following type: " + criteriaType);
        }
    }

    /**
     * Returns the criteria tuple query of the criteria (query on {@link Tuple} objects).
     *   Can only be used if the type of the criteria is {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#SELECT}.
     * @return The criteria query of the criteria.
     */
    public CriteriaQuery<Tuple> getCriteriaTupleQuery() {
        if (criteriaType == CriteriaType.TUPLE_SELECT) {
            return criteriaTupleQuery;
        } else {
            throw new IllegalStateException("Cannot use this kind of criteria as the helper is the following type: " + criteriaType);
        }
    }

    /**
     * Returns the criteria update of the criteria.
     *   Can only be used if the type of the criteria is {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#UPDATE}.
     * @return The criteria update of the criteria.
     */
    public CriteriaUpdate<E> getCriteriaUpdate() {
        if (criteriaType == CriteriaType.UPDATE) {
            return criteriaUpdate;
        } else {
            throw new IllegalStateException("Cannot use this kind of criteria as the helper is the following type: " + criteriaType);
        }
    }

    /**
     * Returns the criteria delete of the criteria.
     *   Can only be used if the type of the criteria is {@link hu.bme.aut.onlab.beans.helper.CriteriaHelper.CriteriaType#DELETE}.
     * @return The criteria delete of the criteria.
     */
    public CriteriaDelete<E> getCriteriaDelete() {
        if (criteriaType == CriteriaType.DELETE) {
            return criteriaDelete;
        } else {
            throw new IllegalStateException("Cannot use this kind of criteria as the helper is the following type: " + criteriaType);
        }
    }

    /**
     * Returns the root entity of the criteria.
     * @return The root entity of the criteria.
     */
    public Root<E> getRootEntity() {
        return rootEntity;
    }


    /**
     * The possible types of a criteria.
     */
    public enum CriteriaType {
        /**
         * Select operation will be done.
         */
        SELECT,
        /**
         * Tuple select operation (query on {@link Tuple} objects).
         */
        TUPLE_SELECT,
        /**
         * Update operation will be done.
         */
        UPDATE,
        /**
         * Delete operation will be done.
         */
        DELETE
    }
}
