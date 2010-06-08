package pomodoro.views
import pomodoro._
import org.eclipse.swt.widgets._
import org.eclipse.swt.layout._

object Main {

  def main(args: Array[String]) {

    val display = new Display
    val shell = new Shell(display)
    shell.setText("Pomodoro")
    shell.setLayout(new FillLayout)

    val panel = new PomodoroPanel(shell, Large, new PomodoroTimerService(25, NotRunning))
    //      def endOfPomodoroReached() {
    //        println("End of Pomodoro reached!")
    //      }
    //      def getInitialPomodoroDuration() = 25
    //      def setPomodoroDuration(duration: Int) {
    //    	  println("Setting pomodoro duration = " + duration)
    //      }
    //      def setTitle(s: String) = shell.setText(s)
    shell.pack()
    shell.open()
    while (!shell.isDisposed)
      if (!display.readAndDispatch())
        display.sleep()
    display.dispose()
  }

}