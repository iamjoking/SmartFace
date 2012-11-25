CXX_FLAG= -Wall -O2

All: TrainingSetGeneration FeatureCombiner

TrainingSetGeneration: TrainingSetGenerator.cpp
	g++ TrainingSetGenerator.cpp -o TrainingSetGeneration $(CXX_FLAG)
FeatureCombiner: FeatureCombiner.cpp
	g++ FeatureCombiner.cpp -o FeatureCombiner $(CXX_FLAG)

clean:
	rm TrainingSetGeneration FeatureCombiner




