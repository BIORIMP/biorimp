package edu.wayne.cs.severe.redress2.controller.metric;

import java.util.LinkedHashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.MetricUtils;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.exception.MetricException;
import edu.wayne.cs.severe.redress2.utils.ExceptionUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 23-Mar-2014 12:28:47
 */
public class CYCLOMetric extends CodeMetric {

	private static final String ACRONYM = "CYCLO";
	// logger
	private static Logger LOGGER = LoggerFactory.getLogger(NOMMetric.class);

	public CYCLOMetric(List<TypeDeclaration> sysTypeDcls,
			HierarchyBuilder builder) {
		super(sysTypeDcls, builder);
	}

	public CYCLOMetric() {
	}

	@Override
	public double computeMetric(TypeDeclaration typeDcl) throws MetricException {

		try {

			double cyclo = 0.0;

			LinkedHashSet<String> methods = MetricUtils.getMethods(typeDcl);
			for (String method : methods) {
				double cycloMethod = 0.0;
				if (method.equals(typeDcl.getName())) {
					cycloMethod = MetricUtils.getCycloConstructor(typeDcl,
							method);
				} else {
					cycloMethod = MetricUtils.getCycloMethod(typeDcl, method);
				}

				// LOGGER.debug("Method " + method + ": " + cycloMethod);

				cyclo += cycloMethod;
			}

			return cyclo;

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
			MetricException ex = new MetricException(e.getMessage());
			ExceptionUtils.addStackTrace(e, ex);
			throw ex;
		}
	}

	@Override
	public String getMetricAcronym() {
		return ACRONYM;
	}

}// end CYCLOMetric