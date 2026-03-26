package news.readian.notoesapp.core.designsystem.icon.icons

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.group
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import news.readian.notoesapp.core.designsystem.icon.ReadianIcons

val ReadianIcons.ImageLookingGlass: ImageVector
  @Composable get() {
    if (imageLookingGlass != null) {
      return imageLookingGlass!!
    }
    val mainColor = MaterialTheme.colorScheme.primaryContainer
    imageLookingGlass = Builder(
      name = "ImageLookingGlass",
      defaultWidth = 107.0.dp,
      defaultHeight = 140.0.dp,
      viewportWidth = 107.0f,
      viewportHeight = 140.0f,
    ).apply {
      group {
        path(
          fill = SolidColor(mainColor),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(77.0f, 135.392f)
          lineTo(65.788f, 139.087f)
          lineTo(47.28f, 76.292f)
          lineTo(54.693f, 73.849f)
          lineTo(77.0f, 135.392f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(77.07f, 89.999f)
          curveTo(68.758f, 96.029f, 56.188f, 97.153f, 55.598f, 89.702f)
          curveTo(54.866f, 80.452f, 94.272f, 61.497f, 90.223f, 107.955f)
          lineTo(105.55f, 124.728f)
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(39.0f, 77.0f)
          curveTo(59.987f, 77.0f, 77.0f, 59.987f, 77.0f, 39.0f)
          curveTo(77.0f, 18.013f, 59.987f, 1.0f, 39.0f, 1.0f)
          curveTo(18.013f, 1.0f, 1.0f, 18.013f, 1.0f, 39.0f)
          curveTo(1.0f, 59.987f, 18.013f, 77.0f, 39.0f, 77.0f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(47.761f, 111.001f)
          curveTo(54.629f, 109.331f, 59.61f, 105.562f, 58.886f, 102.584f)
          curveTo(58.162f, 99.605f, 52.007f, 98.545f, 45.138f, 100.215f)
          curveTo(38.269f, 101.886f, 33.288f, 105.654f, 34.013f, 108.633f)
          curveTo(34.737f, 111.611f, 40.892f, 112.672f, 47.761f, 111.001f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(44.536f, 100.092f)
          curveTo(51.603f, 99.49f, 57.121f, 96.535f, 56.862f, 93.491f)
          curveTo(56.602f, 90.447f, 50.664f, 88.468f, 43.597f, 89.07f)
          curveTo(36.531f, 89.672f, 31.013f, 92.628f, 31.272f, 95.672f)
          curveTo(31.531f, 98.715f, 37.47f, 100.694f, 44.536f, 100.092f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(53.611f, 121.003f)
          curveTo(59.79f, 118.418f, 63.834f, 114.016f, 62.644f, 111.172f)
          curveTo(61.454f, 108.328f, 55.48f, 108.119f, 49.301f, 110.704f)
          curveTo(43.122f, 113.29f, 39.078f, 117.691f, 40.268f, 120.535f)
          curveTo(41.458f, 123.379f, 47.432f, 123.589f, 53.611f, 121.003f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(mainColor),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(59.889f, 129.349f)
          curveTo(64.718f, 127.102f, 67.67f, 123.211f, 66.482f, 120.658f)
          curveTo(65.294f, 118.104f, 60.417f, 117.855f, 55.587f, 120.102f)
          curveTo(50.758f, 122.349f, 47.806f, 126.24f, 48.994f, 128.793f)
          curveTo(50.182f, 131.347f, 55.06f, 131.595f, 59.889f, 129.349f)
          close()
        }
      }
    }
      .build()
    return imageLookingGlass!!
  }

private var imageLookingGlass: ImageVector? = null
