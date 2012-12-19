%学习OptimalMIC曲线 
%Study OptimalMIC curve
%学习reg目录下的所有图片,从中计算出最优曲线
function OptimalMIC(reg)
imageFiles = dir(reg);%也可以是其他的
Xopt = zeros(1,60);
for i = 1:size(imageFiles,1)
	img = imread(imageFiles(i).name);
	normalImg = GetCenterSquareRegion(img,120);%规范化为120*120的图像
	[X1,X2,d] = CalcMICCurve(normalImg);
	Xopt = Xopt .+ (X1 .+ X2);
	end
Xopt = 1/(2*size(imageFiles,1)) * Xopt;
dlmwrite('OptimalMIC.vec',Xopt,' ');

