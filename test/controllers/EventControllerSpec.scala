package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{JsLookupResult, JsString, JsValue, Json}
import play.api.mvc.{Result, Results}
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.Future

class EventControllerSpec extends PlaySpec with GuiceOneAppPerSuite with Results {

  "EventController#CreateEvent" should {
    "create a new event with a well formed body" in {

      // arrange
      val body =  Json.parse("""
         {
         	"name": "event name",
         	"favorite_resource": "resource name",
         	"resources": [
         		{
         			"name": "resource name",
         			"price": 250.45
         		}
         	],
         	"inputs": [
         		{
         			"name": "input name",
         			"price": 152000.25
         		}
         	]
         }
      """)

      // act
      val request = FakeRequest(POST, "/events").withBody(body)
      val result: Option[Future[Result]] = route(app, request)

      // assert
      if (result.isDefined) status(result.get) mustBe NO_CONTENT
      else fail()
    }
  }

  "EventController#CreateEvent" should {
    "fail to create a new event when its name is missing" in {

      val body =  Json.parse("""
         {
         	"favorite_resource": "resource name",
         	"resources": [
         		{
         			"name": "resource name",
         			"price": 250.45
         		}
         	],
         	"inputs": [
         		{
         			"name": "input name",
         			"price": 152000.25
         		}
         	]
         }
      """)

      // act
      val request = FakeRequest(POST, "/events").withBody(body)
      val result: Option[Future[Result]] = route(app, request)

      // assert
      if (result.isDefined) {

        status(result.get) mustBe UNPROCESSABLE_ENTITY

        val jsonBody = contentAsJson(result.get)
        val fieldErrors = jsonBody \ "obj.name"
        val emptyFieldMessage = fieldErrors \ 0 \ "msg" \ 0

        if (emptyFieldMessage.isDefined) {
          emptyFieldMessage.get mustBe JsString("error.path.missing")
        } else fail()

      } else fail()
    }
  }

  "EventController#CreateEvent" should {
    "failed to create a new event when its resources are missing" in {

      val body =  Json.parse("""
         {
          "name": "event name",
         	"inputs": [
         		{
         			"name": "input name",
         			"price": 152000.25
         		}
         	]
         }
      """)

      // act
      val request = FakeRequest(POST, "/events").withBody(body)
      val result: Option[Future[Result]] = route(app, request)

      // assert
      if (result.isDefined) {

        status(result.get) mustBe UNPROCESSABLE_ENTITY

        val jsonBody = contentAsJson(result.get)
        val fieldErrors = jsonBody \ "obj.resources"
        val emptyFieldMessage = fieldErrors \ 0 \ "msg" \ 0

        if (emptyFieldMessage.isDefined) {
          emptyFieldMessage.get mustBe JsString("error.path.missing")
        } else fail()

      } else fail()
    }
  }
}
