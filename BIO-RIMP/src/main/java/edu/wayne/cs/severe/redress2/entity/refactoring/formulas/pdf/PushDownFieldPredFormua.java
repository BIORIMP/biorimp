package edu.wayne.cs.severe.redress2.entity.refactoring.formulas.pdf;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.CodeObject;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.formulas.PredictionFormula;

public abstract class PushDownFieldPredFormua extends PredictionFormula {

	protected TypeDeclaration getSourceClass(RefactoringOperation ref) {
		return (TypeDeclaration) ref.getParams().get("src").get(0).getCodeObj();
	}

	protected List<TypeDeclaration> getTargetClasses(RefactoringOperation ref) {

		List<RefactoringParameter> params = ref.getParams().get("tgt");
		List<TypeDeclaration> classes = new ArrayList<TypeDeclaration>();

		for (RefactoringParameter par : params) {
			CodeObject codeObj = par.getCodeObj();
			classes.add((TypeDeclaration) codeObj);
		}

		return classes;
	}

	protected AttributeDeclaration getAttribute(RefactoringOperation ref) {
		return (AttributeDeclaration) ref.getParams().get("fld").get(0)
				.getCodeObj();
	}
}
