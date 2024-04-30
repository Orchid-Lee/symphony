package io.github.zyrouge.symphony.ui.view


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.zyrouge.symphony.App
import io.github.zyrouge.symphony.services.login.Login
import io.github.zyrouge.symphony.ui.helpers.ViewContext

@Composable
fun Fuck(context: ViewContext) {
    JsutKiding(context)
}

@Composable
fun JsutKiding(context: ViewContext) {

    Scaffold(content = { contentPadding ->
        EditTextCom(Modifier.padding(contentPadding), context)
    })

//    val subsonicClientInstance = App.getSubsonicClientInstance(false)
//    Log.d("Lee2", "JsutKiding: $subsonicClientInstance")


}

@SuppressLint("CommitPrefEdits")
@Composable
fun EditTextCom(modifier: Modifier, context: ViewContext) {
    var url by remember {
        mutableStateOf("")
    }
    var user by remember {
        mutableStateOf("")
    }
    var pswd by remember {
        mutableStateOf("")
    }

    Column(modifier = Modifier.padding(50.dp)) {
        TextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("请输入地址:") }
        )

        TextField(
            value = user,
            onValueChange = { user = it },
            label = { Text("请输入账号:") }
        )

        TextField(
            value = pswd,
            onValueChange = { pswd = it },
            label = { Text("请输入密码:") }
        )
    }

    if (url.isNotBlank() && user.isNotBlank() && pswd.isNotBlank()) {
        Log.d("Lee3", "EditTextCom: url:$url, user:$user, pswd:$pswd ")
        App.getInstance().preferences.edit().putString("server", "http://192.168.31.3:43962/").apply()
        App.getInstance().preferences.edit().putString("user", "codefish").apply()
        App.getInstance().preferences.edit().putString("password", "10bsyr1000lblx@Zxj").apply()

        val login = Login(context)
        login.login()



//        initSyncStarredView()
//        initDiscoverSongSlideView()
//        initSimilarSongView()
//        initArtistRadio()
//        initArtistBestOf()
//        initStarredTracksView()
//        initStarredAlbumsView()
//        initStarredArtistsView()
//        initMostPlayedAlbumView()
//        initRecentPlayedAlbumView()
//        initNewReleasesView()
//        initYearSongView()
//        initRecentAddedAlbumView()
//        initGridView()
//        initSharesView()
//        initHomeReorganizer()
    }

}
