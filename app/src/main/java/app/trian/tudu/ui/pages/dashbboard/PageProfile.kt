package app.trian.tudu.ui.pages.dashbboard

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import app.trian.tudu.R
import app.trian.tudu.common.*
import app.trian.tudu.domain.ChartModelData
import app.trian.tudu.ui.component.chart.BarChartView
import app.trian.tudu.ui.component.customShape.CurveShape
import app.trian.tudu.ui.component.task.BottomSheetInputNewTask
import app.trian.tudu.ui.theme.TuduTheme
import app.trian.tudu.viewmodel.TaskViewModel
import app.trian.tudu.viewmodel.UserViewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import compose.icons.Octicons
import compose.icons.octicons.ArrowLeft24
import compose.icons.octicons.Gear16
import compose.icons.octicons.Person24
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Composable
fun PageProfile(
    modifier: Modifier=Modifier,
    router: NavHostController
){
    val scope = rememberCoroutineScope()
    val userViewModel = hiltViewModel<UserViewModel>()
    val taskViewModel = hiltViewModel<TaskViewModel>()
    val currentUser by userViewModel.currentUser.observeAsState()

    val allTaskCount by taskViewModel.allTaskCount.observeAsState(initial = 0)
    val completeTaskCount by taskViewModel.completedTaskCount.observeAsState(initial = 0)
    val unCompleteTaskCount by taskViewModel.unCompleteTaskCount.observeAsState(initial = 0)
    val chartStatistics by taskViewModel.chartCompleteTask.observeAsState(initial = ChartModelData())
    val currentDate by taskViewModel.currentDate.observeAsState(initial = getNowMillis())


    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false,
    )

    val isDark = isSystemInDarkTheme()
    fun signOut(){
        scope.launch(Dispatchers.Main) {
            router.signOut()
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        userViewModel.getCurrentUser()
        taskViewModel.calculateTaskCount()
        taskViewModel.getStatisticChart()
    })
    BasePagesDashboard(
        router = router,
        currentUser = currentUser,
        enableDrawerGesture = true,
        sheetContent={
            BottomSheetInputNewTask()
        },
        onLogout = {
            userViewModel.signOut{
                signOut()
            }
        },
        modalBottomSheetState=modalBottomSheetState
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    elevation = 0.dp,
                    title = {
                            Text(
                                stringResource(R.string.title_page_profile),
                                style = TextStyle(
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colors.onPrimary
                                )
                            )
                    },
                    actions = {
                        IconToggleButton(
                            checked = false,
                            onCheckedChange = {
                                router.navigate(Routes.PAGE_USER_INFORMATION){
                                    launchSingleTop = true
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Octicons.Person24,
                                contentDescription = stringResource(R.string.content_description_button_user_profile),
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    }
                )
            }
        ) {
            Column {
                Box {
                    Box (
                        modifier = modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(MaterialTheme.colors.primary)
                    ){

                    }
                    Card(
                        modifier=modifier.padding(
                            horizontal = 30.dp,
                            vertical = 20.dp
                        )
                    ) {
                        Column(
                            modifier=modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Spacer(modifier = modifier.height(20.dp))
                            Image(

                                modifier= modifier
                                    .size(70.dp)
                                    .clip(CircleShape),
                                painter = rememberImagePainter(
                                    data = currentUser?.photoUrl,
                                    builder = {
                                        transformations(CircleCropTransformation())
                                    }
                                ),
                                contentDescription = stringResource(R.string.content_description_profile_image),
                            )

                            Column(
                                modifier=modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Spacer(modifier = modifier.height(30.dp))
                                Text(currentUser?.displayName ?: stringResource(R.string.placeholder_unknown))
                                Text(currentUser?.email ?: stringResource(id = R.string.placeholder_unknown))
                                Spacer(modifier = modifier.height(30.dp))
                            }
                            Column(
                                modifier = modifier.fillMaxWidth(),
                            ) {
                                Divider()
                                Spacer(modifier = modifier.height(10.dp))
                                Row(
                                    modifier = modifier
                                        .fillMaxWidth()
                                        .padding(
                                            horizontal = 30.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "$allTaskCount")
                                        Text(text = stringResource(R.string.label_total_task))
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "$completeTaskCount")
                                        Text(text = stringResource(R.string.label_complete_task))
                                    }
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(text = "$unCompleteTaskCount")
                                        Text(text = stringResource(R.string.label_uncomplete_task))
                                    }
                                }
                            }
                            Spacer(modifier = modifier.height(30.dp))
                        }
                    }
                }
                Spacer(modifier = modifier.height(10.dp))
                Column(
                    modifier = modifier.padding(
                        horizontal = 20.dp
                    )
                ) {
                    BarChartView(
                        title=currentDate.getPreviousWeek().getDateUntil(currentDate),
                        items = chartStatistics.items,
                        labels = chartStatistics.labels,
                        maxAxis = chartStatistics.max,
                        minAxis = chartStatistics.min,
                        onArrowClicked = {
                            taskViewModel.getStatistic(it)
                        }
                    )
                }
            }


        }
    }
}



@ExperimentalMaterialApi
@Preview
@Composable
fun PreviewPageProfile() {
    TuduTheme {
        PageProfile(router = rememberNavController())
    }
}