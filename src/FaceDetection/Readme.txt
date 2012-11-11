FaceDetection:
	It can detect faces in the image and export the face images to files:
	face0,face1,face2,....

Usage: 
	FaceDetection -i inputImagePath -d outputDirectoryPath -x FileExtensionName [-g] -[e] [-o outputInfomationFile]
	inputImagePath - the path of the input image file
	outputDirectoryPath - the path to which the face image will export, SHOULD NOT ended with "/" and the directory should exist
	FileExtensionName - the extension name of the face image file,e.g.
	jpg,png,...
	-g	the exported the face images will be in grayscale
	-e  the program will equalize the hist before face detecting

Output:
	The file names of face images will be face0,face1,face2,...
	The program will output the face area infomation to the file pointed out by
	the outputInfomationFile option. If the option is not set, it will print the
	result to the standard output.

Face Area Information Output:
	The first line of the file is an integer N which means the number of faces contained in the image.
	The following N lines are location information of the face.
	Each line contains 4 integer representing a rectangle in the image file:
		[x,y,height of the rectangle,width of the rectangle].
