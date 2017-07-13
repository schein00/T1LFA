
all: java

java: *.java *.class
	javac *.java

clean:
	$(RM) *.class


