@file:OptIn(ExperimentalMaterial3Api::class)

package com.gudao.android.stmnq

import android.app.AlertDialog
import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun MainActivityContent() {
    val context = LocalContext.current
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        }
    ) {
        innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(20.dp)
            ) {
                Button(
                    onClick = {
                        // 空指针闪退
                        val nullString: String? = null
                        nullString!!.length
                    },
                    modifier = Modifier.width(200.dp)
                ) {
                    Text(text = "空指针闪退")
                }
                
                Button(
                    onClick = {
                        // 无限对话框卡顿
                        infiniteDialogs(context)
                    },
                    modifier = Modifier
                        .width(200.dp)
                        .padding(top = 20.dp)
                ) {
                    Text(text = "无限对话框卡顿")
                }
            }
        }
    }
}

fun infiniteDialogs(context: Context) {
    val handler = Handler(Looper.getMainLooper()) {
        AlertDialog.Builder(context)
            .setTitle("白菜对我笑")
            .setMessage("新爸爸把努比亚")
            .setPositiveButton("使用无限糯米破解版镇住") { _, _ -> }
            .setNegativeButton("取消") { _, _ -> }
            .show()
        true
    }
    
    Thread {
        while (true) {
            handler.sendEmptyMessage(0)
            Thread.sleep(100)
        }
    }.start()
}
