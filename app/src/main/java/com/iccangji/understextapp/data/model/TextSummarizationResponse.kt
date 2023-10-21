package com.iccangji.understextapp.data.model

import com.google.gson.annotations.SerializedName

data class TextSummarizationResponse(

	@field:SerializedName("msg")
	val msg: String,

	@field:SerializedName("summary")
	val summary: String,

	@field:SerializedName("app_version")
	val appVersion: String,

	@field:SerializedName("sentence_count")
	val sentenceCount: Int,

	@field:SerializedName("sentences")
	val sentences: List<String>,

	@field:SerializedName("ok")
	val ok: Boolean,

	@field:SerializedName("time_taken")
	val timeTaken: Any
)
