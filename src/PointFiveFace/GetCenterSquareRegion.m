%获取一个图像的中心正方形区域，将这个中心正方形区域缩放到指定大小后返回
function ResizedCenterRegion=GetCenterSquareRegion(img,s)%image,square block size

	%img = imread('/tmp/NJULogo2.jpg');
	%s = 120;	
	%将图像的两个边长都变成偶数
	if mod(size(img,1),2) == 1
		img = img(1:size(img,1)-1,:);
		end
	if mod(size(img,2),2) == 1
		img = img(:,1:size(img,2) -1);
		end
	centerPoint = size(img) .* 0.5;%中心点的坐标
	squareLength = min(centerPoint);%中心正方形的边长
	centerRegion = img(centerPoint(1) - squareLength + 1:centerPoint(1) + squareLength,
			centerPoint(2)-squareLength+1:centerPoint(2)+squareLength);%取得中心正方形区域
	ResizedCenterRegion = imresize(centerRegion,0.5 * s/squareLength);
	