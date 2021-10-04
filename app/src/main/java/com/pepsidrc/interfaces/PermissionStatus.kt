package com.pepsidrc.interfaces

import androidx.annotation.IntDef
import com.pepsidrc.common.CommonInt.BLOCKED_OR_NEVER_ASKED
import com.pepsidrc.common.CommonInt.DENIED
import com.pepsidrc.common.CommonInt.GRANTED


import java.lang.annotation.RetentionPolicy
import kotlin.annotation.Retention

@Retention(AnnotationRetention.SOURCE)
@IntDef(GRANTED, DENIED, BLOCKED_OR_NEVER_ASKED)
annotation class PermissionStatus

