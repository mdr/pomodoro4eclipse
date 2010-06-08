package pomodoro.views

import pomodoro._
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.{ List ⇒ _, _ }
import org.eclipse.swt.events._
import net.miginfocom.layout._
import net.miginfocom.swt.MigLayout

import java.math.BigDecimal

class PomodoroPanel(parent: Composite, digitSize: DigitSize, pomodoroTimerService: PomodoroTimerService) extends Composite(parent, SWT.NONE) {

  private def clockColour: Colour = pomodoroTimerService.pomodoroState match {
    case NotRunning | PomodoroComplete(_) ⇒ Green
    case InPomodoro(_) ⇒ Yellow
  }

  private val (clock, startButton, stopButton, durationSlider: Slider, listener, allWidgets) = {

    setLayout(new MigLayout(new LC()/*.debug(1000)*/.fillX, new AC().index(2).grow(1.0f).index(3).align("right").index(4)))
    val digits = getTimeDigits(pomodoroTimerService.timeRemaining)
    val clock = new DigitalClock(this, digitSize, digits, clockColour)
    val clockWidth = clock.getBounds.width
    val clockHeight = clock.getBounds.height
    clock.setLayoutData(new CC().alignX("center").spanX(5).width(clockWidth + "px").height(clockHeight + "px").wrap)

    val startButton = new Button(this, SWT.PUSH)
    startButton.setText("Start")
    startButton.setLayoutData(new CC().sizeGroupX("button"))

    val stopButton = new Button(this, SWT.PUSH)
    stopButton.setText("Stop")
    stopButton.setEnabled(false)
    stopButton.setLayoutData(new CC().sizeGroupX("button"))

    val separator = new Label(this, SWT.NONE)
    separator.setLayoutData(new CC().growX)

    val sliderLabel = new Label(this, SWT.NONE)
    sliderLabel.setText("Duration:")
    sliderLabel.setLayoutData(new CC().alignX("right"))

    object DurationSlider extends Slider(this, SWT.HORIZONTAL) {

      setMinimum(1)
      setMaximum(60 + getThumb)
      setIncrement(1)
      setPageIncrement(5)
      setSelection(pomodoroTimerService.pomodoroDuration)
      addListener(SWT.Selection, new Listener() {
        def handleEvent(e: Event) { pomodoroTimerService.pomodoroDuration = durationSlider.getSelection }
      })

      override def checkSubclass() {/* skip checks to allow subclassing */}

    }
    DurationSlider.setLayoutData(new CC().alignX("right").growX(0))

    startButton.addSelectionListener(new SelectionAdapter() {
      override def widgetSelected(e: SelectionEvent) = pomodoroTimerService.startPomodoro()
    })

    stopButton.addSelectionListener(new SelectionAdapter() {
      override def widgetSelected(e: SelectionEvent) = pomodoroTimerService.stopPomodoro()
    })

    object Listener extends pomodoroTimerService.Listener {
      override def updated(timeRemaining: Millis, pomodoroState: PomodoroState) = updateWidgets(timeRemaining, pomodoroState)
    }

    pomodoroTimerService.addListener(Listener)
    val allWidgets = List(clock, startButton, stopButton, separator, sliderLabel, DurationSlider)
    (clock, startButton, stopButton, DurationSlider, Listener, allWidgets)
  }
  updateWidgets(pomodoroTimerService.timeRemaining, pomodoroTimerService.pomodoroState)

  def setDigitSize(digitSize: DigitSize) {
    //    clock.setDigitSize(digitSize)
    //    layout(true)
    ()
  }

  private def updateWidgets(timeRemaining: Millis, pomodoroState: PomodoroState) {
    pomodoroState match {
      case InPomodoro(_) | PomodoroComplete(_) ⇒
        startButton.setEnabled(false)
        stopButton.setEnabled(true)
        durationSlider.setEnabled(false)
      case NotRunning ⇒
        stopButton.setEnabled(false)
        startButton.setEnabled(true)
        durationSlider.setEnabled(true)
    }
    clock.setDigits(getTimeDigits(timeRemaining))
    clock.setColour(clockColour)
  }

  private def getTimeDigits(t: Millis): Digits = {
    val positiveTime: Millis = math.abs(t)
    val allSeconds: Seconds = positiveTime / 1000
    val minutes = allSeconds / 60 % 60 /* wrap at 60 to avoid problems when overflow at 100 */
    val seconds = allSeconds % 60
    val minutesPrefix = if (minutes < 10) "0" else ""
    val secondsPrefix = if (seconds < 10) "0" else ""
    val s = minutesPrefix + minutes + secondsPrefix + seconds
    Digits(s(0) - '0', s(1) - '0', s(2) - '0', s(3) - '0')
  }

  override def dispose() = {
    allWidgets foreach { _.dispose() }
    pomodoroTimerService.removeListener(listener)
    super.dispose()
  }

}