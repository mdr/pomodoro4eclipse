package pomodoro.views

import pomodoro._
import org.eclipse.swt.widgets.{ List ⇒ _, _ }
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics._
import org.eclipse.swt.layout._
import org.eclipse.swt.events._

case class Digits(m1: Int, m2: Int, s1: Int, s2: Int) {

  private def isDigit(d: Int) = d >= 0 && d <= 9
  require(isDigit(m1))
  require(isDigit(m2))
  require(isDigit(s1))
  require(isDigit(s2))

  val minutes = m1 * 10 + m2

}

sealed trait DigitSize { val code: String; val preferenceKey: String }
case object Large extends DigitSize { val code = "L"; val preferenceKey = "Large" }
case object Medium extends DigitSize { val code = "M"; val preferenceKey = "Medium" }

sealed trait Colour { val code: String }
case object Red extends Colour { val code = "R" }
case object Green extends Colour { val code = "G" }
case object Yellow extends Colour { val code = "Y" }

class DigitalClock(parent: Composite, initialDigitSize: DigitSize, initialDigits: Digits, initialColour: Colour) extends Canvas(parent, SWT.NONE) {

  import Images._

  private var digits: Digits = initialDigits
  private implicit var colour: Colour = initialColour
  private implicit var digitSize = initialDigitSize

  def setDigits(digits: Digits) {
    this.digits = digits
    redraw()
  }

  def setColour(colour: Colour) {
    this.colour = colour
    redraw()
  }

  def setDigitSize(implicit digitSize: DigitSize) {
    this.digitSize = digitSize
    val (width, height) = size
    setBounds(0, 0, width, height)
    redraw()
  }

  private def size = (digitWidth * 4 + colonWidth, height)

  override def computeSize(wHint: Int, hHint: Int, changed: Boolean) = new Point(digitWidth * 4 + colonWidth, height)

  addPaintListener(new PaintListener() {
    def paintControl(e: PaintEvent) {
      val gc = e.gc
      val backgroundColour = getDisplay.getSystemColor(SWT.COLOR_BLACK)
      gc.setBackground(backgroundColour)
      gc.fillRectangle(e.x, e.y, e.width, e.height)
      val Digits(m1, m2, s1, s2) = digits
      gc.drawImage(digitImage(m1), 0 * digitWidth, 0)
      gc.drawImage(digitImage(m2), 1 * digitWidth, 0)
      gc.drawImage(colonImage, 2 * digitWidth, 0)
      gc.drawImage(digitImage(s1), 2 * digitWidth + colonWidth, 0)
      gc.drawImage(digitImage(s2), 3 * digitWidth + colonWidth, 0)
    }
  })

  setBounds(0, 0, digitWidth * 4 + colonWidth, height)

  override def dispose() {
    Images.dispose()
    super.dispose()
  }

  object Images {
    // TODO: Initialise images statically? use red?

    private val imagesByColour: Map[(Colour, DigitSize), List[Image]] =
      (for {
        colour ← List(Red, Green, Yellow)
        size ← List(Large, Medium)
        val images = "0123456789d" map { loadImage(_, colour, size) } toList
      } yield ((colour, size) -> images)) toMap

    private def loadImage(c: Char, colour: Colour, size: DigitSize): Image = {
      val resourceName = "/pomodoro/images/" + c + colour.code + size.code + ".png"
      val stream = getClass.getResourceAsStream(resourceName)
      if (stream == null)
        throw new RuntimeException("Cannot load image " + resourceName)
      new Image(getDisplay, stream)
    }

    private val COLON_INDEX = 10

    private def digitBounds(size: DigitSize) = imagesByColour(Red, size)(0).getBounds

    private def colonBounds(size: DigitSize) = imagesByColour(Red, size)(COLON_INDEX).getBounds

    def digitImage(n: Int)(implicit colour: Colour, size: DigitSize) = imagesByColour((colour, size))(n)

    def colonImage(implicit colour: Colour, size: DigitSize) = imagesByColour((colour, size))(COLON_INDEX)

    def height(implicit size: DigitSize) = digitBounds(size).height

    def digitWidth(implicit size: DigitSize) = digitBounds(size).width

    def colonWidth(implicit size: DigitSize) = colonBounds(size).width

    def dispose() = imagesByColour.values.flatten foreach { _.dispose() }

  }
}