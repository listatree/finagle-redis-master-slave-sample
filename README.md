# finagle-redis-master-slave-sample
A simple custom Redis client for Finagle with a manual load balancing.

For running the sample a local Redis on :6379 is required and the code should be able to query the right server. Of course a complete Redis master/slave database can be used with the tests and on production to.

Mainly the code what does is to fan out an operation on all the Redis hosts available and get the response of the first successful host.

This solve the fact that some write operations will hit the slaves and so will give errors since the Redis slaves are normally on readonly mode, but of course when the Redis master responds successfully the operation will be reported as a successul Future.
