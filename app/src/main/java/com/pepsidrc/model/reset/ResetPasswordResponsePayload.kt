package com.pepsidrc.model.reset

import com.google.gson.annotations.SerializedName

data class ResetPasswordResponsePayload(@SerializedName("success")
                                        val success: Boolean = false,
                                        @SerializedName("message")
                                        val message: String = "")