package com.zenTrading.service;


import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zenTrading.component.Algo;
import com.zenTrading.component.SignalConfig;

/**
 * This class implements the SignalHandler interface and represents a service
 * that handles trading signals in a trading system.
 * 
 * The class is responsible for processing incoming trading signals and executing the appropriate
 * actions based on the signal data. It uses a mapping of signal IDs to SignalConfig objects to
 * determine the methods and their corresponding parameters and values to be executed for each signal.
 * The Algo component is used to execute the trading algorithm's actions based on the signal data.
 * */
@Service
public class SignalHandlerImpl implements SignalHandler {

	@Resource(name = "signalConfigMap")
	private HashMap<Integer, SignalConfig> signalConfigMap;

	
	private Algo algo;
	
	

	public SignalHandlerImpl(HashMap<Integer, SignalConfig> signalConfigMap, Algo algo) {
        this.signalConfigMap = signalConfigMap;
        this.algo = algo;
    }
	
	/**
	 *  This method will process the incoming trading signals and executing the appropriate
	 * actions based on the signal data. It uses a mapping of signal IDs to SignalConfig objects to
	 * determine the methods and their corresponding parameters and values to be executed for each signal.
	 * The Algo component is used to execute the trading algorithm's actions based on the signal data.
	 * @param signalConfigMap
	 * @param algo
	 **/
	public void handleSignal(int signal) {

		SignalConfig signalConfig = signalConfigMap.get(signal);
		if (signalConfig == null) {
			algo.cancelTrades();
		} else {
			try {
				for (String methodName : signalConfig.getMethods()) {
					int nextParamIndex = 0;
					if (methodName.equals("setAlgoParam")) {
						int param = signalConfig.getParams()[nextParamIndex];
						int value = signalConfig.getValues()[nextParamIndex];
						invokeAlgoMethodWithParam(algo, methodName, param, value);
						nextParamIndex++;
					} else
						invokeAlgoMethod(algo, methodName);
				}
				algo.doAlgo();

			} catch (NoSuchMethodException e) {
				e.printStackTrace();
				algo.cancelTrades();
			} catch (Exception e) {
				e.printStackTrace();
				algo.cancelTrades();
			}

		}

	}

	/**
     * Invokes a method in the Algo component by its name using reflection.
     * 
     * @param algo       The Algo component on which the method is to be invoked.
     * @param methodName The name of the method to be invoked.
     * @throws Exception If there is an error while invoking the method.
     */
	private void invokeAlgoMethod(Algo algo, String methodName) throws Exception {
		this.algo.getClass().getMethod(methodName).invoke(algo);
	}

	/**
     * Invokes a method with parameters in the Algo component by its name.
     * 
     * @param algo       The Algo component on which the method is to be invoked.
     * @param methodName The name of the method to be invoked.
     * @param params     The parameters to be passed to the method.
     * @param value      The value to be passed to the method.
     */
	private void invokeAlgoMethodWithParam(Algo algo, String methodName, int params, int value) {
		this.algo.setAlgoParam(params, value);
	}


}
