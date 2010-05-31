package pomodoro

import org.eclipse.ui.plugin.AbstractUIPlugin
import org.osgi.framework.BundleContext
import org.eclipse.ui._
import org.eclipse.core.runtime._
import org.eclipse.core.runtime.preferences._

class PomodoroPlugin extends AbstractUIPlugin {

  import PreferenceConstants._
  import PomodoroPlugin._
  override def start(context: BundleContext) {
    super.start(context)
    PomodoroPlugin.pluginOpt = Some(this)
    val initialPomodoroDuration: Minutes = preferencesNode.getInt(POMODORO_DURATION, DEFAULT_POMODORO_DURATION) match {
      case d if d <= 0 | d >= 60 => DEFAULT_POMODORO_DURATION
      case d => d
    }
    val initialPomodoroState = preferencesNode.getLong(TARGET_TIME, DEFAULT_TARGET_TIME) match {
      case DEFAULT_TARGET_TIME => NotRunning
      case targetTime if targetTime > System.currentTimeMillis => InPomodoro(targetTime)
      case _ => NotRunning
    }
    pomodoroTimerServiceOption = Some(new PomodoroTimerService(initialPomodoroDuration, initialPomodoroState))
  }

  override def stop(context: BundleContext) {
    PomodoroPlugin.pluginOpt = None
    pomodoroTimerServiceOption foreach { _.stopPomodoro() }
    pomodoroTimerServiceOption = None
    super.stop(context)
  }

  def getImageDescriptor(path: String) = AbstractUIPlugin.imageDescriptorFromPlugin(PomodoroPlugin.PLUGIN_ID, path)

}

object PomodoroPlugin {

  var pluginOpt: Option[PomodoroPlugin] = None

  val PLUGIN_ID = "pomodoro4eclipse"

  var pomodoroTimerServiceOption: Option[PomodoroTimerService] = None
  
  def preferencesNode = Platform.getPreferencesService.getRootNode.node(InstanceScope.SCOPE).node(PomodoroPlugin.PLUGIN_ID)

}