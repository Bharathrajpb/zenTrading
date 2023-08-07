package com.zenTrading.component;

import org.springframework.stereotype.Component;

/**
 
 *This class represent the a configuration for a particular trading signal.
 * An instance of this class holds information about methods to be executed,
 * along with corresponding parameters and values.
 *
 */
@Component
public class SignalConfig {
	private  String[] methods;
    private  int[] params;
    private  int[] values;
    

    public SignalConfig(String[] methods, int[] params, int[] values) {
    	this.methods = methods;
        this.params = params;
        this.values = values;
        
    }
    
    public SignalConfig() {
    	
    }

	public int[] getParams() {
		return params;
	}

	public int[] getValues() {
		return values;
	}

	public String[] getMethods() {
		return methods;
	}

	public void setMethods(String[] methods) {
		this.methods = methods;
	}

	public void setParams(int[] params) {
		this.params = params;
	}

	public void setValues(int[] values) {
		this.values = values;
	}

	
}
