/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deploy;

//import actuator.GRTVictor;
import core.GRTLoggedProcess;
import event.ButtonEvent;
import event.ButtonListener;
import mechanism.Drawbridge;
import mechanism.GRTDriveTrain;
import mechanism.ShootingSystem;
import mechanism.Wedge;
import sensor.GRTBGSystemsFXJoystick;



/**
 *
 * @author nadia
 * MechTester is a diagnostic that goes through the robot's various
 * mechanisms, testing them, and asking if they work, they can also optionally be disabled,
 * but hopefully they can be fixed instead.
 */
public class MechTester extends GRTLoggedProcess implements ButtonListener{
    
    private boolean passIsPressed; 
    private boolean failIsPressed;
    private boolean disableIsPressed;
    private final GRTBGSystemsFXJoystick joystick;
    private final GRTDriveTrain dt;  
    private final Wedge wedge;
    private final Drawbridge drawbridge;
    private final ShootingSystem ss;
    private String name;
    
    public MechTester(GRTBGSystemsFXJoystick joystick, GRTDriveTrain dt, Wedge wedge, Drawbridge drawbridge, ShootingSystem ss, String name) {
        super(name);
        this.joystick = joystick;
        this.dt = dt;                   //int 1
        this.wedge = wedge;             //int 2
        this.drawbridge = drawbridge;   //int 3
        this.ss = ss;                   //int 4 for entire, 5 for turret rotation, 6 for turret visor
    }
    
    // the main method of the MechTester - this is where the methods we test can be changed.
    public void run(){
        
//****************************************Test Drive Train*************************************************************\\
        
        dt.tankDrive(.2, .2); //go forward
        wait(500);
        dt.tankDrive(0.0,0.0);
        askForFail("Drive Train",1, "Did the Drive Train Go Forward, then Stop?  Press pass or fail.", 1);
        
        
//****************************************Test Wedge*******************************************************************\\
        
        wedge.lowerWedge();
        wedge.raiseWedge();
        askForFail("Wedge", 2, "Did the Wedge lower, then raise?  Press pass or fail.", 1);
        
        
//****************************************Test Drawbridge**************************************************************\\
        
        drawbridge.lowerArm();
        drawbridge.raiseArm();
        askForFail("Drawbridge", 3, "Did the Drawbridge lower, then raise?  Press pass or fail.", 1);
        
        
//****************************************Test Shooter*****************************************************************\\
        //should have subcategories for less important things like visor and rotation of turret
        
        System.out.println("Please feed a ball into the system.");
        //Collector 
        ss.setFlailSpeed(1.0); //intake 
        //Bot Transition 
        ss.setBotTransitionSpeed(1.0); //collection
        wait (4000);
        ss.setFlailSpeed(0.0);
        wait(2000);
        ss.setBotTransitionSpeed(0.0);
        
        // Rotation of Turret - WRITE ENCODERS
        ss.setPanSpeed(-.2);  ///want this to be left
        wait(500);
        ss.setPanSpeed(.2);   //want this to be right
        wait(500);
        ss.setPanSpeed(0.0);
        
        // Turret Visor - WRITE ENCODERS
        ss.setVisorSpeed(.2); //want up
        wait(500);
        ss.setVisorSpeed(-.2); //want down  
        wait(500);
        ss.setVisorSpeed(0.0);
        
        //Fly Wheel 
        ss.setFlywheelSpeed(1.0); //eject
        wait(3000);
        //Top Transition 
        ss.setTopTransitionSpeed(-1.0); // eject
        wait(1000);
        ss.setFlywheelSpeed(0.0);
        ss.setTopTransitionSpeed(0.0);
        //End
        askForFail("ShootingSystem", 4, "What should have happened: "
                + "/n -ball collector worked for 4 seconds "
                + "/n -bottom transition for 6 seconds "
                + "/n -rotation of turret moved left then right"
                + "/n -visor moved up then back "
                + "/n -flywheel sped up in about 3 seconds"
                + "/n -top transition shot the ball"
                + "/n  Press pass or fail.", 2);
        
    }            
    
    //a method that asks the user to pass or fail a mech based on what it should do.  
    //If fail is pressed it goes to nextAction which provides possible courses of action
    public void askForFail(String name, int mechanism, String message, int carry) { 
        this.name = name;  //to use the mech name in the messages
        System.out.println(message);  //message asks if mechanism did what it was programmed to do
        waitForResponse(passIsPressed, failIsPressed);  //waits until pass or fail is pressed
        if (passIsPressed) System.out.print(name + " passed."); //notifies if pass pressed and moves on
        else{ //fail is pressed
            nextAction(name, mechanism, carry); // nextAction branches into either 
         }
    }
    
    public void nextAction(String name, int mechanism, int carry) {
        switch(carry) {
            case 1: askForDisable(name, mechanism); break; //asks wether to disable mech
            case 2: shooterFailure(); break;    //shooter is complex and can be disabled partially or wholly
        }
    }
    
    public void askForDisable(String name, int mechanism) {
        System.out.print(name + " failed. /n Do you wish to disable the " + name + "?  Press pass or disable."); //asks wether you wish to disable mech
            waitForResponse(passIsPressed, disableIsPressed); //waits until pass or disable is pressed
            if (disableIsPressed) {
                System.out.println(name + " Disabled.");
                disable(mechanism);      //will disable the mechanism
            }
            else System.out.println(name + " still Enabled.");
    }
    
        //disables tested mechanism
    public void disable(int mechanism) {
        switch (mechanism) {
            //drive train
            case 1: dt.disable(); break;
            case 2: wedge.disable(); break;
            case 3: drawbridge.disable(); break;
            case 4: ss.disableRotation(); break; //disable turret rotation
            case 5: ss.disableVisor(); break; //disable turret visor
            case 6: ss.disable(); break; // if any part of the shooting mech, besides visor and rotation, fails it renders whole thing useless.
        }
    }
    
    public void shooterFailure() {
        //Option to disable whole shooter
        System.out.println("Do you wish to disable whole Shooter?  Press pass or disable.");
        waitForResponse(passIsPressed, disableIsPressed);
        if (disableIsPressed) {
            disable(6);  //disables whole shooter mech
            System.out.println("Shooter disabled.");
        } 
        //If Shooter remains enabled option to disable Rotation of Turret, and Visor
        else {
            System.out.println("Shooter still enabled.");
            //Option to disable Rotation
            specificFailure("Turret Rotation", 4, "Do you wish to disable Turret Rotation? Press pass or disable.");  
            //Option to disble Visor
            specificFailure("Turret Visor", 5, "Do you wish to disable Turret Visor? Press pass or disable.");
        }
    }
    
    //method that tests asks wether to disable a specific mechanism
    public void specificFailure(String name, int mechanism, String message) {
        System.out.println(message);
        waitForResponse(passIsPressed, disableIsPressed);
        if (disableIsPressed) {
            disable(mechanism);  
            System.out.println(name + " disabled.");
        }
        else System.out.println(name + " still enabled.");
    } 
        
    //does previous commands for a certain amount of time
    public void wait(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    //a method for waiting until one of two buttons is pressed
    public void waitForResponse(boolean button1, boolean button2) {
        while (!button1 && !button2) {  //while neither button is pressed
            try {
            Thread.sleep(5);
            } catch (InterruptedException ex) {
            ex.printStackTrace();
            }
        }
    }
    
    public void startListening() {
        joystick.addButtonListener(this);
    }

    public void stopListening() {
        joystick.removeButtonListener(this);
    }
    
    public void buttonPressed(ButtonEvent e) {
        if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_RIGHT) passIsPressed = true;  
        else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_LEFT) failIsPressed = true; 
        else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_DOWN) disableIsPressed = true;  
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_RIGHT) passIsPressed = false;  
        else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_LEFT) failIsPressed = false;  
        else if (e.getButtonID() == GRTBGSystemsFXJoystick.KEY_GRAY_DOWN) disableIsPressed = false;  
    }
}