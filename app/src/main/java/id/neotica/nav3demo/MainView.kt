package id.neotica.nav3demo

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay

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
                    Column() {
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

@Composable
fun Nav3EntrySample() {
    val backstack = remember { mutableStateListOf<Any>(Home) }

    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Home> {
                Column() {
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
            entry<Product> {
                Text("Product ${it.id}")
            }
        }
    )
}