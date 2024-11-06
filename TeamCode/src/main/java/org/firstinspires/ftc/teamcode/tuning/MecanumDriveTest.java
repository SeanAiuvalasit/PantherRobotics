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
        double drive, turn, strafe;
        double fLeftPower, fRightPower,bLeftPower, bRightPower;
        //Hware hware = new Hware(hardwareMap);
        //leftFront = hardwareMap.get(DcMotor.class, "LF");
        //rightFront = hardwareMap.get(DcMotor.class, "RF");
        //leftBack = hardwareMap.get(DcMotor.class, "LB");
        //rightBack = hardwareMap.get(DcMotor.class, "RB");


        waitForStart();
        while(opModeIsActive()){
            drive = gamepad1.left_stick_y * -1;
            turn = gamepad1.right_stick_x;
            strafe = gamepad1.left_stick_x;


            //strafe
            fLeftPower = drive + turn + strafe;
            fRightPower = drive - turn - strafe;
            bLeftPower = drive + turn - strafe;
            bRightPower = drive - turn + strafe;

            double[] appliedPowers = scalePowers(fLeftPower, fRightPower, bLeftPower, bRightPower);

            //leftFront.setPower(appliedPowers[0]);
            //leftBack.setPower(appliedPowers[2]);
            //rightFront.setPower(appliedPowers[1]);
            //rightBack.setPower(appliedPowers[3]);
        }
    }
    public double[] scalePowers(double fLeftPower, double fRightPower, double bLeftPower, double bRightPower){
        double max = Math.max(Math.abs(fLeftPower), Math.max(Math.abs(fRightPower), Math.max(Math.abs(bLeftPower), Math.max(Math.abs(bRightPower))));
        if(max > 1){
            fLeftPower /= max;
            fRightPower /= max;
            bLeftPower /= max;
            bRightPower /= max;
        }

        double [] motorPowers = new double[]{fLeftPower, fRightPower, bLeftPower, bRightPower};
        return motorPowers;
    }
}