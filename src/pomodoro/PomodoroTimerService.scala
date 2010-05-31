package pomodoro

import org.eclipse.swt.widgets.Display

sealed trait PomodoroState {
  val targetTimeOption: Option[Long]
}

case class InPomodoro(targetTime: Long) extends PomodoroState {
  val targetTimeOption = Some(targetTime)
}
case class PomodoroComplete(targetTime: Long) extends PomodoroState {
  val targetTimeOption = Some(targetTime)
}
case object NotRunning extends PomodoroState {
  val targetTimeOption = None
}

class PomodoroTimerService(initialDuration: Int/* minutes */, initialPomodoroState: PomodoroState) extends BasicListenable {
  require(initialDuration > 0 && initialDuration <= 60)

  private var pomodoroState_ : PomodoroState = initialPomodoroState

  private var pomodoroDuration_ : Minutes = initialDuration

  private var currentTimerRunnable: Option[Runnable] = None

  pomodoroState.targetTimeOption foreach startTimerRunnable

  def timeRemaining: Millis = pomodoroState.targetTimeOption match {
    case Some(targetTime) => targetTime - System.currentTimeMillis
    case None => pomodoroDuration * 60 * 1000
  }

  def pomodoroDuration = pomodoroDuration_

  def pomodoroDuration_=(minutes: Minutes) {
    require(pomodoroState == NotRunning)
    pomodoroDuration_ = minutes
    signalPomodoroDurationChanged(pomodoroDuration)
    signalUpdateEvent(timeRemaining, pomodoroState)
  }

  def pomodoroState = pomodoroState_

  def startPomodoro() {
    val targetTime = System.currentTimeMillis + pomodoroDuration * 60 * 1000
    require(pomodoroState_ == NotRunning)
    pomodoroState_ = InPomodoro(targetTime)
    startTimerRunnable(targetTime)
  }

  def startTimerRunnable(targetTime: Millis) {
    require(currentTimerRunnable.isEmpty)
    val timerRunnable = makeTimerRunnable()
    currentTimerRunnable = Some(timerRunnable)
    signalPomodoroStarted(targetTime)
    signalUpdateEvent(timeRemaining, pomodoroState)
    Display.getDefault.timerExec(100, timerRunnable)
  }

  def makeTimerRunnable(): Runnable = new Runnable() {
    def run() = {
      currentTimerRunnable = None
      val targetTime = pomodoroState.targetTimeOption.get // Check
      val timeRemaining = pomodoroState.targetTimeOption.get - System.currentTimeMillis
      if (timeRemaining < 0 && pomodoroState.isInstanceOf[InPomodoro]) {
        pomodoroState_ = PomodoroComplete(targetTime)
        signalPomodoroComplete()
      }
      val timerRunnable = makeTimerRunnable()
      currentTimerRunnable = Some(timerRunnable)
      signalUpdateEvent(timeRemaining, pomodoroState)
      Display.getDefault.timerExec(1000, timerRunnable)
    }
  }

  def stopPomodoro() {
    internalStop()
    signalPomodoroStopped()
    signalUpdateEvent(timeRemaining, pomodoroState)
  }

  def dispose() = internalStop()

  private def internalStop() {
    for (timerRunnable <- currentTimerRunnable)
      Display.getDefault.timerExec(-1, timerRunnable)
    currentTimerRunnable = None
    pomodoroState_ = NotRunning
  }

}

trait BasicListenable {

  trait Listener {

    def updated(timeRemaining: Millis, pomodoroState: PomodoroState) {}

    def pomodoroStarted(targetTime: Millis) {}

    def pomodoroStopped() {}

    def pomodoroComplete() {}

    def pomodoroDurationChanged(duration: Minutes) {}

  }

  private var listeners = Set[Listener]()

  def addListener(listener: Listener) { listeners = listeners + listener }

  def removeListener(listener: Listener) { listeners = listeners - listener }

  protected def signalUpdateEvent(timeRemaining: Millis, pomodoroState: PomodoroState) = listeners foreach { _.updated(timeRemaining, pomodoroState) }

  protected def signalPomodoroStarted(targetTime: Millis) = listeners foreach { _.pomodoroStarted(targetTime) }

  protected def signalPomodoroStopped() = listeners foreach { _.pomodoroStopped }

  protected def signalPomodoroComplete() = listeners foreach { _.pomodoroComplete }

  protected def signalPomodoroDurationChanged(duration: Minutes) = listeners foreach { _.pomodoroDurationChanged(duration) }
}
