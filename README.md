Edge SH
=======
![https://travis-ci.org/sriki77/edgesh](https://travis-ci.org/sriki77/edgesh.png)

*REST API is for computers not for humans :-p *  - this is premise with which this command line shell has been developed to manage Apigee edge. The shell provides UNIX like commands over the REST API provided by the Apigee Edge management server. 

Edge management server is morphed into an <u>UNIX like directory structure</u>. The root directory containing all the Apigee `orgs` as sub directories; `environments` are sub directories of `orgs` and so-on.

For example, `ls` command on an `org` lists its entities like `environments`. You can `cd` to an environment and see its entities.

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
```
The command line parameters are self explanatory; in essence they capture the information required to work with REST API of Apigee Edge management server.

**Supported Commands In Shell**
