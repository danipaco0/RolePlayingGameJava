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
public class Mage extends Heros{
    public Mage(){
        this.pv = 35;
        this.defense = 5;
        this.force = 2;
        this.prec = 4;
        this.intel = 5;
        this.inventaire = new Equipement[2];
        this.recapCombats = new String[4][6];
    }
    public Mage(Heros heros){
        pv += 5;
        force = 2;
        prec = 4;
        intel = 5;
        inventaire = heros.inventaire;
        recapCombats = heros.recapCombats;
        
    }
}
