package com.grt192.core;

/**
 * Message encapsulation object which supports more data.
 * It is backwards compatible.
 * @author data, ajc
 */
public final class CommandArray extends Command {

    private double[] values;

    public CommandArray(double[] values) {
        this(values,0);
    }

    public CommandArray(double value) {
        this(new double[]{value});
    }

    public CommandArray(double value1,double value2) {
        this(new double[]{value1,value2});
    }

    public CommandArray(double value1,double value2,double value3) {
        this(new double[]{value1,value2,value3});
    }

    public CommandArray(double[] values,int sleepTime) {
        this(values,sleepTime,false);
    }

    public CommandArray(double[] values, int sleepTime, boolean atomic) {
        super(0,sleepTime,atomic);
        this.values = values;
    }

    public double getValue() {
        return getValue(0);
    }

    public double getValue(int index){
        return values[index];
    }

    public int size(){
        return values.length;
    }


    }
