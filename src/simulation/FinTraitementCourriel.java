package simulation;

public class FinTraitementCourriel extends Evenement{

	public FinTraitementCourriel(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		simulateur.dateSimu = date_simulation;
		simulateur.c_rep ++;
		if(simulateur.qt.size() > simulateur.nt && simulateur.nt < simulateur.ntmax)
		{
			simulateur.nc --;
			simulateur.nt ++;
			simulateur.doTraitementAppel();
		}
		else if(simulateur.qc.size() > 0)
			simulateur.doTraitementCourriel();
		else
			simulateur.nc_dispo ++;
		
		simulateur.majAires();
		return simulateur;
		
	}
	

}
