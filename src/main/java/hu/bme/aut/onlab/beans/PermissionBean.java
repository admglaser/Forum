package hu.bme.aut.onlab.beans;

import hu.bme.aut.onlab.model.Permission;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by N. Vilagos.
 */
@LocalBean
@Stateless
public class PermissionBean extends BaseBean<Permission> {

    @PersistenceContext
    private EntityManager entityManager;

    public PermissionBean() {
        super(Permission.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return entityManager;
    }
}