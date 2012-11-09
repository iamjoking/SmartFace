/*
 * =====================================================================================
 *
 *       Filename:  SingleSampleGenerator.cpp
 *
 *    Description:  单样本生成类
 *
 *        Version:  1.0
 *        Created:  10/28/2012 11:03:52 AM
 *       Revision:  none
 *       Compiler:  g++
 *
 *         Author:  Siu-Tung Wang
 *   Organization:  NJU
 *
 * =====================================================================================
 */
#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>

#include <vector>
#include <iostream>
#include <fstream>

#define MAX_FEAVEC_WIDTH		500

using namespace std;

struct ConfigurationInfo {
	vector<string> featureFiles;
	string outputFileName;
};

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

void writeSamples(string path, string first, string second) {
	double a[MAX_FEAVEC_WIDTH], b[MAX_FEAVEC_WIDTH];
	ofstream oFile(path.c_str(),ios::out);
	ifstream aFile, bFile;
	aFile.open(first.c_str(),ios::in);
	bFile.open(second.c_str(),ios::in);	
	if (!aFile || !bFile)
		return ;
	int i, index = 0;
	while (aFile.eof() == 0 && bFile.eof() == 0) {
		aFile >> a[index]; bFile >> b[index];
		index++;
	}
	aFile.close(); bFile.close();
	
	// 组合策略<a1,a2,..an,b1,b2,...,bn>，可替换为<a1*b1,a2*b2,...,an*bn>，
	// 或<(ai-bi)^2>，或<(ai/bi)>
	for (i = 0; i < index; i++)
		oFile << a[i] << " ";
	
	for (i = 0; i < index; i++)
		oFile << b[i] << " ";

	oFile.close();
	
	return ;
}

ConfigurationInfo parseArguments(int argc,char** argv) {
	ConfigurationInfo config;
	
	for(int i = 1; i < argc ; i++) {
		if(argv[i][0] =='-') {
			// It is an option
			if(strlen(argv[i]) < 2) {
				cout << "option:" << argv[i] << " format error" << endl;
				exit(2);
			}
			switch(argv[i][1]) {
				case 'i':
					//input feature files path
					i++; assert(i < argc);
					config.featureFiles = splitString(argv[i],";");
					if (config.featureFiles.size() > 2)
						cout << "ERR:more than two feature files are included!" << endl;
					break;
				case 'o':
					//output samples file name
					i++; assert(i < argc);
					config.outputFileName = argv[i];
					break;
				default:
					cout << "ERR:unknown option:" << argv[i] << endl;
					break;
			}
		} else {
			cout << "ERR: unknown option:" << argv[i] << endl;
		}
	}
	return config;
}

void showUsage() {
printf("Usage    : ssGen -i inputFeatureFiles -o outputFileName\n\n");
printf("Attension: The two \"inputFeatureFiles\" should be separated with \";\".\n\n");
printf("Example  : ssGen -i A_1.fea;A_2.fea -o output\\sample.sam \n");
}

// The main method.
int main(int argc,char** argv) {
	ConfigurationInfo config;
	if(argc < 5) {
		showUsage();
		return 1;
	} else
		config = parseArguments(argc, argv);
	

	writeSamples(config.outputFileName,(config.featureFiles)[0], (config.featureFiles)[1]);

	cout << "Done.\n";
	return 0;
}