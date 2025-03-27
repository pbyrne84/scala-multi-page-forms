package com.github.pbyrne84.scalamultipageforms

sealed trait Page {
  val maybeNextPage: Option[Page]
}

case class HomePageValues()
case class HomePage(homePageValues: HomePageValues, maybeNextPage: Option[Page] = None) extends Page
