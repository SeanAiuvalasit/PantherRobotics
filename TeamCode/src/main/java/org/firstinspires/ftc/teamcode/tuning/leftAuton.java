package org.firstinspires.ftc.teamcode.tuning;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
@Autonomous(name = "leftAuto")
public class leftAuton extends LinearOpMode {
    DcMotor leftFront = null;
    DcMotor rightFront = null;
    DcMotor leftBack = null;
    DcMotor rightBack = null;


    @Override
    public void runOpMode() throws InterruptedException{
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
        if(opModeIsActive()){
            rightBack.setPower(-0.5);
            rightFront.setPower(0.5);
            leftBack.setPower(0.5);
            leftFront.setPower(-0.5);
            sleep(5000);
            rightBack.setPower(0);
            rightFront.setPower(0);
            leftBack.setPower(0);
            leftFront.setPower(0);
            sleep(10000000);
        }
    }
}
