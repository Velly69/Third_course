// Run() is called from Scheduling.main() and is where
// the scheduling algorithm written by the user resides.
// User modification should occur within the Run() function.


import java.util.Vector;
import java.io.*;

public class SchedulingAlgorithm {

  public static Results Run(int runtime, Vector<Process> processVector, Results result) {
    int comptime = 0;
    int currentProcess = 0;
    int previousProcess = 0;
    int size = processVector.size();
    int completed = 0;
    boolean repeat = false;

    String resultsFile = "Summary-Processes";

    result.schedulingType = "Preemptive";
    result.schedulingName = "Shortest remaining time first";
    try {
      PrintStream out = new PrintStream(new FileOutputStream(resultsFile));

      currentProcess = getNextProcess(processVector);
      Process process = processVector.elementAt(currentProcess);

      out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
      while (comptime < runtime) {
        if (process.cpudone == process.cputime) {
          completed++;
          out.println("Process: " + currentProcess + " completed... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          if (completed == size) {
            result.compuTime = comptime;
            out.close();
            return result;
          }
          if(completed == size - 1) {
            for(int i = 0; i < processVector.size(); i++){
              if(processVector.elementAt(i).cpudone < processVector.elementAt(i).cputime){
                currentProcess = i;
                if(processVector.elementAt(i).isBlocked){
                  processVector.elementAt(i).isBlocked = false;
                }
              }
            }
            process = processVector.elementAt(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");

          } else {
            currentProcess = getNextProcess(processVector);
            process = processVector.elementAt(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          }
        }

        if (process.ioblocking <= process.ionext) {
          if(!allProcessInaccessible(processVector, currentProcess)) {
            out.println("Process: " + currentProcess + " I/O blocked... ("
                    + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
            process.ionext = 0;
            process.numblocked++;
            process.isBlocked = true;
            process.absoluteUnblockingTime = comptime + process.blockingTime;
            currentProcess = getNextProcess(processVector);
            process = processVector.elementAt(currentProcess);
            out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          }
        }

        for(int i = 0; i < processVector.size(); i++){
          if(processVector.elementAt(i).absoluteUnblockingTime == comptime){
            processVector.elementAt(i).isBlocked = false;
            processVector.elementAt(i).ionext = 0;
            processVector.elementAt(i).absoluteUnblockingTime = -1;
            repeat = true;
          }
        }

        if(repeat && completed!= size){
          previousProcess = currentProcess;
          currentProcess = getNextProcess(processVector);
          if(previousProcess!=currentProcess){
            out.println("Process: " + previousProcess + " interrupted by scheduler... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
          }
          process = processVector.elementAt(currentProcess);
          out.println("Process: " + currentProcess + " registered... (" + process.cputime + " " + process.ioblocking + " " + process.cpudone + ")");
        }

        process.cpudone++;       
        if (process.ioblocking > 0) {
          process.ionext++;
        }
        comptime++;
        repeat = false;
      }
      out.close();
    } catch (IOException e) { /* Handle exceptions */ }
    result.compuTime = comptime;
    return result;
  }

  private static int getNextProcess(Vector<Process> processVector){
      int minProcessIndex = 0;
      Process process = processVector.elementAt(minProcessIndex);
      while (process.isBlocked || process.cpudone >= process.cputime){
        minProcessIndex++;
        process = processVector.elementAt(minProcessIndex);
      }

      int remainingTime = process.cputime - process.cpudone;

      for (int i = 0; i < processVector.size(); i++){
        if(i == minProcessIndex){
          continue;
        }
        process = processVector.elementAt(i);
        if(process.cpudone < process.cputime){
          if(process.cputime - process.cpudone < remainingTime && !process.isBlocked){
            minProcessIndex = i;
            remainingTime = process.cputime - process.cpudone;
          }
        }
      }
      return minProcessIndex;
  }

  private static boolean allProcessInaccessible(Vector<Process> processVector, int currentProcess){
    boolean allBlocked = true;
    for (int i = 0; i < processVector.size(); i++) {
      if(!processVector.elementAt(i).isBlocked && processVector.elementAt(i).cpudone < processVector.elementAt(i).cputime){
        if(i == currentProcess){
          continue;
        }
        allBlocked = false;
      }
    }
    return allBlocked;
  }
}
