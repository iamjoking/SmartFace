FaceDetection:
	It can detect faces in the image and export the face images to files:
	face_0.xxx,face_1.xxx,face_2.xxx,....

Usage: 
	FaceDetection -i inputImagePaths -d outputDirectoryPath -x FileExtensionName [-g] -[e] [-o outputInfomationFileDirectory]
	inputImagePaths - the path of the input image file(s)(see the example below). image file paths are separated by ';'
	outputDirectoryPath - the path to which the face image will export, SHOULD ended with "/"(or "\\" in Windows) and the directory should exist
	FileExtensionName - the extension name of the face image file,e.g.
	jpg,png,...
	-g	the exported the face images will be in grayscale
	-e  the program will equalize the hist before face detecting
	outputInfomationFileDirectory - the program will output the location info about faces in the image into a text file which has the same name as image file but ends with ".pos".
	Location info files have a format like:
	------------------------
	N (there are N faces in the image file)
	[following are N lines. Each line consists of four numbers x,y,height and width which indicates the face region location. (x,y) is the top-left corner of the face region. OpenCV locates the dot in a coordinate:
	
	O------>y
	|
	|
	|
	v
	x
	]
	-----------------------

Output:
	The file names of face images will be face_0.xxx,face_1.xxx,face_2.xxx,...
	The program will output the face area infomation to the file pointed out by
	the outputInfomationFileDirectory option. If the option is not set, it will print the
	result to the standard output.

Face Area Information Output:
	The first line of the file is an integer N which means the number of faces contained in the image.
	The following N lines are location information of the face.
	Each line contains 4 integer representing a rectangle in the image file:
		[x,y,height of the rectangle,width of the rectangle].

	(x,y) is the top-left corner of the face region. OpenCV locates the dot in a coordinate:
	
	O------>y
	|
	|
	|
	v
	x
	

EXAMPLE:
	in *nix:
	[eg1]
		If we have a.jpg,b.jpg,c.jpg under /tmp and there is an existing directory "/tmp/preview", run the following command in terminal:
       ./FaceDetection -i "/tmp/a.jpg;/tmp/b.jpg;/tmp/c.jpg" -d /tmp/preview -x jpg -o /pos/

	   We will have:
		/tmp/preview/a_0.jpg
		/tmp/preview/b_0.jpg
		/tmp/preview/c_0.jpg
		/tmp/preview/c_1.jpg(c.jpg contains two faces)
		
		/pos/a.pos
		/pos/b.pos
		/pos/c.pos
	[eg2]	

		If we have a.jpg,b.jpg,c.jpg under /tmp and there is an existing directory "/tmp/preview", run the following command in terminal:
       ./FaceDetection -i "/tmp/a.jpg" -d /tmp/preview/ -x jpg 

	   We will have:
		/tmp/preview/a_0.jpg
