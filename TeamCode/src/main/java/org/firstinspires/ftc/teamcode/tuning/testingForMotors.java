package org.firstinspires.ftc.teamcode.tuning;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "sampleDumpSequence2.0")

public class testingForMotors extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        //  CRServo intake = hardwareMap.get(CRServo.class, "intake");
        //  Servo rotation = hardwareMap.get(Servo.class, "rotation");

        DcMotor leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotor rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        DcMotor left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotor right = hardwareMap.get(DcMotorEx.class, "right");

        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setDirection(DcMotor.Direction.FORWARD);
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setDirection(DcMotor.Direction.FORWARD);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotor.Direction.FORWARD);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setDirection(DcMotor.Direction.FORWARD);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        boolean lastCycle = false;                  // used with below to read button presses properly
        boolean thisCycle = false;

        //  intake.setPower(0);
        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("height", rightSlide.getCurrentPosition() + "woo");
            telemetry.update();
            lastCycle = thisCycle;
            thisCycle = gamepad1.b;
            if (lastCycle && thisCycle) {
                // declare variables
                final double CPR = 5281.1;                  // ticks per revolution
                final int POSITION_TOLERANCE = 25;          // (ticks) prevents infinite loop
                // TODO: update target position
                final double SERVO_UP = 1;
                final double SERVO_DUMP = 0;
                // TODO: value for intake dump
                final double INTAKE_DUMP = 0;
                final int ARM_UP = 1300;                    // position of arm up (ticks)
                final int ARM_DOWN = 0;                     // position of arm down (ticks)
                final int SLIDE_MAX = 2855/**3089*/;                 // empirical position of fully extended arm
                final int SLIDE_MIN = 15;

                double slideTarget = 0.0;                   // target position (0-1)

                // all of the below are in revolutions, corrected for direction
                int leftTarget, rightTarget, leftSlideTarget, rightSlideTarget;
                int leftPos = 0;
                int leftSlidePos = 0;
                double slideHold = 0.05;
                double speed = 1;

                //rotates 90 deg
                right.setTargetPosition(ARM_UP);
                right.setPower(speed);
                while (ARM_UP != right.getCurrentPosition()) {
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                right.setPower(0.05);

                //slides to top
                rightSlide.setTargetPosition(SLIDE_MAX);
                rightSlide.setPower(speed);
                while (SLIDE_MAX != rightSlide.getCurrentPosition()) {
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setPower(0.1);

                //rotate servos
                /**rotation.setPosition(SERVO_DUMP);

                 // TODO: release sample
                 intake.setPower(1);
                 sleep(2500);
                 intake.setPower(0);

                 // turn servo home
                 rotation.setPosition(SERVO_UP);
                 */
                sleep(2500);
                //slides to bottom
                rightSlide.setTargetPosition(SLIDE_MIN);
                rightSlide.setPower(speed);
                while (SLIDE_MIN != rightSlide.getCurrentPosition()) {
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setPower(-0.1);

                //rotate 0 deg
                right.setTargetPosition(ARM_DOWN);
                right.setPower(0.7*speed);
                while (ARM_DOWN != right.getCurrentPosition()) {
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

                rightSlide.setPower(0);
            }
        }
    }
}