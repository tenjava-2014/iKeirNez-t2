iKeirNez's ten.java submission
==============================

[![ten.java](https://cdn.mediacru.sh/hu4CJqRD7AiB.svg)](https://tenjava.com/)

This is a submission for the 2014 ten.java contest.

- __Theme:__
- __Time:__ Time 2 (7/12/2014 09:00 to 7/12/2014 19:00 UTC)
- __MC Version:__ 1.7.9 (latest Bukkit beta)
- __Stream URL:__ https://twitch.tv/iKeirNez

<!-- put chosen theme above -->

---------------------------------------

Compilation
-----------

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/tenjava/iKeirNez-t2`
- Compile and create the plugin package using Maven: `mvn`

Maven will download all required dependencies and build a ready-for-use plugin package!

---------------------------------------

How can energy be harnessed and used in the Minecraft world?
-------

Doesn't it seem a little un-realistic that you can simply place a redstone torch/block and instantly have a stream of power? That's exactly what this plugin intends to fix. A battery is created using a the command **/build battery <size>** (maybe in the future actually placing blocks instead of doing a command) assuming you have enough materials.

The battery has 3 inputs/outputs:
  * Redstone input line, powered by generator
  * Redstone output line, do as you want with it
  * Meter, piston that pushes out when charged
  
The battery will charge faster depending on the power level input.
If the battery overcharges too long it will explode, spilling lethal battery acid everywhere (lava)