package com.github.pbyrne84.scalamultipageforms

import io.circe.Decoder.Result
import io.circe.{Decoder, DecodingFailure, Encoder, FailedCursor, HCursor, Json, JsonObject}

sealed trait Page
case object StartPage extends Page
case object SecondPageA extends Page
case object SecondPageB extends Page
case object ThirdPageA extends Page
case object ThirdPageB extends Page
case object FourthPageA extends Page
case object FourthPageB extends Page

case object Finished extends Page
