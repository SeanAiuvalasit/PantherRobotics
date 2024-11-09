package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "linearTest")
public class linearTest extends LinearOpMode {
    //Hware hware = new Hware(hardwareMap);
    //DcMotor motor;
    //tick value - 537.7
    //5,281.1 - new tick value
    final double TPR = 5281.1; // ticks per revolution
    final double POSITION_TOLERANCE = 0.05; // prevents infinite loop
    final int SLIDE_MAX = 3089;
    double revolutions = 0.0; // used for calculation of rotation angle
    double angle = 0.0; // degrees
    double slideTarget = 0.0; // target position (0-1)
    int leftTarget, rightTarget, leftSlideTarget, rightSlideTarget,
        leftPos, rightPos, leftSlidePos, rightSlidePos; // all corrected for direction/scale

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


        waitForStart();

        while(opModeIsActive()){
            // updates with current position
            telemetry.addData("pos", slideTarget );
            telemetry.update();

            // revolutions used to compare target to pos - easier to convert
            // angle used for user input - easier to visualize
            // gear ratio of 3
            angle = 90;
            revolutions = angle / 360 / 3;

            // calculate target for given angle
            leftTarget = (int) (-1 * revolutions * TPR);
            rightTarget = (int) (revolutions * TPR);
            leftSlideTarget = (int) (-1 * slideTarget * SLIDE_MAX);
            rightSlideTarget = (int) (slideTarget * SLIDE_MAX);

            // record current positions
            leftPos = (int) (-1 * left.getCurrentPosition() % TPR);
            rightPos = (int) (right.getCurrentPosition() % TPR);
            leftSlidePos = (int) (-1 * leftSlide.getCurrentPosition() / SLIDE_MAX);
            rightSlidePos = (int) (rightSlide.getCurrentPosition() / SLIDE_MAX);

            // set motor targets and powers
            left.setTargetPosition(leftTarget);
            right.setTargetPosition(rightTarget);
            leftSlide.setTargetPosition(leftSlideTarget);
            rightSlide.setTargetPosition(rightSlideTarget);

            left.setPower(0.1);
            right.setPower(0.1);
            leftSlide.setPower(0.15);
            rightSlide.setPower(0.15);

            // if not in correct position, run rotation to that position
            if (Math.abs(leftTarget-leftPos) > POSITION_TOLERANCE)
            {
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // if not in correct position, run slide to that position
            if (Math.abs(leftSlideTarget-leftPos) > POSITION_TOLERANCE)
            {
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            /*
            // dpad up increases angle, dpad down decreases angle
            // angle must be within 0 and 90 degrees
            if (angle > 90)
            {
                angle = 90;
            }
            else if (angle < 0)
            {
                angle = 0;
            }
            else
            {
                if (gamepad1.dpad_up)
                {
                    angle++;
                }
                else if (gamepad1.dpad_down)
                {
                    angle--;
                }
            }
             */

            if (slideTarget > 1)
            {
                slideTarget = 1;
            }
            else if (slideTarget < 0)
            {
                slideTarget = 0;
            }
            else
            {
                if (gamepad1.y)
                {
                    slideTarget += 0.005;
                }
                else if (gamepad1.a)
                {
                    slideTarget -= 0.005 ;
                }
            }
        }
    }
}

            /*
             //Mecanum drive code
             *double drive, turn, strafe;
             *
             //under waitForStop()
             *
             *  drive = gamepad1.left_stick_y * -1;
             *  turn = gamepad1.right_stick_x;
             *  strafe = gamepad1.left_stick_x;
             *
             *  //strafe
             *  fLeftPower = drive + turn + strafe;
             *  fRightPower = drive - turn - strafe;
             *  bLeftPower = drive + turn - strafe;
             *  bRightPower = drive - turn + strafe;
             *
             *  double[] appliedPowers = scalePowers(fLeftPower, fRightPower, bLeftPower, bRightPower);
             *
             *  leftFront.setPower(appliedPowers[0]);
             *  leftBack.setPower(appliedPowers[2]);
             *  rightFront.setPower(appliedPowers[1]);
             *  rightBack.setPower(appliedPowers[3]);
             *
             *
             //outside of opModeIsActive()
             * public double[] scalePowers(double fLeftPower, double fRightPower, double bLeftPower, double bRightPower){
             *      double max = Math.max(Math.abs(fLeftPower), Math.max(Math.abs(fRightPower), Math.max(Math.abs(bLeftPower), Math.max(Math.abs(bRightPower))));
             *      if(max > 1){
             *          fLeftPower /= max;
             *          fRightPower /= max;
             *          bLeftPower /= max;
             *          bRightPower /= max;
             *      }
             *
             *      double [] motorPowers = new double[]{fLeftPower, fRightPower, bLeftPower, bRightPower};
             *      return motorPowers;
            */