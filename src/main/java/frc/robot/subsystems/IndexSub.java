// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IndexSub extends SubsystemBase {
  TalonFX Index, Feeder;
  /** Creates a new IndexSub. */
  public IndexSub() {
    Index = new TalonFX(24, "CANivore");
    Feeder = new TalonFX(1, "CANivore");
    Index.setNeutralMode(NeutralModeValue.Coast);
    Feeder.setNeutralMode(NeutralModeValue.Coast);
  }

  public void setPower(double power){
    Index.set(power);
    Feeder.set(-power);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
