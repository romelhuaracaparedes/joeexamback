package com.romel.online.store.controller.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.romel.online.store.controller.commons.ObjectMessage;
import com.romel.online.store.controller.commons.ObjectResponse;
import com.romel.online.store.controller.enums.CRUDEnum;



public class GenericController {

	protected List<Map<String, String>> getErrrors(BindingResult result) {

		List<Map<String, String>> errors = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}
		).collect(Collectors.toList());
		return errors;
	}

	protected ResponseEntity<ObjectResponse> notFound() {
		return customNotFound(null);
	}

	protected ResponseEntity<ObjectResponse> notFound(Long id) {
		return customNotFound(id);
	}

	protected ResponseEntity<ObjectResponse> badRequest(Object msg) {

		if (msg instanceof BindingResult) {
			msg = this.getErrrors((BindingResult) msg);
		}

		return ResponseEntity.badRequest().body(ObjectResponse.builder()
				.message(ObjectMessage.builder().code(0).message(msg).build()).data(null).build());
	}

	protected ResponseEntity<ObjectResponse> ok(Object obj, CRUDEnum crud) {

		String msg = "";
		HttpStatus httpStatus = HttpStatus.OK;
		switch (crud) {

		case CONSULTA:
			msg = "Exito de consulta";
			break;
		case REGISTRO:
			msg = "Exito de registro";
			httpStatus = HttpStatus.CREATED;
			break;
		case ACTUALIZACION:
			msg = "Exito de actualización";
			break;
		case ELIMINACION:
			msg = "Exito de eliminación";
			break;

		}
		return ResponseEntity.status(httpStatus).body(ObjectResponse.builder()
				.message(ObjectMessage.builder().code(1).message(msg).build()).data(obj).build());
	}

	protected ResponseEntity<ObjectResponse> error(Exception e) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	protected ResponseEntity<ObjectResponse> error() {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	public ResponseEntity<ObjectResponse> customError(Object msg) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ObjectResponse.builder()
				.message(ObjectMessage.builder().code(-1).message("Error interno").build()).data(null).build());
	}

	private ResponseEntity<ObjectResponse> customNotFound(Long id) {
		String msg = (id != null) ? "No existe resultado con el Id = " + id + " especificado" : "No existe registros";
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ObjectResponse.builder()
				.message(ObjectMessage.builder().code(0).message(msg).build()).data(null).build());
	}

}
