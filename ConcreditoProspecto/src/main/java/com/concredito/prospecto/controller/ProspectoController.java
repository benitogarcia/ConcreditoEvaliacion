package com.concredito.prospecto.controller;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.concredito.prospecto.entity.Prospecto;
import com.concredito.prospecto.service.ProspectoService;

/**
 * 
 * https://attacomsian.com/blog/spring-boot-thymeleaf-file-upload
 * 
 * https://es.stackoverflow.com/questions/28338/subir-archivo-a-servidor-java-ee
 *
 * http://bootstrapvalidator.votintsev.ru/getting-started/
 * https://programmerclick.com/article/56241853051/
 * https://www.solvetic.com/tutoriales/article/1339-validaciones-con-bootstrap-validator/
 * 
 * @author benito
 *
 */
@Controller
@RequestMapping(path = "/")
public class ProspectoController {

	private static final Logger log = LoggerFactory.getLogger(ProspectoController.class);

	private final String UPLOAD_DIR = "./uploads/";

	@Autowired
	private ProspectoService prospectoService;

	@GetMapping(path = { "/", "index", "home" })
	public String lista(Model model) {

		List<Prospecto> prospectos = prospectoService.orderByFieldStatus();

		model.addAttribute("prospectos", prospectos);

		return "prospecto/lista";
	}

	@GetMapping(path = "/evaluar/{id}")
	public String evaluacion(@PathVariable(name = "id") Long id, Model model) {

		Prospecto _prospecto = prospectoService.findById(id);
		
		List<String> docs = new ArrayList<>();
		
		if (_prospecto == null) {
			model.addAttribute("message", "El prospecto no existe.");
			_prospecto = new Prospecto();
		} else {
			for (String doc : _prospecto.getRutaDocs().split(";")) {
				docs.add(doc);
			}
		}
				
		model.addAttribute("prospecto", _prospecto);
		model.addAttribute("documentos", docs);

		return "prospecto/evaluacion";
	}

	@GetMapping(path = "/nuevo")
	public String capturaGet(Model model) {

		model.addAttribute("prospecto", new Prospecto());

		return "prospecto/captura";
	}

	@PostMapping(path = "/nuevo") // , consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
	public String capturaPost(@Valid Prospecto prospecto, BindingResult br, Model model) throws EOFException {
		log.info("Guardando formulario.");
		if (br.hasErrors()) {
			log.info("Formulario invalido.");
			model.addAttribute("prospecto", prospecto);
			return "prospecto/captura";
		}

		if (prospecto.getDocumentos().length==0) {
			log.info("No se cargo ningun documento invalido.");
			model.addAttribute("msgDocumentos", "Please select a file to upload.");
			return "prospecto/captura";
		}

		// Registrar prospectante
		prospecto.setStatus("ENVIADO");
		Prospecto _prospecto = prospectoService.save(prospecto);

		if (_prospecto == null) {
			log.error("Error al registrar el prospectante.");

			model.addAttribute("msgError",
					"Ocurrio un error al registrar el prospectante, intentelo en unos minutos de nuevo.");
			return "prospecto/captura";
		}

		// Guardar los documentos.
		boolean upload = true;
		File uploads = new File(UPLOAD_DIR + _prospecto.getId() + "/");
		List<String> files = new ArrayList<>();
		try {
			String fileName = "";
			// Carpeta donde se guardan los archivos
			uploads.mkdirs(); // Crea los directorios necesarios
			
			for (MultipartFile mf : prospecto.getDocumentos()) {
				if (!upload) {
					break;
				}
				fileName = StringUtils.cleanPath(mf.getOriginalFilename());
				File file = File.createTempFile("Doc ", fileName, uploads);

				try (InputStream input = mf.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
					files.add(file.getName());
				}
			}
		} catch (IOException e) {
			log.error("Error al cargar los documentos:" + e.getMessage());
			e.printStackTrace();
			upload = false;
		}

		// En caso de que ocurra un error se elimina: prostecto y directorio de documentos		
		if (!upload) {
			if (uploads.exists()) {
				uploads.delete();
			}
			prospectoService.deleteProspecto(_prospecto);

			model.addAttribute("msgError",
					"Ocurrio un error al registrar el prospectante, no se cargo los documentos, intentelo en unos minutos de nuevo.");
			return "prospecto/captura";
		}

		log.info(files.toString());
		
		String rutas = "";
		
		for (String string : files) {
			if (!rutas.equals("")) {
				rutas += ";";
			}
			rutas += string;
		}
		log.info("-> rutas:" + rutas);
		
		_prospecto.setRutaDocs(rutas);
		
		prospectoService.save(_prospecto);

		return "redirect:/";
	}

}
