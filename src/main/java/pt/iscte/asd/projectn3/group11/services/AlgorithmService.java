package pt.iscte.asd.projectn3.group11.services;


import pt.iscte.asd.projectn3.group11.services.algorithms.BasicAlgorithmService;
import pt.iscte.asd.projectn3.group11.services.algorithms.jmetal.CustomAlgorithmServiceJMetal;
import pt.iscte.asd.projectn3.group11.services.algorithms.moea.CustomAlgorithmServiceMoea;
import pt.iscte.asd.projectn3.group11.services.algorithms.IAlgorithmService;

import java.util.List;
import java.util.Locale;

/**
 * Service to create Algorithms based on String names
 */
public final class AlgorithmService {
	public static final String BASIC_ALGORITHM_NAME = "BASIC";
	public static final String OWL_ALGORITHM_NAME = "OWL";
	public static final String MOEA_ALGORITHM_NAME = "OWL";

	public static final int NOT_USE_BASIC_FLAG = 1;  	// 001
	public static final int USE_MOEA_FLAG = 2;  		// 010
	public static final int USE_OWL_FLAG = 4;  			// 100

	private static final int MAX_EVALUATION = 1000;

	public static IAlgorithmService generateAlgorithm(String algoName){
		LogService.getInstance().info(algoName);
		algoName = algoName.toLowerCase(Locale.ROOT).trim();

		int flags = parse(algoName);
		if(flags == 0) {
			LogService.getInstance().info("Chose Basic");
			return new BasicAlgorithmService();
		}
		else {
			if ((flags & USE_OWL_FLAG) == USE_OWL_FLAG)
			{
				List<String> querryResult = SwrlService.querry(TimetableEvaluationService.METRICSLIST.size());
				if(querryResult == null) {
					LogService.getInstance().info("Chose Basic");
					return new BasicAlgorithmService();
				}else {
					if((flags & USE_MOEA_FLAG) == USE_MOEA_FLAG) {
						LogService.getInstance().info("Chose Moea with owl");
						return new CustomAlgorithmServiceMoea(querryResult.get(0), MAX_EVALUATION);
					}
					else {
						LogService.getInstance().info("Chose jmetal with owl");
						return new CustomAlgorithmServiceJMetal();
					}
				}
			}
			else {
				if((flags & USE_MOEA_FLAG) == USE_MOEA_FLAG) {
					LogService.getInstance().info("Chose Moea");
					return new CustomAlgorithmServiceMoea(algoName, MAX_EVALUATION);
				}
				else {
					LogService.getInstance().info("Chose jmetal");
					return new CustomAlgorithmServiceJMetal();
				}
			}
		}
	}

	private static int parse(String algoName)
	{
		algoName = algoName.toUpperCase().trim();
		int flags = 0;
		if(!algoName.contains(BASIC_ALGORITHM_NAME))
		{
			flags |= NOT_USE_BASIC_FLAG;
			if(algoName.contains(MOEA_ALGORITHM_NAME))
			{
				flags |= USE_MOEA_FLAG;
			}
			if(algoName.contains(OWL_ALGORITHM_NAME))
			{
				flags |= USE_OWL_FLAG;
			}
		}
		return flags;
	}
}
