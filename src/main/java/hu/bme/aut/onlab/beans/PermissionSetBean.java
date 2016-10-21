package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.PermissionSet;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class PermissionSetBean extends BaseBean<PermissionSet> {

    @PersistenceContext
    private EntityManager entityManager;

    public PermissionSetBean() {
        super(PermissionSet.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}