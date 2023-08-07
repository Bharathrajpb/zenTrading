package com.zenTrading.service;

/**
 * This interface handles trading signals.
 * Implementations of this interface are responsible for processing incoming trading signals
 * and executing the appropriate actions based on the signal data.
 *
 */
public interface SignalHandler {

	public void handleSignal(int signal);
}
