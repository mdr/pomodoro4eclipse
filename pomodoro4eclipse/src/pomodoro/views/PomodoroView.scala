package pomodoro.views

import org.eclipse.core.runtime._
import org.eclipse.ui.part._
import org.eclipse.ui._
import org.eclipse.jface.viewers._
import org.eclipse.jface.action._
import org.eclipse.jface.action.Action
import org.eclipse.swt.widgets._
import org.eclipse.swt.SWT
import org.eclipse.core.runtime.preferences._

import net.miginfocom.layout._
import net.miginfocom.swt.MigLayout

import pomodoro._
import pomodoro.PomodoroPlugin.preferencesNode

class PomodoroView extends ViewPart {
  import PomodoroView._
  import PreferenceConstants._

  private var pomodoroPanelOption: Option[PomodoroPanel] = None
  private var holderOption: Option[Canvas] = None
  private val pomodoroTimerService = PomodoroPlugin.pomodoroTimerServiceOption getOrElse (throw new AssertionError("Could not find Pomodoro Timer Service"))

  private def setPartNameFromPomodoroState(pomodoroState: PomodoroState) {
    val titleSuffix = pomodoroState match {
      case InPomodoro(_) ⇒ " [In Pomodoro]"
      case PomodoroComplete(_) ⇒ " [Complete]"
      case NotRunning ⇒ ""
    }
    setPartName("Pomodoro" + titleSuffix)
  }

  private object Listener extends pomodoroTimerService.Listener() {
    override def updated(timeRemaining: Millis, pomodoroState: PomodoroState) = setPartNameFromPomodoroState(pomodoroState)

    override def pomodoroStarted(targetTime: Millis) {
      preferencesNode.putLong(TARGET_TIME, targetTime)
      preferencesNode.flush()
    }

    override def pomodoroStopped() {
      preferencesNode.putLong(TARGET_TIME, DEFAULT_TARGET_TIME)
      preferencesNode.flush()
    }

    override def pomodoroComplete() {
      val workbenchPage = getSite.getPage
      workbenchPage.showView(VIEW_ID)
      val viewPartReference = workbenchPage.getReference(PomodoroView.this)
      if (preferencesNode.getBoolean(ZOOM_ON_POMODORO_COMPLETE, DEFAULT_ZOOM_ON_POMODORO_COMPLETE))
        if (workbenchPage.getPartState(viewPartReference) != IWorkbenchPage.STATE_MAXIMIZED)
          workbenchPage.toggleZoom(viewPartReference)
    }

    private var flushPreferencesRunnableOption: Option[Runnable] = None

    override def pomodoroDurationChanged(duration: Minutes) {
      preferencesNode.putInt(POMODORO_DURATION, duration)

      // Flushing preferences is a slightly slow operation, so we push it to a background thread to maintain responsiveness.
      val flushPreferencesRunnable = new Runnable() {
        def run() {
          preferencesNode.flush()
          flushPreferencesRunnableOption = None
        }
      }
      for (previousFlushPreferencesRunnable ← flushPreferencesRunnableOption)
        Display.getDefault.timerExec(-1, previousFlushPreferencesRunnable)
      flushPreferencesRunnableOption = Some(flushPreferencesRunnable)
      Display.getDefault.timerExec(1000, flushPreferencesRunnable)
    }
  }

  private def setDigitSize(digitSize: DigitSize) {
    preferencesNode.put(DIGIT_SIZE, digitSize.preferenceKey)
    preferencesNode.flush()
    pomodoroPanelOption foreach { panel ⇒
      panel.setDigitSize(digitSize)
      panel.getParent.layout(true, true)
    }
  }

  def createPartControl(parent: Composite) {

    val maximiseOnPomodoroEndAction = new Action("Maximise on Pomodoro end", IAction.AS_CHECK_BOX) {
      override def run() {
        preferencesNode.putBoolean(ZOOM_ON_POMODORO_COMPLETE, isChecked)
        preferencesNode.flush()
      }
    }
    maximiseOnPomodoroEndAction.setChecked(preferencesNode.getBoolean(ZOOM_ON_POMODORO_COMPLETE, DEFAULT_ZOOM_ON_POMODORO_COMPLETE))

    val useLargeDigitsAction = new Action("Large Digits", IAction.AS_RADIO_BUTTON) { override def run() = setDigitSize(Large) }
    val useMediumDigitsAction = new Action("Medium Digits", IAction.AS_RADIO_BUTTON) { override def run() = setDigitSize(Medium) }
    getDigitSizeFromPreferences match {
      case Large ⇒ useLargeDigitsAction.setChecked(true)
      case Medium ⇒ useMediumDigitsAction.setChecked(true)
    }

    val dropDownMenu = getViewSite.getActionBars.getMenuManager
    dropDownMenu.add(maximiseOnPomodoroEndAction)
    dropDownMenu.add(useLargeDigitsAction)
    dropDownMenu.add(useMediumDigitsAction)

    val holder = new Canvas(parent, SWT.NONE) {
      setLayout(new MigLayout(new LC()))
      pomodoroTimerService.addListener(Listener)
      val pomodoroPanel = new PomodoroPanel(this, getDigitSizeFromPreferences, pomodoroTimerService)
      pomodoroPanelOption = Some(pomodoroPanel)
      pomodoroPanel.setLayoutData(new CC().growX(0.0f).growY(0.0f))

      override def dispose() {
        pomodoroPanel.dispose()
        super.dispose()
      }

    }
    holderOption = Some(holder)
    setPartNameFromPomodoroState(pomodoroTimerService.pomodoroState)
  }

  def getDigitSizeFromPreferences =
    preferencesNode.get(DIGIT_SIZE, DEFAULT_DIGIT_SIZE) match {
      case Large.preferenceKey ⇒ Large
      case Medium.preferenceKey ⇒ Medium
      case _ ⇒ Medium
    }

  def setFocus() = pomodoroPanelOption foreach { _.setFocus() }

  override def dispose() {
    holderOption foreach { _.dispose() }
    holderOption = None
    pomodoroPanelOption = None
    pomodoroTimerService.removeListener(Listener)
    super.dispose()
  }

}

object PomodoroView {

  private val VIEW_ID = "pomodoro.views.PomodoroView"

}