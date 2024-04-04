package com.aloneCook.infra.email;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {

	private String to;
	
	private String title;
	
	private String message;
}
