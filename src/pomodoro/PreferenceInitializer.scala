package pomodoro

import org.eclipse.core.runtime.preferences._

class PreferenceInitializer extends AbstractPreferenceInitializer {

  import PreferenceConstants._

  def initializeDefaultPreferences() {
    val node = new DefaultScope().getNode(PomodoroPlugin.PLUGIN_ID)
    node.put(POMODORO_DURATION, DEFAULT_POMODORO_DURATION.toString)
    node.put(ZOOM_ON_POMODORO_COMPLETE, DEFAULT_ZOOM_ON_POMODORO_COMPLETE.toString)
    node.put(DIGIT_SIZE, DEFAULT_DIGIT_SIZE)
    node.put(TARGET_TIME, DEFAULT_TARGET_TIME.toString)
  }

}

object PreferenceConstants {

  val POMODORO_DURATION = "pomodoro.duration"
  val DEFAULT_POMODORO_DURATION = 25

  val ZOOM_ON_POMODORO_COMPLETE = "pomodoro.zoomOnPomodoroComplete"
  val DEFAULT_ZOOM_ON_POMODORO_COMPLETE = true

  val DIGIT_SIZE = "pomodoro.digitSize"
  val DEFAULT_DIGIT_SIZE = "Large"
	   
  /**
   * Not a preference, used to allow the pomodoro timer to survive a restart of Eclipse
   */
  val TARGET_TIME = "pomodoro.targetTime"	  
  val DEFAULT_TARGET_TIME = -1L

}