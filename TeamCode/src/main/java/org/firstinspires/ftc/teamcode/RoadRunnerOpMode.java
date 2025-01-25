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
    final int SPECIMEN_HEIGHT = 1300;
    final double Y_SLOW = 0.6;

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
        public class armDown implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                leftY.setTargetPosition(0);
                rightY.setTargetPosition(0);
                leftY.setTargetPositionTolerance(1);
                leftY.setPower(Y_SLOW);
                rightY.setTargetPositionTolerance(1);
                rightY.setPower(Y_SLOW);
                leftY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightY.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                return false;
            }
        }
        public Action armDown() {
            return new armDown();
        }

    }
    public class Claw {
        private Servo leftX;
        private Servo rightX;
        private Servo leftClawPos;
        private Servo rightClawPos;
        private Servo clawWrist;
        private Servo clawClamp;
        private Servo clawAngle;
        public Claw(HardwareMap hardwareMap) {
            leftX = hardwareMap.get(Servo.class, "leftX");
            rightX = hardwareMap.get(Servo.class, "rightX");
            leftClawPos = hardwareMap.get(Servo.class, "leftClawPos");
            rightClawPos = hardwareMap.get(Servo.class, "rightClawPos");
            clawWrist = hardwareMap.get(Servo.class, "clawWrist");
            clawClamp = hardwareMap.get(Servo.class, "clawClamp");
            clawAngle = hardwareMap.get(Servo.class, "clawAngle");
        }
        public class home implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawClamp.setPosition(1);
                rightClawPos.setPosition(1);
                leftClawPos.setPosition(0);

                clawAngle.setPosition(0.35);
                leftX.setPosition(0);
                rightX.setPosition(1);
                clawWrist.setPosition(0.65);
                return false;
            }
        }
        public Action home() {
            return new home();
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
        public class hook implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawAngle.setPosition(0.5);
                clawClamp.setPosition(0.75);
                return false;
            }
        }
        public Action hook() {
            return new hook();
        }
        public class goToHook implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket telemetryPacket) {
                clawClamp.setPosition(1);
                clawWrist.setPosition(0);
                leftX.setPosition(0.5);
                rightX.setPosition(0.5);
                leftClawPos.setPosition(0.75);
                rightClawPos.setPosition(0.25);
                clawAngle.setPosition(0.15);
                return false;
            }
        }
        public Action goToHook() {
            return new goToHook();
        }
    }
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(-59.5, 3, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        SlidesY slidesY = new SlidesY(hardwareMap);
        Claw claw = new Claw(hardwareMap);

        TrajectoryActionBuilder path1 = drive.actionBuilder(initialPose)
                .splineTo(new Vector2d(), Math.toRadians(90));
        TrajectoryActionBuilder push = drive.actionBuilder(new Pose2d(-31.15354, 3, Math.toRadians(90)))
                .splineTo(new Vector2d(-24,-36), Math.toRadians(180)) // intermediate
                .splineTo(new Vector2d(-18,-48), Math.toRadians(180)) //  back of block 1
                .splineTo(new Vector2d(-66,-48), Math.toRadians(180)) // push block 1
                .splineTo(new Vector2d(), Math.toRadians(180)) // go to back of block 2
                .splineTo(new Vector2d(-66, -48), Math.toRadians(180)) // push block 2
                .splineTo(new Vector2d(), Math.toRadians(180)) // go to back of block 3
                .splineTo(new Vector2d(-66,-48), Math.toRadians(180)) // push block 3
                .splineTo(new Vector2d(), Math.toRadians(180)); // go to pickup point
        TrajectoryActionBuilder pickup = drive.actionBuilder(new Pose2d(-31.15354, 3, Math.toRadians(90)))
                .splineTo(new Vector2d(), Math.toRadians(90)); // go to pickup point
        TrajectoryActionBuilder dropoff =  drive.actionBuilder()
                .splineTo(new Vector2d(-31.15354,3), Math.toRadians(90)); // go to dropoff point

        Actions.runBlocking(claw.closeClaw());

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        new ParallelAction(
                                claw.closeClaw(),
                                path1.build(),
                                slidesY.armUp(),
                                claw.goToHook(),
                                path1.endTrajectory().fresh().build()
                        ),
                        claw.hook(),
                        claw.openClaw(),
                        new ParallelAction(
                                push.build(),
                                slidesY.armDown(),
                                claw.home(),
                                push.endTrajectory().fresh().build()
                        ),
                        claw.closeClaw(),
                        new ParallelAction(
                                dropoff.build(),
                                claw.goToHook(),
                                slidesY.armUp(),
                                dropoff.endTrajectory().fresh().build()
                        ),
                        claw.hook(),
                        claw.openClaw(),
                        new ParallelAction(
                                pickup.build(),
                                claw.home(),
                                slidesY.armDown(),
                                pickup.endTrajectory().fresh().build()
                        ),
                        claw.closeClaw(),
                        new ParallelAction(
                                dropoff.build(),
                                claw.goToHook(),
                                slidesY.armUp(),
                                dropoff.endTrajectory().fresh().build()
                        )
                )
        );
    }
}





