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

@TeleOp(name = "mecanumDriveTest")
public class MecanumDriveTest extends LinearOpMode{
    @Override
    public void runOpMode() throws InterruptedException {
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
        while(opModeIsActive()){
            //MecanumDrive Code
            drive = gamepad1.left_stick_y * 0.1;
            turn = gamepad1.right_stick_x * -0.1;
            strafe = gamepad1.left_stick_x * -0.1;

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