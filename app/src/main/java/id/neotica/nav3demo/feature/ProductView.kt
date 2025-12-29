package id.neotica.nav3demo.feature

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ProductView(id: String) {
    Scaffold {
        LazyColumn(contentPadding = it) {
            item {
                Text("Product $id")
            }
        }

    }
}