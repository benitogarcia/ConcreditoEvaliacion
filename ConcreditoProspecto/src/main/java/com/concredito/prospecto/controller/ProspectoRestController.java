package com.concredito.prospecto.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.concredito.prospecto.entity.Prospecto;
import com.concredito.prospecto.service.ProspectoService;
import com.concredito.prospecto.util.Utileria;
import com.concredito.prospecto.vo.ProspectoUpdateStatus;
import com.concredito.prospecto.vo.ResponseStatusHttp;

@RestController
@RequestMapping("/api/prospectos/")
public class ProspectoRestController {

	private static final Logger LOG = LoggerFactory.getLogger(ProspectoRestController.class);

	private final String UPLOAD_DIR = "./uploads/";

	@Autowired
	private ProspectoService prospectoService;

	@PostMapping(path = "/v1/update/statu")
	public ResponseEntity<?> updateStatus(@RequestBody ProspectoUpdateStatus pus) {

		LOG.info("Actualizando status de prospecto");
		LOG.info(pus.toString());

		List<String> errores = new ArrayList<>();

		Map<String, Object> message = new HashMap<>();
		ResponseStatusHttp rsh = new ResponseStatusHttp();

		// Validar si existe el prospecto.
		if (!prospectoService.existsById(pus.getId())) {
			LOG.info("Error en el prospecto, no existe.");
			errores.add("El prospecto a editar es invalido.");
			// message.put("id", "El prospecto a editar es invalido.");
		}

		// Valdar si existe las obervaciones si fue recazado
		if (!pus.isStatus() && pus.getObservacion().equals("")) {
			LOG.info("Error en el prospecto, status: rechazado no tiene descripcion.");
			errores.add("Observaciones es obligatrio si rechaza el prospecto.");
			// message.put("Observaciones", "Observaciones es obligatrio si rechaza el
			// prospecto.");
		}

		if (errores.size() > 0) {// !message.isEmpty()
			message.put("title", "Error en el formulario.");
			message.put("messages", errores);
			return new ResponseEntity<Map<String, Object>>(message, HttpStatus.BAD_REQUEST);
		}

		Prospecto _prospecto = prospectoService.findById(pus.getId());

		String status = pus.isStatus() ? "AUTORIZADO" : "RECHAZADO";
		String observacion = pus.isStatus() ? "" : pus.getObservacion();

		_prospecto.setStatus(status);
		_prospecto.setObservacion(observacion);

		_prospecto = prospectoService.save(_prospecto);

		if (_prospecto == null) {
			errores.add("Ocurrio un error al actulizar el status.");
			message.put("title", "Error en el servidor.");
			message.put("messages", errores);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, Utileria.errorMessagesMap(message));
		}

		return new ResponseEntity<ResponseStatusHttp>(rsh, HttpStatus.OK);
	}

	@PostMapping(path = "/v1/create", consumes = { "multipart/form-data" })
	public ResponseEntity<?> createProspecto(@Valid Prospecto pros,
			@RequestParam("documentos") MultipartFile[] paramFiles, BindingResult br) {

		LOG.info("Registrando nuevo prospecto.");

		List<String> errores = new ArrayList<>();
		Map<String, Object> message = new HashMap<>();

		// Error detectados con validation.
		if (br.hasErrors()) {
			LOG.info("Error en el formulario.");
			message.put("title", "Formulario invalido.");
			message.put("messages", Utileria.messagesBindingResult(br));
			return new ResponseEntity<Map<String, Object>>(message, HttpStatus.BAD_REQUEST);
		}

		// Validar si no se encuentra registrado el RFC
		if (prospectoService.existsByRfc(pros.getRfc())) {
			LOG.info("RFC ya existe.");
			errores.add("RFC ya se encuentra registrado.");
		}

		// Validar si no se encuentra registrado el telefono
		if (prospectoService.existsByTelefono(pros.getTelefono())) {
			LOG.info("Teléfono ya existe.");
			errores.add("Teléfono ya se encuentra registrado.");
		}

		if (pros.getDocumentos().length == 0) {
			LOG.info("Documento vacio.");
			errores.add("Documentos es obligatorio.");
		}

		// Responder formulario invalido.
		if (errores.size() > 0) {
			message.put("title", "Formulario invalido.");
			message.put("messages", errores);
			return new ResponseEntity<Map<String, Object>>(message, HttpStatus.BAD_REQUEST);
		}

		// Registrar propecto
		pros.setStatus("ENVIADO");
		Prospecto _prospecto = prospectoService.save(pros);

		if (_prospecto == null) {
			LOG.error("Error al registrar el prospectante.");
			message.put("title", "Error en el servidor.");
			errores.add("Ocurrio un error inesperado, intente en unos minutos.");
			message.put("messages", errores);
			return new ResponseEntity<Map<String, Object>>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Guardar los documentos.
		boolean upload = true;
		File uploads = new File(UPLOAD_DIR + _prospecto.getId() + "/");
		List<String> files = new ArrayList<>();

		uploads.mkdirs();

		Arrays.asList(pros.getDocumentos()).stream().forEach(mf -> {
			String fileName = StringUtils.cleanPath(mf.getOriginalFilename());
			File file;
			try {

				file = File.createTempFile("Doc", " " + fileName, uploads);
				try (InputStream input = mf.getInputStream()) {
					Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
					files.add(file.getName());
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		// En caso de error se elimina: prostecto y directorio de documentos
		if (!upload) {
			if (uploads.exists()) {
				uploads.delete();
			}
			prospectoService.deleteProspecto(_prospecto);
		}

		// Se actuliza los rutasDocs con los nombres de los doc guardados
		String rutas = "";

		for (String string : files) {
			if (!rutas.equals("")) {
				rutas += ";";
			}
			rutas += string;
		}

		_prospecto.setRutaDocs(rutas);
		prospectoService.save(_prospecto);
		message.put("title", "Success");
		return new ResponseEntity<>(message, HttpStatus.OK);
	}

	@GetMapping("/v1/download/doc/{id}")
	public ResponseEntity<?> downloadDoc(@PathVariable Long id, @RequestParam String doc, HttpServletRequest request) {

		List<String> errores = new ArrayList<>();
		Map<String, Object> message = new HashMap<>();

		String fileUri = UPLOAD_DIR + id + "/" + doc;
		String folderDownloads = System.getProperty("user.home") + "/Downloads/" + doc;

		final Path path = Paths.get(fileUri);
		final byte[] bytes;
		try {
			// Read the file

			if (!path.toFile().exists()) {
				message.put("status", false);
				message.put("title", "Error en la solicitud.");
				errores.add("No se encontro el documento.");
				message.put("messages", errores);
				return new ResponseEntity<Map<String, Object>>(message, HttpStatus.INTERNAL_SERVER_ERROR);

			}

			bytes = Files.readAllBytes(path);

			OutputStream out = new FileOutputStream(new File(folderDownloads));
			out.write(bytes);
			out.close();
			
			message.put("status", true);
			message.put("doc", bytes);
		} catch (IOException e) {
			message.put("status", false);
			return ResponseEntity.of(Optional.empty());
		}
		return ResponseEntity.ok(message);
	}
	
	@GetMapping("/v2/download/doc/{id}")
	public ResponseEntity<Map<String, Object>> downloadDocV2(@PathVariable Long id, @RequestParam String doc, HttpServletRequest request) {

		Map<String, Object> message = new HashMap<>();

		String fileUri = UPLOAD_DIR + id + "/" + doc;
		//String folderDownloads = System.getProperty("user.home") + "/Downloads/" + doc;

		final Path path = Paths.get(fileUri);
		final byte[] bytes;
		try {
			if (!path.toFile().exists()) {
				message.put("status", false);
				message.put("message", "No se encontro el archivo");
				return new ResponseEntity<Map<String, Object>>(message, HttpStatus.BAD_REQUEST);

			}

			bytes = Files.readAllBytes(path);
			
			String base64 = Base64.getEncoder().encodeToString(bytes);
			
			message.put("status", true);
			message.put("doc", bytes);
			message.put("base64", base64);
		} catch (IOException e) {
			message.put("status", false);
			message.put("message", "Error al descargar el archivo");
			return new ResponseEntity<Map<String, Object>>(message, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseEntity.ok(message);
	}
}
