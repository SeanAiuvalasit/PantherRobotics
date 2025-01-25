package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.MecanumDrive;

@Config
@Autonomous(name = "RoadRunnerOpMode", group = "Autonomous")
public class RoadRunnerOpMode extends LinearOpMode {

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

    public class SlidesY {
        private DcMotorEx leftY;
        private DcMotorEx rightY;
        public SlidesY (HardwareMap hardwareMap){
            leftY = hardwareMap.get(DcMotorEx.class, "leftSlide");
            leftY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            leftY.setDirection(DcMotor.Direction.FORWARD);
            leftY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            leftY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            rightY = hardwareMap.get(DcMotorEx.class, "leftSlide");
            rightY.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            rightY.setDirection(DcMotor.Direction.FORWARD);
            rightY.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            rightY.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        public class armUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftY.setTargetPosition(SPECIMEN_HEIGHT);
                rightY.setTargetPosition(-1 * SPECIMEN_HEIGHT);
                leftY.setTargetPositionTolerance(1);
                leftY.setPower(Y_SLOW);
                rightY.setTargetPositionTolerance(1);
                rightY.setPower(Y_SLOW);
                leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                return false;
            }
        }
        public Action armUp() {
            return new armUp();
        }
    }
    public class SlidesX {
        private Servo leftX;
        private Servo rightX;

        public SlidesX(HardwareMap hardwareMap) {
            leftX = hardwareMap.get(Servo.class, "leftX");
            rightX = hardwareMap.get(Servo.class, "rightX");
        }

        public class XOut implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftX.setPosition(0.8);
                rightX.setPosition(0.2);
                return false;
            }
        }
        public Action XOut() {
            return new XOut();
        }
        public class XIn implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftX.setPosition(0);
                rightX.setPosition(0);
                return false;
            }
        }
        public Action XIn() {
            return new XIn();
        }
    }
    public class clawPos {
        private Servo leftClawPos;
        private Servo rightClawPos;
        public clawPos (HardwareMap hardwareMap){
            leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
            rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
        }
        public class clawPosOut implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftClawPos.setPosition(0.75);
                rightClawPos.setPosition(0.25);
                return false;
            }
        }
        public Action clawPosOut() {
            return new clawPosOut();
        }
    }
    public class ClawWrist {
        private Servo clawWrist;
        public ClawWrist (HardwareMap hardwareMap){
            clawWrist = hardwareMap.get(Servo.class, "clawWrist");
        }
        public class straight implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                clawWrist.setPosition(0);
                return false;
            }
        }
        public Action straight() {
            return new straight();
        }
    }
    public class ClawClamp {
        private Servo clawClamp;
        public ClawClamp (HardwareMap hardwareMap){
            Servo clawClamp = hardwareMap.get(Servo.class, "clawClamp");
        }
        public class openClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawClamp.setPosition(0.75);
                return false;
            }
        }
        public Action openClaw() {
            return new openClaw();
        }
        public class closeClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawClamp.setPosition(1);
                return false;
            }
        }
        public Action closeClaw() {
            return new closeClaw();
        }
    }
    public class ClawAngle {
        private Servo clawAngle;
        public ClawAngle (HardwareMap hardwareMap){
            clawAngle = hardwareMap.get(Servo.class, "clawAngle");
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        ClawClamp clawClamp = new ClawClamp(hardwareMap);
        ClawWrist clawWrist = new ClawWrist(hardwareMap);
        ClawAngle clawAngle = new ClawAngle(hardwareMap);
        SlidesX slidesX = new SlidesX(hardwareMap);
        SlidesY slidesY = new SlidesY(hardwareMap);

        TrajectoryActionBuilder tab1 = drive.actionBuilder(initialPose);
        Action trajectoryActionCloseOut = tab1.endTrajectory().fresh()
                .build();

        Actions.runBlocking(clawClamp.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        clawClamp.closeClaw(),
                        clawWrist.straight(),
                        clawPos.clawPosOut();
                        slidesY.armUp();
                        clawClamp

                        tab1.build(),
                        trajectoryActionCloseOut
                )
        );
    }
}





