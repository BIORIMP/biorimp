package repositories;

import entities.Register;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by developer on 12/29/15.
 */
public class RegisterRepository extends Repository<Register> {

    public static String TABLE_NAME = "brp_register";

    public Register fetchAll(ResultSet resultSet) {
        return null;
    }

    public List<Register> fetchAll() {
        getConnection();
        List<Register> results = new ArrayList();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM " + TABLE_NAME + " ORDER BY " + Register.COLUMN_REFACTOR;
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                results.add(resultEntity(resultSet));
            }

            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return results;
    }

    @Override
    public Register resultEntity(ResultSet resultSet) {
        try {
            String refactor = resultSet.getString(Register.COLUMN_REFACTOR);
            String metric = resultSet.getString(Register.COLUMN_METRIC);
            String sources = resultSet.getString(Register.COLUMN_SOURCES);
            String targets = resultSet.getString(Register.COLUMN_TARGETS);
            String method =resultSet.getString(Register.COLUMN_METHOD);
            String field = resultSet.getString(Register.COLUMN_FIELD);
            String classs = resultSet.getString(Register.COLUMN_FIELD);
            double value = resultSet.getDouble(Register.COLUMN_VALUE);

            return new Register(refactor, metric, value,sources,targets,field,method,classs);
        } catch (Exception e) {
            return null;
        }
    }

    public Register getRegister(String refactorID, String src, String tgt,String mth, String fld){
        getConnection();
        Register register = new Register();
        if(connection!=null){
            try{
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND "+ Register.COLUMN_SOURCES + "= ? AND "
                        + Register.COLUMN_TARGETS+"= ? AND "+Register.COLUMN_METHOD+"= ? AND "+ Register.COLUMN_FIELD +"= ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, tgt);
                statement.setString(4, mth);
                statement.setString(5, fld);
                ResultSet resultSet = statement.executeQuery();

                while(resultSet.next()){
                    register = resultEntity(resultSet);
                }

                resultSet.close();
                statement.close();
                connection.close();

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return register;
    }

    public void insertRegister(String refactorID, String src, String metric, double value, String tgt, String mth, String fld, String classs){
        getConnection();
        if(connection!=null){
            try{
                String query = "INSERT INTO " + TABLE_NAME +" ("+Register.COLUMN_REFACTOR+ ","+ Register.COLUMN_SOURCES+","+Register.COLUMN_METRIC+","+ Register.COLUMN_VALUE+
                        ","+Register.COLUMN_TARGETS+","+Register.COLUMN_METHOD+","+Register.COLUMN_FIELD+","+Register.COLUMN_CLASS+") VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, metric);
                statement.setDouble(4,value);
                statement.setString(5,tgt);
                statement.setString(6,mth);
                statement.setString(7,fld);
                statement.setString(8,classs);
                statement.executeUpdate();
                statement.close();
                connection.close();

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }
    public void insertRegister(Register register){
        insertRegister(register.getRefactor(),register.getSources(), register.getMetric(),register.getValue(),register.getTargets(),
                register.getMethod(),register.getField(),register.getClasss());

    }


    public List<Register> getRegisters(String refactorID, String src, String tgt,String mth, String fld){
        getConnection();
        List<Register> results = new ArrayList();
        if(connection!=null){
            try{
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND "+ Register.COLUMN_SOURCES + "= ? AND "
                        + Register.COLUMN_TARGETS+"= ? AND "+Register.COLUMN_METHOD+"= ? AND "+ Register.COLUMN_FIELD +"= ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, tgt);
                statement.setString(4, mth);
                statement.setString(5, fld);
                ResultSet resultSet = statement.executeQuery();


                while(resultSet.next()){
                    results.add(resultEntity(resultSet));
                }

                resultSet.close();
                statement.close();
                connection.close();

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
    public List<Register> getRegistersByClass(String refactorID, String src, String tgt,String mth, String fld){
        getConnection();
        List<Register> results = new ArrayList();
        if(connection!=null){
            try{
                String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + Register.COLUMN_REFACTOR + " = ? AND "+ Register.COLUMN_SOURCES + "= ? AND "
                        + Register.COLUMN_TARGETS+"= ? AND "+Register.COLUMN_METHOD+"= ? AND "+ Register.COLUMN_FIELD +"= ? ORDER BY "+Register.COLUMN_CLASS;
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, refactorID);
                statement.setString(2, src);
                statement.setString(3, tgt);
                statement.setString(4, mth);
                statement.setString(5, fld);
                ResultSet resultSet = statement.executeQuery();


                while(resultSet.next()){
                    results.add(resultEntity(resultSet));
                }

                resultSet.close();
                statement.close();
                connection.close();

            }catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return results;
    }


}
