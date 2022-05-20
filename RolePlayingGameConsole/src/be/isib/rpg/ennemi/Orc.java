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
public class Orc extends Monstre{
    
    public Orc() {
        typeMonstre = "Orc";
        pv = 10;
        defense = 2;
        attaque = 3;
        nivButin = 2;
        itemRecup = butin(nivButin,Equipement.typeButin());
    } 
}
