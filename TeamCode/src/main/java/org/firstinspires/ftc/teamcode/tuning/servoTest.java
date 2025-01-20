package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "servoTest")
public class servoTest extends LinearOpMode {

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
        //Servo clawRotation = hardwareMap.get(Servo.class, "clawWrist");
        Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");
        Servo clawAngle = hardwareMap.get(Servo.class, "clawAngle");

        // Y: vertical positions
        final int SPECIMEN_HEIGHT = 4000;
        final int BUCKET_HEIGHT = 0;
        final int Y_HOME = 0;
        double YSpeed = 0.7;
        boolean atSpecimenPos = false;
        boolean lastCycle = false;    // used with below to read button presses properly
        boolean thisCycle = false;
        boolean lastCycle2 = false;    // used with below to read button presses properly
        boolean thisCycle2 = false;
        boolean up = false;

        // X: Servo stuff
        double XTarget = 0.0;
        boolean in = true;

        // A: Button
        boolean readyForVertical = false;
        boolean verticallyUp = false;

        //Y: Button
        double clawClampTarget = 0.0;
        double clawTarget = 0.0;
        boolean clawOpen = true;

        //
        boolean clawDown = false;

        waitForStart();
        while (opModeIsActive()) {
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
             * Extend Horizontal Slides about 3/4th of the way
             * Turn arm into extended position - right above the blocks
             *
             * If Button A pressed again should go back to home position.
             * Keeps repeating this pattern.
             */
            if (gamepad2.a) {
                leftX.setPosition(1);
                rightX.setPosition(0);
                if (leftX.getPosition() == 1) {
                    rightClawPos.setPosition(0.3);   // rotation of claw
                    leftClawPos.setPosition(0.7);
                    //clawClamp.wait(1000);
                    clawClamp.setPosition(0.75);     // picking up blocks
                    clawAngle.setPosition(0.2);      //
                }
            }

            if (gamepad2.b) {
                rightClawPos.setPosition(1);
                leftClawPos.setPosition(0);
                clawClamp.setPosition(1);
                if (rightClawPos.getPosition() == 1) {
                    clawAngle.setPosition(0.4);
                    leftX.setPosition(0);
                    rightX.setPosition(1);
                }
            }

            if (gamepad2.y) {
                if(!clawDown){
                    leftClawPos.setPosition(1);
                    rightClawPos.setPosition(0);
                    clawDown = true;
                }else{
                    clawDown = false;
                    leftClawPos.setPosition(0.6);
                    rightClawPos.setPosition(0.4);
                }
            }

            if(gamepad2.x){
                clawClamp.setPosition(1);
                leftX.setPosition(0.35);
                rightX.setPosition(0.65);
                leftClawPos.setPosition(0.75);
                rightClawPos.setPosition(0.25);

                if(true){
                    leftY.setTargetPosition(1300);
                    rightY.setTargetPosition(-1300);

                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.2);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(0.2);

                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }
                clawAngle.setPosition(0.75);
            }

            /**
             * Opening and closing of claw      //MAYBE KEEP ON A
             * If pressed once should close claw
             * I
             * f pressed again should open claw
             * *keeps repeating this pattern

            if (gamepad2.y) {
                if (clawOpen) {
                    clawClampTarget = 0.9;
                    clawClamp.setPosition(clawClampTarget);
                    clawOpen = false;
                } else {
                    clawClamp.setPosition(0);
                    clawOpen = true;
                }
            }
*/
            /**
             * Allow small movement of horizontal slide
             * If moved up should slowly extend horizontal slide
             * If moved down should slowly retract horizontal slide

             if (gamepad1.right_stick_y > 0.5 || gamepad1.right_stick_y < -0.5){
             clawClampTarget -= (0.01 *  gamepad1.right_stick_y);
             clawClamp.setPosition(clawClampTarget);
             }
             */
            /**
             * to twist the claw  //MAYBE MOVE TO A
             * If moved left should turn the claw angle position more left
             * If moved right should turn the claw angle position more right

             if(gamepad1.left_stick_x > 0.5 || gamepad1.left_stick_x < -0.5) {
             clawTarget -= (0.01 *  gamepad1.left_stick_x);
             rightClawPos.setPosition(clawTarget);
             leftClawPos.setPosition(clawTarget);
             }
             */
            lastCycle = thisCycle;
            thisCycle = gamepad2.dpad_up;
            if (lastCycle && thisCycle) {
                if (up) {
                    leftY.setTargetPosition(0);
                    rightY.setTargetPosition(0);

                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.2);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(0.2);

                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    up = false;
                } else {
                    leftY.setTargetPosition(SPECIMEN_HEIGHT);
                    rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);

                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.2);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(0.2);

                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    up = true;
                }
            }

            if (gamepad1.x) {
                leftX.setPosition(0);
                rightX.setPosition(1);
            }
            if (gamepad1.y) {
                leftX.setPosition(1);
                rightX.setPosition(0);
            }
            if (gamepad1.b) {
                clawAngle.setPosition(0.7);
            }


            if (gamepad1.dpad_up) {
                rightClawPos.setPosition(0);
                leftClawPos.setPosition(1);
            }
            if (gamepad1.dpad_down) {
                rightClawPos.setPosition(1);
                leftClawPos.setPosition(0);
            }
            if (gamepad1.dpad_right) {
                clawClamp.setPosition(1);
            }
            if (gamepad1.dpad_left) {
                clawClamp.setPosition(0.75);
            }

            if (gamepad1.right_bumper) {
                clawAngle.setPosition(1);
            }
            if (gamepad1.left_bumper) {
                clawAngle.setPosition(0.1);
            }
            /**
             if(gamepad1.a){
             rightClawPos.setPosition(0);
             leftClawPos.setPosition(1);

             leftY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
             rightY.setTargetPosition(SPECIMEN_HEIGHT);

             leftY.setTargetPosition(0);
             rightY.setTargetPosition(0);

             leftY.setTargetPositionTolerance(1);
             leftY.setPower(0.3);
             rightY.setTargetPositionTolerance(1);
             rightY.setPower(0.3);

             readyForVertical = true;
             if(readyForVertical){

             leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

             readyForVertical = false;
             verticallyUp = true;
             if(verticallyUp){
             rightClawPos.setPosition(1);
             leftClawPos.setPosition(0);

             clawRotation.setPosition(1);
             wait(500);
             clawRotation.setPosition(0);

             rightClawPos.setPosition(0);
             leftClawPos.setPosition(1);

             leftY.setTargetPosition(SPECIMEN_HEIGHT);
             rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);

             leftY.setTargetPositionTolerance(1);
             leftY.setPower(0.2);
             rightY.setTargetPositionTolerance(1);
             rightY.setPower(0.2);

             leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
             }
             }
             }*/
        }
    }
}

