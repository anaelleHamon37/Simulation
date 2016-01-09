package simulation;

import java.util.ArrayList;
import java.util.List;

public class Simulateur {
	
	/*Variables*/
	int n, ntmax, dateSimu, last_dateSimu ;
	// infos li�es aux appels
	int nt, nt_dispo ;
	List<Integer> qt ;
	// infos li�es aux courriels
	int nc, nc_dispo ;
	List<Integer> qc ;
	
	/*Indicateurs*/
	int a, c ;
	// taux d'occupation
	int o_tc, o_pt ;
	// temps d'attente/r�ponse
	int att, rep ;
	
	/* Variables li�es au simulateur */
	List<Evenement> evenements ;
	boolean isFinished;
	
	/*Variables li�es au r�sultat*/
	int c_rep;
	int courriels_non_traites;
	double tps_attente_moyen;
	double tps_reponse_moyen;
	double taux_occupation_teleconseillers;
	double taux_occupation_postes;
	
	public Simulateur(int n, int nt, int ntmax)	{
		this.n = n;
		this.nt = nt;
		this.ntmax = ntmax;
		evenements = new ArrayList<Evenement>();
		isFinished = false;
	}
	
	public void initSimulateur()	{
		nt_dispo = nt;
		nc = n - nt;
		nc_dispo = nc;
		qt = new ArrayList<Integer>();
		qc = new ArrayList<Integer>();
		a = 0;
		c = 0;
		o_tc = 0;
		o_pt = 0;
		att = 0;
		rep = 0;
		courriels_non_traites = 0;
		tps_attente_moyen = 0;
		tps_reponse_moyen = 0;
		taux_occupation_teleconseillers = 0;
		taux_occupation_postes = 0;
	}
	
	public boolean isFinished()	{
		return isFinished;
	}

	public Simulateur doNextEvent() {
		Evenement next = evenements.get(0);
		evenements.remove(0);
		return next.doEvent(this);
	}
	
	public void insertNewEvent( Evenement e )	{
		int dateSimulation = e.getDate_simulation();
		int index_event = -1;
		for(int i = 0; (i < evenements.size() && index_event == -1); i ++)	{
			Evenement current = evenements.get(i);
			int currentDate = current.getDate_simulation();
			if(currentDate > dateSimulation)
				index_event = i;
			else if(currentDate == dateSimulation)	{
				/* dans ce cas on d�finit les priorit�s suivantes 
				 * ++ prioritaire : DebutSimulation et FinSimulation
				 * + prioritaire : ArriveeAppel et ArriveeCourriel
				 * - prioritaire : FinTraitementAppel et FinTraitementCourriel
				 */
				if(e.getClass().equals(FinSimulation.class) || e.getClass().equals(DebutSimulation.class))
					index_event = i;
				if((e.getClass().equals(ArriveeAppel.class) || e.getClass().equals(ArriveeCourriel.class)) &&
						(current.getClass().equals(FinTraitementAppel.class) || current.getClass().equals(FinTraitementAppel.class)))
					index_event = i;
			}
			
		}
		if(index_event == -1 )
			evenements.add(e);
		else
			evenements.add(index_event, e);
	}
	
	public void majAires()	{
		o_tc += (dateSimu - last_dateSimu) * (n - nt_dispo - nc_dispo);
		o_pt += (dateSimu - last_dateSimu) * nt;
		last_dateSimu = dateSimu;
	}
	
	public void doTraitementAppel()	{
		// maj des temps d'attente
		att += dateSimu - qt.get(0);
		qt.remove(0);
		// creation du nouvel evenement
		insertNewEvent(new FinTraitementAppel(dateSimu + Probabilites.uniforme(5, 15)));
	}
	
	public void doTraitementCourriel()	{
		int date_finRep = dateSimu + Probabilites.uniforme(3, 7);
		rep += date_finRep - qc.get(0);
		qc.remove(0);
		// creation du nouvel evenement
		insertNewEvent(new FinTraitementCourriel(date_finRep));
	}
	
	public void genereResultats()	{
		courriels_non_traites = c - c_rep;
		tps_attente_moyen = att / a;
		tps_reponse_moyen = rep / c;
		taux_occupation_teleconseillers = (o_tc / n) / 14400.;
		taux_occupation_postes = (o_pt / ntmax) / 14400.;
	}
	
	public void afficheResultats()	{
		System.out.println("R�sultats de la simulation pour N = "+n+", Nt = "+nt+" et Ntmax = "+ntmax);
		System.out.println("---------------------------");
		System.out.println("Nombre de courriels non trait�s : "+courriels_non_traites);
		System.out.println("Temps d'attente moyen pour les appels : "+(tps_attente_moyen/60)+" minutes");
		System.out.println("Temps de r�ponse moyen pour les courriels : "+(tps_reponse_moyen/60)+" minutes");
		System.out.println("Taux d'occupation des t�l�conseillers : "+(taux_occupation_teleconseillers*100)+" %");
		System.out.println("Taux d'occupation des postes t�l�phoniques : "+(taux_occupation_postes*100)+" %");
	}

}
