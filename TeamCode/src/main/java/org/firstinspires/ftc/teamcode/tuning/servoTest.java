package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
public class servoTest extends LinearOpMode{

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotor leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
        DcMotor rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");

        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setDirection(DcMotor.Direction.FORWARD);
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setDirection(DcMotor.Direction.FORWARD);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
/*
        Servo leftX = hardwareMap.get(Servo.class, "leftX");
        Servo rightX = hardwareMap.get(Servo.class, "rightX");
        Servo leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
        Servo rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
*/
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("left pos: ", leftSlide.getCurrentPosition());
            telemetry.update();

            leftSlide.setTargetPosition(500);
            leftSlide.setPower(0.25);
            leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            /*
            if(gamepad1.a)
            {
                leftX.setPosition(0.25);
                sleep(1000);
                leftX.setPosition(0.75);
                sleep(2000);
                rightX.setPosition(0.25);
                sleep(1000);
                rightX.setPosition(0.75);
            }

            if(gamepad1.b)
            {
                leftClawPos.setPosition(0.25);
                sleep(1000);
                leftClawPos.setPosition(0.75);
                sleep(2000);
                rightClawPos.setPosition(0.25);
                sleep(1000);
                rightClawPos.setPosition(0.75);
            }
             */

/*
            if(gamepad1.a)
                rightSlide.setPower(0.1);
            else if (gamepad1.b)
                rightSlide.setPower(-0.1);
            else
                rightSlide.setPower(0);

            if(gamepad1.x)
                leftSlide.setPower(0.1);
            else if (gamepad1.y)
                leftSlide.setPower(-0.1);
            else
                leftSlide.setPower(0);

 */
        }
    }
}
