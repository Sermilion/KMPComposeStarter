package com.sermilion.kmpcomposestarter.core.datastore

import okio.FileSystem

/**
 * The host's file system.
 *
 * okio declares `FileSystem.SYSTEM` in an intermediate source set that covers the JVM, Native and
 * Node targets rather than in `commonMain`, so common code cannot reference it directly. Every
 * target this module builds for has one, which is what this expect narrows to.
 */
internal expect val platformFileSystem: FileSystem
