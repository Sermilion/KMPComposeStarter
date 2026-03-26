package news.readian.notoesapp.feature.onboarding.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PagerDotsIndicator(
  totalDots: Int,
  selectedIndex: Int,
  selectedColor: Color,
  unselectedColor: Color,
  modifier: Modifier = Modifier,
) {
  LazyRow(
    modifier = modifier.then(
      Modifier
        .wrapContentWidth()
        .wrapContentHeight(),
    ),
  ) {
    items(totalDots) { index ->
      Box(
        modifier = Modifier
          .size(6.dp)
          .clip(CircleShape)
          .background(
            if (index == selectedIndex) {
              selectedColor
            } else {
              unselectedColor
            },
          ),
      )
      if (index != totalDots - 1) {
        Spacer(modifier = Modifier.padding(horizontal = 6.dp))
      }
    }
  }
}
