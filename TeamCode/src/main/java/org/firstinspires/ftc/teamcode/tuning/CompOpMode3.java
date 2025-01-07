package org.firstinspires.ftc.teamcode.tuning;
//package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;


/*
TODO: TEST DIRECTIONS FOR MOTORS AND SERVOS
rightY - positive encoder + positive power = slide goes down
leftY
leftX
rightX
leftClawPos
rightClawPos
clawRotation
clawClamp
*/

@TeleOp(name = "v2comp2")
public class CompOpMode3 extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor leftY = hardwareMap.get(DcMotorEx.class, "leftSlide");
        DcMotor rightY = hardwareMap.get(DcMotorEx.class, "rightSlide");
        leftY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftY.setDirection(DcMotor.Direction.FORWARD);
        leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightY.setDirection(DcMotor.Direction.FORWARD);
        rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

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

        //Servos
        Servo leftX = hardwareMap.get(Servo.class, "leftX");
        Servo rightX = hardwareMap.get(Servo.class, "rightX");
        Servo leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
        Servo rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
        Servo clawRotation = hardwareMap.get(Servo.class, "clawRotation");
        Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");

        double XTarget = 0.0;                   // target position (0-1)
        double clawTarget = 0.0;
        double rotationTarget = 0.0;
        boolean clawOut = false;
        boolean clawOpen = false;
        boolean atSpecimenPos = false;
        boolean atBucketPos = false;
        //final double CPR = 5281.1;                  // ticks per revolution

        // TODO: find servo positions
        final int SPECIMEN_HEIGHT = 0;
        final int BUCKET_HEIGHT = 0;
        final int Y_HOME = 0;

        final double SPECIMEN_OUT = 0.0;
        final double INTAKE_OUT = 0.0;
        final double X_HOME = 0.0;

        final double BUCKET_ROTATION = 0.0;
        final double INTAKE_ROTATION = 0.0;
        final double ROTATION_HOME = 0.0;

        final double CLAW_OPEN = 0.0;
        final double CLAW_CLOSED = 0.0;

        waitForStart();

        while (opModeIsActive()) {
            final int SLIDE_MAX = 3089;                 // empirical position of fully extended arm
            double preciseMovement = 1.0;

            //TODO: add or subtract joystick value?
            if (gamepad1.right_stick_y > 0.5 || gamepad1.right_stick_y < -0.5)
                XTarget -= (0.01 *  gamepad1.right_stick_y);
            if (gamepad1.left_stick_x > 0.5 || gamepad1.left_stick_x < -0.5)
                clawTarget -= (0.01 *  gamepad1.left_stick_x);
            if (gamepad1.left_bumper)
                rotationTarget -= 0.01;
            else if (gamepad1.right_bumper)
                rotationTarget += 0.01;

            leftX.setPosition(XTarget);
            rightX.setPosition(1 - XTarget);
            leftClawPos.setPosition(rotationTarget);
            rightClawPos.setPosition(1 - rotationTarget);
            clawRotation.setPosition(clawTarget);

            if(gamepad1.a)
            {
                clawOut = !clawOut;
                if(clawOut)
                {
                    XTarget = INTAKE_OUT;
                    leftX.setPosition(INTAKE_OUT);
                    rightX.setPosition(1 - INTAKE_OUT);
                    leftClawPos.setPosition(INTAKE_ROTATION);
                    rightClawPos.setPosition(1 - INTAKE_ROTATION);
                }
                else
                {
                    XTarget = X_HOME;
                    //TODO: does the slide target code run within this block
                    leftX.setPosition(X_HOME);
                    rightX.setPosition(1 - X_HOME);
                    leftClawPos.setPosition(ROTATION_HOME);
                    rightClawPos.setPosition(1 - ROTATION_HOME);
                }
            }

            if(gamepad1.y)
            {
                clawOpen = !clawOpen;
                if (clawOpen)
                    clawClamp.setPosition(CLAW_OPEN);
                else
                    clawClamp.setPosition(CLAW_CLOSED);
            }

            if(gamepad1.x)
            {
                atSpecimenPos = !atSpecimenPos;
                if(!atSpecimenPos)
                {
                    leftY.setTargetPosition(SPECIMEN_HEIGHT);
                    rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftX.setPosition(SPECIMEN_OUT);
                    rightX.setPosition(1 - SPECIMEN_OUT);
                }
                else
                {
                    leftY.setTargetPosition(Y_HOME);
                    rightY.setTargetPosition(-1 * Y_HOME);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftX.setPosition(X_HOME);
                    rightX.setPosition(1 - X_HOME);
                }
            }

            if(gamepad1.b) {
                atBucketPos = !atBucketPos;
                if (!atBucketPos) {
                    leftY.setTargetPosition(BUCKET_HEIGHT);
                    rightY.setTargetPosition(-1 * BUCKET_HEIGHT);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftClawPos.setPosition(BUCKET_ROTATION);
                    rightClawPos.setPosition(1 - BUCKET_ROTATION);
                } else {
                    leftY.setTargetPosition(Y_HOME);
                    rightY.setTargetPosition(-1 * Y_HOME);
                    leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    leftClawPos.setPosition(ROTATION_HOME);
                    rightClawPos.setPosition(1 - ROTATION_HOME);
                }
            }

            //MecanumDrive Code
            drive = gamepad2.left_stick_y * 0.5 * preciseMovement;
            turn = gamepad2.right_stick_x *-0.5 * preciseMovement;
            strafe = gamepad2.left_stick_x *-0.5 * preciseMovement;

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
