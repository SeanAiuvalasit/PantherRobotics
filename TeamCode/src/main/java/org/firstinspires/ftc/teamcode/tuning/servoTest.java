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
        Servo clawRotation = hardwareMap.get(Servo.class, "clawRotation");
     //   Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");

        // Y: vertical positions
        final int SPECIMEN_HEIGHT = 1000;
        final int BUCKET_HEIGHT = 0;
        final int Y_HOME = 0;
        boolean atSpecimenPos = false;
        boolean lastCycle = false;    // used with below to read button presses properly
        boolean thisCycle = false;
        boolean lastCycle2 = false;    // used with below to read button presses properly
        boolean thisCycle2 = false;

        // X: Servo stuff
        double XTarget = 0.0;

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("leftY pos: ", leftY.getCurrentPosition());
            telemetry.addData("rightY pos: ", rightY.getCurrentPosition());
            telemetry.update();

            /**lastCycle = thisCycle;
            thisCycle = gamepad1.x;
            if (lastCycle && thisCycle) {
                //    leftY.setTargetPosition(SPECIMEN_HEIGHT);
                    rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);

                //    leftY.setTargetPositionTolerance(1);
               //     leftY.setPower(0.3);
                    rightY.setTargetPositionTolerance(1);
                    rightY.setPower(0.3);

              //      leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                }

            lastCycle2 = thisCycle2;
            thisCycle2 = gamepad1.y;
            if (lastCycle2 && thisCycle2) {
                leftY.setTargetPosition(SPECIMEN_HEIGHT);
               // rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);

                leftY.setTargetPositionTolerance(1);
                leftY.setPower(0.3);
              //  rightY.setTargetPositionTolerance(1);
              //  rightY.setPower(0.3);

                leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
              //  rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            /**  leftX.setPosition(SPECIMEN_OUT);
             rightX.setPosition(1 - SPECIMEN_OUT);*/

             /**   else
                {
                    leftY.setTargetPosition(Y_HOME);
                    leftY.setTargetPositionTolerance(1);
                    leftY.setPower(0.3);
                //    rightY.setTargetPosition(-1 * Y_HOME);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 //   rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 //   leftX.setPosition(X_HOME);
                 //   rightX.setPosition(1 - X_HOME);
                }*/

             if(gamepad1.x){
                 leftX.setPosition(0);
                 rightX.setPosition(1);
             }
             if(gamepad1.y){
                 leftX.setPosition(1);
                 rightX.setPosition(0);
             }

            if(gamepad1.dpad_up){
                rightClawPos.setPosition(0);
                leftClawPos.setPosition(1);
            }
            if(gamepad1.dpad_down){
                rightClawPos.setPosition(1);
                leftClawPos.setPosition(0);
            }

            if(gamepad1.right_bumper){
                clawRotation.setPosition(1);
            }
            if(gamepad1.left_bumper){
                clawRotation.setPosition(0);
            }
            }
        }
    }

