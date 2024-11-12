
package org.firstinspires.ftc.teamcode.tuning;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp(name = "MecanumTest")
public class CompOpMode extends LinearOpMode {



    @Override
    public void runOpMode() throws InterruptedException {
        //Hware hware = new Hware(hardwareMap);
        //DcMotor motor;
        //tick value - 537.7
        //5,281.1 - new tick value
        final double TPR = 5281.1;                  // ticks per revolution
        final double POSITION_TOLERANCE = 0.05;     // prevents infinite loop
        // TODO: update target position
        final double SLIDE_TARGET = 0.5;            // slide position to go to on button press
        final int SLIDE_MAX = 3089;                 // empirical position of fully extended arm
        // TODO: update max angle
        final int MAX_ANGLE = 90;                   // degrees

        double revolutions = 0.0;                   // used for calculation of rotation angle
        double angle = 0.0;                         // degrees
        double slideTarget = 0.0;                   // target position (0-1)
        // all of the below are in revolutions, corrected for direction
        int leftTarget, rightTarget, leftSlideTarget, rightSlideTarget;
        int leftPos = 0;
        int rightPos = 0;
        int leftSlidePos = 0;
        int rightSlidePos = 0;

        boolean lastCycle = false;                  // used with below to read button presses properly
        boolean thisCycle = false;

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

        waitForStart();

        while (opModeIsActive()) {
            // updates with current position
            telemetry.addData("rotation target", angle + " degrees");
            telemetry.addData("rotation position", rightPos + " degrees");
            telemetry.addData("rotation power", right.getPower());
            telemetry.addData("slide target", slideTarget + " rotations");
            telemetry.addData("slide position", rightSlidePos / 360 + " rotations");
            telemetry.addData("slide power", rightSlide.getPower());
            telemetry.addData("left joystick", gamepad1.left_stick_y);
            telemetry.addData("right joystick", gamepad1.right_stick_y);
            telemetry.update();

            // revolutions used to compare target to pos - easier to convert
            // angle used for user input - easier to visualize
            // gear ratio of 3
            revolutions = (angle / 360) / 3;

            // calculate target for given angle
            leftTarget = (int) (-1 * revolutions * TPR);
            rightTarget = (int) (revolutions * TPR);

            // record current positions
            leftPos = (int) (-1 * left.getCurrentPosition() % TPR);
            rightPos = (int) (right.getCurrentPosition() % TPR);

            // set motor targets and powers
            left.setTargetPosition(leftTarget);
            right.setTargetPosition(rightTarget);
            left.setPower(-0.25);
            right.setPower(0.25);

            // if not in correct position, run rotation to that position
            if (Math.abs(leftTarget-leftPos) > POSITION_TOLERANCE)
            {
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // left stick changes target angle (0-MAX_ANGLE)
            if (angle > MAX_ANGLE)
                angle = MAX_ANGLE;
            else if (angle < 0)
                angle = 0;
            else if (gamepad1.left_stick_y > 0.5 || gamepad1.left_stick_y < -0.5)
                angle -= gamepad1.left_stick_y;

            // press b to go to position 0.5
            lastCycle = thisCycle;
            thisCycle = gamepad1.b;
            if (!lastCycle && thisCycle)
                slideTarget = SLIDE_TARGET;

            // calculate target for given angle
            leftSlideTarget = (int) (-1 * slideTarget * SLIDE_MAX);
            rightSlideTarget = (int) (slideTarget * SLIDE_MAX);

            // record current positions
            leftSlidePos = (int) (-1 * leftSlide.getCurrentPosition() / SLIDE_MAX);
            rightSlidePos = (int) (rightSlide.getCurrentPosition() / SLIDE_MAX);

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
            else if (gamepad1.right_stick_y > 0.5 || gamepad1.right_stick_y < -0.5)
                slideTarget -= (0.01 *  gamepad1.right_stick_y);

            //MecanumDrive Code
            drive = gamepad2.left_stick_y;
            turn = gamepad2.right_stick_x;
            strafe = gamepad2.left_stick_x;

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

        double[] motorPowers = new double[]{fLeftPower, fRightPower, bLeftPower, bRightPower};
        return motorPowers;

    }

}
