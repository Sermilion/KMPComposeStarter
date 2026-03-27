package news.readian.notoesapp.feature.onboarding.tutorial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.icon.ReadianIcons
import news.readian.notoesapp.core.designsystem.icon.icons.ImageGlasses
import news.readian.notoesapp.core.designsystem.icon.icons.ImageLamp
import news.readian.notoesapp.core.designsystem.icon.icons.ImageLookingGlass
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianLowerCased
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.feature.onboarding.common.ui.RemoteValidationErrorContent
import news.readian.notoesapp.feature.onboarding.composables.PagerDotsIndicator
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialContract
import notesapp.feature.onboarding.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun MobileTutorialScreen(
  uiState: TutorialContract.UiState,
  onNextClick: () -> Unit,
  onLoginOrRegisterClick: () -> Unit,
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.statusBars,
  ) {
    Box(modifier = Modifier.padding(it)) {
      MobileTutorialContent(
        onLoginOrRegisterClick = onLoginOrRegisterClick,
        onNextClick = onNextClick,
        uiState = uiState,
      )
      if (uiState.loading) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
    }
  }
}

@Composable
private fun MobileTutorialContent(
  uiState: TutorialContract.UiState,
  onLoginOrRegisterClick: () -> Unit,
  onNextClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .then(modifier),
  ) {
    val pageCount = TutorialPageType.entries.count()
    val pagerState = rememberPagerState(pageCount = { pageCount })
    val scope = rememberCoroutineScope()

    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(vertical = 16.dp),
    ) {
      TopContent(onLoginClick = onLoginOrRegisterClick)
      PagerContent(
        pagerState = pagerState,
        uiState = uiState,
      )
    }

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .align(Alignment.BottomCenter)
        .padding(bottom = 24.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      PagerDotsIndicator(
        totalDots = pageCount,
        selectedIndex = pagerState.currentPage,
        selectedColor = MaterialTheme.colorScheme.primaryContainer,
        unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
      )

      ReadianButton(
        text = stringResource(Res.string.label_next),
        style = ButtonStyle.Secondary,
        modifier = Modifier.width(100.dp),
        onClick = {
          if (pagerState.currentPage == pageCount - 1) {
            onNextClick()
          } else {
            scope.launch {
              pagerState.animateScrollToPage(pagerState.currentPage + 1)
            }
          }
        },
      )
    }
  }
}

@Composable
private fun PagerContent(pagerState: PagerState, uiState: TutorialContract.UiState) {
  HorizontalPager(state = pagerState) { page ->
    when (TutorialPageType.entries[page]) {
      TutorialPageType.Spotlight -> TutorialPage(
        image = ReadianIcons.ImageLamp,
        title = stringResource(Res.string.label_spotlight),
        description = stringResource(Res.string.description_spotlight),
        uiState = uiState,
      )

      TutorialPageType.Library -> TutorialPage(
        image = ReadianIcons.ImageGlasses,
        title = stringResource(Res.string.label_library),
        description = stringResource(Res.string.description_library),
        uiState = uiState,
      )

      TutorialPageType.Discover -> TutorialPage(
        image = ReadianIcons.ImageLookingGlass,
        title = stringResource(Res.string.label_discover),
        description = stringResource(Res.string.description_discover),
        uiState = uiState,
      )
    }
  }
}

@Composable
private fun TopContent(onLoginClick: () -> Unit) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = 16.dp),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = ReadianIcons.ReadianLowerCased,
      contentDescription = stringResource(Res.string.readian_icon),
    )

    ReadianButton(
      text = stringResource(Res.string.label_login_or_register),
      style = ButtonStyle.Text,
      onClick = onLoginClick,
    )
  }
}

@Composable
private fun TutorialPage(
  image: ImageVector,
  title: String,
  description: String,
  uiState: TutorialContract.UiState,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
  ) {
    Image(
      imageVector = image,
      contentDescription = stringResource(Res.string.tutorial_image),
      modifier = Modifier
        .height(200.dp)
        .padding(top = 42.dp),
    )

    Text(
      text = title,
      style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
      modifier = Modifier.padding(top = 46.dp),
    )

    Text(
      text = description,
      style = MaterialTheme.typography.titleLarge,
      modifier = Modifier.padding(top = 22.dp),
      textAlign = TextAlign.Start,
    )

    ErrorContainer(modifier = Modifier.height(108.dp)) {
      if (uiState.errors.isNotEmpty()) {
        RemoteValidationErrorContent(uiState.errors.toImmutableList())
      }
    }
  }
}
