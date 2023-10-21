package com.iccangji.understextapp.data.model

import com.google.gson.annotations.SerializedName

data class SentimentResponse(

	@field:SerializedName("msg")
	val msg: String? = null,

	@field:SerializedName("sentiment_list")
	val sentimentList: List<SentimentListItem?>? = null,

	@field:SerializedName("sentiment")
	val sentiment: String? = null,

	@field:SerializedName("app_version")
	val appVersion: String? = null,

	@field:SerializedName("aggregate_sentiment")
	val aggregateSentiment: AggregateSentiment? = null,

	@field:SerializedName("ok")
	val ok: Boolean? = null,

	@field:SerializedName("time_taken")
	val timeTaken: Any? = null
)

data class SentimentListItem(

	@field:SerializedName("sentence")
	val sentence: String? = null,

	@field:SerializedName("neg")
	val neg: Any? = null,

	@field:SerializedName("pos")
	val pos: Any? = null,

	@field:SerializedName("compound")
	val compound: Any? = null,

	@field:SerializedName("neu")
	val neu: Any? = null
)

data class AggregateSentiment(

	@field:SerializedName("neg")
	val neg: Double? = null,

	@field:SerializedName("pos")
	val pos: Double? = null,

	@field:SerializedName("compound")
	val compound: Double? = null,

	@field:SerializedName("neu")
	val neu: Double? = null
)
