package simulation;

import java.util.ArrayList;
import java.util.List;

public class Simulateur {
	
	/*Variables*/
	int n, ntmax, dateSimu, last_dateSimu ;
	// infos liées aux appels
	int nt, nt_dispo ;
	List<Integer> qt ;
	// infos liées aux courriels
	int nc, nc_dispo ;
	List<Integer> qc ;
	
	/*Indicateurs*/
	int a, c ;
	// taux d'occupation
	int o_tc, o_pt ;
	// temps d'attente/réponse
	int att, rep ;
	
	/* Variables liées au simulateur */
	List<Evenement> evenements ;
	boolean isFinished;
	
	/*Variables liées au résultat*/
	int c_rep;
	int t_rep;
	ResultatSimulation resultat;
	
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
		resultat = null;
		c_rep = 0;
		t_rep = 0;
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
				/* dans ce cas on définit les priorités suivantes 
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
		insertNewEvent(new FinTraitementAppel(dateSimu + Probabilites.uniforme(300, 900)));
	}
	
	public void doTraitementCourriel()	{
		int date_finRep = dateSimu + Probabilites.uniforme(180, 420);
		rep += date_finRep - qc.get(0);
		qc.remove(0);
		// creation du nouvel evenement
		insertNewEvent(new FinTraitementCourriel(date_finRep));
	}
	
	public void genereResultats()	{
	//	int courriels_non_traites = c - c_rep;
	//	int appels_non_traites = c - t_rep;
		double tps_attente_moyen = att / a;
		double tps_reponse_moyen = rep / c;
		double taux_occupation_teleconseillers = (o_tc / n) / 14400.;
		double taux_occupation_postes = (o_pt / ntmax) / 14400.;
		
		resultat = new ResultatSimulation(qc.size(), qt.size(),
					tps_attente_moyen, tps_reponse_moyen,
					taux_occupation_teleconseillers, taux_occupation_postes);
	}
	
	

}
