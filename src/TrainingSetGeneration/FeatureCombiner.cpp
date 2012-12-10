/*******
 *
 *读入训练集文件，将合并后的特征向量输出到特征文件中去
 *
 * */

#include <cstdlib>
#include <vector>
#include <string>
#include <iostream>
#include <fstream>
using namespace std;

/* Split string "str" with string "delim".
 */
vector<string> splitString (const string &str, const string &delim) {
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

struct Configure
{
	vector<string> inputFiles;
	string outputDir;
	string outputName;
} config;

void parseArgument(int argc, char** argv)
{
	if(argc < 5)
	{
		cout << "Usage: FeatureCombiner -i inputFilePaths(separate by ';')" 			<< " -d output Directory " << endl;
		exit(1);
	}
	for(int i = 1; i < argc; i++)
	{
		if(argv[i][0] != '-')
		{
			cout << "Invalid argument:" << argv[i] << endl;
			exit(2);
		}
		switch(argv[i][1])
		{
			case 'i':
				{
					string inputPaths(argv[i+1]);
					config.inputFiles = splitString(inputPaths,string(";"));
					i++;
					break;
				}
			case 'd':
				config.outputDir = string(argv[i+1]);
				i++;
				break;
			case 'o':
				config.outputName = string(argv[i++]);
				break;
			default:
				cout << "Invalid option:" << argv[i] << endl;
				exit(2);
				break;
		}
	}
}

#define MAX_FEAVEC_WIDTH		500
void combineFeature(const string& inputSampleSetPath)
{
	ifstream in(inputSampleSetPath.c_str());
	if(!in)
	{
		cout << "Error File:" << inputSampleSetPath << " does not exist!" << endl;
		exit(3);
	}
	int start_pos = ((inputSampleSetPath.find_last_of('/') != string::npos) ? inputSampleSetPath.find_last_of('/')
																			: inputSampleSetPath.find_last_of('\\')) + 1;
	string outputFileName = inputSampleSetPath.substr(start_pos,inputSampleSetPath.find_last_of('.')) ;	//除去最后一个.之前的部分
	ofstream out(string(config.outputDir + "/" + outputFileName).c_str());
	cout << string(config.outputDir + "/" + outputFileName).c_str() << endl;
	while(in)
	{
		string aFilePath,bFilePath;
		in >> aFilePath >>  bFilePath;

		double a[MAX_FEAVEC_WIDTH], b[MAX_FEAVEC_WIDTH];
		ifstream aFile, bFile;
		aFile.open(aFilePath.c_str(),ios::in);
		bFile.open(bFilePath.c_str(),ios::in);	
		if (!aFile || !bFile)
			return;
		int i, index = 0;
		while (aFile.eof() == 0 && bFile.eof() == 0) 
		{
			aFile >> a[index]; bFile >> b[index];
			index++;
		}
		aFile.close(); bFile.close();
	
		// 组合策略<a1,a2,..an,b1,b2,...,bn>，可替换为<a1*b1,a2*b2,...,an*bn>，
		// 或<(ai-bi)^2>，或<(ai/bi)>
		for (i = 0; i < index; i++)
			out << a[i]*b[i] << " ";

		out << endl;
	}
	out.close();
}	

/**
 * main 函数参数 
 * -i  输入的训练集文件
 * -d 输出的目录
 */
int main(int argc,char** argv)
{
	parseArgument(argc,argv);
	for(vector<string>::const_iterator it = config.inputFiles.begin();
			it != config.inputFiles.end();
			it++)
	{
		combineFeature(*it);
	} 
}
