package modelo.servicio;

import java.util.List;


import modelo.Departamento;


public interface IDepartamentoServicio {

	public List<Departamento> getAll();
	public Departamento saveOrUpdate(Departamento d) ;
}
