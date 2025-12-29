package id.neotica.nav3demo

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import id.neotica.nav3demo.feature.HomeView
import id.neotica.nav3demo.feature.ProductView

data object Home
data class Product(val id: String)

@Composable
fun MainView(modifier: Modifier = Modifier) {
    Nav3EntrySample()
}

@Composable
fun BasicNav3Sample() {
    val backstack = remember { mutableStateListOf<Any>(Home) }

    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Home -> NavEntry(key) {
                    Column {
                        Text("Home")
                        for (i in 1..10) {
                            Button(
                                {
                                    backstack.add(Product("$i"))
                                }
                            ) { Text("Click $i") }
                        }
                    }
                }
                is Product -> NavEntry(key) {
                    Text("Product ${key.id}")
                }
                else -> NavEntry(Unit) { Text("") }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Nav3EntrySample() {
    val backstack = remember { mutableStateListOf<Any>(Home) }

    val strategy = rememberListDetailSceneStrategy<Any>()

    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        sceneStrategy = strategy, // optional, only if you are using scene strategy
        entryProvider = entryProvider {
            homeEntry(backstack)
            productEntry()
        }
    )
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun EntryProviderScope<Any>.homeEntry(backstack: SnapshotStateList<Any>) {
    entry<Home>(
        metadata = ListDetailSceneStrategy.listPane()
    ) {
        HomeView(backstack)
    }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
private fun EntryProviderScope<Any>.productEntry() {
    entry<Product>(
        metadata = ListDetailSceneStrategy.detailPane() + NavDisplay.transitionSpec {
            // slide new content up, keeping the old content in place underneath
            slideInHorizontally { it }/*(
                initialOffset = { IntOffset(0, it.width) },
                animationSpec = tween(durationMillis = 300)
            )*/ togetherWith ExitTransition.KeepUntilTransitionsFinished
        } + NavDisplay.popTransitionSpec {
            EnterTransition.None togetherWith
                    // slide old content down, keeping the new content in place underneath
                    slideOutHorizontally { it }
//                    slideOut(
//                        targetOffset = { IntOffset(0, it.width) },
//                        animationSpec = tween(durationMillis = 300)
//                    )
        } + NavDisplay.predictivePopTransitionSpec {
            EnterTransition.None togetherWith slideOutHorizontally { it }
                    // slide old content down, keeping the new content in place underneath
//                    slideOut(
//                        targetOffset = { IntOffset(0, it.width) },
//                        animationSpec = tween(durationMillis = 300)
//                    )
        }
    ) {
        ProductView(it.id)
    }
}