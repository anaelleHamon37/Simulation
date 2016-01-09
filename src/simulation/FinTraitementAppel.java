package simulation;

public class FinTraitementAppel extends Evenement{

	public FinTraitementAppel(int date)	{
		date_simulation = date;
	}
	
	@Override
	public Simulateur doEvent(Simulateur simulateur) {
		simulateur.dateSimu = date_simulation;
		simulateur.t_rep ++;
		if(simulateur.qt.size() > 0)
			simulateur.doTraitementAppel();
		else
		{
			simulateur.nt --;
			simulateur.nc ++;
			if(simulateur.qc.size() > 0)
				simulateur.doTraitementCourriel();
			else
				simulateur.nc_dispo ++;
		}
		
		simulateur.majAires();
		
		return simulateur;
	}
	

}
