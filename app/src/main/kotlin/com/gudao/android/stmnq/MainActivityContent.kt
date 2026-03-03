@file:OptIn(ExperimentalMaterial3Api::class)

package com.gudao.android.stmnq

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
    var showDialog by remember { mutableStateOf(false) }
    var showMemoryLeakDialog by remember { mutableStateOf(false) }
    var memoryLeakInput by remember { mutableStateOf("") }
    val leakList = remember { mutableListOf<ByteArray>() }

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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    // 空指针闪退
                    val nullValue: String? = null
                    nullValue!!.length
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp)
            ) {
                Text(text = "空指针闪退")
            }

            Button(
                onClick = {
                    showDialog = true
                    // 启动多线程进行无效操作
                    for (i in 1..100) {
                        Thread {
                            while (true) {
                                // 无效操作
                                val list = mutableListOf<Int>()
                                for (j in 1..10000) {
                                    list.add(j)
                                }
                                list.shuffle()
                                list.sort()
                            }
                        }.start()
                    }
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp)
            ) {
                Text(text = "无限对话框卡顿")
            }

            Button(
                onClick = {
                    showMemoryLeakDialog = true
                },
                modifier = Modifier
                    .width(200.dp)
                    .padding(16.dp)
            ) {
                Text(text = "内存泄露测试")
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
                Text(text = "晓88拔努比亚，（唐笑）")
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text(text = "用古人的无限糯米镇住")
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

    if (showMemoryLeakDialog) {
        AlertDialog(
            onDismissRequest = {
                showMemoryLeakDialog = false
                memoryLeakInput = ""
            },
            title = {
                Text(text = "输入")
            },
            text = {
                Column {
                    Text(text = "内存每秒泄露多少mb？")
                    TextField(
                        value = memoryLeakInput,
                        onValueChange = { memoryLeakInput = it },
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        showMemoryLeakDialog = false
                        val leakRate = memoryLeakInput.toIntOrNull() ?: 1
                        // 开始内存泄露
                        Thread {
                            while (true) {
                                try {
                                    // 泄露指定大小的内存
                                    val byteArray = ByteArray(leakRate * 1024 * 1024) // 1MB = 1024*1024 bytes
                                    leakList.add(byteArray)
                                    Thread.sleep(1000) // 每秒泄露一次
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                        }.start()
                        memoryLeakInput = ""
                    }
                ) {
                    Text(text = "确定")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showMemoryLeakDialog = false
                        memoryLeakInput = ""
                    }
                ) {
                    Text(text = "取消")
                }
            }
        )
    }
}
