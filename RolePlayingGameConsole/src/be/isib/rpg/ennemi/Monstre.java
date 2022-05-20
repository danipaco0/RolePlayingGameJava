package be.isib.rpg.ennemi;

import be.isib.rpg.Equipement;
import be.isib.rpg.joueur.Heros;
import be.isib.rpg.Personnage;
import be.isib.rpg.arme.Tromblon;
import be.isib.rpg.arme.Sceptre;
import be.isib.rpg.arme.Marteau;
import be.isib.rpg.arme.Arc;
import be.isib.rpg.arme.Epee;
import be.isib.rpg.arme.SceptreDeFeu;
import be.isib.rpg.arme.Baton;
import be.isib.rpg.arme.Arbalete;
import be.isib.rpg.arme.Glaive;

/**
 *
 * @author DumitrasDaniel
 */
public class Monstre extends Personnage{ 
    public static final int CHARGE = 2;
    
    public String typeMonstre;
    int nivButin;
    public Equipement itemRecup;
    public int toursAs = 0;
    public int toursBr = 0;
    public int atkrec, degrec;
    public int tempsRecharge = 0;
    
    
    public int atk(Heros heros, Monstre ennemi, int choix){       
        int deg;
        deg = (attaque+((int)((Math.random()*5)-2)))-heros.deffatt();  // +- 2 pour l'attaque du monstre
        if(deg < 0){
            deg = 0;
        }
        if(ennemi.typeMonstre.equals("Minotaure")){     //exécute l'attaque spéciale si minotaure
            deg = specialMinotaure(heros, ennemi, choix);
        }
        this.degrec += deg;
        this.atkrec++;
        heros.setpv(heros.getpv()-deg);
        System.out.printf("Le monstre attaque%-54s     Dégâts reçus =     %2d    PV joueur =     %2d%n", " ", deg, heros.getpv());
        if(heros.tempsRecharge > 0){     //dans le cas du minotaure, le temps de recharge de l'attaque spéciale est diminué
            heros.tempsRecharge--;
        }
        if(this.toursBr > 0){     //dans le cas d'une brulure, le monstre perd des PV après son attaque
                if(this.pv != 0 && (this.pv-2) >= (-1)){
                    ennemi.setpv(this.pv - 2);
                    System.out.println("Le monstre perd 2 PV. Il lui reste "+this.pv+" PV");
                    toursBr--;
                }
                else{
                    this.pv = 0;
                }
            }
        return deg;
    }
    public int specialMinotaure(Heros heros, Monstre ennemi, int choix){
        int deg = 0;
        int a;
        if(tempsRecharge > 0){   //n'execute pas l'attaque si le temps n'est pas 0
            tempsRecharge--;
        }
        else{     //execute l'attaque spéciale
            tempsRecharge = 3;
            System.out.println("Charge");
            if(this.toursBr > 0){     //dans le cas d'une brulure, le monstre perd des PV après son attaque
                if(this.pv != 0 && (this.pv-2) >= (-1)){
                    ennemi.setpv(this.pv - 2);
                    System.out.println("Le monstre perd 2 PV. Il lui reste "+this.pv+" PV");
                    toursBr--;
                }
                else{
                    this.pv = 0;
                }
            }
            a = heros.attaqueFinale(heros, ennemi, choix, 3);    //le personnage attaque une première fois pendant que le monstre charge 
            if(this.toursBr > 0){   //le monstre perd des PV après le tour même si il charge son attaque
                if(this.pv != 0 && (this.pv-2) >= (-1)){
                    ennemi.setpv(this.pv - 2);
                    System.out.println("Le monstre perd 2 PV. Il lui reste "+this.pv+" PV");
                    toursBr--;
                }
                else{
                    this.pv = 0;
                }
            }
            heros.deginf += a; //deuxième attaque avec les même stat que la première
            ennemi.setpv(ennemi.getpv()-a);
            if(this.pv <= 0){
                deg = 0;
            }
            else{
                deg = (int)((Math.random()*3)+9);   // randomisation de l'attaque spéciale du minotaure
            }
            System.out.printf("Le %s lance une attaque %-39s        Dégâts infligés =  %2d    PV monstre =    %2d%n", heros.getClass().getSimpleName(), heros.typeAttaque(choix), a,this.pv);
            if(this.toursBr > 0){
                if(this.pv != 0 && (this.pv-2) >= (-1)){
                    ennemi.setpv(this.pv - 2);
                    System.out.println("Le monstre perd 2 PV. Il lui reste "+this.pv+" PV");
                    toursBr--;
                }
                else{
                    this.pv = 0;
                }
            }
            if(this.pv <= 0){
                deg = 0;
            }
        }
        return deg;
    }
    
    public void atkSpeciale(Heros heros, Monstre ennemi, int choix){    
        switch(choix){
            case 1:
                this.toursAs = (int)((Math.random()*3)+1); // randomise nbr tours 
                System.out.printf("Le %2s est assomé pendant %d tours\n", ennemi.typeMonstre, toursAs);
                while(toursAs > 0){   //héros attaque pendant que monstre est assomé
                    int a = heros.attaquerCaC(ennemi, 3);
                    System.out.printf("Le %s lance une attaque %-40s       Dégâts infligés =  %2d    PV monstre =    %2d%n", heros.getClass().getSimpleName(), heros.typeAttaque(1), a,(ennemi.getpv()));
                    toursAs--;
                }
                heros.tempsRecharge--;
                break;
            case 3:
                this.toursBr = (int)((Math.random()*3)+1); // randomise nbr tours 
                System.out.printf("Le %2s brûle. Il perd 2 PV pendant %d tours\n",ennemi.typeMonstre, toursBr);
                while(this.toursBr > 0){    //toutes les attaques pendant que l'attaque spéciale est activée
                    ennemi.atk(heros, ennemi, 3);
                    int a = heros.attaquerMagie(ennemi, 3);
                    System.out.printf("Le %s lance une attaque %-40s       Dégâts infligés =  %2d    PV monstre =    %2d%n", heros.getClass().getSimpleName(), heros.typeAttaque(3), a,(ennemi.getpv()));
                }
                heros.tempsRecharge--;
                break;
            default:
                break;       
        }
    }
    
    public Equipement butin(int niv, int type){    //retourne l'item recu après avoir battu le monstre
        Equipement item = null;
        switch(niv){
                case 1:
                    switch(type){
                            case Equipement.CAC:
                                item = new Epee();
                                break;
                            case Equipement.DISTANCE:
                                item = new Arc();
                                break;
                            case Equipement.MAGIQUE:
                                item = new Baton();
                                break;
                            default:
                                throw new IllegalArgumentException("Impossible de déterminer le butin du monstre");
                    }    
                    break;
                case 2:
                    switch(type){
                            case Equipement.CAC:
                                item = new Glaive();
                                break;
                            case Equipement.DISTANCE:
                                item = new Arbalete();
                                break;
                            case Equipement.MAGIQUE:
                                item = new Sceptre();
                                break;
                            default:
                                throw new IllegalArgumentException("Impossible de déterminer le butin du monstre");
                    }
                    break;
                case 3:
                    switch(type){
                            case Equipement.CAC:
                                item = new Marteau();
                                break;
                            case Equipement.DISTANCE:
                                item = new Tromblon();
                                break;
                            case Equipement.MAGIQUE:
                                 item = new SceptreDeFeu();
                                break;
                            default:
                                throw new IllegalArgumentException("Impossible de déterminer le butin du monstre");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Impossible de déterminer le butin du monstre");
        
        }
        return item;
    }

}
