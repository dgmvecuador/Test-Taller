/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.testtaller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author dgmv
 */
public class Sistema {
	private static EntityManagerFactory emf = null;

	public static void arrancarSistema() {
		/* Cambiar PATH de la base de datos */
		Properties p = System.getProperties();


		/* Detectar la carpeta del usuario */
		File carpetaConfiguraciones = new File(p.getProperty("user.home"), "tallerConfig");

		if ( !carpetaConfiguraciones.exists() ) {
			carpetaConfiguraciones.mkdirs();
		}

		File carpetaBaseDatos = new File(carpetaConfiguraciones, "data");

		if ( !carpetaBaseDatos.exists() ) {
			carpetaBaseDatos.mkdirs();
		}

		p.setProperty("derby.system.home", carpetaBaseDatos.getAbsolutePath());
		System.setProperties(p);

		/* Parámetros */
		Map propiedades = new HashMap();

		if ( !(new File(carpetaBaseDatos, "taller").exists()) ) {
			propiedades.put("eclipselink.ddl-generation", "create-tables");
		}

		/* Iniciar EMF */
		emf = Persistence.createEntityManagerFactory("TestTallerPU", propiedades);
	}

	/**
	 * @return the emf
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}
}
