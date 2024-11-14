package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "servo")
public class servoTest extends LinearOpMode{
    public void runOpMode() throws InterruptedException {

        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        CRServo servo2 = hardwareMap.get(CRServo.class,"servo2");
        boolean bool = false;
        waitForStart();
        while(opModeIsActive()){
            if(gamepad1.a){
                servo1.setPosition(0.5);
            }if(gamepad1.b){
                servo1.setPosition(1);
            }
            while(gamepad1.right_bumper){
                servo2.setPower(-1);
            }
            while(gamepad1.left_bumper){
                servo2.setPower(1);
            }
            servo2.setPower(0);
            //servo2.setPower(-1);
            telemetry.addData("rotation target", servo1.getPosition() + " degrees");
            telemetry.update();
        }


    }
}
