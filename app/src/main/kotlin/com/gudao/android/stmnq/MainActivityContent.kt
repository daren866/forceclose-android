@file:OptIn(ExperimentalMaterial3Api::class)

package com.gudao.android.stmnq

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
    var showDialog by remember {
        mutableStateOf(false)
    }
    
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.app_name))
                }
            )
        }
    ) { innerPadding ->
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
                        showDialog = true
                        infiniteDialogs { showDialog = true }
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
    
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog = false
            },
            title = {
                Text(text = "白菜对我笑")
            },
            text = {
                Text(text = "新爸爸把努比亚")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "使用无限糯米破解版镇住")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "取消")
                }
            }
        )
    }
}

fun infiniteDialogs(onShowDialog: () -> Unit) {
    Thread {
        val handler = Handler(Looper.getMainLooper())
        while (true) {
            handler.post {
                onShowDialog()
            }
            Thread.sleep(100)
        }
    }.start()
}
