
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
        final double SLIDE_TARGET_NINETY_DEGREES = 0.5;            // slide position to go to on button press gamepad1.b
        final double SLIDE_TARGET_SEVENTY_DEGREES = 0.4;            // slide position to go to on button press gamepad1.a
        final int SLIDE_MAX = 3089;                 // empirical position of fully extended arm
        // TODO: update max angle
        final int MAX_ANGLE = -118;                   // degrees

        double revolutions = 0.0;                   // used for calculation of rotation angle
        double angle = 0.0;                         // degrees
        double slideTarget = 0.0;                   // target position (0-1)
        double preciseMovement = 1.0;
        // all of the below are in revolutions, corrected for direction
        int leftTarget, rightTarget, leftSlideTarget, rightSlideTarget;
        int leftPos = 0;
        int rightPos = 0;
        int leftSlidePos = 0;
        int rightSlidePos = 0;

        boolean lastCycle = false;                  // used with below to read button presses properly
        boolean thisCycle = false;

        boolean lastCycle2 = false;                 //used with button reads below
        boolean thisCycle2 = false;

        boolean slide = false;
        boolean slide2 = false;

        boolean rightBumper = false;
        boolean leftBumper = false;

        boolean precise = false;

        DcMotorEx leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotorEx rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        DcMotorEx left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotorEx right = hardwareMap.get(DcMotorEx.class, "right");

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
    //    Servo servo1 = hardwareMap.get(Servo.class, "servo1");
    //    CRServo servo2 = hardwareMap.get(CRServo.class,"servo2");



        waitForStart();

        while (opModeIsActive()) {

            if(gamepad2.left_trigger > 0.5){
                preciseMovement = 0.2;
            }else{
                preciseMovement = 1.0;
            }

            if(gamepad1.dpad_down){

                left.setPower(-0.0000000000000000001);
                right.setPower(0.0000000000000000001);
                leftSlide.setPower(-0.0000000000000000001);
                rightSlide.setPower(0.0000000000000000001);

                angle = -19.78510985; // Update angle
      //          servo1.setPosition(0.765);

                slide = true; // Set control variable to true

            }

// Separate logic to update slideTarget after angle is updated
            if (slide) {
                slideTarget = 0.26989865; // Update slideTarget
                slide = false; // Reset control variable
            }

            if(gamepad1.dpad_left){
                left.setPower(-0.0000000000000000001);
                right.setPower(0.0000000000000000001);
                leftSlide.setPower(-0.0000000000000000001);
                rightSlide.setPower(0.0000000000000000001);


                angle = -42.78510985; // Update angle
     //           servo1.setPosition(0.765);

                slide = true; // Set control variable to true
            }

// Separate logic to update slideTarget after angle is updated
            if (slide) {
                slideTarget = 0.523; // Update slideTarget
                slide = false; // Reset control variable
            }

            if(gamepad1.dpad_up){
                left.setPower(-0.000000000000000000000001);
                right.setPower(0.000000000000000000000001);
                leftSlide.setPower(-0.000000000000000000000001);
                rightSlide.setPower(0.000000000000000000000001);

                angle = -115; // update angle
         //       servo1.setPosition(0.50);

                slide2 = true;
            }

            if(slide2){
                slideTarget = 0.9;
                slide2 = false;
            }

      /**      if(gamepad1.a){
                servo1.setPosition(0.5);
            }if(gamepad1.b){
                servo1.setPosition(0.7);
            }if(gamepad1.y){
                servo1.setPosition(1);
            }

            if(gamepad2.dpad_up){
                if(servo1.getPosition() > 1)
                {
                    servo1.setPosition(1);
                }else{
                    servo1.setPosition(servo1.getPosition() + 0.001);
                    }
                }

            if(gamepad2.dpad_down){
                if(servo1.getPosition() < 0.5){
                    servo1.setPosition(0.5);
                }else{
                    servo1.setPosition(servo1.getPosition() - 0.001);
                }
            }

            if(gamepad2.right_bumper){
               // rightBumper = true;
                servo2.setPower(-0.333);
            }else if (gamepad2.left_bumper){
                servo2.setPower(0.333);
            }else
                servo2.setPower(0);

            //servo2.setPower(-1);
            telemetry.addData("rotation target", servo1.getPosition() + " degrees");
            telemetry.update();


            // updates with current position
            telemetry.addData("rotation target", angle + " degrees");
            telemetry.addData("rotation position", rightPos + " degrees");
        //    telemetry.addData("rotation power", right.getPower());
            telemetry.addData("slide target", slideTarget + " rotations");
            telemetry.addData("slide position", rightSlidePos / 360 + " rotations");
         //   telemetry.addData("right slide power", rightSlide.getPower());
         //   telemetry.addData("left slide power", leftSlide.getPower());
         //   telemetry.addData("left joystick", gamepad1.left_stick_y);
         //   telemetry.addData("right joystick", gamepad1.right_stick_y);
            telemetry.addData("servo", servo2.getPower());
            telemetry.addData("precise movement", preciseMovement);
            telemetry.update();
*/
            // revolutions used to compare target to pos - easier to convert
            // angle used for user input - easier to visualize
            // gear ratio of 3
            revolutions = (angle / 360);

            // calculate target for given angle
            leftTarget = (int) (-1 * revolutions * TPR);
            rightTarget = (int) (revolutions * TPR);

            // record current positions
            leftPos = (int) (-1 * left.getCurrentPosition() % TPR);
            rightPos = (int) (right.getCurrentPosition() % TPR);

            // set motor targets and powers
            left.setTargetPosition(leftTarget);
            right.setTargetPosition(rightTarget);
            left.setPower(-1);
            right.setPower(1);

            // if not in correct position, run rotation to that position
            if (Math.abs(leftTarget-leftPos) > POSITION_TOLERANCE)
            {
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // left stick changes target angle (0-MAX_ANGLE)
            if (angle < MAX_ANGLE){
                angle = MAX_ANGLE;
            } else if (gamepad1.right_stick_y > 0.5 || gamepad1.right_stick_y < -0.5)
                angle -= 0.55 * gamepad1.right_stick_y;

       //     if (gamepad1.right_stick_y > 0.5 || gamepad1.right_stick_y < -0.5)
       //         angle += 0.4 * gamepad1.right_stick_y;

           /** if (Math.abs(gamepad1.right_stick_y) > 0.5) {
                double slidePower = 0.25 * gamepad1.right_stick_y; // Adjusted the scaling factor
                leftSlide.setPower(slidePower);
                rightSlide.setPower(slidePower); // Same direction for both slides
            } else {
                leftSlide.setPower(0);
                rightSlide.setPower(0); // Stop motors if input is out of range
            }*/

            // press b to go to position 0.5
            /**lastCycle = thisCycle;
            thisCycle = gamepad1.b;
            if (!lastCycle && thisCycle)
                slideTarget = SLIDE_TARGET_NINETY_DEGREES;
            */
            // calculate target for given angle
            leftSlideTarget = (int) (-1 * slideTarget * SLIDE_MAX);
         //   rightSlideTarget = (int) (slideTarget * SLIDE_MAX);

            // record current positions
            leftSlidePos = (int) (-1 * leftSlide.getCurrentPosition() / SLIDE_MAX);
         //   rightSlidePos = (int) (rightSlide.getCurrentPosition() / SLIDE_MAX);

            // set motor targets and powers
            leftSlide.setTargetPosition(leftSlideTarget);
          //  rightSlide.setTargetPosition(rightSlideTarget);
            leftSlide.setPower(-0.5);
          //  rightSlide.setPower(0.5);

            // if not in correct position, run rotation to that position
            if (Math.abs(leftSlideTarget-leftSlidePos) > POSITION_TOLERANCE)
            {
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
           //     rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }

            // left stick changes target angle (0-MAX_ANGLE)
            if (slideTarget > 1)
                slideTarget = 1;
            else if (slideTarget < 0)
                slideTarget = 0;
            else if (gamepad1.left_stick_y > 0.5 || gamepad1.left_stick_y < -0.5)
                slideTarget -= (0.01 *  gamepad1.left_stick_y);

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

        double[] motorPowers = new double[]{fLeftPower, fRightPower, bLeftPower, bRightPower};
        return motorPowers;

    }
}
