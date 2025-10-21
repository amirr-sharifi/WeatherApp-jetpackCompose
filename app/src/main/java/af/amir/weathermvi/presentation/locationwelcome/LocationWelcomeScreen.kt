package af.amir.weathermvi.presentation.locationwelcome

import af.amir.weathermvi.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LocationWelcomeScreen(modifier: Modifier = Modifier,onNavigateToCityFinder:()->Unit) {


    val composition =
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.location_animation))
    val progress = animateLottieCompositionAsState(
        composition = composition.value,
        iterations = LottieConstants.IterateForever
    )

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { paddingValues ->

        Box(Modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier
                    .fillMaxWidth().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LottieAnimation(
                    composition = composition.value,
                    progress = progress.value,
                    Modifier.fillMaxWidth(0.7f)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.location_welcome_title),
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                Button(onClick = onNavigateToCityFinder,shape = RoundedCornerShape(12.dp)) {
                    Text(stringResource(R.string.let_s_start))
                }
            }
        }
    }
}