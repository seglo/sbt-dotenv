version := "0.1"

lazy val root = (project in file(".")).aggregate(proj1, proj2)

lazy val check = taskKey[Unit]("check")

lazy val proj1 = (project in file("proj1")).settings(
  compile in Compile := (compile in Compile).dependsOn(myBeforeTask).value
  envFileName := "proj1/.env",
  check := {
    val lastLog: File = BuiltinCommands.lastLogFile(state.value).get
    val last: String = IO.read(lastLog)
    val contains = last.contains("Configured .env environment")
    if (!contains)
      sys.error("expected log message")
    if (sys.env.get("LINE_ONE").isEmpty || sys.env.get("LINE_TWO").isEmpty)
      sys.error("environment variables not set")
  }
)

lazy val proj2 = (project in file("proj2")).settings(
  envFileName := "proj2/.env"
)


