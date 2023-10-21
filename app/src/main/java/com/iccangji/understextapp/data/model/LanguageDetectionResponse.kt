package com.iccangji.understextapp.data.model

import com.google.gson.annotations.SerializedName

data class LanguageDetectionResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("app_version")
	val appVersion: String,

	@field:SerializedName("language_probability")
	val languageProbability: Map<String, Double>,

	@field:SerializedName("ok")
	val ok: Boolean,

	@field:SerializedName("time_taken")
	val timeTaken: Any
)