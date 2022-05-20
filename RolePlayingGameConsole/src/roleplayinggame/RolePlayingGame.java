package roleplayinggame;

import be.isib.rpg.ennemi.Demon;
import be.isib.rpg.Equipement;
import be.isib.rpg.ennemi.Gobelin;
import be.isib.rpg.joueur.Heros;
import be.isib.rpg.ennemi.Minotaure;
import be.isib.rpg.ennemi.Monstre;
import be.isib.rpg.ennemi.Orc;
import java.util.Arrays;


public class RolePlayingGame{
    public static boolean combat(Heros joueur, Monstre ennemi, int nrcombat){
        joueur.atkport = 0;    //stats du tableau mises à 0 avant chaque combat
        joueur.deginf = 0;     
        System.out.printf("%de combat. Ennemi : %s\n", nrcombat+1, ennemi.typeMonstre); 
        joueur.Agrandirinventaire(nrcombat+1);
        System.out.println(joueur.getInventaire());
        int choix = joueur.choixAttaque();     //choix d'attaque en fonction du type d'arme
        joueur.attaqueFinale(joueur, ennemi, choix, nrcombat);    //première attaque du héros
        while(ennemi.getpv() > 0){    //fight jusqu'à la mort 
            ennemi.atk(joueur, ennemi, choix);
            if(joueur.getpv() == 0){   
                System.out.println("\nLe joueur a perdu");
                joueur.updateTableau(nrcombat, ennemi, "Aucun");
                joueur.tableauRecap();
                System.exit(0);
            }
            else{
                joueur.attaqueFinale(joueur, ennemi, choix, nrcombat);
               
            }
        }
        if(ennemi.itemRecup != null){      //ramasse le butin
           joueur.Agrandirtableaurecap(nrcombat+1);
            joueur.ramasserButin(ennemi);
           
            joueur.updateTableau(nrcombat, ennemi, Equipement.nom); 
            
            
        }
        else{  
            System.out.println(ennemi.typeMonstre+" vaincu. Il reste au héros "+joueur.getpv()+" PV. Objet récupéré : Aucun \n");
            joueur.updateTableau(nrcombat, ennemi, "Aucun");
        }
        return true;
    }
    
    public static void main(String[] args) {
        int nrcombat = 0;
        boolean win;
        System.out.println("Création du héros");
        Heros joueur = new Heros();
        Monstre ennemi = new Gobelin();
        win = combat(joueur, ennemi, nrcombat);
        if(!win){
            return;
        }
        joueur = joueur.newJob(joueur);
       
        nrcombat++;
        
        ennemi = new Orc();
        win = combat(joueur, ennemi, nrcombat);
        if(!win){
            return;
        }
        nrcombat++;
        

        ennemi = new Demon();
        win = combat(joueur, ennemi, nrcombat);
        if(!win){
            return;
        }
        nrcombat++;
        
        ennemi = new Minotaure();
        win = combat(joueur, ennemi, nrcombat);
        if(!win){
            return;
        }
        nrcombat++;
        
        System.out.println("Le joueur a gagné");
        joueur.tableauRecap();

        }  
    }
