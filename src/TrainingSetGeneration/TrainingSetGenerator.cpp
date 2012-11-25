/*
 * =====================================================================================
 *
 *       Filename:  TrainingSetGenerator.cpp
 *
 *    Description:  —µ¡∑ºØ…˙≥…¿‡
 *
 *        Version:  1.0
 *        Created:  10/26/2012 11:05:11 AM
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
#include <time.h>

#include <vector>
#include <map>
#include <iostream>
#include <fstream>

#define POS_EXT					".pos.set"
#define NEG_EXT					".neg.set"
#define MAX_FEAVEC_WIDTH		500
#define MAX_SAMPLES				100000000
#define max(a,b)				((a) >= (b) ? (a) : (b))
#define min(a,b)				((a) < (b) ? (a) : (b))

using namespace std;

struct ConfigurationInfo {
	vector<string> featureFiles;
	string outputFileName;
	string outputDirectory;
	string idDelim;
	int positiveSamples;
	int negativeSamples;

	ConfigurationInfo()
	{
		idDelim = "_";
		positiveSamples = 0;
		negativeSamples = 0;
	}
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

/**
 *
 * Partitioning files to the corresponding people
 * For example, input files are 1_0.jpg,1_1.jpg,2_0.jpg,3_0.jpg
 * then the function maps the input files into 3 set
 * person #1:[1_0.jpg,1_1.jpg]
 * person #2:[2_0.jpg]
 * person #3:[3_0.jpg]
 * */
map<string, vector<string> > partitionItems(vector<string> &strings, string delim) {
	map<string, vector<string> > ret;
	vector<string>::iterator itVec = strings.begin();
	map<string, vector<string> >::iterator itMap;
	string prefix;
	
	for ( ; itVec != strings.end(); itVec++) {
		if ((*itVec).empty()) continue;
		//prefix = (*itVec).substr(0,(*itVec).find("_"));
		prefix = (*itVec).substr(0,(*itVec).find(delim));
		itMap = ret.find(prefix);
		if (itMap == ret.end()) {
			vector<string> tempVec;
			tempVec.push_back(*itVec);
			ret.insert(map<string, vector<string> >::value_type(prefix,tempVec));
		}
		else {
			itMap->second.push_back(*itVec);
		}
	}
	
	return ret;
}

/* Use reservoid sampling.
 */
int selectPositiveSamples(map<string, vector<string> > stringMap,
		int positiveSamples, vector<string> &first, vector<string> &second) {
	int count = 0;	
	if (positiveSamples < 0) return 0;
	if (positiveSamples == 0)
		positiveSamples = MAX_SAMPLES;
	// ÃÓ¬˙–ÓÀÆ≥ÿ
	map<string, vector<string> >::iterator itMap = stringMap.begin();
	vector<string>::iterator itVecA = (itMap->second).begin(), itVecB = itVecA;
	itVecB++;
	while (count < positiveSamples) {
		if (itMap == stringMap.end()) {
			break;
		}

		if (itVecA == (itMap->second).end()) {
			itMap++;
			if (itMap != stringMap.end()) itVecA = (itMap->second).begin();
			itVecB = itVecA;
			if (itVecB != (itMap->second).end()) itVecB++;
			continue;
		}
		
		if (itVecB == (itMap->second).end()) {
			itVecA++;
			itVecB = itVecA;
			if (itVecB != (itMap->second).end()) itVecB++;
			continue;
		}
		
		first.push_back(*itVecA);
		second.push_back(*itVecB);
		itVecB++; count++;
	}
	
	if (count < positiveSamples)
		return count;
		
	int k = positiveSamples, i = k, index;
	srand((unsigned)time(NULL));
	while (itMap != stringMap.end()) {
		if (itVecA == (itMap->second).end()) {
			itMap++;
			if (itMap != stringMap.end()) itVecA = (itMap->second).begin();
			itVecB = itVecA;
			if (itVecB != (itMap->second).end()) itVecB++;
			continue;
		}
		
		if (itVecB == (itMap->second).end()) {
			itVecA++;
			itVecB = itVecA;
			if (itVecB != (itMap->second).end()) itVecB++;
			continue;
		}
		
		i++;
		index = rand() % k;
		if (k > rand() % i) {
			first[index] = *itVecA;
			second[index] = *itVecB;
		}
		itVecB++;
	}
	
	return positiveSamples;
}

/*
int selectPositiveSamples(map<string, vector<string> > stringMap,
		int positiveSamples, vector<string> &first, vector<string> &second) {
	int maxSamples = 0, sizePerItem, count;
	vector<string>::iterator itVecA, itVecB;
	map<string, vector<string> >::iterator itMap = stringMap.begin();
	for ( ; itMap != stringMap.end(); itMap++) {
		sizePerItem = (itMap->second).size();
		maxSamples += sizePerItem * (sizePerItem - 1) / 2;
	}
	
	count = positiveSamples = min(positiveSamples,maxSamples);
	
	itMap = stringMap.begin();
	itVecA = (itMap->second).begin();
	itVecB = itVecA + 1;
	while(count > 0) {
		if (itVecA == (itMap->second).end()) {
			itMap++;
			itVecA = (itMap->second).begin();
			itVecB = itVecA + 1;
			continue ;
		}
		
		if (itVecB == (itMap->second).end()) {
			itVecA++;
			if (itVecA != (itMap->second).end()) itVecB = itVecA + 1;
			continue;
		}
		
		first.push_back(*itVecA); second.push_back(*itVecB);
		count--; itVecB++;
	}
	return positiveSamples;
}
*/

/* Use reservoid sampling.
 */
int selectNegativeSamples(map<string, vector<string> > stringMap,
		int negativeSamples, vector<string> &first, vector<string> &second) {
	int count = 0;	
	if (stringMap.size() < 2 || negativeSamples < 0) return 0;
	if (negativeSamples == 0)
		negativeSamples = MAX_SAMPLES;
	// ÃÓ¬˙–ÓÀÆ≥ÿ

	map<string, vector<string> >::iterator itMapA = stringMap.begin(), itMapB = itMapA, temp;
	itMapB++;
	vector<string>::iterator itVecA = (itMapA->second).begin(), itVecB = (itMapB->second).begin();

	while (count < negativeSamples) {

		temp = itMapA;
		temp++;
		if (itMapA == stringMap.end() || temp == stringMap.end()) {
			break;
		}

		if (itVecA == (itMapA->second).end()) {
			itMapA++;
			itVecA = (itMapA->second).begin();
			itMapB = itMapA;
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();
			continue;
		}
		
		if (itMapB == stringMap.end()) {
			itVecA++;
			itMapB = itMapA;
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();
			continue;
		}
		
		if (itVecB == (itMapB->second).end()) {
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();
			continue;
		}
		
		first.push_back(*itVecA);
		second.push_back(*itVecB);
		itVecB++; count++;
	}
	
	if (count < negativeSamples)
		return count;
	
	int k = negativeSamples, i = k, index;
	srand((unsigned)time(NULL));
	while (temp != stringMap.end()) {
		temp = itMapA;
		temp++;
		if (itVecA == (itMapA->second).end()) {
			itMapA++;
			itVecA = (itMapA->second).begin();
			itMapB = itMapA;
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();			
			continue;
		}
		
		if (itMapB == stringMap.end()) {
			itVecA++;
			itMapB = itMapA;
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();
			continue;
		}
		
		if (itVecB == (itMapB->second).end()) {
			itMapB++;
			if (itMapB != stringMap.end()) itVecB = (itMapB->second).begin();
			continue;
		}	
		
		i++;
		index = rand() % k;
		if (k > rand() % i) {
			first[index] = *itVecA;
			second[index] = *itVecB;
		}
		itVecB++;
	}

	return negativeSamples;
}

void writeSamples(string path, int label,
					vector<string> &first, vector<string> &second) {
	int index = 0, i;
	double a[MAX_FEAVEC_WIDTH], b[MAX_FEAVEC_WIDTH];
	string fileName = (label == 0) ? (path + NEG_EXT) : (path + POS_EXT);
	ofstream oFile(fileName.c_str(),ios::out);

	vector<string>::iterator itA = first.begin(), itB = second.begin();

	for ( ; itA != first.end(); itA++, itB++) {

		//Output the filename pair
		//<fileA,fileB>
		oFile << *itA << " " << *itB << endl;
	
	}
	
	oFile.close();
	
	return ;
}

ConfigurationInfo parseArguments(int argc,char** argv) {
	ConfigurationInfo config;
	vector<string> numberStrings;
	
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
					break;
				case 'o':
					//output samples file name
					i++; assert(i < argc);
					config.outputFileName = argv[i];
					break;
				case 'd':
					i++; assert(i < argc);
					config.outputDirectory = argv[i];
					break;
				case 's':
					i++; assert(i < argc);
					numberStrings = splitString(argv[i],":");
					config.positiveSamples = atoi(numberStrings[0].c_str());
					config.negativeSamples = atoi(numberStrings[1].c_str());
					break;
				case 'f':
					i++; assert(i < argc);
					config.idDelim = argv[i];
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
printf("Usage    : trsGen -i inputFeatureFiles -d outputDirectoryPath -o outputFileName\n");
printf("           [-s [positiveSamples]:[negativeSamples]] [-f identifierEndFlag]\n\n");
printf("Attension: 1.The \"inputFeatureFiles\" should be separated with \";\".\n");
printf("           2.If \"positiveSamples\" is odmitted or is 0, all of the possible\n");
printf("             combinations will be given. So does \"negativeSamples\".\n");
printf("           3.The default value of \"identifierEndFlag\" is \"_\".\n\n");
printf("Example  : trsGen -i A_1.fea;A_2.fea;B_1.fea;B_2.fea -d output\\ -o samples \n");
printf("           -s 2:2 -f _\n");
}

#ifdef DEBUG
void showConfigurationInfo(ConfigurationInfo &config) {
	vector<string>::iterator it = config.featureFiles.begin();
	cout << "Feature Files    :\n";
	for ( ; it != config.featureFiles.end(); it++)
		cout << "[" <<*it << "]\n";
	cout << endl;
	
	cout << "Output Name      : " << config.outputFileName << endl;
	cout << "Output Directory : " << config.outputDirectory << endl;
	cout << "Positive Samples : " << config.positiveSamples << endl;
	cout << "Negative Samples : " << config.negativeSamples << endl;
}

void showPartitionItems(map<string, vector<string> > &stringMap) {
	map<string, vector<string> >::iterator itMap = stringMap.begin();
	vector<string> stringVec;
	vector<string>::iterator itVec;
	
	cout << "Partition results : " << endl;
	for ( ; itMap != stringMap.end(); itMap++) {
		stringVec = itMap->second;
		cout << "[ ";
		for (itVec = stringVec.begin() ; itVec != stringVec.end(); itVec++)
			cout << "<" << *itVec << ">";
		cout << " ]\n";
	}

}

void showPair(vector<string> &first, vector<string> &second) {
	vector<string>::iterator itA = first.begin(), itB = second.begin();
	for ( ; itA != first.end(); itA++, itB++) {
		cout << "(" << *itA << "," << *itB << ")" << endl;
	}
	
	return ;
}

#endif

// The main method.
int main(int argc,char** argv) {
	ConfigurationInfo config;
	if(argc < 7) {
		showUsage();
		return 1;
	} else
		config = parseArguments(argc, argv);
	
	map<string, vector<string> > stringMap = partitionItems(config.featureFiles,config.idDelim);	
	vector<string> posFirst, posSecond, negFirst, negSecond;
	int positiveSamples, negativeSamples;
	positiveSamples = selectPositiveSamples(stringMap,config.positiveSamples,posFirst,posSecond);
	negativeSamples = selectNegativeSamples(stringMap,config.negativeSamples,negFirst,negSecond);

#ifdef DEBUG
	cout << "==========Debug Message==========\n";
	showConfigurationInfo(config);
	showPartitionItems(stringMap);	
	showPair(posFirst,posSecond);
	cout << endl;
	showPair(negFirst,negSecond);
	cout << "==========Debug Message==========\n";
#endif
	
	writeSamples(config.outputDirectory + config.outputFileName, 1, posFirst, posSecond);
	writeSamples(config.outputDirectory + config.outputFileName, 0, negFirst, negSecond);
	
	cout << "Positive Samples : " << positiveSamples << endl;
	cout << "Negative Samples : " << negativeSamples << endl;

	return 0;
}

//trsGen -i 1_1;1_2;1_3;1_4;2_2;2_3;2_4;2_5;3_2;3_3;4_4;5_1;5_2;5_3;5_3 -o samples -d .\ -s 10:10
//trsGen -d output\ -o samples -s 10:10 -i 1_1.fea;1_2.fea;1_3.fea;2_2.fea;2_3.fea;2_9.fea;3_4.fea;3_5.fea;3_9.fea;4_1.fea;4_2.fea;4_4.fea;4_8.fea;4_9.fea

/*
trsGen -i C:\Users\Momo\Desktop\temp\001A02.fea;C:\Users\Momo\Desktop\temp\001A05.fea;C:\Users\Momo\Desktop\temp\001A08.fea;C:\Users\Momo\Desktop\temp\002A03.fea;C:\Users\Momo\Desktop\temp\002A04.fea;C:\Users\Momo\Desktop\temp\002A05.fea;C:\Users\Momo\Desktop\temp\003A18.fea;C:\Users\Momo\Desktop\temp\003A20.fea;C:\Users\Momo\Desktop\temp\003A23.fea;C:\Users\Momo\Desktop\temp\004A26.fea;C:\Users\Momo\Desktop\temp\004A28.fea;C:\Users\Momo\Desktop\temp\004A30.fea -d C:\Users\Momo\Desktop\temp\sample\ -o samples -f A
*/
