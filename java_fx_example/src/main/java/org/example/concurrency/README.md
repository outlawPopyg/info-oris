## Concurrency in JavaFX
___
Sometimes you absolutely need to perform some long-running task in a JavaFX application.
You don't want to leave the GUI unresponsive while the task is running, so you want to run
the ask in its own thread. However, you would like the running task to update the GUI - either
along the way, or when the task is completed. The task thread cannot update the GUI
(the scene graph) directly - but JavaFX has a solution for this problem.

JavaFX contains the Platform class which has a `runLater()` method. The `runLater()` method
takes a `Runnable` which is executed by the JavaFX application thread when it has time.
From inside this `Runnable` you can modify the JavaFX scene graph. Here is an example showing a task
thread updating a JavaFX `ProgressBar` while it is executing - by calling `Platform.runLater()`
and update the `ProgressBar` control from in there.