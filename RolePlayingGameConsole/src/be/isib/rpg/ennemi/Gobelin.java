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
public class Gobelin extends Monstre{
    
    public Gobelin() {
        typeMonstre = "Gobelin";
        pv = 5;
        defense = 0;
        attaque = 2;
        nivButin = 1;
        itemRecup = butin(nivButin,Equipement.typeButin());
    } 
}
