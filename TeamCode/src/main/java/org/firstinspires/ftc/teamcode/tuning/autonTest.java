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
        final int SPECIMEN_HEIGHT = 1300;
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



        waitForStart();

            double preciseMovement = 1.0;
            double power = 0.4;

            telemetry.addData("leftY pos: ", leftY.getCurrentPosition());
            telemetry.addData("rightY pos: ", rightY.getCurrentPosition());
            telemetry.addData("leftx: ", leftX.getPosition());
            telemetry.addData("rightx: ", rightX.getPosition());
            telemetry.addData("leftclawpos: ", leftClawPos.getPosition());
            telemetry.addData("rightclawpos: ", rightClawPos.getPosition());
            telemetry.addData("clawclamp: ", clawClamp.getPosition());
            telemetry.addData("clawAngle: ", clawAngle.getPosition());
            telemetry.addData("right back: ", rightBack.getCurrentPosition());
            telemetry.update();

            /**
             * auton stuff below this
             */
            // this for copy paste
            /*
            leftBack.setTargetPosition(-1 * run());
            leftFront.setTargetPosition(-1 * run());
            rightBack.setTargetPosition(run());
            rightFront.setTargetPosition(run());

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);

            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            leftBack.setTargetPosition(rotate());
            leftFront.setTargetPosition(rotate());
            rightBack.setTargetPosition(rotate());
            rightFront.setTargetPosition(rotate());
            */

            // arm up
            clawClamp.setPosition(1);
            clawWrist.setPosition(0);
            leftX.setPosition(0.8);
            rightX.setPosition(0.2);
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
            sleep(1300);

            //going forward
            leftBack.setTargetPosition(run(60));
            leftFront.setTargetPosition(run(60));
            rightBack.setTargetPosition(run(60));
            rightFront.setTargetPosition(run(60));
            leftBack.setTargetPositionTolerance(1);
            leftFront.setTargetPositionTolerance(1);
            rightBack.setTargetPositionTolerance(1);
            rightFront.setTargetPositionTolerance(1);

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            sleep(2000);

        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            // hook
            leftBack.setTargetPosition(-1 * run(5));
            leftFront.setTargetPosition(-1 * run(5));
            rightBack.setTargetPosition(run(5));
            rightFront.setTargetPosition(run(5));

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


            //claw release
        clawAngle.setPosition(0.5);
            clawClamp.setPosition(0.75);

            // back up
            leftBack.setTargetPosition(-1 * run(-10));
            leftFront.setTargetPosition(-1 * run(-10));
            rightBack.setTargetPosition(run(-10));
            rightFront.setTargetPosition(run(-10));

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            /*
            //wait?

            // arm down
            clawClamp.setPosition(1);
            rightClawPos.setPosition(1);
            leftClawPos.setPosition(0);
            clawAngle.setPosition(0.4);
            leftX.setPosition(0);
            rightX.setPosition(1);
            clawWrist.setPosition(1);
            leftY.setTargetPosition(Y_HOME);
            rightY.setTargetPosition(Y_HOME);
            leftY.setTargetPositionTolerance(1);
            leftY.setPower(Y_SLOW);
            rightY.setTargetPositionTolerance(1);
            rightY.setPower(Y_SLOW);
            leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // turn
            leftBack.setTargetPosition(rotate(90));
            leftFront.setTargetPosition(rotate(90));
            rightBack.setTargetPosition(rotate(90));
            rightFront.setTargetPosition(rotate(90));

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            //go to closest sample
            leftBack.setTargetPosition(-1 * run(72));
            leftFront.setTargetPosition(-1 * run(72));
            rightBack.setTargetPosition(run(72));
            rightFront.setTargetPosition(run(72));

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // turn to face sample
            leftBack.setTargetPosition(rotate(-90));
            leftFront.setTargetPosition(rotate(-90));
            rightBack.setTargetPosition(rotate(-90));
            rightFront.setTargetPosition(rotate(-90));

            leftBack.setPower(power);
            leftFront.setPower(power);
            rightBack.setPower(power);
            rightFront.setPower(power);
            leftBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightBack.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightFront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            // hover sample
            leftX.setPosition(1);
            rightX.setPosition(0);
            rightClawPos.setPosition(0.33);   // rotation of claw (higher value means higher claw)
            leftClawPos.setPosition(0.67);
            clawClamp.setPosition(0.75);     // picking up blocks
            clawAngle.setPosition(0.5);
            clawWrist.setPosition(1);
            leftY.setTargetPosition(Y_HOME);
            rightY.setTargetPosition(Y_HOME);
            leftY.setTargetPositionTolerance(1);
            leftY.setPower(Y_SLOW);
            rightY.setTargetPositionTolerance(1);
            rightY.setPower(Y_SLOW);
            leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // grab sample
            leftClawPos.setPosition(1);
            rightClawPos.setPosition(0);
            clawWrist.setPosition(1);

            // pick up sample
            clawClamp.setPosition(1);
            rightClawPos.setPosition(1);
            leftClawPos.setPosition(0);
            clawAngle.setPosition(0.4);
            leftX.setPosition(0);
            rightX.setPosition(1);
            clawWrist.setPosition(1);
            leftY.setTargetPosition(Y_HOME);
            rightY.setTargetPosition(Y_HOME);
            leftY.setTargetPositionTolerance(1);
            leftY.setPower(Y_SLOW);
            rightY.setTargetPositionTolerance(1);
            rightY.setPower(Y_SLOW);
            leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            // turn to face bucket
            // go to bucket
            // score sample
            // park?



          */
        while(!isStopRequested() && opModeIsActive());
    }
    public int run(double distance) {
        return (int) (-1 * distance / (Math.PI * diameter)*CPR);
    }

    public int rotate(double degrees) {
        return (int) (degrees * CPR / 360);
    }
}