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
@Autonomous(name = "left")

//104mm diameter    5281.1 ticks per revolutiona

public class leftParkAuton extends LinearOpMode {

    //104mm diameter
    // 5281.1 CPR
    //robot full length ~45 cm, between wheels on side ~35 cm, between wheels on front ~34 cm
    final double CPR = 384.5;
    final double diameter = 10.4;

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
        DcMotorEx leftFront = hardwareMap.get(DcMotorEx.class, "LF");
        DcMotorEx rightFront = hardwareMap.get(DcMotorEx.class, "RF");
        DcMotorEx leftBack = hardwareMap.get(DcMotorEx.class, "LB");
        DcMotorEx rightBack = hardwareMap.get(DcMotorEx.class, "RB");
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
        final int SPECIMEN_HEIGHT = 1370;
        final int BUCKET_HEIGHT = 4300;
        final int Y_HOME = 0;
        final double Y_SLOW = 0.6;

        // A: Button
        boolean readyForVertical = false;
        boolean verticallyUp = false;

        //Y: Button
        double clawClampTarget = 0.0;
        double clawTarget = 0.0;
        boolean clawOpen = true;

        //
        boolean clawDown = false;
        final double fastSpeed = 0.6;
        final double slowSpeed = 0.5;



        waitForStart();

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * The arm goes up into the air, leftY and rightY go up at the Y_Slow speed
         */
        telemetry.addData("HighBar1      ",rightBack.getCurrentPosition());
        telemetry.update();
        sleep(5000);
        clawClamp.setPosition(1);
        clawWrist.setPosition(0);
        leftX.setPosition(0.5);
        rightX.setPosition(0.5);
        leftClawPos.setPosition(0.75);
        rightClawPos.setPosition(0.25);
        leftY.setTargetPosition(SPECIMEN_HEIGHT);
        rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
        leftY.setTargetPositionTolerance(1);
        leftY.setPower(Y_SLOW);
        rightY.setTargetPositionTolerance(1);
        rightY.setPower(Y_SLOW);
        leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        clawAngle.setPosition(0.15);
        sleep(700);

        /**
         * The robot moves forward a specified amount of centimeters
         */
        leftBack.setTargetPosition(run(68));
        leftFront.setTargetPosition(run(68));
        rightBack.setTargetPosition(run(68));
        rightFront.setTargetPosition(run(68));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed/2);
        leftFront.setPower(fastSpeed/2);
        rightBack.setPower(fastSpeed/2);
        rightFront.setPower(fastSpeed/2);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1500);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /**
         * claw goes back
         */
        telemetry.addData("should come back      ",rightBack.getCurrentPosition());
        clawAngle.setPosition(0.5);
        clawClamp.setPosition(0.75);

        sleep(200);

        /**
         * back to home position (b button code)
         */
        clawClamp.setPosition(1);

        clawAngle.setPosition(0.35);
        leftX.setPosition(0);
        rightX.setPosition(1);
        clawWrist.setPosition(0.65);

        sleep(1000);

        /**
         * y vertical back home & left and right claw pos back to home (button b code)
         */
        rightClawPos.setPosition(0.5);
        leftClawPos.setPosition(0.5);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightY.setPower(0);
        leftY.setPower(0);

        sleep(200);

        /**
         * back up to park
         */
        leftBack.setTargetPosition(run(-68));
        leftFront.setTargetPosition(run(-68));
        rightBack.setTargetPosition(run(-68));
        rightFront.setTargetPosition(run(-68));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed/2);
        leftFront.setPower(fastSpeed/2);
        rightBack.setPower(fastSpeed/2);
        rightFront.setPower(fastSpeed/2);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1500);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * go left to park
         */
        leftBack.setTargetPosition(run(68));
        leftFront.setTargetPosition(run(-68));
        rightBack.setTargetPosition(run(-68));
        rightFront.setTargetPosition(run(68));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed/2);
        leftFront.setPower(fastSpeed/2);
        rightBack.setPower(fastSpeed/2);
        rightFront.setPower(fastSpeed/2);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(4000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        while(!isStopRequested() && opModeIsActive());
    }
    public int run(double distance) {
        return (int) (-1 * distance / (Math.PI * diameter)*CPR);
    }

    public int rotate(double degrees) {
        return (int) (10 * degrees * CPR / 360);
    }
}