package infrastructure.play

class ErrorHandler {

  // todo: leave all exceptions unhandled in the controllers, and manage them in a global handler ( is this viable (?) )
  // the validator class will throw a custom exception. That exception will be handled
  // like github does: https://developer.github.com/v3/#client-errors
  // other types of exceptions will be handled with a "message" field
}
