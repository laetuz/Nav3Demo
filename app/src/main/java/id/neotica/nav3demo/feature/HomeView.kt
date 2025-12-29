package id.neotica.nav3demo.feature

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import id.neotica.nav3demo.Product

@Composable
fun HomeView(backstack: SnapshotStateList<Any>, modifier: Modifier = Modifier) {
    Scaffold() {
        LazyColumn(contentPadding = it) {
            item {
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
    }
}