package com.concredito.prospecto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.concredito.prospecto.entity.Prospecto;
import com.concredito.prospecto.jpa.ProspectoRepository;

@Service
public class ProspectoServiceImpl implements ProspectoService{
	
	@Autowired
	private ProspectoRepository prosRepo;

	@Override
	public Prospecto save(Prospecto prospecto) {
		return prosRepo.save(prospecto);
	}

	@Override
	public void deleteProspecto(Prospecto prospecto) {
		prosRepo.delete(prospecto);
	}

	@Override
	public List<Prospecto> findAll() {
		return prosRepo.findAll();
	}

	@Override
	public boolean existsById(Long id) {
		return prosRepo.existsById(id);
	}

	@Override
	public Prospecto findById(Long id) {
		return prosRepo.findById(id).orElse(null);
	}

	@Override
	public boolean existsByRfc(String rfc) {
		return prosRepo.existsByRfc(rfc);
	}

	@Override
	public boolean existsByTelefono(String telefono) {
		return prosRepo.existsByTelefono(telefono);
	}

	@Override
	public List<Prospecto> orderByFieldStatus() {
		return prosRepo.orderByFieldStatus();
	}

}
