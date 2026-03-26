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

val ReadianIcons.ReadianWelcome: ImageVector
  @Composable get() {
    if (readianWelcome != null) {
      return readianWelcome!!
    }
    readianWelcome = Builder(
      name = "Group 16",
      defaultWidth = 337.0.dp,
      defaultHeight = 178.0.dp,
      viewportWidth = 337.0f,
      viewportHeight = 178.0f,
    ).apply {
      group {
        path(
          fill = SolidColor(Color(0xFF000000)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(0.509f, 51.398f)
          curveTo(1.186f, 51.398f, 1.187f, 50.341f, 0.509f, 50.341f)
          curveTo(-0.169f, 50.341f, -0.17f, 51.398f, 0.509f, 51.398f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(212.105f, 28.867f)
          curveTo(211.916f, 28.116f, 212.369f, 27.354f, 213.116f, 27.165f)
          lineTo(235.696f, 21.444f)
          curveTo(236.443f, 21.254f, 237.202f, 21.71f, 237.39f, 22.46f)
          lineTo(243.557f, 47.028f)
          curveTo(243.745f, 47.778f, 243.292f, 48.54f, 242.545f, 48.73f)
          lineTo(219.977f, 54.448f)
          curveTo(219.23f, 54.638f, 218.472f, 54.183f, 218.284f, 53.432f)
          lineTo(212.105f, 28.867f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(212.613f, 28.727f)
          curveTo(212.463f, 28.004f, 213.037f, 27.731f, 213.597f, 27.589f)
          curveTo(214.114f, 27.458f, 214.63f, 27.327f, 215.147f, 27.196f)
          curveTo(217.029f, 26.72f, 218.91f, 26.243f, 220.791f, 25.766f)
          curveTo(225.074f, 24.681f, 229.356f, 23.596f, 233.638f, 22.511f)
          curveTo(234.203f, 22.368f, 234.767f, 22.225f, 235.332f, 22.082f)
          curveTo(235.697f, 21.99f, 236.075f, 21.84f, 236.438f, 22.028f)
          curveTo(236.841f, 22.238f, 236.898f, 22.665f, 236.997f, 23.059f)
          curveTo(237.132f, 23.594f, 237.266f, 24.129f, 237.4f, 24.664f)
          curveTo(238.454f, 28.863f, 239.508f, 33.061f, 240.562f, 37.26f)
          curveTo(241.377f, 40.507f, 242.209f, 43.75f, 243.007f, 47.0f)
          curveTo(243.141f, 47.548f, 242.968f, 48.051f, 242.378f, 48.227f)
          curveTo(242.065f, 48.32f, 241.743f, 48.388f, 241.426f, 48.468f)
          curveTo(239.781f, 48.885f, 238.136f, 49.302f, 236.491f, 49.718f)
          curveTo(232.063f, 50.841f, 227.634f, 51.963f, 223.205f, 53.085f)
          curveTo(222.11f, 53.362f, 221.017f, 53.653f, 219.919f, 53.918f)
          curveTo(218.796f, 54.189f, 218.69f, 52.889f, 218.497f, 52.122f)
          curveTo(217.508f, 48.191f, 216.519f, 44.259f, 215.53f, 40.327f)
          curveTo(214.61f, 36.668f, 213.689f, 33.008f, 212.769f, 29.349f)
          curveTo(212.717f, 29.141f, 212.665f, 28.934f, 212.613f, 28.727f)
          curveTo(212.447f, 28.067f, 211.431f, 28.347f, 211.597f, 29.008f)
          curveTo(212.317f, 31.869f, 213.036f, 34.729f, 213.756f, 37.59f)
          curveTo(214.874f, 42.036f, 215.993f, 46.484f, 217.111f, 50.931f)
          curveTo(217.332f, 51.809f, 217.523f, 52.702f, 217.775f, 53.572f)
          curveTo(218.094f, 54.673f, 219.147f, 55.193f, 220.224f, 54.931f)
          curveTo(220.636f, 54.831f, 221.047f, 54.723f, 221.458f, 54.619f)
          curveTo(225.672f, 53.551f, 229.885f, 52.483f, 234.1f, 51.415f)
          curveTo(236.154f, 50.895f, 238.208f, 50.375f, 240.261f, 49.854f)
          curveTo(240.877f, 49.698f, 241.492f, 49.542f, 242.107f, 49.386f)
          curveTo(242.491f, 49.289f, 242.904f, 49.217f, 243.241f, 48.996f)
          curveTo(244.599f, 48.107f, 243.988f, 46.585f, 243.677f, 45.342f)
          curveTo(243.222f, 43.529f, 242.766f, 41.716f, 242.312f, 39.903f)
          curveTo(241.162f, 35.322f, 240.012f, 30.74f, 238.862f, 26.158f)
          curveTo(238.557f, 24.943f, 238.252f, 23.728f, 237.946f, 22.513f)
          curveTo(237.724f, 21.627f, 237.072f, 20.904f, 236.107f, 20.875f)
          curveTo(235.721f, 20.863f, 235.338f, 20.989f, 234.966f, 21.083f)
          curveTo(231.103f, 22.062f, 227.24f, 23.041f, 223.376f, 24.02f)
          curveTo(219.95f, 24.888f, 216.517f, 25.733f, 213.096f, 26.625f)
          curveTo(212.006f, 26.909f, 211.366f, 27.885f, 211.597f, 29.008f)
          curveTo(211.735f, 29.674f, 212.75f, 29.392f, 212.613f, 28.727f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(241.227f, 76.761f)
          curveTo(241.479f, 76.029f, 242.273f, 75.641f, 243.001f, 75.893f)
          lineTo(265.015f, 83.521f)
          curveTo(265.744f, 83.773f, 266.131f, 84.571f, 265.879f, 85.303f)
          lineTo(257.658f, 109.256f)
          curveTo(257.407f, 109.988f, 256.612f, 110.376f, 255.884f, 110.124f)
          lineTo(233.881f, 102.5f)
          curveTo(233.153f, 102.248f, 232.766f, 101.45f, 233.017f, 100.719f)
          lineTo(241.227f, 76.761f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(241.735f, 76.902f)
          curveTo(242.003f, 76.216f, 242.655f, 76.332f, 243.193f, 76.518f)
          curveTo(243.697f, 76.693f, 244.2f, 76.867f, 244.704f, 77.042f)
          curveTo(246.538f, 77.677f, 248.373f, 78.313f, 250.207f, 78.949f)
          curveTo(254.382f, 80.395f, 258.557f, 81.842f, 262.732f, 83.288f)
          curveTo(263.283f, 83.479f, 263.833f, 83.67f, 264.384f, 83.861f)
          curveTo(264.747f, 83.986f, 265.142f, 84.068f, 265.335f, 84.449f)
          curveTo(265.534f, 84.843f, 265.349f, 85.23f, 265.219f, 85.609f)
          curveTo(265.04f, 86.131f, 264.861f, 86.652f, 264.682f, 87.174f)
          curveTo(263.276f, 91.268f, 261.872f, 95.361f, 260.466f, 99.455f)
          curveTo(259.38f, 102.619f, 258.335f, 105.801f, 257.207f, 108.951f)
          curveTo(257.016f, 109.485f, 256.581f, 109.788f, 255.998f, 109.605f)
          curveTo(255.686f, 109.508f, 255.379f, 109.391f, 255.07f, 109.284f)
          curveTo(253.466f, 108.728f, 251.862f, 108.172f, 250.258f, 107.617f)
          curveTo(245.941f, 106.121f, 241.623f, 104.625f, 237.305f, 103.128f)
          curveTo(236.239f, 102.759f, 235.157f, 102.418f, 234.101f, 102.019f)
          curveTo(233.038f, 101.615f, 233.661f, 100.462f, 233.916f, 99.719f)
          curveTo(235.23f, 95.885f, 236.544f, 92.05f, 237.858f, 88.216f)
          curveTo(239.081f, 84.647f, 240.304f, 81.078f, 241.527f, 77.509f)
          curveTo(241.596f, 77.306f, 241.666f, 77.104f, 241.735f, 76.902f)
          curveTo(241.956f, 76.257f, 240.939f, 75.98f, 240.719f, 76.62f)
          curveTo(239.763f, 79.41f, 238.807f, 82.2f, 237.851f, 84.99f)
          curveTo(236.365f, 89.326f, 234.879f, 93.663f, 233.392f, 98.0f)
          curveTo(233.098f, 98.857f, 232.773f, 99.709f, 232.509f, 100.576f)
          curveTo(232.174f, 101.682f, 232.81f, 102.675f, 233.845f, 103.046f)
          curveTo(234.245f, 103.188f, 234.647f, 103.323f, 235.048f, 103.463f)
          curveTo(239.157f, 104.886f, 243.265f, 106.31f, 247.373f, 107.733f)
          curveTo(249.376f, 108.427f, 251.378f, 109.121f, 253.381f, 109.814f)
          curveTo(253.981f, 110.022f, 254.581f, 110.23f, 255.181f, 110.438f)
          curveTo(255.56f, 110.569f, 255.939f, 110.731f, 256.347f, 110.729f)
          curveTo(257.952f, 110.719f, 258.27f, 109.091f, 258.683f, 107.889f)
          curveTo(259.289f, 106.121f, 259.896f, 104.354f, 260.503f, 102.586f)
          curveTo(262.036f, 98.119f, 263.569f, 93.652f, 265.102f, 89.185f)
          curveTo(265.509f, 88.0f, 265.916f, 86.816f, 266.322f, 85.631f)
          curveTo(266.621f, 84.76f, 266.44f, 83.804f, 265.646f, 83.255f)
          curveTo(265.333f, 83.039f, 264.937f, 82.934f, 264.58f, 82.811f)
          curveTo(260.813f, 81.506f, 257.046f, 80.2f, 253.28f, 78.895f)
          curveTo(249.939f, 77.738f, 246.606f, 76.557f, 243.257f, 75.423f)
          curveTo(242.18f, 75.058f, 241.134f, 75.56f, 240.719f, 76.62f)
          curveTo(240.472f, 77.254f, 241.489f, 77.529f, 241.735f, 76.902f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(261.839f, 40.765f)
          curveTo(261.801f, 39.992f, 262.394f, 39.334f, 263.163f, 39.296f)
          lineTo(286.422f, 38.141f)
          curveTo(287.192f, 38.103f, 287.846f, 38.698f, 287.884f, 39.471f)
          lineTo(289.13f, 64.777f)
          curveTo(289.168f, 65.551f, 288.575f, 66.208f, 287.805f, 66.247f)
          lineTo(264.559f, 67.401f)
          curveTo(263.79f, 67.439f, 263.135f, 66.844f, 263.096f, 66.071f)
          lineTo(261.839f, 40.765f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(262.365f, 40.765f)
          curveTo(262.36f, 40.027f, 262.923f, 39.837f, 263.514f, 39.808f)
          curveTo(264.046f, 39.781f, 264.578f, 39.755f, 265.11f, 39.728f)
          curveTo(267.048f, 39.632f, 268.986f, 39.536f, 270.925f, 39.44f)
          curveTo(275.336f, 39.221f, 279.747f, 39.001f, 284.157f, 38.782f)
          curveTo(284.739f, 38.753f, 285.32f, 38.724f, 285.902f, 38.696f)
          curveTo(286.278f, 38.677f, 286.711f, 38.601f, 287.029f, 38.853f)
          curveTo(287.386f, 39.134f, 287.361f, 39.543f, 287.381f, 39.943f)
          curveTo(287.408f, 40.494f, 287.435f, 41.046f, 287.462f, 41.597f)
          curveTo(287.675f, 45.922f, 287.888f, 50.247f, 288.101f, 54.571f)
          curveTo(288.265f, 57.916f, 288.447f, 61.26f, 288.594f, 64.605f)
          curveTo(288.619f, 65.159f, 288.397f, 65.653f, 287.777f, 65.72f)
          curveTo(287.453f, 65.755f, 287.121f, 65.752f, 286.796f, 65.769f)
          curveTo(285.102f, 65.853f, 283.407f, 65.937f, 281.713f, 66.021f)
          curveTo(277.151f, 66.248f, 272.589f, 66.474f, 268.028f, 66.701f)
          curveTo(266.9f, 66.757f, 265.771f, 66.826f, 264.643f, 66.869f)
          curveTo(263.455f, 66.914f, 263.603f, 65.684f, 263.563f, 64.867f)
          curveTo(263.362f, 60.816f, 263.16f, 56.766f, 262.959f, 52.716f)
          curveTo(262.772f, 48.946f, 262.584f, 45.176f, 262.397f, 41.406f)
          curveTo(262.386f, 41.192f, 262.376f, 40.979f, 262.365f, 40.765f)
          curveTo(262.332f, 40.088f, 261.279f, 40.084f, 261.313f, 40.765f)
          curveTo(261.459f, 43.712f, 261.605f, 46.659f, 261.752f, 49.606f)
          curveTo(261.98f, 54.187f, 262.207f, 58.768f, 262.435f, 63.349f)
          curveTo(262.48f, 64.255f, 262.499f, 65.166f, 262.57f, 66.07f)
          curveTo(262.658f, 67.2f, 263.551f, 67.97f, 264.668f, 67.925f)
          curveTo(265.092f, 67.908f, 265.516f, 67.883f, 265.94f, 67.862f)
          curveTo(270.28f, 67.646f, 274.621f, 67.431f, 278.961f, 67.215f)
          curveTo(281.077f, 67.11f, 283.193f, 67.005f, 285.308f, 66.9f)
          curveTo(285.942f, 66.868f, 286.576f, 66.837f, 287.21f, 66.805f)
          curveTo(287.605f, 66.785f, 288.03f, 66.796f, 288.403f, 66.655f)
          curveTo(289.936f, 66.074f, 289.641f, 64.468f, 289.578f, 63.186f)
          curveTo(289.486f, 61.319f, 289.394f, 59.451f, 289.302f, 57.584f)
          curveTo(289.069f, 52.864f, 288.837f, 48.145f, 288.605f, 43.426f)
          curveTo(288.543f, 42.174f, 288.482f, 40.922f, 288.42f, 39.671f)
          curveTo(288.375f, 38.752f, 287.916f, 37.921f, 286.972f, 37.673f)
          curveTo(286.596f, 37.574f, 286.197f, 37.623f, 285.814f, 37.642f)
          curveTo(281.835f, 37.84f, 277.855f, 38.038f, 273.876f, 38.235f)
          curveTo(270.346f, 38.411f, 266.815f, 38.562f, 263.287f, 38.762f)
          curveTo(262.178f, 38.824f, 261.305f, 39.605f, 261.313f, 40.766f)
          curveTo(261.317f, 41.445f, 262.37f, 41.447f, 262.365f, 40.765f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(223.109f, 124.141f)
          curveTo(223.314f, 123.395f, 224.082f, 122.957f, 224.825f, 123.163f)
          lineTo(247.271f, 129.394f)
          curveTo(248.013f, 129.6f, 248.449f, 130.372f, 248.244f, 131.118f)
          lineTo(241.528f, 155.54f)
          curveTo(241.322f, 156.286f, 240.554f, 156.724f, 239.812f, 156.518f)
          lineTo(217.377f, 150.291f)
          curveTo(216.635f, 150.085f, 216.199f, 149.313f, 216.404f, 148.567f)
          lineTo(223.109f, 124.141f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(223.617f, 124.281f)
          curveTo(223.843f, 123.578f, 224.462f, 123.611f, 225.024f, 123.767f)
          curveTo(225.537f, 123.909f, 226.051f, 124.052f, 226.564f, 124.195f)
          curveTo(228.435f, 124.714f, 230.305f, 125.233f, 232.175f, 125.752f)
          curveTo(236.432f, 126.934f, 240.689f, 128.116f, 244.946f, 129.297f)
          curveTo(245.507f, 129.453f, 246.068f, 129.609f, 246.629f, 129.765f)
          curveTo(246.995f, 129.866f, 247.406f, 129.925f, 247.63f, 130.272f)
          curveTo(247.875f, 130.653f, 247.718f, 131.043f, 247.611f, 131.432f)
          curveTo(247.465f, 131.964f, 247.319f, 132.497f, 247.172f, 133.029f)
          curveTo(246.024f, 137.202f, 244.877f, 141.376f, 243.729f, 145.55f)
          curveTo(242.842f, 148.777f, 241.971f, 152.01f, 241.067f, 155.232f)
          curveTo(240.915f, 155.772f, 240.527f, 156.138f, 239.925f, 156.0f)
          curveTo(239.607f, 155.927f, 239.293f, 155.825f, 238.978f, 155.737f)
          curveTo(237.343f, 155.284f, 235.708f, 154.83f, 234.073f, 154.376f)
          curveTo(229.67f, 153.154f, 225.268f, 151.932f, 220.865f, 150.709f)
          curveTo(219.777f, 150.407f, 218.684f, 150.117f, 217.599f, 149.803f)
          curveTo(216.479f, 149.478f, 217.019f, 148.314f, 217.231f, 147.544f)
          curveTo(218.304f, 143.634f, 219.377f, 139.725f, 220.45f, 135.816f)
          curveTo(221.449f, 132.177f, 222.448f, 128.538f, 223.446f, 124.9f)
          curveTo(223.503f, 124.694f, 223.56f, 124.488f, 223.617f, 124.281f)
          curveTo(223.797f, 123.624f, 222.782f, 123.344f, 222.601f, 124.0f)
          curveTo(221.82f, 126.844f, 221.04f, 129.688f, 220.259f, 132.533f)
          curveTo(219.045f, 136.955f, 217.832f, 141.376f, 216.618f, 145.798f)
          curveTo(216.378f, 146.672f, 216.106f, 147.543f, 215.897f, 148.424f)
          curveTo(215.634f, 149.537f, 216.274f, 150.522f, 217.344f, 150.829f)
          curveTo(217.752f, 150.947f, 218.162f, 151.056f, 218.571f, 151.17f)
          curveTo(222.76f, 152.333f, 226.948f, 153.496f, 231.137f, 154.659f)
          curveTo(233.179f, 155.225f, 235.221f, 155.792f, 237.263f, 156.359f)
          curveTo(237.874f, 156.529f, 238.486f, 156.698f, 239.098f, 156.868f)
          curveTo(239.48f, 156.974f, 239.879f, 157.118f, 240.279f, 157.094f)
          curveTo(241.901f, 156.998f, 242.118f, 155.381f, 242.458f, 154.144f)
          curveTo(242.954f, 152.342f, 243.449f, 150.54f, 243.945f, 148.738f)
          curveTo(245.198f, 144.183f, 246.45f, 139.629f, 247.703f, 135.074f)
          curveTo(248.035f, 133.866f, 248.367f, 132.658f, 248.7f, 131.45f)
          curveTo(248.942f, 130.568f, 248.741f, 129.615f, 247.913f, 129.106f)
          curveTo(247.584f, 128.903f, 247.193f, 128.823f, 246.825f, 128.721f)
          curveTo(242.984f, 127.654f, 239.144f, 126.588f, 235.303f, 125.522f)
          curveTo(231.897f, 124.577f, 228.497f, 123.607f, 225.085f, 122.686f)
          curveTo(224.001f, 122.393f, 222.954f, 122.905f, 222.602f, 124.0f)
          curveTo(222.393f, 124.65f, 223.409f, 124.928f, 223.617f, 124.281f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(255.332f, 120.568f)
          curveTo(255.131f, 119.821f, 255.57f, 119.051f, 256.313f, 118.848f)
          lineTo(278.786f, 112.716f)
          curveTo(279.529f, 112.513f, 280.296f, 112.954f, 280.498f, 113.701f)
          lineTo(287.108f, 138.152f)
          curveTo(287.309f, 138.899f, 286.87f, 139.669f, 286.127f, 139.872f)
          lineTo(263.666f, 146.001f)
          curveTo(262.923f, 146.204f, 262.157f, 145.763f, 261.954f, 145.017f)
          lineTo(255.332f, 120.568f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(255.84f, 120.428f)
          curveTo(255.678f, 119.708f, 256.23f, 119.419f, 256.792f, 119.266f)
          curveTo(257.306f, 119.125f, 257.82f, 118.985f, 258.334f, 118.845f)
          curveTo(260.207f, 118.334f, 262.079f, 117.823f, 263.952f, 117.312f)
          curveTo(268.214f, 116.149f, 272.476f, 114.986f, 276.738f, 113.823f)
          curveTo(277.3f, 113.669f, 277.862f, 113.516f, 278.423f, 113.363f)
          curveTo(278.785f, 113.264f, 279.165f, 113.106f, 279.53f, 113.281f)
          curveTo(279.942f, 113.48f, 280.007f, 113.904f, 280.113f, 114.297f)
          curveTo(280.257f, 114.83f, 280.401f, 115.362f, 280.545f, 115.895f)
          curveTo(281.675f, 120.074f, 282.805f, 124.252f, 283.934f, 128.431f)
          curveTo(284.808f, 131.662f, 285.699f, 134.889f, 286.555f, 138.125f)
          curveTo(286.699f, 138.668f, 286.549f, 139.18f, 285.96f, 139.369f)
          curveTo(285.65f, 139.469f, 285.328f, 139.542f, 285.013f, 139.627f)
          curveTo(283.376f, 140.074f, 281.738f, 140.521f, 280.101f, 140.968f)
          curveTo(275.693f, 142.17f, 271.286f, 143.373f, 266.878f, 144.576f)
          curveTo(265.788f, 144.874f, 264.701f, 145.184f, 263.607f, 145.469f)
          curveTo(262.481f, 145.762f, 262.355f, 144.481f, 262.146f, 143.711f)
          curveTo(261.086f, 139.798f, 260.027f, 135.885f, 258.967f, 131.972f)
          curveTo(257.981f, 128.33f, 256.994f, 124.688f, 256.008f, 121.046f)
          curveTo(255.952f, 120.84f, 255.896f, 120.634f, 255.84f, 120.428f)
          curveTo(255.662f, 119.771f, 254.646f, 120.05f, 254.825f, 120.709f)
          curveTo(255.596f, 123.556f, 256.367f, 126.403f, 257.138f, 129.25f)
          curveTo(258.336f, 133.676f, 259.535f, 138.102f, 260.734f, 142.528f)
          curveTo(260.971f, 143.402f, 261.178f, 144.291f, 261.446f, 145.156f)
          curveTo(261.783f, 146.248f, 262.836f, 146.765f, 263.912f, 146.483f)
          curveTo(264.322f, 146.375f, 264.731f, 146.259f, 265.14f, 146.148f)
          curveTo(269.334f, 145.003f, 273.528f, 143.859f, 277.722f, 142.714f)
          curveTo(279.766f, 142.157f, 281.81f, 141.599f, 283.854f, 141.041f)
          curveTo(284.467f, 140.874f, 285.079f, 140.707f, 285.692f, 140.54f)
          curveTo(286.073f, 140.436f, 286.486f, 140.356f, 286.818f, 140.131f)
          curveTo(288.168f, 139.219f, 287.534f, 137.713f, 287.199f, 136.474f)
          curveTo(286.711f, 134.67f, 286.224f, 132.865f, 285.736f, 131.061f)
          curveTo(284.503f, 126.501f, 283.27f, 121.941f, 282.038f, 117.381f)
          curveTo(281.71f, 116.172f, 281.384f, 114.962f, 281.057f, 113.753f)
          curveTo(280.818f, 112.871f, 280.162f, 112.157f, 279.194f, 112.14f)
          curveTo(278.807f, 112.133f, 278.428f, 112.265f, 278.058f, 112.366f)
          curveTo(274.213f, 113.416f, 270.368f, 114.465f, 266.523f, 115.514f)
          curveTo(263.112f, 116.445f, 259.695f, 117.352f, 256.292f, 118.306f)
          curveTo(255.211f, 118.609f, 254.572f, 119.587f, 254.824f, 120.709f)
          curveTo(254.974f, 121.372f, 255.99f, 121.092f, 255.84f, 120.428f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(105.139f, 1.447f)
          curveTo(105.431f, 0.689f, 106.28f, 0.312f, 107.035f, 0.606f)
          lineTo(129.841f, 9.485f)
          curveTo(130.596f, 9.779f, 130.971f, 10.631f, 130.678f, 11.389f)
          lineTo(121.108f, 36.204f)
          curveTo(120.816f, 36.963f, 119.967f, 37.339f, 119.213f, 37.045f)
          lineTo(96.417f, 28.171f)
          curveTo(95.663f, 27.878f, 95.288f, 27.025f, 95.58f, 26.267f)
          lineTo(105.139f, 1.447f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(105.594f, 1.714f)
          curveTo(105.838f, 1.149f, 106.332f, 0.917f, 106.914f, 1.123f)
          curveTo(107.013f, 1.159f, 107.11f, 1.2f, 107.208f, 1.238f)
          curveTo(107.715f, 1.435f, 108.223f, 1.633f, 108.731f, 1.831f)
          curveTo(110.624f, 2.568f, 112.516f, 3.305f, 114.409f, 4.041f)
          curveTo(118.747f, 5.73f, 123.085f, 7.419f, 127.423f, 9.108f)
          curveTo(128.007f, 9.335f, 128.59f, 9.562f, 129.173f, 9.789f)
          curveTo(129.569f, 9.943f, 129.948f, 10.03f, 130.149f, 10.428f)
          curveTo(130.368f, 10.865f, 130.161f, 11.275f, 130.004f, 11.682f)
          curveTo(129.8f, 12.212f, 129.595f, 12.743f, 129.39f, 13.274f)
          curveTo(127.756f, 17.511f, 126.122f, 21.748f, 124.488f, 25.986f)
          curveTo(123.214f, 29.288f, 121.94f, 32.591f, 120.667f, 35.894f)
          curveTo(120.454f, 36.445f, 119.954f, 36.748f, 119.328f, 36.525f)
          curveTo(119.01f, 36.412f, 118.697f, 36.28f, 118.382f, 36.157f)
          curveTo(116.732f, 35.515f, 115.081f, 34.872f, 113.431f, 34.23f)
          curveTo(108.963f, 32.49f, 104.495f, 30.751f, 100.028f, 29.012f)
          curveTo(98.902f, 28.574f, 97.757f, 28.166f, 96.644f, 27.695f)
          curveTo(96.103f, 27.465f, 95.901f, 26.913f, 96.119f, 26.328f)
          curveTo(96.253f, 25.969f, 96.395f, 25.612f, 96.533f, 25.254f)
          curveTo(98.067f, 21.269f, 99.602f, 17.284f, 101.137f, 13.3f)
          curveTo(102.559f, 9.605f, 103.982f, 5.911f, 105.405f, 2.216f)
          curveTo(105.486f, 2.007f, 105.567f, 1.797f, 105.647f, 1.588f)
          curveTo(105.892f, 0.952f, 104.874f, 0.677f, 104.632f, 1.306f)
          curveTo(103.512f, 4.213f, 102.393f, 7.12f, 101.273f, 10.027f)
          curveTo(99.548f, 14.508f, 97.822f, 18.99f, 96.096f, 23.471f)
          curveTo(95.756f, 24.354f, 95.385f, 25.231f, 95.074f, 26.125f)
          curveTo(94.678f, 27.265f, 95.336f, 28.306f, 96.388f, 28.724f)
          curveTo(96.802f, 28.888f, 97.219f, 29.047f, 97.634f, 29.209f)
          curveTo(101.874f, 30.859f, 106.113f, 32.51f, 110.353f, 34.16f)
          curveTo(112.427f, 34.968f, 114.502f, 35.775f, 116.576f, 36.583f)
          curveTo(117.203f, 36.827f, 117.831f, 37.071f, 118.459f, 37.316f)
          curveTo(118.817f, 37.455f, 119.172f, 37.623f, 119.557f, 37.662f)
          curveTo(120.597f, 37.767f, 121.338f, 37.069f, 121.689f, 36.16f)
          curveTo(121.859f, 35.718f, 122.029f, 35.277f, 122.199f, 34.835f)
          curveTo(122.904f, 33.009f, 123.608f, 31.183f, 124.312f, 29.357f)
          curveTo(126.099f, 24.723f, 127.887f, 20.088f, 129.674f, 15.454f)
          curveTo(130.152f, 14.213f, 130.631f, 12.973f, 131.109f, 11.732f)
          curveTo(131.449f, 10.85f, 131.3f, 9.867f, 130.513f, 9.267f)
          curveTo(130.191f, 9.02f, 129.775f, 8.894f, 129.398f, 8.748f)
          curveTo(125.506f, 7.233f, 121.615f, 5.718f, 117.722f, 4.203f)
          curveTo(114.252f, 2.852f, 110.79f, 1.479f, 107.312f, 0.15f)
          curveTo(106.296f, -0.238f, 105.136f, 0.139f, 104.685f, 1.18f)
          curveTo(104.417f, 1.798f, 105.324f, 2.337f, 105.594f, 1.714f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(68.962f, 79.46f)
          curveTo(68.926f, 78.647f, 69.553f, 77.96f, 70.361f, 77.925f)
          lineTo(94.797f, 76.848f)
          curveTo(95.605f, 76.812f, 96.289f, 77.442f, 96.325f, 78.254f)
          lineTo(97.485f, 104.841f)
          curveTo(97.521f, 105.653f, 96.894f, 106.341f, 96.086f, 106.376f)
          lineTo(71.663f, 107.452f)
          curveTo(70.855f, 107.488f, 70.171f, 106.859f, 70.135f, 106.047f)
          lineTo(68.962f, 79.46f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(69.488f, 79.46f)
          curveTo(69.485f, 78.678f, 70.088f, 78.465f, 70.719f, 78.438f)
          curveTo(71.272f, 78.413f, 71.825f, 78.389f, 72.379f, 78.365f)
          curveTo(74.418f, 78.275f, 76.456f, 78.185f, 78.495f, 78.095f)
          curveTo(83.134f, 77.891f, 87.772f, 77.687f, 92.411f, 77.482f)
          curveTo(93.022f, 77.455f, 93.633f, 77.428f, 94.245f, 77.401f)
          curveTo(94.63f, 77.384f, 95.077f, 77.309f, 95.408f, 77.55f)
          curveTo(95.822f, 77.852f, 95.8f, 78.287f, 95.82f, 78.731f)
          curveTo(95.845f, 79.304f, 95.87f, 79.877f, 95.895f, 80.449f)
          curveTo(96.094f, 85.009f, 96.293f, 89.57f, 96.492f, 94.13f)
          curveTo(96.646f, 97.64f, 96.799f, 101.149f, 96.952f, 104.659f)
          curveTo(96.978f, 105.256f, 96.725f, 105.782f, 96.06f, 105.849f)
          curveTo(95.725f, 105.882f, 95.383f, 105.879f, 95.047f, 105.894f)
          curveTo(93.265f, 105.972f, 91.484f, 106.05f, 89.702f, 106.129f)
          curveTo(84.907f, 106.34f, 80.112f, 106.552f, 75.316f, 106.763f)
          curveTo(74.13f, 106.815f, 72.943f, 106.88f, 71.756f, 106.92f)
          curveTo(70.496f, 106.963f, 70.646f, 105.682f, 70.607f, 104.812f)
          curveTo(70.419f, 100.543f, 70.231f, 96.274f, 70.042f, 92.006f)
          curveTo(69.867f, 88.048f, 69.693f, 84.091f, 69.518f, 80.134f)
          curveTo(69.508f, 79.908f, 69.498f, 79.684f, 69.488f, 79.46f)
          curveTo(69.458f, 78.782f, 68.405f, 78.778f, 68.435f, 79.46f)
          curveTo(68.573f, 82.574f, 68.71f, 85.688f, 68.848f, 88.801f)
          curveTo(69.06f, 93.602f, 69.271f, 98.402f, 69.483f, 103.203f)
          curveTo(69.525f, 104.149f, 69.54f, 105.101f, 69.608f, 106.046f)
          curveTo(69.693f, 107.221f, 70.623f, 108.017f, 71.781f, 107.976f)
          curveTo(72.226f, 107.96f, 72.671f, 107.937f, 73.115f, 107.917f)
          curveTo(77.672f, 107.717f, 82.229f, 107.516f, 86.785f, 107.315f)
          curveTo(89.012f, 107.217f, 91.24f, 107.119f, 93.467f, 107.021f)
          curveTo(94.131f, 106.992f, 94.794f, 106.962f, 95.458f, 106.933f)
          curveTo(95.863f, 106.915f, 96.3f, 106.929f, 96.686f, 106.791f)
          curveTo(98.3f, 106.214f, 97.998f, 104.511f, 97.94f, 103.178f)
          curveTo(97.854f, 101.223f, 97.769f, 99.269f, 97.684f, 97.314f)
          curveTo(97.468f, 92.362f, 97.252f, 87.41f, 97.035f, 82.458f)
          curveTo(96.977f, 81.126f, 96.919f, 79.793f, 96.861f, 78.461f)
          curveTo(96.818f, 77.485f, 96.31f, 76.587f, 95.291f, 76.365f)
          curveTo(94.918f, 76.283f, 94.519f, 76.331f, 94.141f, 76.348f)
          curveTo(89.952f, 76.532f, 85.763f, 76.717f, 81.575f, 76.901f)
          curveTo(77.881f, 77.064f, 74.185f, 77.201f, 70.493f, 77.39f)
          curveTo(69.342f, 77.448f, 68.431f, 78.254f, 68.436f, 79.46f)
          curveTo(68.437f, 80.14f, 69.49f, 80.141f, 69.488f, 79.46f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(68.989f, 125.11f)
          curveTo(68.754f, 124.332f, 69.19f, 123.51f, 69.965f, 123.273f)
          lineTo(93.367f, 116.129f)
          curveTo(94.142f, 115.893f, 94.96f, 116.332f, 95.195f, 117.11f)
          lineTo(102.896f, 142.573f)
          curveTo(103.131f, 143.35f, 102.694f, 144.173f, 101.92f, 144.409f)
          lineTo(78.53f, 151.55f)
          curveTo(77.756f, 151.786f, 76.938f, 151.348f, 76.702f, 150.57f)
          lineTo(68.989f, 125.11f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(69.496f, 124.969f)
          curveTo(69.3f, 124.213f, 69.846f, 123.862f, 70.447f, 123.679f)
          curveTo(70.977f, 123.517f, 71.507f, 123.355f, 72.037f, 123.194f)
          curveTo(73.989f, 122.597f, 75.942f, 122.002f, 77.895f, 121.405f)
          curveTo(82.337f, 120.049f, 86.779f, 118.693f, 91.222f, 117.337f)
          curveTo(91.808f, 117.158f, 92.393f, 116.979f, 92.979f, 116.8f)
          curveTo(93.347f, 116.688f, 93.757f, 116.505f, 94.138f, 116.658f)
          curveTo(94.61f, 116.848f, 94.698f, 117.282f, 94.826f, 117.707f)
          curveTo(94.992f, 118.255f, 95.158f, 118.803f, 95.324f, 119.352f)
          curveTo(96.645f, 123.719f, 97.965f, 128.087f, 99.286f, 132.455f)
          curveTo(100.303f, 135.816f, 101.319f, 139.177f, 102.336f, 142.538f)
          curveTo(102.51f, 143.112f, 102.382f, 143.68f, 101.755f, 143.907f)
          curveTo(101.438f, 144.021f, 101.106f, 144.104f, 100.784f, 144.203f)
          curveTo(99.078f, 144.723f, 97.372f, 145.244f, 95.666f, 145.765f)
          curveTo(91.074f, 147.167f, 86.481f, 148.569f, 81.888f, 149.971f)
          curveTo(80.751f, 150.318f, 79.618f, 150.677f, 78.478f, 151.012f)
          curveTo(77.276f, 151.366f, 77.101f, 150.073f, 76.851f, 149.246f)
          curveTo(75.612f, 145.158f, 74.374f, 141.07f, 73.135f, 136.982f)
          curveTo(71.988f, 133.193f, 70.839f, 129.403f, 69.691f, 125.613f)
          curveTo(69.626f, 125.399f, 69.561f, 125.184f, 69.496f, 124.969f)
          curveTo(69.299f, 124.32f, 68.283f, 124.597f, 68.481f, 125.251f)
          curveTo(69.384f, 128.233f, 70.287f, 131.214f, 71.191f, 134.196f)
          curveTo(72.583f, 138.793f, 73.976f, 143.39f, 75.368f, 147.987f)
          curveTo(75.643f, 148.893f, 75.891f, 149.812f, 76.193f, 150.709f)
          curveTo(76.571f, 151.828f, 77.672f, 152.354f, 78.782f, 152.025f)
          curveTo(79.209f, 151.899f, 79.634f, 151.765f, 80.06f, 151.635f)
          curveTo(84.424f, 150.303f, 88.788f, 148.97f, 93.152f, 147.638f)
          curveTo(95.285f, 146.987f, 97.418f, 146.336f, 99.551f, 145.685f)
          curveTo(100.187f, 145.491f, 100.823f, 145.297f, 101.458f, 145.103f)
          curveTo(101.846f, 144.984f, 102.268f, 144.889f, 102.607f, 144.659f)
          curveTo(104.025f, 143.695f, 103.308f, 142.116f, 102.922f, 140.84f)
          curveTo(102.355f, 138.968f, 101.789f, 137.096f, 101.223f, 135.223f)
          curveTo(99.789f, 130.481f, 98.354f, 125.738f, 96.92f, 120.995f)
          curveTo(96.534f, 119.72f, 96.148f, 118.443f, 95.763f, 117.168f)
          curveTo(95.48f, 116.233f, 94.756f, 115.495f, 93.716f, 115.538f)
          curveTo(93.336f, 115.554f, 92.96f, 115.701f, 92.599f, 115.811f)
          curveTo(88.587f, 117.036f, 84.575f, 118.26f, 80.563f, 119.485f)
          curveTo(77.026f, 120.565f, 73.48f, 121.621f, 69.95f, 122.725f)
          curveTo(68.846f, 123.07f, 68.179f, 124.087f, 68.481f, 125.251f)
          curveTo(68.651f, 125.909f, 69.667f, 125.63f, 69.496f, 124.969f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(45.595f, 43.273f)
          curveTo(45.499f, 42.466f, 46.073f, 41.734f, 46.876f, 41.637f)
          lineTo(71.163f, 38.726f)
          curveTo(71.966f, 38.629f, 72.695f, 39.206f, 72.791f, 40.013f)
          lineTo(75.93f, 66.438f)
          curveTo(76.025f, 67.246f, 75.452f, 67.978f, 74.648f, 68.075f)
          lineTo(50.374f, 70.984f)
          curveTo(49.57f, 71.081f, 48.842f, 70.505f, 48.745f, 69.698f)
          lineTo(45.595f, 43.273f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(46.121f, 43.273f)
          curveTo(46.059f, 42.477f, 46.599f, 42.199f, 47.232f, 42.123f)
          curveTo(47.782f, 42.057f, 48.331f, 41.991f, 48.881f, 41.925f)
          curveTo(50.908f, 41.682f, 52.934f, 41.439f, 54.96f, 41.196f)
          curveTo(59.571f, 40.644f, 64.181f, 40.091f, 68.791f, 39.539f)
          curveTo(69.399f, 39.466f, 70.006f, 39.393f, 70.614f, 39.32f)
          curveTo(70.995f, 39.275f, 71.44f, 39.167f, 71.79f, 39.361f)
          curveTo(72.264f, 39.623f, 72.266f, 40.021f, 72.322f, 40.487f)
          curveTo(72.389f, 41.056f, 72.457f, 41.625f, 72.524f, 42.194f)
          curveTo(73.063f, 46.727f, 73.601f, 51.259f, 74.139f, 55.792f)
          curveTo(74.553f, 59.28f, 74.968f, 62.769f, 75.382f, 66.257f)
          curveTo(75.454f, 66.862f, 75.228f, 67.438f, 74.622f, 67.549f)
          curveTo(74.291f, 67.609f, 73.948f, 67.629f, 73.615f, 67.669f)
          curveTo(71.844f, 67.881f, 70.074f, 68.094f, 68.303f, 68.306f)
          curveTo(63.537f, 68.877f, 58.771f, 69.449f, 54.005f, 70.02f)
          curveTo(52.825f, 70.161f, 51.646f, 70.315f, 50.465f, 70.444f)
          curveTo(49.175f, 70.585f, 49.234f, 69.384f, 49.125f, 68.469f)
          curveTo(48.62f, 64.227f, 48.114f, 59.984f, 47.608f, 55.741f)
          curveTo(47.139f, 51.808f, 46.67f, 47.875f, 46.201f, 43.942f)
          curveTo(46.174f, 43.719f, 46.148f, 43.496f, 46.121f, 43.273f)
          curveTo(46.041f, 42.606f, 44.987f, 42.598f, 45.068f, 43.273f)
          curveTo(45.437f, 46.368f, 45.806f, 49.463f, 46.175f, 52.558f)
          curveTo(46.744f, 57.329f, 47.313f, 62.1f, 47.882f, 66.871f)
          curveTo(47.994f, 67.812f, 48.096f, 68.756f, 48.219f, 69.696f)
          curveTo(48.37f, 70.852f, 49.335f, 71.628f, 50.491f, 71.499f)
          curveTo(50.934f, 71.45f, 51.375f, 71.393f, 51.818f, 71.34f)
          curveTo(56.347f, 70.797f, 60.875f, 70.255f, 65.404f, 69.712f)
          curveTo(67.618f, 69.446f, 69.831f, 69.181f, 72.045f, 68.915f)
          curveTo(72.704f, 68.837f, 73.364f, 68.757f, 74.024f, 68.678f)
          curveTo(74.433f, 68.629f, 74.879f, 68.61f, 75.258f, 68.447f)
          curveTo(76.84f, 67.768f, 76.416f, 66.108f, 76.259f, 64.786f)
          curveTo(76.029f, 62.843f, 75.798f, 60.9f, 75.567f, 58.957f)
          curveTo(74.983f, 54.035f, 74.398f, 49.113f, 73.814f, 44.192f)
          curveTo(73.657f, 42.868f, 73.499f, 41.543f, 73.342f, 40.219f)
          curveTo(73.226f, 39.245f, 72.699f, 38.384f, 71.657f, 38.216f)
          curveTo(71.275f, 38.155f, 70.891f, 38.229f, 70.51f, 38.275f)
          curveTo(66.347f, 38.774f, 62.183f, 39.273f, 58.02f, 39.772f)
          curveTo(54.349f, 40.212f, 50.676f, 40.643f, 47.006f, 41.093f)
          curveTo(45.877f, 41.231f, 44.973f, 42.059f, 45.068f, 43.274f)
          curveTo(45.121f, 43.947f, 46.174f, 43.953f, 46.121f, 43.273f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(26.481f, 87.898f)
          curveTo(26.356f, 87.095f, 26.902f, 86.341f, 27.702f, 86.216f)
          lineTo(51.866f, 82.412f)
          curveTo(52.666f, 82.287f, 53.415f, 82.836f, 53.541f, 83.639f)
          lineTo(57.64f, 109.931f)
          curveTo(57.765f, 110.734f, 57.218f, 111.487f, 56.419f, 111.613f)
          lineTo(32.267f, 115.414f)
          curveTo(31.468f, 115.54f, 30.718f, 114.991f, 30.593f, 114.188f)
          lineTo(26.481f, 87.898f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(27.007f, 87.898f)
          curveTo(26.937f, 87.283f, 27.253f, 86.837f, 27.862f, 86.722f)
          curveTo(27.965f, 86.703f, 28.07f, 86.689f, 28.173f, 86.673f)
          curveTo(28.711f, 86.589f, 29.249f, 86.504f, 29.787f, 86.419f)
          curveTo(31.792f, 86.104f, 33.798f, 85.788f, 35.803f, 85.472f)
          curveTo(40.4f, 84.749f, 44.996f, 84.026f, 49.592f, 83.302f)
          curveTo(50.21f, 83.205f, 50.828f, 83.107f, 51.446f, 83.01f)
          curveTo(51.87f, 82.943f, 52.224f, 82.83f, 52.597f, 83.07f)
          curveTo(53.011f, 83.336f, 53.037f, 83.803f, 53.105f, 84.238f)
          curveTo(53.193f, 84.8f, 53.28f, 85.362f, 53.368f, 85.924f)
          curveTo(54.068f, 90.414f, 54.768f, 94.904f, 55.468f, 99.393f)
          curveTo(56.014f, 102.892f, 56.559f, 106.392f, 57.105f, 109.891f)
          curveTo(57.195f, 110.469f, 56.91f, 110.983f, 56.253f, 111.107f)
          curveTo(55.922f, 111.169f, 55.585f, 111.212f, 55.251f, 111.264f)
          curveTo(53.502f, 111.54f, 51.754f, 111.815f, 50.005f, 112.09f)
          curveTo(45.271f, 112.835f, 40.538f, 113.58f, 35.804f, 114.325f)
          curveTo(34.611f, 114.513f, 33.417f, 114.739f, 32.219f, 114.889f)
          curveTo(31.647f, 114.961f, 31.192f, 114.583f, 31.088f, 113.963f)
          curveTo(31.024f, 113.584f, 30.969f, 113.205f, 30.91f, 112.826f)
          curveTo(30.25f, 108.605f, 29.59f, 104.383f, 28.929f, 100.162f)
          curveTo(28.317f, 96.249f, 27.705f, 92.336f, 27.093f, 88.422f)
          curveTo(27.059f, 88.201f, 27.024f, 87.979f, 26.989f, 87.757f)
          curveTo(26.884f, 87.086f, 25.869f, 87.371f, 25.974f, 88.038f)
          curveTo(26.455f, 91.117f, 26.937f, 94.197f, 27.419f, 97.276f)
          curveTo(28.161f, 102.022f, 28.903f, 106.769f, 29.646f, 111.516f)
          curveTo(29.792f, 112.451f, 29.908f, 113.397f, 30.085f, 114.327f)
          curveTo(30.311f, 115.514f, 31.408f, 116.076f, 32.524f, 115.906f)
          curveTo(32.964f, 115.838f, 33.404f, 115.767f, 33.844f, 115.698f)
          curveTo(38.336f, 114.991f, 42.828f, 114.284f, 47.32f, 113.577f)
          curveTo(49.518f, 113.231f, 51.715f, 112.886f, 53.913f, 112.54f)
          curveTo(54.578f, 112.435f, 55.243f, 112.33f, 55.908f, 112.226f)
          curveTo(56.285f, 112.167f, 56.675f, 112.133f, 57.026f, 111.973f)
          curveTo(57.981f, 111.536f, 58.268f, 110.56f, 58.117f, 109.594f)
          curveTo(58.044f, 109.126f, 57.971f, 108.659f, 57.898f, 108.191f)
          curveTo(57.597f, 106.256f, 57.295f, 104.321f, 56.993f, 102.387f)
          curveTo(56.228f, 97.476f, 55.462f, 92.566f, 54.697f, 87.656f)
          curveTo(54.492f, 86.341f, 54.287f, 85.027f, 54.082f, 83.712f)
          curveTo(53.936f, 82.779f, 53.314f, 82.008f, 52.336f, 81.886f)
          curveTo(51.933f, 81.836f, 51.507f, 81.937f, 51.108f, 82.0f)
          curveTo(46.984f, 82.649f, 42.861f, 83.298f, 38.737f, 83.947f)
          curveTo(35.06f, 84.526f, 31.38f, 85.082f, 27.707f, 85.683f)
          curveTo(26.635f, 85.858f, 25.825f, 86.772f, 25.955f, 87.898f)
          curveTo(26.031f, 88.566f, 27.085f, 88.574f, 27.007f, 87.898f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(29.988f, 124.627f)
          curveTo(30.149f, 123.83f, 30.923f, 123.316f, 31.716f, 123.477f)
          lineTo(55.685f, 128.369f)
          curveTo(56.478f, 128.531f, 56.991f, 129.308f, 56.83f, 130.105f)
          lineTo(51.557f, 156.185f)
          curveTo(51.396f, 156.982f, 50.623f, 157.497f, 49.83f, 157.335f)
          lineTo(25.872f, 152.446f)
          curveTo(25.08f, 152.284f, 24.567f, 151.507f, 24.728f, 150.711f)
          lineTo(29.988f, 124.627f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(30.496f, 124.768f)
          curveTo(30.681f, 124.01f, 31.303f, 123.932f, 31.927f, 124.059f)
          curveTo(32.469f, 124.17f, 33.012f, 124.281f, 33.555f, 124.391f)
          curveTo(35.555f, 124.799f, 37.554f, 125.208f, 39.554f, 125.616f)
          curveTo(44.104f, 126.544f, 48.654f, 127.473f, 53.204f, 128.401f)
          curveTo(53.804f, 128.523f, 54.404f, 128.646f, 55.004f, 128.768f)
          curveTo(55.383f, 128.845f, 55.839f, 128.882f, 56.105f, 129.194f)
          curveTo(56.437f, 129.585f, 56.315f, 129.997f, 56.227f, 130.432f)
          curveTo(56.114f, 130.993f, 56.0f, 131.555f, 55.887f, 132.117f)
          curveTo(54.982f, 136.59f, 54.078f, 141.064f, 53.173f, 145.537f)
          curveTo(52.478f, 148.98f, 51.782f, 152.422f, 51.085f, 155.865f)
          curveTo(50.968f, 156.447f, 50.607f, 156.912f, 49.943f, 156.819f)
          curveTo(49.61f, 156.772f, 49.278f, 156.683f, 48.949f, 156.616f)
          curveTo(47.202f, 156.259f, 45.454f, 155.902f, 43.707f, 155.546f)
          curveTo(39.003f, 154.586f, 34.299f, 153.626f, 29.595f, 152.666f)
          curveTo(28.431f, 152.429f, 27.264f, 152.203f, 26.102f, 151.953f)
          curveTo(24.858f, 151.687f, 25.306f, 150.501f, 25.48f, 149.638f)
          curveTo(26.324f, 145.451f, 27.169f, 141.263f, 28.013f, 137.075f)
          curveTo(28.796f, 133.193f, 29.579f, 129.31f, 30.362f, 125.428f)
          curveTo(30.407f, 125.208f, 30.451f, 124.988f, 30.496f, 124.768f)
          curveTo(30.63f, 124.103f, 29.615f, 123.819f, 29.48f, 124.487f)
          curveTo(28.865f, 127.541f, 28.248f, 130.596f, 27.632f, 133.651f)
          curveTo(26.683f, 138.361f, 25.733f, 143.07f, 24.783f, 147.779f)
          curveTo(24.596f, 148.708f, 24.387f, 149.635f, 24.221f, 150.568f)
          curveTo(24.014f, 151.725f, 24.713f, 152.738f, 25.848f, 152.979f)
          curveTo(26.283f, 153.072f, 26.721f, 153.157f, 27.157f, 153.246f)
          curveTo(31.627f, 154.159f, 36.097f, 155.071f, 40.566f, 155.983f)
          curveTo(42.751f, 156.429f, 44.936f, 156.875f, 47.121f, 157.32f)
          curveTo(47.772f, 157.453f, 48.423f, 157.586f, 49.074f, 157.719f)
          curveTo(49.471f, 157.8f, 49.893f, 157.92f, 50.299f, 157.882f)
          curveTo(52.013f, 157.725f, 52.13f, 156.004f, 52.395f, 154.694f)
          curveTo(52.782f, 152.777f, 53.17f, 150.859f, 53.558f, 148.942f)
          curveTo(54.54f, 144.084f, 55.522f, 139.226f, 56.504f, 134.369f)
          curveTo(56.768f, 133.062f, 57.032f, 131.756f, 57.296f, 130.449f)
          curveTo(57.49f, 129.489f, 57.226f, 128.496f, 56.288f, 128.025f)
          curveTo(55.947f, 127.854f, 55.552f, 127.803f, 55.182f, 127.728f)
          curveTo(51.073f, 126.89f, 46.964f, 126.051f, 42.855f, 125.213f)
          curveTo(39.232f, 124.473f, 35.613f, 123.709f, 31.985f, 122.994f)
          curveTo(30.859f, 122.773f, 29.768f, 123.312f, 29.481f, 124.487f)
          curveTo(29.319f, 125.148f, 30.334f, 125.43f, 30.496f, 124.768f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(272.993f, 76.226f)
          curveTo(273.074f, 75.456f, 273.762f, 74.899f, 274.528f, 74.98f)
          lineTo(297.685f, 77.446f)
          curveTo(298.451f, 77.528f, 299.006f, 78.218f, 298.925f, 78.988f)
          lineTo(296.267f, 104.183f)
          curveTo(296.186f, 104.953f, 295.499f, 105.511f, 294.733f, 105.43f)
          lineTo(271.587f, 102.965f)
          curveTo(270.822f, 102.883f, 270.266f, 102.193f, 270.347f, 101.424f)
          lineTo(272.993f, 76.226f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(273.501f, 76.367f)
          curveTo(273.586f, 75.793f, 273.969f, 75.468f, 274.549f, 75.511f)
          curveTo(274.65f, 75.519f, 274.752f, 75.533f, 274.854f, 75.544f)
          curveTo(275.374f, 75.599f, 275.895f, 75.655f, 276.416f, 75.71f)
          curveTo(278.348f, 75.916f, 280.281f, 76.122f, 282.214f, 76.328f)
          curveTo(286.601f, 76.795f, 290.988f, 77.262f, 295.376f, 77.729f)
          curveTo(295.968f, 77.792f, 296.561f, 77.855f, 297.153f, 77.919f)
          curveTo(297.569f, 77.963f, 297.937f, 77.943f, 298.214f, 78.29f)
          curveTo(298.483f, 78.626f, 298.393f, 79.042f, 298.352f, 79.431f)
          curveTo(298.295f, 79.972f, 298.238f, 80.512f, 298.181f, 81.053f)
          curveTo(297.727f, 85.362f, 297.272f, 89.67f, 296.817f, 93.979f)
          curveTo(296.465f, 97.323f, 296.123f, 100.668f, 295.759f, 104.011f)
          curveTo(295.7f, 104.551f, 295.33f, 104.947f, 294.705f, 104.898f)
          curveTo(294.386f, 104.873f, 294.066f, 104.83f, 293.748f, 104.796f)
          curveTo(292.067f, 104.617f, 290.387f, 104.438f, 288.706f, 104.259f)
          curveTo(284.162f, 103.776f, 279.618f, 103.291f, 275.074f, 102.808f)
          curveTo(273.942f, 102.687f, 272.799f, 102.604f, 271.671f, 102.445f)
          curveTo(271.135f, 102.37f, 270.828f, 101.92f, 270.883f, 101.335f)
          curveTo(270.917f, 100.964f, 270.961f, 100.594f, 271.0f, 100.224f)
          curveTo(271.423f, 96.191f, 271.847f, 92.158f, 272.27f, 88.126f)
          curveTo(272.664f, 84.372f, 273.059f, 80.618f, 273.453f, 76.864f)
          curveTo(273.475f, 76.651f, 273.497f, 76.439f, 273.52f, 76.226f)
          curveTo(273.591f, 75.55f, 272.537f, 75.555f, 272.467f, 76.226f)
          curveTo(272.159f, 79.16f, 271.851f, 82.094f, 271.543f, 85.029f)
          curveTo(271.063f, 89.59f, 270.584f, 94.152f, 270.105f, 98.713f)
          curveTo(270.011f, 99.614f, 269.881f, 100.519f, 269.821f, 101.423f)
          curveTo(269.743f, 102.583f, 270.611f, 103.384f, 271.697f, 103.506f)
          curveTo(272.118f, 103.553f, 272.541f, 103.596f, 272.962f, 103.64f)
          curveTo(277.27f, 104.099f, 281.577f, 104.558f, 285.885f, 105.017f)
          curveTo(287.987f, 105.241f, 290.089f, 105.464f, 292.19f, 105.688f)
          curveTo(292.83f, 105.756f, 293.47f, 105.824f, 294.109f, 105.892f)
          curveTo(294.48f, 105.932f, 294.856f, 106.0f, 295.227f, 105.932f)
          curveTo(296.214f, 105.753f, 296.715f, 104.925f, 296.813f, 103.994f)
          curveTo(296.861f, 103.545f, 296.908f, 103.094f, 296.956f, 102.644f)
          curveTo(297.151f, 100.796f, 297.346f, 98.949f, 297.541f, 97.101f)
          curveTo(298.037f, 92.398f, 298.533f, 87.694f, 299.029f, 82.991f)
          curveTo(299.163f, 81.725f, 299.296f, 80.46f, 299.43f, 79.195f)
          curveTo(299.523f, 78.306f, 299.153f, 77.441f, 298.296f, 77.072f)
          curveTo(297.923f, 76.911f, 297.496f, 76.897f, 297.095f, 76.854f)
          curveTo(293.142f, 76.434f, 289.189f, 76.012f, 285.237f, 75.592f)
          curveTo(281.713f, 75.217f, 278.191f, 74.816f, 274.665f, 74.466f)
          curveTo(273.626f, 74.363f, 272.646f, 75.009f, 272.486f, 76.086f)
          curveTo(272.387f, 76.752f, 273.401f, 77.039f, 273.501f, 76.367f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(291.013f, 123.607f)
          curveTo(290.923f, 122.838f, 291.471f, 122.142f, 292.236f, 122.052f)
          lineTo(315.366f, 119.337f)
          curveTo(316.131f, 119.247f, 316.824f, 119.797f, 316.913f, 120.566f)
          lineTo(319.841f, 145.732f)
          curveTo(319.93f, 146.501f, 319.382f, 147.197f, 318.617f, 147.287f)
          lineTo(295.5f, 150.001f)
          curveTo(294.735f, 150.091f, 294.042f, 149.541f, 293.952f, 148.772f)
          lineTo(291.013f, 123.607f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(291.539f, 123.607f)
          curveTo(291.484f, 122.854f, 291.989f, 122.61f, 292.585f, 122.54f)
          curveTo(293.115f, 122.478f, 293.644f, 122.416f, 294.173f, 122.354f)
          curveTo(296.1f, 122.127f, 298.028f, 121.901f, 299.955f, 121.675f)
          curveTo(304.341f, 121.16f, 308.728f, 120.645f, 313.114f, 120.13f)
          curveTo(313.693f, 120.062f, 314.271f, 119.994f, 314.849f, 119.926f)
          curveTo(315.221f, 119.882f, 315.654f, 119.778f, 315.989f, 119.989f)
          curveTo(316.398f, 120.247f, 316.392f, 120.605f, 316.442f, 121.035f)
          curveTo(316.505f, 121.583f, 316.569f, 122.132f, 316.633f, 122.68f)
          curveTo(317.133f, 126.981f, 317.633f, 131.281f, 318.133f, 135.582f)
          curveTo(318.52f, 138.908f, 318.925f, 142.232f, 319.294f, 145.559f)
          curveTo(319.357f, 146.124f, 319.154f, 146.658f, 318.589f, 146.761f)
          curveTo(318.27f, 146.82f, 317.937f, 146.838f, 317.614f, 146.876f)
          curveTo(315.929f, 147.074f, 314.244f, 147.272f, 312.559f, 147.469f)
          curveTo(308.022f, 148.002f, 303.486f, 148.535f, 298.949f, 149.067f)
          curveTo(297.828f, 149.199f, 296.707f, 149.344f, 295.583f, 149.463f)
          curveTo(294.367f, 149.591f, 294.439f, 148.437f, 294.338f, 147.574f)
          curveTo(293.868f, 143.547f, 293.398f, 139.519f, 292.927f, 135.491f)
          curveTo(292.489f, 131.743f, 292.052f, 127.994f, 291.614f, 124.245f)
          curveTo(291.589f, 124.032f, 291.564f, 123.819f, 291.539f, 123.607f)
          curveTo(291.461f, 122.939f, 290.408f, 122.931f, 290.486f, 123.607f)
          curveTo(290.829f, 126.538f, 291.171f, 129.468f, 291.513f, 132.398f)
          curveTo(292.045f, 136.954f, 292.577f, 141.509f, 293.109f, 146.065f)
          curveTo(293.215f, 146.966f, 293.309f, 147.87f, 293.425f, 148.77f)
          curveTo(293.568f, 149.884f, 294.492f, 150.637f, 295.608f, 150.517f)
          curveTo(296.03f, 150.471f, 296.451f, 150.418f, 296.873f, 150.368f)
          curveTo(301.189f, 149.861f, 305.506f, 149.354f, 309.822f, 148.848f)
          curveTo(311.926f, 148.601f, 314.03f, 148.354f, 316.134f, 148.107f)
          curveTo(316.765f, 148.033f, 317.395f, 147.959f, 318.025f, 147.884f)
          curveTo(318.425f, 147.838f, 318.858f, 147.82f, 319.228f, 147.657f)
          curveTo(320.73f, 146.996f, 320.331f, 145.415f, 320.183f, 144.149f)
          curveTo(319.967f, 142.292f, 319.751f, 140.435f, 319.535f, 138.577f)
          curveTo(318.989f, 133.884f, 318.443f, 129.191f, 317.897f, 124.498f)
          curveTo(317.752f, 123.253f, 317.608f, 122.008f, 317.463f, 120.764f)
          curveTo(317.357f, 119.852f, 316.884f, 119.037f, 315.918f, 118.841f)
          curveTo(315.529f, 118.762f, 315.148f, 118.833f, 314.761f, 118.879f)
          curveTo(310.804f, 119.343f, 306.846f, 119.808f, 302.889f, 120.272f)
          curveTo(299.379f, 120.685f, 295.868f, 121.086f, 292.359f, 121.509f)
          curveTo(291.269f, 121.64f, 290.4f, 122.438f, 290.486f, 123.607f)
          curveTo(290.536f, 124.282f, 291.589f, 124.287f, 291.539f, 123.607f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(33.313f, 98.544f)
          curveTo(35.159f, 98.249f, 37.005f, 97.954f, 38.851f, 97.66f)
          curveTo(41.809f, 97.188f, 44.768f, 96.716f, 47.726f, 96.244f)
          curveTo(48.401f, 96.136f, 49.076f, 96.029f, 49.751f, 95.921f)
          curveTo(50.418f, 95.815f, 50.135f, 94.795f, 49.471f, 94.901f)
          curveTo(47.625f, 95.196f, 45.779f, 95.49f, 43.933f, 95.785f)
          curveTo(40.974f, 96.257f, 38.016f, 96.729f, 35.057f, 97.201f)
          curveTo(34.383f, 97.308f, 33.708f, 97.416f, 33.033f, 97.524f)
          curveTo(32.365f, 97.63f, 32.649f, 98.65f, 33.313f, 98.544f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(34.018f, 103.255f)
          curveTo(35.704f, 102.945f, 37.391f, 102.635f, 39.077f, 102.324f)
          curveTo(41.782f, 101.827f, 44.487f, 101.329f, 47.191f, 100.832f)
          curveTo(47.809f, 100.718f, 48.427f, 100.604f, 49.045f, 100.491f)
          curveTo(49.71f, 100.368f, 49.428f, 99.349f, 48.765f, 99.471f)
          curveTo(47.078f, 99.781f, 45.392f, 100.091f, 43.706f, 100.402f)
          curveTo(41.001f, 100.899f, 38.296f, 101.397f, 35.592f, 101.894f)
          curveTo(34.974f, 102.008f, 34.356f, 102.121f, 33.738f, 102.235f)
          curveTo(33.073f, 102.357f, 33.354f, 103.377f, 34.018f, 103.255f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(33.238f, 136.122f)
          curveTo(33.958f, 135.248f, 35.055f, 134.0f, 36.282f, 134.377f)
          curveTo(37.162f, 134.647f, 37.874f, 135.475f, 38.606f, 136.024f)
          curveTo(39.006f, 136.325f, 39.443f, 136.588f, 39.926f, 136.728f)
          curveTo(40.492f, 136.892f, 41.003f, 136.766f, 41.574f, 136.764f)
          curveTo(43.006f, 136.76f, 44.416f, 137.472f, 45.627f, 138.176f)
          curveTo(46.217f, 138.52f, 46.789f, 138.895f, 47.343f, 139.295f)
          curveTo(47.446f, 139.369f, 47.549f, 139.444f, 47.651f, 139.521f)
          curveTo(47.683f, 139.545f, 47.909f, 139.718f, 47.811f, 139.641f)
          curveTo(47.873f, 139.69f, 47.936f, 139.739f, 47.997f, 139.79f)
          curveTo(48.518f, 140.217f, 49.267f, 139.473f, 48.742f, 139.042f)
          curveTo(48.051f, 138.475f, 47.308f, 137.964f, 46.548f, 137.496f)
          curveTo(45.213f, 136.676f, 43.729f, 135.933f, 42.157f, 135.744f)
          curveTo(41.575f, 135.674f, 40.944f, 135.853f, 40.4f, 135.755f)
          curveTo(39.929f, 135.671f, 39.516f, 135.398f, 39.137f, 135.11f)
          curveTo(38.313f, 134.484f, 37.579f, 133.669f, 36.563f, 133.356f)
          curveTo(34.897f, 132.845f, 33.468f, 134.19f, 32.494f, 135.373f)
          curveTo(32.065f, 135.894f, 32.806f, 136.647f, 33.238f, 136.122f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(31.669f, 139.766f)
          curveTo(33.643f, 140.196f, 35.617f, 140.627f, 37.591f, 141.056f)
          curveTo(40.742f, 141.743f, 43.894f, 142.43f, 47.045f, 143.116f)
          curveTo(47.775f, 143.275f, 48.506f, 143.434f, 49.235f, 143.594f)
          curveTo(49.896f, 143.738f, 50.178f, 142.718f, 49.515f, 142.574f)
          curveTo(47.541f, 142.144f, 45.568f, 141.713f, 43.594f, 141.284f)
          curveTo(40.442f, 140.597f, 37.291f, 139.91f, 34.14f, 139.224f)
          curveTo(33.409f, 139.065f, 32.679f, 138.906f, 31.949f, 138.746f)
          curveTo(31.288f, 138.602f, 31.006f, 139.622f, 31.669f, 139.766f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(31.088f, 143.647f)
          curveTo(35.221f, 144.515f, 39.354f, 145.382f, 43.487f, 146.25f)
          curveTo(44.081f, 146.375f, 44.675f, 146.499f, 45.27f, 146.624f)
          curveTo(45.931f, 146.763f, 46.213f, 145.744f, 45.55f, 145.604f)
          curveTo(41.417f, 144.737f, 37.284f, 143.869f, 33.152f, 143.001f)
          curveTo(32.557f, 142.877f, 31.963f, 142.752f, 31.368f, 142.627f)
          curveTo(30.707f, 142.488f, 30.424f, 143.508f, 31.088f, 143.647f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(74.337f, 88.559f)
          curveTo(74.589f, 88.229f, 74.973f, 87.829f, 75.301f, 87.716f)
          curveTo(75.595f, 87.615f, 75.788f, 87.65f, 76.147f, 87.771f)
          curveTo(76.936f, 88.037f, 77.745f, 88.53f, 78.624f, 88.716f)
          curveTo(79.244f, 88.848f, 79.879f, 88.879f, 80.508f, 88.804f)
          curveTo(81.41f, 88.698f, 82.221f, 88.366f, 83.085f, 88.107f)
          curveTo(84.811f, 87.59f, 86.634f, 87.432f, 88.424f, 87.655f)
          curveTo(89.044f, 87.732f, 89.661f, 87.862f, 90.26f, 88.039f)
          curveTo(90.446f, 88.094f, 90.631f, 88.154f, 90.812f, 88.221f)
          curveTo(90.88f, 88.246f, 91.136f, 88.355f, 90.902f, 88.253f)
          curveTo(91.517f, 88.521f, 92.054f, 87.61f, 91.433f, 87.339f)
          curveTo(89.082f, 86.312f, 86.207f, 86.293f, 83.747f, 86.842f)
          curveTo(82.125f, 87.203f, 80.608f, 88.058f, 78.904f, 87.697f)
          curveTo(77.97f, 87.499f, 77.18f, 86.956f, 76.271f, 86.702f)
          curveTo(75.043f, 86.36f, 74.142f, 87.091f, 73.429f, 88.025f)
          curveTo(73.016f, 88.564f, 73.93f, 89.092f, 74.337f, 88.559f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(73.883f, 93.003f)
          curveTo(75.93f, 92.914f, 77.976f, 92.827f, 80.023f, 92.739f)
          curveTo(83.269f, 92.599f, 86.516f, 92.459f, 89.763f, 92.32f)
          curveTo(90.513f, 92.287f, 91.264f, 92.255f, 92.014f, 92.223f)
          curveTo(92.689f, 92.194f, 92.693f, 91.136f, 92.014f, 91.165f)
          curveTo(89.967f, 91.253f, 87.921f, 91.341f, 85.874f, 91.429f)
          curveTo(82.628f, 91.569f, 79.381f, 91.708f, 76.134f, 91.848f)
          curveTo(75.384f, 91.88f, 74.633f, 91.912f, 73.883f, 91.945f)
          curveTo(73.208f, 91.974f, 73.205f, 93.032f, 73.883f, 93.003f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(74.3f, 97.187f)
          curveTo(77.509f, 97.157f, 80.719f, 97.126f, 83.928f, 97.096f)
          curveTo(84.391f, 97.091f, 84.854f, 97.087f, 85.318f, 97.082f)
          curveTo(85.995f, 97.076f, 85.997f, 96.018f, 85.318f, 96.024f)
          curveTo(82.109f, 96.055f, 78.9f, 96.085f, 75.691f, 96.116f)
          curveTo(75.227f, 96.121f, 74.764f, 96.125f, 74.3f, 96.13f)
          curveTo(73.623f, 96.136f, 73.621f, 97.194f, 74.3f, 97.187f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(76.2f, 132.824f)
          curveTo(78.03f, 132.233f, 79.859f, 131.641f, 81.689f, 131.05f)
          curveTo(84.602f, 130.108f, 87.516f, 129.167f, 90.43f, 128.224f)
          curveTo(91.095f, 128.009f, 91.761f, 127.794f, 92.426f, 127.579f)
          curveTo(93.069f, 127.371f, 92.794f, 126.349f, 92.146f, 126.559f)
          curveTo(90.317f, 127.15f, 88.488f, 127.742f, 86.658f, 128.333f)
          curveTo(83.744f, 129.275f, 80.831f, 130.217f, 77.917f, 131.159f)
          curveTo(77.251f, 131.374f, 76.586f, 131.589f, 75.92f, 131.804f)
          curveTo(75.278f, 132.012f, 75.552f, 133.034f, 76.2f, 132.824f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(76.976f, 136.898f)
          curveTo(78.913f, 136.313f, 80.85f, 135.729f, 82.786f, 135.144f)
          curveTo(85.857f, 134.218f, 88.927f, 133.291f, 91.998f, 132.364f)
          curveTo(92.705f, 132.151f, 93.412f, 131.937f, 94.119f, 131.724f)
          curveTo(94.767f, 131.528f, 94.491f, 130.507f, 93.839f, 130.704f)
          curveTo(91.902f, 131.288f, 89.966f, 131.873f, 88.029f, 132.457f)
          curveTo(84.959f, 133.384f, 81.888f, 134.311f, 78.818f, 135.238f)
          curveTo(78.111f, 135.451f, 77.403f, 135.665f, 76.696f, 135.878f)
          curveTo(76.049f, 136.073f, 76.325f, 137.095f, 76.976f, 136.898f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(78.316f, 140.762f)
          curveTo(82.22f, 139.473f, 86.168f, 138.31f, 90.092f, 137.087f)
          curveTo(90.635f, 136.918f, 91.178f, 136.749f, 91.721f, 136.58f)
          curveTo(92.366f, 136.379f, 92.091f, 135.357f, 91.441f, 135.56f)
          curveTo(87.551f, 136.771f, 83.662f, 137.983f, 79.774f, 139.198f)
          curveTo(79.195f, 139.378f, 78.613f, 139.552f, 78.036f, 139.742f)
          curveTo(77.396f, 139.953f, 77.67f, 140.975f, 78.316f, 140.762f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(62.921f, 56.126f)
          curveTo(64.494f, 55.901f, 66.068f, 55.676f, 67.642f, 55.451f)
          curveTo(67.866f, 55.419f, 68.089f, 55.387f, 68.312f, 55.355f)
          curveTo(68.594f, 55.315f, 68.749f, 54.955f, 68.68f, 54.705f)
          curveTo(68.597f, 54.401f, 68.315f, 54.295f, 68.033f, 54.335f)
          curveTo(66.459f, 54.56f, 64.885f, 54.785f, 63.311f, 55.01f)
          curveTo(63.088f, 55.042f, 62.865f, 55.074f, 62.641f, 55.106f)
          curveTo(62.359f, 55.146f, 62.205f, 55.506f, 62.273f, 55.757f)
          curveTo(62.356f, 56.061f, 62.638f, 56.166f, 62.921f, 56.126f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(50.361f, 57.975f)
          curveTo(53.443f, 57.515f, 56.526f, 57.055f, 59.609f, 56.594f)
          curveTo(60.05f, 56.528f, 60.492f, 56.462f, 60.933f, 56.396f)
          curveTo(61.601f, 56.296f, 61.317f, 55.277f, 60.653f, 55.376f)
          curveTo(57.57f, 55.837f, 54.488f, 56.297f, 51.405f, 56.758f)
          curveTo(50.964f, 56.824f, 50.522f, 56.889f, 50.081f, 56.955f)
          curveTo(49.413f, 57.055f, 49.697f, 58.075f, 50.361f, 57.975f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(50.807f, 61.777f)
          curveTo(53.186f, 61.425f, 55.564f, 61.073f, 57.943f, 60.722f)
          curveTo(61.731f, 60.161f, 65.518f, 59.601f, 69.307f, 59.041f)
          curveTo(70.172f, 58.913f, 71.037f, 58.785f, 71.902f, 58.657f)
          curveTo(72.57f, 58.558f, 72.286f, 57.539f, 71.622f, 57.637f)
          curveTo(69.244f, 57.989f, 66.865f, 58.34f, 64.487f, 58.692f)
          curveTo(60.699f, 59.253f, 56.911f, 59.813f, 53.123f, 60.373f)
          curveTo(52.258f, 60.501f, 51.393f, 60.629f, 50.527f, 60.757f)
          curveTo(49.86f, 60.856f, 50.144f, 61.875f, 50.807f, 61.777f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(51.254f, 65.259f)
          curveTo(55.393f, 64.669f, 59.533f, 64.079f, 63.672f, 63.489f)
          curveTo(64.259f, 63.405f, 64.847f, 63.321f, 65.434f, 63.237f)
          curveTo(66.103f, 63.142f, 65.818f, 62.123f, 65.154f, 62.217f)
          curveTo(61.015f, 62.807f, 56.876f, 63.397f, 52.737f, 63.987f)
          curveTo(52.15f, 64.071f, 51.562f, 64.155f, 50.974f, 64.239f)
          curveTo(50.306f, 64.334f, 50.591f, 65.353f, 51.254f, 65.259f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(50.878f, 54.048f)
          curveTo(52.209f, 52.066f, 53.54f, 50.085f, 54.871f, 48.103f)
          curveTo(54.631f, 48.166f, 54.391f, 48.23f, 54.151f, 48.293f)
          curveTo(55.794f, 49.431f, 57.437f, 50.569f, 59.08f, 51.707f)
          curveTo(59.307f, 51.864f, 59.676f, 51.757f, 59.8f, 51.518f)
          curveTo(60.229f, 50.694f, 60.787f, 49.931f, 61.613f, 49.477f)
          curveTo(62.324f, 49.085f, 63.144f, 49.006f, 63.939f, 49.087f)
          curveTo(66.312f, 49.329f, 68.56f, 50.753f, 70.521f, 52.009f)
          curveTo(71.094f, 52.376f, 71.622f, 51.461f, 71.052f, 51.096f)
          curveTo(68.875f, 49.7f, 66.385f, 48.188f, 63.741f, 48.013f)
          curveTo(61.563f, 47.869f, 59.876f, 49.094f, 58.892f, 50.984f)
          curveTo(59.132f, 50.921f, 59.372f, 50.858f, 59.612f, 50.794f)
          curveTo(57.969f, 49.656f, 56.326f, 48.518f, 54.683f, 47.38f)
          curveTo(54.438f, 47.21f, 54.112f, 47.347f, 53.963f, 47.57f)
          curveTo(52.631f, 49.551f, 51.3f, 51.533f, 49.969f, 53.514f)
          curveTo(49.589f, 54.079f, 50.501f, 54.609f, 50.878f, 54.048f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(102.16f, 17.774f)
          curveTo(104.441f, 18.701f, 106.722f, 19.626f, 109.004f, 20.553f)
          curveTo(112.637f, 22.028f, 116.271f, 23.503f, 119.905f, 24.978f)
          curveTo(120.735f, 25.315f, 121.566f, 25.653f, 122.396f, 25.99f)
          curveTo(123.024f, 26.245f, 123.298f, 25.222f, 122.677f, 24.97f)
          curveTo(120.395f, 24.044f, 118.114f, 23.118f, 115.832f, 22.191f)
          curveTo(112.199f, 20.716f, 108.565f, 19.241f, 104.931f, 17.766f)
          curveTo(104.101f, 17.429f, 103.27f, 17.091f, 102.44f, 16.754f)
          curveTo(101.811f, 16.499f, 101.538f, 17.522f, 102.16f, 17.774f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(109.144f, 24.164f)
          curveTo(110.825f, 24.859f, 112.507f, 25.553f, 114.188f, 26.248f)
          curveTo(114.428f, 26.347f, 114.667f, 26.446f, 114.907f, 26.545f)
          curveTo(115.17f, 26.654f, 115.486f, 26.427f, 115.554f, 26.176f)
          curveTo(115.638f, 25.872f, 115.451f, 25.634f, 115.187f, 25.525f)
          curveTo(113.505f, 24.83f, 111.824f, 24.136f, 110.143f, 23.441f)
          curveTo(109.903f, 23.342f, 109.664f, 23.243f, 109.424f, 23.144f)
          curveTo(109.161f, 23.035f, 108.845f, 23.262f, 108.776f, 23.514f)
          curveTo(108.693f, 23.817f, 108.88f, 24.055f, 109.144f, 24.164f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(101.098f, 20.767f)
          curveTo(102.906f, 21.529f, 104.714f, 22.291f, 106.522f, 23.053f)
          curveTo(106.78f, 23.161f, 107.038f, 23.27f, 107.296f, 23.378f)
          curveTo(107.558f, 23.489f, 107.875f, 23.259f, 107.943f, 23.009f)
          curveTo(108.027f, 22.704f, 107.839f, 22.469f, 107.575f, 22.358f)
          curveTo(105.767f, 21.597f, 103.96f, 20.834f, 102.152f, 20.073f)
          curveTo(101.894f, 19.964f, 101.636f, 19.855f, 101.378f, 19.747f)
          curveTo(101.116f, 19.636f, 100.799f, 19.866f, 100.731f, 20.116f)
          curveTo(100.647f, 20.421f, 100.835f, 20.656f, 101.098f, 20.767f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(104.614f, 14.018f)
          curveTo(106.766f, 12.998f, 108.917f, 11.978f, 111.069f, 10.958f)
          curveTo(110.829f, 10.895f, 110.589f, 10.832f, 110.349f, 10.769f)
          curveTo(111.183f, 12.59f, 112.016f, 14.412f, 112.85f, 16.234f)
          curveTo(112.968f, 16.493f, 113.344f, 16.558f, 113.57f, 16.424f)
          curveTo(114.892f, 15.642f, 116.656f, 14.651f, 118.234f, 15.171f)
          curveTo(119.636f, 15.632f, 120.433f, 17.259f, 121.013f, 18.508f)
          curveTo(121.576f, 19.72f, 122.03f, 20.982f, 122.477f, 22.24f)
          curveTo(122.704f, 22.876f, 123.722f, 22.602f, 123.493f, 21.959f)
          curveTo(122.732f, 19.82f, 121.988f, 17.482f, 120.567f, 15.677f)
          curveTo(119.522f, 14.35f, 118.072f, 13.76f, 116.408f, 14.075f)
          curveTo(115.208f, 14.302f, 114.08f, 14.894f, 113.038f, 15.511f)
          curveTo(113.279f, 15.574f, 113.519f, 15.637f, 113.759f, 15.7f)
          curveTo(112.925f, 13.878f, 112.092f, 12.057f, 111.258f, 10.235f)
          curveTo(111.132f, 9.96f, 110.774f, 9.933f, 110.538f, 10.045f)
          curveTo(108.386f, 11.065f, 106.234f, 12.085f, 104.082f, 13.105f)
          curveTo(103.47f, 13.395f, 104.004f, 14.307f, 104.614f, 14.018f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(224.663f, 136.689f)
          curveTo(226.437f, 137.184f, 228.212f, 137.679f, 229.986f, 138.174f)
          curveTo(232.799f, 138.96f, 235.613f, 139.745f, 238.426f, 140.53f)
          curveTo(239.078f, 140.712f, 239.729f, 140.894f, 240.381f, 141.076f)
          curveTo(241.035f, 141.258f, 241.314f, 140.238f, 240.66f, 140.055f)
          curveTo(238.886f, 139.56f, 237.112f, 139.065f, 235.338f, 138.57f)
          curveTo(232.525f, 137.785f, 229.711f, 137.0f, 226.897f, 136.214f)
          curveTo(226.246f, 136.032f, 225.594f, 135.851f, 224.943f, 135.669f)
          curveTo(224.289f, 135.486f, 224.01f, 136.507f, 224.663f, 136.689f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(223.521f, 140.131f)
          curveTo(227.38f, 141.174f, 231.238f, 142.217f, 235.097f, 143.259f)
          curveTo(235.649f, 143.409f, 236.201f, 143.558f, 236.753f, 143.707f)
          curveTo(237.409f, 143.884f, 237.688f, 142.864f, 237.033f, 142.687f)
          curveTo(233.175f, 141.644f, 229.316f, 140.601f, 225.458f, 139.558f)
          curveTo(224.906f, 139.409f, 224.353f, 139.26f, 223.801f, 139.11f)
          curveTo(223.146f, 138.933f, 222.867f, 139.954f, 223.521f, 140.131f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(261.896f, 134.951f)
          curveTo(264.118f, 134.395f, 266.339f, 133.839f, 268.562f, 133.282f)
          curveTo(272.108f, 132.395f, 275.655f, 131.507f, 279.201f, 130.619f)
          curveTo(280.022f, 130.413f, 280.842f, 130.207f, 281.663f, 130.002f)
          curveTo(282.32f, 129.838f, 282.042f, 128.818f, 281.383f, 128.982f)
          curveTo(279.162f, 129.539f, 276.94f, 130.095f, 274.718f, 130.651f)
          curveTo(271.171f, 131.539f, 267.625f, 132.427f, 264.078f, 133.315f)
          curveTo(263.257f, 133.52f, 262.437f, 133.726f, 261.616f, 133.931f)
          curveTo(260.959f, 134.096f, 261.237f, 135.116f, 261.896f, 134.951f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(262.848f, 137.917f)
          curveTo(264.515f, 137.489f, 266.181f, 137.061f, 267.847f, 136.632f)
          curveTo(270.489f, 135.954f, 273.13f, 135.276f, 275.771f, 134.598f)
          curveTo(276.382f, 134.441f, 276.993f, 134.284f, 277.604f, 134.127f)
          curveTo(278.26f, 133.958f, 277.982f, 132.938f, 277.324f, 133.107f)
          curveTo(275.657f, 133.535f, 273.991f, 133.963f, 272.324f, 134.391f)
          curveTo(269.683f, 135.069f, 267.042f, 135.748f, 264.401f, 136.426f)
          curveTo(263.79f, 136.583f, 263.179f, 136.74f, 262.568f, 136.897f)
          curveTo(261.912f, 137.065f, 262.19f, 138.086f, 262.848f, 137.917f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(261.899f, 131.81f)
          curveTo(262.337f, 130.726f, 262.81f, 129.639f, 263.3f, 128.554f)
          curveTo(263.6f, 127.892f, 263.903f, 127.229f, 264.247f, 126.588f)
          curveTo(264.358f, 126.382f, 264.476f, 126.184f, 264.605f, 125.989f)
          curveTo(264.537f, 126.092f, 264.585f, 125.988f, 264.606f, 125.993f)
          curveTo(264.612f, 125.994f, 264.634f, 125.961f, 264.643f, 125.957f)
          curveTo(264.599f, 125.976f, 264.572f, 126.017f, 264.524f, 126.034f)
          curveTo(264.529f, 126.033f, 264.456f, 126.048f, 264.494f, 126.048f)
          curveTo(264.394f, 126.047f, 264.412f, 126.003f, 264.45f, 126.048f)
          curveTo(264.492f, 126.095f, 264.352f, 125.991f, 264.448f, 126.048f)
          curveTo(264.525f, 126.094f, 264.598f, 126.142f, 264.67f, 126.195f)
          curveTo(264.794f, 126.286f, 265.127f, 126.585f, 265.32f, 126.757f)
          curveTo(266.168f, 127.518f, 267.006f, 128.499f, 268.048f, 128.985f)
          curveTo(270.169f, 129.972f, 271.534f, 127.411f, 273.069f, 126.434f)
          curveTo(275.491f, 124.892f, 278.579f, 126.111f, 280.857f, 127.331f)
          curveTo(281.454f, 127.651f, 281.987f, 126.738f, 281.388f, 126.418f)
          curveTo(279.162f, 125.225f, 276.42f, 124.138f, 273.877f, 124.911f)
          curveTo(272.709f, 125.266f, 271.799f, 126.026f, 270.925f, 126.854f)
          curveTo(270.366f, 127.384f, 269.588f, 128.403f, 268.705f, 128.115f)
          curveTo(268.076f, 127.91f, 267.582f, 127.378f, 267.102f, 126.946f)
          curveTo(266.71f, 126.594f, 266.321f, 126.237f, 265.927f, 125.886f)
          curveTo(265.566f, 125.565f, 265.158f, 125.127f, 264.669f, 125.013f)
          curveTo(264.175f, 124.898f, 263.846f, 125.214f, 263.599f, 125.6f)
          curveTo(263.247f, 126.15f, 262.975f, 126.759f, 262.699f, 127.35f)
          curveTo(262.057f, 128.725f, 261.453f, 130.121f, 260.884f, 131.529f)
          curveTo(260.629f, 132.159f, 261.647f, 132.433f, 261.899f, 131.81f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(295.566f, 138.271f)
          curveTo(297.845f, 138.025f, 300.125f, 137.779f, 302.405f, 137.533f)
          curveTo(306.021f, 137.143f, 309.637f, 136.753f, 313.253f, 136.363f)
          curveTo(314.086f, 136.273f, 314.918f, 136.183f, 315.751f, 136.093f)
          curveTo(316.417f, 136.021f, 316.424f, 134.963f, 315.751f, 135.036f)
          curveTo(313.471f, 135.282f, 311.192f, 135.527f, 308.912f, 135.774f)
          curveTo(305.296f, 136.164f, 301.68f, 136.554f, 298.063f, 136.944f)
          curveTo(297.231f, 137.034f, 296.399f, 137.124f, 295.566f, 137.214f)
          curveTo(294.899f, 137.285f, 294.892f, 138.344f, 295.566f, 138.271f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(295.886f, 140.992f)
          curveTo(298.166f, 140.746f, 300.445f, 140.5f, 302.725f, 140.254f)
          curveTo(306.341f, 139.864f, 309.957f, 139.474f, 313.573f, 139.084f)
          curveTo(314.406f, 138.994f, 315.238f, 138.904f, 316.071f, 138.814f)
          curveTo(316.738f, 138.742f, 316.745f, 137.684f, 316.071f, 137.757f)
          curveTo(313.791f, 138.003f, 311.512f, 138.248f, 309.232f, 138.495f)
          curveTo(305.616f, 138.885f, 302.0f, 139.275f, 298.383f, 139.665f)
          curveTo(297.551f, 139.755f, 296.719f, 139.845f, 295.886f, 139.935f)
          curveTo(295.219f, 140.006f, 295.212f, 141.065f, 295.886f, 140.992f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(295.926f, 134.609f)
          curveTo(296.52f, 133.536f, 297.144f, 132.478f, 297.781f, 131.431f)
          curveTo(298.165f, 130.801f, 298.552f, 130.17f, 298.98f, 129.569f)
          curveTo(299.118f, 129.375f, 299.205f, 129.302f, 299.357f, 129.101f)
          curveTo(299.424f, 129.013f, 299.282f, 129.135f, 299.284f, 129.134f)
          curveTo(299.359f, 129.108f, 299.148f, 129.127f, 299.244f, 129.142f)
          curveTo(299.175f, 129.131f, 299.179f, 129.165f, 299.206f, 129.141f)
          curveTo(299.193f, 129.152f, 299.078f, 129.077f, 299.177f, 129.132f)
          curveTo(299.192f, 129.14f, 299.294f, 129.211f, 299.233f, 129.166f)
          curveTo(299.173f, 129.122f, 299.273f, 129.202f, 299.288f, 129.217f)
          curveTo(299.337f, 129.263f, 299.385f, 129.309f, 299.432f, 129.357f)
          curveTo(299.669f, 129.598f, 299.888f, 129.856f, 300.109f, 130.111f)
          curveTo(300.847f, 130.961f, 301.565f, 132.06f, 302.576f, 132.601f)
          curveTo(303.379f, 133.03f, 304.201f, 132.885f, 304.956f, 132.428f)
          curveTo(305.882f, 131.866f, 306.672f, 131.098f, 307.645f, 130.607f)
          curveTo(310.246f, 129.294f, 313.251f, 130.969f, 315.372f, 132.467f)
          curveTo(315.927f, 132.859f, 316.454f, 131.942f, 315.903f, 131.553f)
          curveTo(313.804f, 130.072f, 311.211f, 128.705f, 308.564f, 129.194f)
          curveTo(307.383f, 129.412f, 306.402f, 130.083f, 305.454f, 130.787f)
          curveTo(304.878f, 131.214f, 304.032f, 132.054f, 303.242f, 131.75f)
          curveTo(302.723f, 131.551f, 302.335f, 131.067f, 301.98f, 130.662f)
          curveTo(301.581f, 130.206f, 301.185f, 129.745f, 300.787f, 129.288f)
          curveTo(300.455f, 128.906f, 300.101f, 128.41f, 299.634f, 128.183f)
          curveTo(299.189f, 127.966f, 298.78f, 128.146f, 298.477f, 128.5f)
          curveTo(298.043f, 129.008f, 297.685f, 129.591f, 297.33f, 130.156f)
          curveTo(296.522f, 131.438f, 295.751f, 132.748f, 295.016f, 134.074f)
          curveTo(294.687f, 134.67f, 295.596f, 135.204f, 295.926f, 134.609f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(296.206f, 143.713f)
          curveTo(300.337f, 143.286f, 304.467f, 142.859f, 308.598f, 142.433f)
          curveTo(309.189f, 142.372f, 309.781f, 142.31f, 310.372f, 142.249f)
          curveTo(311.04f, 142.18f, 311.047f, 141.122f, 310.372f, 141.192f)
          curveTo(306.242f, 141.618f, 302.111f, 142.045f, 297.98f, 142.472f)
          curveTo(297.389f, 142.533f, 296.797f, 142.594f, 296.206f, 142.655f)
          curveTo(295.538f, 142.724f, 295.531f, 143.782f, 296.206f, 143.713f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(218.161f, 35.988f)
          curveTo(223.788f, 34.574f, 229.343f, 32.889f, 234.931f, 31.332f)
          curveTo(235.583f, 31.15f, 235.306f, 30.129f, 234.651f, 30.312f)
          curveTo(229.063f, 31.869f, 223.508f, 33.554f, 217.881f, 34.968f)
          curveTo(217.224f, 35.133f, 217.503f, 36.154f, 218.161f, 35.988f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(219.463f, 39.487f)
          curveTo(224.105f, 38.302f, 228.717f, 36.998f, 233.339f, 35.735f)
          curveTo(233.993f, 35.556f, 233.715f, 34.536f, 233.06f, 34.715f)
          curveTo(228.437f, 35.977f, 223.826f, 37.282f, 219.183f, 38.467f)
          curveTo(218.527f, 38.635f, 218.805f, 39.655f, 219.463f, 39.487f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(222.157f, 46.203f)
          curveTo(223.235f, 45.131f, 224.415f, 44.257f, 225.712f, 43.482f)
          curveTo(225.666f, 43.46f, 225.619f, 43.438f, 225.573f, 43.417f)
          curveTo(225.593f, 43.491f, 225.61f, 43.565f, 225.623f, 43.64f)
          curveTo(225.66f, 43.797f, 225.699f, 43.955f, 225.782f, 44.096f)
          curveTo(225.93f, 44.348f, 226.162f, 44.536f, 226.425f, 44.654f)
          curveTo(227.054f, 44.936f, 227.707f, 44.746f, 228.293f, 44.452f)
          curveTo(230.936f, 43.124f, 233.669f, 41.132f, 236.778f, 42.14f)
          curveTo(237.425f, 42.35f, 237.701f, 41.329f, 237.058f, 41.12f)
          curveTo(233.859f, 40.083f, 230.813f, 41.85f, 228.098f, 43.355f)
          curveTo(227.79f, 43.526f, 227.419f, 43.775f, 227.05f, 43.742f)
          curveTo(226.685f, 43.708f, 226.687f, 43.483f, 226.602f, 43.19f)
          curveTo(226.401f, 42.492f, 225.763f, 42.273f, 225.13f, 42.581f)
          curveTo(224.453f, 42.908f, 223.823f, 43.402f, 223.23f, 43.86f)
          curveTo(222.591f, 44.354f, 221.986f, 44.885f, 221.413f, 45.454f)
          curveTo(220.931f, 45.934f, 221.675f, 46.683f, 222.157f, 46.203f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(243.468f, 85.228f)
          curveTo(248.655f, 87.02f, 253.886f, 88.674f, 259.045f, 90.545f)
          curveTo(259.683f, 90.776f, 259.959f, 89.755f, 259.325f, 89.525f)
          curveTo(254.166f, 87.654f, 248.934f, 85.999f, 243.748f, 84.208f)
          curveTo(243.106f, 83.986f, 242.83f, 85.008f, 243.468f, 85.228f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(242.065f, 88.477f)
          curveTo(246.688f, 90.216f, 251.362f, 91.815f, 256.081f, 93.271f)
          curveTo(256.73f, 93.472f, 257.007f, 92.451f, 256.361f, 92.251f)
          curveTo(251.642f, 90.795f, 246.968f, 89.196f, 242.345f, 87.457f)
          curveTo(241.709f, 87.218f, 241.435f, 88.24f, 242.065f, 88.477f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(239.711f, 97.17f)
          curveTo(240.85f, 96.329f, 242.1f, 95.609f, 243.432f, 95.123f)
          curveTo(243.619f, 95.055f, 243.62f, 95.039f, 243.745f, 95.173f)
          curveTo(243.866f, 95.303f, 243.939f, 95.521f, 244.025f, 95.676f)
          curveTo(244.217f, 96.024f, 244.409f, 96.371f, 244.601f, 96.719f)
          curveTo(244.78f, 97.042f, 244.958f, 97.365f, 245.137f, 97.687f)
          curveTo(245.306f, 97.994f, 245.45f, 98.359f, 245.689f, 98.618f)
          curveTo(246.193f, 99.165f, 246.778f, 98.851f, 247.261f, 98.472f)
          curveTo(247.713f, 98.118f, 248.247f, 97.905f, 248.818f, 97.853f)
          curveTo(249.997f, 97.744f, 251.157f, 98.179f, 252.077f, 98.903f)
          curveTo(253.258f, 99.833f, 254.093f, 101.122f, 254.906f, 102.368f)
          curveTo(255.277f, 102.935f, 256.189f, 102.405f, 255.815f, 101.834f)
          curveTo(254.337f, 99.571f, 252.599f, 97.132f, 249.703f, 96.81f)
          curveTo(249.081f, 96.741f, 248.424f, 96.788f, 247.827f, 96.985f)
          curveTo(247.552f, 97.076f, 247.278f, 97.196f, 247.029f, 97.347f)
          curveTo(246.903f, 97.424f, 246.784f, 97.511f, 246.668f, 97.602f)
          curveTo(246.463f, 97.763f, 246.46f, 97.906f, 246.334f, 97.675f)
          curveTo(245.941f, 96.952f, 245.538f, 96.234f, 245.139f, 95.515f)
          curveTo(244.823f, 94.943f, 244.5f, 94.004f, 243.722f, 93.986f)
          curveTo(243.328f, 93.978f, 242.935f, 94.182f, 242.578f, 94.331f)
          curveTo(242.18f, 94.498f, 241.788f, 94.676f, 241.404f, 94.872f)
          curveTo(240.625f, 95.271f, 239.884f, 95.737f, 239.18f, 96.257f)
          curveTo(238.64f, 96.656f, 239.165f, 97.574f, 239.711f, 97.17f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(266.989f, 50.687f)
          curveTo(266.863f, 50.713f, 266.904f, 50.696f, 267.046f, 50.685f)
          curveTo(267.179f, 50.675f, 267.312f, 50.665f, 267.445f, 50.656f)
          curveTo(267.888f, 50.626f, 268.33f, 50.6f, 268.773f, 50.575f)
          curveTo(270.152f, 50.495f, 271.531f, 50.423f, 272.91f, 50.351f)
          curveTo(275.939f, 50.194f, 278.969f, 50.043f, 281.999f, 49.895f)
          curveTo(282.685f, 49.862f, 283.373f, 49.828f, 284.059f, 49.795f)
          curveTo(284.734f, 49.763f, 284.738f, 48.704f, 284.059f, 48.737f)
          curveTo(282.144f, 48.829f, 280.229f, 48.924f, 278.314f, 49.02f)
          curveTo(275.244f, 49.173f, 272.174f, 49.325f, 269.105f, 49.498f)
          curveTo(268.325f, 49.542f, 267.479f, 49.507f, 266.709f, 49.667f)
          curveTo(266.046f, 49.806f, 266.327f, 50.825f, 266.989f, 50.687f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(267.511f, 54.251f)
          curveTo(269.403f, 54.16f, 271.296f, 54.069f, 273.188f, 53.977f)
          curveTo(276.19f, 53.832f, 279.192f, 53.687f, 282.194f, 53.542f)
          curveTo(282.889f, 53.509f, 283.585f, 53.475f, 284.28f, 53.442f)
          curveTo(284.955f, 53.409f, 284.958f, 52.351f, 284.28f, 52.384f)
          curveTo(282.388f, 52.475f, 280.495f, 52.566f, 278.603f, 52.658f)
          curveTo(275.601f, 52.803f, 272.599f, 52.948f, 269.597f, 53.093f)
          curveTo(268.902f, 53.126f, 268.206f, 53.16f, 267.511f, 53.194f)
          curveTo(266.836f, 53.226f, 266.832f, 54.284f, 267.511f, 54.251f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(267.732f, 57.693f)
          curveTo(271.079f, 57.486f, 274.426f, 57.279f, 277.774f, 57.073f)
          curveTo(278.251f, 57.043f, 278.728f, 57.014f, 279.205f, 56.984f)
          curveTo(279.879f, 56.943f, 279.883f, 55.885f, 279.205f, 55.927f)
          curveTo(275.858f, 56.133f, 272.511f, 56.34f, 269.163f, 56.547f)
          curveTo(268.686f, 56.576f, 268.209f, 56.606f, 267.732f, 56.635f)
          curveTo(267.058f, 56.677f, 267.054f, 57.735f, 267.732f, 57.693f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(276.591f, 88.237f)
          curveTo(278.407f, 88.442f, 280.224f, 88.647f, 282.041f, 88.853f)
          curveTo(284.935f, 89.18f, 287.829f, 89.507f, 290.723f, 89.834f)
          curveTo(291.386f, 89.909f, 292.049f, 89.984f, 292.711f, 90.059f)
          curveTo(293.383f, 90.134f, 293.378f, 89.076f, 292.711f, 89.001f)
          curveTo(290.895f, 88.796f, 289.078f, 88.59f, 287.261f, 88.385f)
          curveTo(284.367f, 88.058f, 281.473f, 87.73f, 278.579f, 87.404f)
          curveTo(277.916f, 87.329f, 277.253f, 87.254f, 276.591f, 87.179f)
          curveTo(275.918f, 87.103f, 275.924f, 88.161f, 276.591f, 88.237f)
          close()
        }
        path(
          fill = SolidColor(Color(0xFF606368)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(275.986f, 91.881f)
          curveTo(279.934f, 92.325f, 283.882f, 92.769f, 287.829f, 93.213f)
          curveTo(288.382f, 93.275f, 288.934f, 93.337f, 289.487f, 93.399f)
          curveTo(290.159f, 93.475f, 290.153f, 92.417f, 289.487f, 92.341f)
          curveTo(285.539f, 91.898f, 281.591f, 91.454f, 277.643f, 91.009f)
          curveTo(277.091f, 90.947f, 276.538f, 90.885f, 275.986f, 90.823f)
          curveTo(275.314f, 90.747f, 275.319f, 91.806f, 275.986f, 91.881f)
          close()
        }
        path(
          fill = SolidColor(MaterialTheme.colorScheme.secondaryContainer),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(8.788f, 177.577f)
          curveTo(14.803f, 177.593f, 20.818f, 177.6f, 26.832f, 177.607f)
          curveTo(35.808f, 177.617f, 44.784f, 177.624f, 53.76f, 177.63f)
          curveTo(65.056f, 177.638f, 76.351f, 177.645f, 87.647f, 177.651f)
          curveTo(100.436f, 177.658f, 113.225f, 177.665f, 126.015f, 177.671f)
          curveTo(139.692f, 177.679f, 153.368f, 177.687f, 167.045f, 177.695f)
          curveTo(180.825f, 177.704f, 194.604f, 177.714f, 208.383f, 177.726f)
          curveTo(221.478f, 177.737f, 234.572f, 177.75f, 247.667f, 177.766f)
          curveTo(259.473f, 177.78f, 271.28f, 177.797f, 283.087f, 177.818f)
          curveTo(292.775f, 177.836f, 302.463f, 177.857f, 312.151f, 177.887f)
          curveTo(319.085f, 177.909f, 326.02f, 177.932f, 332.954f, 177.975f)
          curveTo(334.133f, 177.983f, 335.312f, 177.991f, 336.491f, 178.0f)
          curveTo(337.169f, 178.005f, 337.17f, 176.948f, 336.491f, 176.942f)
          curveTo(330.349f, 176.894f, 324.206f, 176.871f, 318.063f, 176.849f)
          curveTo(308.987f, 176.816f, 299.911f, 176.794f, 290.835f, 176.776f)
          curveTo(279.467f, 176.752f, 268.098f, 176.735f, 256.73f, 176.719f)
          curveTo(243.896f, 176.702f, 231.062f, 176.689f, 218.228f, 176.677f)
          curveTo(204.534f, 176.664f, 190.84f, 176.654f, 177.145f, 176.644f)
          curveTo(163.376f, 176.635f, 149.608f, 176.627f, 135.839f, 176.619f)
          curveTo(122.783f, 176.612f, 109.726f, 176.605f, 96.67f, 176.598f)
          curveTo(84.929f, 176.592f, 73.188f, 176.585f, 61.447f, 176.578f)
          curveTo(51.853f, 176.571f, 42.258f, 176.565f, 32.663f, 176.555f)
          curveTo(25.851f, 176.548f, 19.038f, 176.541f, 12.225f, 176.527f)
          curveTo(11.079f, 176.524f, 9.934f, 176.522f, 8.788f, 176.519f)
          curveTo(8.111f, 176.517f, 8.11f, 177.575f, 8.788f, 177.577f)
          close()
        }
      }
      group {
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(Color(0xFF63C8FF)),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(138.337f, 78.869f)
          curveTo(110.808f, 96.242f, 109.921f, 92.847f, 135.658f, 120.395f)
          lineTo(126.773f, 131.2f)
        }
        path(
          fill = SolidColor(Color(0xFF36A0DA)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(197.337f, 166.894f)
          lineTo(167.337f, 148.894f)
          lineTo(137.337f, 166.894f)
          verticalLineTo(39.0f)
          horizontalLineTo(197.337f)
          verticalLineTo(166.894f)
          close()
        }
        // eye
        path(
          fill = SolidColor(Color(0xFF010101)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(181.337f, 62.895f)
          curveTo(182.442f, 62.895f, 183.337f, 62.0f, 183.337f, 60.895f)
          curveTo(183.337f, 59.79f, 182.442f, 58.895f, 181.337f, 58.895f)
          curveTo(180.232f, 58.895f, 179.337f, 59.79f, 179.337f, 60.895f)
          curveTo(179.337f, 61.999f, 180.232f, 62.895f, 181.337f, 62.895f)
          close()
        }
        // eye
        path(
          fill = SolidColor(Color(0xFF010101)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(153.337f, 62.895f)
          curveTo(154.442f, 62.895f, 155.337f, 62.0f, 155.337f, 60.895f)
          curveTo(155.337f, 59.79f, 154.442f, 58.895f, 153.337f, 58.895f)
          curveTo(152.232f, 58.895f, 151.337f, 59.79f, 151.337f, 60.895f)
          curveTo(151.337f, 61.999f, 152.232f, 62.895f, 153.337f, 62.895f)
          close()
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(Color(0xFF63C8FF)),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(173.319f, 53.099f)
          lineTo(189.215f, 53.157f)
          curveTo(237.727f, 76.996f, 225.756f, 110.361f, 192.058f, 76.275f)
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(Color(0xFF63C8FF)),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(137.337f, 169.582f)
          verticalLineTo(176.223f)
          horizontalLineTo(124.338f)
        }
        path(
          fill = SolidColor(Color(0x00000000)),
          stroke = SolidColor(Color(0xFF63C8FF)),
          strokeLineWidth = 2.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(197.338f, 169.582f)
          verticalLineTo(176.223f)
          horizontalLineTo(210.337f)
        }
        path(
          fill = SolidColor(Color(0xFF36A0DA)),
          stroke = null,
          strokeLineWidth = 0.0f,
          strokeLineCap = Butt,
          strokeLineJoin = Miter,
          strokeLineMiter = 4.0f,
          pathFillType = NonZero,
        ) {
          moveTo(197.337f, 69.645f)
          horizontalLineTo(181.337f)
          verticalLineTo(97.724f)
          horizontalLineTo(197.337f)
          verticalLineTo(69.645f)
          close()
        }
      }
    }
      .build()
    return readianWelcome!!
  }

private var readianWelcome: ImageVector? = null
