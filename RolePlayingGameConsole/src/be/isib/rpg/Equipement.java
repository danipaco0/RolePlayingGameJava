/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.isib.rpg;

/**
 *
 * @author DumitrasDaniel
 */
public class Equipement {
    public static final int CAC = 1;
    public static final int DISTANCE = 2;
    public static final int MAGIQUE = 3;
    
    public static String nom;
    public String type;
    public int bonus;
    public int niveau = 0;
    
    public String getNom(){
         return nom;       
    }
  
    
    public int getNiveau(){
        return niveau;
    }
    
    public int getBonus(){
        return bonus;
    }
    
    @Override
    public String toString(){
        return nom +" : "+ getClass().getSuperclass().getSimpleName() +" de niv. "+ this.niveau;
    }
    
    public static int typeButin(){                   //générateur random d'arme
        return (int)(Math.random()*3)+1;
    }
    
}
