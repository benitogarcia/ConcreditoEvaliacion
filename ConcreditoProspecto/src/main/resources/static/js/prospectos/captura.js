function confirmExit() {
	if (confirm("Decea salir del formulario?")) {
		window.location.href = "/";
	}
}

function fileSelect() {

	console.log("Se selecciono un archivo");
	var fileUpload = $("#documentos").get(0);
	var files = fileUpload.files;
	$("#filesNames").html("");
	var names = "";

	for (var i = 0; i < files.length; i++) {

		if (names !== "") {
			names += "<br>";
		}
		console.log("fileName:" + files[i].name);
		names += files[i].name;
	}
	$("#filesNames").html(names);
}

$(document).ready(function() {
	$('#formNuevo').bootstrapValidator({
		live: 'disabled',
		message: 'El valor no es valido.',
		feedbackIcons: {
			valid: 'fa fa-check',
			invalid: 'fa fa-times',
			validating: 'fa fa-refresh'
		},
		fields: {
			nombre: {
				message: 'Nombre no es valido.',
				validators: {
					notEmpty: {
						message: 'Nombre es requerido y no puede ser vacio.'
					},
					stringLength: {
						min: 3,
						max: 25,
						message: 'Nombre debe contener de 3 a 25 caracteres.'
					},
					regexp: {
						regexp: /^[a-zA-Z0]+( [a-zA-Z]+)*$/,
						message: 'Nombre debe ser alfabetico y espacios [aA-zZ].'
					}
				}
			},
			primerApellido: {
				message: 'Primer Apellido no es valido.',
				validators: {
					notEmpty: {
						message: 'Primer Apellido es requerido y no pueder ser vacio.'
					},
					stringLength: {
						min: 3,
						max: 25,
						message: 'Primer Apellido debe contener de 3 a 25 caracteres.'
					},
					regexp: {
						regexp: /^[a-zA-Z0]+( [a-zA-Z]+)*$/,
						message: 'Primer Apellido debe ser alfabetico y espacios [aA-zZ].'
					}
				}
			},
			calle: {
				message: 'Calle no es valido.',
				validators: {
					notEmpty: {
						message: 'Calle es requerido y no pueder ser vacio.'
					}
				}
			},
			numero: {
				message: 'Número no es valido.',
				validators: {
					notEmpty: {
						message: 'Número es requerido y no pueder ser vacio.'
					},
					numeric: {
						message: 'Número debe ser númerico.'
					}
				}
			},

			colonia: {
				validators: {
					notEmpty: {
						message: 'Colonia es requerido'
					}
				}
			},
			codigoPostal: {
				validators: {
					notEmpty: {
						message: 'Código Postal es requerido'
					},
					regexp: {
						regexp: /^[0-9]{5,6}$/,
						message: 'Código postal solo puede contener números de 5 a 6.'
					}
				}
			},
			telefono: {
				message: 'Teléfono no es valido',
				validators: {
					notEmpty: {
						message: 'Teléfono es requerido y no puede ser vacio'
					},
					regexp: {
						regexp: /^[0-9]+$/,
						message: 'Teléfono solo puede contener números'
					}
				}
			},
			rfc: {
				message: 'RFC no es valido',
				validators: {
					notEmpty: {
						message: 'RFC es requerido y no puede ser vacio'
					},
					regexp: {
						regexp: /^[A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?$/,
						message: 'RFC formato invalido'
					}
				}
			},
			documentos: {
				message: 'Documentos no es valido',
				validators: {
					notEmpty: {
						message: 'Documentos es requerido y no puede ser vacio'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {

		e.preventDefault();
		var file_data = $("#documentos").prop("files");
		
		console.log('$("#documentos").prop("files")');
		console.log($("#documentos").prop("files"));
		console.log('$("#documentos").prop("files")[0]');
		console.log($("#documentos").prop("files")[0]);
		
		var form_data = new FormData();
		form_data.append("documentos", file_data);
		form_data.append("nombre", $("#nombre").val());
		form_data.append("primerApellido", $("#primerApellido").val());
		form_data.append("segundoApellido", $("#segundoApellido").val());
		form_data.append("calle", $("#calle").val());
		form_data.append("numero", $("#numero").val());
		form_data.append("colonia", $("#colonia").val());
		form_data.append("codigoPostal", $("#codigoPostal").val());
		form_data.append("telefono", $("#telefono").val());
		form_data.append("rfc", $("#rfc").val());
		
		
		var form_data_2 = new FormData($("#formNuevo")[0]);

		$("#tittle").text('');
		$("#messages").html('');
		$("#success").html('');

		var button_content = $(this).find('button[type=submit]');
		var btnSubmitHtml = button_content.html();
		button_content.html('<div class="spinner-border text-secondary"></div>');

		$.ajax({
			url: "/api/prospectos/v1/create",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: form_data_2,
			processData: false,
			contentType: false,
			success: function(result) {
				window.location.href = "/";
			},
			error: function(xhr) {
				console.log("error");
				console.log(xhr);
				/*var json = xhr.responseJSON;
				$("#title").text(json.tittle);
				var messages = "";
				json.messages.forEach(function(msg) {
					messages += "* " + msg + "<br>";
				});
				$("#messages").html(messages);*/
			},
			complete: function(xhr, status) {
				button_content.html(btnSubmitHtml);
			}
		});
	});
});