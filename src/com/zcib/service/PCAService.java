package com.zcib.service;

import java.util.List;
import java.util.Map;

import com.zcib.dao.PCADao;
import com.zcib.util.JDBCUtils;

public class PCAService {
	
	private PCADao pcaDao = new PCADao();

	//获取省份列表
	public List<Map<String, Object>> getProvinces(){
		return pcaDao.getProvinces();
	}

	//通过省级编号获取市级列表
	public List<Map<String, Object>> getCities(String provinceid){
		return pcaDao.getCities(provinceid);
	}

	//通过市级编号获取区级列表
	public List<Map<String, Object>> getAreas(String cityid){
		return pcaDao.getAreas(cityid);
	}
	
	
	
	
}
