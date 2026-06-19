package com.example

import android.os.Build
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import kotlin.math.sin
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import androidx.compose.animation.AnimatedVisibility
import kotlinx.coroutines.flow.asSharedFlow
import androidx.compose.runtime.withFrameNanos
import kotlin.math.abs
import kotlin.math.min
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.scale
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.MyApplicationTheme
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import androidx.lifecycle.viewModelScope

import androidx.activity.SystemBarStyle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import android.animation.ObjectAnimator
import android.view.View
import android.animation.Animator
import android.animation.AnimatorListenerAdapter

import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    val splashScreen = installSplashScreen()
    super.onCreate(savedInstanceState)
    
    WindowCompat.setDecorFitsSystemWindows(window, false)
    val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
    windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    windowInsetsController.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    
    var keepSplash = true
    lifecycleScope.launch {
        delay(1200)
        keepSplash = false
    }
    splashScreen.setKeepOnScreenCondition { keepSplash }
    splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->
        val fadeOut = ObjectAnimator.ofFloat(
            splashScreenViewProvider.view,
            View.ALPHA,
            1f,
            0f
        )
        fadeOut.duration = 400L
        fadeOut.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                splashScreenViewProvider.remove()
            }
        })
        fadeOut.start()
    }
    
    setContent {
      MyApplicationTheme {
        GotchaApp()
      }
    }
  }
}

sealed class Screen(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    object Feed : Screen("feed", "Feed", Icons.Filled.Home)
    object Leaderboard : Screen("leaderboard", "Leaderboard", Icons.Filled.EmojiEvents)
    object Profile : Screen("profile", "Profile", Icons.Filled.Person)
}

enum class GameResult {
    WIN, LOSS, NONE
}

data class ParticleData(val x: Float, val y: Float, val size: Float, val speed: Float)

@Composable
fun GameResultOverlay(result: GameResult, content: @Composable () -> Unit) {
    val shakeOffset = remember { Animatable(0f) }
    var triggerBorder by remember { mutableStateOf(false) }
    
    val borderWidth by animateDpAsState(
        targetValue = if (triggerBorder) 6.dp else 0.dp,
        animationSpec = tween(
            durationMillis = if (result == GameResult.WIN) 200 else 150, 
            easing = LinearEasing
        ),
        label = "borderWidth"
    )

    var showParticles by remember { mutableStateOf(false) }
    val particleProgress = remember { Animatable(0f) }
    val particles = remember { 
        List(12) { 
            ParticleData(
                x = (10..90).random() / 100f,
                y = (40..80).random() / 100f,
                size = (5..15).random().toFloat(),
                speed = (2..6).random() / 100f
            )
        }
    }

    LaunchedEffect(result) {
        if (result == GameResult.WIN) {
            triggerBorder = true
            showParticles = true
            launch {
                particleProgress.snapTo(0f)
                particleProgress.animateTo(1f, animationSpec = tween(800, easing = LinearOutSlowInEasing))
                showParticles = false
            }
            delay(200)
            triggerBorder = false
        } else if (result == GameResult.LOSS) {
            triggerBorder = true
            launch {
                shakeOffset.animateTo(12f, tween(50, easing = LinearEasing))
                shakeOffset.animateTo(-12f, tween(100, easing = LinearEasing))
                shakeOffset.animateTo(8f, tween(100, easing = LinearEasing))
                shakeOffset.animateTo(-8f, tween(100, easing = LinearEasing))
                shakeOffset.animateTo(0f, tween(50, easing = LinearEasing))
            }
            delay(150)
            triggerBorder = false
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = shakeOffset.value.dp)
    ) {
        content()
        
        if (triggerBorder || borderWidth > 0.dp) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = borderWidth, 
                        color = if (result == GameResult.WIN) Color(0xFF4ADE80) else Color(0xFFEF4444)
                    )
            )
        }
        
        if (showParticles) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val alpha = (1f - particleProgress.value).coerceIn(0f, 1f)
                particles.forEach { p ->
                    val curY = p.y - (p.speed * particleProgress.value)
                    drawCircle(
                        color = Color(0xFF4ADE80).copy(alpha = alpha),
                        radius = p.size * size.minDimension / 100f,
                        center = androidx.compose.ui.geometry.Offset(
                            x = p.x * size.width,
                            y = curY * size.height
                        )
                    )
                }
            }
        }
    }
}

enum class GameType {
    REACTION, CRASH, MINES, HILO, MEMORY, PLINKO, PATTERN, PRECISION, RISK, PREDICTION
}

data class GameCard(
    val id: String,
    val title: String,
    val type: GameType
)

class EngagementVelocityViewModel : ViewModel() {
    private val _userProgressionScore = MutableStateFlow(1000f)
    val userProgressionScore = _userProgressionScore.asStateFlow()

    fun reEngageSession() {
        _userProgressionScore.value = 1000f
    }

    init {
        // Telemetry loop: Simulates background score decay during periods of idle state
        viewModelScope.launch {
            while (isActive) {
                delay(5000)
                _userProgressionScore.update { currentScore -> 
                    (currentScore - 5f).coerceAtLeast(0f) 
                }
            }
        }
    }
}

class ContinuousLoopManager : ViewModel() {
    private val _momentumMultiplier = MutableStateFlow(1)
    val momentumMultiplier = _momentumMultiplier.asStateFlow()

    private val _countdownTicks = MutableStateFlow(0)
    val countdownTicks = _countdownTicks.asStateFlow()

    private var currentJob: kotlinx.coroutines.Job? = null

    fun triggerSessionEnd(bypass: Boolean = false, onFinished: () -> Unit = {}) {
        currentJob?.cancel()
        if (bypass) {
            _countdownTicks.value = 0
            _momentumMultiplier.value += 1
        } else {
            currentJob = viewModelScope.launch {
                _countdownTicks.value = 3
                while (_countdownTicks.value > 0) {
                    delay(1000)
                    _countdownTicks.value -= 1
                }
                _momentumMultiplier.value += 1
                onFinished()
            }
        }
    }
}

class GreedStakesTracker : ViewModel() {
    private val _accumulatedStakes = MutableStateFlow(0.0)
    val accumulatedStakes = _accumulatedStakes.asStateFlow()
    
    private val _withdrawalFeeMultiplier = MutableStateFlow(0.5f) // Early cashout penalty
    val withdrawalFeeMultiplier = _withdrawalFeeMultiplier.asStateFlow()

    fun advanceMilestone() {
        _accumulatedStakes.update { current -> current * 1.8 } // Exponential scale
        // The penalty for cashing out drops by 5% each round, inducing "one more turn"
        _withdrawalFeeMultiplier.update { current -> (current - 0.05f).coerceAtLeast(0.0f) }
    }
}

@Composable
fun GotchaApp(playerViewModel: PlayerViewModel = viewModel(), engagementViewModel: EngagementVelocityViewModel = viewModel()) {
    val navController = rememberNavController()
    val items = listOf(Screen.Feed, Screen.Leaderboard, Screen.Profile)

    GlobalEffectOverlay(playerViewModel = playerViewModel) {
        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navController,
                startDestination = Screen.Feed.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Feed.route) { FeedScreen(playerViewModel, engagementViewModel) }
                composable(Screen.Leaderboard.route) { LeaderboardScreen() }
                composable(Screen.Profile.route) { ProfileScreen() }
            }

            NavigationBar(
            modifier = Modifier.align(Alignment.BottomCenter).background(Color.Transparent),
            containerColor = Color.Transparent,
            contentColor = Color.White
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            items.forEach { screen ->
                NavigationBarItem(
                    icon = { Icon(screen.icon, contentDescription = screen.title) },
                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                    colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                        selectedIconColor = Color(0xFF8B5CF6),
                        unselectedIconColor = Color.White.copy(alpha = 0.5f),
                        indicatorColor = Color.Transparent
                    ),
                    onClick = {
                        navController.navigate(screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
}

@Composable
fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = title)
    }
}

data class BouncingParticle(
    var x: Float,
    var y: Float,
    var vx: Float,
    var vy: Float,
    var color: Color,
    var size: Float,
    var life: Float = 1.0f
)

@Composable
fun GlobalEffectOverlay(playerViewModel: PlayerViewModel, content: @Composable () -> Unit) {
    var particles by remember { mutableStateOf(emptyList<BouncingParticle>()) }
    var shaking by remember { mutableStateOf(false) }
    val shakeOffset = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        playerViewModel.gameEvents.collect { event ->
            if (event == GameEvent.Win || event == GameEvent.LevelUp) {
                // Generate violent particles
                val neonColors = listOf(Color(0xFF00FFCC), Color(0xFFFF00FF), Color(0xFFFFFF00), Color(0xFFFF3300))
                val newParticles = List(70) {
                    // Random burst from center
                    val angle = (Math.random() * 2 * Math.PI).toFloat()
                    val speed = (Math.random() * 0.04f + 0.02f).toFloat() // faster
                    BouncingParticle(
                        x = 0.5f,
                        y = 0.5f,
                        vx = cos(angle.toDouble()).toFloat() * speed,
                        vy = sin(angle.toDouble()).toFloat() * speed,
                        color = neonColors.random(),
                        size = (15..45).random().toFloat()
                    )
                }
                particles = newParticles
                
                launch {
                    // Violently shake
                    shaking = true
                    repeat(15) {
                        shakeOffset.animateTo((Math.random() * 60 - 30).toFloat() * if(it%2==0) 1f else -1f, tween(30, easing = LinearEasing))
                    }
                    shakeOffset.animateTo(0f, tween(30))
                    shaking = false
                }
            }
        }
    }

    LaunchedEffect(particles) {
        if (particles.isNotEmpty()) {
            var lastTime = withFrameNanos { it }
            while (particles.any { it.life > 0 }) {
                val time = withFrameNanos { it }
                val dt = (time - lastTime) / 1000000000f
                lastTime = time

                particles = particles.map { p ->
                    if (p.life <= 0f) return@map p
                    var nx = p.x + p.vx * dt * 60f // dt * 60 assumes 60fps baseline
                    var ny = p.y + p.vy * dt * 60f 
                    var nvx = p.vx
                    var nvy = p.vy + 0.001f * dt * 60f // Gravity
                    var nlife = p.life - 0.015f * dt * 60f
                    
                    // Bounce off walls right
                    if (nx >= 1f) {
                        nvx = -nvx * 0.8f
                        nx = 1f
                    }
                    // Bounce left
                    if (nx <= 0f) {
                        nvx = -min(nvx, -nvx) * 0.8f // ensure positive output
                        nvx = abs(nvx) * 0.8f
                        nx = 0f
                    }
                    
                    // Bounce bottom
                    if (ny >= 1f) {
                        nvy = -nvy * 0.8f
                        ny = 1f
                    }
                    // Bounce top
                    if (ny <= 0f) {
                        nvy = abs(nvy) * 0.8f
                        ny = 0f
                    }
                    
                    p.copy(x = nx, y = ny, vx = nvx, vy = nvy, life = nlife)
                }
            }
            particles = emptyList() // clear
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(x = shakeOffset.value.dp, y = if (shaking) shakeOffset.value.dp * 0.5f else 0.dp) // Shake in both X and Y
    ) {
        content()

        if (particles.isNotEmpty()) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                particles.forEach { p ->
                    if (p.life > 0f) {
                        drawCircle(
                            color = p.color.copy(alpha = p.life),
                            radius = p.size * p.life,
                            center = androidx.compose.ui.geometry.Offset(p.x * size.width, p.y * size.height)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(playerViewModel: PlayerViewModel = viewModel()) {
    val playerState by playerViewModel.uiState.collectAsState()
    
    val xp = playerState.totalXP
    val (nextRankXP, nextRankName) = when {
        xp < 500 -> 500 to "Hustler"
        xp < 1500 -> 1500 to "Rising Star"
        xp < 4000 -> 4000 to "Elite"
        xp < 10000 -> 10000 to "Legendary"
        xp < 20000 -> 20000 to "GOD TIER"
        else -> xp to "Max Rank"
    }
    val baseXP = when {
        xp < 500 -> 0
        xp < 1500 -> 500
        xp < 4000 -> 1500
        xp < 10000 -> 4000
        xp < 20000 -> 10000
        else -> 20000
    }
    
    val targetProgress = if (nextRankXP == baseXP) 1f else (xp - baseXP).toFloat() / (nextRankXP - baseXP).toFloat()
    
    var startProgressAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        startProgressAnimation = true
    }
    
    val progressAnim by animateFloatAsState(
        targetValue = if (startProgressAnimation) targetProgress else 0f,
        animationSpec = tween(1000, easing = LinearEasing),
        label = "progress"
    )

    val rankColor = when(playerState.rank.lowercase(java.util.Locale.US)) {
        "bronze" -> Color(0xFFCD7F32)
        "silver" -> Color(0xFFC0C0C0)
        "gold" -> Color(0xFFF59E0B)
        "platinum" -> Color(0xFF38BDF8)
        "diamond" -> Color(0xFFA78BFA)
        else -> Color.White
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(32.dp))
            
            // Avatar
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF38BDF8))))
                    .border(2.dp, Color.White.copy(alpha = 0.2f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Player#1337",
                color = Color.White,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Rank Badge
            Box(
                modifier = Modifier
                    .background(rankColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .border(1.dp, rankColor.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "${playerState.rank.uppercase(java.util.Locale.US)} \ud83c\udfc6",
                    color = rankColor,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))

            // Fake pride stat
            Text(
                text = "🔥 YOU ARE IN THE TOP 1.2% GLOBALLY 🔥",
                color = Color(0xFFF59E0B),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))
            
            // XP Progress
            val xpFormatted = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(xp)
            val nextXpFormatted = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(nextRankXP)
            
            LinearProgressIndicator(
                progress = { progressAnim },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp)),
                color = Color(0xFF8B5CF6),
                trackColor = Color(0xFF13131A)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (nextRankXP == baseXP) "MAX RANK REACHED" else "$xpFormatted / $nextXpFormatted XP to $nextRankName",
                color = Color.White.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Stats Row
            val totalWins = playerState.stats.gamesWonByType.values.sum()
            val totalLosses = playerState.stats.gamesLostByType.values.sum()
            val totalResolved = totalWins + totalLosses
            val winRate = if (totalResolved > 0) ((totalWins.toFloat() / totalResolved.toFloat()) * 100).toInt() else 0
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                StatCard(modifier = Modifier.weight(1f), value = playerState.gamesPlayed.toString(), label = "Games Played")
                StatCard(modifier = Modifier.weight(1f), value = "$winRate%", label = "Win Rate")
                StatCard(modifier = Modifier.weight(1f), value = "${playerState.currentStreak} 🔥", label = "Best Streak")
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            // Achievements Header
            Text(
                text = "Achievements",
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        
        // Achievement Tiles
        item {
            val totalWins = playerState.stats.gamesWonByType.values.sum()
            val hasFirstWin = totalWins > 0
            val hasHighRoller = (playerState.challengeProgress["crash_3x"] ?: 0) >= 3
            val hasReactionPro = (playerState.challengeProgress["reaction_5"] ?: 0) >= 5
            val has7DayStreak = playerState.currentStreak >= 7
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AchievementTile(modifier = Modifier.weight(1f), title = "First Win", icon = "🎯", unlocked = hasFirstWin)
                AchievementTile(modifier = Modifier.weight(1f), title = "High Roller", icon = "💎", unlocked = hasHighRoller)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                AchievementTile(modifier = Modifier.weight(1f), title = "Reaction Pro", icon = "⚡", unlocked = hasReactionPro)
                AchievementTile(modifier = Modifier.weight(1f), title = "7-Day Streak", icon = "🔥", unlocked = has7DayStreak)
            }
        }
    }
}

@Composable
fun StatCard(modifier: Modifier = Modifier, value: String, label: String) {
    Card(
        modifier = modifier,
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF13131A)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = label,
                color = Color.White.copy(alpha = 0.5f),
                style = MaterialTheme.typography.labelSmall,
                maxLines = 1
            )
        }
    }
}

@Composable
fun AchievementTile(modifier: Modifier = Modifier, title: String, icon: String, unlocked: Boolean) {
    val bgColor = if (unlocked) Color(0xFF8B5CF6).copy(alpha = 0.15f) else Color(0xFF1A1A24)
    val borderColor = if (unlocked) Color(0xFF8B5CF6).copy(alpha = 0.5f) else Color.Transparent
    val titleColor = if (unlocked) Color.White else Color.White.copy(alpha = 0.4f)
    val iconAlpha = if (unlocked) 1f else 0.3f

    Card(
        modifier = modifier.aspectRatio(1f),
        colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 40.sp,
                color = Color.White.copy(alpha = iconAlpha)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                color = titleColor,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            if (!unlocked) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Locked",
                    tint = Color.White.copy(alpha = 0.2f),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun GotchaRouletteOverlay(
    isVisible: Boolean,
    onResult: (GameType) -> Unit
) {
    if (!isVisible) return
    
    var spinning by remember { mutableStateOf(true) }
    var displayGame by remember { mutableStateOf(GameType.entries.first()) }
    
    LaunchedEffect(isVisible) {
        if (isVisible) {
            spinning = true
            for (i in 0..25) {
                displayGame = GameType.entries.random()
                delay((30 + i * 15).toLong())
            }
            spinning = false
            delay(1200)
            onResult(displayGame)
        }
    }
    
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
            .clickable(enabled = false) {}, // Intercept clicks
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "GOTCHA PULL!", 
                style = MaterialTheme.typography.displaySmall, 
                color = Color.White,
                fontWeight = FontWeight.Black
            )
            Spacer(Modifier.height(48.dp))
            
            Box(
                Modifier
                    .size(240.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF222233))
                    .border(8.dp, if (!spinning) Color(0xFFFFD700) else Color(0xFF444455), RoundedCornerShape(24.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = displayGame.name.replace("_", " "),
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(Modifier.height(48.dp))
            
            AnimatedVisibility(visible = !spinning) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "⭐ 1.5x XP MULTIPLIER ACTIVE ⭐",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFFFFD700),
                        fontWeight = FontWeight.Black
                    )
                    Spacer(Modifier.height(16.dp))
                    Text("Dropping you in...", color = Color.LightGray)
                }
            }
        }
    }
}

@Composable
fun FeedScreen(playerViewModel: PlayerViewModel = viewModel(), engagementViewModel: EngagementVelocityViewModel = viewModel()) {
    val playerState by playerViewModel.uiState.collectAsState()
    val engagementScore by engagementViewModel.userProgressionScore.collectAsState()
    val showStreakDialog by playerViewModel.showStreakDialog.collectAsState()
    val context = LocalContext.current
    var showRetentionModal by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    androidx.activity.compose.BackHandler(enabled = true) {
        showRetentionModal = true
    }

    if (showRetentionModal) {
        RetentionInterceptModal(
            onDismiss = { showRetentionModal = false },
            onExecuteExit = {
                showRetentionModal = false
                (context as? ComponentActivity)?.finish()
            }
        )
    }

    LaunchedEffect(Unit) {
        if (playerState.feedGames.isEmpty()) {
            playerViewModel.extendFeed()
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        val pagerState = rememberPagerState(pageCount = { playerState.feedGames.size + 1 })
        val context = LocalContext.current

        LaunchedEffect(pagerState.currentPage) {
            if (pagerState.currentPage > 0 || pagerState.currentPage == 0) {
                HapticFeedbackHelper.onSwipe(context)
            }
            if (pagerState.currentPage >= playerState.feedGames.size - 10) {
                playerViewModel.extendFeed()
            }
        }

        VerticalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            if (page == 2) {
                DailyChallengesCard(playerViewModel)
            } else {
                val gameIndex = if (page > 2) page - 1 else page
                if (gameIndex < playerState.feedGames.size) {
                    val isActive = pagerState.currentPage == page
                    GotchaGameView(playerState.feedGames[gameIndex], isActive)
                }
            }
        }
        


        // HUD Overlay
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(50))
                .background(Color(0xB3000000))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left: Progression Score
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Bolt,
                    contentDescription = "Progression",
                    tint = if (engagementScore < 200f) Color.Red else Color(0xFF8B5CF6),
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "SCORE ${engagementScore.toInt()}",
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
            
            // Center: Rank
            val rankColor = when(playerState.rank.lowercase(java.util.Locale.US)) {
                "bronze" -> Color(0xFFCD7F32)
                "silver" -> Color(0xFFC0C0C0)
                "gold" -> Color(0xFFF59E0B)
                "platinum" -> Color(0xFF38BDF8)
                "diamond" -> Color(0xFFA78BFA)
                else -> Color.White
            }
            Text(
                text = playerState.rank.uppercase(java.util.Locale.US),
                color = rankColor,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.ExtraBold
            )

            // Right: Streak
            Text(
                text = "🔥 ${playerState.currentStreak}",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
        
        // Gotcha Pull Lever state
        var showGotchaOverlay by remember { mutableStateOf(false) }

        if (pagerState.currentPage == 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 96.dp, end = 16.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Brush.linearGradient(listOf(Color(0xFFFF007F), Color(0xFFFF5E00))))
                    .clickable { 
                        SoundHelper.playTapSound()
                        showGotchaOverlay = true
                    }
                    .padding(horizontal = 20.dp, vertical = 14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🎰", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "GOTCHA PULL",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
        
        GotchaRouletteOverlay(
            isVisible = showGotchaOverlay,
            onResult = { gameType ->
                showGotchaOverlay = false
                playerViewModel.setGotchaMultiplier(1.5)
                playerViewModel.injectGotchaGame(gameType, pagerState.currentPage + 1)
                scope.launch {
                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                }
            }
        )

        ProximityAssessmentOverlay(
            isActive = engagementScore <= 200f,
            targetedScoreMetric = 142.50,
            onReEngage = {
                engagementViewModel.reEngageSession()
            }
        )
    }
}

@Composable
fun GotchaGameView(
    gameType: GameType,
    isActive: Boolean,
    loopManager: ContinuousLoopManager = viewModel()
) {
    var resetKey by remember { mutableStateOf(0) }
    
    val rapidReentry: () -> Unit = { 
        resetKey++ 
    }
    
    val handleRetry: () -> Unit = { 
        loopManager.triggerSessionEnd(false, onFinished = rapidReentry)
    }

    PerpetualGameViewport(
        loopManager = loopManager,
        onRapidReentry = { resetKey++ }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            androidx.compose.runtime.key(resetKey) {
                if (gameType == GameType.REACTION) {
                    ReactionTestGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.HILO) {
                    HiLoGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.MINES) {
                    MinesGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.CRASH) {
                    CrashGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.MEMORY) {
                    MemoryGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.PRECISION) {
                    PrecisionGame(isActive = isActive, onRetry = handleRetry)
                } else if (gameType == GameType.PATTERN) {
                    PatternGame(isActive = isActive, onRetry = handleRetry)
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF13131A)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Coming Soon",
                                color = Color.White,
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = gameType.name,
                                color = Color(0xFF8B5CF6),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "Swipe Up",
                            tint = Color.White.copy(alpha = 0.5f),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 32.dp)
                                .size(32.dp)
                        )
                    }
                }
            }
            
            SocialProofOverlay(modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}

@Composable
fun PerpetualGameViewport(
    loopManager: ContinuousLoopManager,
    onRapidReentry: () -> Unit,
    content: @Composable () -> Unit
) {
    val multiplier by loopManager.momentumMultiplier.collectAsState()
    val ticksLeft by loopManager.countdownTicks.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0C))) {
        // Core game rendering stays visible in low-contrast background
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent
        ) {
            content()
        }

        // Overlapping prompt that dominates the visual field during the 3-second break
        if (ticksLeft > 0) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "MULTIPLIER CHAIN: ${multiplier}x",
                        color = Color(0xFF00FFCC),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "NEXT ROUND ENFORCED IN: $ticksLeft",
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // High-contrast, flashing action node to instantly bypass the timer
                    val interactionSource = remember { androidx.compose.foundation.interaction.MutableInteractionSource() }
                    val isPressed by interactionSource.collectIsPressedAsState()
                    Button(
                        onClick = { 
                            loopManager.triggerSessionEnd(true) // Simulating instant bypass
                            onRapidReentry() 
                        },
                        interactionSource = interactionSource,
                        modifier = Modifier.glowOnPress(isPressed, Color(0xFFFF0055)),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0055))
                    ) {
                        Text("ENGAGE NEXT STREAM NOW", color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
fun AsynchronousActivityStream() {
    val telemetryEvents = listOf(
        "Node_892x achieved peak velocity in Crash module.",
        "Local node proximity upgrade: 'Neon Emperor' unlocked.",
        "System state: 1,402 concurrent nodes drawing 3x score modifiers."
    )
    var activeEvent by remember { mutableStateOf(telemetryEvents.random()) }

    LaunchedEffect(Unit) {
        while(true) {
            delay(4000)
            activeEvent = telemetryEvents.random()
        }
    }

    Text(
        text = activeEvent,
        color = Color.Cyan,
        modifier = Modifier.fillMaxWidth().background(Color.DarkGray).padding(8.dp)
    )
}

@Composable
fun ProximityAssessmentOverlay(isActive: Boolean, targetedScoreMetric: Double, onReEngage: () -> Unit = {}) {
    if (isActive) {
        Box(
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.85f)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "OPTIMAL PATHWAY VARIANCE 0.2s:",
                    color = Color.Red,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "+${String.format("%.2f", targetedScoreMetric)} METRIC POINTS",
                    color = Color.Green,
                    style = MaterialTheme.typography.displayMedium
                )
                Button(onClick = onReEngage) {
                    Text("RE-ENGAGE SESSION")
                }
            }
        }
    }
}

@Composable
fun RetentionInterceptModal(onDismiss: () -> Unit, onExecuteExit: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Session Interruption Detected") },
        text = { Text("Terminating the session now resets the active 2x Multiplier and alters your current ranking placement.") },
        confirmButton = {
            // Maximum visual weight applied to the retention path
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = onDismiss
            ) {
                Text("MAINTAIN VELOCITY (RECOMMENDED)")
            }
        },
        dismissButton = {
            // Minimum visual weight applied to the exit path
            TextButton(onClick = onExecuteExit) {
                Text("Terminate session and discard metrics", color = Color.Gray)
            }
        }
    )
}

@Composable
fun DailyChallengesCard(playerViewModel: PlayerViewModel) {
    val state by playerViewModel.uiState.collectAsState()
    val progress = state.challengeProgress
    
    val reactCount = progress["reaction_5"] ?: 0
    val crashCount = progress["crash_3x"] ?: 0
    val minesCount = progress["mines_10"] ?: 0
    
    val allDone = reactCount >= 5 && crashCount >= 3 && minesCount >= 10
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF13131A)),
        contentAlignment = Alignment.Center
    ) {
        if (allDone) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .border(2.dp, Color(0xFFF59E0B), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF1E1E29))
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "All Done! 🎉",
                        color = Color(0xFFF59E0B),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Come back tomorrow",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, Brush.verticalGradient(listOf(Color(0xFFF59E0B), Color.Transparent)), RoundedCornerShape(16.dp)),
                shape = RoundedCornerShape(16.dp),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF1E1E29))
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Filled.DateRange, contentDescription = null, tint = Color(0xFFF59E0B))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Daily Challenges", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    ChallengeRow("Win 5 Reaction Tests", reactCount, 5, 200)
                    Spacer(modifier = Modifier.height(16.dp))
                    ChallengeRow("Survive Crash above 3x", crashCount, 3, 150)
                    Spacer(modifier = Modifier.height(16.dp))
                    ChallengeRow("Clear 10 Mines safely", minesCount, 10, 300)
                }
            }
        }
        
        Icon(
            imageVector = Icons.Filled.KeyboardArrowUp,
            contentDescription = "Swipe Up",
            tint = Color.White.copy(alpha = 0.5f),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(32.dp)
        )
    }
}

@Composable
fun ChallengeRow(title: String, current: Int, target: Int, xp: Int) {
    val completed = current >= target
    val progress = minOf(current.toFloat() / target.toFloat(), 1f)
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(title, color = if (completed) Color.White.copy(alpha = 0.5f) else Color.White, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (completed) {
                    Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = null, tint = Color(0xFF22C55E), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Completed", color = Color(0xFF22C55E), style = MaterialTheme.typography.labelMedium)
                } else {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.weight(1f).height(6.dp).clip(RoundedCornerShape(50)),
                        color = Color(0xFFF59E0B),
                        trackColor = Color(0xFF333340),
                        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("$current/$target", color = Color.White.copy(alpha = 0.7f), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (completed) Color(0xFF22C55E).copy(alpha = 0.2f) else Color(0xFFF59E0B).copy(alpha = 0.2f))
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "+$xp XP",
                color = if (completed) Color(0xFF22C55E) else Color(0xFFF59E0B),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

enum class ReactionGameState {
    WAITING, READY, EARLY, RESULT, DONE
}

@Composable
fun ReactionTestGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(ReactionGameState.WAITING) }
    var reactionTime by remember { mutableStateOf(0L) }
    var startTime by remember { mutableStateOf(0L) }
    var showXpReward by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(state, isActive) {
        if (!isActive) return@LaunchedEffect
        if (state == ReactionGameState.WAITING) {
            val delayMillis = (1500L..4000L).random()
            kotlinx.coroutines.delay(delayMillis)
            if (state == ReactionGameState.WAITING) {
                state = ReactionGameState.READY
                startTime = System.currentTimeMillis()
            }
        } else if (state == ReactionGameState.EARLY) {
            HapticFeedbackHelper.onLoss(context)
            kotlinx.coroutines.delay(1500L)
            state = ReactionGameState.DONE
        } else if (state == ReactionGameState.RESULT) {
            HapticFeedbackHelper.onWin(context)
            kotlinx.coroutines.delay(1500L)
            state = ReactionGameState.DONE
        }
    }

    val backgroundColor = when (state) {
        ReactionGameState.READY -> Color(0xFF22C55E)
        ReactionGameState.EARLY -> Color(0xFFEF4444)
        else -> Color(0xFF13131A)
    }

    val gameResult = when (state) {
        ReactionGameState.RESULT, ReactionGameState.DONE -> if (reactionTime > 0) GameResult.WIN else GameResult.NONE
        ReactionGameState.EARLY -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.REACTION, gameResult)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                if (!isActive) return@clickable
                if (state == ReactionGameState.WAITING) {
                    state = ReactionGameState.EARLY
                } else if (state == ReactionGameState.READY) {
                    reactionTime = System.currentTimeMillis() - startTime
                    state = ReactionGameState.RESULT
                    if (reactionTime < 450) {
                        showXpReward = true
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when (state) {
                ReactionGameState.WAITING -> {
                    Text(
                        text = "GET READY...",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                ReactionGameState.READY -> {
                    Text(
                        text = "TAP NOW!",
                        color = Color.Black,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
                ReactionGameState.EARLY -> {
                    Text(
                        text = "TOO EARLY! ❌",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                }
                ReactionGameState.RESULT -> {
                    val category = when {
                        reactionTime < 200 -> "GODLIKE"
                        reactionTime in 200..300 -> "GREAT"
                        reactionTime in 301..450 -> "GOOD"
                        else -> "TOO SLOW"
                    }
                    Text(
                        text = "⚡ ${reactionTime}ms — $category",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                ReactionGameState.DONE -> {
                    Text(
                        text = "DONE ✓",
                        color = Color.White,
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))) {
                        Text("Retry", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        
        XPRewardPopup(
            xpAmount = 25,
            visible = showXpReward,
            onAnimationFinished = { showXpReward = false }
        )
    }
}
}

enum class HiLoGameState {
    PLAYING, CORRECT, WRONG, TIMEOUT
}

@Composable
fun HiLoGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(HiLoGameState.PLAYING) }
    var currentNumber by remember { mutableStateOf((1..13).random()) }
    var nextNumber by remember { mutableStateOf(generateNextNumber(currentNumber)) }
    var timeLeft by remember { mutableStateOf(3000L) }
    var showXpReward by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(state, isActive) {
        if (!isActive) return@LaunchedEffect
        if (state == HiLoGameState.PLAYING) {
            val startTime = System.currentTimeMillis()
            while (timeLeft > 0) {
                val elapsed = System.currentTimeMillis() - startTime
                timeLeft = maxOf(0L, 3000L - elapsed)
                if (timeLeft <= 0L && state == HiLoGameState.PLAYING) {
                    state = HiLoGameState.TIMEOUT
                }
                kotlinx.coroutines.delay(16L)
            }
        } else if (state == HiLoGameState.CORRECT) {
            HapticFeedbackHelper.onWin(context)
        } else if (state == HiLoGameState.WRONG || state == HiLoGameState.TIMEOUT) {
            HapticFeedbackHelper.onLoss(context)
        }
    }

    val cardBackgroundColor = when (state) {
        HiLoGameState.CORRECT -> Color(0xFF22C55E).copy(alpha = 0.3f)
        HiLoGameState.WRONG, HiLoGameState.TIMEOUT -> Color(0xFFEF4444).copy(alpha = 0.3f)
        else -> Color(0xFF13131A)
    }

    val borderColor = when (state) {
        HiLoGameState.CORRECT -> Color(0xFF22C55E)
        HiLoGameState.WRONG, HiLoGameState.TIMEOUT -> Color(0xFFEF4444)
        else -> Color(0xFF8B5CF6)
    }

    val gameResult = when (state) {
        HiLoGameState.CORRECT -> GameResult.WIN
        HiLoGameState.WRONG, HiLoGameState.TIMEOUT -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.HILO, gameResult)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0F)), // match theme background
            contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Card(
                modifier = Modifier
                    .size(240.dp, 360.dp)
                    .border(2.dp, borderColor, RoundedCornerShape(16.dp))
                    .clip(RoundedCornerShape(16.dp)),
                colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = cardBackgroundColor)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    LinearProgressIndicator(
                        progress = { timeLeft / 3000f },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp),
                        color = Color(0xFF8B5CF6),
                        trackColor = Color.Transparent
                    )

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (state == HiLoGameState.PLAYING) currentNumber.toString() else nextNumber.toString(),
                            color = Color.White,
                            style = MaterialTheme.typography.displayLarge,
                            fontWeight = FontWeight.Bold,
                            fontSize = 80.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            if (state == HiLoGameState.PLAYING) {
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Button(
                        onClick = {
                            if (!isActive) return@Button
                            if (nextNumber > currentNumber) {
                                state = HiLoGameState.CORRECT
                                showXpReward = true
                            }
                            else state = HiLoGameState.WRONG
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6), contentColor = Color.White)
                    ) {
                        Text("HIGHER ▲", fontWeight = FontWeight.Bold)
                    }
                    Button(
                        onClick = {
                            if (!isActive) return@Button
                            if (nextNumber < currentNumber) {
                                state = HiLoGameState.CORRECT
                                showXpReward = true
                            }
                            else state = HiLoGameState.WRONG
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6), contentColor = Color.White)
                    ) {
                        Text("LOWER ▼", fontWeight = FontWeight.Bold)
                    }
                }
            } else {
                Text(
                    text = when (state) {
                        HiLoGameState.CORRECT -> "CORRECT! +XP"
                        HiLoGameState.WRONG -> "WRONG ✗"
                        HiLoGameState.TIMEOUT -> "TIMEOUT ✗"
                        else -> ""
                    },
                    color = when (state) {
                        HiLoGameState.CORRECT -> Color(0xFF22C55E)
                        else -> Color(0xFFEF4444)
                    },
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))) {
                    Text("Retry", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        XPRewardPopup(
            xpAmount = 15,
            visible = showXpReward,
            onAnimationFinished = { showXpReward = false }
        )
    }
}
}

fun generateNextNumber(current: Int): Int {
    var next = (1..13).random()
    while (next == current) {
        next = (1..13).random()
    }
    return next
}

enum class MinesGameState {
    PLAYING, CASHED_OUT, BOOM
}

@Composable
fun MinesGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(MinesGameState.PLAYING) }
    var safeTilesFound by remember { mutableStateOf(0) }
    val context = LocalContext.current
    
    val mineIndices = remember {
        val indices = mutableSetOf<Int>()
        while (indices.size < 4) {
            indices.add((0..15).random())
        }
        indices
    }
    
    val tilesRevealed = remember { mutableStateListOf<Boolean>().apply { 
        for (i in 0..15) add(false) 
    } }
    
    val gameResult = when (state) {
        MinesGameState.CASHED_OUT -> GameResult.WIN
        MinesGameState.BOOM -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.MINES, gameResult, score = safeTilesFound)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0F)), // match theme background
            contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Find safe tiles — 4 mines hidden",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            ) {
                items(16) { index ->
                    val isMine = mineIndices.contains(index)
                    val isRevealed = tilesRevealed[index]
                    
                    val bgColor = when {
                        !isRevealed && state == MinesGameState.PLAYING -> Color(0xFF13131A)
                        !isRevealed && state != MinesGameState.PLAYING && isMine -> Color(0xFFEF4444).copy(alpha = 0.5f) // highlight missed mines
                        !isRevealed && state != MinesGameState.PLAYING -> Color(0xFF13131A)
                        isMine -> Color(0xFFEF4444)
                        else -> Color(0xFF22C55E)
                    }
                    
                    val text = when {
                        isRevealed && isMine -> "💥"
                        isRevealed && !isMine -> "💎"
                        !isRevealed && state != MinesGameState.PLAYING && isMine -> "💥"
                        else -> ""
                    }
                    
                    Button(
                        onClick = {
                            if (!isActive) return@Button
                            if (state == MinesGameState.PLAYING && !isRevealed) {
                                tilesRevealed[index] = true
                                if (isMine) {
                                    state = MinesGameState.BOOM
                                    HapticFeedbackHelper.onLoss(context)
                                } else {
                                    safeTilesFound++
                                    HapticFeedbackHelper.onTap(context)
                                    if (safeTilesFound >= 5) {
                                        state = MinesGameState.CASHED_OUT
                                        HapticFeedbackHelper.onWin(context)
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = bgColor,
                            disabledContainerColor = bgColor,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.aspectRatio(1f)
                    ) {
                        Text(text = text, fontSize = 24.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            if (state == MinesGameState.PLAYING) {
                Button(
                    onClick = { 
                        if (!isActive) return@Button
                        state = MinesGameState.CASHED_OUT
                        HapticFeedbackHelper.onTap(context)
                        HapticFeedbackHelper.onWin(context)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6), contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth(0.6f)
                ) {
                    Text("Cash Out", fontWeight = FontWeight.Bold)
                }
            } else {
                Text(
                    text = when(state) {
                        MinesGameState.CASHED_OUT -> if (safeTilesFound >= 5) "CASHED OUT! 🎉 +50 XP" else "CASHED OUT! +${safeTilesFound * 10} XP"
                        MinesGameState.BOOM -> "BOOM! Game Over"
                        else -> ""
                    },
                    color = if (state == MinesGameState.CASHED_OUT) Color(0xFF22C55E) else Color(0xFFEF4444),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
}

enum class CrashGameState {
    PLAYING, CASHED_OUT, CRASHED
}

@Composable
fun CrashGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(CrashGameState.PLAYING) }
    var currentMultiplier by remember { mutableStateOf(1.00) }
    var cashedOutAt by remember { mutableStateOf(0.0) }
    val context = LocalContext.current
    
    val crashPoint = remember { 
        (1.0 - 1.5 * kotlin.math.ln(1.0 - Math.random())).coerceIn(1.2, 10.0)
    }
    
    val history = remember { mutableStateListOf<Double>() }

    LaunchedEffect(state, isActive) {
        if (!isActive) return@LaunchedEffect
        if (state == CrashGameState.PLAYING) {
            history.add(currentMultiplier)
            while (state == CrashGameState.PLAYING) {
                kotlinx.coroutines.delay(100L)
                val newMultiplier = currentMultiplier + (currentMultiplier * 0.05)
                if (newMultiplier >= crashPoint) {
                    currentMultiplier = crashPoint
                    history.add(currentMultiplier)
                    state = CrashGameState.CRASHED
                    HapticFeedbackHelper.onLoss(context)
                } else {
                    currentMultiplier = newMultiplier
                    history.add(currentMultiplier)
                }
            }
        }
    }

    val backgroundColor = when (state) {
        CrashGameState.CASHED_OUT -> Color(0xFF22C55E).copy(alpha = 0.2f)
        CrashGameState.CRASHED -> Color(0xFFEF4444).copy(alpha = 0.2f)
        else -> Color(0xFF0A0A0F)
    }

    val gameResult = when (state) {
        CrashGameState.CASHED_OUT -> GameResult.WIN
        CrashGameState.CRASHED -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.CRASH, gameResult, multiplier = cashedOutAt)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            Canvas(modifier = Modifier.fillMaxWidth().height(240.dp).padding(horizontal = 32.dp)) {
                if (history.isNotEmpty()) {
                    val visiblePoints = history.takeLast(100)
                    val maxMultiplier = visiblePoints.maxOrNull()?.coerceAtLeast(2.0) ?: 2.0
                    val stepX = size.width / (visiblePoints.size.coerceAtLeast(2) - 1).toFloat()
                    
                    val path = Path()
                    visiblePoints.forEachIndexed { index, value ->
                        val x = index * stepX
                        val y = if (state == CrashGameState.CRASHED) size.height else size.height - ((value - 1.0) / (maxMultiplier - 1.0) * size.height).toFloat()
                        if (index == 0) {
                            path.moveTo(x, y)
                        } else {
                            path.lineTo(x, y)
                        }
                    }
                    
                    drawPath(
                        path = path,
                        color = if (state == CrashGameState.CRASHED) Color(0xFFEF4444) else Color(0xFF8B5CF6),
                        style = Stroke(width = 8f)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = String.format(java.util.Locale.US, "%.2fx", currentMultiplier),
                color = when (state) {
                    CrashGameState.CRASHED -> Color(0xFFEF4444)
                    CrashGameState.CASHED_OUT -> Color(0xFF22C55E)
                    else -> Color.White
                },
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            
            if (state == CrashGameState.PLAYING) {
                Button(
                    onClick = {
                        if (!isActive) return@Button
                        cashedOutAt = currentMultiplier
                        state = CrashGameState.CASHED_OUT
                        HapticFeedbackHelper.onTap(context)
                        HapticFeedbackHelper.onWin(context)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF22C55E), contentColor = Color.White),
                    modifier = Modifier.fillMaxWidth(0.6f).height(64.dp)
                ) {
                    Text("CASH OUT", fontWeight = FontWeight.Bold, fontSize = 24.sp)
                }
            } else {
                Text(
                    text = when(state) {
                        CrashGameState.CASHED_OUT -> "Cashed out at ${String.format(java.util.Locale.US, "%.2fx", cashedOutAt)}! 🎉 +XP"
                        CrashGameState.CRASHED -> "CRASHED at ${String.format(java.util.Locale.US, "%.2fx", currentMultiplier)} 💥"
                        else -> ""
                    },
                    color = if (state == CrashGameState.CASHED_OUT) Color(0xFF22C55E) else Color(0xFFEF4444),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
}

enum class MemoryGameState {
    SHOWING, INPUT, WON, LOST
}

@Composable
fun MemoryGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(MemoryGameState.SHOWING) }
    val context = LocalContext.current
    
    val targetIndices = remember {
        val indices = mutableSetOf<Int>()
        while (indices.size < 3) {
            indices.add((0..8).random())
        }
        indices
    }
    
    val tappedIndices = remember { mutableStateListOf<Int>() }
    
    LaunchedEffect(state, isActive) {
        if (!isActive) return@LaunchedEffect
        if (state == MemoryGameState.SHOWING) {
            kotlinx.coroutines.delay(1000L)
            state = MemoryGameState.INPUT
        }
    }

    val gameResult = when (state) {
        MemoryGameState.WON -> GameResult.WIN
        MemoryGameState.LOST -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.MEMORY, gameResult)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0F)),
            contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            val topText = when (state) {
                MemoryGameState.SHOWING -> "Remember 1/3 of sequence"
                MemoryGameState.INPUT -> "Your turn!"
                MemoryGameState.WON -> "Perfect Memory! \uD83E\uDDE0 +50 XP"
                MemoryGameState.LOST -> "Wrong! \u2717"
            }

            Text(
                text = topText,
                color = when (state) {
                    MemoryGameState.WON -> Color(0xFF22C55E)
                    MemoryGameState.LOST -> Color(0xFFEF4444)
                    else -> Color.White
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                items(9) { index ->
                    val isTarget = targetIndices.contains(index)
                    val isTapped = tappedIndices.contains(index)
                    
                    val bgColor = when {
                        state == MemoryGameState.SHOWING && isTarget -> Color(0xFF8B5CF6)
                        state == MemoryGameState.SHOWING -> Color(0xFF13131A)
                        isTapped && isTarget -> Color(0xFF22C55E)
                        isTapped && !isTarget -> Color(0xFFEF4444)
                        state == MemoryGameState.LOST && isTarget -> Color(0xFF8B5CF6).copy(alpha = 0.5f)
                        else -> Color(0xFF13131A)
                    }
                    
                    Button(
                        onClick = {
                            if (!isActive) return@Button
                            if (state == MemoryGameState.INPUT && !isTapped) {
                                tappedIndices.add(index)
                                HapticFeedbackHelper.onTap(context)
                                if (!isTarget) {
                                    state = MemoryGameState.LOST
                                    HapticFeedbackHelper.onLoss(context)
                                } else if (tappedIndices.size == 3) {
                                    state = MemoryGameState.WON
                                    HapticFeedbackHelper.onWin(context)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = bgColor,
                            disabledContainerColor = bgColor,
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.aspectRatio(1f)
                    ) {
                        // Empty tile
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))

            if (state == MemoryGameState.SHOWING || state == MemoryGameState.INPUT) {
                Text(
                    text = "Tip: Focus on position, not pattern",
                    color = Color.White.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            
            if (state == MemoryGameState.WON || state == MemoryGameState.LOST) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))) {
                    Text("Retry", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
}

enum class PrecisionGameState {
    PLAYING, DONE
}

@Composable
fun PrecisionGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(PrecisionGameState.PLAYING) }
    var score by remember { mutableStateOf(0) }
    var targetCount by remember { mutableStateOf(0) }
    
    var flashColor by remember { mutableStateOf(Color.Transparent) }
    var flashVisible by remember { mutableStateOf(false) }

    val flashAlpha by animateFloatAsState(
        targetValue = if (flashVisible) 0.3f else 0f,
        animationSpec = tween(150),
        label = "flash"
    )

    LaunchedEffect(flashVisible) {
        if (flashVisible) {
            kotlinx.coroutines.delay(150L)
            flashVisible = false
        }
    }

    var targetX by remember { mutableStateOf((10..90).random() / 100f) }
    var targetY by remember { mutableStateOf((20..80).random() / 100f) }
    val context = LocalContext.current

    LaunchedEffect(targetCount, isActive) {
        if (!isActive) return@LaunchedEffect
        if (state == PrecisionGameState.PLAYING && targetCount < 5) {
            targetX = (10..90).random() / 100f
            targetY = (20..80).random() / 100f
            
            kotlinx.coroutines.delay(800L)
            
            if (state == PrecisionGameState.PLAYING) {
                flashColor = Color(0xFFEF4444)
                flashVisible = true
                HapticFeedbackHelper.onLoss(context)
                targetCount++
            }
        } else if (state == PrecisionGameState.PLAYING && targetCount >= 5) {
            state = PrecisionGameState.DONE
        }
    }

    val gameResult = when (state) {
        PrecisionGameState.DONE -> if (score >= 3) GameResult.WIN else GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.PRECISION, gameResult)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(modifier = Modifier.fillMaxSize().background(Color(0xFF0A0A0F))) {
            if (flashAlpha > 0f) {
            Box(modifier = Modifier.fillMaxSize().background(flashColor.copy(alpha = flashAlpha)))
        }

        if (state == PrecisionGameState.PLAYING && targetCount < 5) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize().padding(60.dp)) {
                val circleScale = remember(targetCount) { Animatable(0.5f) }
                LaunchedEffect(targetCount) {
                    circleScale.animateTo(1f, tween(150))
                }

                val infiniteTransition = rememberInfiniteTransition(label = "pulse")
                val pulseAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.5f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(400, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "pulseAlpha"
                )

                Box(
                    modifier = Modifier
                        .offset(
                            x = maxWidth * targetX,
                            y = maxHeight * targetY
                        )
                        .scale(circleScale.value)
                        .size(60.dp)
                        .border(
                            width = 4.dp,
                            color = Color(0xFF8B5CF6).copy(alpha = pulseAlpha),
                            shape = CircleShape
                        )
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            if (!isActive) return@clickable
                            if (state == PrecisionGameState.PLAYING) {
                                score++
                                flashColor = Color(0xFF22C55E)
                                flashVisible = true
                                HapticFeedbackHelper.onTap(context)
                                targetCount++
                            }
                        }
                )
            }
        } else if (state == PrecisionGameState.DONE) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val rankText = when (score) {
                    5 -> "PERFECT \ud83c\udfaf"
                    4 -> "SHARP \ud83d\udc4d"
                    3 -> "DECENT"
                    else -> "MISSED \ud83d\ude05"
                }
                Text(
                    text = "You hit $score/5 targets \u2014 $rankText",
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))) {
                    Text("Retry", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
}

enum class PatternGameState {
    PLAYING, CORRECT, WRONG
}

enum class PatternDifficulty {
    EASY, MEDIUM, HARD
}

@Composable
fun PatternGame(isActive: Boolean = true, onRetry: () -> Unit = {}, playerViewModel: PlayerViewModel = viewModel()) {
    var state by remember { mutableStateOf(PatternGameState.PLAYING) }
    var level by remember { mutableStateOf(PatternDifficulty.values().random()) }
    
    val symbols = listOf("⭐", "🔺", "🔵", "🟣", "🟡")
    
    var grid by remember { mutableStateOf(emptyList<String>()) }
    var answer by remember { mutableStateOf("") }
    var options by remember { mutableStateOf(emptyList<String>()) }
    var missingIndex by remember { mutableStateOf(-1) }
    var selectedOption by remember { mutableStateOf("") }
    val context = LocalContext.current
    
    fun generateGame() {
        val pickedSymbols = symbols.shuffled()
        val s1 = pickedSymbols[0]
        val s2 = pickedSymbols[1]
        val s3 = pickedSymbols[2]
        
        val newGrid = mutableListOf<String>()
        missingIndex = (0..5).random()
        
        when (level) {
            PatternDifficulty.EASY -> {
                newGrid.add(s1); newGrid.add(s1); newGrid.add(s1)
                newGrid.add(s2); newGrid.add(s2); newGrid.add(s2)
            }
            PatternDifficulty.MEDIUM -> {
                newGrid.add(s1); newGrid.add(s2); newGrid.add(s1)
                newGrid.add(s2); newGrid.add(s1); newGrid.add(s2)
            }
            PatternDifficulty.HARD -> {
                newGrid.add(s1); newGrid.add(s2); newGrid.add(s3)
                newGrid.add(s1); newGrid.add(s2); newGrid.add(s3)
            }
        }
        
        answer = newGrid[missingIndex]
        grid = newGrid
        
        val opts = mutableSetOf(answer)
        while (opts.size < 3) {
            opts.add(pickedSymbols.random())
        }
        options = opts.shuffled().toList()
        state = PatternGameState.PLAYING
        selectedOption = ""
    }
    
    LaunchedEffect(Unit, isActive) {
        if (!isActive) return@LaunchedEffect
        generateGame()
    }

    val gameResult = when (state) {
        PatternGameState.CORRECT -> GameResult.WIN
        PatternGameState.WRONG -> GameResult.LOSS
        else -> GameResult.NONE
    }

    LaunchedEffect(gameResult) {
        if (gameResult != GameResult.NONE) {
            playerViewModel.recordGameResult(GameType.PATTERN, gameResult)
        }
    }

    GameResultOverlay(result = gameResult) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0A0A0F)),
            contentAlignment = Alignment.Center
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            val badgeColor = when (level) {
                PatternDifficulty.EASY -> Color(0xFF22C55E)
                PatternDifficulty.MEDIUM -> Color(0xFFEAB308)
                PatternDifficulty.HARD -> Color(0xFFEF4444)
            }
            
            Box(
                modifier = Modifier
                    .background(badgeColor.copy(alpha = 0.2f), RoundedCornerShape(16.dp))
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Text(
                    text = level.name,
                    color = badgeColor,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp)
            ) {
                items(grid.size) { index ->
                    val isMissing = (index == missingIndex)
                    
                    val displaySymbol = if (isMissing && state == PatternGameState.PLAYING) "?" 
                                        else if (isMissing && state != PatternGameState.PLAYING) answer 
                                        else grid[index]
                    
                    val bgColor = when {
                        isMissing && state == PatternGameState.PLAYING -> Color(0xFF13131A)
                        isMissing && state == PatternGameState.CORRECT -> Color(0xFF22C55E).copy(alpha = 0.5f)
                        isMissing && state == PatternGameState.WRONG -> Color(0xFF8B5CF6).copy(alpha = 0.5f)
                        else -> Color(0xFF13131A)
                    }
                    
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .background(bgColor, RoundedCornerShape(8.dp))
                            .border(
                                2.dp, 
                                if (isMissing) Color.White.copy(alpha = 0.2f) else Color.Transparent, 
                                RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = displaySymbol, fontSize = 32.sp)
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(48.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                options.forEach { opt ->
                    val isSelected = (opt == selectedOption)
                    val isCorrect = (opt == answer)
                    
                    val btnColor = when {
                        state == PatternGameState.PLAYING -> Color(0xFF13131A)
                        isSelected && isCorrect -> Color(0xFF22C55E).copy(alpha = 0.5f)
                        isSelected && !isCorrect -> Color(0xFFEF4444).copy(alpha = 0.5f)
                        !isSelected && isCorrect && state == PatternGameState.WRONG -> Color(0xFF8B5CF6).copy(alpha = 0.5f)
                        else -> Color(0xFF13131A)
                    }
                    
                    Button(
                        onClick = {
                            if (!isActive) return@Button
                            if (state == PatternGameState.PLAYING) {
                                selectedOption = opt
                                if (opt == answer) {
                                    state = PatternGameState.CORRECT
                                    HapticFeedbackHelper.onWin(context)
                                } else {
                                    state = PatternGameState.WRONG
                                    HapticFeedbackHelper.onLoss(context)
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = btnColor,
                            disabledContainerColor = btnColor,
                            contentColor = Color.White,
                            disabledContentColor = Color.White
                        ),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f).height(64.dp)
                    ) {
                        Text(text = opt, fontSize = 24.sp)
                    }
                }
            }
            
            if (state != PatternGameState.PLAYING) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = if (state == PatternGameState.CORRECT) "Pattern Master! \u2705" else "Wrong! \u274C",
                    color = if (state == PatternGameState.CORRECT) Color(0xFF22C55E) else Color(0xFFEF4444),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = onRetry, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF59E0B))) {
                    Text("Retry", color = Color.Black, fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = "Swipe Up",
                        tint = Color.White.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Swipe to continue",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}
}

data class PlayerStats(
    val lastResultMap: Map<GameType, GameResult> = emptyMap(),
    val gamesWonByType: Map<GameType, Int> = emptyMap(),
    val gamesLostByType: Map<GameType, Int> = emptyMap(),
    val sessionPlayedTypes: Set<GameType> = emptySet()
)

data class PlayerState(
    val totalXP: Int = 0,
    val currentStreak: Int = 1,
    val rank: String = "Bronze",
    val gamesPlayed: Int = 0,
    val stats: PlayerStats = PlayerStats(),
    val feedGames: List<GameType> = emptyList(),
    val challengeProgress: Map<String, Int> = emptyMap(),
    val activeGotchaIndex: Int = -1
)

object FeedAlgorithm {
    val implementedGames = listOf(
        GameType.REACTION, GameType.CRASH, GameType.MINES, 
        GameType.HILO, GameType.MEMORY, GameType.PRECISION, GameType.PATTERN
    )

    fun generateFeed(playerStats: PlayerStats): List<GameType> {
        val feed = mutableListOf<GameType>()
        
        val lostGames = implementedGames.filter { playerStats.lastResultMap[it] == GameResult.LOSS }
        val wonGames = implementedGames.filter { playerStats.lastResultMap[it] == GameResult.WIN }
        
        for (i in 0 until 50) {
            val unplayed = implementedGames.filter { !playerStats.sessionPlayedTypes.contains(it) && !feed.contains(it) }
            
            if ((i + 1) % 5 == 0 && unplayed.isNotEmpty()) {
                feed.add(unplayed.random())
            } else if (lostGames.isNotEmpty() && (0..3).random() == 0) {
                feed.add(lostGames.random())
            } else {
                var candidate = implementedGames.random()
                if (wonGames.contains(candidate) && (0..2).random() > 0) {
                    val fallback = implementedGames.filter { it != candidate }
                    if (fallback.isNotEmpty()) candidate = fallback.random()
                }
                feed.add(candidate)
            }
        }
        
        // Ensure all 7 types appear at least once in every 15 elements
        for (chunkStart in 0 until 50 step 15) {
            val chunkEnd = minOf(chunkStart + 15, 50)
            val chunk = feed.subList(chunkStart, chunkEnd)
            val missing = implementedGames.filter { !chunk.contains(it) }.toMutableList()
            var i = 0
            while (missing.isNotEmpty() && i < chunk.size) {
                if ((chunkStart + i + 1) % 5 != 0) {
                    chunk[i] = missing.removeAt(0)
                }
                i++
            }
        }
        return feed
    }
}

sealed class GameEvent {
    object Win : GameEvent()
    object LevelUp : GameEvent()
    object Loss : GameEvent()
}

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    private val prefs = application.getSharedPreferences("gotcha_prefs", Context.MODE_PRIVATE)

    private val _gameEvents = kotlinx.coroutines.flow.MutableSharedFlow<GameEvent>(extraBufferCapacity = 10)
    val gameEvents: kotlinx.coroutines.flow.SharedFlow<GameEvent> = _gameEvents.asSharedFlow()

    private val _uiState = MutableStateFlow(PlayerState())
    val uiState: StateFlow<PlayerState> = _uiState.asStateFlow()

    private val _showStreakDialog = MutableStateFlow(false)
    val showStreakDialog: StateFlow<Boolean> = _showStreakDialog.asStateFlow()

    init {
        loadData()
        checkStreak()
    }

    private fun loadData() {
        val totalXP = prefs.getInt("gotcha_totalXP", 0)
        val currentStreak = prefs.getInt("gotcha_currentStreak", 1)
        val gamesPlayed = prefs.getInt("gotcha_gamesPlayed", 0)

        val gamesWonByType = mutableMapOf<GameType, Int>()
        val gamesLostByType = mutableMapOf<GameType, Int>()
        val challengeProgress = mutableMapOf<String, Int>()

        for (type in GameType.entries) {
            val wins = prefs.getInt("gotcha_wins_${type.name}", 0)
            if (wins > 0) gamesWonByType[type] = wins
            
            val losses = prefs.getInt("gotcha_losses_${type.name}", 0)
            if (losses > 0) gamesLostByType[type] = losses
        }

        challengeProgress["reaction_5"] = prefs.getInt("gotcha_chal_reaction_5", 0)
        challengeProgress["crash_3x"] = prefs.getInt("gotcha_chal_crash_3x", 0)
        challengeProgress["mines_10"] = prefs.getInt("gotcha_chal_mines_10", 0)

        val stats = PlayerStats(
            gamesWonByType = gamesWonByType,
            gamesLostByType = gamesLostByType
        )

        val rank = calculateRank(totalXP)
        _uiState.value = PlayerState(
            totalXP = totalXP, 
            currentStreak = currentStreak, 
            rank = rank, 
            gamesPlayed = gamesPlayed, 
            stats = stats, 
            challengeProgress = challengeProgress
        )
    }

    private fun calculateRank(xp: Int): String = when {
        xp >= 20000 -> "GOD TIER"
        xp >= 10000 -> "Legendary"
        xp >= 4000 -> "Elite"
        xp >= 1500 -> "Rising Star"
        xp >= 500 -> "Hustler"
        else -> "Rookie"
    }

    private fun checkStreak() {
        val lastPlayedStr = prefs.getString("gotcha_lastPlayedDate", null)
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE
        val todayStr = today.format(formatter)

        if (lastPlayedStr != null) {
            val lastPlayed = LocalDate.parse(lastPlayedStr, formatter)
            val daysBetween = ChronoUnit.DAYS.between(lastPlayed, today)

            if (daysBetween == 1L) {
                val newStreak = _uiState.value.currentStreak + 1
                val newXP = _uiState.value.totalXP + 100
                val newRank = calculateRank(newXP)
                
                _uiState.update { it.copy(currentStreak = newStreak, totalXP = newXP, rank = newRank) }
                
                prefs.edit()
                    .putString("gotcha_lastPlayedDate", todayStr)
                    .putInt("gotcha_currentStreak", newStreak)
                    .putInt("gotcha_totalXP", newXP)
                    .apply()
                    
                SoundHelper.playStreakSound()
                _showStreakDialog.value = true
            } else if (daysBetween > 1L) {
                _uiState.update { it.copy(currentStreak = 1) }
                prefs.edit()
                    .putString("gotcha_lastPlayedDate", todayStr)
                    .putInt("gotcha_currentStreak", 1)
                    .apply()
            }
        } else {
            prefs.edit()
                .putString("gotcha_lastPlayedDate", todayStr)
                .putInt("gotcha_currentStreak", 1)
                .apply()
            _uiState.update { it.copy(currentStreak = 1) }
        }
    }

    private var pendingGotchaMultiplier: Double = 1.0

    fun setGotchaMultiplier(multiplier: Double) {
        pendingGotchaMultiplier = multiplier
    }

    fun recordGameResult(gameType: GameType, result: GameResult, score: Int = 0, multiplier: Double = 0.0) {
        var didLevelUp = false
        var isWin = false
        var isLoss = false

        _uiState.update { currentState ->
            val stats = currentState.stats
            val newSessionPlayedTypes = stats.sessionPlayedTypes + gameType
            val newLastResultMap = stats.lastResultMap.toMutableMap().apply { put(gameType, result) }
            val newGamesWonByType = stats.gamesWonByType.toMutableMap()
            val newGamesLostByType = stats.gamesLostByType.toMutableMap()
            val newChallengeProgress = currentState.challengeProgress.toMutableMap()
            
            var xpGained = 0

            // Base XP for playing the game
            if (result == GameResult.WIN) {
                isWin = true
                xpGained += 50
                newGamesWonByType[gameType] = (newGamesWonByType[gameType] ?: 0) + 1
                
                // Challenge: Win 5 Reaction Tests
                if (gameType == GameType.REACTION) {
                    val current = newChallengeProgress["reaction_5"] ?: 0
                    if (current < 5) {
                        newChallengeProgress["reaction_5"] = current + 1
                        if (current + 1 == 5) xpGained += 200
                    }
                }
                
                // Challenge: Survive Crash above 3x
                if (gameType == GameType.CRASH && multiplier >= 3.0) {
                    val current = newChallengeProgress["crash_3x"] ?: 0
                    if (current < 3) {
                        newChallengeProgress["crash_3x"] = current + 1
                        if (current + 1 == 3) xpGained += 150
                    }
                }
            } else if (result == GameResult.LOSS) {
                isLoss = true
                xpGained += 10
                newGamesLostByType[gameType] = (newGamesLostByType[gameType] ?: 0) + 1
            }
            
            // Challenge: Clear 10 Mines safely (can happen on win or loss if score > 0)
            if (gameType == GameType.MINES && score > 0) {
                val current = newChallengeProgress["mines_10"] ?: 0
                if (current < 10) {
                    val adding = minOf(10 - current, score)
                    newChallengeProgress["mines_10"] = current + adding
                    if (current + adding == 10) xpGained += 300
                }
            }
            
            // Apply streak multiplier to simulate 'gluttony / greed' mechanic
            xpGained = (xpGained * (1.0 + (currentState.currentStreak * 0.1))).toInt()
            
            // Apply Gotcha XP Multiplier
            xpGained = (xpGained * pendingGotchaMultiplier).toInt()
            pendingGotchaMultiplier = 1.0 // consume it
            
            val newXP = currentState.totalXP + xpGained
            val newRank = calculateRank(newXP)
            val newGamesPlayed = currentState.gamesPlayed + 1
            
            if (newRank != currentState.rank) {
                SoundHelper.playLevelUpSound()
                didLevelUp = true
            }
            
            val editor = prefs.edit()
                .putInt("gotcha_totalXP", newXP)
                .putInt("gotcha_gamesPlayed", newGamesPlayed)
                
            newGamesWonByType.forEach { (type, count) ->
                editor.putInt("gotcha_wins_${type.name}", count)
            }
            newGamesLostByType.forEach { (type, count) ->
                editor.putInt("gotcha_losses_${type.name}", count)
            }
            
            newChallengeProgress.forEach { (key, progress) ->
                editor.putInt("gotcha_chal_$key", progress)
            }
            editor.apply()
            
            currentState.copy(
                totalXP = newXP,
                rank = newRank,
                gamesPlayed = newGamesPlayed,
                challengeProgress = newChallengeProgress,
                stats = PlayerStats(newLastResultMap, newGamesWonByType, newGamesLostByType, newSessionPlayedTypes)
            )
        }
        
        if (didLevelUp) {
            _gameEvents.tryEmit(GameEvent.LevelUp)
        } else if (isWin) {
            _gameEvents.tryEmit(GameEvent.Win)
        } else if (isLoss) {
            _gameEvents.tryEmit(GameEvent.Loss)
        }
    }

    fun extendFeed() {
        _uiState.update { currentState ->
            val moreGames = FeedAlgorithm.generateFeed(currentState.stats)
            currentState.copy(feedGames = currentState.feedGames + moreGames)
        }
    }

    fun injectGotchaGame(gameType: GameType, insertIndex: Int) {
        _uiState.update { currentState ->
            val mutableFeed = currentState.feedGames.toMutableList()
            if (insertIndex in 0..mutableFeed.size) {
                mutableFeed.add(insertIndex, gameType)
            } else {
                mutableFeed.add(gameType)
            }
            currentState.copy(
                feedGames = mutableFeed,
                activeGotchaIndex = if (insertIndex in 0..mutableFeed.size) insertIndex else mutableFeed.size - 1
            )
        }
    }

    fun dismissStreakDialog() {
        _showStreakDialog.value = false
    }

    fun awardXP(amount: Int) {
        _uiState.update { currentState ->
            val newXP = currentState.totalXP + amount
            val newRank = calculateRank(newXP)
            val newState = currentState.copy(
                totalXP = newXP,
                rank = newRank,
                gamesPlayed = currentState.gamesPlayed + 1
            )
            
            prefs.edit()
                .putInt("gotcha_totalXP", newXP)
                .putInt("gotcha_gamesPlayed", newState.gamesPlayed)
                .apply()
                
            newState
        }
    }
}

@Composable
fun XPRewardPopup(xpAmount: Int, visible: Boolean, onAnimationFinished: () -> Unit) {
    if (visible) {
        var startAnimation by remember { mutableStateOf(false) }
        
        LaunchedEffect(visible) {
            startAnimation = true
            kotlinx.coroutines.delay(1500L)
            startAnimation = false
            onAnimationFinished()
        }

        val yOffset by animateFloatAsState(
            targetValue = if (startAnimation) -50f else 50f,
            animationSpec = tween(1500),
            label = "yOffset"
        )
        val alpha by animateFloatAsState(
            targetValue = if (startAnimation) 0f else 1f,
            animationSpec = tween(1500),
            label = "alpha"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 120.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(
                text = "+$xpAmount XP",
                color = Color(0xFFFBBF24).copy(alpha = alpha),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.offset(y = yOffset.dp)
            )
        }
    }
}

@Composable
fun StreakCelebrationDialog(streak: Int, onDismiss: () -> Unit) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        HapticFeedbackHelper.onStreakCelebration(context)
    }

    androidx.compose.ui.window.Dialog(
        onDismissRequest = onDismiss,
        properties = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xCC000000)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "firePulse")
                val scale by infiniteTransition.animateFloat(
                    initialValue = 0.8f,
                    targetValue = 1.2f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(600, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "scale"
                )

                Text(
                    text = "🔥",
                    fontSize = 120.sp,
                    modifier = Modifier.scale(scale)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Day $streak Streak!",
                    color = Color.White,
                    style = MaterialTheme.typography.displayMedium,
                    fontWeight = FontWeight.Black
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "+100 Bonus XP!",
                    color = Color(0xFFFBBF24),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(48.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5CF6)),
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Keep it up!", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
fun LeaderboardScreen(playerViewModel: PlayerViewModel = viewModel()) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Global", "Weekly")
    val playerState by playerViewModel.uiState.collectAsState()

    val fakeLeaderboardBase = listOf(
        LeaderboardPlayer(1, "ArcadeKing", 24500, "Diamond", 512),
        LeaderboardPlayer(2, "PixelQueen", 19800, "Diamond", 430),
        LeaderboardPlayer(3, "RetroGamer_99", 15200, "Platinum", 310),
        LeaderboardPlayer(4, "NeonKnight", 12400, "Platinum", 250),
        LeaderboardPlayer(5, "SynthWaveGirl", 9800, "Gold", 195),
        LeaderboardPlayer(6, "BitBoss", 8600, "Gold", 170),
        LeaderboardPlayer(7, "CyberRunner", 7100, "Gold", 145),
        LeaderboardPlayer(8, "GlitchCoder", 5600, "Silver", 120),
        LeaderboardPlayer(9, "SpeedRunner", 4200, "Silver", 90),
        LeaderboardPlayer(10, "NoobMaster", 3100, "Silver", 60)
    )

    val allGlobalPlayers = (fakeLeaderboardBase + LeaderboardPlayer(0, "Player#1337", playerState.totalXP, playerState.rank, playerState.gamesPlayed))
        .sortedByDescending { it.xp }
        .mapIndexed { index, player -> player.copy(position = index + 1) }

    // Randomize weekly scores differently so leaderboard changes order
    val fakeLeaderboardWeekly = listOf(
        LeaderboardPlayer(4, "NeonKnight", 4500, "Gold", 45),
        LeaderboardPlayer(7, "CyberRunner", 3800, "Silver", 38),
        LeaderboardPlayer(2, "PixelQueen", 3100, "Silver", 25),
        LeaderboardPlayer(1, "ArcadeKing", 2800, "Bronze", 20),
        LeaderboardPlayer(9, "SpeedRunner", 2100, "Bronze", 15),
        LeaderboardPlayer(5, "SynthWaveGirl", 1400, "Bronze", 10),
        LeaderboardPlayer(10, "NoobMaster", 900, "Bronze", 8),
        LeaderboardPlayer(3, "RetroGamer_99", 500, "Bronze", 3)
    )
    
    val allWeeklyPlayers = (fakeLeaderboardWeekly + LeaderboardPlayer(0, "Player#1337", (playerState.totalXP * 0.3).toInt(), playerState.rank, (playerState.gamesPlayed * 0.3).toInt()))
        .sortedByDescending { it.xp }
        .mapIndexed { index, player -> player.copy(position = index + 1) }

    val currentPlayers = if (selectedTabIndex == 0) allGlobalPlayers else allWeeklyPlayers
    val myRank = currentPlayers.find { it.name == "Player#1337" }?.position ?: 0
    val top10 = currentPlayers.take(10)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0A0A0F))
    ) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = Color.Transparent,
            contentColor = Color.White,
            indicator = { tabPositions ->
                if (selectedTabIndex < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color(0xFF8B5CF6),
                        height = 3.dp
                    )
                }
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { 
                        Text(
                            text = title, 
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTabIndex == index) Color(0xFF8B5CF6) else Color.White.copy(alpha = 0.5f)
                        ) 
                    }
                )
            }
        }

        val top3 = top10.take(3)
        val rest = top10.drop(3)

        // Podium top 3
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            PodiumPlayer(player = top3[1], modifier = Modifier.weight(1f), height = 120.dp, color = Color(0xFFC0C0C0)) // Silver
            PodiumPlayer(player = top3[0], modifier = Modifier.weight(1.2f), height = 160.dp, color = Color(0xFFFFD700)) // Gold
            PodiumPlayer(player = top3[2], modifier = Modifier.weight(1f), height = 100.dp, color = Color(0xFFCD7F32)) // Bronze
        }

        // Lazy column for ranks 4-10
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(rest.size) { index ->
                LeaderboardRowItem(player = rest[index])
            }
        }

        // Pinned Bottom Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(12.dp),
            colors = androidx.compose.material3.CardDefaults.cardColors(containerColor = Color(0xFF13131A))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(72.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .fillMaxHeight()
                        .background(Color(0xFF8B5CF6))
                )
                Spacer(modifier = Modifier.width(16.dp))
                
                Text(
                    text = myRank.toString(),
                    color = Color.White.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.width(32.dp)
                )
                
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(listOf(Color(0xFF8B5CF6), Color(0xFF38BDF8)))),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Avatar",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your Rank",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1
                    )
                    Text(
                        text = "Player#1337",
                        color = Color.White.copy(alpha = 0.5f),
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1
                    )
                }
                
                val xpFormatted = java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(playerState.totalXP)
                Text(
                    text = "$xpFormatted XP",
                    color = Color(0xFF8B5CF6),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.ExtraBold
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
        }
    }
}

@Composable
fun PodiumPlayer(player: LeaderboardPlayer, modifier: Modifier = Modifier, height: androidx.compose.ui.unit.Dp, color: Color) {
    Column(
        modifier = modifier.padding(horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = "👑",
            fontSize = 28.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(Color(0xFF13131A))
                .border(2.dp, color, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = "Avatar",
                tint = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .background(
                    color = Color(0xFF13131A),
                    shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp, start = 4.dp, end = 4.dp)
            ) {
                Text(
                    text = player.position.toString(),
                    color = color,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = player.name,
                    color = Color.White,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(player.xp)} XP",
                    color = Color.White.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun LeaderboardRowItem(player: LeaderboardPlayer) {
    val rankColor = when(player.badge.lowercase(java.util.Locale.US)) {
        "bronze" -> Color(0xFFCD7F32)
        "silver" -> Color(0xFFC0C0C0)
        "gold" -> Color(0xFFF59E0B)
        "platinum" -> Color(0xFF38BDF8)
        "diamond" -> Color(0xFFA78BFA)
        else -> Color.White
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = player.position.toString(),
            color = Color.White.copy(alpha = 0.5f),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.width(32.dp)
        )
        
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color(0xFF1A1A24)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Person,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.5f),
                modifier = Modifier.size(24.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = player.name,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
            Text(
                text = player.badge,
                color = rankColor,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold
            )
        }
        
        Column(horizontalAlignment = Alignment.End) {
            Text(
                text = "${java.text.NumberFormat.getNumberInstance(java.util.Locale.US).format(player.xp)} XP",
                color = Color.White,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = "${player.gamesPlayed} games",
                color = Color.White.copy(alpha = 0.4f),
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
    HorizontalDivider(color = Color(0xFF1A1A24), thickness = 0.5.dp)
}

data class LeaderboardPlayer(
    val position: Int,
    val name: String,
    val xp: Int,
    val badge: String,
    val gamesPlayed: Int
)

object HapticFeedbackHelper {
    private fun getVibrator(context: android.content.Context): Vibrator {
        val attributionContext = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.createAttributionContext("gotcha_vibrations")
        } else {
            context
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager = attributionContext.getSystemService(android.content.Context.VIBRATOR_MANAGER_SERVICE) as android.os.VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            attributionContext.getSystemService(android.content.Context.VIBRATOR_SERVICE) as Vibrator
        }
    }

    fun onWin(context: android.content.Context) {
        SoundHelper.playWinSound()
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 100, 50, 150)
            val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 100, 50, 150), -1)
        }
    }

    fun onLoss(context: android.content.Context) {
        SoundHelper.playLossSound()
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(80, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(80)
        }
    }

    fun onTap(context: android.content.Context) {
        SoundHelper.playTapSound()
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(30)
        }
    }

    fun onSwipe(context: android.content.Context) {
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(20, 50))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(20)
        }
    }

    fun onStreakCelebration(context: android.content.Context) {
        val vibrator = getVibrator(context)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val timings = longArrayOf(0, 50, 50, 50, 50, 50, 50, 100)
            val amplitudes = intArrayOf(0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE, 0, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(longArrayOf(0, 50, 50, 50, 50, 50, 50, 100), -1)
        }
    }
}

object SoundHelper {
    private val scope = kotlinx.coroutines.CoroutineScope(kotlinx.coroutines.Dispatchers.Default)
    private val sampleRate = 44100

    private fun playSynthTone(freqHz: Double, durationMs: Int) {
        scope.launch {
            try {
                val numSamples = (durationMs * sampleRate / 1000.0).toInt()
                val generatedSnd = ByteArray(2 * numSamples)
                for (i in 0 until numSamples) {
                    val angle = 2.0 * Math.PI * i.toDouble() / (sampleRate / freqHz)
                    val soundVal = (sin(angle) * 32767).toInt().toShort()
                    // 16 bit wav PCM, converted to byte array
                    generatedSnd[2 * i] = (soundVal.toInt() and 0x00FF).toByte()
                    generatedSnd[2 * i + 1] = ((soundVal.toInt() and 0xFF00) shr 8).toByte()
                }

                val audioTrack = AudioTrack(
                    AudioManager.STREAM_MUSIC,
                    sampleRate,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    generatedSnd.size,
                    AudioTrack.MODE_STATIC
                )
                audioTrack.write(generatedSnd, 0, generatedSnd.size)
                audioTrack.play()
                
                // Release after playing
                kotlinx.coroutines.delay(durationMs.toLong() + 50)
                audioTrack.release()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun playWinSound() {
        // Stimulating chime
        playSynthTone(880.0, 100) // A5
        scope.launch {
            kotlinx.coroutines.delay(120)
            playSynthTone(1108.7, 200) // C#6
        }
    }

    fun playLossSound() {
        // Low error tone
        playSynthTone(220.0, 300) // A3
    }

    fun playTapSound() {
        // High, short click
        playSynthTone(1760.0, 30) // A6
    }

    fun playCashOutSound() {
        // Fast, high ping sequence
        playSynthTone(1318.5, 80) // E6
        scope.launch {
            kotlinx.coroutines.delay(100)
            playSynthTone(1760.0, 150) // A6
        }
    }
    
    fun playLevelUpSound() {
        // Ascending major chord
        playSynthTone(523.25, 80) // C5
        scope.launch {
            kotlinx.coroutines.delay(100)
            playSynthTone(659.25, 80) // E5
            kotlinx.coroutines.delay(100)
            playSynthTone(783.99, 80) // G5
            kotlinx.coroutines.delay(100)
            playSynthTone(1046.50, 200) // C6
        }
    }
    
    fun playStreakSound() {
        // Short engaging blip
        playSynthTone(987.77, 80) // B5
        scope.launch {
            kotlinx.coroutines.delay(100)
            playSynthTone(1318.51, 150) // E6
        }
    }
}

@Composable
fun SocialProofOverlay(modifier: Modifier = Modifier) {
    val likes = remember { (1200..10_500).random() }
    val shares = remember { (50..300).random() }
    var liked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(bottom = 120.dp, end = 16.dp), // Avoid bottom bar
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Favorite, 
                contentDescription = "Like", 
                tint = if (liked) Color(0xFFEF4444) else Color.White, 
                modifier = Modifier
                    .size(32.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { liked = !liked }
            )
            Spacer(modifier = Modifier.height(4.dp))
            val displayLikes = if (liked) likes + 1 else likes
            Text("${displayLikes / 1000}.${(displayLikes % 1000) / 100}k", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Share, 
                contentDescription = "Share", 
                tint = Color.White, 
                modifier = Modifier
                    .size(32.dp)
                    .clickable { }
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(shares.toString(), color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun PrideDisplacementBanner(isDethroned: Boolean, rivalName: String) {
    AnimatedVisibility(
        visible = isDethroned,
        enter = slideInVertically() + fadeIn(),
        exit = slideOutVertically() + fadeOut()
    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFF3A0007)),
            modifier = Modifier.fillMaxWidth().padding(16.dp).border(1.dp, Color.Red)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Warning, contentDescription = "Loss of Rank", tint = Color.Red)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text("STATUS DOWNGRADED", color = Color.Red, style = MaterialTheme.typography.labelLarge)
                    Text(
                        text = "$rivalName just stripped your elite title. Reclaim your position immediately.",
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

fun Modifier.glowOnPress(isPressed: Boolean, glowColor: Color): Modifier = this.composed {
    val animatedGlow by animateDpAsState(targetValue = if (isPressed) 24.dp else 0.dp)
    
    this.drawBehind {
        if (animatedGlow > 0.dp) {
            // Draws an expanded neon blur behind the layout boundaries to visually reward touch
            drawCircle(
                color = glowColor.copy(alpha = 0.3f),
                radius = size.maxDimension / 2 + animatedGlow.toPx()
            )
        }
    }
}