package optimization;

import java.util.List;

import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import entity.Qubit;
import entity.QubitArray;
import unalcol.search.space.Space;

public class RefactoringOperationSpace extends Space<RefactoringOperation> {
	protected int n;
	
	public RefactoringOperationSpace( int n ){
		this.n = n; 
	}

	@Override
	public boolean feasible(RefactoringOperation x) {
		return x.size()==n;
	}

	@Override
	public double feasibility(RefactoringOperation x) {
		return feasible(x)?1:0;
	}

	@Override
	public RefactoringOperation repair(RefactoringOperation x) {
		if( x.size() != n ){
			if(x.size()>n){
				x = x.subQubitArray(0,n);
			}else{
				//x = new QubitArray(n, true);
				for( int i=0; i<n;i++)
					x.set(new Qubit(true));
			}
		}
		return x;
	}

	@Override
	public RefactoringOperation get() {
		return new QubitArray(n, true);
	}
}
