package controllers

import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import play.api.libs.json.{JsString, Json}
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

      // arrange
      val body =  Json.parse("""
         {
         	"resources": [
         		{
         			"name": "resource name",
         			"price": 250.45
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
    "fail to create a new event when its resources are missing" in {

      // arrange
      val body =  Json.parse("""
         {
          "name": "event name",
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

  "EventController#CreateEvent" should {
    "fail to create a new event when its favorite resource is not in the resources list" in {

      val body =  Json.parse("""
         {
          "name": "event name",
          "favorite_resource": "a different resource",
          "resources": [
            {
              "name": "resource name",
              "price": 250.45
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
        val errorMessage = jsonBody \ "message"

        if (errorMessage.isDefined) {
          errorMessage.get mustBe JsString("requirement failed: favorite_resource must match a resource name in the " +
            "resource item list")
        } else fail()

      } else fail()
    }
  }

  "EventController#CreateEvent" should {
    "fail to create a new event when its resources list is empty" in {

      // arrange
      val body =  Json.parse("""
         {
          "name": "event name",
          "favorite_resource": "a different resource",
          "resources": []
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
        val lengthMessage = fieldErrors \ 0 \ "msg" \ 0

        if (lengthMessage.isDefined) {
          lengthMessage.get mustBe JsString("error.minLength")
        } else fail()
      }
    }
  }

  // todo: name and description minimun length and maximun length
}
