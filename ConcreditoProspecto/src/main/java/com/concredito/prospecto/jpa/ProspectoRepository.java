package com.concredito.prospecto.jpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.concredito.prospecto.entity.Prospecto;

@Repository
public interface ProspectoRepository  extends JpaRepository<Prospecto, Long>{

	boolean existsByRfc(String rfc);

	boolean existsByTelefono(String telefono);
	
	@Query(value = "SELECT p FROM Prospecto p ORDER BY FIELD (p.status,'RECHAZADO','AUTORIZADO','ENVIADO') DESC, p.status")
	List<Prospecto> orderByFieldStatus();

}
