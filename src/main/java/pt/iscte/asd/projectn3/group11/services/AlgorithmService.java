package pt.iscte.asd.projectn3.group11.services;


import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.moea.CustomAlgorithmServiceMoea;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;

import java.util.List;
import java.util.Locale;

/**
 * Service to create Algorithms based on String names
 */
public final class AlgorithmService {
	public static final String BASIC_ALGORITHM_NAME = "basic";
	public static final String OWL_ALGORITHM_NAME = "owl";

	private static final int MAX_EVALUATION = 1000;

	public static IAlgorithmService generateAlgorithm(String algoName){
		algoName = algoName.toLowerCase(Locale.ROOT).trim();

		IAlgorithmService iAlgorithmService;

		if(algoName.equals(BASIC_ALGORITHM_NAME)) {
			iAlgorithmService = new BasicAlgorithmService();
		}
		else if (algoName.equals(OWL_ALGORITHM_NAME)) {
			List<String> querryResult = SwrlService.querry(TimetableEvaluationService.METRICSLIST.size());
			if(querryResult == null) {
				iAlgorithmService = new BasicAlgorithmService();
			}else {
				iAlgorithmService = new CustomAlgorithmServiceMoea(querryResult.get(0), MAX_EVALUATION);
			}
		}
		else {
			iAlgorithmService = new CustomAlgorithmServiceMoea(algoName, MAX_EVALUATION);
		}
		return iAlgorithmService;
	}


}
