package frc.robot.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;

/**
 * The Base Runnymede Command implements command helpers to aid with logging
 */
public class RunnymedeCommandBase extends CommandBase {

  protected long  initializeTime = 0;
  private String  finishReason   = null;

  List<Subsystem> subsystemList  = new ArrayList<>();

  public void logCommandStart() {

    logCommandStart(new Subsystem[0]);
  }

  public void logCommandStart(String msg) {

    logCommandStart(msg, new Subsystem[0]);
  }

  public void logCommandStart(Subsystem... subsystemList) {

    logCommandStart(null, subsystemList);
  }

  public void logCommandStart(String commandParms, Subsystem... subsystemList) {

    this.subsystemList.clear();
    finishReason   = null;
    initializeTime = 0;

    // Capture the subsystem list associated with this command
    if (subsystemList != null && subsystemList.length > 0) {
      this.subsystemList.addAll(Arrays.asList(subsystemList));
    }
    else {
      this.subsystemList.addAll(getRequirements());
    }

    logCommandState("STARTING", commandParms, true);

    // Set the initialize time after logging of the start message.
    initializeTime = System.currentTimeMillis();
  }

  public void logCommandEnd(boolean interrupted) {

    logCommandEnd(interrupted, null);
  }

  public void logCommandEnd(boolean interrupted, String endMsg) {

    String state = "ENDED";

    if (interrupted) {
      state = "INTERUPTED";
    }

    logCommandState(state, endMsg, true);
  }

  public void logStateTransition(String transition) {
    logStateTransition(transition, null);
  }

  public void logStateTransition(String transition, boolean logSubsystems) {
    logStateTransition(transition, null, logSubsystems);
  }

  public void logStateTransition(String transition, String msg) {
    logStateTransition(transition, msg, false);
  }

  public void logStateTransition(String transition, String msg, boolean logSubsystems) {
    logCommandState(transition, msg, logSubsystems);
  }

  public void log(String msg) {
    logCommandState(null, msg, false);
  }

  private void logCommandState(String state, String msg, boolean logSubsystems) {

    StringBuilder sb = new StringBuilder();

    sb.append(this.getClass().getSimpleName());

    if (state != null) {
      sb.append(" : ").append(state);
    }

    sb.append(" at ").append(System.currentTimeMillis() - initializeTime).append("ms");

    if (finishReason != null) {
      sb.append(" : ").append(finishReason);
    }

    if (msg != null) {
      sb.append(" : ").append(msg);
    }

    if (logSubsystems) {
      // Print the subsystems as passed in on the command start
      for (Subsystem subsystem : subsystemList) {
        sb.append("\n   ").append(subsystem.toString());
      }
    }

    System.out.println(sb.toString());
  }

  public void setFinishReason(String finishReason) {
    this.finishReason = finishReason;
  }


}
