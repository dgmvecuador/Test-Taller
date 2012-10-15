/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testtaller.entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dgmv
 */
@Entity
@Table(name = "CARRO")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "Carro.findAll", query = "SELECT c FROM Carro c"),
	@NamedQuery(name = "Carro.findByIdCarro", query = "SELECT c FROM Carro c WHERE c.idCarro = :idCarro"),
	@NamedQuery(name = "Carro.findByPlaca", query = "SELECT c FROM Carro c WHERE c.placa = :placa"),
    @NamedQuery(name = "Carro.findByMarca", query = "SELECT c FROM Carro c WHERE c.marca = :marca")})
public class Carro implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
    @Basic(optional = false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ID_CARRO")
	private Integer idCarro;
	@Basic(optional = false)
    @Column(name = "PLACA")
	private String placa;
	@ManyToOne
    @JoinColumn(name = "ID_MARCA", nullable=false)
	private Marca marca;

	public Carro() {
	}

	public Carro(Integer idCarro) {
		this.idCarro = idCarro;
	}

	public Carro(Integer idCarro, String placa, Marca Marca) {
		this.idCarro = idCarro;
		this.placa = placa;
		this.marca = Marca;
	}

	public Integer getIdCarro() {
		return idCarro;
	}

	public void setIdCarro(Integer idCarro) {
		this.idCarro = idCarro;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca Marca) {
		this.marca = Marca;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (idCarro != null ? idCarro.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof Carro)) {
			return false;
		}
		Carro other = (Carro) object;
		if ((this.idCarro == null && other.idCarro != null) || (this.idCarro != null && !this.idCarro.equals(other.idCarro))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "com.testtaller.entidades.Carro[ idCarro=" + idCarro + " ]";
	}
	
}
