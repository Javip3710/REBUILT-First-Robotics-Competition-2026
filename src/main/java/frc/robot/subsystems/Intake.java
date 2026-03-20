// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {


  ShuffleboardTab tab = Shuffleboard.getTab("Intake");
  GenericEntry shooterPower = tab.add("Power: ", 0.25).getEntry();
  /** Creates a new Shooter. */
  Double speed = shooterPower.getDouble(0.21);
  TalonFX intake, positionleft, positionright;
  
  /** Creates a new Intake. */
  public Intake() {
    positionleft = new TalonFX(0);
    positionright = new TalonFX(0);
    intake = new TalonFX(32);
    intake.setNeutralMode(NeutralModeValue.Coast);

    var TalonFXConfigs = new TalonFXConfiguration();
    var configs = TalonFXConfigs;

    var configs2 = TalonFXConfigs;
    configs.Slot0.kS = 0.25;
    configs.Slot0.kV = 0.12;
    configs.Slot0.kA = 0.01;
    configs.Slot0.kP = 4.8;
    configs.Slot0.kI = 0;
    configs.Slot0.kD = 0.1;

    configs2.Slot1.kV = 0.0075;
    configs2.Slot1.kP = 0.01;



    // set Motion Magic Velocity settings
  var motionMagicConfigs = TalonFXConfigs.MotionMagic;
  motionMagicConfigs.MotionMagicAcceleration = 12; // Target acceleration of 400 rps/s (0.25 seconds to max)
  motionMagicConfigs.MotionMagicJerk = 500; // Target jerk of 4000 rps/s/s (0.1 seconds)

    positionleft.getConfigurator().apply(configs);
    positionright.getConfigurator().apply(configs);

  }


  public void setPower(double power){
    intake.set(power * speed);
  }

  @Override
  public void periodic() {
  speed = shooterPower.getDouble(0.21);
    // This method will be called once per scheduler run
  }
}
