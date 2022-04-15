package com.romel.online.store.controller.commons;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ObjectMessage {
	private Integer code;
	private Object  message;

}

