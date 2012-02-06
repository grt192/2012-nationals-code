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
import sensor.GRTAttack3Joystick;



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
    private final GRTAttack3Joystick joystick;
    private final GRTDriveTrain dt;  
    private final Wedge wedge;
    private final Drawbridge drawbridge;
    private final ShootingSystem ss;
    private String name;
    
    public MechTester(GRTAttack3Joystick joystick, GRTDriveTrain dt, Wedge wedge, Drawbridge drawbridge, ShootingSystem ss, String dtname) {
        super(dtname);
        this.joystick = joystick;
        this.dt = dt;
        this.wedge = wedge;
        this.drawbridge = drawbridge;
        this.ss = ss;
    }
    
    // the main method of the MechTester - this is where the methods we test can be changed.
    public void run(){
        
//****************************************Test Drive Train*************************************************************\\
        
        dt.tankDrive(.2, .2); //go forward
        wait(500);
        dt.tankDrive(0.0,0.0);
        mechTest("Drive Train",1, "Did the Drive Train Go Forward, then Stop?  Press pass or fail.");
        
        
//****************************************Test Wedge*******************************************************************\\
        
        wedge.lowerWedge();
        wedge.raiseWedge();
        mechTest("Wedge", 2, "Did the Wedge lower, then raise?  Press pass or fail.");
        
        
//****************************************Test Drawbridge**************************************************************\\
        
        drawbridge.lowerArm();
        drawbridge.raiseArm();
        mechTest("Drawbridge", 3, "Did the Drawbridge lower, then raise?  Press pass or fail.");
        
        
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
        mechTest("ShootingSystem", 4, "What should have happened: "
                + "/n -ball collector worked for 4 seconds "
                + "/n -bottom transition for 6 seconds "
                + "/n -rotation of turret moved left then right"
                + "/n -visor moved up then back "
                + "/n -flywheel sped up in about 3 seconds"
                + "/n -top transition shot the ball"
                + "/n  Press pass or fail.");
        
    }            
    
    //a method with the reoccurring logic of testing a mechanism - asks wether pass/fail and if fail then asks pass or disable
    public void mechTest(String name, int mechanism, String message) {
        this.name = name;  //for clearer messages
        System.out.println(message);  //message asks if mechanism did what it was programmed to do
        waitForResponse(passIsPressed, failIsPressed);  //waits until pass or fail is pressed
        if (passIsPressed) System.out.print(name + " passed.");
        else{ //fail is pressed
            System.out.print(name + " failed. /n Do you wish to disable the " + name + "?  Press pass or disable."); //asks wether you wish to disable it
            waitForResponse(passIsPressed, disableIsPressed); //waits until pass or disable is pressed
            if (disableIsPressed) {
                System.out.println(name + " Disabled.");
                consequence(mechanism);      
            }
            else System.out.println(name + " still Enabled.");
         }
    }
        
    public void shooterFailure() {
        //Option to disable whole shooter
        System.out.println("Do you wish to disable whole Shooter?  Press pass or disable.");
        waitForResponse(passIsPressed, disableIsPressed);
        if (disableIsPressed) {
            consequence(7);  //disables whole shooter mech
            System.out.println("Shooter disabled.");
        } 
        //If Shooter remains enabled option to disable Rotation of Turret, and Visor
        else {
            System.out.println("Shooter still enabled.");
            //Option to disable Rotation
            specificFailure("Turret Rotation", 5, "Do you wish to disable Turret Rotation? Press pass or disable.");  
            //Option to disble Visor
            specificFailure("Turret Visor", 6, "Do you wish to disable Turret Visor? Press pass or disable.");
        }
    }
    
    public void specificFailure(String name, int mechanism, String message) {
        System.out.println(message);
        waitForResponse(passIsPressed, disableIsPressed);
        if (disableIsPressed) {
            consequence(mechanism);  
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
    
    //disables tested mechanism
    public void consequence(int mechanism) {
        switch (mechanism) {
            //drive train
            case 1: dt.disable(); break;
            case 2: wedge.disable(); break;
            case 3: drawbridge.disable(); break;
            case 4: shooterFailure(); break;    //part of the shooter failed - seperates into 3 categories: 
                                                //the turret rotation failed - can be disabled on own
                                                //the turret visor failed - can be disabled on own
                                                //any other part of the shooting mech fails - whole thing useless, can be disabled
            case 5: ss.disableRotation(); break; //disable turret rotation
            case 6: ss.disableVisor(); break; //disable turret visor
            case 7: ss.disable(); break; // if any part of the shooting mech, besides visor and rotation, fails it renders whole thing useless.
        }
    }

    
    public void startListening() {
        joystick.addButtonListener(this);
    }

    public void stopListening() {
        joystick.removeButtonListener(this);
    }
    
    public void buttonPressed(ButtonEvent e) {
        if (e.getButtonID() == 1) passIsPressed = true;  //KEY_BUTTON_2
        else if (e.getButtonID() == 2) failIsPressed = true; //KEY_BUTTON_3
        else if (e.getButtonID() == 3) disableIsPressed = true;   //KEY_BUTTON_4
    }

    public void buttonReleased(ButtonEvent e) {
        if (e.getButtonID() == 1) passIsPressed = false;  //KEY_BUTTON_2
        else if (e.getButtonID() == 2) failIsPressed = false;  //KEY_BUTTON_3
        else if (e.getButtonID() == 3) disableIsPressed = false;  //KEY_BUTTON_4
    }
}