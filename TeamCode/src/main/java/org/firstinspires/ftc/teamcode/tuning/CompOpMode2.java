
package org.firstinspires.ftc.teamcode.tuning;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;


@TeleOp(name = "v2comp2")
public class CompOpMode2 extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotor rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        DcMotor left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotor right = hardwareMap.get(DcMotorEx.class, "right");

        telemetry.addData("hardware: ", "Initialize");
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


        //MecanumDrive Code
        double drive, turn, strafe;
        double fLeftPower, fRightPower, bLeftPower, bRightPower;
        DcMotor leftFront = hardwareMap.get(DcMotor.class, "LF");
        DcMotor rightFront = hardwareMap.get(DcMotor.class, "RF");
        DcMotor leftBack = hardwareMap.get(DcMotor.class, "LB");
        DcMotor rightBack = hardwareMap.get(DcMotor.class, "RB");
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Servos
        Servo servo1 = hardwareMap.get(Servo.class, "servo1");
        CRServo servo2 = hardwareMap.get(CRServo.class,"servo2");

        boolean lastCycle = false;                  // used with below to read button presses properly
        boolean thisCycle = false;
        boolean isDown = false;
        double slideTarget = 0.0;                   // target position (0-1)
        //final double CPR = 5281.1;                  // ticks per revolution

        waitForStart();

        while (opModeIsActive()) {

            final double POSITION_TOLERANCE = 0.05;     // prevents infinite loop
            // TODO: update target position
            final int SLIDE_MAX = 3089;                 // empirical position of fully extended arm
            double preciseMovement = 1.0;

            // all of the below are in revolutions, corrected for direction
            int leftSlideTarget, rightSlideTarget;
            int leftSlidePos = 0;
            int rightSlidePos = 0;

            if  (gamepad2.left_trigger > 0.5)
                preciseMovement = 0.2;
            else
                preciseMovement = 1.0;

            telemetry.addData("slide target", slideTarget + " rotations");
            telemetry.addData("slide position", rightSlidePos / 360 + " rotations");
            telemetry.addData("servo", servo2.getPower());
            telemetry.addData("precise movement", preciseMovement);
            telemetry.update();

            // calculate target for given angle
            leftSlideTarget = (int) (-1 * slideTarget * SLIDE_MAX);
            rightSlideTarget = (int) (slideTarget * SLIDE_MAX);

            // record current positions
            leftSlidePos = -1 * leftSlide.getCurrentPosition() / SLIDE_MAX;
            rightSlidePos = rightSlide.getCurrentPosition() / SLIDE_MAX;

            // set motor targets and powers
            leftSlide.setTargetPosition(leftSlideTarget);
            rightSlide.setTargetPosition(rightSlideTarget);
            leftSlide.setPower(-0.5);
            rightSlide.setPower(0.5);

            // if not in correct position, run rotation to that position
            if (Math.abs(leftSlideTarget-leftSlidePos) > POSITION_TOLERANCE)
            {
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // left stick changes target angle (0-MAX_ANGLE)
            if (slideTarget > 1)
                slideTarget = 1;
            else if (slideTarget < 0)
                slideTarget = 0;
            else if (gamepad1.left_stick_y > 0.5 || gamepad1.left_stick_y < -0.5)
                slideTarget -= (0.01 *  gamepad1.left_stick_y);


            // intake button
            lastCycle = thisCycle;
            thisCycle = gamepad1.b;
            if (lastCycle && thisCycle) {
                final double SERVO_UP = 1;
                final double SERVO_DUMP = 0;
                final int ARM_UP = 1300;                    // position of arm up (ticks)
                final int ARM_DOWN = 0;                     // position of arm down (ticks)
                final int SLIDE_MIN = 15;
                double speed = 1;

                //rotates 90 deg
                left.setTargetPosition(ARM_UP);
                right.setTargetPosition(-1 * ARM_UP);
                left.setPower(-1 * speed);
                right.setPower(speed);
                while (ARM_UP != right.getCurrentPosition()) {
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                left.setPower(-0.05);
                right.setPower(0.05);

                //slides to top
                leftSlide.setTargetPosition(-1 * SLIDE_MAX);
                rightSlide.setTargetPosition(SLIDE_MAX);
                leftSlide.setPower(-1 * speed);
                rightSlide.setPower(speed);
                while (SLIDE_MAX != rightSlide.getCurrentPosition()) {
                    leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftSlide.setPower(-0.1);
                rightSlide.setPower(0.1);

                // rotate servos
                servo1.setPosition(SERVO_DUMP);

                // intake
                servo2.setPower(speed);
                sleep(2500);
                servo2.setPower(0);

                // turn servo home
                servo1.setPosition(SERVO_UP);

                //slides to bottom
                leftSlide.setTargetPosition(-1 * SLIDE_MIN);
                rightSlide.setTargetPosition(SLIDE_MIN);
                leftSlide.setPower(-1 * speed);
                rightSlide.setPower(speed);
                while (SLIDE_MIN != rightSlide.getCurrentPosition()) {
                    leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                leftSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                rightSlide.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                leftSlide.setPower(0.1);
                rightSlide.setPower(-0.1);

                //rotate 0 deg
                left.setTargetPosition(ARM_DOWN);
                right.setTargetPosition(-1 * ARM_DOWN);
                left.setPower(-0.7 * speed);
                right.setPower(0.7 * speed);
                while (ARM_DOWN != right.getCurrentPosition()) {
                    left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

                rightSlide.setPower(0);
            }

            if(gamepad1.a)
            {
                isDown = !isDown;
                if(isDown)
                {
                    leftSlide.setTargetPosition(-1 * 500);
                    rightSlide.setTargetPosition(500);
                    leftSlide.setPower(-0.5);
                    rightSlide.setPower(0.5);
                    while (500 != rightSlide.getCurrentPosition()) // 500 is 12 in. extended
                    {
                        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                    servo1.setPosition(0.5);
                }
                else
                {
                    servo1.setPosition(0);
                    leftSlide.setTargetPosition(0);
                    rightSlide.setTargetPosition(0);
                    leftSlide.setPower(-0.5);
                    rightSlide.setPower(0.5);
                    while (0 != rightSlide.getCurrentPosition()) // 500 is 12 in. extended
                    {
                        leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                }
            }

            if(gamepad2.right_bumper)
                servo2.setPower(-0.333);
            else if (gamepad2.left_bumper)
                servo2.setPower(0.333);
            else
                servo2.setPower(0);

            //MecanumDrive Code
            drive = gamepad2.left_stick_y * 0.75 * preciseMovement;
            turn = gamepad2.right_stick_x *-1* preciseMovement;
            strafe = gamepad2.left_stick_x * preciseMovement;

            //strafe
            fLeftPower = drive + turn + strafe;
            fRightPower = drive - turn - strafe;
            bLeftPower = drive + turn - strafe;
            bRightPower = drive - turn + strafe;

            double[] appliedPowers = scalePowers(fLeftPower, fRightPower, bLeftPower, bRightPower);

            leftFront.setPower(appliedPowers[0]);
            leftBack.setPower(appliedPowers[2]);
            rightFront.setPower(appliedPowers[1]);
            rightBack.setPower(appliedPowers[3]);
        }

    }

    public double[] scalePowers(double fLeftPower, double fRightPower, double bLeftPower, double bRightPower) {
        double max = Math.max(Math.abs(fLeftPower), Math.max(Math.abs(fRightPower), Math.max(Math.abs(bLeftPower), Math.abs(bRightPower))));
        if (max > 1) {
            fLeftPower /= max;
            fRightPower /= max;
            bLeftPower /= max;
            bRightPower /= max;
        }

        return new double[]{fLeftPower, fRightPower, bLeftPower, bRightPower};

    }
}
