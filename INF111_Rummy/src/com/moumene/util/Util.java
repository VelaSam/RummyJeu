package com.moumene.util;

/**
 * @author atoudeft
 *
 */
public class Util {

	/**
	 * Génére un nombre entier aléatoire situé dans un intervalle.
	 * @param min La valeur minimale de l'intervalle
	 * @param max La valeur maximale de l'intervalle
	 * @return nombre entier aléatoire entre min, inclus, et max, exclu
	 */
	public static int getNbAleatoireEntre(int min, int max) {
		return (int)(Math.random() * (max-min) + min);
	}

	/**
	 * Génére un nombre entier aléatoire situé dans un intervalle commençant à 0
	 * @param max La valeur maximale de l'intervalle
	 * @return nombre entier aléatoire entre 0, inclus, et max, exclu
	 */
	public static int getNbAleatoireEntre(int max) {
		return getNbAleatoireEntre(0, max);
	}
}
