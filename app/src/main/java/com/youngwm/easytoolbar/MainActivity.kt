package com.youngwm.easytoolbar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.youngwm.widget.EasyToolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<EasyToolbar>(R.id.toolbar).apply {
            useLightStyle()
            title = "标题在代码中添加"
            addBackImageView(R.drawable.ic_baseline_arrow_back_24) {
                setOnClickListener { Toast.makeText(this@MainActivity, "返回图标", Toast.LENGTH_SHORT).show() }
            }
            addBackTextView {
                text = "返回"
                setOnClickListener { Toast.makeText(this@MainActivity, "返回文本", Toast.LENGTH_SHORT).show() }
            }
            addImageViewMenu(R.drawable.ic_baseline_menu_open_24) {
                setOnClickListener { Toast.makeText(this@MainActivity, "菜单图标", Toast.LENGTH_SHORT).show() }
            }

            addTextViewMenu {
                text = "更多"
                setOnClickListener { Toast.makeText(this@MainActivity, "更多文本", Toast.LENGTH_SHORT).show() }
            }
        }

        findViewById<EasyToolbar>(R.id.toolbar2).apply {
            setBackgroundColor(Color.RED)
            useDarkTintStyle()
            title = "显示过长标题的样式效果待优化"
            widgetAddMargin = 10
            addBackImageView(R.drawable.ic_baseline_arrow_back_24)
            addBackTextView("返回")
            addImageViewMenu(R.drawable.ic_baseline_menu_open_24)
            addTextViewMenu("更多")
            addTextViewMenu("...")
        }

        findViewById<EasyToolbar>(R.id.toolbar2).apply {
            setBackgroundColor(Color.RED)
            useDarkTintStyle()
            title = "显示过长标题的样式效果待优化"
            widgetAddMargin = 10
            addBackImageView(R.drawable.ic_baseline_arrow_back_24)
            addBackTextView("返回")
            addImageViewMenu(R.drawable.ic_baseline_menu_open_24)
            addTextViewMenu("更多")
            addTextViewMenu("...")
        }

        findViewById<EasyToolbar>(R.id.toolbar3).apply {
            customUiStyle(Color.parseColor("#e9536c"), Color.WHITE)
            widgetAddMargin = 10
            addBackImageView(R.drawable.ic_baseline_arrow_back_24)
            addBackTextView("自定义颜色")
            addTextViewMenu("...")
        }

        findViewById<EasyToolbar>(R.id.toolbar4).apply {
            widgetAddMargin = 10
            useDarkTintStyle()
            addBackTextView("自定义控件")
            addTextViewMenu("...")
            addViewMenu(Button(this@MainActivity)){
                setOnClickListener { Toast.makeText(this@MainActivity, "按键", Toast.LENGTH_SHORT).show() }
            }
        }


    }
}
