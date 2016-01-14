package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// AutonomousBlue
//

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

/**
 * Provide autonomous operation that uses the left and rightdrive motors
 * and associated encoders implemented using a state machine for
 * the Push Bot.
 *
 */

public class AutonomousBlue extends PushBotTelemetry
{
//--------------------------------------------------------------------------
//
// AutonomousBlue
//
public AutonomousBlue ()

        {
        } // AutonomousBlue

final static double ARM_MIN_RANGE  = 0.00;
final static double ARM_MAX_RANGE  = 1.00;
final static double BUCKET_HOLD  = 0.00;
final static double BUCKET_DUMP  = 1.00;


public Servo bucket;
public Servo arm;

        // position of the arm servo.
        double armPosition;

        // amount to change the arm servo position.
        double armDelta = 0.005;


//--------------------------------------------------------------------------
//
// v_state
//
/**
 * This class member remembers which state is currently active.  When the
 * start method is called, the state will be initialized (0).  When the loop
 * starts, the state will change from initialize to state_1.  When state_1
 * actions are complete, the state will change to state_2.  This implements
 * a state machine for the loop method.
 */
private int v_state = 0;

//--------------------------------------------------------------------------
//
// start
//
/**
 * Perform any actions that are necessary when the OpMode is enabled.
 *
 * The system calls this member once when the OpMode is enabled.
 */
@Override public void start ()

        {
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_drive_encoders ();

        arm = hardwareMap.servo.get("arm");
        bucket = hardwareMap.servo.get("bucket");

        // assign the starting position of the arm and bucket variables
        // Retract the arm
        arm.setPosition(ARM_MIN_RANGE);
        // Set the bucket
        bucket.setPosition(BUCKET_HOLD);

        } // start

//--------------------------------------------------------------------------
//
// loop
//
/**
 * Implement a state machine that controls the robot during auto-operation.
 * The state machine uses a class member and encoder input to transition
 * between states.
 *
 * The system calls this member repeatedly while the OpMode is running.
 */
@Override public void loop ()

        {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
        //
        // Synchronize the state machine and hardware.
        //
        case 0:
        //
        // Reset the encoders to ensure they are at a known good value.
        //
        reset_drive_encoders ();

        //
        // Transition to the next state when this method is called again.
        //
        v_state++;

        break;
        //
        // Drive forward straight until the encoders exceed the specified values.
        //
        case 1:
        //
        // Tell the system that motor encoders will be used.  This call MUST
        // be in this state and NOT the previous or the encoders will not
        // work.  It doesn't need to be in subsequent states.
        //
        run_using_encoders ();

        //
        // Start the drive wheel motors at half power.
        //
        set_drive_power (-.5f, -0.5f);

        //
        // Have the motor shafts turned the required amount?
        //
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        //
        if (have_drive_encoders_reached (18400d, 18400d))
        {
        //
        // Stop the motors.
        //
        set_drive_power(0.0f, 0.0f);

        //
        // Reset the encoders to ensure they are at a known good value.
        //
        reset_drive_encoders ();

        //
        // Transition to the next state when this method is called
        // again.
        //
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 2:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // Turn Right 90 deg.
        //
        case 3:
        run_using_encoders ();
        set_drive_power (0.5f, -0.5f);
        if (have_drive_encoders_reached (2750, 2750))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 4:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // straight 3' .
        //
        case 5:
        run_using_encoders ();
        set_drive_power (-0.5f, -0.5f);
        if (have_drive_encoders_reached (8000, 8000))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 6:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // Turn Right appx 45 deg.
        case 7:
        run_using_encoders ();
        set_drive_power (0.5f, -0.5f);
        if (have_drive_encoders_reached (1300, 1300))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 8:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // drive straight appx 42"
        case 9:
        run_using_encoders ();
        set_drive_power (-0.5f, -0.5f);
        if (have_drive_encoders_reached (9400, 9400))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 10:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // back up appx 33"
        case 11:
        run_using_encoders ();
        set_drive_power (0.5f, 0.5f);
        if (have_drive_encoders_reached (7200, 7200))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 12:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        // turn left appx 45 deg.
        case 13:
        run_using_encoders ();
        set_drive_power (-0.5f, 0.5f);
        if (have_drive_encoders_reached (1300, 1300))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 14:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        // drive straight 48"
        case 15:
        run_using_encoders ();
        set_drive_power (-0.5f, -0.5f);
        if (have_drive_encoders_reached (9900, 9900))
        {
        reset_drive_encoders ();
        set_drive_power (0.0f, 0.0f);
        v_state++;
        }
        break;
        //
        // Wait...
        //
        case 16:
        if (have_drive_encoders_reset ())
        {
        v_state++;
        }
        break;
        //
        case 17:
        // update the position of the arm.
        if (armPosition < ARM_MAX_RANGE-0.01) {
        // slowly step arm around
        // Extend the arm
        arm.setPosition(armPosition += armDelta);
        }
        else {
        v_state++;
        }
        break;

        //  dump
        case 18:

        // Dump the bucket
        bucket.setPosition(BUCKET_DUMP);

        break;
//
//
// Perform no action - stay in this case until the OpMode is stopped.
// This method will still be called regardless of the state machine.
// Wait...
//

default:
        //
        // The autonomous actions have been accomplished (i.e. the state has
        // transitioned into its final state.
        //
        break;
        }

        //
        // Send telemetry data to the driver station.
        //
        update_telemetry (); // Update common telemetry
        telemetry.addData ("18", "State: " + v_state);

        } // loop


        } // AutonomousRed
