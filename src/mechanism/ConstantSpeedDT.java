/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mechanism;

import actuator.IMotor;
import controller.GRTPIDController;
import core.GRTLoggedProcess;
import event.EncoderEvent;
import event.EncoderListener;
import sensor.GRTEncoder;

/**
 * 4 motor drivetrain with speed control
 * @author calvin
 */
public class ConstantSpeedDT extends GRTDriveTrain implements EncoderListener{
 
    private final IMotor leftFront;
    private final IMotor leftBack;
    private final IMotor rightFront;
    private final IMotor rightBack;
    private GRTEncoder leftEncoder;
    private GRTEncoder rightEncoder;
    
    private static final double MAX_RATE = 14500; //TODO: find correct maximum rate
    
    private static final double MAXIMUM_OUTPUT = 1;
    private static final double MINIMUM_OUTPUT = -1;
    
    private double leftEncoderRate;
    private double rightEncoderRate;
    
    private double leftVelocity;
    private double rightVelocity;
    
    private static final double pConstant = 1;
    private static final double iConstant = 0;
    private static final double dConstant = 0;

    private GRTPIDController leftPIDController;
    private GRTPIDController rightPIDController;
    
    private final static int THREAD_SLEEP_TIME = 20;
    /**
     * 
     * @param leftFront left front motor
     * @param leftBack left back motor
     * @param rightFront right front motor
     * @param rightBack right back motor
     */
    
    public ConstantSpeedDT(IMotor leftFront, IMotor leftBack,
            IMotor rightFront, IMotor rightBack, GRTEncoder leftEncoder, GRTEncoder rightEncoder, String name) {
        super(leftFront, leftBack, rightFront, rightBack, name);
        this.leftFront = leftFront;
        this.leftBack = leftBack;
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.leftEncoder = leftEncoder;
        this.rightEncoder = rightEncoder;
        leftPIDController = new GRTPIDController(pConstant, iConstant, dConstant, MAXIMUM_OUTPUT, MINIMUM_OUTPUT);
        rightPIDController = new GRTPIDController(pConstant, iConstant, dConstant, MAXIMUM_OUTPUT, MINIMUM_OUTPUT);
    }
    
    public void run(){
        running = true;
        while(running){
            
            //pid calculation
            
//            System.out.println("left:" + (-leftVelocity) + "\tright:" + -rightVelocity);
            
            double leftTargetRate = leftVelocity * MAX_RATE;
            double rightTargetRate = rightVelocity * MAX_RATE;

            System.out.println("Left target rate:" + leftTargetRate + " Right target rate:" + rightTargetRate);
            
            leftPIDController.update(leftEncoderRate, leftTargetRate, THREAD_SLEEP_TIME);
            rightPIDController.update(rightEncoderRate, rightTargetRate, THREAD_SLEEP_TIME);
            
            double calcLeftVelocity = leftPIDController.getResult();
            double calcRightVelocity = rightPIDController.getResult();

            leftFront.setSpeed(calcLeftVelocity);
            leftBack.setSpeed(-calcLeftVelocity);
            log(100, calcLeftVelocity);
            rightFront.setSpeed(calcRightVelocity);
            rightBack.setSpeed(-calcRightVelocity);
            log(101, calcRightVelocity);
            
            
            try {
                Thread.sleep(THREAD_SLEEP_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * TankDrive uses differential steering.
     * @param leftVelocity
     * @param rightVelocity
     */
    public void tankDrive(double leftVelocity, double rightVelocity) {
        this.leftVelocity = leftVelocity;
        this.rightVelocity = rightVelocity;
//        System.out.println("Velocity updated");

    }
    
    protected void startListening(){
        leftEncoder.addEncoderListener(this);
        rightEncoder.addEncoderListener(this);
    }

    public void directionChanged(EncoderEvent e) {
        //in the case of a direction change, act the same way as a rate change
        rateChanged(e);
    }

    public void degreeChanged(EncoderEvent e) {
    }

    public void distanceChanged(EncoderEvent e) {
    }

    public void rateChanged(EncoderEvent e) {  
        //if encoder is going backwards, rate is negative
        //TODO: figure out which encoder to multiply by negative one
        //if encoder is left? negate
        if(e.getSource() == leftEncoder){
            leftEncoderRate = e.getRate();
        }
        if(e.getSource() == rightEncoder){
            rightEncoderRate = e.getRate() * -1;
        }

    }
    
}
