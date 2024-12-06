package org.firstinspires.ftc.teamcode.tuning;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "MecanumTest2")

public class SampleDumpSequence extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException
    {
        CRServo intake = hardwareMap.get(CRServo.class, "intake");
        Servo rotation = hardwareMap.get(Servo.class, "rotation");

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

        intake.setPower(0);

        while(opModeIsActive())
        {
            // on button press turn servo to home
            // set rotation target
            // go there
            // extend linear slide
            // turn servo to face basket
            // release sample
            // turn servo back to home
            // retract linear slide
            // set rotation to home
            // rotate home

            lastCycle = thisCycle;
            thisCycle = gamepad1.b;
            if (!lastCycle && thisCycle)
            {
                // declare variables
                final double CPR = 5281.1;                  // ticks per revolution
                final int POSITION_TOLERANCE = 25;          // (ticks) prevents infinite loop
                // TODO: update target position
                final double SERVO_UP = 1;
                final double SERVO_DUMP = 0;
                // TODO: value for intake dump
                final double INTAKE_DUMP = 0;
                final int ARM_UP = 1320;                    // position of arm up (ticks)
                final int ARM_DOWN = 0;                     // position of arm down (ticks)
                final int SLIDE_MAX = 3089;                 // empirical position of fully extended arm
                final int SLIDE_MIN = 0;

                double slideTarget = 0.0;                   // target position (0-1)

                // all of the below are in revolutions, corrected for direction
                int leftTarget, rightTarget, leftSlideTarget, rightSlideTarget;
                int leftPos = 0;
                int leftSlidePos = 0;
                double slideHold = 1.0;

                // turn servo home
                rotation.setPosition(SERVO_UP);

                // set rotation target
                left.setTargetPosition(-1 * ARM_UP);
                right.setTargetPosition(ARM_UP);
                left.setPower(-0.25);
                right.setPower(0.25);

                // go to rotation position
                while (Math.abs(ARM_UP-left.getCurrentPosition()) > POSITION_TOLERANCE)
                {
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                left.setPower(-0.1);
                right.setPower(0.1);

                // set slide target
                leftSlide.setTargetPosition(-1 * SLIDE_MAX);
                rightSlide.setTargetPosition(SLIDE_MAX);
                leftSlide.setPower(-0.25);
                rightSlide.setPower(0.25);

                // go there
                while (Math.abs(SLIDE_MAX-leftSlide.getCurrentPosition()) > POSITION_TOLERANCE)
                {
                    leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftSlide.setPower(-1 * slideHold);
                rightSlide.setPower(slideHold);


                // turn servo
                rotation.setPosition(SERVO_DUMP);

                // TODO: release sample
                intake.setPower(1);
                sleep(2500);
                intake.setPower(0);
                // turn servo home
                rotation.setPosition(SERVO_UP);


                // set slide pos
                leftSlide.setTargetPosition(-1 * SLIDE_MIN);
                rightSlide.setTargetPosition(SLIDE_MIN);
                leftSlide.setPower(-0.25);
                rightSlide.setPower(0.25);
                // go there
                while (Math.abs(SLIDE_MIN-leftSlide.getCurrentPosition()) > POSITION_TOLERANCE)
                {
                    leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

                // rotate home
                left.setTargetPosition(-1 * ARM_UP);
                right.setTargetPosition(ARM_UP);
                left.setPower(-0.25);
                right.setPower(0.25);
                // go there
                while (Math.abs(SLIDE_MAX-left.getCurrentPosition()) > POSITION_TOLERANCE)
                {
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
            }
        }
    }
}
