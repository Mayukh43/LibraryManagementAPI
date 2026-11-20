package org.mckv.librarymanagement.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class UniversalRepository {
	
	 @PersistenceContext
	    private EntityManager entityManager;

	    // Find by ID for any entity
	    public <T> T findById(Class<T> entityClass, Object id) {
	        return entityManager.find(entityClass, id);
	    }

	    // Find all records for any entity
	    public <T> List<T> findAll(Class<T> entityClass) {
	        return entityManager.createQuery(
	                "FROM " + entityClass.getSimpleName(), entityClass)
	                .getResultList();
	    }

	    // Save or update entity
	    public <T> T save(T entity) {
	        return entityManager.merge(entity);
	    }

	    // Delete entity
	    public <T> void delete(T entity) {
	        if (entity != null) {
	            T managedEntity = entityManager.contains(entity)
	                    ? entity
	                    : entityManager.merge(entity);

	            entityManager.remove(managedEntity);
	        }
	    }

	    // Delete by ID for any entity
	    public <T> void deleteById(Class<T> entityClass, Object id) {
	        T entity = findById(entityClass, id);

	        if (entity != null) {
	            entityManager.remove(entity);
	        }
	    }

}
