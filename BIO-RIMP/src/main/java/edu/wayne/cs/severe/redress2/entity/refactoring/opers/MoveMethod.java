package edu.wayne.cs.severe.redress2.entity.refactoring.opers;

import java.util.HashMap;
import java.util.List;

import edu.wayne.cs.severe.redress2.controller.HierarchyBuilder;
import edu.wayne.cs.severe.redress2.controller.metric.CBOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.CYCLOMetric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM2Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LCOM5Metric;
import edu.wayne.cs.severe.redress2.controller.metric.LOCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.MPCMetric;
import edu.wayne.cs.severe.redress2.controller.metric.NOMMetric;
import edu.wayne.cs.severe.redress2.controller.metric.RFCMetric;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.CBOMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.CYCLOMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.LCOM2MoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.LCOM5MoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.LOCMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.MPCMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.NOMMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.mm.RFCMoveMethodPF;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.JSONRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.exception.RefactoringException;
import edu.wayne.cs.severe.redress2.utils.RefactoringUtils;

/**
 * @author ojcchar
 * @version 1.0
 * @created 28-Mar-2014 17:27:28
 */
public class MoveMethod extends RefactoringType {

	public MoveMethod(List<TypeDeclaration> sysTypeDcls,
			HierarchyBuilder builder) {
		super(sysTypeDcls);
		formulas.put(new LOCMetric().getMetricAcronym(), new LOCMoveMethodPF());
		formulas.put(new NOMMetric().getMetricAcronym(), new NOMMoveMethodPF());
		formulas.put(new RFCMetric().getMetricAcronym(), new RFCMoveMethodPF());
		formulas.put(new CBOMetric().getMetricAcronym(), new CBOMoveMethodPF(
				builder));
		formulas.put(new MPCMetric().getMetricAcronym(), new MPCMoveMethodPF());
		formulas.put(new LCOM5Metric().getMetricAcronym(),
				new LCOM5MoveMethodPF());
		formulas.put(new LCOM2Metric().getMetricAcronym(),
				new LCOM2MoveMethodPF());
		formulas.put(new CYCLOMetric().getMetricAcronym(),
				new CYCLOMoveMethodPF());

	}

	@Override
	public HashMap<String, List<RefactoringParameter>> getRefactoringParams(
			List<JSONRefParam> jsonParams) throws RefactoringException {

		String srcKey = "src";
		String tgtKey = "tgt";
		String mtdKey = "mtd";

		HashMap<String, JSONRefParam> idxParams = RefactoringUtils
				.validateJsonParams(jsonParams, 3, new String[] { srcKey,
						tgtKey, mtdKey }, new int[] { 1, 1, 1 });

		HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
		JSONRefParam jsonParam = idxParams.get(srcKey);
		List<RefactoringParameter> refParams = RefactoringUtils
				.getOpersCodeObject(jsonParam, sysTypeDcls,
						TypeDeclaration.class);
		params.put(srcKey, refParams);

		jsonParam = idxParams.get(tgtKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				TypeDeclaration.class);
		params.put(tgtKey, refParams);

		jsonParam = idxParams.get(mtdKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				MethodDeclaration.class);
		params.put(mtdKey, refParams);

		return params;
	}

	@Override
	public String getAcronym() {
		return "MM";
	}
	
	//danaderp 
	@Override
	public HashMap<String, List<RefactoringParameter>> getOBSERVRefactoringParams(List<OBSERVRefParam> jsonParams)
			throws RefactoringException {
		// TODO Auto-generated method stub
		String srcKey = "src";
		String tgtKey = "tgt";
		String mtdKey = "mtd";

		HashMap<String, OBSERVRefParam> idxParams = RefactoringUtils
				.validateObservParams(jsonParams, 3, new String[] { srcKey,
						tgtKey, mtdKey }, new int[] { 1, 1, 1 });

		HashMap<String, List<RefactoringParameter>> params = new HashMap<String, List<RefactoringParameter>>();
		OBSERVRefParam jsonParam = idxParams.get(srcKey);
		List<RefactoringParameter> refParams = RefactoringUtils
				.getOpersCodeObject(jsonParam, sysTypeDcls,
						TypeDeclaration.class);
		params.put(srcKey, refParams);

		jsonParam = idxParams.get(tgtKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				TypeDeclaration.class);
		params.put(tgtKey, refParams);

		jsonParam = idxParams.get(mtdKey);
		refParams = RefactoringUtils.getOpersCodeObject(jsonParam, sysTypeDcls,
				MethodDeclaration.class);
		params.put(mtdKey, refParams);

		return params;
	}

}// end MoveMethod