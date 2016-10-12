package scala

import java.lang.Double
import java.optmodel.mappings.metaphor.MetaphorCode
import java.storage.entities.{RefKey, Register}

import edu.wayne.cs.severe.redress2.entity.{MethodDeclaration, AttributeDeclaration, TypeDeclaration}
import edu.wayne.cs.severe.redress2.entity.refactoring.RefactoringOperation
import unalcol.optimization.OptimizationFunction
import scala.collection.JavaConversions._


import scala.FitnessScalaApply.{ClassMap, RefAcronym, RefactorRegister, Metric}
import scala.concurrent.Future

/**
  * Created by david on 6/10/16.
  */

object FitnessScalaApply{
  type Metric = Map[String,Map[String, Map[String, Double]]]
  type ClassMap = Map[String, Map[String, Double]]

  object RefAcronym extends Enumeration{
    val EM, IM, RMMO, MF, PDF, PUF, MM, PDM, PUM, RDI, RID, EC = Value
  }

  case class RefactorRegister(src:Option[List[String]], tgt:Option[List[String]]=None,
                              fld:Option[List[String]], mtd:Option[List[String]])
}

trait FitnessCacheUtils{
  /**
    * Extract the Params from a "Extract Class" Refactoring
    *
    * @param operRef
    */
  private[scala] def extractParamsEC(operRef: RefactoringOperation):RefactorRegister = {
    //1.Extracting src from subrefs
    val src = operRef.getSubRefs.toList flatMap {oneRef =>
      oneRef.getParams.toMap.get("src") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[TypeDeclaration].getId.toString
        }
      }
    } flatten

    //2.Extracting fld from subrefs
    val fld = operRef.getSubRefs.toList flatMap{oneRef =>
      oneRef.getParams.toMap.get("fld") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[AttributeDeclaration].getObjName
        }
      }
    } flatten

    //3.Extracting mtd from subrefs
    val mtd = operRef.getSubRefs.toList flatMap{oneRef =>
      oneRef.getParams.toMap.get("mtd") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[MethodDeclaration].getObjName
        }
      }
    } flatten

    RefactorRegister(src = Option(src), fld = Option(fld), mtd = Option(mtd) )
  }

  private[scala] def extractParams(operRef: RefactoringOperation):RefactorRegister = {
    //1.Extracting src from ref
    val src = operRef.getParams.toMap.get("src") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[TypeDeclaration].getId.toString
        }
      }

    val tgt = operRef.getParams.toMap.get("tgt") map { refactoringParameterList =>
      refactoringParameterList.toList map {refactoringParameter =>
        refactoringParameter.getCodeObj.asInstanceOf[TypeDeclaration].getId.toString
      }
    }

    //2.Extracting fld from ref
    val fld = operRef.getParams.toMap.get("fld") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[AttributeDeclaration].getObjName
        }
      }

    //3.Extracting mtd from ref
    val mtd = operRef.getParams.toMap.get("mtd") map { refactoringParameterList =>
        refactoringParameterList.toList map {refactoringParameter =>
          refactoringParameter.getCodeObj.asInstanceOf[MethodDeclaration].getObjName
        }
      }

    RefactorRegister(src = src, tgt = tgt, fld = fld, mtd = mtd )
  }

  private def matchMetric(acronym:String, src:String, tgt:String, mtd:String, fld:String): RefKey ={
    acronym match {
      case RefAcronym.EM  =>
        new RefKey(acronym, src, "", mtd, "")
      case RefAcronym.IM  =>
        new RefKey(acronym, src, "", mtd, "")
      case RefAcronym.RMMO  =>
        new RefKey(acronym, src, "", mtd, "")
      case RefAcronym.MF  =>
        new RefKey(acronym, src, tgt, "", fld)
      case RefAcronym.PDF  =>
        new RefKey(acronym, src, tgt, "", fld)
      case RefAcronym.PUF  =>
        new RefKey(acronym, src, tgt, "", fld)
      case RefAcronym.MM  =>
        new RefKey(acronym, src, tgt, mtd, "")
      case RefAcronym.PDM  =>
        new RefKey(acronym, src, tgt, mtd, "")
      case RefAcronym.PUM  =>
        new RefKey(acronym, src, tgt, mtd, "")
      case RefAcronym.RDI  =>
        new RefKey (acronym, src, tgt, "", "")
      case RefAcronym.RID  =>
        new RefKey (acronym, src, tgt, "", "")
      case RefAcronym.EC  =>
        new RefKey(acronym, src, "", mtd, fld)
    }
  }

  private[scala] def retrieveMetrics(refactParam: RefactorRegister, acronym: String): ClassMap ={
    val refKeys = refactParam.src map { srcList =>
      srcList flatMap { src =>
        refactParam.fld map{ fldList =>
          fldList flatMap { fld =>
            refactParam.mtd map { mtdList =>
              mtdList flatMap { mtd =>
                refactParam.tgt map { tgtList =>
                  tgtList map { tgt =>
                    matchMetric(acronym = acronym, src = src, tgt = tgt, mtd = mtd, fld = fld)
                  }
                }
              } flatten
            }
          } flatten
        }
      } flatten
    }

    val listMetric = refKeys map{ listRefK =>
      listRefK flatMap  { refK =>
        MetaphorCode.RefactoringCache.get(refK)
      }
    }

    lazy val classesMap = (listMetric map { metricL =>
      metricL  map (_.getClasss)
    } map { optRegClass =>
      optRegClass map { regClass =>
        val tempMetric = (listMetric flatMap { metricL =>
          metricL find (_ == regClass) map { sMetricL =>
            Map(sMetricL.getMetric -> sMetricL.getValue)
          }
        }).getOrElse(Map.empty)
        (regClass -> tempMetric)
      } toMap
    }).getOrElse(Map.empty).toMap[String, Map[String, Double]]
    classesMap
  }

}

trait FitnessCache extends FitnessCacheUtils {

  var predictMetricsRecordar: Metric

  protected def recordar(operRef: RefactoringOperation): Future[Metric] = {
    val acronym = operRef.getRefType.getAcronym

    val rMetrics = (if(!operRef.getParams.isEmpty){
      //1. If is params defined
      val refactParam = if(acronym == RefAcronym.EC){
        retrieveMetrics(extractParamsEC(operRef), acronym)
      } else {
        retrieveMetrics(extractParams(operRef), acronym)
      }
      Map(acronym, refactParam)

    } else{
      //2. If is not params defined
      Map.empty
    }).toMap[String,Map[String, Map[String, Double]]]

    Future(rMetrics)
  }

  protected def memorizar(operRef:RefactoringOperation):Future[Metric]={
    ???
  }
}

//class FitnessScalaApply extends FitnessCache {
//  def PredictingMetrics(operations: List[RefactoringOperation]):Metric={
//    operations map {
//      operation =>
//???
//    }
//    ???
//  }
//}
