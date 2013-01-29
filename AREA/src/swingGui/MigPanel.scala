/**
 * The MIT License
 *
 * Copyright (c) 2010 Benjamin Klum
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package swingGui;

import scala.swing.{Panel, Component, LayoutContainer}
import net.miginfocom.swing.MigLayout

object MigPanel {

  var SeparatorColor = new scala.swing.Color(0, 70, 213)
  
  def addSeparatorTo(panel: MigPanel, label: String): MigPanel = {
    import scala.swing.{Label, Separator}
    
    val lbl = new Label(label)
    lbl.foreground = SeparatorColor
    
    val sep = new Separator
    
    panel.add(lbl, "gapbottom 1, span, split 2, aligny center")
    panel.add(sep, "gapleft rel, growx")
    panel
  }
  
}

class MigPanel(
  val layoutConstraints: String = "",
  val columnConstraints: String = "",
  val rowConstraints: String = "") extends Panel with LayoutContainer {
  
  override lazy val peer = {
    val mig = new MigLayout(
      layoutConstraints,
      columnConstraints,
      rowConstraints
    )
    new javax.swing.JPanel(mig) with SuperMixin
  }
  
  type Constraints = String
  
  private def layoutManager = peer.getLayout.asInstanceOf[MigLayout]
  
  protected def constraintsFor(comp: Component): Constraints =
    layoutManager.getComponentConstraints(comp.peer).asInstanceOf[String]
  
  protected def areValid(constr: Constraints): (Boolean, String) = (true, "")
  
  def add(comp: Component, constr: String): Unit = peer.add(comp.peer, constr)

  def add(comp: Component): Unit = add(comp, "")
}
