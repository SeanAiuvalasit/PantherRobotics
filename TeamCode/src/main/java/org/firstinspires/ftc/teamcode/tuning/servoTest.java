package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
public class servoTest extends LinearOpMode {          //104mm diameter    5281.1 ticks per revolutiona

    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx leftY = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotorEx rightY = hardwareMap.get(DcMotorEx.class, "rightSlide");
        leftY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftY.setDirection(DcMotor.Direction.FORWARD);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightY.setDirection(DcMotor.Direction.FORWARD);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //Servos
        Servo leftX = hardwareMap.get(Servo.class, "leftX");
        Servo rightX = hardwareMap.get(Servo.class, "rightX");
        Servo leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
        Servo rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
        Servo clawWrist = hardwareMap.get(Servo.class, "clawWrist");
        Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");
        Servo clawAngle = hardwareMap.get(Servo.class, "clawAngle");

        //Mecanum Drive Code
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

        // Y: vertical positions
        final int SPECIMEN_HEIGHT = 1360;
        final int BUCKET_HEIGHT = 4300;
        final int Y_HOME = 0;
        final double Y_SLOW = 0.25;

        double wristTarget = 0.0;


        waitForStart();
        while (opModeIsActive()) {
            double preciseMovement = 1.0;

            telemetry.addData("leftY pos: ", leftY.getCurrentPosition());
            telemetry.addData("rightY pos: ", rightY.getCurrentPosition());
            telemetry.addData("leftx: ", leftX.getPosition());
            telemetry.addData("rightx: ", rightX.getPosition());
            telemetry.addData("leftclawpos: ", leftClawPos.getPosition());
            telemetry.addData("rightclawpos: ", rightClawPos.getPosition());
            telemetry.addData("clawclamp: ", clawClamp.getPosition());
            telemetry.addData("clawAngle: ", clawAngle.getPosition());
            telemetry.update();

            /**
             * wrist
             */
            if(gamepad2.left_bumper){
                    clawWrist.setPosition(0.45);
            }

            /**
             * wrist
             */
            if(gamepad2.right_bumper){
                    clawWrist.setPosition(1);
            }


            /**
             * changes precise movement based on left_trigger
             */
            if  (gamepad1.left_trigger > 0.5)
                preciseMovement = 0.5;
            else
                preciseMovement = 1.0;

            /**
             * Extend Horizontal Slides about the whole way
             * Turn arm into extended position - right above the blocks
             *
             * If Button A pressed again should go back to home position.
             * Keeps repeating this pattern.
             *
             */
            if (gamepad2.a) {

                leftX.setPosition(1);
                rightX.setPosition(0);

                    rightClawPos.setPosition(0.4);   // rotation of claw (higher value means higher claw)
                    leftClawPos.setPosition(0.6);
                  //  clawClamp.setPosition(0.75);     // picking up blocks
                    clawAngle.setPosition(0.5);
                    clawWrist.setPosition(0.65);
            }

            /**
             * Closes the claw, rotates it to home position, and returns horizontal slides to home
             * Vertical slides go to 0
             */
            if (gamepad2.b) {
                clawClamp.setPosition(1);
                rightClawPos.setPosition(0.95);
                leftClawPos.setPosition(0.05);

                clawAngle.setPosition(0.35);
                leftX.setPosition(0);
                rightX.setPosition(1);
                clawWrist.setPosition(0.65);
            }

            /**
             * Closes the claw, rotates it to home position, and moves thw horizontal slides halfway
             * Vertical slides go to 0
             */
            if (gamepad2.dpad_right) {
                leftX.setPosition(0.6);
                rightX.setPosition(0.4);

                rightClawPos.setPosition(0.33);   // rotation of claw (higher value means higher claw)
                leftClawPos.setPosition(0.67);
                //  clawClamp.setPosition(0.75);     // picking up blocks
                clawAngle.setPosition(0.5);
                clawWrist.setPosition(0.65);
            }

            /**
             * Makes the claw go down more to pick up blocks, and if pressed again would go back up
             */
            if (gamepad2.y) {
              //  if(!clawDown){
                    leftClawPos.setPosition(1);
                    rightClawPos.setPosition(0);
                    //clawWrist.setPosition(1);
                    clawClamp.setPosition(0.75);
              //      clawDown = true;
              //  }
                /*
                else{
                    clawDown = false;
                    leftClawPos.setPosition(0.6);
                    rightClawPos.setPosition(0.4);
                    clawWrist.setPosition(1);
                }

                 */
            }

            /**
             * Brings horizontal slides out, rotates claw into position
             */
            if(gamepad2.x){
                if(true){
                    leftY.setTargetPosition(SPECIMEN_HEIGHT);
                    rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);

                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.5);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(0.5);

                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                clawClamp.setPosition(1);
                clawWrist.setPosition(0);
                leftX.setPosition(0.8);
                rightX.setPosition(0.2);
                leftClawPos.setPosition(0.75);
                rightClawPos.setPosition(0.25);

                clawAngle.setPosition(0.15);
            }

            /**
             * Vertical slides go down to 0
             */
            if(gamepad2.dpad_down){
                rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
                rightY.setPower(0);
                leftY.setPower(0);
            }


            if(gamepad2.dpad_left) {
                leftY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                rightY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            /**
             * Vertical slides go up to vertical height
             */
            if (gamepad2.dpad_up) {
                leftY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

               // if(leftY.getCurrentPosition()<50 && rightY.getCurrentPosition()>-50) {
                    leftY.setTargetPosition(BUCKET_HEIGHT);
                    rightY.setTargetPosition(-1 * BUCKET_HEIGHT);

                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(1.0);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(1.0);

                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                    clawClamp.setPosition(1);
                    rightClawPos.setPosition(1);
                    leftClawPos.setPosition(0);

                    clawAngle.setPosition(0.4);
                    clawWrist.setPosition(0.65);
               // }
            }

            if (gamepad1.left_bumper) {
                clawClamp.setPosition(0.75);
            }
            if (gamepad1.right_bumper) {
                clawClamp.setPosition(1.0);
            }/**
            if (gamepad1.right_bumper) {
                clawAngle.setPosition(0.2);
            }
            if (gamepad1.left_bumper) {
                clawAngle.setPosition(0.25);
            }

            if (gamepad1.x) {
                leftX.setPosition(0);
                rightX.setPosition(1);
            }
            if (gamepad1.y) {
                leftX.setPosition(1);
                rightX.setPosition(0);
            }
            if (gamepad1.b) clawAngle.setPosition(0);

            if (gamepad1.dpad_up) {
                rightClawPos.setPosition(0.2);
                leftClawPos.setPosition(0.8);
            }
            if (gamepad1.dpad_down) {
                rightClawPos.setPosition(1);
                leftClawPos.setPosition(0);
            }*/

            //MecanumDrive Code
            drive = gamepad1.left_stick_y * 0.5 * preciseMovement;
            turn = gamepad1.right_stick_x *-0.5 * preciseMovement;
            strafe = gamepad1.left_stick_x *-0.5 * preciseMovement;

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

