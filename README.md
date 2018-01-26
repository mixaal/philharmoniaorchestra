# Philharmonia Orchestra

## Build

```
mvn clean package
```

## Run

```
java -javaagent:tracer/target/philharmonia-orchestra-tracer-1.0-SNAPSHOT.jar \
     -cp tracer/target/philharmonia-orchestra-tracer-1.0-SNAPSHOT.jar:example/target/philharmonia-orchestra-example-1.0-SNAPSHOT.jar \ 
     net.mixaal.tools.instrumental.example.InstTest
```
