package com.moumene.rummy;

public class Constantes {

	//Maximum de 40 combinaisons sur la table :
	public static final int MAX_COMBINAISONS = 40; 
	
	//Une combinaison ne peut dépasser 13 pièces :
	public static final int LONGUEUR_MAX_COMBINAISON = 13; 
	
	//106 pièces :
	public static final int NOMBRE_TOTAL_PIECES = 106;
	
	//Une main ne peut dépasser 106 pièces :
	public static final int LONGUEUR_MAX_MAIN = 106;
	
	public static final int UN = 1;
	public static final int DEUX = 2;
	public static final int TROIS = 3;
	public static final int QUATRE = 4;
	public static final int CINQ = 5;
	public static final int SIX = 6;
	public static final int SEPT = 7;
	public static final int HUIT = 8;
	public static final int NEUF = 9;
	public static final int DIX = 10;
	public static final int ONZE = 11;
	public static final int DOUZE = 12;
	public static final int TREIZE = 13;	
	
	public static final int VINGT_CINQ = 25;//valeur du joker

	public static final char ROUGE  = 'R';
	public static final char VERT  = 'V';
	public static final char BLEU  = 'B';
	public static final char JAUNE  = 'J';
	public static final char NOIR  = 'N'; //utilisée pour représenter un joker
	
	//Les 13 numéros des pièces :
	public static final int[] NUMEROS = {UN,DEUX,TROIS,QUATRE,CINQ,SIX,SEPT,
			HUIT,NEUF,DIX,ONZE,DOUZE,TREIZE};
	
	//Les 4 couleurs des pièces :
	public static final char[] COULEURS = {ROUGE,VERT,BLEU,JAUNE};

	public static final int TAILLE_MANNE_DEPART = 14; 	
}