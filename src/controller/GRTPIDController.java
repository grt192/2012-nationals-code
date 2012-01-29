/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author calvin
 */
public class GRTPIDController {
    private double pConstant;
    private double dConstant;
    private double iConstant;
    
    private double minOutput;
    private double maxOutput;
    
    private double error = 0;
    private double errorPrevious = 0;
    private double errorAccumulated = 0;
    private double errorDelta = 0;
    
    private double result;
    
    private static final double MILLIS_TO_SECONDS = .001;
    
    public GRTPIDController(double p, double i, double d, double maxOutput, double minOutput){
        setPID(p,i,d);
        setOutputRange(minOutput, maxOutput);
    }
    
    public void setPID(double p, double i, double d){
        pConstant = p;
        iConstant = i;
        dConstant = d;
    }
    
    public void setOutputRange(double minOutput, double maxOutput){
        this.minOutput = minOutput;
        this.maxOutput = maxOutput;
    }
    
    public void update(double data, double target, int timeInMillis){
        error = target - data;        
        errorAccumulated += error * timeInMillis * MILLIS_TO_SECONDS;
        errorDelta = (error - errorPrevious) / (timeInMillis * MILLIS_TO_SECONDS);
        errorPrevious = error;
        calculateResult();
        if((result < maxOutput) && (result > minOutput)){
            errorAccumulated -= error * timeInMillis * MILLIS_TO_SECONDS;
            calculateResult();
        }
        
    }
    
    private void calculateResult(){
        result = pConstant * error + iConstant * errorAccumulated + dConstant * errorDelta;
    }
    
    public double getResult(){
        return result;
    }
}