package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;


public class TeleOp extends OpMode {

    final static double ARM_MIN_RANGE  = 0.00;
    final static double ARM_MAX_RANGE  = 1.00;
    final static double BUCKET_HOLD  = 0.00;
    final static double BUCKET_DUMP  = 1.00;

    // position of the arm servo.
    double armPosition;

    // amount to change the arm servo position.
    double armDelta = 0.005;

    // position of the bucket servo
    double bucketPosition;

    public DcMotor leftMotor;
    public DcMotor rightMotor;
    public Servo bucket;
    public Servo arm;


    @Override
    public void init(){
        leftMotor = hardwareMap.dcMotor.get("left_drive");
        rightMotor = hardwareMap.dcMotor.get("right_drive");
        leftMotor.setDirection(DcMotor.Direction.FORWARD);
        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        arm = hardwareMap.servo.get("arm");
        bucket = hardwareMap.servo.get("bucket");

        // assign the starting position of the arm and bucket variables
        armPosition = 0.0;
        bucketPosition = 0.0;

    }
    @Override
    public void loop(){
        double leftStick1=gamepad1.left_stick_y;
        double rightStick1=gamepad1.right_stick_y;

        double leftDrivePower=0;
        double rightDrivePower=0;

        leftDrivePower = Range.clip(rightStick1,-1,1);
        rightDrivePower = Range.clip(leftStick1,-1,1);

        leftMotor.setPower(leftDrivePower);
        rightMotor.setPower(rightDrivePower);

        // update the position of the arm.
        if (gamepad2.dpad_right) {
            // if the A button is pushed on gamepad1, increment the position of
            // the arm servo.
            armPosition += armDelta;
        }

        if (gamepad2.dpad_left) {
            // if the Y button is pushed on gamepad1, decrease the position of
            // the arm servo.
            armPosition -= armDelta;
        }

        // update the position of the claw
        if (gamepad2.a) {
            bucketPosition = BUCKET_DUMP;
        }

        else {
            bucketPosition = BUCKET_HOLD;
        }

        // clip the position values so that they never exceed their allowed range.
        armPosition = Range.clip(armPosition, ARM_MIN_RANGE, ARM_MAX_RANGE);

        // write position values to the wrist and claw servo
        arm.setPosition(armPosition);
        bucket.setPosition(bucketPosition);


        telemetry.addData("Text", "*** Robot Data***");
        telemetry.addData("arm", "arm:  " + String.format("%.2f", armPosition));
        telemetry.addData("claw", "claw:  " + String.format("%.2f", bucketPosition));
    }

}
