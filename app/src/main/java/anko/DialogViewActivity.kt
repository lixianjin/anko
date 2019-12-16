package anko

import android.app.Activity
import android.os.Bundle
import android.widget.*
import android.view.*
import org.jetbrains.anko.*

import com.lxj.ankosqlit.R

/**
 * Generate with Plugin
 * @plugin Kotlin Anko Converter For Xml
 * @version 1.3.4
 */
class DialogViewActivity : Activity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		linearLayout {
			gravity = Gravity.CENTER_HORIZONTAL
			orientation = LinearLayout.VERTICAL
			textView {
				text = "title"
			}
			textView {
				text = "content"
			}
			linearLayout {
				orientation = LinearLayout.HORIZONTAL
				button {
					text = "Yes"
				}.lparams(width = 0) {
					weight = 1f
				}
				button {
					text = "No"
				}.lparams(width = 0) {
					weight = 1f
				}
			}.lparams(width = matchParent)
		}
	}
}
