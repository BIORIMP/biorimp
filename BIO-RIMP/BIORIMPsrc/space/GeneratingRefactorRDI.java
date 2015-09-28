/**
 * 
 */
package space;

import java.util.ArrayList;
import java.util.List;

import edu.wayne.cs.severe.redress2.entity.AttributeDeclaration;
import edu.wayne.cs.severe.redress2.entity.MethodDeclaration;
import edu.wayne.cs.severe.redress2.entity.TypeDeclaration;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation;
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringParameter;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefParam;
import edu.wayne.cs.severe.redress2.entity.refactoring.json.OBSERVRefactoring;
import entity.MetaphorCode;
import unalcol.random.integer.IntUniform;
import unalcol.types.collection.bitarray.BitArray;

/**
 * @author Daavid
 *
 */
public class GeneratingRefactorRDI extends GeneratingRefactor {

	/* (non-Javadoc)
	 * @see entity.MappingRefactor#mappingRefactor(java.lang.String, unalcol.types.collection.bitarray.BitArray, entity.MetaphorCode)
	 */
	protected Refactoring type = Refactoring.replaceDelegationInheritance;
	@Override
	public OBSERVRefactoring generatingRefactor( MetaphorCode code ) {
		// TODO Auto-generated method stub
		boolean feasible;
        List<OBSERVRefParam> params;
        IntUniform g = new IntUniform ( code.getMapClass().size() );
        do{
        	feasible = true;
        	params = new ArrayList<OBSERVRefParam>();
			//Creating the OBSERVRefParam for the src class
			TypeDeclaration sysType_src =  code.getMapClass().get( g.generate() );
			List<String> value_src  = new ArrayList<String>();
			value_src.add(sysType_src.getQualifiedName());
			params.add(new OBSERVRefParam("src", value_src));
			
			//Creating the OBSERVRefParam for the tgt
			List<String> value_tgt  = new ArrayList<String>();
			TypeDeclaration sysType_tgt = code.getMapClass().get( g.generate() );
			value_tgt.add( sysType_tgt.getQualifiedName());
			params.add(new OBSERVRefParam("tgt", value_tgt));
			
			//Verification of equality
			if( sysType_src.equals(sysType_tgt) )
				feasible = false;
			
			if(feasible){
				//Hierarchy verification parents 
				if( !code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
					for( TypeDeclaration clase : code.getBuilder().getParentClasses().get( sysType_src.getQualifiedName()) ){
						if( clase.equals(sysType_tgt) ){
							feasible = false;
							break;
						}
							
					}
				}
				
				if(feasible){
					//Hierarchy verification children
					if( !code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()).isEmpty() ){
						for( TypeDeclaration clase : code.getBuilder().getChildClasses().get( sysType_src.getQualifiedName()) ){
							if( clase.equals(sysType_tgt) ){
								feasible = false;
								break;
							}
						}
					}
				}
			}
			
        }while( !feasible );
		return new OBSERVRefactoring(type.name(),params,feasible);
	}
	@Override
	public boolean feasibleRefactor(RefactoringOperation ref, MetaphorCode code) {
		// TODO Auto-generated method stub
		boolean feasible = true;
		
		//Extracting the source class
		List<TypeDeclaration> src = new ArrayList<TypeDeclaration>();
		if( ref.getParams().get("src") != null ){
			if( !ref.getParams().get("src").isEmpty() ){
				for(RefactoringParameter param_src : ref.getParams().get("src") ){
					src.add( (TypeDeclaration) param_src.getCodeObj() );
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		
		//Extracting the target class
		List<TypeDeclaration> tgt = new ArrayList<TypeDeclaration>();
		if( ref.getParams().get("tgt") != null ){
			if( !ref.getParams().get("tgt").isEmpty() ){
				for(RefactoringParameter param_tgt : ref.getParams().get("tgt") ){
					tgt.add( (TypeDeclaration) param_tgt.getCodeObj() );
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
		
		//Verification of equality
		for( TypeDeclaration src_class : src ){
			for( TypeDeclaration tgt_class : tgt ){
				if( src_class.getName().equals( tgt_class.getName() ) )
					return false;
			}
		}
		
		//Hierarchy verification parents 
		for( TypeDeclaration src_class : src ){
			if( !code.getBuilder().getParentClasses().get( src_class.getQualifiedName()).isEmpty() ){
				for( TypeDeclaration clase_parent : code.getBuilder().getParentClasses().get( src_class.getQualifiedName()) ){
					for( TypeDeclaration tgt_class : tgt ){
						if( clase_parent.equals(tgt_class) ){
							return false;
						}
					}
						
				}
			}
		}
		
		//Hierarchy verification children
		for( TypeDeclaration src_class : src ){
			if( !code.getBuilder().getChildClasses().get( src_class.getQualifiedName()).isEmpty() ){
				for( TypeDeclaration clase_child : code.getBuilder().getChildClasses().get( src_class.getQualifiedName()) ){
					for( TypeDeclaration tgt_class : tgt ){
						if( clase_child.equals(tgt_class) ){
									return false;
						}
					}
								
				}
			}
		}
		
		return feasible;
	}

}
