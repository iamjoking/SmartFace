//OpenCV Library
#include "opencv2/opencv.hpp"
#include "opencv2/core/core.hpp"
using namespace std;
using namespace cv;

class BasicImage
{
	private:
		Mat image;
	public:
		/** 利用Image2初始化 */
		BasicImage(Mat& image2);
		/** 利用path所指定的图片初始化图片 */
		BasicImage(String path);
		/** 复制构造函数 */
		BasicImage(BasicImage& img)
		{
			*this = img;
		}

		/** 输出所保存的图像到一个文件中，目标文件由path指定 */
		bool saveImage(String path);
		/** 判断当前是否有图像 */
		bool isEmpty();
		/** 转化为灰度图 */
		void color2Gray();
		/** 均衡化直方图 */
		void equalizeHist();

		BasicImage& operator =(BasicImage& img);

		operator Mat() const
		{
			return this->image;
		}

};
