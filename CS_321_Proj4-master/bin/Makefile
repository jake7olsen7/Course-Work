# Final Project Makefile
# Compiler flags
JFLAGS = -g
JC = javac
J = java
# Ensure that source files are separated by tabs and not spaces
SOURCES = BTreeADT.java	BTreeNodeADT.java	BTree.java	BTreeNode.java

.SILENT:

# Setup default targets
.SUFFIXES: .java .class

default:
	$(JC) $(SOURCES)

man: default
	$(J) Test

clean:
	rm -f  *.class