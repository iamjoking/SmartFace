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
using namespace std;
using namespace cv;

/**
 * 检测对象
 * @param classifierFile 分类器文件路径
 * @param image 输入图像文件
 * @return 检测到的对象区域
 * */
vector<Rect> DetectObjectUsingCascadeClassifier(String classifierFile, cv::Mat image)
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

String face_cascade_name = "haarcascade_frontalface_alt.xml";

void saveSubImage(Mat image,Rect mask,String path)
{
	Range rowRange(mask.y,mask.y + mask.width);
	Range colRange(mask.x,mask.x + mask.height);
	Mat subImage(image,rowRange,colRange);
	cv::imwrite(path,subImage);
}

struct ConfigurationInfo
{
	String inputImagePath;
	String outputImageDirectory;
	String fileExtension;
	String outputInfoFile;
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
					//output file path
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


	BasicImage img(config.inputImagePath);
	BasicImage originImage = img;
	BasicImage& outputImage = (config.isGray?img:originImage);
	img.color2Gray();
	if(config.isEqualized)
		img.equalizeHist();

	//可以调整参数来改善效果
	vector<Rect> result = DetectObjectUsingCascadeClassifier(face_cascade_name,img);

	vector<Rect>::const_iterator it = result.begin();
	int count = 0;
	String faceImagePath = config.outputImageDirectory + "/face";
	String imageExtension = "." + config.fileExtension;
	char numberString[100];
	
	ofstream fout;
	if(config.outputInfoFile.length() > 0)
	{
		fout.open(config.outputInfoFile.c_str());
	}

	ostream& out = (fout.is_open()?fout:cout);

	out << result.size() << endl;
	while(it != result.end()){
		sprintf(numberString,"%d",count);
		String number(numberString);
		saveSubImage(outputImage,*it,faceImagePath + number + imageExtension);
		out << it->x << " " << it->y << " " << it->height << " " << it->width << endl;
		it++;
		count++;
	}
	return 0;
}
