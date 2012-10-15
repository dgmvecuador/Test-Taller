/*
 *  CarroJpaController.java
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

import com.testtaller.entidades.Carro;
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
public class CarroJpaController implements Serializable {

	public CarroJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}
	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Carro carro) throws PreexistingEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(carro);
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (findCarro(carro.getIdCarro()) != null) {
				throw new PreexistingEntityException("Carro " + carro + " already exists.", ex);
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void edit(Carro carro) throws NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			carro = em.merge(carro);
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = carro.getIdCarro();
				if (findCarro(id) == null) {
					throw new NonexistentEntityException("The carro with id " + id + " no longer exists.");
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
			Carro carro;
			try {
				carro = em.getReference(Carro.class, id);
				carro.getIdCarro();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The carro with id " + id + " no longer exists.", enfe);
			}
			em.remove(carro);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Carro> findCarroEntities() {
		return findCarroEntities(true, -1, -1);
	}

	public List<Carro> findCarroEntities(int maxResults, int firstResult) {
		return findCarroEntities(false, maxResults, firstResult);
	}

	private List<Carro> findCarroEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Carro.class));
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

	public Carro findCarro(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Carro.class, id);
		} finally {
			em.close();
		}
	}

	public int getCarroCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Carro> rt = cq.from(Carro.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
	
}
