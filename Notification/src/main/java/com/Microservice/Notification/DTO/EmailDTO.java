package com.Microservice.Notification.DTO;


import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

	 	private Long bookingId;
	    private String userEmail;
	    private String userName;
	    private String eventName;
	    private Double amountPaid;
	    private String razorpayPaymentId;
}

