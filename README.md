----------------
# JDBC Framework

My aim in developing a JDBC framework was to create a simple, flexible, and lightweight framework for running JDBC queries.

It can be used as a standalone framework within any Java application, or as a drop-in replacement for the Spring Framework 
native JDBC abstraction code.

N.B.: I developed the JDBC Framework code prior to the existence of <code>java.lang.AutoCloseable</code>, and the <code>try-with-resources</code>
statement, introduced in Java SE 7. So, much of the motivation and thinking behind the JDBC Framework is best understood within that context, not having
the benefits of the Java 7 improvements in the language.

Further reading: <a target="_blank" href="https://climber09.github.io/p/jdbc_framework">climber09.github.io/p/jdbc_framework</a>
