package modelo.servicio;

import exceptions.InstanceNotFoundException;
import modelo.Empleado;

public interface IEmpleadoServicio {
	public Empleado find(int id) throws InstanceNotFoundException;

}
