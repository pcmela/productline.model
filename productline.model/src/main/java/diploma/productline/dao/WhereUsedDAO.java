package diploma.productline.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import diploma.productline.entity.Element;
import diploma.productline.entity.Module;
import diploma.productline.entity.ProductLine;
import diploma.productline.entity.Variability;

public class WhereUsedDAO extends BaseDAO {

	private final String moduleSQL = "SELECT m.name AS module_name, p.name AS productline_name " + 
			"FROM " + 
			"(SELECT name, product_line_id FROM module WHERE product_line_id  IN (SELECT productline_id  FROM productline WHERE parent_productline = ?) AND name = ?) AS m " + 
			"JOIN " + 
			"productline AS p ON m.product_line_id = p.productline_id";
	
	private final String variabilitySQL = "SELECT m.v_name AS variability_name, p.name AS productline_name " + 
			"FROM "+ 
			"("+
			"SELECT m1.name, m1.product_line_id, v1.name AS v_name FROM module as m1 "+  
			"JOIN variability as v1 on m1.module_id = v1.module_id AND m1.product_line_id IN (SELECT productline_id  FROM productline WHERE parent_productline = ? AND v1.name = ?) "+
			") AS m "+
			"JOIN " + 
			"productline AS p ON m.product_line_id = p.productline_id";
	
	private final String elementsSQL = "SELECT m.v_name AS element_name, p.name AS productline_name " + 
			"FROM "+ 
			"("+
			"SELECT m1.name, m1.product_line_id, v1.name AS v_name FROM module as m1 "+  
			"JOIN element as v1 on m1.module_id = v1.module_id AND m1.product_line_id IN (SELECT productline_id  FROM productline WHERE parent_productline = ? AND v1.name = ?) "+
			") AS m "+
			"JOIN " + 
			"productline AS p ON m.product_line_id = p.productline_id";
	
	public Set<WhereUsedRecord> getModules(ProductLine p, Module m, Connection con) throws SQLException{
		Set<WhereUsedRecord> set = new HashSet<>();
		try(PreparedStatement prepareStatement = con.prepareStatement(moduleSQL)){
			prepareStatement.setInt(1, p.getId());
			prepareStatement.setString(2, m.getName());
			
			try(ResultSet result = prepareStatement.executeQuery()){
				while(result.next()){
					WhereUsedRecord rec = new WhereUsedRecord();
					rec.setProductLine(result.getString("productline_name"));
					rec.setName(result.getString("module_name"));
					rec.setType("Module");
					set.add(rec);
				}
			}
		}
		return set;
	}
	
	public Set<WhereUsedRecord> getVariabilities(ProductLine p, Variability v, Connection con) throws SQLException{
		Set<WhereUsedRecord> set = new HashSet<>();
		try(PreparedStatement prepareStatement = con.prepareStatement(variabilitySQL)){
			prepareStatement.setInt(1, p.getId());
			prepareStatement.setString(2, v.getName());
			
			try(ResultSet result = prepareStatement.executeQuery()){
				while(result.next()){
					WhereUsedRecord rec = new WhereUsedRecord();
					rec.setProductLine(result.getString("productline_name"));
					rec.setName(result.getString("variability_name"));
					rec.setType("Variability");
					set.add(rec);
				}
			}
		}
		return set;
	}
	
	public Set<WhereUsedRecord> getElements(ProductLine p, Element e, Connection con) throws SQLException{
		Set<WhereUsedRecord> set = new HashSet<>();
		try(PreparedStatement prepareStatement = con.prepareStatement(elementsSQL)){
			prepareStatement.setInt(1, p.getId());
			prepareStatement.setString(2, e.getName());
			
			try(ResultSet result = prepareStatement.executeQuery()){
				while(result.next()){
					WhereUsedRecord rec = new WhereUsedRecord();
					rec.setProductLine(result.getString("productline_name"));
					rec.setName(result.getString("variability_name"));
					rec.setType("Element");
					set.add(rec);
				}
			}
		}
		return set;
	}
}

