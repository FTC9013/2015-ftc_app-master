package com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;


/**
 * Created by Leutbecker.Leslie on 11/14/2015.
 */
public class TeleOp extends OpMode {

    public DcMotor leftMotor;
    public DcMotor rightMotor;

    @Override
    public void init(){
        leftMotor = hardwareMap.dcMotor.get("left");
        rightMotor = hardwareMap.dcMotor.get("right");
        leftMotor.setDirection(DcMotor.Direction.REVERSE);
        rightMotor.setDirection(DcMotor.Direction.FORWARD);
    }
    @Override
    public void loop(){
        double leftStick=gamepad1.left_stick_y;
        double rightStick=gamepad1.right_stick_y;
        double leftDrivePower=0;
        double rightDrivePower=0;

        leftDrivePower = Range.clip(leftStick,-1,1);
        rightDrivePower = Range.clip(rightStick,-1,1);

        leftMotor.setPower(leftDrivePower);
        rightMotor.setPower(rightDrivePower);
    }

}
