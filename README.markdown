# SoftOff 
## Software Availability Switch 

## tl;dr

You are a Java app developer and you need an easy and configurable way
to remotely test application health.  This library might be for you.

## The Long Version

Load balancers worth their salt can test the servers in a load
balanced pool to ensure that each server is handling traffic properly.
The load balancer will stop sending traffic to a server that fails a
health check.  The test often checks for a specific file or just
checks that the server is accepting connections at all.

Unfortunately the health checks are often incomplete.  They won't test
the fragile parts of the system, or they won't do any real tests at
all.  SoftOff provides an easily configured servlet that allows the
health of an application to determine availability.  Developers
configure the SoftOff servlet to run any number of methods.  If any
method fails by returning false, the SoftOff servlet will return the
HTTP response code 503 (unavailable).  Should all tests pass, the
servlet will return 200 (OK).

We take this concept one step further by providing a JMX interface for
marking the server as available or not.  This allows developers and
administrators to mark an application as down without actually
shutting down the application or killing the process that is running
the code.  The load balancer will immediately stop sending new traffic
to the server, but any open connections will be allowed to complete.
Server maintenance can be done in a completely transparent fashion,
with absolutely no dropped connections or transactions, all without
requiring the network administration team to manually monkey with load
balancer pool membership.

## Getting Started

See the war file at samples/plainwar in the source distribution for an
example of just how easy it is to configure SoftOff for your
application.

There are no external dependencies for the SoftOff library, and there
is only one servlet to configure.


