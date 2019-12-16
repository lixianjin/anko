package com.lxj.ankosqlit.commons.misc

import android.graphics.Color
import android.text.InputType
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.marginTop
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.core.widget.doOnTextChanged
import com.lxj.ankosqlit.R
import org.jetbrains.anko.*
import org.jetbrains.anko.custom.ankoView
import org.jetbrains.anko.design.textInputLayout
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onSeekBarChangeListener

/**
 * @description AnkoSqlit
 * @author lixianjin
 * create on 2019-11-18 17:21
 *
 * git地址： https://github.com/Kotlin/anko/wiki/Anko-Layouts
 */
class MiscActivityUI : AnkoComponent<MiscActivity> {

//    override fun createView(ui: AnkoContext<MiscActivity>) = ui.apply {
//        verticalLayout {
//            button("bar")
//        }
//    }.view

    override fun createView(ui: AnkoContext<MiscActivity>) = with(ui) {

        scrollView {

            verticalLayout {

                linearLayout {
                    orientation = LinearLayout.HORIZONTAL
                    backgroundResource = R.color.colorPrimary
                    gravity = CENTER
                    padding = dip(5)
                    // 不少重载 随便设置
                    checkBox("Java", checked = true) {
                        textSize = 18f
                        textColorResource = R.color.colorAccent
                    }
                    checkBox("Kotlin", checked = true) {
                        textSize = 18f
                        textColor = Color.WHITE
                    }
                    // 设置宽高
                }.lparams(width = dip(300), height = dip((300))) {
                    topMargin = dip(20)
                    leftMargin = dip(20)
                }

                textInputLayout {
                    editText {
                        isFocusableInTouchMode = true
                        inputType = InputType.TYPE_CLASS_TEXT
                        hint = "请输入内容"
                    }
                }

                textInputLayout {
                    editText {
                        isFocusableInTouchMode = true
                        inputType = InputType.TYPE_CLASS_NUMBER
                        hintResource = R.string.app_name
                    }
                }

                // setHintTextColor和hintTextColor设置无效，可以通过修改style的AppTheme样式修改全局EditText的hintColor
                textInputLayout {
                    themedEditText{
                        isFocusableInTouchMode = true
                        inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        hint = "改个提示颜色试试？"
                        setHintTextColor(Color.YELLOW)
//                        setHintTextColor(ContextCompat.getColor(ctx, R.color.colorPrimary))
//                        hintTextColor = ContextCompat.getColor(ctx, R.color.colorPrimary)
                    }
                }

                // 自定义样式R.style.EditTextHint放在textInputLayout中hint颜色无效
                // 普通EditText通过style和设置setHintTextColor(Color.YELLOW)都可以修改
                themedEditText(R.style.EditTextHint) {
                    isFocusableInTouchMode = true
                    inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    hint = "设置一个Color颜色看看"
//                    setHintTextColor(Color.YELLOW)
                }

                button("Show") {
                    // implementation "org.jetbrains.anko:anko-sdk25-coroutines:$anko_version"
                    // 支持协程，可以写异步任务，无需调用async(UI)
                    onClick { toast("Hello, Word!") }
                }.lparams(width = wrapContent) {
                    topMargin = dip(10)
                    leftMargin = dip(20)
                    rightMargin = dip(20)
                    bottomMargin = dip(10)
                }

                themedButton("ok", R.style.ThemeOverlay_MaterialComponents_Dark) {

                }

                button("NO").lparams(width = matchParent){
                    // 新增左右/上下margin  paddingVertical和paddingHorizontal过时了
                    horizontalMargin = dip(30)
                    verticalMargin = dip(50)
                }

                val ID_CLICK = 1
                val ID_Too = 1

                relativeLayout {
                    button("Click"){
                        id = ID_CLICK
                        // 协程
//                        onClick(context){
//
//                        }
                    }.lparams { alignParentTop() }

                    button("Too"){
                        id = ID_Too
                    }.lparams { below(ID_CLICK) }.setOnClickListener {

                    }

                }

                seekBar {
                    onSeekBarChangeListener {
                        onProgressChanged { seekBar, i, b ->  }
                        onStartTrackingTouch {  }
                        onStopTrackingTouch {  }
                    }
                }

                editText {
                    addTextChangedListener {
                        doAfterTextChanged {

                        }
                        doBeforeTextChanged { text, start, count, after ->  }

                        doOnTextChanged { text, start, count, after ->  }
                    }
                }

//            frameLayout {
//                val mapView = mapView().lparams(width = matchParent)
//            }

                include<View>(R.layout.layout_dialog_view){
                    backgroundColor = Color.BLUE
                }.lparams(width = dip(200)){
                    margin = dip(20)
                }

                include<Button>(R.layout.layout_include_button){
                    text = "Include Button"
                }

                // 老版本的xml也可以转化anko Layout
                // 1。打开相关xml，
                // 2。Code——>Convert Layout Xml to Kotlin Anko，
                // 3。在java目录下生成一个anko的目录，里面就是生成Xml对应的Activity


            }
        }
    }


    // MapView 不知道是啥东西
//    inline fun ViewManager.mapView(init: MapView.() -> Unit = {}): MapView {
//        return ankoView({ MapView(it) }, theme = 0, init = init)
//    }
}