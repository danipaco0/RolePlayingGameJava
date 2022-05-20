package be.isib.rpg;

/**
 *
 * @author DumitrasDaniel
 */
public abstract class Personnage extends Equipement{
    public int pv;
    public int attaque;
    public int defense;
    public Personnage(){
        
    }
    
    public int getpv(){
        return pv;
    }
    public void setpv(int pv){   //met à jour les PV 
        this.pv = pv;
        if(this.pv<0){
            this.pv = 0;
        }
    }
    public int getdef(){
        return defense;
    }
    public int deffatt(){                  //détermine stat défense du monstre +/- 2
        int prot;
        prot = (getdef()+((int)(Math.random()*5)-2));
        if(prot < 0){
            prot = 0;
        }
        return prot;
    }

}
