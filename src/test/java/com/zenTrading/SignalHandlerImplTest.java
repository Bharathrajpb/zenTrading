package com.zenTrading;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zenTrading.component.Algo;
import com.zenTrading.component.SignalConfig;
import com.zenTrading.service.SignalHandlerImpl;

/**
 * This class contains JUnit test cases for the SignalHandlerImpl class.
 */
@ExtendWith(MockitoExtension.class)
class SignalHandlerImplTest {

	@InjectMocks
    private SignalHandlerImpl signalHandlerImpl;

    @Mock
    private Algo mockAlgo;

    @Mock
    private HashMap<Integer, SignalConfig> signalConfigMap;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        signalHandlerImpl = new SignalHandlerImpl(signalConfigMap, mockAlgo);
    }

    /**
     * Test case for handling an unknown signal.
     */
    @Test
    void testHandleSignalWithUnknownSignal() {
        int signal = 1;
        signalConfigMap.put(signal, null);
        signalHandlerImpl.handleSignal(signal);
        verify(mockAlgo).cancelTrades();
        verify(mockAlgo, never()).doAlgo();
    }
    
    /**
     * Test case for handling a valid signal method  with no parameters.
     */
    @Test
    void testHandleSignalWithValidSignalAndNoParams() {
        int signal = 2;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"setUp"};
        signalConfig.setMethods(methods);
        signalConfigMap.put(signal, signalConfig);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);
        verify(mockAlgo).doAlgo();
        verify(mockAlgo, never()).cancelTrades();
        verify(mockAlgo, never()).setAlgoParam(anyInt(), anyInt());
    }
    /**
     * Test case for handling a valid signal with parameters to set in Algo.
     */
    @Test
    void testHandleSignalWithValidSignalAndParams() {
        int signal = 3;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"setAlgoParam"};
        int[] param= {1};
        int[] values= {42};
        
        signalConfig.setMethods(methods);
        signalConfig.setParams(param);
        signalConfig.setValues(values);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);
        verify(mockAlgo).setAlgoParam(1, 42);
        verify(mockAlgo, never()).cancelTrades();
    }
    
    /**
     * Test case for handling a valid signal with an invalid method name.
     */
    @Test
    void testHandleSignalWithInvalidMethodName() {
        int signal = 4;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"nonExistentMethod","setUp"};
        signalConfig.setMethods(methods);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);
        verify(mockAlgo,never()).setUp();
        verify(mockAlgo).cancelTrades();
        verify(mockAlgo, never()).doAlgo();
        verify(mockAlgo, never()).setAlgoParam(anyInt(), anyInt());
    }

    
    /**
     * Test case for handling a valid signal with multiple methods to execute.
     */
    @Test
    void testHandleSignalWithMultipleMethods() {
        int signal = 5;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"setUp","setAlgoParam"};
        int[] param= {1};
        int[] values= {42};
        signalConfig.setMethods(methods);
        signalConfig.setParams(param);
        signalConfig.setValues(values);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);
        verify(mockAlgo,never()).cancelTrades();
        verify(mockAlgo).setUp();
        verify(mockAlgo).doAlgo();
        verify(mockAlgo).setAlgoParam(1, 42);
    }
    
    
    /**
     * Test case for handling a valid signal with null  method names.
     */
    @Test
    void testHandleSignalWithNullParamsAndMethodNames() {
        int signal = 6;
        SignalConfig signalConfig = new SignalConfig();
        signalConfig.setMethods(null);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);  
        verify(mockAlgo).cancelTrades();
        verify(mockAlgo, never()).doAlgo();
    }
    
    /**
     * Test case for handling actual signal with id 1
     */
    @Test
    void testHandleSignalForSignal1() {
        int signal = 1;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"reverse","performCalc","submitToMarket"};
        int[] param= {1};
        int[] values= {80};
        signalConfig.setMethods(methods);
        signalConfig.setParams(param);
        signalConfig.setValues(values);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);  
        verify(mockAlgo).reverse();
        verify(mockAlgo).performCalc();
        verify(mockAlgo).submitToMarket();
        verify(mockAlgo).doAlgo();
    }

    /**
     * Test case for handling actual signal with id 2
     */
    @Test
    void testHandleSignalForSignal2() {
        int signal = 2;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"setUp","setAlgoParam","performCalc","submitToMarket"};
        int[] param= {1};
        int[] values= {60};
        signalConfig.setMethods(methods);
        signalConfig.setParams(param);
        signalConfig.setValues(values);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);  
        verify(mockAlgo).setUp();
        verify(mockAlgo).setAlgoParam(1,60);
        verify(mockAlgo).performCalc();
        verify(mockAlgo).submitToMarket();
        verify(mockAlgo).doAlgo();
    }
    
    /**
     * Test case for handling actual signal with id 3
     */
    @Test
    void testHandleSignalForSignal3() {
        int signal = 2;
        SignalConfig signalConfig = new SignalConfig();
        String [] methods= {"setAlgoParam","setAlgoParam","performCalc","submitToMarket"};
        int[] param= {1,2};
        int[] values= {90,15};
        signalConfig.setMethods(methods);
        signalConfig.setParams(param);
        signalConfig.setValues(values);
        when(signalConfigMap.get(signal)).thenReturn(signalConfig);
        signalHandlerImpl.handleSignal(signal);  
        verify(mockAlgo,times(2)).setAlgoParam(anyInt(),anyInt());
        verify(mockAlgo).performCalc();
        verify(mockAlgo).submitToMarket();
        verify(mockAlgo).doAlgo();
    }
}
