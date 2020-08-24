if not exist bin md bin
javac -d bin ./src/*
java -classpath ./bin lexer.Main < t2.txt
