package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

//package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.JavaUtil;

@TeleOp(name = "linearTest")
public class linearTest extends LinearOpMode {
    //   HardwareMap hware = new HardwareMap();
    //DcMotor motor;
    //tick value - 537.7

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftSlide = hardwareMap.get(DcMotor.class, "leftSlide");
        DcMotor rightSlide = hardwareMap.get(DcMotor.class, "rightSlide");
        DcMotor left = hardwareMap.get(DcMotor.class, "left");
        DcMotor right = hardwareMap.get(DcMotor.class, "right");

        telemetry.addData("hardware: ", "Initialize");
        leftSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftSlide.setDirection(DcMotor.Direction.FORWARD);
        leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightSlide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightSlide.setDirection(DcMotor.Direction.FORWARD);
        rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        left.setDirection(DcMotor.Direction.FORWARD);
        left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        right.setDirection(DcMotor.Direction.FORWARD);
        right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        waitForStart();

        while(opModeIsActive()){   //up
            if(gamepad1.a){
                telemetry.addData("hardware: ", "right");
                telemetry.update();
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                left.setTargetPosition(-268);
                //left.setTargetPositionTolerance()
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.19);
                right.setTargetPosition(268);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.19);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
            if(gamepad1.b){   //down
                telemetry.addData("hardware: ", "left");
                telemetry.update();
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                left.setTargetPosition(0);
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.075);
                right.setTargetPosition(0);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.075);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            if(gamepad1.x) {
                telemetry.addData("hardware: ", "up");
                telemetry.update();
                leftSlide.setPower(0);
                rightSlide.setPower(0);
                left.setTargetPosition(-536);
                //left.setTargetPositionTolerance()
                left.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                left.setPower(0.19);
                right.setTargetPosition(536);
                right.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                right.setPower(0.19);
                left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }

            if(gamepad1.dpad_up){
                telemetry.addData("hardware: ", "vertical slide up");
                telemetry.update();
                left.setPower(0);
                right.setPower(0);

                leftSlide.setTargetPosition(-360);
                //left.setTargetPositionTolerance()
                leftSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                leftSlide.setPower(0.1);
                rightSlide.setTargetPosition(360);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setPower(0.1);
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
                leftSlide.setPower(0.1);
                rightSlide.setTargetPosition(0);
                rightSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightSlide.setPower(0.1);
                leftSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                rightSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            }
        }
    }
}
