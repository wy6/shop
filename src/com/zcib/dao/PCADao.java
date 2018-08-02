package com.zcib.dao;

import java.util.List;
import java.util.Map;
import com.zcib.util.JDBCUtils;

public class PCADao {
	//获取省份列表
	public List<Map<String, Object>> getProvinces(){
		String sql = "select * from provinces";
		return JDBCUtils.select(sql);
	}

	//通过省级编号获取市级列表
	public List<Map<String, Object>> getCities(String provinceid){
		String sql = "select * from cities where provinceid= ?";
		return JDBCUtils.select(sql,provinceid);
	}

	//通过市级编号获取区级列表
	public List<Map<String, Object>> getAreas(String cityid){
		String sql = "select * from areas where cityid= ?";
		return JDBCUtils.select(sql,cityid);
	}
	

}
