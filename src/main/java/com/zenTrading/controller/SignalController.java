package com.zenTrading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.zenTrading.service.SignalHandler;

@RestController
public class SignalController {
	
	private final SignalHandler signalHandler;

    public SignalController(SignalHandler signalHandler) {
        this.signalHandler = signalHandler;
    }

    @GetMapping("/signal/{signal}")
    public void handleSignal(@PathVariable Integer signal) {
        signalHandler.handleSignal(signal);
    }

}
