Create the database according to the instructions, then nothing will have to be changed in the program's internal configuration.
Download the latest database - 2.5.1 because this version also uses the driver in the program.
Otherwise, an incompatibility error will pop up.
https://www.tutorialspoint.com/hsqldb/hsqldb_installation.htm

If the database is where, then you should modify the leads to the base in the DateSource class

When starting the program, it is necessary to provide one parameter with the path to the json file. The path must be complete, e.g.
"C: \\ Users \\ logfile.txt";

A sample json file from the job content is inside the project in the src / main / resources location
