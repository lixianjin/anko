package com.lxj.ankosqlit.commons.dialogs

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable

import com.lxj.ankosqlit.R
import com.lxj.ankosqlit.commons.misc.MiscActivity
import kotlinx.android.synthetic.main.activity_anko_dialog.*
import org.jetbrains.anko.*
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.design.textInputEditText



class DialogActivity : AppCompatActivity(), AnkoLogger {

    private val log = AnkoLogger(this.javaClass)
    private val logWithASpecificTag = AnkoLogger("my_tag")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anko_dialog)

        Log.isLoggable("", Log.INFO)


        logWithASpecificTag.info("London is the capital of Great Britain")
        logWithASpecificTag.info { "London is the capital of Great Britain" }
        debug(5) // .toString() method will be executed
        debug { 5 }
        warn(null) // "null" will be printed

        btn_toast.setOnClickListener {
            toast("toast")
        }

        btn_long_toast.setOnClickListener {
            longToast("long toast")
        }

        btn_snack_bar.setOnClickListener {
            it.snackbar("snackBar")
        }

        btn_long_snack_bar.setOnClickListener {
            it.longSnackbar("long snackBar")
        }

        btn_click_snack_bar.setOnClickListener {
            it.snackbar("click snackBar", "press") {
                toast("click snackBar press")
            }
        }

        btn_alert_dialog.setOnClickListener {
            alert("Content", "Title") {
                yesButton {
                    toast("Yes, It is a Alert")
                }
                noButton {
                    toast("NO, It is a toast")
                }
            }.show()
        }

        btn_alert_dialog_custom_button.setOnClickListener {
            alert("Content", "Title") {
                positiveButton("Yes") {
                    toast("Yes, It is a Alert")
                }
                negativeButton("No") {
                    toast("NO, It is a toast")

                }
                neutralPressed("Neutral") {
                    toast("neutral")
                }
                onCancelled {
                    toast("cancel")
                }
                // 设置点击dialog外部不可取消
                isCancelable = false
                // 设置标题左侧icon(优先)
                iconResource = R.mipmap.ic_launcher
                // 设置标题左侧icon
                icon = createUpDrawable()
                // 自定义标题View
                customTitle = createTitleView()
                // 自定义content的View(如果设置系统自带的标题等也会显示)
                customView = createView()
            }.show()
        }

        btn_alert_dialog_appcompat.setOnClickListener {
            alert(Appcompat, "Some text message").show()
        }

        btn_alert_dialog_custom_view.setOnClickListener {
            alert {
                customView {
                    textInputEditText().setText("1111")
                }
            }.show()
        }

        btn_alert_dialog_select.setOnClickListener {
            val list = listOf("北京", "上海", "广州", "深圳")
            selector("选择城市", list) { _, i ->
                toast("选择了${list[i]}")
            }
        }

        btn_alert_dialog_progress.setOnClickListener {
            progressDialog("message", "title").apply {
                progress = 20
                max = 100
                secondaryProgress = 30
            }
        }

        btn_alert_dialog_other_progress.setOnClickListener {
            val normal = resources.getDrawable(R.drawable.shape_progress)
            progressDialog("message", "title").apply {
                // 设置加载中的样式后无法设置进度
                isIndeterminate = true
            }
        }

        btn_alert_dialog_indeterminate_progress.setOnClickListener {
            indeterminateProgressDialog("message", "title") {
            }
        }

        btn_start_layout_activity.setOnClickListener {
            startActivity<MiscActivity>()
        }
    }

    private fun createUpDrawable() = with(DrawerArrowDrawable(this)) {
        progress = 1f
        this
    }

    private fun createTitleView() =
        LayoutInflater.from(this).inflate(R.layout.layout_dialog_title, null, false)

    private fun createView() =
        LayoutInflater.from(this).inflate(R.layout.layout_dialog_view, null, false)
}
