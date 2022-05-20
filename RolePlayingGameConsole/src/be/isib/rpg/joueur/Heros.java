/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.isib.rpg.joueur;

import be.isib.rpg.Equipement;
import be.isib.rpg.Personnage;
import be.isib.rpg.ennemi.Monstre;
import be.isib.rpg.arme.ArmeMagique;
import be.isib.rpg.arme.ArmeCaC;
import be.isib.rpg.arme.ArmeDistance;
import be.isib.rpg.arme.Marteau;
import be.isib.rpg.arme.SceptreDeFeu;
import be.isib.rpg.arme.Tromblon;
import java.util.Arrays;

/**
 *
 * @author DumitrasDaniel
 */
public class Heros extends Personnage {

    public static final int ASSOMME = 1;
    public static final int TRIPLE = 2;
    public static final int BRULE = 3;

    public int force;
    public int prec;
    public int intel;
    int deg;
    public int atkport, deginf;
    Equipement[] inventaire;
    public String recapCombats[][];
    public int tempsRecharge = 0;
    public String recapCombats1[][];

    public Heros() {
        this.pv = 30;
        this.defense = 5;
        this.force = 1;
        this.prec = 1;
        this.intel = 1;
        this.inventaire = new Equipement[1];
        this.recapCombats = new String[2][6];
    }

    public Heros(Heros heros) {
        this.pv = heros.getpv();
        this.defense = 5;
        this.prec = 1;
        this.intel = 1;
        this.force = 1;
        this.inventaire = heros.getInventaire();
        this.recapCombats = heros.getRecapCombats();
    }

    public int itemBonus(int nbr) {     //ajoute le bonus en fonction du niveau d'arme
        int upgrade = 0;
        switch (nbr) {
            case 1:
                upgrade = 2;
            case 2:
                upgrade = 4;
            case 3:
                upgrade = 5;
        }

        return upgrade;
    }

    public Heros newJob(Heros heros) {            //change de job en fonction du type d'arme
        switch (getItem(0).getClass().getSuperclass().getSimpleName()) {
            case ("ArmeCaC"):
                heros = new Guerrier(heros);
                break;
            case ("ArmeDistance"):
                heros = new Voleur(heros);
                break;
            case ("ArmeMagique"):
                heros = new Mage(heros);
                break;
            default:
                break;
        }
        System.out.println("Le joueur devient un " + heros.getClass().getSimpleName() + ". Ses PV sont maintenant = " + heros.pv + "\n");
        return heros;
    }

    public int attaquerCaC(Monstre ennemi, int nbr) {
        int stat = force;
        stat += itemBonus(nbr);
        deg = (stat + ((int) ((Math.random() * 5) - 2))) - ennemi.deffatt();      //A utiliser pour déterminer le + ou - 2 de dégâts 
        if (deg < 0) {
            deg = 0;
        }
        this.deginf += deg;
        this.atkport++;
        ennemi.setpv(ennemi.getpv() - deg);
        return deg;
    }

    public int attaquerDistance(Monstre ennemi, int nbr) {
        int stat = prec;
        stat += itemBonus(nbr);
        deg = (stat + ((int) ((Math.random() * 5) - 2))) - ennemi.deffatt();       //A utiliser pour déterminer le + ou - 2 de dégâts 
        if (deg < 0) {
            deg = 0;
        }
        this.deginf += deg;
        this.atkport++;
        ennemi.setpv(ennemi.getpv() - deg);
        return deg;
    }

    public int attaquerMagie(Monstre ennemi, int nbr) {
        int stat = intel;
        stat += itemBonus(nbr);
        deg = (stat + ((int) ((Math.random() * 5) - 2))) - ennemi.deffatt();        //A utiliser pour déterminer le + ou - 2 de dégâts 
        if (deg < 0) {
            deg = 0;
        }
        this.deginf += deg;
        this.atkport++;
        ennemi.setpv(ennemi.getpv() - deg);
        return deg;
    }

    public int choixAttaque() {     //choix d'attaque en fonction du type d'arme
        int choix = 1;
        if (inventaire[0] != null) {
            for (int i = 0; i < inventaire.length; i++) {
                Equipement arme = this.getItem(i);
                if (arme instanceof ArmeCaC) {
                    choix = 1;
                } else if (arme instanceof ArmeDistance) {
                    choix = 2;
                } else if (arme instanceof ArmeMagique) {
                    choix = 3;
                }
            }
        }
        return choix;
    }

    public int attaqueSpeciale(int nrcombat) {
        //choix de l'attaque spéciale en fonction du type d'arme si 3 tours sont passés depuis la dernière attaque
        if (nrcombat > 2) {
            if (inventaire[2] != null) {
                Equipement arme = this.getItem(2);
                if (tempsRecharge == 0) {
                    if (arme instanceof Marteau) {
                        tempsRecharge = 3;
                        return ASSOMME;
                    } else if (arme instanceof Tromblon) {
                        tempsRecharge = 3;
                        return TRIPLE;
                    } else if (arme instanceof SceptreDeFeu) {
                        tempsRecharge = 3;
                        return BRULE;
                    }
                }
            }

        }
        return 0;
    }

    public int attaqueFinale(Heros heros, Monstre ennemi, int choix, int nbr) {   //attaque normale + attaque spéciale si les conditions sont remplies
        int count = 0;
        int rand;
        int nrAtkSpe = attaqueSpeciale(nbr);
        switch (choix) {
            case 1:
                deg = attaquerCaC(ennemi, nbr);
                System.out.printf("Le %s lance une attaque %-40s       Dégâts infligés =  %2d    PV monstre =    %2d%n", getClass().getSimpleName(), typeAttaque(choix), deg, (ennemi.getpv()));
                if (nrAtkSpe == ASSOMME && tempsRecharge == 3 && ennemi.tempsRecharge > 0) {  //attaque n'est pas réalisée si le minotaure exécute son attaque spéciale     
                    ennemi.atkSpeciale(heros, ennemi, nrAtkSpe);
                }
                break;
            case 2:
                deg = attaquerDistance(ennemi, nbr);
                if (nrAtkSpe == TRIPLE) {
                    rand = (int) ((Math.random() * 2) + 2);     //nombre de tirs du héros
                    while (count <= rand) {
                        deg += attaquerDistance(ennemi, nbr);
                        deginf += deg;
                        atkport++;
                        count++;
                    }
                    System.out.printf("Le %s lance une attaque spéciale           Il tire %d fois              Dégâts infligés =  %2d    PV monstre =    %2d%n", getClass().getSimpleName(), rand, deg, (ennemi.getpv()));
                } else {
                    System.out.printf("Le %s lance une attaque %-40s       Dégâts infligés =  %2d    PV monstre =    %2d%n", getClass().getSimpleName(), typeAttaque(choix), deg, (ennemi.getpv()));
                }
                break;
            case 3:
                deg = attaquerMagie(ennemi, nbr);
                System.out.printf("Le %s lance une attaque %-40s       Dégâts infligés =  %2d    PV monstre =    %2d%n", getClass().getSimpleName(), typeAttaque(choix), deg, (ennemi.getpv()));
                if (nrAtkSpe == BRULE && tempsRecharge == 3 && ennemi.tempsRecharge > 0) {   //attaque n'est pas réalisée si le minotaure exécute son attaque spéciale
                    ennemi.atkSpeciale(heros, ennemi, nrAtkSpe);
                }
                break;

        }
        return deg;
    }

    public String typeAttaque(int choix) {
        String att = null;
        switch (choix) {
            case 1:
                att = "au CaC";
                break;
            case 2:
                att = "à distance";
                break;
            case 3:
                att = "magique";
                break;
        }
        return att;
    }

    public Equipement[] getInventaire() {
        return inventaire;
    }

    public void Agrandirtableaurecap(int nrcombat) {   //retourne l'équipement placé en indice ind

        if (nrcombat != 1) {
            String recapCombats1[][];
            recapCombats1 = new String[nrcombat][6];
            int i;
           
            for (i=0; i <= nrcombat-1; i++) {
               int j=0;  
                for (j = 0; j <= 5; j++) {
                  recapCombats1[i][j] = recapCombats[i][j];

                }

            }//copier chacune des cases
            
            recapCombats = new String[nrcombat + 1][6];
            
            int u;
             for (u = 0; u <= nrcombat-1; u++) {
                int v=0;  
                for (v =0; v <= 5; v++) {
                  recapCombats[u][v] = recapCombats1[u][v];

                }

            }

            

            }
        }

    

    public void Agrandirinventaire(int nrcombat) {   //retourne l'équipement placé en indice ind
        Equipement[] inventaire1;
        if (nrcombat != 1) {
            inventaire1 = new Equipement[nrcombat - 1];

            for (int i = 0; i <= inventaire.length - 1; i++) {
                inventaire1[i] = inventaire[i];

            }//copier chacune des cases

            inventaire = new Equipement[nrcombat];
            for (int i = 0; i <= inventaire1.length - 1; i++) {
                inventaire[i] = inventaire1[i];

            }
        }

    }

    public Equipement getItem(int ind) {   //retourne l'équipement placé en indice ind
        if (ind >= inventaire.length) {
            return null;
        } else {
            return inventaire[ind];
        }
    }

    public Equipement ramasserButin(Monstre ennemi) {     //ramasse le butin et retourne une erreur si l'ennemi n'est pas mort
        Equipement butin = null;
        if (ennemi.getpv() != 0) {
            throw new IllegalArgumentException("Ennemi encore vivant");
        }
        if (ennemi.itemRecup != null) {

            for (int i = 0; i < inventaire.length; i++) {
                if (inventaire[i] == null) {
                    inventaire[i] = ennemi.itemRecup;
                    butin = inventaire[i];

                    break;
                }
            }
            System.out.println(ennemi.typeMonstre + " vaincu. Il reste au héros " + this.pv + " PV. Objet récupéré : " + butin.toString() + "\n");
        }
        return ennemi.itemRecup;
    }

    public String getRecap(int ligne, int col) {
        return recapCombats[ligne][col];
    }

    public String[][] getRecapCombats() {
        return recapCombats;
    }

    public void setRecap(int ligne, int col, String stats) {
     
        this.recapCombats[ligne][col] = stats;
    }

    public void updateTableau(int ligne, String[] stat) {
        for (int i = 0; i < stat.length; i++) {
            setRecap(ligne, i, stat[i]);
        }
    }

    public void updateTableau(int ligne, Monstre ennemi, String butin) {   //le tableau est mis à jour
        String[] stat = new String[6];
        stat[0] = ennemi.typeMonstre;
        stat[1] = String.valueOf(this.atkport);
        stat[2] = String.valueOf(this.deginf);
        stat[3] = String.valueOf(ennemi.atkrec);
        stat[4] = String.valueOf(ennemi.degrec);
        stat[5] = butin;
        updateTableau(ligne, stat);
    }

    public void tableauRecap() {   //donne le tableau final
        int taille = 0;
        System.out.println("|------------------|------------------|------------------|------------------|------------------|------------------|");
        System.out.println("| Monstre Combattu | Nb d'atk portées | Dégâts infligés  | Nb d'atk reçues  |  Dégâts reçus    |   Item ramassé   |");
        System.out.println("|------------------|------------------|------------------|------------------|------------------|------------------|");
        while ((taille != 4) && (getRecap(taille, 0) != null)) {
            taille++;
        }
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.printf("|%18s", getRecap(i, j));
            }
            System.out.println("\n|------------------|------------------|------------------|------------------|------------------|------------------|");
        }
    }
}
