package org.firstinspires.ftc.teamcode.tuning;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class Hware {
    //Declare motors
    public DcMotor rightFront = null;
    public DcMotor leftFront = null;
    public DcMotor rightBack = null;
    public DcMotor leftBack = null;

    public DcMotor leftRotation;
    public DcMotor rightRotation;
    public DcMotor rightEle;
    public DcMotor leftEle;
    //Declare servos
    public Servo rightElbow = null;
    public Servo leftElbow = null;
    public Servo intake = null;


    //Declare Additional variables
    public double ticks = 751.8;
    public double liftNewTarget;
    public double armNewTarget;

    HardwareMap hardwareMap = null;
    public ElapsedTime runtime = new ElapsedTime();

    public Hware(HardwareMap hwMap) {
        initialize(hwMap);
    }
    private void initialize(HardwareMap hwMap) {
        hardwareMap = hwMap;

        //initialize Chassis
        leftFront = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        leftBack = hardwareMap.get(DcMotor.class, "LB");
        rightBack = hardwareMap.get(DcMotor.class, "RB");
        leftRotation = hardwareMap.get(DcMotor.class, "VR");
        rightRotation =hardwareMap.get(DcMotor.class, "VL");
        rightEle = hardwareMap.get(DcMotor.class, "H");
        leftEle = hardwareMap.get(DcMotor.class, "L");

        //Set motor directions
        rightBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.FORWARD);
        leftFront.setDirection(DcMotor.Direction.FORWARD);

        //initialize slide motors

        leftRotation.setDirection(DcMotor.Direction.REVERSE);
        rightRotation.setDirection(DcMotor.Direction.REVERSE);
        rightEle.setDirection(DcMotor.Direction.FORWARD);
        leftEle.setDirection(DcMotor.Direction.FORWARD);

        //Set motor modes
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        leftRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightRotation.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftRotation.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightRotation.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //Initialize Servos
        rightElbow = hardwareMap.get(Servo.class, "rightElbow");
        leftElbow = hardwareMap.get(Servo.class, "leftElbow");
        intake = hardwareMap.get(Servo.class, "intake");





        //Set Zero Power Behavior
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightRotation.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
}