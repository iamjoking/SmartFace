[trsGen] Training Set Generator.
Usage    : trsGen -i inputFeatureFiles -d outputDirectoryPath -o outputFileName
           [-s [positiveSamples]:[negativeSamples]] [-f identifierEndFlag]

Attension: 1.The "inputFeatureFiles" should be separated with ";".
           2.If "positiveSamples" is odmitted or is 0, all of the possible
             combinations will be given. So does "negativeSamples".
           3.The default value of "identifierEndFlag" is "_".

Example  : trsGen -i A_1.fea;A_2.fea;B_1.fea;B_2.fea -d output\ -o samples
           -s 2:2 -f _
		   

[ssGen] Single Sample Generator.
Usage    : ssGen -i inputFeatureFiles -o outputFileName

Attension: The two "inputFeatureFiles" should be separated with ";".

Example  : ssGen -i A_1.fea;A_2.fea -o output\sample.sam