$(document).ready(function() {

	$("#formEvaluar").submit(function(e) {
		e.preventDefault();

		var status = ($('input:radio[name=status]:checked').val() == "1" ? true : false);
		var datos = {
			'id': $('#id').val(),
			'status': status,
			'observacion': $('#observacion').val()
		};

		var button_content = $(this).find('button[type=submit]');
		var btnSubmitHtml = button_content.html();
		button_content.html('<div class="spinner-border text-secondary"></div>');

		$("#tittle").text('');
		$("#messages").html('');
		$("#success").html('');

		$.ajax({
			url: "/api/prospectos/v1/update/statu",
			type: "POST",
			dataType: "json",
			contentType: "application/json",
			data: JSON.stringify(datos),
			success: function(result) {
				window.location.href = "/";
				//$("#success").html('Se actualizo el estado.');
			},
			error: function(xhr) {

				var json = xhr.responseJSON;
				$("#title").text(json.tittle);
				var messages = "";
				json.messages.forEach(function(msg) {
					messages += "* " + msg + "<br>";
				});
				$("#messages").html(messages);
			},
			complete: function(xhr, status) {
				button_content.html(btnSubmitHtml);
			}
		});

	});

	$(".btnDownloadDoc").click(function(e) {

		var doc = $(this).val();

		var arrayDeCadenas = doc.split(".");
		var type = arrayDeCadenas[arrayDeCadenas.length - 1];

		$.ajax({
			url: "/api/prospectos/v2/download/doc/" + $('#id').val() + "?doc=" + doc,
			type: "GET",
			success: function(result) {

				if (result.status) {
					var bytes = base64ToArrayBuffer(result.base64);
					saveByteArray(doc, bytes, type);
				} else {
					alert(result.message);
				}
			},
			error: function(xhr) {
				alert("Ocurrio un error inesperado, intentlo mas tarde.");
			}
		});

	});

	function base64ToArrayBuffer(base64) {
		var binaryString = window.atob(base64);
		var binaryLen = binaryString.length;
		var bytes = new Uint8Array(binaryLen);
		for (var i = 0; i < binaryLen; i++) {
			var ascii = binaryString.charCodeAt(i);
			bytes[i] = ascii;
		}
		return bytes;
	}

	function saveByteArray(reportName, byte, type) {
		var blob = new Blob([byte], {
			type: "application/"+type
		});
		var link = document.createElement('a');
		link.href = window.URL.createObjectURL(blob);
		var fileName = reportName;
		link.download = fileName;
		link.click();
	};
});