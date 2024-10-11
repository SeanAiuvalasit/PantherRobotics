package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "Motor Test2")
public class motorTest extends OpMode {
 //   HardwareMap hware = new HardwareMap();
    DcMotor motor;


    @Override
    public void init(){
        motor = hardwareMap.get(DcMotor.class, "testMotor2");

        telemetry.addData("hardware: ", "Initialize");
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setDirection(DcMotor.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void driverControl() {

        if (gamepad1.cross){
            motor.setPower(1);
        }
    }
    @Override
    public void loop(){
        driverControl();
    }
}
