/*
 *  MarcaJpaController.java
 *  Copyright (C) 2012  Diego Estévez <dgmvecuador@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.testtaller.entidades.controladores;

import com.testtaller.entidades.Marca;
import com.testtaller.entidades.controladores.exceptions.NonexistentEntityException;
import com.testtaller.entidades.controladores.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author dgmv
 */
public class MarcaJpaController implements Serializable {

	public MarcaJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Marca marca) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(marca);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findMarca(marca.getIdMarca()) != null) {
				throw new PreexistingEntityException("Marca " + marca + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Marca marca) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			marca = em.merge(marca);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = marca.getIdMarca();
				if (findMarca(id) == null) {
					throw new NonexistentEntityException("The marca with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Marca marca;
			try {
				marca = em.getReference(Marca.class, id);
				marca.getIdMarca();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The marca with id " + id + " no longer exists.", enfe);
			}
			em.remove(marca);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Marca> findMarcaEntities() {
		return findMarcaEntities(true, -1, -1);
	}

	public List<Marca> findMarcaEntities(int maxResults, int firstResult) {
		return findMarcaEntities(false, maxResults, firstResult);
	}

	private List<Marca> findMarcaEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Marca.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Marca findMarca(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Marca.class, id);
		} finally {
			em.close();
		}
	}

	public int getMarcaCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Marca> rt = cq.from(Marca.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
