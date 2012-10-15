/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testtaller.entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dgmv
 */
@Entity
@Table(name = "MARCA")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Marca.findAll", query = "SELECT m FROM Marca m"),
	@NamedQuery(name = "Marca.findByIdMarca", query = "SELECT m FROM Marca m WHERE m.idMarca = :idMarca"),
	@NamedQuery(name = "Marca.findByNombre", query = "SELECT m FROM Marca m WHERE m.nombre = :nombre")})
public class Marca implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_MARCA")
	private Integer idMarca;
	@Basic(optional = false)
    @Column(name = "NOMBRE")
	private String nombre;
	@Basic(fetch= FetchType.LAZY)
	@OneToMany(mappedBy="CARRO",cascade= CascadeType.ALL)
	private Collection<Carro> carros;

	public Marca() {
	}

	public Marca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public Marca(Integer idMarca, String nombre) {
		this.idMarca = idMarca;
		this.nombre = nombre;
	}

	public Integer getIdMarca() {
		return idMarca;
	}

	public void setIdMarca(Integer idMarca) {
		this.idMarca = idMarca;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idMarca != null ? idMarca.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Marca)) {
			return false;
		}
		Marca other = (Marca) object;
		if ((this.idMarca == null && other.idMarca != null) || (this.idMarca != null && !this.idMarca.equals(other.idMarca))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return nombre;
	}

	/**
	 * @return the carros
	 */
	public Collection<Carro> getCarros() {
		return carros;
	}

	/**
	 * @param carros the carros to set
	 */
	public void setCarros(Collection<Carro> carros) {
		this.carros = carros;
	}
	
}
