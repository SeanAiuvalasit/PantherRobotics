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
@Autonomous(name = "autonTest")

//104mm diameter    5281.1 ticks per revolutiona

public class autonTest extends LinearOpMode {

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
        rightClawPos.setPosition(1);
        leftClawPos.setPosition(0);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightY.setPower(0);
        leftY.setPower(0);

        sleep(200);

        /**
         * Strafes to the right
         */
        leftBack.setTargetPosition(run(-74));
        leftFront.setTargetPosition(run(74));
        rightBack.setTargetPosition(run(74));
        rightFront.setTargetPosition(run(-74));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed);
        leftFront.setPower(fastSpeed);
        rightBack.setPower(fastSpeed);
        rightFront.setPower(fastSpeed);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1800);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * The robot moves forward a specified amount of centimeters FOR FIRST BLOCK
         */
        leftBack.setTargetPosition(run(55));
        leftFront.setTargetPosition(run(55));
        rightBack.setTargetPosition(run(55));
        rightFront.setTargetPosition(run(55));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed);
        leftFront.setPower(fastSpeed);
        rightBack.setPower(fastSpeed);
        rightFront.setPower(fastSpeed);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1700);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * Strafes to the right FOR FIRST BLOCK
         */
        leftBack.setTargetPosition(run(-18));
        leftFront.setTargetPosition(run(18));
        rightBack.setTargetPosition(run(18));
        rightFront.setTargetPosition(run(-18));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(slowSpeed/2);
        leftFront.setPower(slowSpeed/2);
        rightBack.setPower(slowSpeed/2);
        rightFront.setPower(slowSpeed/2);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * The robot moves BACKWARDS specified amount of centimeters FOR THE FIRST BLOCK
         */
        leftBack.setTargetPosition(run(-95));
        leftFront.setTargetPosition(run(-95));
        rightBack.setTargetPosition(run(-95));
        rightFront.setTargetPosition(run(-95));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed - 0.1);
        leftFront.setPower(fastSpeed - 0.1);
        rightBack.setPower(fastSpeed - 0.1);
        rightFront.setPower(fastSpeed - 0.1);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(2000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        /**
         * The robot moves forward a specified amount of centimeters SECOND BLOCK
         */
        leftBack.setTargetPosition(run(85));
        leftFront.setTargetPosition(run(85));
        rightBack.setTargetPosition(run(85));
        rightFront.setTargetPosition(run(85));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed);
        leftFront.setPower(fastSpeed);
        rightBack.setPower(fastSpeed);
        rightFront.setPower(fastSpeed);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(1700);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * Strafes to the right FOR SECOND BLOCK
         */
        leftBack.setTargetPosition(run(-18));
        leftFront.setTargetPosition(run(18));
        rightBack.setTargetPosition(run(18));
        rightFront.setTargetPosition(run(-18));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(slowSpeed);
        leftFront.setPower(slowSpeed);
        rightBack.setPower(slowSpeed);
        rightFront.setPower(slowSpeed);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(800);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * The robot moves BACKWARDS specified amount of centimeters FOR THE SECOND BLOCK
         */
        leftBack.setTargetPosition(run(-80));
        leftFront.setTargetPosition(run(-80));
        rightBack.setTargetPosition(run(-80));
        rightFront.setTargetPosition(run(-80));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(fastSpeed);
        leftFront.setPower(fastSpeed);
        rightBack.setPower(fastSpeed);
        rightFront.setPower(fastSpeed);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(3000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * Sets claw to home values, goes back slowly
         */
        clawClamp.setPosition(0.75);
        rightClawPos.setPosition(1);
        leftClawPos.setPosition(0);

        clawAngle.setPosition(0.35);
        leftX.setPosition(0);
        rightX.setPosition(1);
        clawWrist.setPosition(0.65);

        /**
         * The robot moves BACKWARDS slowly to pick up block off wall
         */
        leftBack.setTargetPosition(run(-40));
        leftFront.setTargetPosition(run(-40));
        rightBack.setTargetPosition(run(-40));
        rightFront.setTargetPosition(run(-40));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(0.1);
        leftFront.setPower(0.1);
        rightBack.setPower(0.1);
        rightFront.setPower(0.1);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(2000);

        //claw clamp
        clawClamp.setPosition(1.0);

        /**
         * goes upo to specimen height
         */
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftY.setTargetPosition(SPECIMEN_HEIGHT);
        rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
        leftY.setTargetPositionTolerance(1);
        leftY.setPower(0.6);
        rightY.setTargetPositionTolerance(1);
        rightY.setPower(0.6);
        leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(2000);

        /**
         * The robot moves FOWRARD fastly to get away from the wall
         */
        leftBack.setTargetPosition(run(10));
        leftFront.setTargetPosition(run(10));
        rightBack.setTargetPosition(run(10));
        rightFront.setTargetPosition(run(10));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(0.6);
        leftFront.setPower(0.6);
        rightBack.setPower(0.6);
        rightFront.setPower(0.6);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(400);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        /**
         * The robot goes left to line up with bar
         */
        leftBack.setTargetPosition(run(90));
        leftFront.setTargetPosition(run(-90));
        rightBack.setTargetPosition(run(-90));
        rightFront.setTargetPosition(run(90));
        leftBack.setTargetPositionTolerance(1);
        leftFront.setTargetPositionTolerance(1);
        rightBack.setTargetPositionTolerance(1);
        rightFront.setTargetPositionTolerance(1);

        leftBack.setPower(0.6);
        leftFront.setPower(0.6);
        rightBack.setPower(0.6);
        rightFront.setPower(0.6);
        leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sleep(2000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        clawClamp.setPosition(1);
        clawWrist.setPosition(0);
        leftX.setPosition(0.8);
        rightX.setPosition(0.2);
        leftClawPos.setPosition(0.75);
        rightClawPos.setPosition(0.25);

        clawAngle.setPosition(0.15);

        sleep(1000);

        /**
         * The robot moves forward a specified amount of centimeters
         */
        leftBack.setTargetPosition(run(60));
        leftFront.setTargetPosition(run(60));
        rightBack.setTargetPosition(run(60));
        rightFront.setTargetPosition(run(60));
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
        rightClawPos.setPosition(1);
        leftClawPos.setPosition(0);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightY.setPower(0);
        leftY.setPower(0);

        sleep(200);




        while(!isStopRequested() && opModeIsActive());
    }
    public int run(double distance) {
        return (int) (-1 * distance / (Math.PI * diameter)*CPR);
    }

    public int rotate(double degrees) {
        return (int) (10 * degrees * CPR / 360);
    }
}