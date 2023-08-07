package com.zenTrading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zenTrading.service.SignalHandler;

/**
 * This class is a REST controller responsible for handling incoming HTTP
 * requests related to trading signals. It forwards the processing of trading
 * signals to the SignalHandler component.
 * 
 */

@RestController
public class SignalController {

	private final SignalHandler signalHandler;

	public SignalController(SignalHandler signalHandler) {
		this.signalHandler = signalHandler;
	}

	/**
	 * Handles the incoming HTTP GET request for trading signals. It takes {signal}
	 * as a input paramter and forward the processing of trading signals to the
	 * SignalHandler component.
	 * 
	 * @param signal The ID of the trading signal to be processed.
	 */
	@GetMapping("/signal/{signal}")
	public void handleSignal(@PathVariable Integer signal) {
		signalHandler.handleSignal(signal);
	}

}
