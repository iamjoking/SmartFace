/*
 * =====================================================================================
 *
 *       Filename:  BasicImage.cpp
 *
 *    Description:  基础的利用OpenCV2进行图像处理的类
 *
 *        Version:  1.0
 *        Created:  07/31/2012 11:38:31 AM
 *       Revision:  none
 *       Compiler:  gcc
 *
 *         Author:  WZK 
 *   Organization:  NJU,CS
 *
 * =====================================================================================
 */

//OpenCV Library
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>


#include <iostream>
using namespace std;
using namespace cv;

#include "BasicImage.h"

BasicImage::BasicImage(Mat& image2)
{
	image = image2;
}

BasicImage::BasicImage(String path)
{
	image = imread(path);
}

bool BasicImage::saveImage(String path)
{
	return imwrite(path,this->image);
}

bool BasicImage::isEmpty()
{
	return this->image.empty();
}

void BasicImage::color2Gray()
{
	if(image.empty() == false){
		cvtColor(image,image,CV_BGR2GRAY);
	}
}

void BasicImage::equalizeHist()
{
	cv::equalizeHist(image,image);
}

BasicImage& BasicImage::operator =(BasicImage& img)
{
	this->image = img.image.clone();
	return *this;
}

/*
int main()
{
	BasicImage img("/home/bsidb/picture/猫猫头像.png");
	cout << img.isEmpty() << endl;
	img.color2Gray();
	BasicImage img2(img);
	img.equalizeHist();
	img.saveImage("/tmp/NJU.png");
	img2.saveImage("/tmp/NJU2.png");
	return 0;
}
*/
