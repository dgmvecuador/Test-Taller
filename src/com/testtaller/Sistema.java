/*
 *  Sistema.java
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
