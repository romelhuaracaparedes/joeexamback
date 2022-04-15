package com.romel.online.store.controller.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectResponse {

	private ObjectMessage	message;
	
	private Object  		data;
	
}
