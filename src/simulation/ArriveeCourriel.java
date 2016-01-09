package simulation;

public class ArriveeCourriel extends Evenement{

	public ArriveeCourriel(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		simulateur.dateSimu = date_simulation;
		simulateur.c++;
		
		if(simulateur.nc_dispo == 0)
			simulateur.qc.add(date_simulation);
		else	{
			simulateur.nc_dispo--;
			int tps_reponse = date_simulation + Probabilites.uniforme(180, 420);
			simulateur.rep += tps_reponse;
			simulateur.insertNewEvent(new FinTraitementCourriel(date_simulation + tps_reponse));
		}
		
		
		if(date_simulation != 0){
			// Pas dans le cas des courriels arrivés la nuit
			int date_suite = date_simulation;
			if(date_simulation < 3600)
				date_suite += Probabilites.exponentielle(60);
			else 
				date_suite += Probabilites.exponentielle(300);
			simulateur.insertNewEvent(new ArriveeCourriel(date_suite));
		}
		
		simulateur.majAires();
		return simulateur;
	}
	

}
