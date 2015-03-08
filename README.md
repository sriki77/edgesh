Edge SH
=======
![https://travis-ci.org/sriki77/edgesh](https://travis-ci.org/sriki77/edgesh.png)

*REST API is for computers not for humans :-p *  - this is premise with which this command line shell has been developed to manage Apigee edge. The shell provides UNIX like commands over the REST API provided by the Apigee Edge management server. 

Edge management server is morphed into an <u>UNIX like directory structure</u>. The root directory containing all the Apigee `orgs` as sub directories; `environments` are sub directories of `orgs` and so-on.

For example, `ls` command on an `org` lists its entities like `environments`. You can `cd` to an environment and see its entities.

Concepts
---------

+ **Entities --- Directory Mapping**<br/>
	Every Edge entity can be considered as a directory. Since Edge entities are 	hierarchal in nature - they map to a hierarchal directory structure. The root directory 	of this hierarchy is `org` entity.
	
+ **Entity names**<br/>
	Entity name is of the format `entity-type:entity-name` 	
	If the entity type is omitted, its assumed to be `org` .<br/>
	For example, the following command `cd` to a `org` named `dev`<br/>
	
	```				
		cd dev
		cd org:dev
	```
	The above two commands are equivalent. To switch to environment `test` following 	is the command.	<br/>
	
	```
		cd env:test
	```
	
Usage
--------

**Pre-requisties**

The [distribution](https://github.com/sriki77/edgesh/tree/master/dist) (not ready) provides a executable `Java 8` jar file - which makes` Java SE 8` as the pre-requisite!.


**Command Line**

```
java -jar edgesh-1.0.jar 
 -help           Prints this message
 -p <password>   Edge Password
 -u <username>   Edge Username
 -url <url>      Apigee Edge Mgmt URL
 -v              verbose logging
```
The command line parameters are self explanatory; in essence they capture the information required to work with REST API of Apigee Edge management server.

**Supported Commands In Shell**

```
Valid Commands:
---------------
[ls]            - list directory(entity) contents.
[cd]            - change directory(entity) to given parameter.
[cat]           - print details of the given directory(entity).
[rm]            - remove a given directory(entity). *NOT IMPLEMENTED YET*
[new]           - create a new directory(entity). *NOT IMPLEMENTED YET*
[q, quit, exit] - quit the shell.
[pwd]           - print the current working directory(entity).
[set]           - set the value of a given directory(entity). *NOT IMPLEMENTED YET*
[help, h, ?]    - prints the help message.
[man]           - provides detailed documentation of commands.
---------------
```

Current Status & Limitations
--------
 + Currently read only commands are implemented in the shell. Mutable commands will be added soon. 
 
+ This is still work in progress; supported commands are being enhanced for options and bug fixes.

About
--------
The initial version is implemented by Srikanth Seshadri. Please feel free to contribute ideas,feedback and of course code!!
