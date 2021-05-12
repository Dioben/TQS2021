# Log

At class: Due to a VM partitioning work got delayed, user partition didn't have sufficient space at first.<br>
          After figuring that out the entire class was spent trying to get jenkins to run either as a normal user or as root, but by the end of the class I found out I only had a java RUNTIME, I partially installed jdk11-headless but left halfway through because my connection was slow.<br>
          Later at the same day attempts at running the same code told me they could not locate libawt_xawt.so for some reason,<br> I installed apt-file and used it to find packages with the file, finally getting a successful jenkins build
          ![Jenkins Build](https://github.com/buckaroo69/TQS2021/blob/master/08/1stsuccess.png "success")
          
The console log for the build can be found [here](https://github.com/buckaroo69/TQS2021/blob/master/08/1strunlog.txt "log")

For ex 2 a pipeline was configured to run [every 5 minutes] (https://github.com/buckaroo69/TQS2021/blob/master/08/polling.png "polling interval)
A failing test was introduced and caught by Jenkins ![error](https://github.com/buckaroo69/TQS2021/blob/master/08/autorun.png "error")
Error log [here](https://github.com/buckaroo69/TQS2021/blob/master/08/pipelinefail.txt "error log")

Blue UI was installed and inspected
![main](https://github.com/buckaroo69/TQS2021/blob/master/08/blueui.png "main")
![pipeline](https://github.com/buckaroo69/TQS2021/blob/master/08/blueuipipeline.png "pipeline")
![test list](https://github.com/buckaroo69/TQS2021/blob/master/08/testlist.png "test list")

Docker containers have been created, due to a lack of time docker-related jenkinsfiles will not be looked into.
![run](https://github.com/buckaroo69/TQS2021/blob/master/08/dockerjenkinsrun.png "run")
![pw](https://github.com/buckaroo69/TQS2021/blob/master/08/extractpw.png "pw")
