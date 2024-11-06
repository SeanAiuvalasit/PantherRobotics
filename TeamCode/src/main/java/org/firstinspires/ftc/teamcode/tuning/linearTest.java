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
    int tick = 5281;
       int encoderValue = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftSlide = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotor rightSlide = hardwareMap.get(DcMotorEx.class, "rightSlide");
        DcMotor left = hardwareMap.get(DcMotorEx.class, "left");
        DcMotor right = hardwareMap.get(DcMotorEx.class, "right");

        telemetry.addData("hardware: ", "Initialize");
        leftSlide.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
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

        while(opModeIsActive()){   //up
            /**
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

            double slide;
            telemetry.addData("hardware: ", rightSlide.getCurrentPosition());
            telemetry.update();
            /**
             * Rotates the left and right motor by the amount given by right_stick_y
             * multiplied by 0.5
             * commet
             */
            left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            slide = gamepad1.right_stick_y * 0.5;
               /**        this is for teh slide to hold its position
            if(slide==0){
                left.setPower(0.0000000000000000000000001);
                right.setPower(0.0000000000000000000000001);
                left.setTargetPosition(left.getCurrentPosition());
                right.setTargetPosition(right.getCurrentPosition());
                //left.setTargetPosition();
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            */
                //left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

                left.setPower(slide);
                left.setTargetPosition(left.getCurrentPosition());

                //right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                right.setPower(slide * -1);
                left.setTargetPosition(right.getCurrentPosition());

                /**
                right.setTargetPosition(tick/15);
                left.setTargetPosition(tick/-15);
                //telemetry.addData("hardware: ", "right");
                //telemetry.update();
                //left.setTargetPositionTolerance()
                */

                if(slide==0) {
                    left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                }
                if(gamepad1.y){
                    left.setPower(0);
                    right.setPower(0);
                    telemetry.addData("hardware: ", "slide");
                    telemetry.update();
                    rightSlide.setTargetPosition(500);
                    ((DcMotorEx) rightSlide).setTargetPositionTolerance(1);
                    rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightSlide.setPower(0.5);
                    rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    //right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                    telemetry.addData("hardware: ", rightSlide.getCurrentPosition());
                    telemetry.update();
                }
              if(gamepad1.a){//up
                telemetry.addData("hardware: ", "right");
                telemetry.update();
                 leftSlide.setPower(0);
                 rightSlide.setPower(0);
                 left.setTargetPosition((int)(tick/-12));
                 ((DcMotorEx) left).setTargetPositionTolerance(1);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 left.setPower(0.5);
                right.setTargetPosition((int)(tick/12));
                  ((DcMotorEx) right).setTargetPositionTolerance(1);
                 right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                 right.setPower(0.5);
                 left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                 right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            if(gamepad1.b){   //down
                //telemetry.addData("hardware: ", "left");
                //telemetry.update();
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                left.setTargetPosition(0);
                ((DcMotorEx) left).setTargetPositionTolerance(1);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.2);
                right.setTargetPosition(0);
                ((DcMotorEx) right).setTargetPositionTolerance(1);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.2);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            if(gamepad1.x) {
                //telemetry.addData("hardware: ", "up");
                //telemetry.update();
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                left.setTargetPosition((int)(tick/-9));
                ((DcMotorEx) left).setTargetPositionTolerance(1);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.2);
                right.setTargetPosition((int)(tick/9));
                ((DcMotorEx) right).setTargetPositionTolerance(1);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.2);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
              /**
            if(gamepad1.dpad_up){
                telemetry.addData("hardware: ", "vertical slide up");
                telemetry.update();
                left.setPower(0.2);
                right.setPower(0.2);
                left.setTargetPosition((int)(tick/-10.75));
                //left.setTargetPositionTolerance()
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.2);
                right.setTargetPosition((int)(tick/10.75));
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.2);
                leftSlide.setTargetPosition(-2640);
                //left.setTargetPositionTolerance()
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSlide.setPower(0.01);
                rightSlide.setTargetPosition(2640);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setPower(0.01);
                leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
              
            if(gamepad1.dpad_down){
                telemetry.addData("hardware: ", "vertical slide down");
                telemetry.update();
                left.setPower(0);
                right.setPower(0);

                leftSlide.setTargetPosition(0);
                //left.setTargetPositionTolerance()
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSlide.setPower(0.3);
                rightSlide.setTargetPosition(0);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setPower(0.3);
                leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
               */
              
              if(gamepad1.dpad_up){
               while(gamepad1.dpad_up){
              encoderValue+=50;
               }
              left.setTargetPosition(encoderValue);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.2);
                right.setTargetPosition(-1*encoderValue);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.2);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
              }
              if(gamepad1.dpad_down){
               while(gamepad1.dpad_down){
              encoderValue-=50;
               }
              left.setTargetPosition(encoderValue);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.2);
                right.setTargetPosition(-1*encoderValue);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.2);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
              }
        }
    }
}
