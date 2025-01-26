package com.example.meepmeeptesting;

import static java.lang.Math.*;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, toRadians(180), toRadians(180), 15)
                .build();

        Pose2d initialPose = new Pose2d(0,63.25, toRadians(270));

        myBot.runAction(myBot.getDrive().actionBuilder(initialPose)
                .splineTo(new Vector2d(0, 34.90354), toRadians(90))// path 1
                .strafeTo(new Vector2d(-34,32)) // to block 1

                /*
                .splineTo(new Vector2d(-43,12), toRadians(90)) //behind block 1
                .strafeTo(new Vector2d(-43,67)) // push to obzone
                .strafeTo(new Vector2d(-57,12)) // behind block 2
                .strafeTo(new Vector2d(-57,67)) // push to obzone
                .strafeTo(new Vector2d(-62,12)) // behind block 3
                .strafeTo(new Vector2d(-62,67)) // push to obzone
                .strafeTo(new Vector2d(0,65)) // go to spot to grab specimen

                .splineTo(new Vector2d(0, 34.90354), toRadians(270)) // go to hook
                .splineTo(new Vector2d(-42, 65), toRadians(90)) // pick up new specimen
                .splineTo(new Vector2d(0, 34.90354), toRadians(270)) // go to hook
                .splineTo(new Vector2d(-42, 65), toRadians(90)) // etc.
                .splineTo(new Vector2d(0, 34.90354), toRadians(270))
                .splineTo(new Vector2d(-42, 65), toRadians(90))
                 */
                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}