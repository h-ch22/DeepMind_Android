package com.cj.deepmind.frameworks.ui

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.cj.deepmind.frameworks.models.BottomNavigationItem
import com.cj.deepmind.frameworks.models.COMMUNITY
import com.cj.deepmind.frameworks.models.DRAWING_VIEW
import com.cj.deepmind.frameworks.models.HOME
import com.cj.deepmind.frameworks.models.MANAGE_CONSULTING
import com.cj.deepmind.frameworks.models.MAP
import com.cj.deepmind.frameworks.models.MORE
import com.cj.deepmind.frameworks.models.MainViewModel
import com.cj.deepmind.frameworks.models.NavigationGraph
import com.cj.deepmind.inspection.ui.InspectionCanvasView
import com.cj.deepmind.inspection.ui.InspectionMainView
import com.cj.deepmind.ui.theme.DeepMindColorPalette
import com.cj.deepmind.ui.theme.accent
import com.cj.deepmind.ui.theme.background
import com.cj.deepmind.ui.theme.backgroundAsDark
import com.cj.deepmind.ui.theme.gray
import com.cj.deepmind.ui.theme.red
import com.cj.deepmind.ui.theme.white
import com.cj.deepmind.userManagement.helper.UserManagement
import com.cj.deepmind.userManagement.models.UserTypeModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainView(viewModel: MainViewModel) {

    val navController = rememberNavController()

    val customerItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Map,
        BottomNavigationItem.Community,
        BottomNavigationItem.More
    )

    val professionalItems = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.ManageConsulting,
        BottomNavigationItem.Community,
        BottomNavigationItem.More
    )

    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var showBottomBar by remember {
        mutableStateOf(
            true
        )
    }

    navBackStackEntry?.destination?.route?.let{ route ->
        showBottomBar = when(route){
            HOME, MAP, MANAGE_CONSULTING, COMMUNITY, MORE -> true
            else -> false
        }
    }

    viewModel.setInspectionDrawingViewState(navBackStackEntry?.destination?.route == DRAWING_VIEW)

    val modalSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = {
            showBottomBar = (it == ModalBottomSheetValue.Hidden)
            it != ModalBottomSheetValue.HalfExpanded
        },
        skipHalfExpanded = true
    )

    val context = LocalContext.current
    val showNotificationPermissionDialog = remember {
        mutableStateOf(false)
    }


    BackHandler {
        if (modalSheetState.isVisible) {
            coroutineScope.launch {
                showBottomBar = true
                modalSheetState.hide()
            }
        } else {
            Runtime.getRuntime().runFinalization()
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                BottomAppBar(
                    actions = {
                        if(UserManagement.userInfo?.type == UserTypeModel.PROFESSIONAL){
                            professionalItems.forEach { item ->
                                IconButton(onClick = {
                                    navController.navigate(item.screenRoute) {
                                        navController.graph.startDestinationRoute?.let {
                                            popUpTo(it)
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = if (currentRoute == item.screenRoute) accent else gray
                                    )
                                }
                            }
                        } else{
                            customerItems.forEach { item ->
                                IconButton(onClick = {
                                    navController.navigate(item.screenRoute) {
                                        navController.graph.startDestinationRoute?.let {
                                            popUpTo(it)
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = null,
                                        tint = if (currentRoute == item.screenRoute) accent else gray
                                    )
                                }
                            }
                        }

                    },
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            coroutineScope.launch {
                                if (modalSheetState.isVisible) {
                                    showBottomBar = true
                                    modalSheetState.hide()
                                } else {
                                    showBottomBar = false
                                    modalSheetState.show()
                                }
                            }
                        }, shape = RoundedCornerShape(15.dp), containerColor = accent) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = white
                            )
                        }
                    },
                    containerColor = if(isSystemInDarkTheme()) backgroundAsDark else background
                )
            }
        }
    ) {
        LaunchedEffect(key1 = true) {
            val notificationPermission = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            )

            if (notificationPermission != PackageManager.PERMISSION_GRANTED) {
                showNotificationPermissionDialog.value = true
            }
        }
        Box(modifier = Modifier.padding(it)) {
            NavigationGraph(navController = navController)
        }

        ModalBottomSheetLayout(
            sheetContent = {
                Surface(color = DeepMindColorPalette.current.background) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.background(color = if(isSystemInDarkTheme()) backgroundAsDark else background)) {
                            Spacer(modifier = Modifier.weight(1f))

                            IconButton(onClick = {
                                coroutineScope.launch {
                                    showBottomBar = true
                                    modalSheetState.hide()
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Cancel,
                                    contentDescription = null,
                                    tint = gray
                                )
                            }
                        }
                        InspectionMainView(viewModel=viewModel)
                    }
                }

            },
            sheetState = modalSheetState,
            modifier = Modifier.fillMaxSize(),
            sheetShape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp)
        ) {

        }

        if (showNotificationPermissionDialog.value) {
            AlertDialog(
                onDismissRequest = { showNotificationPermissionDialog.value = false },

                confirmButton = {
                    TextButton(onClick = {
                        showNotificationPermissionDialog.value = false
                        ActivityCompat.requestPermissions(
                            context as MainActivity,
                            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                            1000
                        )
                    }) {
                        Text("확인", color = accent, fontWeight = FontWeight.Bold)
                    }
                },
                title = {
                    Text("권한 상승 필요")
                },
                text = {
                    Text("검사 결과 확인, 커뮤니티 알림 등을 위해 알림 권한이 필요합니다.")
                },
                icon = {
                    Icon(imageVector = Icons.Default.Notifications, contentDescription = null)
                }
            )
        }

        if(viewModel.showInspectionDrawingView.value){
            LaunchedEffect(key1 = true){
                coroutineScope.launch {
                    modalSheetState.hide()

                    navController.navigate(BottomNavigationItem.inspectionDrawingView.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it)
                        }

                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainView_previews() {
    MainView(viewModel = MainViewModel())
}