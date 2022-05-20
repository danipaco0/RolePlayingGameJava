/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.isib.rpg.joueur;

import be.isib.rpg.Equipement;

/**
 *
 * @author dumit
 */
public class Voleur extends Heros{
    public Voleur(){
        this.pv = 35;
        this.defense = 5;
        this.force = 4;
        this.prec = 5;
        this.intel = 2;
        this.inventaire = new Equipement[2];
        this.recapCombats = new String[4][6];
    }
    public Voleur(Heros heros){
        inventaire = heros.inventaire;
        recapCombats = heros.recapCombats;
        pv += 5;
        force = 4;
        prec = 5;
        intel = 2;
        
    }
}
