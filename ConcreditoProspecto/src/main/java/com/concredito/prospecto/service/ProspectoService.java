package com.concredito.prospecto.service;

import java.util.List;

import com.concredito.prospecto.entity.Prospecto;

public interface ProspectoService {
	
	Prospecto save(Prospecto prospecto);
	
	void deleteProspecto(Prospecto prospecto);
	
	List<Prospecto> findAll();
	
	boolean existsById(Long id);

	Prospecto findById(Long id);
	
	boolean existsByRfc(String rfc);

	boolean existsByTelefono(String telefono);
	
	List<Prospecto> orderByFieldStatus();
	
}
