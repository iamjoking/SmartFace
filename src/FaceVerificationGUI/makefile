# A gereral java project makefile
# Author : Wu Jiqing
# Modified by : iamjoking
# create 2012-06-12
# update 2012-06-13
# version 0.7
# Special thanks to Wu Jiqing.

# Set the file name of the jar package
JAR_PKG = fvaa.jar

# Set the entry point of your java app
ENTRY_POINT = main.MainFrame

# Set the external jar file path
EXT_JAR = ext/*;

# Give the source files of your project. Pay attention to the order of these files.
SOURCE_FILES = \
	$(SOURCE_COMMON_UTIL) \
	$(SOURCE_COMMON_VIEW) \
	$(SOURCE_MAIN) \

SOURCE_COMMON_UTIL = \
	common/util/logging/LogFile.java \
	common/util/logging/LogFactory.java \
	common/util/logging/Logger.java \
	common/util/command/ExecCommand.java \
	common/util/command/LoggedCommand.java \
	common/util/FileOperation.java \
	common/util/Section.java \
	common/util/Project.java \

SOURCE_COMMON_VIEW = \
	common/view/FileDirectoryTree.java \
	common/view/MessagePane.java \
	common/view/OptionFrame.java \

SOURCE_MAIN = \
	main/InitialInformation.java \
	main/NewProjectFrame.java \
	main/ProjectPane.java \
	main/MainFrame.java \

# Set your java compiler here.
JAVAC = javac

# Set the compiler's options
JOPTIONS =

# Usage :
# make new      : New a project.
# make build    : Build your project. Generate class files(.class) under "bin".
# make clean    : Clean all the class files(.class).
# make rebuild  : clean + build
# make run      : Run the application.
# make jar      : Generate the jar file.
# make test     : Test some class file.


vpath %.class bin
vpath %.java src

# Show help message by default
Default :
	@echo make new     : New a project.
	@echo make build   : Build your project. Generate class files(.class) under "bin".
	@echo make clean   : Clean all the class files(.class).
	@echo make rebuild : clean + build
	@echo make run     : Run the jar file.
	@echo make jar     : Generate the jar file.
	@echo make test    : Test some class file.

new :
	mkdir src bin res ext

build : $(SOURCE_FILES:.java=.class)
%.class: %.java
	$(JAVAC) -cp bin;$(EXT_JAR) -d bin $(JOPTIONS) $<

clean : 
	rmdir /s /q bin
	mkdir bin
	del $(JAR_PKG)

rebuild : clean build

run :
	java -cp res/pic/;bin;$(EXT_JAR) $(ENTRY_POINT)

jar :
	jar cvfe $(JAR_PKG) $(ENTRY_POINT) -C bin . res ext
	
test :
	java -cp bin;$(EXT_JAR) $(tar)
