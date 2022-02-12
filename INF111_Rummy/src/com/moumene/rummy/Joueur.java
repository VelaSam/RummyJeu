package com.moumene.rummy;

public class Joueur {
	public String nom;
	//La main du joueur :
	public Piece[] manne = new Piece[Constantes.LONGUEUR_MAX_MAIN];
	public int nombrePieces = 0;
}