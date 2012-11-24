/*
 * =====================================================================================
 *
 *       Filename:  FaceDetection.cpp
 *
 *    Description:  人脸检测类
 *
 *        Version:  1.0
 *        Created:  08/01/2012 12:05:38 PM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  WZK
 *   Organization:  NJU
 *
 * =====================================================================================
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

//OpenCV
#include "opencv2/objdetect/objdetect.hpp"
#include "opencv2/highgui/highgui.hpp"
#include "opencv2/imgproc/imgproc.hpp"


#include "BasicImage.h"
#include <vector>
#include <iostream>
#include <fstream>
#include <vector>
#include <string>
using namespace std;
using namespace cv;

/* Split string "str" with string "delim".
 */
vector<string> splitstring (const string &str, const string &delim) {
	vector<string> ret;
	int current = 0, end = str.size(), width = delim.size();
	string::size_type result;
	while (current < end) {
		result = str.find(delim,current);
		if (result == string::npos)
			result = end;
		ret.push_back(str.substr(current,result - current));
		current = result + width;
	}
	return ret;
}


/**
 * 检测对象
 * @param classifierFile 分类器文件路径
 * @param image 输入图像文件
 * @return 检测到的对象区域
 * */
vector<Rect> DetectObjectUsingCascadeClassifier(string classifierFile, cv::Mat image)
{
	CascadeClassifier faceDetect;
	vector<Rect> result;

	if(!faceDetect.load(classifierFile))
	{
		cerr << "Open Classifier File:" << classifierFile << " fail." << endl;
		return result;
	}

	//其他参数采用默认值
	faceDetect.detectMultiScale(image,result);
//	faceDetect.detectMultiScale(image,result,1.1,2,0|CV_HAAR_SCALE_IMAGE,Size(30,30));
	return result;
}

string face_cascade_name = "haarcascade_frontalface_alt.xml";

void saveSubImage(Mat image,Rect mask,string path)
{
	Range rowRange(mask.y,mask.y + mask.width);
	Range colRange(mask.x,mask.x + mask.height);
	Mat subImage(image,rowRange,colRange);
	cv::imwrite(path,subImage);
}

struct ConfigurationInfo
{
	string inputImagePath;
	string outputImageDirectory;
	string fileExtension;
	string outputInfoFile;
	bool isGray;
	bool isEqualized;

	ConfigurationInfo()
	{
		isGray = false;
		isEqualized = false;
	}
};

ConfigurationInfo parseArguments(int argc,char** argv)
{
	ConfigurationInfo config;
	for(int i = 1; i < argc ; i++)
	{
		if(argv[i][0] =='-')
		{
			// It is an option
			if(strlen(argv[i]) < 2)
			{
				cout << "option:" << argv[i] << " format error" << endl;
				exit(2);
			}
			switch(argv[i][1])
			{
				case 'i':
					//input file path
					i++;
					assert(i < argc);
					config.inputImagePath = argv[i];
					break;
				case 'o':
					//output face location file directory 
					i++;
					assert(i < argc);
					config.outputInfoFile = argv[i];
					break;
				case 'd':
					i++;
					assert(i < argc);
					config.outputImageDirectory = argv[i];
					break;
				case 'g':
					config.isGray = true;
					break;
				case 'e':
					config.isEqualized = true;
					break;
				case 'x':
					i++;
					assert(i < argc);
					config.fileExtension = argv[i];
					break;
				default:
					cout << "ERR:unknown option:" << argv[i] << endl;
			}
		} else
		{
			cout << "ERR: unknown option:" << argv[i] << endl;
		}
	}
	return config;
}

int main(int argc,char** argv)
{
	ConfigurationInfo config;
	if(argc < 7)
	{
		cout << "Usage: FaceDetection -i inputImagePath -d outputDirectoryPath -x FileExtensionName [-g] -[e] [-o outputInfomationFile]" << endl;
		return 1;
	} else
		config = parseArguments(argc, argv);

	
	vector<string> imagePaths;
	imagePaths = splitstring(config.inputImagePath,string(";"));
	vector<string>::const_iterator cit = imagePaths.begin();
	
	for(;cit != imagePaths.end(); cit++){
		/* We need to deal with the situation that the inputImagePath contains many image files. *cit is a image file */
		string currentImageFile = *cit;
		string currentImageName;/* filename without extision name */
		currentImageName = currentImageFile.substr(0,currentImageFile.find_last_of('.'));
		if(currentImageName.find('/') == string::npos)
		{
			/* It is under WINDOWS */
			currentImageName = currentImageName.substr(currentImageName.find_last_of('\\') + 1);
		}else
		{
			/*  It is under *nix */
			currentImageName = currentImageName.substr(currentImageName.find_last_of('/') + 1);
		}
		BasicImage img(currentImageFile);
		BasicImage originImage = img;
		BasicImage& outputImage = (config.isGray?img:originImage);
		img.color2Gray();
		if(config.isEqualized)
			img.equalizeHist();

		//可以调整参数来改善效果
		vector<Rect> result = DetectObjectUsingCascadeClassifier(face_cascade_name,img);

		vector<Rect>::const_iterator it = result.begin();
		int count = 0;
		string faceImagePath = config.outputImageDirectory;
		string imageExtension = "." + config.fileExtension;
		char numberstring[100];
	

		ofstream fout;
		if(config.outputInfoFile.length() > 0)
		{
			fout.open(string(config.outputInfoFile + currentImageName + ".pos").c_str());
		}

		ostream& out = (fout.is_open()?fout:cout);

		out << result.size() << endl;
		while(it != result.end()){
			sprintf(numberstring,"%d",count);
			string number(numberstring);
			string outputImageFilePath = faceImagePath + currentImageName + "_" + number + imageExtension;
			saveSubImage(outputImage,*it,outputImageFilePath);
			out << it->x << " " << it->y << " " << it->height << " " << it->width << endl;
			cerr << outputImageFilePath << endl;
			it++;
			count++;
		}
	}
	return 0;
}
