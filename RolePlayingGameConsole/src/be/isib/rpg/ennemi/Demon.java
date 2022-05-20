/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.isib.rpg.ennemi;

import be.isib.rpg.Equipement;

/**
 *
 * @author dumit
 */
public class Demon extends Monstre{
    
    public Demon() {
        typeMonstre = "Demon";
        pv = 20;
        defense = 5;
        attaque = 5;
        nivButin = 3;
        itemRecup = butin(nivButin,Equipement.typeButin());
    } 
}
