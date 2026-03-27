package news.readian.notoesapp.feature.onboarding.tutorial.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.toImmutableList
import news.readian.notoesapp.core.designsystem.component.ButtonStyle
import news.readian.notoesapp.core.designsystem.component.ReadianButton
import news.readian.notoesapp.core.designsystem.icon.ReadianIcons
import news.readian.notoesapp.core.designsystem.icon.icons.ImageGlasses
import news.readian.notoesapp.core.designsystem.icon.icons.ImageLamp
import news.readian.notoesapp.core.designsystem.icon.icons.ImageLookingGlass
import news.readian.notoesapp.core.designsystem.icon.icons.ReadianLowerCased
import news.readian.notoesapp.core.ui.composables.ErrorContainer
import news.readian.notoesapp.feature.onboarding.common.ui.RemoteValidationErrorContent
import news.readian.notoesapp.feature.onboarding.tutorial.TutorialContract
import notesapp.feature.onboarding.generated.resources.*
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun WebTutorialScreen(
  uiState: TutorialContract.UiState,
  onNextClick: () -> Unit,
  onLoginOrRegisterClick: () -> Unit,
) {
  var currentPage by remember { mutableStateOf(0) }
  val pageCount = TutorialPageType.entries.size

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    contentWindowInsets = WindowInsets.statusBars,
  ) { paddingValues ->
    Box(modifier = Modifier.padding(paddingValues)) {
      WebTutorialScaffoldContent(
        currentPage = currentPage,
        pageCount = pageCount,
        uiState = uiState,
        onLoginOrRegisterClick = onLoginOrRegisterClick,
        onPreviousClick = { if (currentPage > 0) currentPage-- },
        onNextClick = {
          if (currentPage < pageCount - 1) {
            currentPage++
          } else {
            onNextClick()
          }
        },
      )

      if (uiState.loading) {
        CircularProgressIndicator(
          modifier = Modifier
            .align(Alignment.Center)
            .size(64.dp),
        )
      }
    }
  }
}

@Composable
private fun WebTutorialScaffoldContent(
  currentPage: Int,
  pageCount: Int,
  uiState: TutorialContract.UiState,
  onLoginOrRegisterClick: () -> Unit,
  onPreviousClick: () -> Unit,
  onNextClick: () -> Unit,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(32.dp),
    verticalArrangement = Arrangement.SpaceBetween,
  ) {
    WebTopBar(onLoginClick = onLoginOrRegisterClick)

    LinearProgressIndicator(
      progress = { (currentPage + 1).toFloat() / pageCount },
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 24.dp)
        .height(4.dp),
    )

    Row(
      modifier = Modifier
        .fillMaxWidth()
        .weight(1f),
      horizontalArrangement = Arrangement.spacedBy(48.dp),
    ) {
      WebTutorialContent(
        currentPage = currentPage,
        uiState = uiState,
        modifier = Modifier.weight(1f),
      )

      WebTutorialImage(
        currentPage = currentPage,
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight(),
      )
    }

    WebNavigationButtons(
      currentPage = currentPage,
      pageCount = pageCount,
      onPreviousClick = onPreviousClick,
      onNextClick = onNextClick,
      modifier = Modifier.padding(top = 32.dp),
    )
  }
}

@Composable
private fun WebTopBar(onLoginClick: () -> Unit) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = ReadianIcons.ReadianLowerCased,
      contentDescription = stringResource(Res.string.readina_icon),
      modifier = Modifier.size(48.dp),
    )

    ReadianButton(
      text = stringResource(Res.string.label_login_or_register),
      style = ButtonStyle.Primary,
      onClick = onLoginClick,
      modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
    )
  }
}

@Composable
private fun WebTutorialContent(
  currentPage: Int,
  uiState: TutorialContract.UiState,
  modifier: Modifier = Modifier,
) {
  val pageType = TutorialPageType.entries[currentPage]

  val (title, description) = when (pageType) {
    TutorialPageType.Spotlight -> Pair(
      stringResource(Res.string.label_spotlight),
      stringResource(Res.string.description_spotlight),
    )
    TutorialPageType.Library -> Pair(
      stringResource(Res.string.label_library),
      stringResource(Res.string.description_library),
    )
    TutorialPageType.Discover -> Pair(
      stringResource(Res.string.label_discover),
      stringResource(Res.string.description_discover),
    )
  }

  Column(
    modifier = modifier.fillMaxHeight(),
    verticalArrangement = Arrangement.Center,
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.displayLarge.copy(
        fontWeight = FontWeight.Bold,
        fontSize = MaterialTheme.typography.displayLarge.fontSize * 1.2f,
      ),
      modifier = Modifier.padding(bottom = 32.dp),
    )

    Text(
      text = description,
      style = MaterialTheme.typography.headlineMedium,
      modifier = Modifier.padding(bottom = 24.dp),
      textAlign = TextAlign.Start,
      lineHeight = MaterialTheme.typography.headlineMedium.lineHeight * 1.5f,
    )

    ErrorContainer(modifier = Modifier.height(120.dp)) {
      if (uiState.errors.isNotEmpty()) {
        RemoteValidationErrorContent(uiState.errors.toImmutableList())
      }
    }
  }
}

@Composable
private fun WebTutorialImage(currentPage: Int, modifier: Modifier = Modifier) {
  val pageType = TutorialPageType.entries[currentPage]

  val image = when (pageType) {
    TutorialPageType.Spotlight -> ReadianIcons.ImageLamp
    TutorialPageType.Library -> ReadianIcons.ImageGlasses
    TutorialPageType.Discover -> ReadianIcons.ImageLookingGlass
  }

  Box(
    modifier = modifier,
    contentAlignment = Alignment.Center,
  ) {
    Image(
      imageVector = image,
      contentDescription = stringResource(Res.string.tutorial_image),
      modifier = Modifier.size(400.dp),
    )
  }
}

@Composable
private fun WebNavigationButtons(
  currentPage: Int,
  pageCount: Int,
  onPreviousClick: () -> Unit,
  onNextClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    if (currentPage > 0) {
      ReadianButton(
        text = stringResource(Res.string.label_previous),
        style = ButtonStyle.Text,
        onClick = onPreviousClick,
        modifier = Modifier
          .width(160.dp)
          .height(56.dp),
      )
    } else {
      Spacer(modifier = Modifier.width(160.dp))
    }

    Text(
      text = "${currentPage + 1} / $pageCount",
      style = MaterialTheme.typography.titleMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )

    ReadianButton(
      text = if (currentPage == pageCount - 1) {
        stringResource(Res.string.label_get_started)
      } else {
        stringResource(Res.string.label_next)
      },
      style = ButtonStyle.Primary,
      onClick = onNextClick,
      modifier = Modifier
        .width(160.dp)
        .height(56.dp),
    )
  }
}
